import exceptions.NoSuchChatException
import exceptions.NoSuchMessageException

object ChatService {

    private var messageIndex: Int = 1
    private var chats = mutableMapOf<Int, Chat>()
    private const val count: Int = 6

    fun addMessage(userId: Int, message: Message) {
        chats.getOrPut(userId) { Chat() }.apply {
            message.messageId = messageIndex++
            messages.add(message)
        }
    }

    private fun existenceChatCheck(userId: Int) {
        chats[userId] ?: throw NoSuchChatException("Chat with user #$userId does not exist")
    }

    private fun existenceMessageCheck(messageId: Int): Boolean {
        val exists = chats.values.asSequence()
            .filter { chat -> chat.messages.any { it.messageId == messageId } }
            .any()
        if (!exists) {
            throw NoSuchMessageException("Message with messageId =$messageId not found")
        }
        return true
    }

    private fun unReadCount(userId: Int): Int {
        return chats[userId]?.messages
            ?.asSequence()
            ?.map { if (!it.isRead && !it.isDeleted && it.isIncoming) 1 else 0 }
            ?.reduce { acc, count -> acc + count }
            ?: 0
    }

    private fun nullingReadCountByMessageId(messageId: Int) {
        val nullingRead = getChatByMessageId(messageId)
        nullingRead.messages.forEach { it.isRead = true }
    }

    fun deleteChat(userId: Int) {
        existenceChatCheck(userId)
        chats.remove(userId)
    }

    fun editMessage(messageId: Int, newText: String) {
        existenceMessageCheck(messageId)
        getChatByMessageId(messageId).messages
            .asSequence()
            .filter { it.messageId == messageId }
            .forEach { it.text = newText }
    }

    fun deleteMessage(messageId: Int) {
        existenceMessageCheck(messageId)
        getChatByMessageId(messageId).messages
            .asSequence()
            .filter { it.messageId == messageId }
            .forEach { it.isDeleted = true }
    }

    fun getLastMessages() {       //последние сообщения чатов
        if (chats.isEmpty()) throw NoSuchChatException("Chat List is empty")
        chats.forEach { (userId, chat) ->
            val lastMessage = chat.messages
                .lastOrNull { !it.isDeleted }
            val messageInfo = lastMessage
                ?.let {
                    "${lastMessage.toStringIsIncoming()} " + "Текст: ${it.text} MessageId: " +
                            "${lastMessage.messageId}. " + "Непрочитанных: ${unReadCount(userId)}"
                }
            println("Чат с пользователем #$userId - $messageInfo")
        }
    }

    fun getChatList() {
        if (chats.isEmpty()) throw NoSuchChatException("Chat List is empty")
        chats.asSequence()
            .map { (userId, _) -> "#$userId - Непроч.сообщ.: ${unReadCount(userId)}" }
            .forEach { println(it) }
    }

    private fun printMessagesByUserId(userId: Int) {
        chats[userId]?.messages
            ?.filter { !it.isDeleted }
            ?.takeLast(count)
            ?.map { message ->
                val messageType = if (message.isIncoming) "Входящее" else "Исходящее"
                "Тип: $messageType, Текст: ${message.text}, MessageId: ${message.messageId}"
            }
            ?.forEach { println(it) }
    }


    fun getAllMessagesByMessageId(messageId: Int): Int {
        existenceMessageCheck(messageId)
        val userById = findUserIdByMessageId(messageId)
        userById?.let { userId ->
            printMessagesByUserId(userById)
            nullingReadCountByMessageId(messageId)
            val unReadCount = unReadCount(userId)
            println("Непрочитанных сообщений с пользователем #$userById: ${unReadCount(userById)}")
            return unReadCount
        }
        return 0
    }

    fun findUserIdByMessageId(messageId: Int): Int? {
        val chatWithMessage = chats.values.find { chat -> chat.messages.any { it.messageId == messageId } }
        return chatWithMessage?.let { chatEntry ->
            chats.entries.firstOrNull { it.value == chatEntry }?.key
        }
    }

    fun getChatByMessageId(messageId: Int): Chat {
        return chats.values.find { chat ->
            chat.messages.any { it.messageId == messageId }
        }
            ?: throw NoSuchMessageException("Message with messageId =$messageId not found")
    }

    fun getChatMessages(userId: Int) {
        val chat = chats[userId] ?: throw NoSuchChatException("Чатов пока нет..")
        chat.messages
            .fold(0) { acc, message ->
                message.isRead = true
                acc + 1
            }
        printMessagesByUserId(userId)
        println("Непрочитанных сообщений с пользователем #$userId: ${unReadCount(userId)}")
    }

    fun clear() {
        chats = mutableMapOf<Int, Chat>()
        messageIndex = 1
    }
}
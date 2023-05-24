import exceptions.NoSuchChatException

object ChatService {

    private var messageIndex: Int = 1
    private val chats = mutableMapOf<Int, Chat>()
    private const val count: Int = 6

    fun addMessage(userId: Int, message: Message) {
        chats.getOrPut(userId) { Chat() }.apply {
            message.messageId = messageIndex++
            messages.add(message)
        }
    }

    private fun unReadCount(userId: Int): Int {
        val chat = chats[userId]
        return chat?.messages?.count { !it.isRead && !it.isDeleted && it.isIncoming } ?: 0
    }

    private fun nullingReadCountByMessageId(messageId: Int) {
        val nullingRead = getChatByMessageId(messageId)
        nullingRead.messages.forEach { it.isRead = true }
    }

    fun deleteChat(userId: Int) {
        chats.remove(userId)
    }

    fun editMessage(messageId: Int, newText: String) {
        val chatWithMessage = getChatByMessageId(messageId)
        chatWithMessage.messages.find { it.messageId == messageId }?.text = newText
    }

    fun deleteMessage(messageId: Int) {
        val chatWithMessage = getChatByMessageId(messageId)
        chatWithMessage.messages.find { it.messageId == messageId }?.isDeleted = true
    }

    fun printLastMessages() {       //последние сообщения чатов
        chats.forEach { (userId, chat) ->
            val lastMessage = chat.messages.lastOrNull { !it.isDeleted }
            val messageInfo = lastMessage?.let {
                "${lastMessage.toStringIsIncoming()} " + "Текст: ${it.text} MessageId: ${lastMessage.messageId}. " +
                        "Непрочитанных: ${unReadCount(userId)}"
            } ?: "No messages"
            println("Чат с пользователем #$userId - $messageInfo")
        }
    }

    fun getChatList() {       //список чатов
        chats.forEach { (userId, chat) ->
            println("#$userId - Непроч.сообщ.: ${unReadCount(userId)}")
        }
    }

    private fun printMessagesByUserId(userId: Int) {
        val userMessages = chats[userId]?.messages?.filter { !it.isDeleted }
        if (!userMessages.isNullOrEmpty()) {
            println("Чат с пользователем #$userId:")
            userMessages.takeLast(count).forEach { message ->
                val messageType = if (message.isIncoming) "Входящее" else "Исходящее"
                println("Тип: $messageType, Текст: ${message.text}, MessageId: ${message.messageId}")
            }
        }
    }

    fun getAllMessagesByMessageId(messageId: Int) {
        val userById = findUserIdByMessageId(messageId)
        userById?.let {
            printMessagesByUserId(userById)
            nullingReadCountByMessageId(messageId)
            println("Непрочитанных сообщений с пользователем #$userById: ${userById?.let { unReadCount(it) }}")
        }
    }

    private fun findUserIdByMessageId(messageId: Int): Int? {
        val chatWithMessage = chats.values.find { chat -> chat.messages.any { it.messageId == messageId } }
        return chatWithMessage?.let { chatEntry ->
            chats.entries.firstOrNull { it.value == chatEntry }?.key
        }
    }

    fun getChatByMessageId(messageId: Int): Chat {
        return chats.values.find { chat -> chat.messages.any { it.messageId == messageId } }
            ?: throw NoSuchElementException("Сообщение с MessageId=$messageId не найдено")
    }

    fun getChatMessages(userId: Int) {
        val chat = chats[userId] ?: throw NoSuchChatException("Error")
        chat.messages.forEach { it.isRead = true }
        printMessagesByUserId(userId)
        println("Непрочитанных сообщений с пользователем #$userId: ${unReadCount(userId)}")
    }
}
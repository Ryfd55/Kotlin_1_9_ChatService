import exceptions.NoSuchChatException

object ChatService {

    private var messageIndex: Int = 1
    private val chats = mutableMapOf<Int, Chat>()

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

    fun getChatMessages(userId: Int, count: Int): List<Message> {
        val chat = chats[userId] ?: throw NoSuchChatException("Error")
        return chat.messages.filter { !it.isDeleted }.takeLast(count)
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

    fun printLastMessages() {       //список чатов
        for ((userId, chat) in chats) {
            val lastMessage = chat.messages.lastOrNull { !it.isDeleted }
//            val unread = chat.messages.forEach{it.isRead == false}) println(1111) else println(2222)
            val messageInfo = lastMessage?.let {
                "${lastMessage.toStringIsIncoming()} Текст: ${it.text} MessageId: ${lastMessage.messageId} непрочитанных: ${
                    unReadCount(
                        userId
                    )
                }"
            } ?: "No messages"
            println("Чат с пользователем #$userId - $messageInfo")
        }
    }

    fun printMessagesByUserId(userId: Int) {
        val userMessages = chats[userId]?.messages?.filter { !it.isDeleted }
        if (userMessages != null) {
            if (userMessages.isNotEmpty()) {
                println("Чат с пользователем #$userId:")
                userMessages.forEach { message ->
                    val messageType = if (message.isIncoming) "Входящее" else "Исходящее"
                    println("Тип: $messageType, Текст: ${message.text}, MessageId: ${message.messageId}")

                }
            }
        }
    }

    fun getAllMessagesByMessageId(messageId: Int) {
        val userById = findUserIdByMessageId(messageId)
        if (userById != null) {
            printMessagesByUserId(userById)
        }
        nullingReadCountByMessageId(messageId)
    }

    fun findUserIdByMessageId(messageId: Int): Int? {
        val chatWithMessage = chats.values.find { chat -> chat.messages.any { it.messageId == messageId } }
        return chatWithMessage?.let { chatEntry ->
            chats.entries.firstOrNull { it.value == chatEntry }?.key
        }
    }

    fun getChatByMessageId(messageId: Int): Chat {
        return chats.values.find { chat -> chat.messages.any { it.messageId == messageId } }
            ?: throw NoSuchElementException("Сообщение с MessageId=$messageId не найдено")
    }
}
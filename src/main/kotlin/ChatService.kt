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

    fun getChatMessages(userId: Int, count: Int): List<Message> {
        val chat = chats[userId] ?: throw NoSuchChatException("Error")
        return chat.messages.filter { !it.isDeleted }.takeLast(count)
    }

    fun deleteChat(userId: Int) {
        chats.remove(userId)
    }


    fun printLastMessages() {       //список чатов
        for ((userId, chat) in chats) {
            val lastMessage = chat.messages.lastOrNull { !it.isDeleted }
            val messageInfo = lastMessage?.let {
                "${lastMessage.toStringIsIncoming()} Текст: ${it.text} MessageId: ${lastMessage.messageId}"
            } ?: "No messages"
            println("Чат с пользователем #$userId - $messageInfo")
        }
    }

    fun editMessage(messageId: Int, newText: String) {
        val chatWithMessage = chats.values.find { chat -> chat.messages.any { it.messageId == messageId } }
            ?: throw NoSuchElementException("Сообщение с MessageId=$messageId не найдено")
        chatWithMessage.messages.find { it.messageId == messageId }?.text = newText
    }

    fun deleteMessage(messageId: Int) {
        val chatWithMessage = chats.values.find { chat -> chat.messages.any { it.messageId == messageId } }
            ?: throw NoSuchElementException("Сообщение с MessageId=$messageId не найдено")
        chatWithMessage.messages.find { it.messageId == messageId }?.isDeleted = true
    }


}
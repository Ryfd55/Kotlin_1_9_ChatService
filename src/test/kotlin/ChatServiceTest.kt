import exceptions.NoSuchChatException
import exceptions.NoSuchMessageException
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun addMessage() {
        ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
    }

    @Test
    fun deleteChat() {
        ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_4", isRead = false))
        ChatService.deleteChat(2)
    }

    @Test(expected = NoSuchChatException::class)
    fun deleteChatException() {
        ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_4", isRead = false))
        ChatService.deleteChat(3)
    }

    @Test
    fun editMessage() {
        ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_4", isRead = false))
        ChatService.editMessage(2, "отредактированное сообщение")
    }

    @Test(expected = NoSuchMessageException::class)
    fun editMessageException() {
        ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_4", isRead = false))
        ChatService.editMessage(9, "отредактированное сообщение")
    }

    @Test
    fun deleteMessage() {
        ChatService.addMessage(1, Message(true, "Сообщение_1"))
        ChatService.addMessage(2, Message(true, "Сообщение_4"))
        ChatService.deleteMessage(1)
    }

    @Test
    fun getLastMessages() {
        ChatService.addMessage(1, Message(true, "Сообщение_1"))
        ChatService.addMessage(1, Message(true, "Сообщение_2"))
        ChatService.addMessage(2, Message(true, "Сообщение_3"))
        ChatService.addMessage(2, Message(true, "Сообщение_4"))
        ChatService.getLastMessages()
    }

    @Test(expected = NoSuchChatException::class)
    fun getLastMessagesException() {
        ChatService.getLastMessages()
    }

    @Test
    fun getChatList() {
        ChatService.addMessage(1, Message(true, "Сообщение_1"))
        ChatService.addMessage(1, Message(true, "Сообщение_2"))
        ChatService.addMessage(2, Message(true, "Сообщение_3"))
        ChatService.addMessage(2, Message(true, "Сообщение_4"))
        ChatService.getChatList()
    }

    @Test
    fun getAllMessagesByMessageId() {
        ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_2", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_3", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_4", isRead = false))
        ChatService.addMessage(2, Message(false, "Сообщение_5", isRead = true))
        ChatService.addMessage(2, Message(true, "Сообщение_6", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_7", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_8", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_9", isRead = false))
        ChatService.getAllMessagesByMessageId(3)
        val result = ChatService.getAllMessagesByMessageId(3)
        assertEquals(result, 0)
    }

    @Test
    fun getChatMessages() {
        ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_2", isRead = true))
        ChatService.getChatMessages(2)
    }

    @Test(expected = NoSuchMessageException::class)
    fun getChatByMessageId() {
        ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_2", isRead = true))
        ChatService.getChatByMessageId(3)
    }

    @Test
    fun findUserIdByMessageId() {
        ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
        ChatService.addMessage(2, Message(true, "Сообщение_2", isRead = true))
        ChatService.addMessage(2, Message(true, "Сообщение_3", isRead = true))
        val result = ChatService.findUserIdByMessageId(3)
        assertEquals(result, 2)
    }


}
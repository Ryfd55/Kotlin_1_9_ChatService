fun main() {
    ChatService.addMessage(1, Message(true, "Сообщение_1", isRead = false))
    ChatService.addMessage(1, Message(true, "Сообщение_2", isRead = false))
    ChatService.addMessage(1, Message(true, "Сообщение_3", isRead = false))
    ChatService.addMessage(2, Message(true, "Сообщение_4", isRead = false))
    ChatService.addMessage(2, Message(false, "Сообщение_5", isRead = true))
    ChatService.addMessage(3, Message(true, "Сообщение_6", isRead = false))
    ChatService.addMessage(3, Message(true, "Сообщение_7", isRead = false))
    ChatService.addMessage(3, Message(true, "Сообщение_8", isRead = false))
    ChatService.addMessage(4, Message(true, "Сообщение_9", isRead = false))
    ChatService.addMessage(4, Message(true, "Сообщение_10", isRead = false))


    println("________________________________________")
    println("Выводим чаты")
    ChatService.printLastMessages()

    println()
    println("________________________________________")
    println("Удаляем один чат")
    ChatService.deleteChat(1)
    ChatService.printLastMessages()


    println()
    println("________________________________________")
    println("Получить чат по номеру сообщения")
    ChatService.getChatByMessageId(5)
    ChatService.printLastMessages()

    println()
    println("________________________________________")
    println("Редактируем сообщение")
    ChatService.editMessage(5, "отредактированное сообщение")
    ChatService.printLastMessages()

    println()
    println("________________________________________")
    println("Удаление сообщения")
    ChatService.deleteMessage(7)
    ChatService.printLastMessages()


    println()
    println("________________________________________")
    println("Получение всех сообщений по Id сообщения")
    ChatService.getAllMessagesByMessageId(6)
    println()
    ChatService.printLastMessages()

    println()
    println("________________________________________")
    println("добавим в прочитанный чат 1 сообщение")
    ChatService.addMessage(3, Message(true, "Сообщение_11", isRead = false))
    ChatService.printLastMessages()

    println()
    println("________________________________________")
    println("Получение сообщений по Id чата")
    ChatService.printMessagesByUserId(2)
    println()
    ChatService.printLastMessages()

}

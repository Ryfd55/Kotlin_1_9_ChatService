fun main() {
    ChatService.addMessage(1, Message(false, "Сообщение_1"))
    ChatService.addMessage(1, Message(false, "Сообщение_2"))
    ChatService.addMessage(1, Message(true, "Сообщение_3"))
    ChatService.addMessage(2, Message(false, "Сообщение_4"))
    ChatService.addMessage(2, Message(false, "Сообщение_5"))
    ChatService.addMessage(3, Message(false, "Сообщение_6"))


    println("________________________________________")
    println("Создаем сообщения / чаты")
    ChatService.printLastMessages()

    println()
    println("________________________________________")
    println("Удаляем один чат")
    ChatService.deleteChat(1)
    ChatService.printLastMessages()


    println()
    println("________________________________________")
    println("Редактируем сообщение")
    ChatService.editMessage(5, "отредактированное сообщение")
    ChatService.printLastMessages()

    println()
    println("________________________________________")
    println("Удаление сообщения")
    ChatService.deleteMessage(6)
    ChatService.printLastMessages()

}

data class Message(
    val isIncoming: Boolean = false, //исходящее
    var text: String,
    var isDeleted: Boolean = false,  //неудаленное
    var isRead: Boolean = false,  // непрочитанное
    var messageId: Int = 0
) {
    fun toStringIsIncoming() = if (isIncoming) "вх." else "исх."
}

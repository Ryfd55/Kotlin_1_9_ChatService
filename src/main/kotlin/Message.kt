data class Message(
//    val userId: Long,
    val isIncoming: Boolean = false, //исходящее
    var text: String,
    var isDeleted: Boolean = false,  //неудаленное
    var isReaded: Boolean = false,  // непрочитанное
    var messageId: Int = 0,
) {
    fun toStringIsIncoming() = if (isIncoming) "вх." else "исх."
}
## Мини-проект. ChatService

Вы можете реализовать задачу так, как сочтёте нужным. Но вот несколько требований:

1. Должны быть чаты. Чат — это общение с одним человеком, так называемые direct messages.
2. Можно создавать чаты, удалять их, получать список имеющихся чатов.
3. В каждом чате есть сообщения от 1 до нескольких (см. раздел ниже).
4. Можно создавать сообщения, редактировать их и удалять. Для простоты — можно удалять и свои, и чужие.
5. В каждом чате есть прочитанные и непрочитанные сообщения.


Возможности для пользователя:

1. Видеть, сколько чатов не прочитано (например, service.getUnreadChatsCount). В каждом из таких чатов есть хотя бы одно непрочитанное сообщение.
2. Получить список чатов (например, service.getChats).
3. Получить список последних сообщений из чатов (можно в виде списка строк). Если сообщений в чате нет (все были удалены), то пишется «нет сообщений».
4. Получить список сообщений из чата, указав:
- ID чата;
- ID последнего сообщения, начиная с которого нужно подгрузить более новые;
- количество сообщений. После того как вызвана эта функция, все отданные сообщения автоматически считаются прочитанными.
5. Создать новое сообщение.
6. Удалить сообщение.
7. Создать чат. Чат создаётся, когда пользователю отправляется первое сообщение.
8. Удалить чат, т. е. целиком удалить всю переписку.

**Важный момент:** чтобы отделять одного пользователя от другого, передавайте во все функции первым параметром id пользователя. Например, service.getChats(999) — все чаты для пользователя с id=999.

Старайтесь использовать lambda-функции (их напишите сами) и extension-функции (есть в составе Iterable, Collection, List).

Расчёт статистики старайтесь производить как цепочку вызовов lambda-функций. Попробуйте обойтись без for, while и do-while.
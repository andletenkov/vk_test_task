# vk_test_task

`AddLikesTests` - класс с тестами на добавление лайка,
`DeleteLikeTests` - класс с тестами на удаление лайка.

Тесты разделены на две группы - позитивные и негативные (`positive` и `negative`, соответственно).

# Запуск тестов
1. Создать файл `actor.properties` с реальными пользовательскими данными (ID, access_token) по примеру `example.actor.properties`
2. Выполнить `gradlew clean test --info -Dgroups=имя_группы`, где `имя_группы` - `positive` или `negative`

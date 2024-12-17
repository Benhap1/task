# Разработка Системы Управления Задачами
### Описание задачи:
Файл полного описания задачи, а также итоговый JSON API находятся в папке: api-docs.

### Требования
1. Сервис должен поддерживать аутентификацию и авторизацию пользователей по email и паролю.
2. Доступ к API должен быть аутентифицирован с помощью JWT токена.
3. Пользователи могут управлять своими задачами: создавать новые, редактировать существующие, просматривать и удалять, менять статус и назначать исполнителей задачи.
4. Пользователи могут просматривать задачи других пользователей, а исполнители задачи могут менять статус своих задач.
5. К задачам можно оставлять комментарии.
6. API должно позволять получать задачи конкретного автора или исполнителя, а также все комментарии к ним. Необходимо обеспечить фильтрацию и пагинацию вывода.
7. Сервис должен корректно обрабатывать ошибки и возвращать понятные сообщения, а также валидировать входящие данные.
8. Сервис должен быть хорошо задокументирован. API должен быть описан с помощью Open API и Swagger. В сервисе должен быть настроен Swagger UI. Необходимо написать README с инструкциями для локального запуска проекта. Дев среду нужно поднимать с помощью docker compose.
9. Напишите несколько базовых тестов для проверки основных функций вашей системы.
10. Используйте для реализации системы язык Java 17+, Spring, Spring Boot. В качестве БД можно использовать PostgreSQL или MySQL. Для реализации аутентификации и авторизации нужно использовать Spring Security. Можно использовать дополнительные инструменты, если в этом есть необходимость (например, кэш).

### Технологический стек
**Java 17**
- **Spring Boot 3.3.6**
- **Spring Security**
- **PostgreSQL** в качестве базы данных
- **Flyway** для миграции базы данных
- **Maven** для сборки
- **Docker** для контейнеризации
- **Swagger UI** для документирования API
- **JUnit 5** и **Mockito** для тестирования

### Установка и запуск проекта
Склонируйте репозиторий на свой локальный компьютер:

```bash
git clone https://github.com/Benhap1/task
cd task-management-system
```


### Docker
1. Убедитесь, что у вас установлен Docker
2. В терминале Intellij Idea или другой среде разработки введите команду: *docker-compose up --build*

### Postgres
1. **Установите соединение с базой данных:**
    - *Имя базы данных:* tms
    - *Логин:* postgres
    - *Пароль:* postgres
    - *URL подключения:* `jdbc:postgresql://localhost:5432/tms`

### Запуск приложения
1. Запустите приложение в среде разработки.
   В приложении установлен Swagger для тестирования эндпоинтов.  
   Ссылка на Swagger: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

### Проверка эндпоинтов приложения
1. Так как в базе данных ничего нет, необходимо зарегистрироваться:
   **/api/users/register**. Данный эндпоинт не защищен. Пароль минимум 6 знаков. Выберите необходимую роль: **ADMIN** или **USER**
2. Следующим шагом необходимо залогиниться с теми регистрационными данными, которые были введены при регистрации: **/api/users/login**
В ответе вы получите **access token** и **refresh token**.
3. Для аутентификации в Swagger введите в *Authorize* ваш access token.
4. В зависимости от роли, вы будете допущены к остальным эндпоинтам API.
5. **Установленные ограничения:** 
- *валидация данных по некоторым полям;*
- *юзера не создать без email, пароля (минимум 6 знаков) и роли;*
- *задачу не создать без заголовка, описания, автора и исполнителя, а также приоритета и статуса;*
- *комментарии не создать без автора и контента.*
6. Описание эндпоинтов вы найдете в самом Swagger.
7. Права доступа к каждому методу можно найти в аннотациях контроллеров.

### Тестирование приложения
1. По условию задачи выполнены только самые базовые UNIT-тесты.

### Замечания*
#### Так как в задаче не были оговорены данные условия, то сделано так:
1. *При логине явно возвращаются токены, что является недопустимым!*
2. *Секретный ключ установлен в конфигурации, что в продакшн не является допустимым!*
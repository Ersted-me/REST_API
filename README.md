# Описание
Необходимо реализовать REST API, которое взаимодействует с файловым хранилищем  
и предоставляет возможность получать доступ к файлам и истории загрузок.  
### Сущности
User (List<> events)  
Event (File file)  
File  
 
### Требования
* Все CRUD операции для каждой из сущностей
* Придерживаться подхода MVC
* Для сборки проекта использовать Maven
* Для взаимодействия с БД - Hibernate
* Для конфигурирования Hibernate - аннотации
* Инициализация БД должна быть реализована с помощью flyway
* Взаимодействие с пользователем необходимо реализовать с помощью Postman (https://www.getpostman.com/)
* Репозиторий должен иметь бейдж сборки travis(https://travis-ci.com/)
* Рабочее приложение должно быть развернуто на heroku.com
### Технологии: 
* Java
* MySQL
* Hibernate
* HTTP
* Servlets
* Maven 
* Flyway.

### Запуск
1. Настроить src/main/resources/hibernate.cfg.xml (url, user, password)  
2. Настроить flyway в pom.xml или src/main/resources/flyway/flyway.conf
3. Интегрировать БД при помощи flyway  
   maven -> plugin -> flyway -> migrate
4. Запустить приложение  
   maven -> plugins -> tomcat7 -> run.

# Проект "Корпоративная библиотека"

Данный проект выполнен с использованием spring - boot, liquibase, hibernate, Spring Data JPA, postgreSQL и docker.
Проект нацелен на работу с базой данных при помощи JPA. В проекте используется liquibase для создания таблиц и выполнения миграций в базу данных из csv файлов.
При помощи docker - compose описан запуск контейнера с postgreSQL с описанием переменных окружения в dockerfile.
Конфигурация datasource реализована в application.yml. Доступ к эндпоинтам контроллеров осуществляется при помощи использованием Spring Security.

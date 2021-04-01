# ATM_Project
Многомодульный Spring Boot проект, построенный на фреймворке Maven.
Взаимодействие между модулями осуществляется по протоколу REST.

# Модуль "client" реализует:
1. Web интерфейс с использованием шаблонов Thymeleaf (доступен по адресу http://localhost:8080/)
2. Просмотр, добавление, редактирование, удаление записей в базе данных телефонов
3. Просмотр базы данных карт
4. Для получения и редактирования данных модуль "client" обращается по протоколу REST к модулю "serverDB"

# Модуль "serverDB" реализует:
1. Обмен информацией с модулем "client" по протоколу REST в формате JSON (запускается по адресу http://localhost:8081/)
2. Взаимодействие с базой данных (добавление, редактирование, удаление)

# ДО запуска приложения необходимо:
1. Скомпилировать и запустить на исполнение модули "serverDB" и "client". 
 

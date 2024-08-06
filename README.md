# Translator

Приложение-переводчик, использующий API Яндекс Переводчика

## API

### Пример запроса для использования приложения

~~~
POST localhost:8080/translate
Content-Type: application/json
Accept: application/json

{
    "sourceLang" : "en",
    "targetLang" : "ru",
    "text" : "Hello world, this is my first program"
}
~~~

У Rest контроллера единственный эндпоинт /translate, принимающий три параметра "sourceLang" - код исходного языка, "targetLang" - код целевого языка, "text" - строка, которую необходимо перевести на целевой язык.

### Пример ответа 

~~~
HTTP/1.1 200 
Content-Type: application/json
Content-Length: 98
Date: Tue, 06 Aug 2024 23:34:47 GMT

Здравствуйте мир, этот является мой первый программа
~~~


## Схема хранения данных в БД

БД состоит из всего одной скромной таблички, в которой содержатся все необходимые для хранения данные: IP-адрес пользователя, входную строку для перевода и результат перевода.


### Скрипт создания таблицы с необходимыми полями
~~~
CREATE TABLE TRANSLATION_REQUEST(
    id int generated by default as identity PRIMARY KEY,
    IP VARCHAR(255),
    inputText VARCHAR(255),
    outputText VARCHAR(255)
);
~~~


P.S.S Очень старался писать адекватный код под Spring, но очень сильно запутался с exceptionHandler и всем вот этим адекватным выводом правильных статусов ....
Был бы супер признателен каким-то комментариям/векторам, направляющим меня на путь истинный, а то самому больно на это смотреть.... Помогите....
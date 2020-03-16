# Number Service

```
git clone https://github.com/vladi-elena/number-service.git
cd number-service
mvn install 
```

```xml
<dependency>
    <groupId>com.optimaize</groupId>
    <artifactId>number-service</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

1. Чтобы начинающий программист мог правильно использовать сервис, я добавила документацию и проверки аргументов методов.
2. Чтобы злой программист не смог сломать сервис:
   1. Все открытые методы объявлены с модификатором final, чтобы защитить их от переопеределения.
   2. Все private поля объявлены с модификатором final, чтобы не подменить их через Reflection.
   3. Все методы - потоко безопасны.

Если же необходимо защитить код от Reverse Engineering, то можно использовать разные методы от обфускации (ProGuard) и изменения байт-кодовых инструкций до использование динамической загрузки классов с возможным шифрованием.

# Расширенный эмулятор БЭВМ

Приложение поддерживает также и [оригинальной эмулятор](https://se.ifmo.ru/bcomp/), который запускается командой:
```cmd
java -jar -Dmode=gui bcomp-extended-[version].jar
```

Для вызова HEXConverter для представления строковых значений в различных кодировках:
```
java -jar -Dmode=encoder bcomp-extended-[version].jar
```

### Скачать JAR: https://github.com/AppLoidx/bcomp-extended/releases/latest
#### Все jar: https://github.com/AppLoidx/bcomp-extended/releases
#### Документация: [wiki](https://github.com/AppLoidx/bcomp-extended/wiki)

<hr>
<br><br>

## Ключевые особенности

### Assembler с подсветкой синтаксиса
![](https://github.com/AppLoidx/bcomp-extended/blob/master/page/img/assembler-example.png?raw=true)

### Новые вкладки 
![](https://github.com/AppLoidx/bcomp-extended/blob/master/report/res/basic-view.png)

### Шпаргалка
![](https://github.com/AppLoidx/bcomp-extended/blob/master/report/res/cheat-sheet.png)

### Прямой доступ к памяти без всяких регистров
![](https://github.com/AppLoidx/bcomp-extended/blob/master/page/img/tools-example.png?raw=true)

Введите адрес памяти (в radix 16) и нажмите `Найти`, чтобы получить данные. Также вы можете изменить данные прямо в таблице и нажать `применить`, тогда введенны данные перезапишутся в память (за исключением тех значений, у которых неверный формат).

Также можно изменить количество показываемых ячеек с помощью соответствующего поля внизу таблицы.

### Консольный режим интегрированный с GUI
#### Since: 1.4+
Новая вкладка для консоли, данные которые те же, что и GUI.

Иными словами, вы можете вводить данные в память команды типа `DF89 w` и менять значение клавишного регистра через команду типа `AA12 a`

### Настройки внешнего вида
#### Since: 1.4+

Установка фонового изображения и настройка цветов стрелок

Также можно установить тактовую частоту, чтобы ускорить процесс выполнения команд

### Проверка синтаксиса еще до компиляции
#### Since: 1.4.3+

Пример вывода ошибки синтаксиса при перекрытии командой ORG:
![](https://github.com/AppLoidx/bcomp-extended/blob/master/report/res/assembler-syntax-example.png)

(в оригинальном эмуляторе это бы скомпилировалось)

### ВУ всегда активно
#### Since: 1.4.4.1+

В настрйокай есть CheckBox где можно ставить автоготовность ВУ

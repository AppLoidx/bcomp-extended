
<h1 align=center>Расширенный эмулятор БЭВМ</h1>

Для вызова HEXConverter для представления строковых значений в различных кодировках:
```
java -jar -Dmode=encoder bcomp-extended-[version].jar
```

#### Веб страница : https://apploidx.github.io/bcomp-extended/
#### Документация: [wiki](https://github.com/AppLoidx/bcomp-extended/wiki)

<hr>
<br><br>

<h2 align=center>Ключевые особенности</h2>

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
![](https://github.com/AppLoidx/bcomp-extended/blob/master/page/img/code-example.gif?raw=true)

Иными словами, вы можете вводить данные в память команды типа `DF89 w` и менять значение клавишного регистра через команду типа `AA12 a`

### Настройки внешнего вида
#### Since: 1.4+

Установка фонового изображения и настройка цветов стрелок
![](https://github.com/AppLoidx/bcomp-extended/blob/master/page/img/color-choose.PNG?raw=true)

Также можно установить тактовую частоту, чтобы ускорить процесс выполнения команд

### Проверка синтаксиса еще до компиляции
#### Since: 1.4.3+

Пример вывода ошибки синтаксиса при перекрытии командой ORG:
![](https://github.com/AppLoidx/bcomp-extended/blob/master/report/res/assembler-syntax-example.png)

(в оригинальном эмуляторе это бы скомпилировалось)

### ВУ всегда активно
#### Since: 1.4.4.1+

В настройках есть CheckBox где можно ставить автоготовность ВУ

[Пример использования для трассировки в работе с ВУ-3](https://github.com/AppLoidx/bcomp-extended/wiki/Трассировка-с-ВУ-3)

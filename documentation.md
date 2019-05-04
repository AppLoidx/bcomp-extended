# Документация

Интерфейс данного приложения построен путем реверс-инжиниринга [оригинального эмулятора БЭВМ](https://se.ifmo.ru/bcomp/)

## View-элементы

Весь интерфейс общего интерфейса строится на элементах View.

Вот пример из оригинальной программы:
```java
public class BasicView extends BCompPanel {
    private final CPU cpu;
    private final RunningCycleView cycleview;

    public BasicView(GUI gui) {
        super( ... );
        this.cpu = gui.getCPU();
        this.setSignalListeners(new SignalListener[0]);
        this.add(new ALUView(DisplayStyles.REG_C_X_BV, 245, 181, 90));
        this.cycleview = new RunningCycleView(this.cpu, DisplayStyles.REG_INSTR_X_BV, 245);
        this.add(this.cycleview);
    }

    public void panelActivate() {
        this.cycleview.update();
        super.panelActivate();
    }

    public String getPanelName() {
        return "Базовая ЭВМ";
    }

    ...
}
```

### ActivateblePanel

Следует отметить это:
```java
public abstract class BCompPanel extends ActivateblePanel 
```

Можно либо наследоваться от **BcompPanel** или же сразу от **ActivateblePanel**, так как все элементы добавляются в общий массив **ActivateblePanel[]**.

Из оригинальной БЭВМ от **BcompPanel** наследуются: **BasicView**(первое окно), **IOView**(окно ввода\вывода), **MPView(МПУ)**<br>
А от **ActivateblePanel** напрямую: **AssemblerView**

Для добавления кастомных окон используется ActivateblePanel.

## Добавление view элемента в основной Frame

Если коротко, то элементы добавляются в методе GUI.init():
```java
public void init() {
        this.cmanager = new ComponentManager(GUI.this);
        this.bcomp.startTimer();
        basicView = new BasicView(this);
        ioView = new IOView(this, this.pairgui);
        panes = new ActivateblePanel[]{
                basicView,                  // кастомизированные view
                ioView,
                new MPView(this),
                new AssemblerView(this),
                new CheatSheetView(this),
                new MemoryView(this),
                new ConsoleView(this),
                new SettingsView(this)
        };
        ...
```

Как можно отсюда заметить в каждый view - элемент передается экземпляр класс GUI. Это очень важно так как с помощью него мы сможем получить доступ к внутренним объектам эмулятора таким как память или регистры.

## Создание view элемента

Как говорилось ранее, новый view должен наследоваться от ActivateblePanel и желательно иметь конструктор, принимающий в качестве аргумента элемент GUI, чтобы получить или редактировать внутренние данные приложения.

Пример View-элемента:
```java
 public class SettingsView extends ActivateblePanel {
    private final GUI gui;
    Image img ;

    public SettingsView(GUI aGui){
        this.gui = aGui;
        setSleepTimeSettings(0, 0);
        JButton activeBusColorChooserBtn = new JButton("Выбрать цвет для активной стрелки");
        JButton busColorChooserBtn = new JButton("Выбрать цвет стрелок");
        busColorChooserBtn.addActionListener(a-> createColorChooseWindow(0));
        activeBusColorChooserBtn.addActionListener(a-> createColorChooseWindow(1));

        JButton backgroundSelectBtn = new JButton("Выбрать фоновое изображение");
        backgroundSelectBtn.addActionListener( a -> createFileChooseWindow());

        JButton setDefaultBtn = new JButton("Установить настройки по умолчанию");
        setDefaultBtn.addActionListener(a -> Settings.setDefault());

        activeBusColorChooserBtn.setBounds(20, 30, 250, 30);
        busColorChooserBtn.setBounds(20 + 250, 30, 250, 30);
        backgroundSelectBtn.setBounds(20, 60, 500, 30);
        setDefaultBtn.setBounds(20, 100, 500 , 30);

        this.add(activeBusColorChooserBtn);
        this.add(busColorChooserBtn);
        this.add(backgroundSelectBtn);
        this.add(setDefaultBtn);
    }
    private void setSleepTimeSettings(int x, int y){
        JLabel label = new JLabel("Время в мс между тактами(по умолчанию ~6 мс): ");
        JTextField value = new JTextField();
        JButton submit = new JButton("Применить");

        submit.addActionListener( (a) ->{
            
            // ...

            this.gui.getCPU().setTickFinishListener(() -> {
                this.gui.stepFinishViewElements();  // строго до вызова метода sleep
                try {
                    Thread.sleep(6);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        });

    // some code ...
```
Здесь представлена часть элемента SettingsView, который позволяет изменять настройки.

В конструкторе мы создаем и отрисовываем нужные нам элементы (кнопки, поля ввода и пр).

Затем на элементы кнопок типа "submit" мы добавляем ActionListener, чтобы обрабатывать нажатие кнопки.

Здесь можно увидеть бизнес-логику кнопки для установки тактовой частоты. Делается это через поле gui, определенное как объект, переданный через конструктор. С помощью него можно получить CPU и установить Runnable объект-функцию для операции после завершения такта.

## panelActivate и panelDeactivate

Два метода наследованных от ActivateblePanel - это методы вызываемые при отркытии панели и его закрытии.

В оригинальной БЭВМ элементы создается в конструкторе, а не при открытии панели, что оправданно, так как элементы - не сложные и не нагружают приложение даже будучи отрисованными.

Поэтому во всех View - элементы создаются в его конструкторе. В оригинальном приложении они также используются для передачи фокуса какому-нибудь управляющему элементу, например, клавишный регистр.

Листинг метода из AssemblerView:
```java
public void panelActivate() {
        this.text.requestFocus();
    }
```

Но иногда приходилось вставлять туда, относительно, более сложные куски кода.

Наример, при создании ConsoleView (панель консоли) нужно было всегда проверять наличие ввода на стороне CLI-класса. Отсюда могла возникнуть излишняя нагрузка системы. Поэтому я прибегнул, как мне кажется, не совсем хорошей идее:

* Создается поле типа Thread, который циклически опрашивает наличие на новый ввод (создается в конструкторе)
* Когда панель активируется этот поток запускается
* При закртытии панели - прерывается
* Когда пользователь опять заходит в этот панель все начинается со второго пункта

```java
    @Override
    public void panelActivate() {
        // может возникнуть java.lang.IllegalThreadStateException
        this.consoleInputField.requestFocus();
        if (!cliThreadIsActive) {
            newCLISession();
            cliThread.start();          // запуск потока!
            cliThreadIsActive = true;

            this.gui.getCPU().setTickFinishListener(() -> {
                if (cli.getSleep() > 0) {
                    try {
                        Thread.sleep((long) cli.getSleep());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });

            while (!outputStream.available()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!initializedOnce) {
                //newLine(outputStream.readString());
                initializedOnce = true;
            } else outputStream.readString();
        }
    }

    @Override
    public void panelDeactivate() {
        cliThread.interrupt();                  // прерывание
        cliThreadIsActive = false;

        this.gui.getCPU().setTickFinishListener(() -> {

                try {
                    Thread.sleep(Settings.getTickFinishSleepTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        });
    }
```

## Проблемы возникшие при разработке
Трудности, которые встречались при разработке extended-версии эмулятора
### Чужой не документированный код
Первая проблема с который я столкнулся - это было понятие механизма работы чужого не документированного кода. Приходилось иногда узнавать как что-то работает "методом тыка". Ранее я уже сталкивался с ней, когда разрабатывал эмулятор БЭВМ для бота, поэтому что-то базовое было.

Благодаря тому, что были хорошо названы поля и методы (классы) - я кое-как разобрался с логикой работы внешнего интерфейса эмулятора и поверхностно внутреннего устройства.

### Различие тактовых частот CLI и GUI режима
Вторая проблема была более непонятной, так как найти метод который устанавливает тактовую частоту было делом не из легких. Но в конце концов я нашел метод который вызывался при завершении одного такта.

Суть проблемы в том, что у консольной версии и у графической разные тактовые частоты. Для графического режима было важно наблюдать за изменениями элементов CPU. А в консольной версии необходимо было получить быструю трассировку данных.

На листинге выше вы можете наблюдать изменение тактовой частоты при активации и деактивации панели консоли.

### Отрисовка стрелок
Забавной, но критичной проблемой была отрисовка стрелок. Было два вида:
* Когда в первый раз написал свой метод вызываемый при окончании такта - стрелки перестали отображатся.
* При изменении настроек цвета, их цвет менялся только при активации

Первая проблема решилась добавлением в метод окончания такта функцию отрисовки стрелок (пришлось копаться глубоко внутри исходников)

Вторая проблема решилась также повторной отрисовкой стрелок при изменении настроек.

package application;


import application.views.MemoryView;
import application.views.*;
import ru.ifmo.cs.bcomp.*;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;


/**
 * From original bcomp edited by Arthur Kupriyanov
 *
 */
public class GUI extends JApplet {
    private static int framesCount = 0;
    private ComponentManager cmanager;
    private JTabbedPane tabs;
    private ActivateblePanel activePanel;
    private final BasicComp bcomp;
    private final CPU cpu;
    private final GUI pairgui;
    private ActivateblePanel[] panes;
    private Runnable onTickFinishAction;
    private BasicView basicView;
    private IOView ioView;
    private ConsoleView consoleView;
    public boolean IOAlwaysReady = false;
    private int lastPanelIndex = 0;
    private JFrame mainFrame;

    public GUI(MicroProgram mp, GUI pairgui) throws Exception {
        Settings.init();
        this.activePanel = null;
        this.bcomp = new BasicComp(mp);
        this.cpu = this.bcomp.getCPU();
        this.pairgui = pairgui;
    }
    public GUI(BasicComp bcomp, GUI pairgui){
        Settings.init();
        this.activePanel = null;
        this.bcomp = bcomp;
        this.cpu = this.bcomp.getCPU();
        this.pairgui = pairgui;
    }

    public GUI(MicroProgram mp) throws Exception {
        this(mp, null);
    }

    public GUI() throws Exception {
        this(MicroPrograms.getMicroProgram("base"));
    }

    public GUI(GUI pairgui) throws Exception {
        this(pairgui.getCPU().getMicroProgram(), pairgui);
    }

    public void init() {

        CycleActionManager.setGui(this);
        this.cmanager = new ComponentManager(GUI.this);
        this.bcomp.startTimer();
        basicView = new BasicView(this);
        ioView = new IOView(this, this.pairgui);
        consoleView = new ConsoleView(this);
        panes = new ActivateblePanel[]{
                basicView,
                ioView,
                new MPView(this),
                new AssemblerView(this),
                new CheatSheetView(this),
                new MemoryView(this),
                consoleView,
                new SettingsView(this)
        };


        // Верхние вкладки
        this.tabs = new JTabbedPane(){
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(DisplayStyles.COLOR_BACKGROUND_STYLE);
                g.fillRect(0 ,0, this.getWidth(), this.getHeight());
                super.paintComponent(g);
            }



        };

        this.tabs.addKeyListener(this.cmanager.getKeyListener());
        this.tabs.addChangeListener(e -> {
            if (GUI.this.activePanel != null) {
                GUI.this.activePanel.panelDeactivate();
            }

            GUI.this.activePanel = (ActivateblePanel) GUI.this.tabs.getSelectedComponent();
            GUI.this.activePanel.panelActivate();
            tabs.setForegroundAt(tabs.getSelectedIndex(), new Color(30,39,45));
            try {
                tabs.setForegroundAt(lastPanelIndex, Color.white);
            } catch (IndexOutOfBoundsException ignored) {}
            lastPanelIndex = tabs.getSelectedIndex();
        });

        for (ActivateblePanel pane : panes) {
            pane.setPreferredSize(DisplayStyles.PANE_SIZE);
            pane.setBackground(new Color(43,43,43));
            this.tabs.addTab(pane.getPanelName(), pane);
        }
        this.tabs.setBackground(DisplayStyles.COLOR_INPUT_TITLE);
        this.tabs.setForeground(Color.white);
        this.add(this.tabs);
    }

    public void start() {
        this.cmanager.switchFocus   ();
    }
    public void gui(){
        this.init();
        frameInit();
    }
    public void frameInit() {
        framesCount++;                                  // incrementing frames count
        mainFrame = new JFrame("Эмулятор БЭВМ");
        try {
            InputStream in = GUI.class.getClassLoader().getResourceAsStream("app-icon.png");
            if (in!=null){
                Image img = ImageIO.read(in);
                if (img!=null) mainFrame.setIconImage(img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                Settings.save();
                if (framesCount == 1) System.exit(0);
                else framesCount--;
            }

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }

            @Override

            public void windowClosed(WindowEvent e) {

            }
        });
        mainFrame.getContentPane().add(this);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        this.start();
    }

    public BasicComp getBasicComp() {
        return this.bcomp;
    }

    public CPU getCPU() {
        return this.cpu;
    }

    public IOCtrl[] getIOCtrls() {
        return this.bcomp.getIOCtrls();
    }

    public ComponentManager getComponentManager() {
        return this.cmanager;
    }

    public String getMicroProgramName() {
        return this.cpu.getMicroProgramName();
    }

    /**
     * Нужно чтобы отрисовывать активные стрелки
     *
     * Вызывается, при срабатывании метода в setTickFinishListener {@link CPU}
     */
    private void stepFinishViewElements() {
        basicView.stepFinish();
        ioView.stepFinish();
    }

    public void setSettingsTickTime(){
        reDrawBuses();
        cpu.setTickFinishListener(() -> {
            if (IOAlwaysReady) for (IOCtrl ctrl : this.ioView.getIoctrls()) ctrl.setFlag();
            if (onTickFinishAction!=null) onTickFinishAction.run();



            BCompPanel panel  = cmanager.getActivePanel();
            if (panel!=null) panel.stepFinish();
            try {
                Thread.sleep(Settings.getTickFinishSleepTime());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    public Runnable setOnTickFinishAction(Runnable r){
        Runnable oldRunnable = null;
        if (onTickFinishAction!=null) {
            oldRunnable = onTickFinishAction;
        }

        onTickFinishAction = r;
        return oldRunnable;

    }

    private synchronized void reDrawBuses(){
        stepFinishViewElements();  // строго до вызова метода sleep
    }

    public void traceLog(String log){
        consoleView.addConsoleText(log);
    }

    public IOView getIoView() {
        return ioView;
    }

    public void reload(){
        Settings.save();
        GUI gui = new GUI(bcomp, pairgui);
        gui.gui();
        mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
    }
}

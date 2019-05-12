package application;


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
public class GUI extends ru.ifmo.cs.bcomp.ui.GUI {
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
    private boolean traceLogging = false;
    public boolean IOAlwaysReady = false;

    public GUI(MicroProgram mp, GUI pairgui) throws Exception {
        this.activePanel = null;
        this.bcomp = new BasicComp(mp);
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
        this.tabs = new JTabbedPane();
        this.tabs.addKeyListener(this.cmanager.getKeyListener());
        this.tabs.addChangeListener(e -> {
            if (GUI.this.activePanel != null) {
                GUI.this.activePanel.panelDeactivate();
            }

            GUI.this.activePanel = (ActivateblePanel) GUI.this.tabs.getSelectedComponent();
            GUI.this.activePanel.panelActivate();
        });

        for (ActivateblePanel pane : panes) {
            pane.setPreferredSize(DisplayStyles.PANE_SIZE);

            this.tabs.addTab(pane.getPanelName(), pane);
        }

        this.add(this.tabs);

    }

    public void start() {
        this.cmanager.switchFocus();
    }

    public void gui() {
        JFrame mainFrame = new JFrame("БЭВМ"){

        };
        try {
            InputStream in = GUI.class.getClassLoader().getResourceAsStream("app-icon.png");
            if (in!=null){
                Image img = ImageIO.read(in);
                if (img!=null) mainFrame.setIconImage(img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.pairgui == null) {
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }



        mainFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                Settings.save();
                System.exit(0);
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
        this.init();
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
            if (IOAlwaysReady) this.ioView.getIoctrls()[3].setFlag();
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



    public void setTraceLogging(boolean value){
        traceLogging = value;
    }

    public IOView getIoView() {
        return ioView;
    }
}

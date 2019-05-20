package application;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import ru.ifmo.cs.bcomp.*;
import ru.ifmo.cs.bcomp.CPU.Reg;
import ru.ifmo.cs.bcomp.ui.components.*;
import ru.ifmo.cs.elements.DataDestination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * @author se.ifmo
 * @author Arthur Kupriyanov
 */
public class ComponentManager {
    private Color[] buttonColors;
    private ComponentManager.ButtonProperties[] buttonProperties;
    private final KeyAdapter keyListener;
    private static final int BUTTON_RUN = 5;
    private static final int BUTTON_CLOCK = 6;
    private JButton[] buttons;
    private ComponentManager.ButtonsPanel buttonsPanel;
    private final GUI gui;
    private final BasicComp bcomp;
    private final CPU cpu;
    private final IOCtrl[] ioctrls;
    private final MemoryView mem;
    private final MicroMemoryView micromem;
    private EnumMap<Reg, RegisterView> regs;
    private ActiveBitView activeBit;
    private volatile BCompPanel activePanel;
    private final long[] delayPeriods;
    private volatile int currentDelay;
    private volatile int savedDelay;
    private final Object lockActivePanel;
    private final JCheckBox cucheckbox;
    private volatile boolean cuswitch;
    private final SignalListener[] listeners;
    private ArrayList<ControlSignal> openBuses;
    private static final ControlSignal[] busSignals;

    public ComponentManager(GUI gui) {
//        super(gui);
        this.buttonColors = new Color[]{Settings.getMainTextColor(), DisplayStyles.COLOR_ACTIVE};
        this.buttonProperties = new ComponentManager.ButtonProperties[]{
                new ComponentManager.ButtonProperties(135, new String[]{"F4 Ввод адреса"},
                        e -> ComponentManager.this.cmdEnterAddr()),

                new ComponentManager.ButtonProperties(115, new String[]{"F5 Запись"},
                        e -> ComponentManager.this.cmdWrite()),

                new ComponentManager.ButtonProperties(115, new String[]{"F6 Чтение"},
                        e -> ComponentManager.this.cmdRead()),

                new ComponentManager.ButtonProperties(90, new String[]{"F7 Пуск"},
                        e -> ComponentManager.this.cmdStart()),

                new ComponentManager.ButtonProperties(135, new String[]{"F8 Продолжение"},
                        e -> ComponentManager.this.cmdContinue()),

                new ComponentManager.ButtonProperties(110, new String[]{"F9 Останов", "F9 Работа"},
                        e -> ComponentManager.this.cmdInvertRunState()),

                new ComponentManager.ButtonProperties(130, new String[]{"Shift+F9 Такт"},
                        e -> ComponentManager.this.cmdInvertClockState())};

        this.keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                case 81:
                    if (e.isControlDown()) {
                        System.exit(0);
                    }
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                case 110:
                case 111:
                default:
                    break;
                case 112:
                    if (e.isShiftDown()) {
                        ComponentManager.this.cmdAbout();
                    } else {
                        ComponentManager.this.cmdSetIOFlag(1);
                    }
                    break;
                case 113:
                    ComponentManager.this.cmdSetIOFlag(2);
                    break;
                case 114:
                    ComponentManager.this.cmdSetIOFlag(3);
                    break;
                case 115:
                    ComponentManager.this.cmdEnterAddr();
                    break;
                case 116:
                    ComponentManager.this.cmdWrite();
                    break;
                case 117:
                    ComponentManager.this.cmdRead();
                    break;
                case 118:
                    ComponentManager.this.cmdStart();
                    break;
                case 119:
                    ComponentManager.this.cmdContinue();
                    break;
                case 120:
                    if (e.isShiftDown()) {
                        ComponentManager.this.cmdInvertClockState();
                    } else {
                        ComponentManager.this.cmdInvertRunState();
                    }
                    break;
                case 121:
                    System.exit(0);
                    break;
                case 122:
                    ComponentManager.this.cmdPrevDelay();
                    break;
                case 123:
                    ComponentManager.this.cmdNextDelay();
                }

            }
        };
        this.buttonsPanel = new ComponentManager.ButtonsPanel();
        this.regs = new EnumMap<>(Reg.class);
        this.activeBit = new ActiveBitView(DisplayStyles.ACTIVE_BIT_X, 445);
        this.delayPeriods = new long[]{0L, 1L, 5L, 10L, 25L, 50L, 100L, 1000L};
        this.currentDelay = 3;
        this.lockActivePanel = new Object();
        this.cuswitch = false;
        this.openBuses = new ArrayList<>();
        this.gui = gui;
        this.bcomp = gui.getBasicComp();
        this.cpu = gui.getCPU();
        this.ioctrls = gui.getIOCtrls();
        this.cpu.setTickStartListener(() -> {
            synchronized(ComponentManager.this.lockActivePanel) {
                if (ComponentManager.this.activePanel != null) {
                    ComponentManager.this.activePanel.stepStart();
                }
            }

            ComponentManager.this.openBuses.clear();
        });
        this.cpu.setTickFinishListener(() -> {
            synchronized(ComponentManager.this.lockActivePanel) {
                if (ComponentManager.this.activePanel != null) {
                    ComponentManager.this.activePanel.stepFinish();
                }
            }

            if (ComponentManager.this.delayPeriods[ComponentManager.this.currentDelay] != 0L) {
                try {
                    Thread.sleep(ComponentManager.this.delayPeriods[ComponentManager.this.currentDelay]);
                } catch (InterruptedException ignored) {
                }
            }

        });
        ControlSignal[] arr$ = busSignals;
        int len$ = arr$.length;

        int i$;
        for(i$ = 0; i$ < len$; ++i$) {
            ControlSignal cs = arr$[i$];
            this.bcomp.addDestination(cs, new ComponentManager.SignalHandler(cs));
        }

        Reg[] arr2 = Reg.values();
        len$ = arr2.length;

        for(i$ = 0; i$ < len$; ++i$) {
            Reg reg = arr2[i$];
            switch(reg) {
            case KEY:
                InputRegisterView regKey = new InputRegisterView(this, this.cpu.getRegister(reg));
                this.regs.put(reg, regKey);
                regKey.setProperties(8, 445, false);
                break;
            case STATE:
                this.regs.put(reg, new StateRegisterView(this.cpu.getRegister(reg)));
                break;
            default:
                this.regs.put(reg, new RegisterView(this.cpu.getRegister(reg)));
            }
        }

        this.listeners = new SignalListener[]{new SignalListener(this.regs.get(Reg.STATE), ControlSignal.BUF_TO_STATE_C, ControlSignal.CLEAR_STATE_C, ControlSignal.SET_STATE_C), new SignalListener((DataDestination)this.regs.get(Reg.ADDR), new ControlSignal[]{ControlSignal.BUF_TO_ADDR}), new SignalListener((DataDestination)this.regs.get(Reg.DATA), new ControlSignal[]{ControlSignal.BUF_TO_DATA, ControlSignal.MEMORY_READ}), new SignalListener((DataDestination)this.regs.get(Reg.INSTR), new ControlSignal[]{ControlSignal.BUF_TO_INSTR}), new SignalListener((DataDestination)this.regs.get(Reg.IP), new ControlSignal[]{ControlSignal.BUF_TO_IP}), new SignalListener((DataDestination)this.regs.get(Reg.ACCUM), new ControlSignal[]{ControlSignal.BUF_TO_ACCUM, ControlSignal.IO2_IN, ControlSignal.IO3_IN, ControlSignal.IO7_IN, ControlSignal.IO8_IN, ControlSignal.IO9_IN})};
        this.mem = new MemoryView(this.cpu.getMemory(), 1, 1);
        this.micromem = new MicroMemoryView(this.cpu, DisplayStyles.MICROMEM_X, 1);
        this.bcomp.addDestination(ControlSignal.MEMORY_READ, value -> {
            if (ComponentManager.this.activePanel != null) {
                ComponentManager.this.mem.eventRead();
            } else {
                ComponentManager.this.mem.updateLastAddr();
            }

        });
        this.bcomp.addDestination(ControlSignal.MEMORY_WRITE, value -> {
            if (ComponentManager.this.activePanel != null) {
                ComponentManager.this.mem.eventWrite();
            } else {
                ComponentManager.this.mem.updateLastAddr();
            }

        });
        this.cucheckbox = new JCheckBox("Ввод в Устройство управления"){
            @Override
            protected void paintComponent(Graphics g)
            {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        this.cucheckbox.setBackground(new Color(255, 255, 255, 0));
        this.cucheckbox.setForeground(DisplayStyles.MAIN_TEXT_COLOR);
        this.cucheckbox.setOpaque(false);
        this.cucheckbox.addKeyListener(this.keyListener);
        this.cucheckbox.addItemListener(e -> ComponentManager.this.cuswitch = e.getStateChange() == ItemEvent.SELECTED);
    }

    public void panelActivate(BCompPanel component) {
        synchronized(this.lockActivePanel) {
            this.activePanel = component;
            this.bcomp.addDestination(this.activePanel.getSignalListeners());
            this.bcomp.addDestination(this.listeners);
        }

        component.add(this.mem);
        component.add(this.buttonsPanel);
        component.add(this.activeBit);
        component.add(this.regs.get(Reg.KEY));
        this.mem.updateMemory();
        this.cuswitch = false;
        this.switchFocus();
    }

    public void panelDeactivate() {
        synchronized(this.lockActivePanel) {
            this.bcomp.removeDestination(this.listeners);
            this.bcomp.removeDestination(this.activePanel.getSignalListeners());
            this.activePanel = null;
        }
    }

    public void keyPressed(KeyEvent e) {
        this.keyListener.keyPressed(e);
    }

    public void switchFocus() {
        ((InputRegisterView)this.regs.get(Reg.KEY)).setActive();
    }

    public RegisterView getRegisterView(Reg reg) {
        return this.regs.get(reg);
    }

    public void cmdContinue() {
        this.cpu.startContinue();
    }

    public void cmdEnterAddr() {
        if (this.cuswitch) {
            this.cpu.runSetMAddr();
            this.regs.get(Reg.MIP).setValue();
        } else {
            this.cpu.startSetAddr();
        }

    }

    public void cmdWrite() {
        if (this.cuswitch) {
            this.micromem.updateLastAddr();
            this.cpu.runMWrite();
            this.micromem.updateMemory();
            this.regs.get(Reg.MIP).setValue();
        } else {
            this.cpu.startWrite();
        }

    }

    public void cmdRead() {
        if (this.cuswitch) {
            this.micromem.eventRead();
            this.cpu.runMRead();
            this.regs.get(Reg.MIP).setValue();
            this.regs.get(Reg.MINSTR).setValue();
        } else {
            this.cpu.startRead();
        }

    }

    public void cmdStart() {
        this.cpu.startStart();
    }

    public void cmdInvertRunState() {
        this.cpu.invertRunState();
        int state = this.cpu.getStateValue(7);

        this.buttons[5].setForeground(this.buttonColors[state]);
        this.buttons[5].setText(this.buttonProperties[5].texts[state]);
    }

    public void cmdInvertClockState() {
        boolean state = this.cpu.invertClockState();
        this.buttons[6].setForeground(this.buttonColors[state ? 0 : 1]);
    }

    public void cmdSetIOFlag(int dev) {
        this.ioctrls[dev].setFlag();
    }

    public void cmdNextDelay() {
        this.currentDelay = this.currentDelay < this.delayPeriods.length - 1 ? this.currentDelay + 1 : 0;
    }

    public void cmdPrevDelay() {
        this.currentDelay = (this.currentDelay > 0 ? this.currentDelay : this.delayPeriods.length) - 1;
    }

    public void saveDelay() {
        this.savedDelay = this.currentDelay;
        this.currentDelay = 0;
    }

    public void restoreDelay() {
        this.currentDelay = this.savedDelay;
    }

    public void cmdAbout() {
        JOptionPane.showMessageDialog(this.gui, "Эмулятор Базовой ЭВМ. Версия r" + GUI.class.getPackage().getImplementationVersion() + "\n\nЗагружена " + this.gui.getMicroProgramName() + " микропрограмма", "О программе", 1);
    }

    public MicroMemoryView getMicroMemory() {
        return this.micromem;
    }

    public JCheckBox getMPCheckBox() {
        return this.cucheckbox;
    }

    public ActiveBitView getActiveBit() {
        return this.activeBit;
    }

    public KeyListener getKeyListener() {
        return this.keyListener;
    }

    public ArrayList<ControlSignal> getActiveSignals() {
        return this.openBuses;
    }

    public void clearActiveSignals() {
        this.openBuses.clear();
    }

    static {
        busSignals = new ControlSignal[]{ControlSignal.DATA_TO_ALU, ControlSignal.INSTR_TO_ALU, ControlSignal.IP_TO_ALU, ControlSignal.ACCUM_TO_ALU, ControlSignal.STATE_TO_ALU, ControlSignal.KEY_TO_ALU, ControlSignal.BUF_TO_ADDR, ControlSignal.BUF_TO_DATA, ControlSignal.BUF_TO_INSTR, ControlSignal.BUF_TO_IP, ControlSignal.BUF_TO_ACCUM, ControlSignal.MEMORY_READ, ControlSignal.MEMORY_WRITE, ControlSignal.INPUT_OUTPUT, ControlSignal.IO0_TSF, ControlSignal.IO1_TSF, ControlSignal.IO1_OUT, ControlSignal.IO2_TSF, ControlSignal.IO2_IN, ControlSignal.IO3_TSF, ControlSignal.IO3_IN, ControlSignal.IO3_OUT};
    }

    private class ButtonsPanel extends JComponent {
        public ButtonsPanel() {
            this.setBounds(0, 514, 856, 30);
            int buttonsX = 1;
            ComponentManager.this.buttons = new JButton[ComponentManager.this.buttonProperties.length];

            for(int i = 0; i < ComponentManager.this.buttons.length; ++i) {
                ComponentManager.this.buttons[i] = new JButton(ComponentManager.this.buttonProperties[i].texts[0]);
                ComponentManager.this.buttons[i].setForeground(ComponentManager.this.buttonColors[0]);
                ComponentManager.this.buttons[i].setBackground(DisplayStyles.COLOR_INPUT_TITLE);
                ComponentManager.this.buttons[i].setFont(DisplayStyles.FONT_BUTTONS_PANEL_TEXT);
                ComponentManager.this.buttons[i].setBounds(buttonsX, 0, ComponentManager.this.buttonProperties[i].width, 30);
                buttonsX += ComponentManager.this.buttonProperties[i].width + 2;
                ComponentManager.this.buttons[i].setFocusable(false);
                ComponentManager.this.buttons[i].addActionListener(ComponentManager.this.buttonProperties[i].listener);
                this.add(ComponentManager.this.buttons[i]);
            }

        }
    }

    private class ButtonProperties {
        public final int width;
        public final String[] texts;
        public final ActionListener listener;

        public ButtonProperties(int width, String[] texts, ActionListener listener) {
            this.width = width;
            this.texts = texts;
            this.listener = listener;
        }
    }

    private class SignalHandler implements DataDestination {
        private final ControlSignal signal;

        public SignalHandler(ControlSignal signal) {
            this.signal = signal;
        }

        public void setValue(int value) {
            ComponentManager.this.openBuses.add(this.signal);
        }
    }

    public boolean isCuswitch() {
        return cuswitch;
    }

    public void setCuswitch(boolean cuswitch) {
        this.cuswitch = cuswitch;
    }

    public BCompPanel getActivePanel(){
        return this.activePanel;
    }
}

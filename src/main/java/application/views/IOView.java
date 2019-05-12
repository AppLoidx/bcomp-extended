package application.views;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import application.DisplayStyles;
import application.GUI;
import application.RegisterView;
import application.InputRegisterView;
import application.BCompLabel;

import ru.ifmo.cs.bcomp.CPU.Reg;
import ru.ifmo.cs.bcomp.ControlSignal;
import ru.ifmo.cs.bcomp.IOCtrl;
import ru.ifmo.cs.bcomp.SignalListener;
import ru.ifmo.cs.bcomp.ui.components.*;
import ru.ifmo.cs.bcomp.ui.io.*;
import ru.ifmo.cs.elements.DataDestination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import ru.ifmo.cs.bcomp.ui.components.DisplayStyles;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

/**
 * @author Arthur Kupriyanov
 */
public class IOView extends application.BCompPanel {
    private final IOCtrl[] ioctrls;
    private TextPrinter textPrinter = null;
    private Ticker ticker = null;
    private SevenSegmentDisplay ssd = null;
    private Keyboard kbd = null;
    private Numpad numpad = null;
    private GUI pairgui = null;
    private final RegisterView[] ioregs = new RegisterView[3];
    private final JButton[] flags = new JButton[]{new JButton("F1 ВУ1"), new JButton("F2 ВУ2"), new JButton("F3 ВУ3")};
    private final BusView[] intrBuses;

    public IOView(final GUI gui, GUI _pairgui) {
        super(gui.getComponentManager(), new RegisterProperties[]{new RegisterProperties(Reg.ADDR, application.DisplayStyles.CU_X_IO, 86, true), new RegisterProperties(Reg.IP, application.DisplayStyles.CU_X_IO, 155, true), new RegisterProperties(Reg.DATA, application.DisplayStyles.CU_X_IO, 224, true), new RegisterProperties(Reg.INSTR, application.DisplayStyles.CU_X_IO, 293, true), new RegisterProperties(Reg.ACCUM, application.DisplayStyles.REG_ACC_X_IO, 362, true), new RegisterProperties(Reg.STATE, application.DisplayStyles.CU_X_IO, 362, false)}, new BusView[]{new BusView(new int[][]{{application.DisplayStyles.IO1_CENTER, 80}, {application.DisplayStyles.IO1_CENTER, 96}, {application.DisplayStyles.BUS_TSF_X, 96}, {application.DisplayStyles.BUS_TSF_X, 82}}, new ControlSignal[]{ControlSignal.IO1_TSF}), new BusView(new int[][]{{application.DisplayStyles.IO2_CENTER, 80}, {application.DisplayStyles.IO2_CENTER, 96}, {application.DisplayStyles.BUS_TSF_X, 96}, {application.DisplayStyles.BUS_TSF_X, 82}}, new ControlSignal[]{ControlSignal.IO2_TSF}), new BusView(new int[][]{{application.DisplayStyles.IO3_CENTER, 80}, {application.DisplayStyles.IO3_CENTER, 96}, {application.DisplayStyles.BUS_TSF_X, 96}, {application.DisplayStyles.BUS_TSF_X, 82}}, new ControlSignal[]{ControlSignal.IO3_TSF}), new BusView(new int[][]{{application.DisplayStyles.IO1_CENTER, 145}, {application.DisplayStyles.IO1_CENTER, 152}}, new ControlSignal[]{ControlSignal.INPUT_OUTPUT}), new BusView(new int[][]{{application.DisplayStyles.IO2_CENTER, 145}, {application.DisplayStyles.IO2_CENTER, 152}}, new ControlSignal[]{ControlSignal.INPUT_OUTPUT}), new BusView(new int[][]{{application.DisplayStyles.BUS_IO_ADDR_X, 237}, {application.DisplayStyles.BUS_TSF_X, 237}, {application.DisplayStyles.BUS_TSF_X, 145}, {application.DisplayStyles.IO3_CENTER, 145}, {application.DisplayStyles.IO3_CENTER, 152}}, new ControlSignal[]{ControlSignal.INPUT_OUTPUT}), new BusView(new int[][]{{application.DisplayStyles.IO1_CENTER, 263}, {application.DisplayStyles.IO1_CENTER, 256}}, new ControlSignal[]{ControlSignal.INPUT_OUTPUT}), new BusView(new int[][]{{application.DisplayStyles.IO2_CENTER, 263}, {application.DisplayStyles.IO2_CENTER, 256}}, new ControlSignal[]{ControlSignal.INPUT_OUTPUT}), new BusView(new int[][]{{application.DisplayStyles.BUS_IO_ADDR_X, 263}, {application.DisplayStyles.IO3_CENTER, 263}, {application.DisplayStyles.IO3_CENTER, 256}}, new ControlSignal[]{ControlSignal.INPUT_OUTPUT}), new BusView(new int[][]{{application.DisplayStyles.IO2_CENTER, 323}, {application.DisplayStyles.IO2_CENTER, 308}, {application.DisplayStyles.BUS_TSF_X, 308}, {application.DisplayStyles.BUS_TSF_X, 375}, {application.DisplayStyles.BUS_IN_X, 375}}, new ControlSignal[]{ControlSignal.IO2_IN}), new BusView(new int[][]{{application.DisplayStyles.IO3_CENTER, 323}, {application.DisplayStyles.IO3_CENTER, 308}, {application.DisplayStyles.BUS_TSF_X, 308}, {application.DisplayStyles.BUS_TSF_X, 375}, {application.DisplayStyles.BUS_IN_X, 375}}, new ControlSignal[]{ControlSignal.IO3_IN}), new BusView(new int[][]{{application.DisplayStyles.BUS_OUT_X, 401}, {application.DisplayStyles.IO1_CENTER, 401}, {application.DisplayStyles.IO1_CENTER, 394}}, new ControlSignal[]{ControlSignal.IO1_OUT}), new BusView(new int[][]{{application.DisplayStyles.BUS_OUT_X, 401}, {application.DisplayStyles.IO3_CENTER, 401}, {application.DisplayStyles.IO3_CENTER, 394}}, new ControlSignal[]{ControlSignal.IO3_OUT})});
        this.intrBuses = new BusView[]{new BusView(new int[][]{{application.DisplayStyles.IO1_CENTER, 46}, {application.DisplayStyles.IO1_CENTER, 30}, {application.DisplayStyles.BUS_INTR_LEFT_X, 30}}, new ControlSignal[0]), new BusView(new int[][]{{application.DisplayStyles.IO2_CENTER, 46}, {application.DisplayStyles.IO2_CENTER, 30}, {application.DisplayStyles.BUS_INTR_LEFT_X, 30}}, new ControlSignal[0]), new BusView(new int[][]{{application.DisplayStyles.IO3_CENTER, 46}, {application.DisplayStyles.IO3_CENTER, 30}, {application.DisplayStyles.BUS_INTR_LEFT_X, 30}}, new ControlSignal[0])};
        this.pairgui = _pairgui;
        this.ioctrls = gui.getIOCtrls();
        JButton button = new JButton("ВУ4");
        button.setFont(application.DisplayStyles.FONT_BUTTONS_PANEL_TEXT);
        button.setBounds(application.DisplayStyles.IO1_CENTER, 445, 100, 25);
        button.setFocusable(false);
        button.addActionListener(e -> {
            if (IOView.this.textPrinter == null) {
                IOView.this.textPrinter = new TextPrinter(IOView.this.ioctrls[4]);
            }

            IOView.this.textPrinter.activate();
        });
        this.add(button);
        button = new JButton("ВУ5");
        button.setFont(application.DisplayStyles.FONT_BUTTONS_PANEL_TEXT);
        button.setBounds(application.DisplayStyles.IO2_CENTER, 445, 100, 25);
        button.setFocusable(false);
        button.addActionListener(e -> {
            if (IOView.this.ticker == null) {
                IOView.this.ticker = new Ticker(IOView.this.ioctrls[5]);
            }

            IOView.this.ticker.activate();
        });
        this.add(button);
        button = new JButton("ВУ6");
        button.setFont(application.DisplayStyles.FONT_BUTTONS_PANEL_TEXT);
        button.setBounds(application.DisplayStyles.IO3_CENTER - 30, 445, 100, 25);
        button.setFocusable(false);
        button.addActionListener(e -> {
            if (IOView.this.ssd == null) {
                IOView.this.ssd = new SevenSegmentDisplay(IOView.this.ioctrls[6]);
            }

            IOView.this.ssd.activate();
        });
        this.add(button);
        button = new JButton("ВУ7");
        button.setFont(application.DisplayStyles.FONT_BUTTONS_PANEL_TEXT);
        button.setBounds(application.DisplayStyles.IO1_CENTER, 475, 100, 25);
        button.setFocusable(false);
        button.addActionListener(e -> {
            if (IOView.this.kbd == null) {
                IOView.this.kbd = new Keyboard(IOView.this.ioctrls[7]);
            }

            IOView.this.kbd.activate();
        });
        this.add(button);
        button = new JButton("ВУ8");
        button.setFont(application.DisplayStyles.FONT_BUTTONS_PANEL_TEXT);
        button.setBounds(application.DisplayStyles.IO2_CENTER, 475, 100, 25);
        button.setFocusable(false);
        button.addActionListener(e -> {
            if (IOView.this.numpad == null) {
                IOView.this.numpad = new Numpad(IOView.this.ioctrls[8]);
            }

            IOView.this.numpad.activate();
        });
        this.add(button);
        button = new JButton("ВУ9");
        button.setFont(application.DisplayStyles.FONT_BUTTONS_PANEL_TEXT);
        button.setBounds(application.DisplayStyles.IO3_CENTER - 30, 475, 100, 25);
        button.setFocusable(false);
        button.addActionListener(e -> {
            if (IOView.this.pairgui == null) {
                try {
                    IOView.this.pairgui = new GUI(gui);
                    IOView.this.pairgui.gui();
                    new BComp2BCompIODev(gui.getBasicComp().getIOCtrls()[9], IOView.this.pairgui.getBasicComp().getIOCtrls()[9]);
                } catch (Exception ignored) {
                }
            }

            gui.requestFocus();
        });
        this.add(button);

        for(int i = 0; i < this.ioregs.length; ++i) {
            int x = application.DisplayStyles.IO_X + i * application.DisplayStyles.IO_DELIM;
            this.ioregs[i] = i == 0 ? new RegisterView(this.ioctrls[i + 1].getRegData()) : new InputRegisterView(this.cmanager, this.ioctrls[i + 1].getRegData());
            this.ioregs[i].setProperties(x, 328, false);
            this.add(this.ioregs[i]);
            this.flags[i].setFont(application.DisplayStyles.FONT_BUTTONS_PANEL_TEXT);
            this.flags[i].setBounds(x + application.DisplayStyles.FLAG_OFFSET, 51, 100, 25);
            this.flags[i].setFocusable(false);
            this.add(this.flags[i]);
            this.flags[i].addActionListener(new IOView.FlagButtonListener(this.ioctrls[i + 1]));
            this.ioctrls[i + 1].addDestination(ru.ifmo.cs.bcomp.IOCtrl.ControlSignal.SETFLAG, new IOView.FlagListener(this.flags[i]));
            this.add(new BCompLabel(x, 166, application.DisplayStyles.REG_8_WIDTH, "Дешифратор", "адреса и", "приказов"));
        }

        this.add(new BCompLabel(application.DisplayStyles.CU_X_IO, 17, application.DisplayStyles.REG_8_WIDTH, "Устройство", "управления"));
        this.addLabel("Запрос прерывания", 6);
        this.addLabel("Состояние флага ВУ", 104);
        this.addLabel("Адрес ВУ", 124);
        this.addLabel("Приказ на ввод/вывод", 271);
        this.addLabel("Шина ввода", 287);
        this.addLabel("Шина вывода", 409);
        DataDestination intrListener = new DataDestination() {
            public void setValue(int value) {
                IOView.this.drawIntrBuses(IOView.this.getGraphics());
            }
        };
        this.setSignalListeners(new SignalListener[]{new SignalListener(this.ioregs[0], ControlSignal.IO1_OUT), new SignalListener(this.ioregs[2], new ControlSignal[]{ControlSignal.IO3_OUT}), new SignalListener(intrListener, new ControlSignal[]{ControlSignal.IO1_SETFLAG}), new SignalListener(intrListener, new ControlSignal[]{ControlSignal.IO2_SETFLAG}), new SignalListener(intrListener, new ControlSignal[]{ControlSignal.IO3_SETFLAG})});

    }

    private void addLabel(String text, int y) {
        JLabel l = new JLabel(text, 0);
        l.setFont(application.DisplayStyles.FONT_COURIER_BOLD_18);
        l.setForeground(new Color(80,122,117));
        l.setBounds(application.DisplayStyles.IO1_CENTER, y, application.DisplayStyles.IO3_CENTER - application.DisplayStyles.IO1_CENTER, 16);
        this.add(l);
    }

    private void drawIntrBuses(Graphics g) {
        int i;
        for(i = 0; i < 3; ++i) {
            if (this.ioctrls[i + 1].getFlag() == 0) {
                this.intrBuses[i].draw(g, application.DisplayStyles.COLOR_BUS);
            }
        }

        for(i = 0; i < 3; ++i) {
            if (this.ioctrls[i + 1].getFlag() == 1) {
                this.intrBuses[i].draw(g, application.DisplayStyles.COLOR_ACTIVE);
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawIntrBuses(g);
    }

    public String getPanelName() {
        return "Работа с ВУ";
    }

    public void stepFinish() {
        super.stepFinish();

        for (ControlSignal signal : this.cmanager.getActiveSignals()) {
            switch (signal) {
                case IO1_OUT:
                    this.ioregs[0].setValue();
                    break;
                case IO3_OUT:
                    this.ioregs[2].setValue();
            }
        }

    }

    private class FlagListener implements DataDestination {
        private final JButton flag;

        FlagListener(JButton flag) {
            this.flag = flag;
        }

        public void setValue(int value) {
            this.flag.setForeground(value == 1 ? application.DisplayStyles.COLOR_ACTIVE : DisplayStyles.COLOR_TEXT);
        }
    }

    private class FlagButtonListener implements ActionListener {
        private final IOCtrl ioctrl;

        public FlagButtonListener(IOCtrl ioctrl) {
            this.ioctrl = ioctrl;
        }

        public void actionPerformed(ActionEvent e) {
            this.ioctrl.setFlag();
        }
    }

    public IOCtrl[] getIoctrls() {
        return ioctrls;
    }
}


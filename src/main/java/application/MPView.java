package application;

/**
 * @author Arthur Kupriyanov
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import ru.ifmo.cs.bcomp.CPU.Reg;
import ru.ifmo.cs.bcomp.ControlSignal;
import ru.ifmo.cs.bcomp.SignalListener;
import ru.ifmo.cs.bcomp.ui.GUI;
import ru.ifmo.cs.bcomp.ui.components.*;

import javax.swing.*;
import java.util.ArrayList;

public class MPView extends BCompPanel {
    private final MicroMemoryView mem;
    private final RegisterView regMIP;
    private final RegisterView regMInstr;
    private final RegisterView regBuf;
    private final RegisterView regState;
    private final JCheckBox cucheckbox;
    private static final ControlSignal[] statesignals = new ControlSignal[0];

    public MPView(GUI gui) {
        super(gui.getComponentManager(), new RegisterProperties[]{new RegisterProperties(Reg.ADDR, DisplayStyles.CU_X_IO, 17, true), new RegisterProperties(Reg.IP, DisplayStyles.REG_IP_X_MP, 17, true), new RegisterProperties(Reg.DATA, DisplayStyles.CU_X_IO, 86, true), new RegisterProperties(Reg.INSTR, DisplayStyles.REG_INSTR_X_MP, 86, true), new RegisterProperties(Reg.ACCUM, DisplayStyles.CU_X_IO, 261, true), new RegisterProperties(Reg.STATE, DisplayStyles.REG_STATE_X, 330, true)}, new BusView[0]);
        this.add(this.mem = this.cmanager.getMicroMemory());
        this.regMIP = this.cmanager.getRegisterView(Reg.MIP);
        this.regMIP.setProperties(400, 1, false);
        this.add(this.regMIP);
        this.regMInstr = this.cmanager.getRegisterView(Reg.MINSTR);
        this.regMInstr.setProperties(400, 100, false);
        this.add(this.regMInstr);
        this.regBuf = this.cmanager.getRegisterView(Reg.BUF);
        this.regBuf.setProperties(DisplayStyles.REG_BUF_X_MP, 261, true);
        this.add(this.regBuf);
        this.regState = this.cmanager.getRegisterView(Reg.STATE);
        this.setSignalListeners(new SignalListener[]{new SignalListener(this.regState, new ControlSignal[]{ControlSignal.HALT, ControlSignal.BUF_TO_STATE_N, ControlSignal.BUF_TO_STATE_Z, ControlSignal.DISABLE_INTERRUPTS, ControlSignal.ENABLE_INTERRUPTS, ControlSignal.IO0_TSF, ControlSignal.IO1_TSF, ControlSignal.IO2_TSF, ControlSignal.IO3_TSF, ControlSignal.IO4_TSF, ControlSignal.IO5_TSF, ControlSignal.IO6_TSF, ControlSignal.IO7_TSF, ControlSignal.IO8_TSF, ControlSignal.IO9_TSF, ControlSignal.SET_RUN_STATE, ControlSignal.SET_PROGRAM, ControlSignal.SET_REQUEST_INTERRUPT}), new SignalListener(this.regBuf, new ControlSignal[]{ControlSignal.ALU_AND, ControlSignal.SHIFT_RIGHT, ControlSignal.SHIFT_LEFT})});
        this.cucheckbox = this.cmanager.getMPCheckBox();
        this.cucheckbox.setBounds(450, 400, 300, 30);
        this.add(this.cucheckbox);
        this.add(new ALUView(DisplayStyles.ALU_X_MP, 155, 181, 90));
    }

    public void panelActivate() {
        this.mem.updateLastAddr();
        this.mem.updateMemory();
        this.regMIP.setValue();
        this.regMInstr.setValue();
        this.regBuf.setValue();
        this.cucheckbox.setSelected(false);
        super.panelActivate();
    }

    public String getPanelName() {
        return "Работа с МПУ";
    }

    public void stepStart() {
        this.mem.eventRead();
    }

    public void stepFinish() {
        ArrayList<ControlSignal> signals = this.cmanager.getActiveSignals();
        this.regMIP.setValue();
        this.regMInstr.setValue();
    }
}

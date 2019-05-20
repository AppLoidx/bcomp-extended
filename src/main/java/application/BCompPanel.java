package application;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import ru.ifmo.cs.bcomp.ControlSignal;
import ru.ifmo.cs.bcomp.SignalListener;
import ru.ifmo.cs.bcomp.ui.components.ActivateblePanel;
import ru.ifmo.cs.bcomp.ui.components.BusView;
import ru.ifmo.cs.bcomp.ui.components.RegisterProperties;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BCompPanel extends ActivateblePanel {
    protected final ComponentManager cmanager;
    private final RegisterProperties[] regProps;
    private final BusView[] buses;
    private SignalListener[] listeners;

    public BCompPanel(ComponentManager cmanager, RegisterProperties[] regProps, BusView[] buses) {
        this.cmanager = cmanager;
        this.regProps = regProps;
        this.buses = buses;
    }

    protected void setSignalListeners(SignalListener[] listeners) {
        this.listeners = listeners;
    }

    protected SignalListener[] getSignalListeners() {
        return this.listeners;
    }

    public void drawBuses(Graphics g) {
        ArrayList<BusView> openbuses = new ArrayList<>();
        ArrayList<ControlSignal> signals = this.cmanager.getActiveSignals();
        BusView[] arr$ = this.buses;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            BusView bus = arr$[i$];
            ControlSignal[] arr = bus.getSignals();
            int len = arr.length;

            for(int i2 = 0; i2 < len; ++i2) {
                ControlSignal signal = arr[i2];
                if (signals.contains(signal)) {
                    openbuses.add(bus);
                }
            }

            bus.draw(g, DisplayStyles.COLOR_BUS);
        }

        Iterator i$ = openbuses.iterator();

        while(i$.hasNext()) {
            BusView bus = (BusView)i$.next();
            bus.draw(g, DisplayStyles.COLOR_ACTIVE);
        }

    }

    private void drawOpenBuses(Color color) {
        Graphics g = this.getGraphics();
        ArrayList<ControlSignal> signals = this.cmanager.getActiveSignals();
        BusView[] arr$ = this.buses;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            BusView bus = arr$[i$];
            ControlSignal[] arr = bus.getSignals();
            int len = arr.length;

            for(int i2 = 0; i2 < len; ++i2) {
                ControlSignal signal = arr[i2];
                if (signals.contains(signal)) {
                    bus.draw(g, color);
                }
            }
        }

    }

    public void stepStart() {
        this.drawOpenBuses(DisplayStyles.COLOR_BUS);
    }

    public void stepFinish() {
        this.drawOpenBuses(DisplayStyles.COLOR_ACTIVE);
    }

    public void panelActivate() {
        RegisterProperties[] arr$ = this.regProps;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            RegisterProperties prop = arr$[i$];
            RegisterView reg = this.cmanager.getRegisterView(prop.reg);

            reg.setProperties(prop.x, prop.y, prop.hex);
            reg.setBackground(Color.black);
            this.add(reg);
        }

        this.cmanager.panelActivate(this);
    }

    public void panelDeactivate() {
        this.cmanager.panelDeactivate();
    }

    public void paintComponent(Graphics g) {
//        if (Settings.getBackgroundImage() !=null)  g.drawImage(Settings.getBackgroundImage(), 0, 0, this);
//        g.setColor(new Color(30,39,45));
//        g.fillRect(0 ,0, this.getWidth(), this.getHeight());
        DisplayStyles.setGraphics(g, this);
        this.drawBuses(g);
    }
}

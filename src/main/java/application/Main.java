package application;

import fx.gui.EncodeApplication;
import ru.ifmo.cs.bcomp.MicroProgram;
import ru.ifmo.cs.bcomp.MicroPrograms;
import ru.ifmo.cs.bcomp.ui.CLI;
import ru.ifmo.cs.bcomp.ui.MPDecoder;


/**
 * From original bcomp edited by
 * @author Arthur Kupriyanov
 *
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String mpname;
        String app;
        try {
            mpname = System.getProperty("mp", "base");
            app = System.getProperty("mode", "extended");
        } catch (Exception var5) {
            mpname = "base";
            app = "gui";
        }

        MicroProgram mp = MicroPrograms.getMicroProgram(mpname);
        if (mp == null) {
            System.err.println("Invalid microprogram selected");
            System.exit(1);
        }


        switch (app) {
            case "gui":
                // original bcomp
                ru.ifmo.cs.bcomp.ui.GUI gui = new ru.ifmo.cs.bcomp.ui.GUI(mp);
                gui.gui();
                break;
            case "cli":
                CLI cli = new CLI(mp);
                cli.cli();
                break;
            case "decoder":
                MPDecoder mpdecoder = new MPDecoder(mp);
                mpdecoder.decode();
                break;
            case "nightmare":
                new ru.ifmo.cs.bcomp.ui.Nightmare(mp);
                break;
            case "encoder":
                //encoder run
                new EncodeApplication().run();
                break;
            case "extended":
                // extended version
                GUI extendedGUI = new GUI(mp);
                extendedGUI.gui();

                break;
            default:
                System.err.println("Invalid mode selected");
                break;
        }
    }
}

package nightmaretest;

import ru.ifmo.cs.bcomp.MicroProgram;
import ru.ifmo.cs.bcomp.MicroPrograms;
import ru.ifmo.cs.bcomp.ui.CLI;
import ru.ifmo.cs.bcomp.ui.MPDecoder;
import window.Console;


/**
 * From original bcomp edited by Arthur Kupriyanov
 *
 */
public class Nightmare {
    public static void main(String[] args) throws Exception {
        String mpname;
        String app;
        try {
            mpname = System.getProperty("mp", "base");
            app = System.getProperty("mode", "gui");
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
                GUI gui = new GUI(mp);
                gui.gui();
                break;
            case "cli":
                Console console = new Console();
                console.start();
                break;
            case "decoder":
                MPDecoder mpdecoder = new MPDecoder(mp);
                mpdecoder.decode();
                break;
            case "nightmare":
                new ru.ifmo.cs.bcomp.ui.Nightmare(mp);
                break;
            default:
                System.err.println("Invalid mode selected");
                break;
        }
    }
}

package application;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import ru.ifmo.cs.bcomp.Utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;

/**
 * From original bcomp edited by Arthur Kupriyanov
 *
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class DisplayStyles {
    private static final FontRenderContext fr = new FontRenderContext((AffineTransform)null, true, true);
    public static Font FONT_BUTTONS_PANEL_TEXT;
    public static final Font FONT_COURIER_BOLD_18 = new Font("Courier New", Font.BOLD, 18);
    public static final int FONT_COURIER_BOLD_18_WIDTH;
    public static final Font FONT_COURIER_BOLD_20;
    public static final Font FONT_COURIER_BOLD_21;
    public static final int FONT_COURIER_BOLD_21_WIDTH;
    public static final Font FONT_COURIER_BOLD_23;
    public static final int FONT_COURIER_BOLD_23_WIDTH;
    public static final Font FONT_COURIER_BOLD_25;
    public static final int FONT_COURIER_BOLD_25_WIDTH;
    public static final Font FONT_COURIER_BOLD_45;

    public static Color COLOR_TEXT;
    public static Color COLOR_ACTIVE_TEXT;
    public static Color COLOR_BORDER;

    public static Color COLOR_ACTIVE;
    public static Color COLOR_BUS;

    public static Color MAIN_TEXT_COLOR = Color.white;

    public static Color COLOR_INPUT_REGISTER_ACTIVE_BIT;

    public static final Color ERROR_COLOR = new Color(227, 35, 35, 95);
    public static final Color COLOR_TITLE;
    public static final Color COLOR_VALUE;
    public static Color COLOR_INPUT_TITLE;
    public static Color COLOR_INPUT_BODY;
    public static final Color COLOR_ACTIVE_INPUT;
    public static final String COLOR_ACTIVE_BIT = "<font color=\"#FF0000\">";
    public static final String COLOR_END = "</font>";
    public static final String HTML = "<html>";
    public static final String HTML_END = "</html>";
    public static final int PANE_WIDTH = 856;
    public static final int PANE_HEIGHT = 544;
    public static final Dimension PANE_SIZE;
    public static final int CELL_HEIGHT = 25;
    public static final int REG_HEIGHT = 53;
    private static final int REG_1_WIDTH;
    private static final int REG_3_WIDTH;
    private static final int REG_4_WIDTH;
    private static final int REG_5_WIDTH;
    public static final int REG_8_WIDTH;
    public static final int REG_9_WIDTH;
    private static final int REG_11_WIDTH;
    public static final int REG_16_WIDTH;
    private static final int REG_16_HALF;
    private static final int REG_HEIGHT_HALF = 26;
    public static final int BUS_WIDTH = 4;
    public static final int ELEMENT_DELIM = 16;
    private static final int ARROW = 13;
    public static final int BUTTONS_HEIGHT = 30;
    public static final int BUTTONS_SPACE = 2;
    public static final int BUTTONS_Y = 514;
    public static final int REG_KEY_X = 8;
    public static final int REG_KEY_Y = 445;
    public static final int ACTIVE_BIT_X;
    public static final int BUS_LEFT_INPUT_X1;
    public static final int BUS_KEY_ALU = 440;
    public static final int REG_C_X_BV;
    public static final int REG_ACCUM_X_BV;
    public static final int REG_ACCUM_Y_BV = 376;
    public static final int FROM_ALU_X;
    public static final int TO_ACCUM_Y = 362;
    public static final int FROM_ALU_Y1 = 355;
    public static final int FROM_ALU_Y = 339;
    public static final int ALU_WIDTH = 181;
    public static final int ALU_HEIGHT = 90;
    public static final int ALU_Y = 245;
    public static final int BUS_LEFT_INPUT_X;
    public static final int BUS_LEFT_INPUT_DOWN = 231;
    public static final int BUS_LEFT_INPUT_UP = 224;
    public static final int BUS_FROM_ACCUM_X;
    public static final int BUS_FROM_ACCUM_Y = 402;
    public static final int REG_IP_X_BV;
    public static final int REG_IP_Y_BV = 155;
    public static final int BUS_FROM_IP_X;
    public static final int BUS_FROM_IP_Y = 181;
    public static final int BUS_RIGHT_X1;
    public static final int BUS_RIGHT_X;
    public static final int REG_DATA_Y_BV = 86;
    public static final int BUS_FROM_DATA_Y = 143;
    public static final int BUS_TO_DATA_Y = 112;
    public static final int BUS_RIGHT_TO_X;
    public static final int REG_ADDR_Y_BV = 17;
    public static final int BUS_TO_ADDR_Y = 43;
    public static final int BUS_TO_ADDR_X;
    public static final int REG_INSTR_X_BV;
    public static final int BUS_TO_INSTR_X;
    public static final int BUS_TO_DATA_X;
    public static final int BUS_FROM_INSTR_Y = 74;
    public static final int BUS_FROM_INSTR_X;
    public static final int MEM_X = 1;
    public static final int MEM_Y = 1;
    private static final int MEM_WIDTH;
    public static final int BUS_ADDR_X1;
    public static final int BUS_ADDR_X2;
    public static final int BUS_READ_Y = 99;
    public static final int BUS_WRITE_Y = 125;
    public static final int BUS_READ_X1;
    public static final int BUS_READ_X2;
    public static final int CYCLEVIEW_Y = 245;
    public static final int BUS_INSTR_TO_CU_X;
    public static final int BUS_INSTR_TO_CU_Y = 231;
    private static final int REGS_RIGHT_X;
    public static final int CU_X_IO;
    public static final int REG_ACC_X_IO;
    public static final int CU_Y_IO = 17;
    public static final int REG_ADDR_Y_IO = 86;
    public static final int REG_IP_Y_IO = 155;
    public static final int REG_DATA_Y_IO = 224;
    public static final int REG_INSTR_Y_IO = 293;
    public static final int REG_ACCUM_Y_IO = 362;
    public static final int IO_DELIM;
    public static final int IO_X;
    public static final int FLAG_WIDTH = 100;
    public static final int FLAG_OFFSET;
    public static final int BUS_INTR_Y = 30;
    public static final int LABEL_INTR_Y = 6;
    public static final int BUS_INTR_LEFT_X;
    public static final int IO1_CENTER;
    public static final int IO2_CENTER;
    public static final int IO3_CENTER;
    public static final int BUS_INTR_Y1 = 46;
    public static final int FLAG_Y = 51;
    public static final int BUS_TSF_X1;
    public static final int BUS_TSF_Y2 = 80;
    public static final int BUS_TSF_Y = 96;
    public static final int BUS_TSF_Y1 = 82;
    public static final int BUS_TSF_X;
    public static final int LABEL_TSF_Y = 104;
    public static final int LABEL_ADDR_Y = 124;
    public static final int BUS_IO_ADDR_Y = 145;
    public static final int DECODER_Y = 166;
    public static final int BUS_IO_ADDR_Y1 = 152;
    public static final int BUS_IO_ADDR_Y2 = 237;
    public static final int BUS_IO_ADDR_X;
    private static final int DECODER_HEIGHT = 77;
    public static final int BUS_IO_REQ_Y = 263;
    public static final int BUS_IO_REQ_Y1 = 256;
    public static final int LABEL_REQ_Y = 271;
    public static final int LABEL_IN_Y = 287;
    public static final int BUS_IN_Y = 308;
    public static final int IO_DATA_Y = 328;
    public static final int BUS_IN_Y1 = 375;
    public static final int BUS_IN_Y2 = 323;
    public static final int BUS_IN_X;
    public static final int BUS_OUT_Y = 401;
    public static final int BUS_OUT_Y2 = 394;
    public static final int BUS_OUT_X;
    public static final int LABEL_OUT_Y = 409;
    private static final int MP_REGS_WIDTH;
    public static final int ALU_X_MP;
    public static final int REG_ACC_Y_MP = 261;
    public static final int REG_STATE_Y_MP = 330;
    private static final int REG_RIGHT_X_MP;
    public static final int REG_IP_X_MP;
    public static final int REG_INSTR_X_MP;
    public static final int REG_BUF_X_MP;
    public static final int REG_STATE_X;
    private static final int LEFT_X;
    public static final int MICROMEM_X;
    public static final int TEXTAREA_X = 1;
    public static final int TEXTAREA_Y = 1;
    public static final int TEXTAREA_WIDTH = 600;
    public static final int TEXTAREA_HEIGHT = 542;

    public static final int SCALED_BACKGROUND_IMG_WIDTH = 866;
    public static final int SCALED_BACKGROUND_IMG_HEIGHT = 554;

    // CUSTOM FIELDS
    public static Color COLOR_BACKGROUND_STYLE;

    public DisplayStyles() {
    }

    static {
        try {
            InputStream is = DisplayStyles.class.getClassLoader().getResourceAsStream("fonts/BS.ttf");
            Font bloggerSans = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(bloggerSans);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        FONT_COURIER_BOLD_18_WIDTH = (int)Math.round(FONT_COURIER_BOLD_18.getStringBounds("0", fr).getWidth());
        FONT_COURIER_BOLD_20 = new Font("Courier New", Font.BOLD, 20);
        FONT_COURIER_BOLD_21 = new Font("Blogger Sans", Font.PLAIN, 18);
        FONT_COURIER_BOLD_21_WIDTH = (int)Math.round(FONT_COURIER_BOLD_21.getStringBounds("0", fr).getWidth());
        FONT_COURIER_BOLD_23 = new Font("Courier New", Font.BOLD, 23);
        FONT_COURIER_BOLD_23_WIDTH = (int)Math.round(FONT_COURIER_BOLD_23.getStringBounds("0", fr).getWidth());
        FONT_COURIER_BOLD_25 = new Font("Courier New", Font.BOLD, 25);
        FONT_COURIER_BOLD_25_WIDTH = (int)Math.round(FONT_COURIER_BOLD_25.getStringBounds("0", fr).getWidth());
        FONT_COURIER_BOLD_45 = new Font("Arial Nova", Font.BOLD, 45);

        FONT_BUTTONS_PANEL_TEXT = new Font("Arial Nova", Font.BOLD, 12);
        COLOR_INPUT_REGISTER_ACTIVE_BIT = Color.RED;
        COLOR_TEXT = MAIN_TEXT_COLOR;
        COLOR_BORDER = new Color(8,141,165);
        COLOR_ACTIVE_TEXT = Color.RED;

        COLOR_ACTIVE = Color.RED;
        COLOR_BUS = Color.WHITE;

        COLOR_TITLE = new Color(157, 189, 165);
        COLOR_VALUE = new Color(255,0,77);  // цвет активных foreground кнопок
        COLOR_INPUT_TITLE = new Color(Integer.valueOf("29434e", 16)); // цвет регистров - фон
        COLOR_INPUT_BODY = new Color(Integer.valueOf("37464f", 16)); // цвет содержимого регистров - фон
        COLOR_ACTIVE_INPUT = new Color(192, 0, 0);

        // CUSTOM

        COLOR_BACKGROUND_STYLE = new Color(Integer.valueOf("62717b", 16));


        // END CUSTOM

        PANE_SIZE = new Dimension(856, 544);
        REG_1_WIDTH = 2 * FONT_COURIER_BOLD_25_WIDTH + 2;
        REG_3_WIDTH = 4 * FONT_COURIER_BOLD_25_WIDTH + 2;
        REG_4_WIDTH = 5 * FONT_COURIER_BOLD_25_WIDTH + 2;
        REG_5_WIDTH = 6 * FONT_COURIER_BOLD_25_WIDTH + 2;
        REG_8_WIDTH = (Utils.getBinaryWidth(8) + 1) * FONT_COURIER_BOLD_25_WIDTH + 2;
        REG_9_WIDTH = (Utils.getBinaryWidth(9) + 1) * FONT_COURIER_BOLD_25_WIDTH + 2;
        REG_11_WIDTH = (Utils.getBinaryWidth(11) + 1) * FONT_COURIER_BOLD_25_WIDTH + 2;
        REG_16_WIDTH = (Utils.getBinaryWidth(16) + 1) * FONT_COURIER_BOLD_25_WIDTH + 2;
        REG_16_HALF = REG_16_WIDTH / 2;
        ACTIVE_BIT_X = 8 + REG_16_WIDTH + 16;
        BUS_LEFT_INPUT_X1 = 8 + REG_16_HALF;
        REG_C_X_BV = BUS_LEFT_INPUT_X1 + 20 + 1;
        REG_ACCUM_X_BV = REG_C_X_BV + REG_1_WIDTH - 1;
        FROM_ALU_X = REG_ACCUM_X_BV + 61;
        BUS_LEFT_INPUT_X = BUS_LEFT_INPUT_X1 + 60 + 1;
        BUS_FROM_ACCUM_X = REG_C_X_BV - 4 - 1;
        REG_IP_X_BV = REG_ACCUM_X_BV + 6 * FONT_COURIER_BOLD_25_WIDTH;
        BUS_FROM_IP_X = REG_IP_X_BV - 4 - 1;
        BUS_RIGHT_X1 = BUS_FROM_IP_X - 16;
        BUS_RIGHT_X = REG_C_X_BV + 181 - 45 + 2;
        BUS_RIGHT_TO_X = REG_ACCUM_X_BV + REG_16_WIDTH + 16 + 4;
        BUS_TO_ADDR_X = REG_ACCUM_X_BV + REG_11_WIDTH + 13;
        REG_INSTR_X_BV = BUS_RIGHT_TO_X + 4 + 16 + 1;
        BUS_TO_INSTR_X = REG_INSTR_X_BV - 13 - 1;
        BUS_TO_DATA_X = REG_ACCUM_X_BV + REG_16_WIDTH + 13;
        BUS_FROM_INSTR_X = REG_INSTR_X_BV + 32 + 4;
        MEM_WIDTH = 9 * FONT_COURIER_BOLD_25_WIDTH + 3;
        BUS_ADDR_X1 = REG_ACCUM_X_BV - 4 - 1;
        BUS_ADDR_X2 = 1 + MEM_WIDTH + 13;
        BUS_READ_X1 = REG_ACCUM_X_BV - 13 - 1;
        BUS_READ_X2 = 1 + MEM_WIDTH + 4;
        BUS_INSTR_TO_CU_X = REG_INSTR_X_BV + REG_16_HALF;
        REGS_RIGHT_X = 1 + MEM_WIDTH + REG_8_WIDTH + 16 - 1;
        CU_X_IO = REGS_RIGHT_X - REG_8_WIDTH;
        REG_ACC_X_IO = CU_X_IO + REG_1_WIDTH - 1;
        IO_DELIM = REG_8_WIDTH + 16;
        IO_X = REG_INSTR_X_BV + REG_16_WIDTH - REG_8_WIDTH - 2 * IO_DELIM;
        FLAG_OFFSET = (REG_8_WIDTH - 100) / 2;
        BUS_INTR_LEFT_X = REGS_RIGHT_X + 13;
        IO1_CENTER = IO_X + REG_8_WIDTH / 2;
        IO2_CENTER = IO1_CENTER + IO_DELIM;
        IO3_CENTER = IO2_CENTER + IO_DELIM;
        BUS_TSF_X1 = IO_X - 16 - 4 - 1 + FLAG_OFFSET;
        BUS_TSF_X = CU_X_IO + REG_8_WIDTH - 16 - 1;
        BUS_IO_ADDR_X = CU_X_IO + REG_4_WIDTH + 4;
        BUS_IN_X = REG_ACC_X_IO + REG_4_WIDTH + 13;
        BUS_OUT_X = REG_ACC_X_IO + REG_4_WIDTH + 4;
        MP_REGS_WIDTH = REG_4_WIDTH + 16 + REG_5_WIDTH;
        ALU_X_MP = CU_X_IO + (MP_REGS_WIDTH - 181) / 2;
        REG_RIGHT_X_MP = CU_X_IO + MP_REGS_WIDTH;
        REG_IP_X_MP = REG_RIGHT_X_MP - REG_3_WIDTH;
        REG_INSTR_X_MP = REG_RIGHT_X_MP - REG_4_WIDTH;
        REG_BUF_X_MP = REG_RIGHT_X_MP - REG_5_WIDTH;
        REG_STATE_X = CU_X_IO + (MP_REGS_WIDTH - REG_9_WIDTH) / 2;
        LEFT_X = IO_X + 3 * IO_DELIM - 1;
        MICROMEM_X = LEFT_X - MEM_WIDTH;

    }

    public static void setGraphics(Graphics g, Component parent){
        if (Settings.getBackgroundColor()!=null) {
            g.setColor(Settings.getBackgroundColor());
            g.fillRect(0, 0, parent.getWidth(), parent.getHeight());
        }
        if (Settings.getBackgroundImage() !=null)  g.drawImage(Settings.getBackgroundImage(), 0, 0, parent);
    }
}

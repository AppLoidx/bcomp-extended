package encode;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author Arthur Kupriyanov
 */
public class HEXConverter {


    public static String getUTF16HexCode(String context){
        byte[] bytes = context.getBytes(StandardCharsets.UTF_16);
        return getHEX(bytes).substring(6);  // excluding marker
    }
    public static String getKOI8RHexCode(String context){
        try {
            byte[] bytes = context.getBytes("KOI8");
            return getHEX(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getASCIIHexCode(String context){
        byte[] bytes = context.getBytes(StandardCharsets.US_ASCII);
        return getHEX(bytes);
    }

    public static String getISO8859_5HexCode(String context){
        try {
            byte[] bytes = context.getBytes("ISO-8859-5");
            return getHEX(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getWindows1251HexCode(String context){
        try {
            byte[] bytes = context.getBytes("WINDOWS-1251");
            return getHEX(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getUTF8HexCode(String context){
        byte[] bytes = context.getBytes(StandardCharsets.UTF_8);
        return getHEX(bytes);

    }

    private static String getHEX(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes){
            sb.append(UnicodeFormatter.byteToHex(b)).append(" ");
        }

        return sb.toString();
    }

}

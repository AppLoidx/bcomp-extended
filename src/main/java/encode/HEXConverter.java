package encode;

/**
 * @author Arthur Kupriyanov
 */
public class HEXConverter {
    public static void main(String[] args) {
        char[] chars = "Заяц".toCharArray();

        StringBuffer hex = new StringBuffer();
        for (char aChar : chars) {
            hex.append(Integer.toHexString((int) aChar)).append(" ");
        }

        System.out.println(hex.toString());
    }
}

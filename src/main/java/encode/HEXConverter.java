package encode;

import java.util.Scanner;

/**
 * @author Arthur Kupriyanov
 */
public class HEXConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String line;
        while(!(line=sc.nextLine()).equals("exit")){
            String newLine = line
                    .replace("п", "D0 ")
                    .replace("о", "CF ")
                    .replace("м", "CD ")
                    .replace("щ", "DD ")
                    .replace("ь","D8 ")
                    .replace("ю", "C0 ")
                    .replace("д", "C4 ")
                    .replace("р","D2 ")
                    .replace("а", "C1 ")
                    .replace("г", "C7 ")
                    .replace("ы", "D9")
                    .replace(" ", "9A");


            System.out.println(newLine);
        }
//        WORD D0CF
//        WORD CDCF
//        WORD DDD8
//        WORD C09A
//        WORD D0CF
//        WORD C4D0
//        WORD D2CF
//        WORD C7D2
//        WORD C1CD
//        WORD CDD9

    }
}

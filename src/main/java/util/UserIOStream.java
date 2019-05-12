package util;


/**
 * @author Arthur Kupriyanov
 */
public class UserIOStream {
    private boolean available = false;
    private String userInput;

    public boolean available(){
        return available;
    }

    public String readString(){
        if (available){
            available = false;
            String response = userInput;
            userInput = null;
            return response;
        }

        return null;
    }

    public void writeln(String str){
        if (userInput!=null) userInput += str + "\n";
        else userInput = str;
        available = true;
    }
}

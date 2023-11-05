package test;

public class Assert {

    public static String getMessage() {
        return message;
    }

    static String message = "";

    static  public <T> int Check(T shouldBeValue, T isValue){
        if (shouldBeValue.equals(isValue)) {
            message = colorOK("OK");
            return 0;
        }else{
            message =  colorFAIL("FAIL: Value is <" + isValue + "> instead of <" + shouldBeValue + ">");
            return 1;
        }
    }

    static  String colorOK (String message){
        return ConsoleColors.GREEN + message + ConsoleColors.RESET;
    }

    static  String colorFAIL (String message){
        return ConsoleColors.RED + message + ConsoleColors.RESET;
    }
}

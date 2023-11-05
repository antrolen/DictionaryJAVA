package ui;

public class UnknownCommand extends Exception{
    public UnknownCommand(String message) {
        super( "Unknown command:" + message);
    }
}

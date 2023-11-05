package ui;

public enum Command {
    ADD("add"),
    DELETE("delete"),
    SEARCH("search"),
    TRANSLATE("trans"),
    PRINT("print"),
    CLEAR("clear"),
    HELP("help"),
    EXIT("exit"),
    TEST("test"),
    ;

    String value;

    Command(String command) {
        value = command;
    }

    static Command getCommand(String command) throws UnknownCommand {
        if(command.equals(ADD.value)){
            return ADD;
        } else if (command.equals(DELETE.value)) {
            return DELETE;
        }else if (command.equals(SEARCH.value)) {
            return SEARCH;
        }else if (command.equals(TRANSLATE.value)) {
            return TRANSLATE;
        }else if (command.equals(PRINT.value)) {
            return PRINT;
        }else if (command.equals(CLEAR.value)) {
            return CLEAR;
        }else if (command.equals(HELP.value)) {
            return HELP;
        }else if (command.equals(EXIT.value)) {
            return EXIT;
        }else if (command.equals(TEST.value)) {
            return TEST;
        }else{
            throw new UnknownCommand(command);
        }
    }


}

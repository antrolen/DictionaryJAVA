package ui;

import dictionary.Dictionary;
import dictionary.Language;
import jdk.jshell.spi.ExecutionControl;
import test.DictionaryTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DictionaryUi {
    // ********************************************
    private Dictionary dictionary = new Dictionary();
    private String format = "%-5s | %-15s | %s";
    private String delimiter;
    public DictionaryUi() {
        delimiter = String.join("", Collections.nCopies(25, "-"));

        dictionary.addLanguage("en");
        dictionary.addLanguageWordTranslation("en", "car", new String[]{"mashina"});
        dictionary.addLanguageWordTranslation("en", "car", new String[]{"voiture"});
        dictionary.addLanguageWordTranslation("en", "book", new String[]{"kniga"});
        dictionary.addLanguageWordTranslation("en", "back", new String[]{"spina"});
        dictionary.addLanguageWordTranslation("en", "back", new String[]{"dos"});
        dictionary.addLanguageWordTranslation("ru", "mashina", new String[]{"car", "voiture"});
    }

    // ********************************************




     public void printHelp() {
        StringBuilder hlp = new StringBuilder(
        "-------------------------------------------------------------------------------------------------------\n" +
                "|                          Command                          |               Description               |\n" +
                "-------------------------------------------------------------------------------------------------------\n" +
                "| add <language>                                            | Add a language                          |\n" +
                "| add <language> <word> <translation>                       | Add one translation for a word          |\n" +
                "| add <language> <word> <translation1>, <translation2>, ... | Add translations for a word             |\n" +
                "-------------------------------------------------------------------------------------------------------\n" +
                "| delete <language>                                         | Delete all words in a specific language |\n" +
                "| delete <language> <word>                                  | Delete a specific word                  |\n" +
                "-------------------------------------------------------------------------------------------------------\n" +
                "| print                                                     | Print the whole dicitionary             |\n" +
                "| print -D                                                  | Print the whole dicitionary             |\n" +
                "| print -L                                                  | Print the list of languages             |\n" +
                "| print <language> <word>                                   | Print translations for a specific word  |\n" +
                "-------------------------------------------------------------------------------------------------------\n" +
                "| search <language> <mask>                                  | Print words which are like a mask       |\n" +
                "-------------------------------------------------------------------------------------------------------\n" +
                "| trans <language> <word>                                   | Print translations for a specific word  |\n" +
                "-------------------------------------------------------------------------------------------------------\n" +
                "| clear                                                     | Clear the dictionary                    |\n" +
                "-------------------------------------------------------------------------------------------------------"


        );
        System.out.println(hlp);

    }

     public void startLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("cmd:");
            String command = scanner.nextLine();
            try {
                analizeCommand(command);
            } catch (EmptyCommand e) {
                System.out.println(e.getMessage());
                printHelp();
            } catch (UnknownCommand e) {
                System.out.println(e.getMessage());
                printHelp();
            } catch (Exception e){
                System.out.println(e.getMessage());
                printHelp();
            }

        }
    }

     public void analizeCommand(String command) throws EmptyCommand, UnknownCommand {
        command = command.trim();
        if (command == null || command == "") {
            throw new EmptyCommand();
        }

        String commandWord = command.split(" ", 0)[0];
        Command cmd = Command.getCommand(commandWord);
        switch (cmd) {
            case EXIT -> System.exit(0);
            case HELP -> {printHelp(); return ;}
            case CLEAR -> {clearDictionary(); return ;}
            case TEST -> {
                DictionaryTest.staticStartAll(); System.exit(0); }
        }

        String[] params = null;
        try{
            params = command.substring(commandWord.length() + 1).split(" ", 0);
        }catch (Exception e){
        }

        switch (cmd) {
            case SEARCH -> searchCmd(params);
            case TRANSLATE -> translateCmd(params);
            case PRINT -> printCmd(params);
            case ADD -> addCmd(params);
            case DELETE -> deleteCmd(params);
        }
    }

    // -----------------------------------------------------
    void translateCmd(String[] params){
        if(params.length > 1) {
            printWord(params[0], params[1]);
        }
    }

    public void translateWord(String lang, String word) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("");
    }

    // -----------------------------------------------------
    void searchCmd(String[] params){
        if(params.length > 1) {
            printSerchResult(params[0], params[1]);
        }
    }
    public void searchWord(String lang, String mask) {
        dictionary.search(lang, mask);
    }

    // -----------------------------------------------------

    private void deleteCmd(String[] params) {
        if (params.length == 1) {
            deleteLanguage(params[0]);
        }else if(params.length > 1){
            deleteWord(params[0], params[1]);
        }
    }


    public void deleteLanguage(String lang) {
        dictionary.deleteLanguage(lang);
    }

    public void deleteWord(String lang, String word) {
        dictionary.deleteWordInSourceLanguage(lang, word);
    }


    // -----------------------------------------------------
    private void addCmd(String[] params) {
        if (params.length == 1) {
            addLanguage(params[0]);
        } else if (params.length > 2) {
            String[] trans = new String[params.length - 2];
            System.arraycopy(params, 2, trans, 0, params.length - 2);
            addWordTranslation(params[0], params[1], trans);
        }
    }

    public void addLanguage(String lang) {
        dictionary.addLanguage(lang);
    }

    public void addWordTranslation(String lang, String word, String[] translations) {
        dictionary.addLanguageWordTranslation(lang, word, translations);
    }
    // -----------------------------------------------------
    private void printCmd(String[] params) {

        if (params== null || params.length == 0 || (params.length > 0 && params[0].equals("-D"))) {
            printDictionary();
        } else if (params.length > 0 && params[0].equals("-L")) {
            printLanguages();
        } else if (params.length > 1) {
            printWord(params[0], params[1]);
        }
    }
    private void printHeader(){
        System.out.println(delimiter);
        System.out.println(String.format(format, "LANG", "WORD", "TRANSLATIONS"));
        System.out.println(delimiter);
    }

    public void printLanguages() {
        System.out.println("Languges: ");
        for (Language language : dictionary.getSourceLanguages().stream().collect(Collectors.toList())) {
            System.out.println(language.getName());
        }
    }

    public void printDictionary() {
        System.out.println("Dictionary: ");
        printHeader();
        for(Language l : dictionary.getSourceLanguages()){
            System.out.println(String.format(format, l.getName(), "", ""));
            try {
                for (String word : dictionary.getWordsBySourceLanguage(l.getName())) {
                    List<String> trans = dictionary.getTranslationsByWordAndSourceLanguage(l.getName(), word);
                    String translations = String.join(", ", trans);
                    System.out.println(String.format(format, "", word, translations));

                }
                System.out.println(delimiter);
            }catch (Exception e){}
        }

    }

    public void printWord(String lang, String word)  {
        System.out.println("Word: ");
        printHeader();
        try {
            List<String> trans = dictionary.getTranslationsByWordAndSourceLanguage(lang, word);
            String translations = String.join(", ", trans);
            System.out.println(String.format(format, lang, word, translations));

            System.out.println(delimiter);
        }catch (Exception e){}
    }

    public void printSerchResult(String lang, String mask)  {
        System.out.println("Search Results: ");
        printHeader();
        try {
            List<String> results = dictionary.search(lang, mask);
            for(var result: results){
                System.out.println(String.format(format, lang, result, "use <trans> command"));
            }
            System.out.println(delimiter);
        }catch (Exception e){}
    }
    // ---------------------------------------------

    public void clearDictionary() {
        dictionary = new Dictionary();
    }

    // -----------------------------------------------------

}

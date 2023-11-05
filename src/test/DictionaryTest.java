package test;

import dictionary.Dictionary;
import ui.DictionaryUi;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class DictionaryTest {//extends Testable{

    Dictionary dict;
    public DictionaryTest() {
        dict = new Dictionary();
    }

    static public void staticStartAll(){
        Class<DictionaryTest> testsClass = DictionaryTest.class;
        int failed = 0;
        int pass = 0;
        for(Method method: testsClass.getDeclaredMethods()){
            TestToRun annotation = method.getDeclaredAnnotation(TestToRun.class);
            if(annotation != null){
                try{
                    Assert.message = "";
                    Object o = method.invoke(testsClass.getDeclaredConstructors()[0].newInstance());
                    failed += ((Integer) (o)).intValue();

                    System.out.println(method.getName() + ": " + Assert.getMessage());
                }
                catch (Throwable ex){
                    System.out.println(ex.getCause());
                }
                pass++;
            }
        }

        pass -= failed;

        String line = String.join("", Collections.nCopies(28, "-"));

        String format = "%-6s %4s | %-6s %4s |";

        System.out.println(line);
        System.out.println(String.format(format, "PASS", pass, "FAIL", failed));
        System.out.println(line);

    }

    @TestToRun
    public int one_language_has_been_added_into_dictionary(){
        dict = new Dictionary();
        dict.addLanguage("en");

        int res = dict.getSourceLanguages().stream().collect(Collectors.toList()).size();
        return Assert.Check(1, res);
    }

    @TestToRun
    public int tree_languages_have_been_added_into_dictionary(){
        dict = new Dictionary();
        dict.addLanguage("en");
        dict.addLanguage("ru");
        dict.addLanguage("fr");
        int res = dict.getSourceLanguages().stream().collect(Collectors.toList()).size();
        return Assert.Check(3, res);
    }

    @TestToRun
    public int one_language_of_three_has_been_deleted_from_dictionary(){
        dict = new Dictionary();
        dict.addLanguage("en");
        dict.addLanguage("ru");
        dict.addLanguage("fr");
        dict.deleteLanguage("ru");

        int res = dict.getSourceLanguages()
                .stream().filter(o->o.getName() == "ru")
                .collect(Collectors.toList()).size();
        return Assert.Check(0, res);
    }

    @TestToRun
    public int languages_set_is_case_insensitive(){
        dict = new Dictionary();
        dict.addLanguage("en");
        dict.addLanguage("En");

        return Assert.Check(1, dict.getSourceLanguages().size());
    }

}

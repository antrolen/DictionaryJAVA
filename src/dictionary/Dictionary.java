package dictionary;

import java.util.*;
import java.util.stream.Collectors;

public class Dictionary {

//    Map<Language,Map<Word, Translation>> dict = new TreeMap<>();
    Map<Language,Map<String, Set<String>>> dict;

//    Map<Lang,
//            Map<Word,
//                    Map<Lang,
//                            Word[]
//                            >
//                    >
//
//            >


    public Dictionary() {
        dict = new TreeMap<>();
    }
    private Map<String, Set<String>> getWordsBySourceLanguage(Language language){
        return dict.get(language);
    }

    public ArrayList<String> getWordsBySourceLanguage(String language){
        ArrayList<String> list = new ArrayList<>();
        dict.get(new Language(language)).keySet().forEach(word->list.add(word));
        return list;
    }

    public List<String> getTranslationsByWordAndSourceLanguage(String language, String word){
        List<String> list = new ArrayList<>();
        list.addAll(dict.get(new Language(language)).get(word));
        return list;
    }
    public ArrayList<Language> getSourceLanguages(){
        ArrayList<Language> result = new ArrayList<>();
        dict.keySet().forEach(language -> result.add(language.clone()));
        return result;

    }

    public void addLanguage(String name){
        Language lang = new Language(name);
        dict.putIfAbsent(lang, null);
    }
    public void deleteLanguage(String name){
            dict.remove(new Language(name));
    }





    public void addLanguageWordTranslation(String languageName, String word, String[] translations){
        Language language = new Language(languageName);
        var wordsTranslations = getWordsBySourceLanguage(language);

        if(wordsTranslations == null){
            wordsTranslations = new TreeMap<>();
            dict.put(language, wordsTranslations);
        }

        String w = word.toLowerCase();

        var translationsSet = wordsTranslations.get(w);
        if(translationsSet == null){
            translationsSet = new HashSet<>();
            wordsTranslations.put(w, translationsSet);
        }
        ArrayList<String> ts = new ArrayList<>();
        Arrays.stream(translations).toList().forEach(t->ts.add(t.toLowerCase()));

        translationsSet.addAll(ts);

    }

    public void deleteWordInSourceLanguage(String languageName, String word){
        Language language = new Language(languageName);
        var wordsTranslations = getWordsBySourceLanguage(language);
        try {
            wordsTranslations.remove(word);
        }catch (Exception e){}

    }

    public ArrayList<String> search(String languageName, String mask){
        ArrayList list  = new ArrayList();

        /*var wordsTranslations = */
        getWordsBySourceLanguage(languageName).stream().filter(w->{
            return w.contains(mask);
        }).collect(Collectors.toList()).forEach(w->list.add(w));

        return list;
    }

}

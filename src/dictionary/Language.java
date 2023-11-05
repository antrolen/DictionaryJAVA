package dictionary;

import java.util.Objects;

public class Language implements Cloneable, Comparable<Language>{
    public Language(String name) {
        this.name = name.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    String name;


    @Override
    public Language clone() {
        try {
            Language clone = (Language) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            clone.name = new String(name);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int compareTo(Language o) {
        int result = this.name.toLowerCase().compareTo(o.name.toLowerCase());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(name, language.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

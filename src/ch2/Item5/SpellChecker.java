package ch2.Item5;

public class SpellChecker{
    private Language dictionary;

    private SpellChecker(Language language) {
        this.dictionary = language;
    }

    public static SpellChecker initSpellChecker(String language) {
        return switch (language) {
            case "한국어" -> new SpellChecker(new Korean());
            case "영어" -> new SpellChecker(new English());
            case "프랑스어" -> new SpellChecker(new French());
            case "일본어" -> new SpellChecker(new Japanese());
            default -> null;
        };
    }

    public void isValid() {
        dictionary.info();
    }

    public void suggestions(){
        dictionary.info();
    }
}


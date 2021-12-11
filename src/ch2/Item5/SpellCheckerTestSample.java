package ch2.Item5;

public class SpellCheckerTestSample {
    public static void main(String[] args) {
        SpellChecker spellChecker1 = SpellChecker.initSpellChecker("한국어");
        SpellChecker spellChecker2 = SpellChecker.initSpellChecker("영어");
        SpellChecker spellChecker3 = SpellChecker.initSpellChecker("일본어");
        SpellChecker spellChecker4 = SpellChecker.initSpellChecker("프랑스어");

        spellChecker1.isValid();
        spellChecker2.isValid();
        spellChecker3.isValid();
        spellChecker4.isValid();
    }
}

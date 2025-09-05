package src.main.java.com.serumyouneed.wheel_of_fortune_20.utils;

public enum Letters {
    VOWELS("AEIOU"),
    CONSONANTS("BCDFGHJKLMNPQRSTVWXYZ");

    private final String letters;

    Letters(String letters) {
        this.letters = letters;
    }

    public String getLetters() {
        return letters;
    }


}


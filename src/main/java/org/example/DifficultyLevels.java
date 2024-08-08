package org.example;

public enum DifficultyLevels {

    FIRST(7,8),
    SECOND(6, 6),
    THIRD(5, 5);

    private final int secretWordLength;
    private final int errorsNumber;

    DifficultyLevels(int secretWordLength, int errorsNumber) {
        this.secretWordLength = secretWordLength;
        this.errorsNumber = errorsNumber;
    }
    public int getSecretWordLength() {return this.secretWordLength;}
    public int getErrorsNumber() {return this.errorsNumber;}
}

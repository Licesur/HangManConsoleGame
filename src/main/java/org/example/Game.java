package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Game {

    public String foundSymbols = "[^";
    private boolean isPlayingGame;
    private int playerLives;
    private String secretWord;
    private String secretWordMask;
    private String errorsVault;

    public static void start(int difficultyLevelFromUser) {
        Game game = new Game(difficultyLevelFromUser);
        while (game.getIsPlayingGame()) {
            game.turn();
        }
    }

    public void progressView() {
        System.out.println("Вам задагадо слово из " + this.getSecretWord().length() + " букв");
        if (!this.getErrorsVault().isEmpty()) {
            System.out.println("Вы совершили эти ошибки:");
            System.out.println(this.getErrorsVault());
        }

        System.out.println("Вы угадали:" + this.getSecretWordMask());
    }
    public void turn() {
        while(this.isPlayingGame) {
            terminalFlush();
            this.progressView();
            printHangMan(this.getPlayerLives());
            String symbol = this.getSymbolFromUser(new Scanner(System.in)).toLowerCase();
            this.isContainsInWord(symbol);
            this.finishCheck();
        }

    }

    public void finishCheck(){
        if (this.getSecretWord().equals(this.getSecretWordMask())) {
            System.out.println("Вы победили! Отгаданное слово: " + this.getSecretWordMask());
            this.setPlayingGame(false);
        }
        if (this.getPlayerLives() == 0) {
            String answer = this.getSecretWord();
            System.out.println("Вы проиграли. Загаданное слово: \"" + answer + "\" Вы отгадали: " + this.getSecretWordMask());
            this.setPlayingGame(false);
        }
    }

    public String getSymbolFromUser(Scanner scanner) {
        System.out.println("Введите букву");
        String symbol = scanner.nextLine();
        while(this.validateRepeat(symbol) || !validateSymbol(symbol)){
            if (this.validateRepeat(symbol)){
                System.out.println("Вы уже вводили данный символ, пожалуйста введите новую догадку");
                symbol = scanner.nextLine();
            } else if (!validateSymbol(symbol)) {
                System.out.println("Некорректный ввод, пожалуйста попробуйте еще:");
                symbol = scanner.nextLine();
            }
        }
        return symbol;
    }

    public Boolean validateRepeat(String symbol){
        return (this.getErrorsVault().contains(symbol) || this.getSecretWordMask().contains(symbol));
    }

    public static Boolean validateSymbol(String symbol) {
        return (symbol.matches("[А-яёЁ]"));
    }

    public static void printHangMan(int errorsNumber) {
        switch (errorsNumber) {
            case 0:
                System.out.println(Hangs.SEVENTHHANG.getHang());
                break;
            case 1:
                System.out.println(Hangs.SIXTHHANG.getHang());
                break;
            case 2:
                System.out.println(Hangs.FIFTHHANG.getHang());
                break;
            case 3:
                System.out.println(Hangs.FOURTHHANG.getHang());
                break;
            case 4:
                System.out.println(Hangs.THIRDHANG.getHang());
                break;
            case 5:
                System.out.println(Hangs.SECONDHANG.getHang());
                break;
            case 6:
                System.out.println(Hangs.FIRSTHANG.getHang());
                break;
            case 7:
                System.out.println(Hangs.HARDSECOND.getHang());
                break;
            case 8:
                System.out.println(Hangs.HARDTHIRD.getHang());

                break;
        }

        System.out.println("У вас осталось: " + errorsNumber + " ошибки");
    }

    public void isContainsInWord(String symbol) {
        if (this.getSecretWord().contains(symbol)) {
            foundSymbols = foundSymbols + symbol;
            this.setSecretWordMask(this.getSecretWord().replaceAll(this.foundSymbols + "]", "*"));
        } else {
            System.out.println("Такого символа нет");
            this.setErrorsVault(this.getErrorsVault() + " " + symbol);
            this.setPlayerLives(this.getPlayerLives() - 1);
        }
    }

    public boolean getIsPlayingGame() {
        return isPlayingGame;
    }

    public void setPlayingGame(boolean playingGame) {
        isPlayingGame = playingGame;
    }

    public int getPlayerLives() {
        return this.playerLives;
    }

    public void setPlayerLives(int playerLives) {
        this.playerLives = playerLives;
    }

    public String getSecretWord() {
        return this.secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public String getSecretWordMask() {
        return this.secretWordMask;
    }

    public void setSecretWordMask(String secretWordMask) {
        this.secretWordMask = secretWordMask;
    }

    public String getErrorsVault() {
        return this.errorsVault;
    }

    public void setErrorsVault(String errorsVault) {
        this.errorsVault = errorsVault;
    }

    private Game(int difficultyLevel) {
        int secretWordLength;
        int errorsNumber;
        switch (difficultyLevel){
            case 1:
                secretWordLength = DifficultyLevels.FIRST.getSecretWordLength();
                errorsNumber = DifficultyLevels.FIRST.getErrorsNumber();
                break;
            case 2:
                secretWordLength = DifficultyLevels.SECOND.getSecretWordLength();
                errorsNumber = DifficultyLevels.SECOND.getErrorsNumber();
                break;
            case 3:
                secretWordLength = DifficultyLevels.SECOND.getSecretWordLength();
                errorsNumber = DifficultyLevels.SECOND.getErrorsNumber();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + difficultyLevel);
        }
        this.setErrorsVault("");
        this.setSecretWord(generateSecretWord(secretWordLength));
        this.setSecretWordMask(this.getSecretWord().replaceAll("[А-яёЁ]", "*"));
        this.setPlayingGame(true);
        this.setPlayerLives(errorsNumber);
        printHangMan(this.getPlayerLives());
    }
    public static String generateSecretWord(int worldLenth) {
        Random rand = new Random();
        try {
            List<String> dict = Files.readAllLines(Paths.get("src","main","resources", "singular.txt"))
                    .stream().filter((s) -> s.length() == worldLenth).toList();
            return dict.get(rand.nextInt(dict.size()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void terminalFlush(){
        for (int i = 0; i < 10; i++) {
            System.out.println("\n");
        }
    }
}

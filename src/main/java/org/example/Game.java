package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Game {
    public boolean statusOfGame;
    private int errorsNumber;
    private String word;
    private String foundWord;
    private String errors;

    public static void start() {
        Game game = new Game();
        game.statusOfGame = true;
        game.setErrorsNumber(6);
        printHangMan(game.getErrorsNumber());
        game.turn();
    }

    public void turn() {
        while(this.statusOfGame) {
            this.gameStatus();
            System.out.println("Введите букву");
            String symbol = (new Scanner(System.in)).nextLine();
            symbol = this.validateError(validateSymbol(symbol));
            this.isContainsInWord(symbol);
            if (this.getWord().equals(this.getFoundWord())) {
                System.out.println("Вы победили! Отгаданное слово: " + this.getFoundWord());
                this.statusOfGame = false;
            }

            if (this.getErrorsNumber() == 0) {
                String answer = this.getWord();
                System.out.println("Вы проиграли. Загаданное слово: \"" + answer + "\" Вы отгадали: " + this.getFoundWord());
                this.statusOfGame = false;
            }
        }

    }

    public static String validateSymbol(String symbol) {
        if (symbol.matches("[А-яёЁ]{1}")) {
            return symbol.toLowerCase();
        } else {
            System.out.println("Некорректный ввод, пожалуйста попробуйте еще:");
            symbol = (new Scanner(System.in)).nextLine();
            return validateSymbol(symbol).toLowerCase();
        }
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
        }

        System.out.println("У вас осталось: " + errorsNumber + " ошибки");
    }

    public void isContainsInWord(String symbol) {
        String foundWord;
        if (this.getWord().contains(symbol)) {
            int i = 0;
            char[] foundWordArr = this.getFoundWord().toCharArray();
            String[] symbols = this.getWord().split(symbol);


            for (String el : symbols) {
                i += el.length();
                if (i < this.getFoundWord().length()) {
                    foundWordArr[i] = symbol.charAt(0);
                    ++i;
                }
            }

            this.setFoundWord("");
            for (char foundSymbol : foundWordArr) {
                foundWord = this.getFoundWord();
                this.setFoundWord(foundWord + foundSymbol);
            }
        } else {
            System.out.println("Такого символа нет");
            foundWord = this.getErrors();
            this.setErrors(foundWord + " " + symbol);
            this.setErrorsNumber(this.getErrorsNumber() - 1);
            printHangMan(this.getErrorsNumber());
        }

    }

    public String validateError(String symbol) {
        while(this.getErrors().contains(symbol)) {
            System.out.println("Вы уже совершали данную ошибку, пожалуйста введите новую догадку");
            symbol = (new Scanner(System.in)).nextLine();
        }

        return symbol;
    }

    public int getErrorsNumber() {
        return this.errorsNumber;
    }

    public void setErrorsNumber(int errorsNumber) {
        this.errorsNumber = errorsNumber;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getFoundWord() {
        return this.foundWord;
    }

    public void setFoundWord(String foundWord) {
        this.foundWord = foundWord;
    }

    public String getErrors() {
        return this.errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    private Game() {
        List<String> dict;
        try {
            dict = Files.readAllLines(Path.of("src/main/dict/singular.txt"));
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }

        Random rand = new Random();
        dict = dict.stream().filter((s) -> s.length() == 5).collect(Collectors.toList());
        this.setErrors("");
        this.setWord(dict.get(rand.nextInt(dict.size())));
        this.setFoundWord(this.getWord().replaceAll("[А-яёЁ]", "*"));
    }

    public void gameStatus() {
        System.out.println("Вам задагадо слово из " + this.getWord().length() + " букв");
        if (!this.getErrors().isEmpty()) {
            System.out.println("Вы совершили эти ошибки:");
            System.out.println(this.getErrors());
        }

        System.out.println("Вы угадали:" + this.getFoundWord());
    }
}

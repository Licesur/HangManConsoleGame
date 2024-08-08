package org.example;

import java.util.Scanner;

public class Menu {

    public static String command;

    public static void menuImp(){
        Scanner scanner = new Scanner(System.in);
        command = getCommandFromUser(scanner);
        while(true){
            if(command.equals("Н")){
                Game.start(getDifficultyLevelFromUser(scanner));
            } else if (command.equals("В")) {
                System.exit(0);
            }
            command = getCommandFromUser(scanner);
        }
    }

    public static String getCommandFromUser(Scanner scanner){
        System.out.println("Добро пожаловать, пожалуйста введите команду:\n[Н]овая игра | [В]ыход");
        String command = scanner.nextLine();
        while(!commandValidation(command)){
            System.out.println("Неверный формат команды, попробуйте снова:" +
                    "\nПожалуйста введите команду:\n[Н]овая игра | [В]ыход");
            command = scanner.nextLine();
        }
        return command;
    }
    public static Boolean commandValidation(String command){
        return command.matches("[НВ]");
    };

    public static int getDifficultyLevelFromUser(Scanner scanner){
        System.out.println("Пожалуйста введите желаемый уровень сложности:" +
                "\n1 - легкий\n2 - средний\n3 - тяжелый");
        String input = scanner.nextLine();
        while (!validateDifficultyLevel(input)){
            System.out.println("Такой уровень сложности недоступен вам на данный момент!" +
                    "\nВыберите другой уровень сложности\n1 - легкий\n2 - средний\n3 - тяжелый");
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    public static boolean validateDifficultyLevel(String difficultyLevel){
        return ((difficultyLevel.matches("\\d{1}"))
                &&(Integer.parseInt(difficultyLevel) >= 1 && Integer.parseInt(difficultyLevel) <= 3));
    }

}

package src;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PasswordGenerator {
    public static void main(String[] args) {
        int lengthRequirement = retrieveInt("Enter amount of characters requirement: ");
        int numberRequirement = retrieveInt("Enter amount of numbers required: ");
        int specialCharacterRequirement = retrieveInt("Enter amount of special characters required: ");
        boolean caseSensitivityRequirement = retrieveBoolean("Include capital letters (Y/N): ");
        String generatedPassword = generatePassword(lengthRequirement,
                numberRequirement,
                specialCharacterRequirement,
                caseSensitivityRequirement);
        System.out.println("Generating Password...");
        System.out.println("Generated Password: " + generatedPassword);
        boolean regenerate = retrieveBoolean("Generate another password? (Y/N): ");
        if (regenerate) {
            main(args);
        }
    }

    public static int retrieveInt(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        try {
            return scanner.nextInt();
        } catch (Exception InputMismatchException){
            System.out.println("Invalid input. Must be an integer.");
            return retrieveInt(prompt);
        }
    }

    public static boolean retrieveBoolean(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        try {
            return switch (scanner.next().toLowerCase()) {
                case "y" -> true;
                case "n" -> false;
                default -> {
                    System.out.println("Invalid input, must enter (Y/N).");
                    yield retrieveBoolean(prompt);
                }
            };
        } catch (Exception InputMismatchException){
            System.out.println("Invalid input, must enter (Y/N).");
            return retrieveBoolean(prompt);
        }
    }

    public static String generatePassword(int lengthRequirement,
                                          int numberRequirement,
                                          int specialCharacterRequirement,
                                          boolean caseSensitivityRequirement){
        if (lengthRequirement > 1000000 || numberRequirement + specialCharacterRequirement > 1000000){
            return "Error: Password exceeded maximum length of 1000000";
        }

        Random rand = new Random();
        ArrayList<String> assemblePasswordList = new ArrayList<>();
        for (int i = 0; i < numberRequirement; i++) {
            assemblePasswordList.add(Integer.toString(rand.nextInt(10)));
            lengthRequirement--;
        }
        String specialCharacters = "~!@#$%^&*()_-+=[]{}";
        for (int i = 0; i < specialCharacterRequirement; i++) {
            char randomSpecialCharacter = specialCharacters.charAt(rand.nextInt(specialCharacters.length()));
            assemblePasswordList.add(String.valueOf(randomSpecialCharacter));
            lengthRequirement--;
        }
        if (caseSensitivityRequirement){
            char randomUppercaseLetter = (char) ('A' + rand.nextInt(26));
            assemblePasswordList.add(String.valueOf(randomUppercaseLetter));
            lengthRequirement--;
            if (lengthRequirement > 0){
                for (int i = 0; i < lengthRequirement; i++) {
                    String randomLetter = String.valueOf((char) ('a' + rand.nextInt(26)));
                    if (rand.nextBoolean())
                    {
                        randomLetter = randomLetter.toUpperCase();
                    }
                    assemblePasswordList.add(randomLetter);
                }
            }
        }else{
            if (lengthRequirement > 0){
                for (int i = 0; i < lengthRequirement; i++) {
                    String randomLetter = String.valueOf((char) ('a' + rand.nextInt(26)));
                    assemblePasswordList.add(randomLetter);
                }
            }
        }
        StringBuilder generatedPassword = new StringBuilder();
        int assemblePasswordListSize = assemblePasswordList.size();
        for (int i = 0; i < assemblePasswordListSize; i++) {
            int randomIndex = rand.nextInt(assemblePasswordList.size());
            generatedPassword.append(assemblePasswordList.get(randomIndex));
            assemblePasswordList.remove(randomIndex);
        }
        return generatedPassword.toString();
    }
}

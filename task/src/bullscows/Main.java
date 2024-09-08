package bullscows;

import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int turn = 1;
        String guess;
        int guessLen = 0;
        System.out.println("Please, enter the secret code's length:");
        String guessLength = sc.next();
        try {
            guessLen = Integer.parseInt(guessLength);
        } catch(Exception e){
            System.out.println("Error: \""+guessLength+"\" isn't a valid number.");
        }
        System.out.println("Input the number of possible symbols in the code:");
        int possibleSymbol = sc.nextInt();
        if(guessLen > possibleSymbol || guessLen == 0){
            System.out.println("Error: it's not possible to generate a code with a length of "+guessLength+" with "+possibleSymbol+" unique symbols.");

        } else {
        String secretCode = getSecretCode(guessLen, possibleSymbol);
        System.out.println("Okay, let's start a game!");

        do {
            System.out.println("Turn "+ turn++ +":");
            guess = sc.next();
            cowsAndBulls(secretCode, guess);
        }while(!secretCode.equals(guess));

        System.out.println("Congratulations! You guessed the secret code.");
        }


    }

    private static void cowsAndBulls(String secretCode, String guess) {
        int bull = 0;
        int cow = 0;
        for(int i = 0; i < guess.length(); i++) {
            char chGuess = guess.charAt(i);
            char chsecretCode = secretCode.charAt(i);
            if(chGuess == chsecretCode) {
                bull++;
                continue;
            }else if(secretCode.contains(Character.toString(chGuess))){
                cow++;
            }
        }
        System.out.println(printGrade(bull, cow));
    }

    private static String printGrade(int bull, int cow) {
        if(cow == 0 && bull == 0) {
            return "Grade: None.";
        } else if(bull == 0) {
            return "Grade: "+ cow +" cow(s).";
        } else if(cow == 0){
            return "Grade: "+bull+" bull(s).";
        } else {
            return "Grade: "+bull+" bull(s) and "+cow+" cow(s).";
        }
    }

    private static String getSecretCode(int guessLen, int possibleLen) {
        StringBuilder secretCode = new StringBuilder();
        Random random = new Random();
        int alphaLen = possibleLen - 10;
        int alphaLower = 97;
        int alphaUpper = alphaLower + alphaLen - 1;
        if(guessLen > 36 || possibleLen > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        } else {
            int a;
            while(secretCode.length() != guessLen){
                int selector = random.nextInt(1);
                switch (selector) {
                    case 0:
                        a = random.nextInt(9);
                        while(true) {
                            if(!secretCode.toString().contains(String.valueOf(a))){
                                secretCode.append(a);
                                break;
                            } else {
                                a = random.nextInt(9);
                            }
                        }
                        break;
                    case 1:
                        a = random.nextInt(alphaUpper - alphaLower) + alphaLower;
                        char val = (char) a;
                        while(true) {
                            if(!secretCode.toString().contains(String.valueOf(val))){
                                secretCode.append(val);
                                break;
                            } else {
                                a = random.nextInt(alphaUpper - alphaLower) + alphaLower;
                            }
                        }
                        break;
                }
            }
        }
        System.out.print("\nThe secret is prepared: ");
        for(int i = 0; i < secretCode.toString().length(); i++){
            System.out.print("*");
        }
        System.out.print(" (0-9, a-" + (char) alphaUpper+ ").");
        return secretCode.toString();
    }
}

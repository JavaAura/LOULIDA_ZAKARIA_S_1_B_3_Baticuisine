package main.com.baticuisine.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    private final Scanner scanner;
    private static final Pattern PHONE_PATTERN = Pattern.compile("^0[6-7][0-9]{8}$");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }



    public String validatePhoneNumberInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (PHONE_PATTERN.matcher(input).matches()) {
                return input;  // Valid phone number format
            } else {
                System.out.println("Numéro de téléphone invalide. Veuillez entrer un numéro au format 0652967676.");
            }
        } while (true);
    }



    public boolean validateYesNoInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) {
                return true; // Return true for "y"
            } else if (input.equals("n")) {
                return false; // Return false for "n"
            } else {
                System.out.println("Entrée invalide. Veuillez entrer 'y' pour oui ou 'n' pour non.");
            }
        } while (true);
    }
    public boolean validateBooleanInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input); // Return true or false based on input
            } else {
                System.out.println("Entrée invalide. Veuillez entrer 'true' ou 'false'.");
            }
        } while (true);
    }

    public String validateStringInput(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
                break;
            }
            System.out.println("Invalid input. Please try again.");
        }
        return input;
    }

    public double validateDoubleInput(String prompt) {
        double input = 0;
        while (true) {
            System.out.print(prompt);
            try {
                input = Double.parseDouble(scanner.nextLine());
                if (input >= 0) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            System.out.println("Invalid input. Please enter a valid number.");
        }
        return input;
    }

    public double validatePercentageInput(String prompt) {
        double input = 0;
        while (true) {
            System.out.print(prompt);
            try {
                input = Double.parseDouble(scanner.nextLine());
                if (input >= 0 && input <= 100) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            System.out.println("Invalid input. Please enter a percentage between 0 and 100.");
        }
        return input;
    }

    public LocalDate validateDateInput(String prompt) {
        LocalDate date = null;
        while (true) {
            System.out.print(prompt);
            String dateInput = scanner.nextLine();
            if (validateDate(dateInput)) {
                date = LocalDate.parse(dateInput, DATE_FORMATTER);
                break;  // Exit loop if date is valid
            } else {
                System.out.println("Invalid date format. Please enter the date in dd-MM-yyyy format.");
            }
        }
        return date;
    }

    public static boolean validateDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public int validateIntegerInput(String prompt, int min, int max) {
        int input = -1;
        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            System.out.println("Invalid input. Please enter an integer between " + min + " and " + max + ".");
        }
        return input;
    }


}

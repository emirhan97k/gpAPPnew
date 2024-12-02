import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        int choice = 0;

        System.out.print("[ Hope GP ] \n");
        System.out.println(now.format(formatter));

        do {
            // Print the menu
            System.out.println(); // blank line
            System.out.println("1. Add a new patient");
            System.out.println("2. Remove a patient");
            System.out.println("3. Search for a patient");
            System.out.println("4. Book an appointment");
            System.out.println("5. Exit Safely \n");

            System.out.print("Please enter your choice: ");

            try {
                choice = input.nextInt();
                input.nextLine(); // Consume the newline character left by nextInt()

                if (choice < 1 || choice > 5) {
                    System.out.println("Please enter a valid choice (1-5).");
                } else {
                    switch (choice) {
                        case 1:
                            // Add a new patient
                            System.out.println("Patient's name: ");
                            String name = input.nextLine();
                            System.out.println("Patient's email address: ");
                            String email = input.nextLine();
                            System.out.println("Patient's phone number: ");
                            String phone = input.nextLine();
                            System.out.println("Patient's birth date: ");
                            String bday = input.nextLine();

                            // Create new patient
                            patientRecord patient1 = new patientRecord(name, email, phone, bday);
                            System.out.println("Patient added: " + patient1);
                            break;

                        case 2:
                            // Remove a patient (method call can be added here)
                            break;

                        case 3:
                            // Search for a patient (method call can be added here)
                            break;

                        case 4:
                            // Book an appointment (method call can be added here)
                            break;

                        case 5:
                            System.out.println("Exiting safely...");
                            // Optionally, save patient data to file here before exiting
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                input.nextLine(); // Consume invalid input
            }
        } while (choice != 5); // Loop until user chooses to exit
    }
}

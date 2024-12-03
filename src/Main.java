import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main {
    private static List<PatientRecord> patientList = new ArrayList<>();
    private static Map<String, Appointment> appointments = new HashMap<>();
    private static final String[] timeSlots = {
            "01:00PM", "01:30PM", "02:00PM", "02:30PM", "03:00PM"
    };

    private static final String patientFile = "patients.txt";
    private static final String appointmentFile = "appointments.txt";
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        loadPatients();
        loadAppointments();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        int choice;
        do {
            System.out.println("\n[ Hope GP ]");
            System.out.println(now.format(formatter));

            System.out.println();
            System.out.println("1. Add a new patient");
            System.out.println("2. Remove a patient");
            System.out.println("3. Book an appointment");
            System.out.println("4. View available slots");
            System.out.println("5. Save and exit");
            System.out.println(); // print space between lines
            System.out.println("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    removePatient();
                    break;
                case 3:
                    bookAppointment();
                    break;
                case 4:
                    viewAvailableSlots();
                    break;
                case 5:
                    System.out.println("Exiting safely...");
                    saveData();
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 6);
    }

    // Add a new patient
    private static void addPatient() {
        System.out.println("Enter patient's name: ");
        String name = input.nextLine();
        System.out.println("Enter patient's email: ");
        String email = input.nextLine();
        System.out.println("Enter patient's phone number: ");
        String phone = input.nextLine();
        System.out.println("Enter patient's date of birth: ");
        String dob = input.nextLine();

        PatientRecord newPatient = new PatientRecord(name, email, phone, dob);
        patientList.add(newPatient);
        System.out.println("Patient added successfully: " + newPatient);
    }

    // Remove a patient by ID
    private static void removePatient() {
        System.out.println("Enter patient ID to remove: ");
        String patientId = input.nextLine();
        boolean found = false;

        for (Iterator<PatientRecord> iterator = patientList.iterator(); iterator.hasNext();) {
            PatientRecord patient = iterator.next();
            if (patient.getId().equals(patientId)) {
                iterator.remove();
                found = true;
                System.out.println("Patient removed successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Patient ID not found.");
        }
    }

    // Book an appointment for a patient
    private static void bookAppointment() {
        System.out.println("Enter patient ID for appointment: ");
        String patientId = input.nextLine();

        PatientRecord patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Available timeslots:");
        for (String slot : timeSlots) {
            if (!appointments.containsKey(slot)) {
                System.out.println(slot);
            }
        }

        System.out.println("Enter timeslot to book (e.g., 01:00PM): ");
        String slot = input.nextLine();

        if (appointments.containsKey(slot)) {
            System.out.println("Error: The selected timeslot is already booked.");
        } else {
            Appointment appointment = new Appointment(patient, slot);
            appointments.put(slot, appointment);
            System.out.println("Appointment booked successfully! " + appointment);
        }
    }

    // View available timeslots
    private static void viewAvailableSlots() {
        System.out.println("Available and booked timeslots:");
        for (String slot : timeSlots) {
            if (appointments.containsKey(slot)) {
                System.out.println(slot + " - Booked");
            } else {
                System.out.println(slot + " - Available");
            }
        }
    }

    // Find patient by ID
    private static PatientRecord findPatientById(String id) {
        for (PatientRecord patient : patientList) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

    // Save patient and appointment data to files
    private static void saveData() {
        try (BufferedWriter patientWriter = new BufferedWriter(new FileWriter(patientFile));
             BufferedWriter appointmentWriter = new BufferedWriter(new FileWriter(appointmentFile))) {

            for (PatientRecord patient : patientList) {
                patientWriter.write(patient.toString());
                patientWriter.newLine();
            }

            for (Appointment appointment : appointments.values()) {
                appointmentWriter.write(appointment.toString());
                appointmentWriter.newLine();
            }

            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load patient and appointment data from files
    private static void loadPatients() {
        try (BufferedReader reader = new BufferedReader(new FileReader(patientFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    PatientRecord patient = new PatientRecord(parts[1], parts[2], parts[3], parts[4]);
                    patientList.add(patient);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading patient data: " + e.getMessage());
        }
    }

    // Load appointments from file
    private static void loadAppointments() {
        try (BufferedReader reader = new BufferedReader(new FileReader(appointmentFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    PatientRecord patient = findPatientById(parts[0]);
                    if (patient != null) {
                        Appointment appointment = new Appointment(patient, parts[2]);
                        appointments.put(parts[2], appointment);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading appointment data: " + e.getMessage());
        }
    }
}

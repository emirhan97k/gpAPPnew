import java.util.*; // required to use list/map/scanner
import java.io.*; // required for files
import java.time.LocalDateTime; // to display the date and time
import java.time.format.DateTimeFormatter;


public class Main {
    private static List<PatientRecord> patientList = new ArrayList<>(); // creating an arraylist object for patients
    private static Map<String, Appointment> appointments = new HashMap<>(); // creating a hashmap object for appointments
    private static final String[] timeSlots = {
            "01:00PM", "01:30PM", "02:00PM", "02:30PM", "03:00PM" // an array of strings for available time slots, cannot be changed (final)
    };


    private static final String patientFile = "patients.txt"; // file to save patient data
    private static final String appointmentFile = "appointments.txt"; // file to save appointment data
    private static Scanner input = new Scanner(System.in); //

    public static void main(String[] args) {
        loadPatients(); // load  data here
        loadAppointments(); // load data here as wel
        // display time/date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        // MENU HERE
        int choice; //
        do {
            System.out.println("\n[ Hope GP ]");
            System.out.println(now.format(formatter));

            System.out.println(); // for spacing
            System.out.println("1. Add a new patient");
            System.out.println("2. Remove a patient");
            System.out.println("3. Book an appointment");
            System.out.println("4. View available slots");
            System.out.println("5. Save and exit");
            System.out.println(); //space
            System.out.println("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine();
            // switch here for selection
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
                    saveData(); // save data before exiting
                    break;
                default:
                    System.out.println("invalid choice!!!! try again.");
            }
        } while (choice != 5); // LOOPING menu until user chooses to exit
    }

    private static void addPatient() { // add a new patient
        System.out.println("Enter patient's name: ");
        String name = input.nextLine();
        System.out.println("Enter patient's email: ");
        String email = input.nextLine();
        System.out.println("Enter patient's phone number: ");
        String phone = input.nextLine();
        System.out.println("Enter patient's date of birth: ");
        String bday = input.nextLine();

        PatientRecord newPatient = new PatientRecord(name, email, phone, bday); // create new patient OBJECT
        patientList.add(newPatient);
        System.out.println("Patient added successfully: " + newPatient);
    }

    private static void removePatient() { // remove a patient by ID
        System.out.println("Enter patient ID to remove: ");
        String patientId = input.nextLine();
        boolean found = false;

        for (Iterator<PatientRecord> iterator = patientList.iterator(); iterator.hasNext();) { // iterate through patients
            PatientRecord patient = iterator.next();
            if (patient.getId().equals(patientId)) { // check if ID matches
                iterator.remove();
                found = true;
                System.out.println("Patient removed successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Patient ID not found."); // error
        }
    }

    private static void bookAppointment() { // book an appointment for a patient
        System.out.println("Enter patient ID for appointment: ");
        String patientId = input.nextLine();

        PatientRecord patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found."); // error
            return;
        }

        System.out.println("Available timeslots:");
        for (String slot : timeSlots) {
            if (!appointments.containsKey(slot)) { // check
                System.out.println(slot); // print available ones
            }
        }

        System.out.println("Enter timeslot to book (like 01:00PM): ");
        String slot = input.nextLine();

        if (appointments.containsKey(slot)) { // check if booked
            System.out.println("Error: The selected timeslot is already booked."); // error
        } else {
            Appointment appointment = new Appointment(patient, slot); // create new appointment
            appointments.put(slot, appointment); // add appointment to the map
            System.out.println("Appointment booked successfully! " + appointment);
        }
    }

    private static void viewAvailableSlots() {
        System.out.println("Available and booked timeslots:");
        for (String slot : timeSlots) {
            if (appointments.containsKey(slot)) { // check if slot is booked
                System.out.println(slot + " - Booked");
            } else {
                System.out.println(slot + " - Available");
            }
        }
    }

    private static PatientRecord findPatientById(String id) { // finding patient by ID
        for (PatientRecord patient : patientList) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null; // if not found
    }


    // saving data method
    private static void saveData() {
        try (BufferedWriter patientWriter = new BufferedWriter(new FileWriter(patientFile)); // writer object for patient file
             BufferedWriter appointmentWriter = new BufferedWriter(new FileWriter(appointmentFile))) { // writer object for appointment file

            for (PatientRecord patient : patientList) {
                patientWriter.write(patient.toString()); // write patient to file
                patientWriter.newLine(); // newline
            }

            for (Appointment appointment : appointments.values()) {
                appointmentWriter.write(appointment.toString()); // write appointment to file
                appointmentWriter.newLine(); // newline
            }

            System.out.println("Data saved successfully!");
        } catch (IOException e) { // error handling here
            System.out.println("Error saving data: " + e.getMessage()); // error
        }
    }
    // LOADING patient data from file
    private static void loadPatients() {
        try (BufferedReader reader = new BufferedReader(new FileReader(patientFile))) {
            String line; // creating line variable (to store each line)
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // spliting for neat
                if (parts.length == 5) {
                    PatientRecord patient = new PatientRecord(parts[1], parts[2], parts[3], parts[4]); // parts = data
                    patientList.add(patient);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading patient data: " + e.getMessage()); // error
        }
    }
    // LOADING appointments from file
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
            System.out.println("Error loading appointment data: " + e.getMessage()); // error
        }
    }
}


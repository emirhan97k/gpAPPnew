import java.io.Serializable; // for the Serializable interface
import java.util.*;

public class Appointment implements Serializable {
    private PatientRecord patient; // declaring a variable
    private String timeslot; // declaring a variable to store the appointment time

    // Constructor to initialize
    public Appointment(PatientRecord patient, String timeslot) {
        this.patient = patient;
        this.timeslot = timeslot;
    }


    public PatientRecord getPatient() {
        return patient; // Returns the current patient for this appointment
    }


    public void setPatient(PatientRecord patient) {
        this.patient = patient; // Updates the patient with a new PatientRecord
    }

    @Override
    // checks and overriding the toString method for returning info of the appointment
    public String toString() {
        return patient.getId() + " | Patient Name: " + patient.getName() + " | Time: " + timeslot; // Returns the appointment details
    }
}

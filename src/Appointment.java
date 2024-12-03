import java.io.Serializable;
import java.util.*;
public class Appointment implements Serializable {
    private PatientRecord patient;
    private String timeslot;


    public Appointment(PatientRecord patient, String timeslot) {
        this.patient = patient;
        this.timeslot = timeslot;
    }

    // returns

    public PatientRecord getPatient() {
        return patient;
    }
    public void setPatient(PatientRecord patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return patient.getId() + "Patient Name: " + patient.getName() + "Time: " + timeslot;
    }
}
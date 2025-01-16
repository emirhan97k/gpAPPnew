import java.io.*;  // required for Serializable library

// Class for patient record for unique ID's
public class PatientRecord implements Serializable {
    private static int idCounter = 1; // first id is set to 1
    // Declaring Variables here
    private String id;
    private String name;
    private String email;
    private String phone;
    private String bday;

    // Constructor to initialize
    public PatientRecord(String name, String email, String phone, String bday) {
        this.id = String.format("%04d", idCounter++);  // 0000 and increment by 1
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bday = bday;
    }

    // .getId returns id
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getBirthday() {
        return bday;
    }

    @Override // checks if its the same object
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PatientRecord that = (PatientRecord) obj;
        return this.id.equals(that.id);
    }
    // converting same method to toString method for displaying purposes
    @Override
    public String toString() {
        // Format to save in text file: ID | Name | Email | Phone | Birthday
        return id + "|" + name + "|" + email + "|" + phone + "|" + bday;
    }
}

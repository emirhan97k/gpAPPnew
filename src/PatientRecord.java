import java.io.*;

// Class for patientrecord           for unique ID's
public class PatientRecord implements Serializable {
    private static int idCounter = 1;
    private String id;
    private String name;
    private String email;
    private String phone;
    private String bday;

    public PatientRecord(String name, String email, String phone, String bday) {
        this.id = String.format("%04d", idCounter++);  // from 0000 and +1
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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PatientRecord that = (PatientRecord) obj;
        return this.id.equals(that.id); // Compare IDs
    }
}
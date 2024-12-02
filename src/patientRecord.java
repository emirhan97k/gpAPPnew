public class patientRecord {
    String ID;
    String name;
    String email;
    String phone;
    String bday;

    private static int patientCount = 0; // Used to generate unique patient IDs

    public patientRecord(String name, String email, String phone, String bday) {
        this.ID = String.format("%04d", ++patientCount); // Automatically generate a unique ID
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bday = bday;
    }

    @Override
    public String toString() {
        return "ID: " + ID + ", Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Birthdate: " + bday;
    }

    }


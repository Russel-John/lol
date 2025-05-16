public class PatientInfo {
    String name, age, email, bloodType;
    PatientInfo left, right;

    public PatientInfo(String name, String age, String email, String bloodType) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.bloodType = bloodType;
        this.left = this.right = null;
    }
}
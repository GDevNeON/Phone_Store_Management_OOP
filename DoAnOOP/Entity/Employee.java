package Entity;

public class Employee extends Person {
    private String ID_Worker, Role, Shift;

    public Employee(String ID_Worker, String name, Integer age, String gender, String address, String email, String SDT, String role, String shift) {
        super(name, age, gender, address, email, SDT);
        this.ID_Worker = ID_Worker;
        this.Role = role;
        this.Shift = shift;
    }

    public Employee() {

    }
    public String getID_Worker() {
        return ID_Worker;
    }

    public void setID_Worker(String ID_Worker) {
        this.ID_Worker = ID_Worker;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        this.Shift = shift;
    }
}

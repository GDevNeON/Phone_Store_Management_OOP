package Entity;

public class Manager extends Person {
    protected String ID_Manager;
    protected String Role;
    protected String Shift;
    protected int SLNV;

    public Manager(String ID_Manager, String name, Integer age, String gender, String address, String email, String SDT, String role, String shift, int slnv) {
        super(name, age, gender, address, email, SDT);
        this.ID_Manager = ID_Manager;
        this.Role = role;
        this.Shift = shift;
        this.SLNV = slnv;
    }

    public Manager() {

    }


    public String getID_Manager() {
        return ID_Manager;
    }

    public void setID_Manager(String ID_Manager) {
        this.ID_Manager = ID_Manager;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public int getSLNV() {
        return SLNV;
    }

    public void setSLNV(int slnv) {
        SLNV = slnv;
    }
}
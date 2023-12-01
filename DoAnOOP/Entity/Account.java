package Entity;

public class Account {
    protected String Account_id;
    protected String Username;
    protected String Password;
    protected String Position;

    public Account(String Account_id, String Username, String Password, String Position) {
        this.Account_id = Account_id;
        this.Username = Username;
        this.Password = Password;
        this.Position = Position;
    }

    public Account() {

    }

    public String getAccount_id() {
        return Account_id;
    }

    public void setAccount_id(String account_id) {
        this.Account_id = account_id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        this.Position = position;
    }
}

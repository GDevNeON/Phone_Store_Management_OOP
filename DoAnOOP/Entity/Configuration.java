package Entity;

public class Configuration {
	 private String Screen, OS, Chip, Ram, ROM, Battery, FrontCamera, RearCamera, IMEI_Code;
	    public Configuration(String IMEI_Code, String Screen, String OS, String Chip, String Ram, String ROM, String Battery, String FrontCamera, String RearCamera) {
	        super();
	        this.IMEI_Code = IMEI_Code;
	        this.Screen = Screen;
	        this.OS = OS;
	        this.Chip = Chip;
	        this.Ram = Ram;
	        this.ROM = ROM;
	        this.Battery = Battery;
	        this.FrontCamera = FrontCamera;
	        this.RearCamera = RearCamera;
	    }

    public Configuration() {

    }

    public String getIMEI_Code() {
	        return IMEI_Code;
	    }
	    public void setIMEI_Code(String  IMEI_Code) {
	        this.IMEI_Code = IMEI_Code;
	    }
	    public String getScreen() {
	        return Screen;
	    }
	    public void setScreen(String Screen) {
	        this.Screen = Screen;
	    }
	    public String getOS() {
	        return OS;
	    }
	    public void setOS(String OS) {
	        this.OS = OS;
	    }
	    public String getChip() {
	        return Chip;
	    }
	    public void setChip(String Chip) {
	        this.Chip = Chip;
	    }
	    public String getRam() {
	        return Ram;
	    }
	    public void setRam(String Ram) {
	        this.Ram = Ram;
	    }
	    public String getROM() {
	        return ROM;
	    }
	    public void setROM(String ROM) {
	        this.ROM = ROM;
	    }
	    public String getBattery() {
	        return Battery;
	    }
	    public void setBattery(String Battery) {
	        this.Battery = Battery;
	    }
	    public String getFrontCamera() {
	        return FrontCamera;
	    }
	    public void setFrontCamera(String FrontCamera) {
	        this.FrontCamera = FrontCamera;
	    }
	    public String getRearCamera() {
	        return RearCamera;
	    }
	    public void setRearCamera(String RearCamera) {
	        this.RearCamera = RearCamera;
	    }
}

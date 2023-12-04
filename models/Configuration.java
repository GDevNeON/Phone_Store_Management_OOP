package models;

public class Configuration {
  private String screen, os, chip, ram, rom, battery, frontCamera, rearCamera, imeiCode;

  public Configuration() {
  }

  public Configuration(String imeiCode, String screen, String os, String chip, String ram, String rom, String battery,
      String frontCamera, String rearCamera) {
    this.imeiCode = imeiCode;
    this.screen = screen;
    this.os = os;
    this.chip = chip;
    this.ram = ram;
    this.rom = rom;
    this.battery = battery;
    this.frontCamera = frontCamera;
    this.rearCamera = rearCamera;
  }

  public String getImeiCode() {
    return this.imeiCode;
  }

  public void setImeiCode(String imeiCode) {
    this.imeiCode = imeiCode;
  }

  public String getScreen() {
    return this.screen;
  }

  public void setScreen(String screen) {
    this.screen = screen;
  }

  public String getOs() {
    return this.os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public String getChip() {
    return this.chip;
  }

  public void setChip(String chip) {
    this.chip = chip;
  }

  public String getRam() {
    return this.ram;
  }

  public void setRam(String ram) {
    this.ram = ram;
  }

  public String getRom() {
    return this.rom;
  }

  public void setRom(String rom) {
    this.rom = rom;
  }

  public String getBattery() {
    return this.battery;
  }

  public void setBattery(String battery) {
    this.battery = battery;
  }

  public String getFrontCamera() {
    return this.frontCamera;
  }

  public void setFrontCamera(String frontCamera) {
    this.frontCamera = frontCamera;
  }

  public String getRearCamera() {
    return this.rearCamera;
  }

  public void setRearCamera(String rearCamera) {
    this.rearCamera = rearCamera;
  }

  public Configuration imeiCode(String imeiCode) {
    setImeiCode(imeiCode);
    return this;
  }

  public Configuration screen(String screen) {
    setScreen(screen);
    return this;
  }

  public Configuration os(String os) {
    setOs(os);
    return this;
  }

  public Configuration chip(String chip) {
    setChip(chip);
    return this;
  }

  public Configuration ram(String ram) {
    setRam(ram);
    return this;
  }

  public Configuration rom(String rom) {
    setRom(rom);
    return this;
  }

  public Configuration battery(String battery) {
    setBattery(battery);
    return this;
  }

  public Configuration frontCamera(String frontCamera) {
    setFrontCamera(frontCamera);
    return this;
  }

  public Configuration rearCamera(String rearCamera) {
    setRearCamera(rearCamera);
    return this;
  }

}
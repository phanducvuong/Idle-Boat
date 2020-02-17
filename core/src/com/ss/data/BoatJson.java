package com.ss.data;

import com.google.gson.annotations.SerializedName;

public class BoatJson {

  @SerializedName("boat")
  private String boat;

  @SerializedName("blood")
  private int blood;

  @SerializedName("speed")
  private float speed;

  @SerializedName("id_boat")
  private int idBoat;

  @SerializedName("coin")
  private int coin;

  public String getBoat() {
    return boat;
  }

  public void setBoat(String boat) {
    this.boat = boat;
  }

  public int getBlood() {
    return blood;
  }

  public void setBlood(int blood) {
    this.blood = blood;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public int getIdBoat() {
    return idBoat;
  }

  public void setIdBoat(int idBoat) {
    this.idBoat = idBoat;
  }

  public int getCoin() {
    return coin;
  }

  public void setCoin(int coin) {
    this.coin = coin;
  }
}

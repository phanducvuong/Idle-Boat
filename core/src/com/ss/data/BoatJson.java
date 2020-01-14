package com.ss.data;

import com.google.gson.annotations.SerializedName;

public class BoatJson {

  @SerializedName("boat")
  private String boat;

  @SerializedName("blood")
  private int blood;

  @SerializedName("speed")
  private float speed;

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
}

package com.ss.data;

import com.google.gson.annotations.SerializedName;

public class WeaponJson {

  @SerializedName("cannon")
  private String cannon;

  @SerializedName("cannon_fight")
  private String cannonFight;

  @SerializedName("bullet")
  private String bullet;

  @SerializedName("attack_bullet")
  private float attackBullet;

  @SerializedName("id_cannon")
  private int idCannon;

  @SerializedName("speed")
  private float speed;

  @SerializedName("coin")
  private long coin;

  public String getCannon() {
    return cannon;
  }

  public void setCannon(String cannon) {
    this.cannon = cannon;
  }

  public String getCannonFight() {
    return cannonFight;
  }

  public void setCannonFight(String cannonFight) {
    this.cannonFight = cannonFight;
  }

  public String getBullet() {
    return bullet;
  }

  public void setBullet(String bullet) {
    this.bullet = bullet;
  }

  public float getAttackBullet() {
    return attackBullet;
  }

  public void setAttackBullet(float attackBullet) {
    this.attackBullet = attackBullet;
  }

  public int getIdCannon() {
    return idCannon;
  }

  public void setIdCannon(int idCannon) {
    this.idCannon = idCannon;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public long getCoin() {
    return coin;
  }

  public void setCoin(long coin) {
    this.coin = coin;
  }
}

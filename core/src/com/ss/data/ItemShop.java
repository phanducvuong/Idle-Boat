package com.ss.data;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ItemShop {

  private int idCannon;
  private Image imgOff;
  private Image imgWeapon;
  private Group gBtnOn;
  private Label lbCoin;
  private long coin;
  private boolean unlock;

  public ItemShop(int idCannon, Image imgOff, Image imgWeapon, Group gBtnOn, Label lbCoin, long coin, boolean unlock) {

    this.idCannon = idCannon;
    this.imgOff = imgOff;
    this.imgWeapon = imgWeapon;
    this.gBtnOn = gBtnOn;
    this.lbCoin = lbCoin;
    this.coin = coin;
    this.unlock = unlock;

  }

  public int getIdCannon() {
    return idCannon;
  }

  public void setIdCannon(int idCannon) {
    this.idCannon = idCannon;
  }

  public Image getImgOff() {
    return imgOff;
  }

  public void setImgOff(Image imgOff) {
    this.imgOff = imgOff;
  }

  public Group getgBtnOn() {
    return gBtnOn;
  }

  public void setgBtnOn(Group gBtnOn) {
    this.gBtnOn = gBtnOn;
  }

  public long getCoin() {
    return coin;
  }

  public void setCoin(long coin) {
    this.coin = coin;
  }

  public Image getImgWeapon() {
    return imgWeapon;
  }

  public void setImgWeapon(Image imgWeapon) {
    this.imgWeapon = imgWeapon;
  }

  public Label getLbCoin() {
    return lbCoin;
  }

  public void setLbCoin(Label lbCoin) {
    this.lbCoin = lbCoin;
  }

  public boolean isUnlock() {
    return unlock;
  }

  public void setUnlock(boolean unlock) {
    this.unlock = unlock;
  }
}

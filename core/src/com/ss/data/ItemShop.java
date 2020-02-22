package com.ss.data;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ItemShop {

  private Image imgOff;
  private Image imgWeapon;
  private Group gBtnOn;
  private long coin;

  public ItemShop(Image imgOff, Image imgWeapon, Group gBtnOn, long coin) {

    this.imgOff = imgOff;
    this.imgWeapon = imgWeapon;
    this.gBtnOn = gBtnOn;
    this.coin = coin;

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
}

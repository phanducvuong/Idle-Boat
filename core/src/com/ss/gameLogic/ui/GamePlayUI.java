package com.ss.gameLogic.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.GMain;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.objects.Weapon;

public class GamePlayUI {

  private static final float WIDTH = GStage.getWorldWidth();
  private static final float HEIGHT = GStage.getWorldHeight();

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private Game G;
  private Group gUI;

  private Group gBuyWeapon;
  private Image btnCoin, iconWeapon;

  private Image imgShop;
  public Image imgRecycle;

  public GamePlayUI(Game G, Group gUI) {

    this.gUI = gUI;
    this.G = G;
    gBuyWeapon = new Group();

  }

  public void initShopAndBtnBuyWeapon() {

    btnCoin = GUI.createImage(textureAtlas, "coin");
    iconWeapon = GUI.createImage(GMain.weaponAtlas, "cannon_12");
    iconWeapon.setScale(1.4f);
    iconWeapon.setPosition(gBuyWeapon.getX() + 5, gBuyWeapon.getY() - 5);

    gBuyWeapon.addActor(btnCoin);
    gBuyWeapon.addActor(iconWeapon);

    gBuyWeapon.setPosition(WIDTH/2 - btnCoin.getWidth()/2, HEIGHT - btnCoin.getHeight() - 5);
    gBuyWeapon.setOrigin(btnCoin.getWidth()/2, btnCoin.getHeight()/2);

    imgShop = GUI.createImage(textureAtlas, "shop");
    imgShop.setPosition(gBuyWeapon.getX() + btnCoin.getWidth() + 20, gBuyWeapon.getY() + 5);

    imgRecycle = GUI.createImage(textureAtlas, "recycle");
    imgRecycle.setScale(1.2f);
    imgRecycle.setPosition(gBuyWeapon.getX() - 80, gBuyWeapon.getY() + 5);

    gUI.addActor(gBuyWeapon);
    gUI.addActor(imgShop);
    gUI.addActor(imgRecycle);

    eventClickListener();
  }

  private void eventClickListener() {

    gBuyWeapon.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        G.addWeapon(iconWeapon.getX(), iconWeapon.getY());
        return super.touchDown(event, x, y, pointer, button);
      }
    });

  }

  public void setPosIconWeapon(String cannon) {

    switch (cannon) {

      case "cannon_0":
      case "cannon_1":
      case "cannon_2":
      case "cannon_3":
        iconWeapon.setPosition(gBuyWeapon.getX(), gBuyWeapon.getY() + 5);
        break;

      case "cannon_4":
      case "cannon_5":
        iconWeapon.setPosition(gBuyWeapon.getX() + 10, gBuyWeapon.getY() - 20);
        break;

      case "cannon_6":
      case "cannon_7":
      case "cannon_8":
      case "cannon_9":
      case "cannon_10":
      case "cannon_11":
        iconWeapon.setPosition(gBuyWeapon.getX() + 5, gBuyWeapon.getY() - 10);
        break;

      case "cannon_12":
        iconWeapon.setPosition(gBuyWeapon.getX() + 5, gBuyWeapon.getY() - 5);
        break;

    }

  }
}

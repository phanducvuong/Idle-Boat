package com.ss.gameLogic.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.config.Config;
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

  public Group gTopUI;
  private Image coinCollection, bgPercentFinished, imgPercentFinished, imgSetting;
  public Label lbCoinCollection;

  public GamePlayUI(Game G, Group gUI) {

    this.gUI = gUI;
    this.G = G;
    gBuyWeapon = new Group();
    gTopUI = new Group();

  }

  public void initShopAndBtnBuyWeapon() {

    btnCoin = GUI.createImage(textureAtlas, "coin");
    iconWeapon = GUI.createImage(GMain.weaponAtlas, "cannon_0");
    iconWeapon.setScale(1.4f);
    iconWeapon.setPosition(gBuyWeapon.getX() + 5, gBuyWeapon.getY());

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

  public void initTopUI() {

    bgPercentFinished = GUI.createImage(textureAtlas, "bg_percent_finished");
    bgPercentFinished.setPosition(gUI.getWidth()/2 - bgPercentFinished.getWidth()/2, 0);

    coinCollection = GUI.createImage(textureAtlas, "coin_collection");
    coinCollection.setPosition(0, bgPercentFinished.getHeight()/2 - coinCollection.getHeight()/2);

    imgPercentFinished = GUI.createImage(textureAtlas, "percent_finished");
    float y = bgPercentFinished.getY() + bgPercentFinished.getHeight()/2 - imgPercentFinished.getHeight()/2;
    imgPercentFinished.setPosition(bgPercentFinished.getX() + 92, y);

    lbCoinCollection = new Label("25M", new Label.LabelStyle(Config.BITMAP_YELLOW_FONT, null));
    lbCoinCollection.setAlignment(Align.center);
    lbCoinCollection.setPosition(coinCollection.getX() + coinCollection.getWidth()/2 - lbCoinCollection.getWidth()/2 + 20, coinCollection.getY() - 5);
    lbCoinCollection.setFontScale(.5f);

    imgSetting = GUI.createImage(textureAtlas, "setting");
    imgSetting.setScale(1.2f);
    imgSetting.setPosition(coinCollection.getX(), coinCollection.getY() + 100);

    gTopUI.addActor(coinCollection);
    gTopUI.addActor(lbCoinCollection);
    gTopUI.addActor(bgPercentFinished);
    gTopUI.addActor(imgSetting);
    gTopUI.addActor(imgPercentFinished);
    gUI.addActor(gTopUI);

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
      case "cannon_19":
        iconWeapon.setPosition(gBuyWeapon.getX() + 5, gBuyWeapon.getY() - 10);
        break;

      case "cannon_12":
      case "cannon_13":
        iconWeapon.setPosition(gBuyWeapon.getX() + 5, gBuyWeapon.getY() - 5);
        break;

      case "cannon_14":
        iconWeapon.setPosition(gBuyWeapon.getX() + 5, gBuyWeapon.getY() + 10);
        break;

      case "cannon_15":
      case "cannon_20":
      case "cannon_21":
      case "cannon_22":
      case "cannon_23":
        iconWeapon.setPosition(gBuyWeapon.getX() + 5, gBuyWeapon.getY());
        break;

      case "cannon_16":
      case "cannon_17":
      case "cannon_18":
        iconWeapon.setPosition(gBuyWeapon.getX() + 5, gBuyWeapon.getY() - 15);
        break;
    }

  }
}

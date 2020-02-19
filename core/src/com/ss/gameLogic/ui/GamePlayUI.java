package com.ss.gameLogic.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.effect.EffectGame;

public class GamePlayUI {

  private static final float WIDTH = GStage.getWorldWidth();
  private static final float HEIGHT = GStage.getWorldHeight();

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private EffectGame effectGame = EffectGame.getInstance();
  private Game G;
  private Group gUI;

  private Group gBuyWeapon;
  private Image btnCoin, iconWeapon;

  private Image imgShop;
  public long coinCollection = 0;
  public long coinBuyWeapon = 0;
  public int idIconCannon = 0, idNewCannon = 0;
  public Image imgRecycle;

  public Group gTopUI;
  private Image imgCoinCollection, bgPercentFinished, imgBtnSoundOn, imgBtnSoundOff;
  public Image imgPercentFinished;
  public Label lbCoinCollection, lbLevel, lbCoinBuyWeapon;

  private Group gShop;
  private Image bgShop, bgWeapon, btnBuyWeapon, imgLock;

  public GamePlayUI(Game G, Group gUI) {

    this.gUI = gUI;
    this.G = G;

    gBuyWeapon = new Group();
    gShop = new Group();
    gTopUI = new Group();

    initShopAndBtnBuyWeapon();
    initTopUI();
    initShop();
    eventClickListener();

  }

  private void initShopAndBtnBuyWeapon() {

    btnCoin = GUI.createImage(textureAtlas, "coin");
    iconWeapon = GUI.createImage(GMain.weaponAtlas, "cannon_0");

    lbCoinBuyWeapon = new Label("0", new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbCoinBuyWeapon.setAlignment(Align.center);
    lbCoinBuyWeapon.setPosition(btnCoin.getX() + btnCoin.getWidth()/2 - lbCoinBuyWeapon.getWidth()/2, btnCoin.getY() + 15);
    lbCoinBuyWeapon.setFontScale(.7f);

    gBuyWeapon.addActor(btnCoin);
    gBuyWeapon.addActor(iconWeapon);
    gBuyWeapon.addActor(lbCoinBuyWeapon);
    gBuyWeapon.setOrigin(btnCoin.getWidth()/2, btnCoin.getHeight()/2);

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

  }

  private void initTopUI() {

    bgPercentFinished = GUI.createImage(textureAtlas, "bg_percent_finished");
    bgPercentFinished.setPosition(gUI.getWidth()/2 - bgPercentFinished.getWidth()/2, 0);

    imgCoinCollection = GUI.createImage(textureAtlas, "coin_collection");
    imgCoinCollection.setPosition(0, bgPercentFinished.getHeight()/2 - imgCoinCollection.getHeight()/2);

    imgPercentFinished = GUI.createImage(textureAtlas, "percent_finished");
    float y = bgPercentFinished.getY() + bgPercentFinished.getHeight()/2 - imgPercentFinished.getHeight()/2;
    imgPercentFinished.setPosition(bgPercentFinished.getX() + 92, y);
    imgPercentFinished.setScale(0);

    lbLevel = new Label("999", new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbLevel.setAlignment(Align.center);
    lbLevel.setPosition(bgPercentFinished.getX() - lbLevel.getWidth()/2 + 50, bgPercentFinished.getY() + 25);
    lbLevel.setFontScale(.45f, .5f);

    lbCoinCollection = new Label("0", new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbCoinCollection.setAlignment(Align.center);
    lbCoinCollection.setPosition(imgCoinCollection.getX() + imgCoinCollection.getWidth()/2 - lbCoinCollection.getWidth()/2 + 20, imgCoinCollection.getY() + 10);
    lbCoinCollection.setFontScale(.5f);

    imgBtnSoundOn = GUI.createImage(textureAtlas, "sound_on");
    assert imgBtnSoundOn != null;
    imgBtnSoundOn.setScale(.8f);
    imgBtnSoundOn.setPosition(imgCoinCollection.getX() + 10, imgCoinCollection.getY() + 100);
    imgBtnSoundOn.setOrigin(Align.center);

    imgBtnSoundOff = GUI.createImage(textureAtlas, "sound_off");
    assert imgBtnSoundOff != null;
    imgBtnSoundOff.setScale(.8f);
    imgBtnSoundOff.setPosition(imgCoinCollection.getX() + 10, imgCoinCollection.getY() + 100);
    imgBtnSoundOff.setOrigin(Align.center);
    imgBtnSoundOff.setVisible(false);

    gTopUI.addActor(imgCoinCollection);
    gTopUI.addActor(lbCoinCollection);
    gTopUI.addActor(bgPercentFinished);
    gTopUI.addActor(lbLevel);
    gTopUI.addActor(imgBtnSoundOn);
    gTopUI.addActor(imgBtnSoundOff);
    gTopUI.addActor(imgPercentFinished);
    gUI.addActor(gTopUI);

  }

  private void initShop() {

    bgShop = GUI.createImage(textureAtlas, "bg_shop");
    gShop.addActor(bgShop);
    gShop.setPosition(gUI.getWidth()/2 - bgShop.getWidth()/2, gUI.getHeight()/2 - bgShop.getHeight()/2);
    gUI.addActor(gShop);

  }

  public void eventClickListener() {

    btn_add_weapon: {
      gBuyWeapon.addListener(new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          gBuyWeapon.setTouchable(Touchable.disabled);
          Runnable run = () -> {
            G.addWeapon(idIconCannon, iconWeapon.getX(), iconWeapon.getY());
            gBuyWeapon.setTouchable(Touchable.enabled);
          };
          effectGame.eftClick(gBuyWeapon, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });
    }

    btn_sound: {

      imgBtnSoundOn.addListener(new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          imgBtnSoundOn.setTouchable(Touchable.disabled);
          Runnable run = () -> {
            imgBtnSoundOn.setVisible(false);
            imgBtnSoundOff.setVisible(true);
            imgBtnSoundOff.setTouchable(Touchable.enabled);
          };

          effectGame.eftClick(imgBtnSoundOn, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

      imgBtnSoundOff.addListener(new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          imgBtnSoundOff.setTouchable(Touchable.disabled);
          Runnable run = () -> {
            imgBtnSoundOff.setVisible(false);
            imgBtnSoundOn.setVisible(true);
            imgBtnSoundOn.setTouchable(Touchable.enabled);
          };

          effectGame.eftClick(imgBtnSoundOff, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

    }

    btn_shop: {

      imgShop.addListener(new InputListener(){

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {



          return super.touchDown(event, x, y, pointer, button);
        }
      });

    }

  }

  public void setIconWeapon(int idCannon) {

    switch (idCannon) {

      case 0:
        iconWeapon.setPosition(0, 5);
        break;

      case 1:
        TextureRegion t1 = GMain.weaponAtlas.findRegion("cannon_1");
        iconWeapon.setDrawable(new TextureRegionDrawable(t1));
        iconWeapon.setSize(t1.getRegionWidth(), t1.getRegionHeight());
        iconWeapon.setPosition(0, 5);
        break;

      case 2:
        TextureRegion t2 = GMain.weaponAtlas.findRegion("cannon_2");
        iconWeapon.setDrawable(new TextureRegionDrawable(t2));
        iconWeapon.setSize(t2.getRegionWidth(), t2.getRegionHeight());
        iconWeapon.setPosition(0, 5);
        break;

      case 3:
        TextureRegion t3 = GMain.weaponAtlas.findRegion("cannon_3");
        iconWeapon.setDrawable(new TextureRegionDrawable(t3));
        iconWeapon.setSize(t3.getRegionWidth(), t3.getRegionHeight());
        iconWeapon.setPosition(0, 5);
        break;

      case 4:
        TextureRegion t4 = GMain.weaponAtlas.findRegion("cannon_4");
        iconWeapon.setDrawable(new TextureRegionDrawable(t4));
        iconWeapon.setSize(t4.getRegionWidth(), t4.getRegionHeight());
        iconWeapon.setPosition(10, -20);
        break;

      case 5:
        TextureRegion t5 = GMain.weaponAtlas.findRegion("cannon_5");
        iconWeapon.setDrawable(new TextureRegionDrawable(t5));
        iconWeapon.setSize(t5.getRegionWidth(), t5.getRegionHeight());
        iconWeapon.setPosition(10, -20);
        break;

      case 6:
        TextureRegion t6 = GMain.weaponAtlas.findRegion("cannon_6");
        iconWeapon.setDrawable(new TextureRegionDrawable(t6));
        iconWeapon.setSize(t6.getRegionWidth(), t6.getRegionHeight());
        iconWeapon.setPosition(5, -10);
        break;

      case 7:
        TextureRegion t7 = GMain.weaponAtlas.findRegion("cannon_7");
        iconWeapon.setDrawable(new TextureRegionDrawable(t7));
        iconWeapon.setSize(t7.getRegionWidth(), t7.getRegionHeight());
        iconWeapon.setPosition(5, -10);
        break;

      case 8:
        TextureRegion t8 = GMain.weaponAtlas.findRegion("cannon_8");
        iconWeapon.setDrawable(new TextureRegionDrawable(t8));
        iconWeapon.setSize(t8.getRegionWidth(), t8.getRegionHeight());
        iconWeapon.setPosition(5, -10);
        break;

      case 9:
        TextureRegion t9 = GMain.weaponAtlas.findRegion("cannon_9");
        iconWeapon.setDrawable(new TextureRegionDrawable(t9));
        iconWeapon.setSize(t9.getRegionWidth(), t9.getRegionHeight());
        iconWeapon.setPosition(5, -10);
        break;

      case 10:
        TextureRegion t10 = GMain.weaponAtlas.findRegion("cannon_10");
        iconWeapon.setDrawable(new TextureRegionDrawable(t10));
        iconWeapon.setSize(t10.getRegionWidth(), t10.getRegionHeight());
        iconWeapon.setPosition(5, -10);
        break;

      case 11:
        TextureRegion t11 = GMain.weaponAtlas.findRegion("cannon_11");
        iconWeapon.setDrawable(new TextureRegionDrawable(t11));
        iconWeapon.setSize(t11.getRegionWidth(), t11.getRegionHeight());
        iconWeapon.setPosition(5, -10);
        break;

      case 19:
        TextureRegion t19 = GMain.weaponAtlas.findRegion("cannon_19");
        iconWeapon.setDrawable(new TextureRegionDrawable(t19));
        iconWeapon.setSize(t19.getRegionWidth(), t19.getRegionHeight());
        iconWeapon.setPosition(5, -10);
        break;

      case 12:
        TextureRegion t12 = GMain.weaponAtlas.findRegion("cannon_12");
        iconWeapon.setDrawable(new TextureRegionDrawable(t12));
        iconWeapon.setSize(t12.getRegionWidth(), t12.getRegionHeight());
        iconWeapon.setPosition(5, -5);
        break;

      case 13:
        TextureRegion t13 = GMain.weaponAtlas.findRegion("cannon_13");
        iconWeapon.setDrawable(new TextureRegionDrawable(t13));
        iconWeapon.setSize(t13.getRegionWidth(), t13.getRegionHeight());
        iconWeapon.setPosition(5, -5);
        break;

      case 14:
        TextureRegion t14 = GMain.weaponAtlas.findRegion("cannon_14");
        iconWeapon.setDrawable(new TextureRegionDrawable(t14));
        iconWeapon.setSize(t14.getRegionWidth(), t14.getRegionHeight());
        iconWeapon.setPosition(5, +10);
        break;

      case 15:
        TextureRegion t15 = GMain.weaponAtlas.findRegion("cannon_15");
        iconWeapon.setDrawable(new TextureRegionDrawable(t15));
        iconWeapon.setSize(t15.getRegionWidth(), t15.getRegionHeight());
        iconWeapon.setPosition(5, 0);
        break;

      case 20:
        TextureRegion t20 = GMain.weaponAtlas.findRegion("cannon_20");
        iconWeapon.setDrawable(new TextureRegionDrawable(t20));
        iconWeapon.setSize(t20.getRegionWidth(), t20.getRegionHeight());
        iconWeapon.setPosition(5, 0);
        break;

      case 21:
        TextureRegion t21 = GMain.weaponAtlas.findRegion("cannon_21");
        iconWeapon.setDrawable(new TextureRegionDrawable(t21));
        iconWeapon.setSize(t21.getRegionWidth(), t21.getRegionHeight());
        iconWeapon.setPosition(5, 0);
        break;

      case 22:
        TextureRegion t22 = GMain.weaponAtlas.findRegion("cannon_22");
        iconWeapon.setDrawable(new TextureRegionDrawable(t22));
        iconWeapon.setSize(t22.getRegionWidth(), t22.getRegionHeight());
        iconWeapon.setPosition(5, 0);
        break;

      case 23:
        TextureRegion t23 = GMain.weaponAtlas.findRegion("cannon_23");
        iconWeapon.setDrawable(new TextureRegionDrawable(t23));
        iconWeapon.setSize(t23.getRegionWidth(), t23.getRegionHeight());
        iconWeapon.setPosition(5, 0);
        break;

      case 16:
        TextureRegion t16 = GMain.weaponAtlas.findRegion("cannon_16");
        iconWeapon.setDrawable(new TextureRegionDrawable(t16));
        iconWeapon.setSize(t16.getRegionWidth(), t16.getRegionHeight());
        iconWeapon.setPosition(5, -15);
        break;

      case 17:
        TextureRegion t17 = GMain.weaponAtlas.findRegion("cannon_17");
        iconWeapon.setDrawable(new TextureRegionDrawable(t17));
        iconWeapon.setSize(t17.getRegionWidth(), t17.getRegionHeight());
        iconWeapon.setPosition(5, -15);
        break;

      case 18:
        TextureRegion t18 = GMain.weaponAtlas.findRegion("cannon_18");
        iconWeapon.setDrawable(new TextureRegionDrawable(t18));
        iconWeapon.setSize(t18.getRegionWidth(), t18.getRegionHeight());
        iconWeapon.setPosition(5, -15);
        break;
    }

    iconWeapon.setScale(1.4f);

  }

  public void setTextCoinCollection(long coin) {

    coinCollection += coin;
    String c = G.logicGame.compressCoin(coinCollection);
    lbCoinCollection.setText("$"+c);

  }

  public void setTextCoinBuyWeapon(long coin) {

    coinBuyWeapon = coin;
    String s = G.logicGame.compressCoin(coinBuyWeapon);
    lbCoinBuyWeapon.setText("$"+s);

  }

  public void getCoinCollection() {

    //check preference if (have) ? coinCollection : 10000

  }

  public void updateIdIconCannon(int idUpdate) {

    idIconCannon = idUpdate;
    setIconWeapon(idUpdate);
  }

  public void setIdNewCannon(int idCannon) { idNewCannon = idCannon; }

  public void lvUp() {

    int tempLv = Integer.parseInt(lbLevel.getText().toString());
    lbLevel.setText(tempLv+1);

  }
}

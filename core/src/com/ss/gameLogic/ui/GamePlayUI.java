package com.ss.gameLogic.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.core.effect.SoundEffects;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.data.ItemShop;
import com.ss.data.WeaponJson;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.effect.EffectGame;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.Weapon;

import java.util.ArrayList;
import java.util.List;

public class GamePlayUI {

  private static final float WIDTH = GStage.getWorldWidth();
  private static final float HEIGHT = GStage.getWorldHeight();

  private IPlatform plf = GMain.platform;

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private EffectGame effectGame = EffectGame.getInstance();
  private Game G;
  private Group gUI;
  public Image imgBgBlack;

  public Label lbShowCoinDelWeapon;
  public Label lbNoEnough;
  public Label lbFinishedWave;
  public Label lbNewWave;

  public Group gBuyWeapon;
  private Image btnCoin, iconWeapon;

  public Image imgShop, bgShopp;
  public long coinCollection = Config.COIN_START_GAME;
  public long coinBuyWeaponPre = 0;
  public int idIconCannonPreInBuyWeapon = 0, idBestPowerCannon = 0; //1: to setIconBuyWeapon, 2: show effect new weapon
  public Image imgRecycle;

  public Group gTopUI;
  private Image imgCoinCollection, bgPercentFinished, imgBtnSoundOn, imgBtnSoundOff, iconAds;
  public Image imgPercentFinished;
  public Label lbCoinCollection, lbLevel, lbCoinBuyWeapon;

  public Group gShop;
  private Image bgShop;
  public List<ItemShop> listItemShop = new ArrayList<>();

  public Group gContinue;
  public Image imgShineWeapon, imgShineBoat, imgWeaponOrBoat, imgRateDamageOrHitpoint, imgRateRangeOrSpeed, bgUnlock;
  private Label lbWeaponOrBoat, lbUnlock, lbDamageOrHitpoint, lbRangeOrSpeed;

  private Group gAds, gBtnOK;
  private Image bgAds, imgBtnXAds, imgBtnOk;

  public Label lbEndGame;
  public Image bgEndGame;

  public Group gTutorial, gbtnStart;
  private Image arrowL, arrowR;
  private int iPos = 0;

  public GamePlayUI(Game G, Group gUI) {

    this.gUI = gUI;
    this.G = G;

    gBuyWeapon = new Group();
    gShop = new Group();
    gTopUI = G.gTopUI;

    imgBgBlack = GUI.createImage(textureAtlas, "bg_black");
    imgBgBlack.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    imgBgBlack.setVisible(false);
    gUI.addActor(imgBgBlack);

    initTutorial();
    initShopAndBtnBuyWeapon();
    initTopUI();
    initShop();
    initAds();
    initUIUnlockWeapon();
    eventClickListener();

    //lb show coin when delete weapon
    lbShowCoinDelWeapon = new Label("+20k", new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbShowCoinDelWeapon.setAlignment(Align.center);
    lbShowCoinDelWeapon.setPosition(imgRecycle.getX() - lbShowCoinDelWeapon.getWidth()/2 + 10, imgRecycle.getY());
    lbShowCoinDelWeapon.setFontScale(.7f);
    lbShowCoinDelWeapon.getColor().a = 0;
    G.gTopUI.addActor(lbShowCoinDelWeapon);

    //lb show text when pedestal is filled
    lbNoEnough = new Label(C.lang.noEnoughEmty, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbNoEnough.setAlignment(Align.center);
    lbNoEnough.setFontScale(.8f);
    lbNoEnough.getColor().a = 0;
    lbNoEnough.setPosition(GStage.getWorldWidth()/2 - lbNoEnough.getWidth()/2, GStage.getHeight()/2 - lbNoEnough.getHeight()/2);
    G.gAds.addActor(lbNoEnough);

    //lb show win game
    lbFinishedWave = new Label(C.lang.winGame, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbFinishedWave.setAlignment(Align.center);
    lbFinishedWave.setFontScale(.8f);
    lbFinishedWave.getColor().a = 0;
    lbFinishedWave.setPosition(GStage.getWorldWidth()/2 - lbFinishedWave.getWidth()/2, GStage.getWorldHeight()/2 - lbFinishedWave.getHeight()/2 - 200);
    G.gTopUI.addActor(lbFinishedWave);

    //lb end game
    bgEndGame = GUI.createImage(textureAtlas, "bg_end_game");
    bgEndGame.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    bgEndGame.setVisible(false);
    G.gEndGame.addActor(bgEndGame);

    lbEndGame = new Label(C.lang.endGame, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbEndGame.setAlignment(Align.center);
    lbEndGame.setPosition(GStage.getWorldWidth()/2 - lbEndGame.getWidth()/2, GStage.getWorldHeight()/2 - lbEndGame.getHeight()/2 - 150);
    lbEndGame.setFontScale(.8f);
    lbEndGame.getColor().a = 0;
    G.gEndGame.addActor(lbEndGame);

    //lb show new wave
    lbNewWave = new Label(C.lang.newWave, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbNewWave.setAlignment(Align.center);
    lbNewWave.setPosition(-GStage.getWorldWidth()/2 - lbNewWave.getWidth(), GStage.getWorldHeight()/2 - lbNewWave.getHeight()/2 - 200);
    G.gTopUI.addActor(lbNewWave);

  }

  private void initTutorial() {

    gTutorial = new Group();
    List<Image> listTutorial = new ArrayList<>();

    for (int i=1; i<=5; i++) {

      Image t = GUI.createImage(GMain.tutorialAtlas, "t"+i);
      t.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
      gTutorial.addActor(t);
      listTutorial.add(t);

      if (i!=1)
        t.setVisible(false);
      else
        t.setVisible(true);

    }

    arrowL = GUI.createImage(GMain.tutorialAtlas, "arrow");
    arrowL.setOrigin(Align.center);
    arrowL.setRotation(135);
    arrowL.setPosition(20, GStage.getWorldHeight()/2 - arrowL.getHeight()/2);
    gTutorial.addActor(arrowL);

    arrowR = GUI.createImage(GMain.tutorialAtlas, "arrow");
    arrowR.setOrigin(Align.center);
    arrowR.setRotation(-45);
    arrowR.setPosition(GStage.getWorldWidth() - arrowR.getWidth() - 20, GStage.getWorldHeight()/2 - arrowL.getHeight()/2);
    gTutorial.addActor(arrowR);

    gbtnStart = new Group();
    Image btnStart = GUI.createImage(GMain.tutorialAtlas, "btn_start");
    gbtnStart.setSize(btnStart.getWidth(), btnStart.getHeight());
    gbtnStart.setOrigin(Align.center);
    gbtnStart.addActor(btnStart);
    gbtnStart.setPosition(GStage.getWorldWidth()/2 - btnStart.getWidth()/2, GStage.getWorldHeight() - 150);
    gTutorial.addActor(gbtnStart);
    gbtnStart.setVisible(false);

    Label lbStart = new Label(C.lang.start, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbStart.setAlignment(Align.center);
    lbStart.setFontScale(.8f);
    lbStart.setPosition(gbtnStart.getWidth()/2 - lbStart.getWidth()/2, gbtnStart.getHeight()/2 - lbStart.getHeight()/2);
    gbtnStart.addActor(lbStart);

    arrowL.addListener(new InputListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        iPos -= 1;

        if (iPos >= 0) {

          Image t1 = listTutorial.get(iPos);
          Image t2 = listTutorial.get(iPos+1);

          SoundEffects.start("click_button");

          effectGame.eftClick(arrowR, () -> effectGame.eftSliceTutorialL(t1, t2));
          gbtnStart.setVisible(false);

        }
        else iPos = 0;

        return super.touchDown(event, x, y, pointer, button);
      }
    });

    arrowR.addListener(new InputListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        iPos += 1;
        Runnable run = () -> {

          if (iPos == listTutorial.size()-1)
            gbtnStart.setVisible(true);

        };

        if (iPos < listTutorial.size()) {

          SoundEffects.start("click_button");

          Image t1 = listTutorial.get(iPos-1);
          Image t2 = listTutorial.get(iPos);
          effectGame.eftClick(arrowR, () -> effectGame.eftSliceTutorialR(t1, t2, run));

        }
        else
          iPos -= 1;

        return super.touchDown(event, x, y, pointer, button);
      }
    });

    gbtnStart.addListener(new InputListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        Runnable run = () -> {
          gTutorial.remove();
          G.resetWhenLevelUp();

          GMain.pref.putBoolean("isNewMember", true);
        };

        SoundEffects.start("click_button");

        effectGame.eftClick(gbtnStart, run);

        return super.touchDown(event, x, y, pointer, button);
      }
    });

    G.gTutorial.addActor(gTutorial);
  }

  private void initAds() {

    gAds = new Group();

    gAds.setOrigin(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2);

    bgAds = GUI.createImage(textureAtlas, "bg_black");
    bgAds.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    bgAds.setVisible(false);
    G.gAds.addActor(bgAds);

    Image bgAds = GUI.createImage(textureAtlas, "bg_ads");
    bgAds.setPosition(GStage.getWorldWidth()/2 - bgAds.getWidth()/2, GStage.getWorldHeight()/2 - bgAds.getHeight()/2);
    gAds.addActor(bgAds);

    Label lbAds = new Label(C.lang.coin, new Label.LabelStyle(Config.BITMAP_YELLOW_FONT, null));
    lbAds.setAlignment(Align.center);
    lbAds.setPosition(bgAds.getX() + bgAds.getWidth()/2 - lbAds.getWidth()/2, bgAds.getY() + bgAds.getHeight()/2 - 150);
    lbAds.setFontScale(.7f);
    gAds.addActor(lbAds);

    gBtnOK = new Group();
    gAds.addActor(gBtnOK);

    imgBtnOk = GUI.createImage(textureAtlas, "btn_agree");
    gBtnOK.setSize(imgBtnOk.getWidth(), imgBtnOk.getHeight());
    gBtnOK.setPosition(bgAds.getX() + bgAds.getWidth()/2 - gBtnOK.getWidth()/2, bgAds.getY() + bgAds.getHeight() - 120);
    gBtnOK.setOrigin(Align.center);
    gBtnOK.addActor(imgBtnOk);

    Label lbAgree = new Label(C.lang.ok, new Label.LabelStyle(Config.BITMAP_YELLOW_FONT, null));
    lbAgree.setAlignment(Align.center);
    lbAgree.setPosition(imgBtnOk.getWidth()/2 - lbAgree.getWidth()/2 + 15, imgBtnOk.getHeight()/2 - lbAgree.getHeight()/2);
    lbAgree.setFontScale(.7f);
    gBtnOK.addActor(lbAgree);

    imgBtnXAds = GUI.createImage(textureAtlas, "btn_x");
    imgBtnXAds.setPosition(bgAds.getX() + bgAds.getWidth() - 70, bgAds.getY() - 20);
    gAds.addActor(imgBtnXAds);

    gAds.setScale(0);
    G.gAds.addActor(gAds);

  }

  public void setNewWave(int wave) {

    lbLevel.setText(wave+"");
    lbNewWave.setText(C.lang.newWave + " " + wave);

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

    gBuyWeapon.setPosition(WIDTH/2 - btnCoin.getWidth()/2, HEIGHT - btnCoin.getHeight() - 40);

    imgShop = GUI.createImage(textureAtlas, "shop");
    imgShop.setPosition(gBuyWeapon.getX() + btnCoin.getWidth() + 20, gBuyWeapon.getY() + 5);
    imgShop.setOrigin(Align.center);

    imgRecycle = GUI.createImage(textureAtlas, "recycle");
    imgRecycle.setScale(1.2f);
    imgRecycle.setPosition(gBuyWeapon.getX() - 80, gBuyWeapon.getY() + 5);

    G.gPos.addActor(gBuyWeapon);
    G.gPos.addActor(imgShop);
    G.gPos.addActor(imgRecycle);

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

    iconAds = GUI.createImage(textureAtlas, "icon_ads");
    iconAds.setOrigin(Align.center);
    iconAds.setPosition(imgBtnSoundOn.getX(), imgBtnSoundOn.getY() + 100);

    G.gTopUI.addActor(imgCoinCollection);
    G.gTopUI.addActor(lbCoinCollection);
    G.gTopUI.addActor(bgPercentFinished);
    G.gTopUI.addActor(lbLevel);
    G.gTopUI.addActor(imgBtnSoundOn);
    G.gTopUI.addActor(imgBtnSoundOff);
    G.gTopUI.addActor(iconAds);
    G.gTopUI.addActor(imgPercentFinished);

  }

  private void initShop() {

    bgShopp = GUI.createImage(textureAtlas, "bg_black");
    bgShopp.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    bgShopp.setVisible(false);
    G.gItemShop.addActor(bgShopp);

    bgShop = GUI.createImage(textureAtlas, "bg_shop");
    gShop.addActor(bgShop);
    gShop.setPosition(gUI.getWidth()/2 - bgShop.getWidth()/2, gUI.getHeight()/2 - bgShop.getHeight()/2);
    G.gItemShop.addActor(gShop);

    Group gItem = new Group();
    gItem.setSize(bgShop.getWidth() - 65, bgShop.getHeight() - 110);
    gItem.setPosition(35, 75);
    gItem.setOrigin(Align.center);
    gItem.setRotation(180);

    Label lbTitleShop = new Label(C.lang.title_shop, new Label.LabelStyle(Config.BITMAP_YELLOW_FONT, null));
    lbTitleShop.setAlignment(Align.center);
    lbTitleShop.setFontScale(1.2f);
    lbTitleShop.setPosition(bgShop.getWidth()/2 - lbTitleShop.getWidth()/2, bgShop.getY() - lbTitleShop.getHeight() + 10);
    gShop.addActor(lbTitleShop);

    gShop.addActor(gItem);

    //init btn_x
    Image btnXShop = GUI.createImage(textureAtlas, "btn_x");
    btnXShop.setPosition(bgShop.getWidth() - btnXShop.getWidth()/2 - 15, -35);
    gShop.addActor(btnXShop);
    eventClickBtnXShop(btnXShop);

    //init table
    Table scrollTable = new Table();
    for (WeaponJson weapon : G.data.listDataWeapon) {

      Group gTempItem = new Group();

      Image bgWeapon = GUI.createImage(textureAtlas, "bg_weapon");
      bgWeapon.setOrigin(Align.center);
      bgWeapon.setRotation(180);
      gTempItem.setSize(bgWeapon.getWidth(), bgWeapon.getHeight());
      gTempItem.addActor(bgWeapon);

      //image weapon
      Image imgWeapon = GUI.createImage(GMain.weaponAtlas, weapon.getCannon());
      imgWeapon.setPosition(gTempItem.getWidth() - imgWeapon.getWidth() - 50, gTempItem.getHeight()/2 - imgWeapon.getHeight()/2);
      imgWeapon.setRotation(180);
      imgWeapon.setScale(2.2f);
      imgWeapon.setOrigin(Align.center);
      imgWeapon.setColor(Color.BLACK);
      gTempItem.addActor(imgWeapon);

      //btn off
      Image btnBuyWeaponOff = GUI.createImage(textureAtlas, "btn_buy_weapon_off");
      btnBuyWeaponOff.setPosition(10, gTempItem.getHeight()/2 - btnBuyWeaponOff.getHeight()/2);
      btnBuyWeaponOff.setOrigin(Align.center);
      btnBuyWeaponOff.setRotation(180);

      gTempItem.addActor(btnBuyWeaponOff);

      //btn on
      Group gBtnOn = new Group();

      Image btnBuyWeaponOn = GUI.createImage(textureAtlas, "btn_buy_weapon_on");
      btnBuyWeaponOn.setPosition(10, gTempItem.getHeight()/2 - btnBuyWeaponOff.getHeight()/2);

      gBtnOn.setSize(btnBuyWeaponOn.getWidth(), btnBuyWeaponOn.getHeight());
      gBtnOn.setOrigin(Align.center);
      gBtnOn.setPosition(20, 40);
      gBtnOn.addActor(btnBuyWeaponOn);
      gBtnOn.setRotation(180);

      String s = G.logicGame.compressCoin(weapon.getCoin());
      Label lbCoin = new Label(s, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
      lbCoin.setPosition(gBtnOn.getWidth()/2 - lbCoin.getWidth()/2 - 15, gBtnOn.getHeight()/2 - lbCoin.getHeight()/2 + 12);
      lbCoin.setAlignment(Align.center);
      lbCoin.setFontScale(.7f, .8f);
      gBtnOn.addActor(lbCoin);
      gBtnOn.setVisible(false);

      gTempItem.addActor(gBtnOn);

      //add item shop into list item
      ItemShop itemShop = new ItemShop(weapon.getIdCannon(), btnBuyWeaponOff, imgWeapon, gBtnOn, lbCoin, weapon.getCoin(), false);
      listItemShop.add(itemShop);

      scrollTable.add(gTempItem).center().padBottom(10);
      scrollTable.row();

      eventClickBtnBuyWeaponInShop(gBtnOn, itemShop);

    }

    ScrollPane scroller = new ScrollPane(scrollTable);
    Table table = new Table();
    table.setFillParent(true);
    table.add(scroller).fill().expand();

    gItem.addActor(table);

    gShop.moveBy(0, GStage.getWorldHeight());

    listItemShop.get(0).getgBtnOn().setVisible(true);
    listItemShop.get(0).getImgOff().setVisible(false);
    listItemShop.get(0).setUnlock(true);
    listItemShop.get(0).getImgWeapon().setColor(Color.WHITE);

  }

  private void initUIUnlockWeapon() {

    bgUnlock = GUI.createImage(textureAtlas, "bg_black");
    bgUnlock.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    bgUnlock.setVisible(false);
    G.gEffect.addActor(bgUnlock);

    G.gEffect.setOrigin(bgUnlock.getWidth()/2, bgUnlock.getHeight()/2);

    Image ribbon = GUI.createImage(textureAtlas, "ribbon");
    ribbon.setPosition(GStage.getWorldWidth()/2 - ribbon.getWidth()/2, 100);
    G.gEffect.addActor(ribbon);

    lbUnlock = new Label(C.lang.unlock_weapon, new Label.LabelStyle(Config.BITMAP_YELLOW_FONT, null));
    lbUnlock.setAlignment(Align.center);
    lbUnlock.setFontScale(.8f);
    lbUnlock.setPosition(ribbon.getX() + ribbon.getWidth()/2 - lbUnlock.getWidth()/2, ribbon.getY() + ribbon.getHeight()/2 - lbUnlock.getHeight());
    G.gEffect.addActor(lbUnlock);

    imgShineWeapon = GUI.createImage(textureAtlas, "shine_weapon");
    imgShineWeapon.setOrigin(Align.center);
    imgShineWeapon.setPosition(GStage.getWorldWidth()/2 - imgShineWeapon.getWidth()/2, GStage.getWorldHeight()/2 - imgShineWeapon.getHeight()/2 - 100);
    G.gEffect.addActor(imgShineWeapon);

    effectGame.eftImgShine(imgShineWeapon);

    imgShineBoat = GUI.createImage(textureAtlas, "shine_boat");
    imgShineBoat.setOrigin(Align.center);
    imgShineBoat.setPosition(GStage.getWorldWidth()/2 - imgShineWeapon.getWidth()/2, GStage.getWorldHeight()/2 - imgShineWeapon.getHeight()/2 - 100);
    G.gEffect.addActor(imgShineBoat);

    effectGame.eftImgShine(imgShineBoat);

    lbWeaponOrBoat = new Label(C.lang.cannon_1, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbWeaponOrBoat.setAlignment(Align.center);
    lbWeaponOrBoat.setFontScale(.7f);
    lbWeaponOrBoat.setPosition(ribbon.getX() + ribbon.getWidth()/2 - lbWeaponOrBoat.getWidth()/2, ribbon.getY() + ribbon.getHeight() + 20);
    G.gEffect.addActor(lbWeaponOrBoat);

    imgWeaponOrBoat = GUI.createImage(GMain.weaponAtlas, "cannon_1");
    imgWeaponOrBoat.setPosition(GStage.getWorldWidth()/2 - imgWeaponOrBoat.getWidth()/2, GStage.getWorldHeight()/2 - imgWeaponOrBoat.getHeight()/2 - 100);
    G.gEffect.addActor(imgWeaponOrBoat);

    Image bgRateDamage = GUI.createImage(textureAtlas, "bg_rate");
    bgRateDamage.setPosition(imgShineWeapon.getX() + imgShineWeapon.getWidth()/2 - bgRateDamage.getWidth()/2, imgShineWeapon.getY() + imgShineWeapon.getHeight() + 20);
    G.gEffect.addActor(bgRateDamage);

    imgRateDamageOrHitpoint = GUI.createImage(textureAtlas, "rate");
    imgRateDamageOrHitpoint.setPosition(bgRateDamage.getX() + 13, bgRateDamage.getY() + 7);
    imgRateDamageOrHitpoint.setScale(0, 1f);
    G.gEffect.addActor(imgRateDamageOrHitpoint);

    lbDamageOrHitpoint = new Label(C.lang.damage, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbDamageOrHitpoint.setAlignment(Align.center);
    lbDamageOrHitpoint.setFontScale(.4f);
    lbDamageOrHitpoint.setPosition(bgRateDamage.getX() + bgRateDamage.getWidth()/2 - lbDamageOrHitpoint.getWidth()/2, bgRateDamage.getY() - 50);
    G.gEffect.addActor(lbDamageOrHitpoint);

    Image bgRateRange = GUI.createImage(textureAtlas, "bg_rate");
    bgRateRange.setPosition(bgRateDamage.getX(), bgRateDamage.getY() + 130);
    G.gEffect.addActor(bgRateRange);

    imgRateRangeOrSpeed = GUI.createImage(textureAtlas, "rate");
    imgRateRangeOrSpeed.setPosition(bgRateRange.getX() + 13, bgRateRange.getY() + 7);
    imgRateRangeOrSpeed.setScale(0, 1f);
    G.gEffect.addActor(imgRateRangeOrSpeed);

    lbRangeOrSpeed = new Label(C.lang.range, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbRangeOrSpeed.setAlignment(Align.center);
    lbRangeOrSpeed.setFontScale(.4f);
    lbRangeOrSpeed.setPosition(bgRateRange.getX() + bgRateRange.getWidth()/2 - lbRangeOrSpeed.getWidth()/2, bgRateRange.getY() - 50);
    G.gEffect.addActor(lbRangeOrSpeed);

    gContinue = new Group();
    Image imgBtnContinue = GUI.createImage(textureAtlas, "btn_continue");
    gContinue.setPosition(GStage.getWorldWidth()/2 - imgBtnContinue.getWidth()/2, bgRateRange.getY() + 100);
    gContinue.setOrigin(imgBtnContinue.getWidth()/2, imgBtnContinue.getHeight()/2);
    gContinue.addActor(imgBtnContinue);

    Label lbContinue = new Label(C.lang.textContinue, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbContinue.setAlignment(Align.center);
    lbContinue.setFontScale(.6f);
    lbContinue.setPosition(imgBtnContinue.getX() + imgBtnContinue.getWidth()/2 - lbContinue.getWidth()/2, imgBtnContinue.getY() + imgBtnContinue.getHeight()/2 - lbContinue.getHeight()/2);
    gContinue.addActor(lbContinue);

    G.gEffect.addActor(gContinue);
    G.gEffect.setScale(0);

  }

  public void showGUnlockWeaponOrBoat(Weapon weapon) {

    Runnable run = () -> {

      float rateDamage = (float) Math.round((weapon.getAttackBullet() / Config.MAX_ATTACK_BULLET)*100)/100;

      imgRateDamageOrHitpoint.addAction(scaleTo(rateDamage, 1f, .5f, fastSlow));
      imgRateRangeOrSpeed.addAction(scaleTo(1f, 1f, .5f, fastSlow));

    };

    bgUnlock.setVisible(true);

    imgRateDamageOrHitpoint.setScale(0f, 1f);
    imgRateRangeOrSpeed.setScale(0f, 1f);

    TextureRegion textureRegion = GMain.weaponMerge.findRegion(weapon.nameWeapon);
    imgWeaponOrBoat.setDrawable(new TextureRegionDrawable(textureRegion));
    imgWeaponOrBoat.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    imgWeaponOrBoat.setPosition(GStage.getWorldWidth()/2 - imgWeaponOrBoat.getWidth()/2, GStage.getWorldHeight()/2 - imgWeaponOrBoat.getHeight()/2 - 100);

    imgShineBoat.setVisible(true);
    imgShineWeapon.setVisible(true);

    lbUnlock.setText(C.lang.unlock_weapon);
    lbWeaponOrBoat.setText(G.logicGame.getStringNewWeapon(weapon.nameWeapon));
    lbDamageOrHitpoint.setText(C.lang.damage);
    lbRangeOrSpeed.setText(C.lang.range);

    effectGame.eftNewWeapon(G.gEffect, run);

  }

  public void showGUnlockWeaponOrBoat(Boat boat) {

    Runnable run = () -> {

      float rateHitpoint = (float) Math.round((boat.getBlood() / Config.MAX_HITPOINT)*100)/100;
      float rateSpeed = 1 - (float) Math.round((boat.getSpeed() / Config.MAX_SPEED)*100)/100;

      imgRateDamageOrHitpoint.addAction(scaleTo(rateHitpoint, 1f, .5f, fastSlow));
      imgRateRangeOrSpeed.addAction(scaleTo(rateSpeed, 1f, .5f, fastSlow));

    };

    bgUnlock.setVisible(true);

    imgRateDamageOrHitpoint.setScale(0f, 1f);
    imgRateRangeOrSpeed.setScale(0f, 1f);

    TextureRegion textureRegion = GMain.boatMerge.findRegion(boat.name);
    imgWeaponOrBoat.setDrawable(new TextureRegionDrawable(textureRegion));
    imgWeaponOrBoat.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    imgWeaponOrBoat.setPosition(GStage.getWorldWidth()/2 - imgWeaponOrBoat.getWidth()/2, GStage.getWorldHeight()/2 - imgWeaponOrBoat.getHeight()/2 - 100);

    imgShineWeapon.setVisible(false);
    imgShineBoat.setVisible(true);

    lbUnlock.setText(C.lang.unlock_boat);
    lbWeaponOrBoat.setText(G.logicGame.getStringNewBoat(boat.name));
    lbDamageOrHitpoint.setText(C.lang.hitpoint);
    lbRangeOrSpeed.setText(C.lang.speed);

    SoundEffects.stop("unlock_enemy");
    SoundEffects.start("unlock_enemy");

    effectGame.eftNewWeapon(G.gEffect, run);

  }

  private void eventClickBtnXShop(Image btn) {

    btn.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        SequenceAction seq = sequence(
                moveBy(0, GStage.getWorldHeight(), .45f, swingIn),
                run(() -> bgShopp.setVisible(false))
        );

        SoundEffects.stop("click_button");
        SoundEffects.start("click_button");

        gShop.addAction(seq);

        return super.touchDown(event, x, y, pointer, button);
      }
    });

  }

  private void eventClickBtnBuyWeaponInShop(Group gBtn, ItemShop itemShop) {

    gBtn.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        gBtn.setTouchable(Touchable.disabled);
        Runnable run = () -> {

          if (G.logicGame.chkEmptyListPos()) {

            if (coinCollection >= itemShop.getCoin()) {

              G.addWeapon(itemShop.getIdCannon(), true, idIconCannonPreInBuyWeapon);

            }
            else {

              lbNoEnough.setText(C.lang.noEnoughCoin);
              effectGame.eftShowLbNoEnough(lbNoEnough);

            }

          }
          else {

            lbNoEnough.setText(C.lang.noEnoughEmty);
            effectGame.eftShowLbNoEnough(lbNoEnough);

          }

          gBtn.setTouchable(Touchable.enabled);

        };

        effectGame.eftClick(gBtn, run);

      }

    });

  }

  public void eventClickListener() {

    btn_add_weapon: {
      gBuyWeapon.addListener(new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          gBuyWeapon.setTouchable(Touchable.disabled);
          Runnable run = () -> {

            if (G.logicGame.chkEmptyListPos()) {

              if (coinCollection >= coinBuyWeaponPre)
                G.addWeapon(idIconCannonPreInBuyWeapon, false, idIconCannonPreInBuyWeapon);
              else {

                lbNoEnough.setText(C.lang.noEnoughCoin);
                effectGame.eftShowLbNoEnough(lbNoEnough);

              }

            }
            else {

              lbNoEnough.setText(C.lang.noEnoughEmty);
              effectGame.eftShowLbNoEnough(lbNoEnough);

            }

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
            SoundEffects.isMute = true;
            imgBtnSoundOn.setVisible(false);
            imgBtnSoundOff.setVisible(true);
            imgBtnSoundOff.setTouchable(Touchable.enabled);
          };

          SoundEffects.stop("click_button");
          SoundEffects.start("click_button");

          effectGame.eftClick(imgBtnSoundOn, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

      imgBtnSoundOff.addListener(new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          imgBtnSoundOff.setTouchable(Touchable.disabled);
          Runnable run = () -> {
            SoundEffects.isMute = false;
            imgBtnSoundOff.setVisible(false);
            imgBtnSoundOn.setVisible(true);
            imgBtnSoundOn.setTouchable(Touchable.enabled);
          };

          SoundEffects.stop("click_button");
          SoundEffects.start("click_button");

          effectGame.eftClick(imgBtnSoundOff, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

    }

    btn_shop: {

      imgShop.addListener(new InputListener(){

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          Runnable run = () -> {
            bgShopp.setVisible(true);
            gShop.addAction(moveBy(0, -GStage.getWorldHeight(), .65f, swingOut));
          };

          SoundEffects.stop("click_button");
          SoundEffects.start("click_button");

          effectGame.eftClick(imgShop, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

    }

    btn_continue: {

      gContinue.addListener(new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          gContinue.setTouchable(Touchable.disabled);
          Runnable run = () -> {

            gContinue.setTouchable(Touchable.enabled);

            SequenceAction seq = sequence(
                    scaleTo(0f, 0f, .5f, fastSlow),
                    run(() -> bgUnlock.setVisible(false))
            );

            G.gEffect.addAction(seq);

          };

          SoundEffects.stop("click_button");
          SoundEffects.start("click_button");

          effectGame.eftClick(gContinue, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

    }

    btn_x_ads: {

      imgBtnXAds.addListener(new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          Runnable run = () -> bgAds.setVisible(false);

          SequenceAction seq = sequence(
                  scaleTo(0f, 0f, .25f, linear),
                  run(run)
          );

          SoundEffects.stop("click_button");
          SoundEffects.start("click_button");

          gAds.addAction(seq);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

    }

    btn_ok : {

      gBtnOK.addListener(new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          gBtnOK.setTouchable(Touchable.disabled);
          Runnable run = () -> {

            if (plf.isVideoRewardReady()) {

              plf.ShowVideoReward((boolean success) -> {
                if (success) {

                  Runnable run1 = () -> {
                    bgAds.setVisible(false);
                    gBtnOK.setTouchable(Touchable.enabled);
                    setTextCoinCollection(Config.coin);
                  };

                  SequenceAction seq = sequence(
                          scaleTo(0f, 0f, .25f, linear),
                          run(run1)
                  );

                  gAds.addAction(seq);

                }
                else
                  gBtnOK.setTouchable(Touchable.enabled);

              });

            }
            else
              gBtnOK.setTouchable(Touchable.enabled);

          };

          SoundEffects.stop("click_button");
          SoundEffects.start("click_button");

          effectGame.eftClick(gBtnOK, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

    }

    btn_icon_ads: {

      iconAds.addListener(new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

          Runnable run = () -> {

            bgAds.setVisible(true);
            gAds.addAction(scaleTo(1f, 1f, .75f, swingOut));

          };

          SoundEffects.stop("click_button");
          SoundEffects.start("click_button");

          effectGame.eftClick(iconAds, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

    }

  }

  public void setStateBtnShop() {

    for (int i=0; i<idBestPowerCannon - 3; i++) {

      try {

        ItemShop itemShop = listItemShop.get(i);
        itemShop.getImgOff().setVisible(false);
        itemShop.getgBtnOn().setVisible(true);
        itemShop.setUnlock(true);
        itemShop.getImgWeapon().setColor(Color.WHITE);

        G.save.listItemShop("list_item_shop", listItemShop);

      }
      catch (Exception ex) { ex.printStackTrace(); }

    }

    int i = idBestPowerCannon - 4;
    if (i > 0) {

      coinBuyWeaponPre = G.data.HMWeapon.get("cannon_"+i).get(0).getCoin();
      setTextCoinBuyWeapon(coinBuyWeaponPre);

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

  } //setIconWeapon for btnBuyWeapon

  public void setTextCoinCollection(long coin) {

    coinCollection += coin;
    String c = G.logicGame.compressCoin(coinCollection);
    lbCoinCollection.setText(c);

    G.save.coinCollection("coin_collection", coinCollection);

  }

  public void setTextCoinCollectionBuyWeapon(long coin) {

    coinCollection -= coin;
    String c = G.logicGame.compressCoin(coinCollection);
    lbCoinCollection.setText(c);

    G.save.coinCollection("coin_collection", coinCollection);

  }

  public void setTextCoinBuyWeapon(long coin) {

    String s = G.logicGame.compressCoin(coin);
    lbCoinBuyWeapon.setText(s);

  }

  public void updateIdIconCannon(int idUpdate) {

    idIconCannonPreInBuyWeapon = idUpdate;
    setIconWeapon(idUpdate);

  }

  //load state gBtnOn or imgOff in shop
  public void loadStateInShop() {

    for (ItemShop item : listItemShop) {

      if (item.isUnlock()) {

        item.getgBtnOn().setVisible(true);
        item.getImgOff().setVisible(false);
        item.getImgWeapon().setColor(Color.WHITE);

      }

      String c = G.logicGame.compressCoin(item.getCoin());
      item.getLbCoin().setText(c);

    }

  }

  public void loadIconInBtnBuyWeapon() {

    try {

      for (int i=listItemShop.size()-1; i>=0; i--) {

        ItemShop item = listItemShop.get(i);

        if (item.isUnlock()) {

          coinBuyWeaponPre = item.getCoin();
          updateIdIconCannon(item.getIdCannon());
          setTextCoinBuyWeapon(coinBuyWeaponPre);

          break;
        }

      }

    }
    catch (Exception ex) {  }

  }

}
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
import com.ss.GMain;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.data.ItemShop;
import com.ss.data.WeaponJson;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.effect.EffectGame;

import java.util.ArrayList;
import java.util.List;

public class GamePlayUI {

  private static final float WIDTH = GStage.getWorldWidth();
  private static final float HEIGHT = GStage.getWorldHeight();

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private EffectGame effectGame = EffectGame.getInstance();
  private Game G;
  private Group gUI;
  public Image imgBgBlack;
  public Image imgBgEndGame;
  public boolean isOnShop = false; //shop on => setZindex gShop = 1000

  public Label lbShowCoinDelWeapon;
  public Label lbNoEnough;
  public Label lbEndWinGame;
  public Label lbNewWave;

  public Group gBuyWeapon;
  private Image btnCoin, iconWeapon;

  private Image imgShop;
  public long coinCollection = 1100000;
  public long coinBuyWeapon = 0;
  public int idIconCannon = 0, idBestPowerCannon = 0;
  public Image imgRecycle;

  public Group gTopUI;
  private Image imgCoinCollection, bgPercentFinished, imgBtnSoundOn, imgBtnSoundOff;
  public Image imgPercentFinished;
  public Label lbCoinCollection, lbLevel, lbCoinBuyWeapon;

  public Group gShop;
  private Image bgShop;
  private List<ItemShop> listItemShop = new ArrayList<>();

  public Group gUnlockWeapon, gContinue;
  public Image imgShineWeapon, imgShineBoat, imgWeaponOrBoat, imgRateDamageOrHitpoint, imgRateRangeOrSpeed, bgUnlock;
  private Label lbWeaponOrBoat, lbUnlock, lbDamageOrHitpoint;

  public GamePlayUI(Game G, Group gUI) {

    this.gUI = gUI;
    this.G = G;

    gBuyWeapon = new Group();
    gShop = new Group();
    gTopUI = new Group();

    imgBgBlack = GUI.createImage(textureAtlas, "bg_black");
    imgBgBlack.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    imgBgBlack.setVisible(false);
    gUI.addActor(imgBgBlack);

    imgBgEndGame = GUI.createImage(textureAtlas, "bg_end_game");
    imgBgEndGame.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    imgBgEndGame.setVisible(false);
    gUI.addActor(imgBgEndGame);

    initShopAndBtnBuyWeapon();
    initTopUI();
    initShop();
    initUIUnlockWeapon();
    eventClickListener();

    //lb show coin when delete weapon
    lbShowCoinDelWeapon = new Label("+20k", new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbShowCoinDelWeapon.setAlignment(Align.center);
    lbShowCoinDelWeapon.setPosition(imgRecycle.getX() - lbShowCoinDelWeapon.getWidth()/2 + 10, imgRecycle.getY());
    lbShowCoinDelWeapon.setFontScale(.7f);
    lbShowCoinDelWeapon.getColor().a = 0;
    gUI.addActor(lbShowCoinDelWeapon);

    //lb show text when pedestal is filled
    lbNoEnough = new Label(C.lang.noEnoughEmty, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbNoEnough.setAlignment(Align.center);
    lbNoEnough.setFontScale(.8f);
    lbNoEnough.getColor().a = 0;
    lbNoEnough.setPosition(GStage.getWorldWidth()/2 - lbNoEnough.getWidth()/2, GStage.getHeight()/2 - lbNoEnough.getHeight()/2);
    gUI.addActor(lbNoEnough);

    //lb show end or win game
    lbEndWinGame = new Label(C.lang.winGame, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbEndWinGame.setAlignment(Align.center);
    lbEndWinGame.setFontScale(.8f);
    lbEndWinGame.getColor().a = 0;
    lbEndWinGame.setPosition(GStage.getWorldWidth()/2 - lbEndWinGame.getWidth()/2, GStage.getWorldHeight()/2 - lbEndWinGame.getHeight()/2 - 200);
    gUI.addActor(lbEndWinGame);

    //lb show new wave
    lbNewWave = new Label(C.lang.newWave, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbNewWave.setAlignment(Align.center);
    lbNewWave.setPosition(-GStage.getWorldWidth()/2 - lbNewWave.getWidth(), GStage.getWorldHeight()/2 - lbNewWave.getHeight()/2 - 200);
    gUI.addActor(lbNewWave);

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

    imgShop = GUI.createImage(textureAtlas, "shop");
    imgShop.setPosition(gBuyWeapon.getX() + btnCoin.getWidth() + 20, gBuyWeapon.getY() + 5);
    imgShop.setOrigin(Align.center);

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

    Group gItem = new Group();
    gItem.setSize(bgShop.getWidth() - 65, bgShop.getHeight() - 110);
    gItem.setPosition(35, 75);
    gItem.setOrigin(Align.center);
    gItem.setRotation(180);

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
      ItemShop itemShop = new ItemShop(btnBuyWeaponOff, imgWeapon, gBtnOn, weapon.getCoin());
      listItemShop.add(itemShop);

      scrollTable.add(gTempItem).center().padBottom(10);
      scrollTable.row();

      eventClickBtnBuyWeaponInShop(gBtnOn, weapon);

    }

    ScrollPane scroller = new ScrollPane(scrollTable);
    Table table = new Table();
    table.setFillParent(true);
    table.add(scroller).fill().expand();

    gItem.addActor(table);

    gShop.moveBy(0, GStage.getWorldHeight());

    listItemShop.get(0).getgBtnOn().setVisible(true);
    listItemShop.get(0).getImgOff().setVisible(false);
    listItemShop.get(0).getImgWeapon().setColor(Color.WHITE);

  }

  private void initUIUnlockWeapon() {

    gUnlockWeapon = new Group();

    bgUnlock = GUI.createImage(textureAtlas, "bg_black");
    bgUnlock.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    bgUnlock.setVisible(false);
    gUI.addActor(bgUnlock);

    gUnlockWeapon.setOrigin(bgUnlock.getWidth()/2, bgUnlock.getHeight()/2);

    Image ribbon = GUI.createImage(textureAtlas, "ribbon");
    ribbon.setPosition(GStage.getWorldWidth()/2 - ribbon.getWidth()/2, 100);
    gUnlockWeapon.addActor(ribbon);

    lbUnlock = new Label(C.lang.unlock, new Label.LabelStyle(Config.BITMAP_YELLOW_FONT, null));
    lbUnlock.setAlignment(Align.center);
    lbUnlock.setFontScale(.8f);
    lbUnlock.setPosition(ribbon.getX() + ribbon.getWidth()/2 - lbUnlock.getWidth()/2, ribbon.getY() + ribbon.getHeight()/2 - lbUnlock.getHeight());
    gUnlockWeapon.addActor(lbUnlock);

    imgShineWeapon = GUI.createImage(textureAtlas, "shine_weapon");
    imgShineWeapon.setOrigin(Align.center);
    imgShineWeapon.setPosition(GStage.getWorldWidth()/2 - imgShineWeapon.getWidth()/2, GStage.getWorldHeight()/2 - imgShineWeapon.getHeight()/2 - 100);
    gUnlockWeapon.addActor(imgShineWeapon);

    effectGame.eftImgShine(imgShineWeapon);

    imgShineBoat = GUI.createImage(textureAtlas, "shine_boat");
    imgShineBoat.setOrigin(Align.center);
    imgShineBoat.setPosition(GStage.getWorldWidth()/2 - imgShineWeapon.getWidth()/2, GStage.getWorldHeight()/2 - imgShineWeapon.getHeight()/2 - 100);
    gUnlockWeapon.addActor(imgShineBoat);

    effectGame.eftImgShine(imgShineBoat);

    lbWeaponOrBoat = new Label(C.lang.cannon_1, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbWeaponOrBoat.setAlignment(Align.center);
    lbWeaponOrBoat.setFontScale(.7f);
    lbWeaponOrBoat.setPosition(ribbon.getX() + ribbon.getWidth()/2 - lbWeaponOrBoat.getWidth()/2, ribbon.getY() + ribbon.getHeight());
    gUnlockWeapon.addActor(lbWeaponOrBoat);

    imgWeaponOrBoat = GUI.createImage(GMain.weaponAtlas, "cannon_1");
    imgWeaponOrBoat.setScale(4f);
    imgWeaponOrBoat.setPosition(GStage.getWorldWidth()/2 - imgWeaponOrBoat.getWidth()*4/2, GStage.getWorldHeight()/2 - imgWeaponOrBoat.getHeight()*4/2 - 100);
    gUnlockWeapon.addActor(imgWeaponOrBoat);

    Image bgRateDamage = GUI.createImage(textureAtlas, "bg_rate");
    bgRateDamage.setPosition(imgShineWeapon.getX() + imgShineWeapon.getWidth()/2 - bgRateDamage.getWidth()/2, imgShineWeapon.getY() + imgShineWeapon.getHeight() + 20);
    gUnlockWeapon.addActor(bgRateDamage);

    imgRateDamageOrHitpoint = GUI.createImage(textureAtlas, "rate");
    imgRateDamageOrHitpoint.setPosition(bgRateDamage.getX() + 1, bgRateDamage.getY() + 7);
    imgRateDamageOrHitpoint.setScale(0, 1f);
    gUnlockWeapon.addActor(imgRateDamageOrHitpoint);

    lbDamageOrHitpoint = new Label(C.lang.damage, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbDamageOrHitpoint.setAlignment(Align.center);
    lbDamageOrHitpoint.setFontScale(.4f);
    lbDamageOrHitpoint.setPosition(bgRateDamage.getX() + bgRateDamage.getWidth()/2 - lbDamageOrHitpoint.getWidth()/2, bgRateDamage.getY() - 50);
    gUnlockWeapon.addActor(lbDamageOrHitpoint);

    Image bgRateRange = GUI.createImage(textureAtlas, "bg_rate");
    bgRateRange.setPosition(bgRateDamage.getX(), bgRateDamage.getY() + 130);
    gUnlockWeapon.addActor(bgRateRange);

    imgRateRangeOrSpeed = GUI.createImage(textureAtlas, "rate");
    imgRateRangeOrSpeed.setPosition(bgRateRange.getX() + 1, bgRateRange.getY() + 7);
    imgRateRangeOrSpeed.setScale(0, 1f);
    gUnlockWeapon.addActor(imgRateRangeOrSpeed);

    Label lbRange = new Label(C.lang.range, new Label.LabelStyle(Config.BITMAP_WHITE_FONT, null));
    lbRange.setAlignment(Align.center);
    lbRange.setFontScale(.4f);
    lbRange.setPosition(bgRateRange.getX() + bgRateRange.getWidth()/2 - lbRange.getWidth()/2, bgRateRange.getY() - 50);
    gUnlockWeapon.addActor(lbRange);

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

    gUnlockWeapon.addActor(gContinue);
    gUnlockWeapon.setScale(0);
    gUI.addActor(gUnlockWeapon);

  }

  public void showGUnlockWeaponOrBoat() {

    Runnable run = () -> {

      imgRateDamageOrHitpoint.addAction(scaleTo(1f, 1f, .5f, fastSlow));
      imgRateRangeOrSpeed.addAction(scaleTo(1f, 1f, .5f, fastSlow));

    };

    bgUnlock.setVisible(true);
    bgUnlock.setZIndex(1000);
    gUnlockWeapon.setZIndex(1000);

    effectGame.eftNewWeapon(gUnlockWeapon, run);

  }

  private void eventClickBtnXShop(Image btn) {

    btn.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        SequenceAction seq = sequence(
                moveBy(0, GStage.getWorldHeight(), .45f, swingIn),
                run(() -> imgBgBlack.setVisible(false))
        );

        gShop.addAction(seq);

        return super.touchDown(event, x, y, pointer, button);
      }
    });

  }

  private void eventClickBtnBuyWeaponInShop(Group gBtn, WeaponJson weapon) {

    gBtn.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        gBtn.setTouchable(Touchable.disabled);
        Runnable run = () -> {

          if (G.logicGame.chkEmptyListPos()) {

            if (coinCollection >= weapon.getCoin()) {

              G.addWeapon(weapon.getIdCannon(), 0, 0);
              setTextCoinCollectionBuyWeapon(weapon.getCoin());

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

              if (coinCollection >= coinBuyWeapon)
                G.addWeapon(idIconCannon, iconWeapon.getX(), iconWeapon.getY());
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

          isOnShop = true;
          Runnable run = () -> {

            imgBgBlack.setVisible(true);
            imgBgBlack.setZIndex(1000);
            gShop.setZIndex(1000);
            gShop.addAction(moveBy(0, -GStage.getWorldHeight(), .65f, swingOut));

          };

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

            //todo: hide group unlock weapon
            imgRateDamageOrHitpoint.setScale(0f, 1f);
            imgRateRangeOrSpeed.setScale(0f, 1f);
            gContinue.setTouchable(Touchable.enabled);

            SequenceAction seq = sequence(
                    scaleTo(0f, 0f, .5f, fastSlow),
                    run(() -> bgUnlock.setVisible(false))
            );

            gUnlockWeapon.addAction(seq);

          };

          effectGame.eftClick(gContinue, run);

          return super.touchDown(event, x, y, pointer, button);
        }
      });

    }

  }

  public void setStateBtnShop() {

    for (int i=0; i<idBestPowerCannon - 4; i++) {

      try {

        ItemShop itemShop = listItemShop.get(i);
        if (itemShop.getImgOff().isVisible()) {

          itemShop.getImgOff().setVisible(false);
          itemShop.getgBtnOn().setVisible(true);
          itemShop.getImgWeapon().setColor(Color.WHITE);

        }

      }
      catch (Exception ex) { ex.printStackTrace(); }

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
    lbCoinCollection.setText("$"+c);

  }

  public void setTextCoinCollectionBuyWeapon(long coin) {

    coinBuyWeapon = coin;
    coinCollection -= coin;
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

  public void setShowGShop() {

    if (isOnShop) {
      imgBgBlack.setZIndex(1000);
      gShop.setZIndex(1000);
    }

  }

  public void updateIdIconCannon(int idUpdate) {

    idIconCannon = idUpdate;
    setIconWeapon(idUpdate);
  }

  public void setIdBestPowerCannon(int idCannon) { idBestPowerCannon = idCannon; }

  public void lvUp() {

    int tempLv = Integer.parseInt(lbLevel.getText().toString());
    lbLevel.setText(tempLv+1);

  }
}

package com.ss.gameLogic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.data.Data;
import com.ss.data.ItemShop;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.effect.EffectGame;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;

import java.util.List;

public class LogicGame {

  private static LogicGame instance;

  private Game G;
  private Data data = Data.getInstance();
  private EffectGame effectGame = EffectGame.getInstance();

  public static LogicGame getInstance(Game G) {
    return instance == null ? instance = new LogicGame(G) : instance;
  }

  private LogicGame(Game G) {
    this.G = G;
  }

  private PosOfWeapon getPosWeaponAtP(Vector2 v, List<PosOfWeapon> list) {
    for (int i=0; i<list.size(); i++)
      if (list.get(i).pos == v)
        return list.get(i);
    return null;
  }

  private void chkIdCannonAndMerge(Vector2 vFrom, Vector2 vTo, List<PosOfWeapon> list) {

    PosOfWeapon pFrom = getPosWeaponAtP(vFrom, list);
    PosOfWeapon pTo = getPosWeaponAtP(vTo, list);

    try {
      if (pFrom.getWeapon().getIdCannon() != pTo.getWeapon().getIdCannon()) {

        changePosOfWeapon(pFrom, pTo);

      }
      else { //idIconCannonPreInBuyWeapon equal => weapon level up and update weapon at vTo, release vFrome

        Vector2 pos = new Vector2(pTo.getWeapon().gCannon.getX(), pTo.getWeapon().gCannon.getY()); //get pos to make effect merge weapon
        Weapon w1 = pFrom.getWeapon();
        Weapon w2 = pTo.getWeapon();

        int idCannon = pTo.getWeapon().getIdCannon() + 1;
        Weapon weapon = updateWeapon(idCannon);

        Runnable onComplete = () -> {

          weapon.resetWeapon();

          pFrom.getWeapon().removeWeapon();
          pFrom.setWeapon(null);
          pFrom.setEmpty(true);

          pTo.getWeapon().removeWeapon();
          weapon.isOn = true;
          pTo.setWeapon(weapon);

          pTo.getWeapon().addBulletToScene();
          pTo.getWeapon().addCannonToScene();
          pTo.getWeapon().setPosWeapon(pTo.pos);

          pTo.startEftMerge();
          updateIconBuyWeapon(weapon);

          G.save.listPosOfWeapon("list_pos_weapon", list);

        };

        if (weapon != null)
          effectGame.eftMergeWeapon(pos, w1, w2, onComplete);
        else
          changePosOfWeapon(pFrom, pTo);

      }

    }
    catch (Exception ex) {  } //error

  }

  private void changePosOfWeapon(PosOfWeapon pFrom, PosOfWeapon pTo) {

    Weapon weTemp = pTo.getWeapon();
    pTo.setWeapon(pFrom.getWeapon());
    pFrom.setWeapon(weTemp);

    pFrom.getWeapon().setPosWeapon(pFrom.pos);
    pTo.getWeapon().setPosWeapon(pTo.pos);

  }

  public void chkMergeWeapon(Vector2 vFrom, Vector2 vTo, List<PosOfWeapon> list) {
    PosOfWeapon pTo = getPosWeaponAtP(vTo, list);
    PosOfWeapon pFrom = getPosWeaponAtP(vFrom, list);

    if (pTo != null && pFrom != null) {

      if (pTo.getIsEmpty()) {
        pTo.setEmpty(false);
        pTo.setWeapon(pFrom.getWeapon());
        pTo.getWeapon().setPosWeapon(pTo.pos);

        pFrom.setWeapon(null);
        pFrom.setEmpty(true);

      }
      else
        chkIdCannonAndMerge(vFrom, vTo, list);

    }
  }

  private Weapon updateWeapon(int idCannon) {

    Weapon weapon = null;

    if (idCannon >= data.HMWeapon.size())
      return null;
    else {

      for (Weapon w : data.HMWeapon.get("cannon_" + idCannon))
        if (!w.isOn) {
          weapon = w;
          break;
        }
      return weapon;

    }
  }

  public boolean chkWinGame(List<Boat> boats) {

    for (Boat boat : boats)
      if (boat.isAlive)
        return false;
    return true;

  }

  public String compressCoin(long coin) {

    String s = coin+"";
    int temp = s.length();

    switch (temp) {

      case 4: s = s.substring(0, 1) + "K"; break;
      case 5: s = s.substring(0, 2) + "K"; break;
      case 6: s = s.substring(0, 3) + "K"; break;
      case 7: s = s.substring(0, 1) + "M"; break;
      case 8: s = s.substring(0, 2) + "M"; break;
      case 9: s = s.substring(0, 3) + "M"; break;
      case 10: s = s.substring(0, 1) + "B"; break;
      case 11: s = s.substring(0, 2) + "B"; break;
      case 12: s = s.substring(0, 3) + "B"; break;

    }

    return s;
  }

  private void updateIconBuyWeapon(Weapon weapon) {

    int idCannonPre = G.gamePlayUI.idIconCannonPreInBuyWeapon;
    int idCannonMerge = weapon.getIdCannon();

    int temp = (idCannonMerge - (idCannonPre+4)) >= 0 ? (idCannonMerge - 4): idCannonPre;
    G.gamePlayUI.updateIdIconCannon(temp);

    if (idCannonMerge > G.gamePlayUI.idBestPowerCannon) {

      G.gamePlayUI.idBestPowerCannon = idCannonMerge;
      G.gamePlayUI.setStateBtnShop();

      //save idBestPowerCannon
      G.save.idBestPowerCannon("id_best_power_cannon", idCannonMerge);

      Image w1 = data.HMMergeWeapon.get(weapon.nameWeapon).get(0);
      Image w2 = data.HMMergeWeapon.get(weapon.nameWeapon).get(1);

      Runnable run = () -> {
        G.bgGame.remove();
        G.gamePlayUI.showGUnlockWeaponOrBoat(weapon);
      };

      effectGame.eftAniMergeWeapon(w1, w2, run);

    }

  }

  public void setPosWeaponMerge(Image cannonMerge, float x, float y) {

    cannonMerge.setPosition(x, y - cannonMerge.getHeight()/2);

  }

  public void disTouchWeapon(List<PosOfWeapon> listPos, Weapon weapon) {

    for (PosOfWeapon pos : listPos)
      if (pos.getWeapon() != null && pos.getWeapon() != weapon)
        pos.getWeapon().gCannon.setTouchable(Touchable.disabled);

  }

  public void enTouchWeapon(List<PosOfWeapon> listPos, Weapon weapon) {

    for (PosOfWeapon pos : listPos)
      if (pos.getWeapon() != null)
        pos.getWeapon().gCannon.setTouchable(Touchable.enabled);

  }

  public boolean chkEmptyListPos() {

    for (PosOfWeapon pos : G.listPosOfWeapon)
      if (pos.getIsEmpty())
        return true;
    return false;

  }

  public void chkWinOrEndGame(boolean isWin) {

    if (isWin) {

      G.wave += 1;
      G.resetWhenLevelUp();

    }//isWin = true => new wave else setVisible for imgBgEndGame = false
    else {

      G.gamePlayUI.bgEndGame.setVisible(false);
      G.gamePlayUI.imgPercentFinished.setScale(0);
      G.resetWhenEndGame();

      //todo: show ads full screen when pass two level

    }

  }

  public void resetGame(List<PosOfWeapon> listPos, List<Boat> listBoat) {

    for (Boat boat : listBoat)
      boat.reset();

    for (PosOfWeapon pos : listPos)
      if (pos.getWeapon() != null)
        pos.getWeapon().resetBullet();

  }

  //update level when level up (Game call)
  public void updateLevel(int wave) {

    if (wave == 1 || wave == 0) {
      G.target = 5;
      G.listBoat.clear();
      G.initLv(5, "boat_0");
    }
    else if (wave == 2) {

      G.target += 20;

      G.listBoat.clear();
      G.initLv(10, "boat_0", "boat_1");

      Boat boat = data.HMBoat.get("boat_1").get(0);
      G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

    } //unlock boat_1
    else if (wave > 2 && wave <= 6) {

      if (wave == 6) {

        G.listBoat.clear();
        G.initLv(10, "boat_0", "boat_1", "boat_2");

        Boat boat = data.HMBoat.get("boat_2").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_2
      else {

        G.listBoat.clear();
        G.initLv(10, "boat_0", "boat_1");
        G.target += 10;

      }

    }
    else if (wave > 6 && wave <= 10) {

      if (wave == 10) {

        G.listBoat.clear();
        G.initLv(10, "boat_0", "boat_1", "boat_2", "boat_3");

        Boat boat = data.HMBoat.get("boat_3").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_3
      else {

        G.listBoat.clear();
        G.initLv(10, "boat_0", "boat_1", "boat_2");
        G.target += 10;

      }

    }
    else if (wave > 10 && wave <= 15) {

      if (wave == 15) {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1", "boat_4");
        G.initLv(10, "boat_2", "boat_3");

        Boat boat = data.HMBoat.get("boat_4").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_4
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1");
        G.initLv(10, "boat_2", "boat_3");
        G.target += 10;

      }

    }
    else if (wave > 15 && wave <= 20) {

      if (wave == 20) {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1");
        G.initLv(15, "boat_2", "boat_3");
        G.initLv(7, "boat_4", "boat_5");

        Boat boat = data.HMBoat.get("boat_5").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_5
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1");
        G.initLv(15, "boat_2", "boat_3");
        G.initLv(7, "boat_4");
        G.target += 10;

      }

    }
    else if (wave > 20 && wave <= 25) {

      if (wave == 25) {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1", "boat_6");
        G.initLv(15, "boat_2", "boat_3");
        G.initLv(10, "boat_4", "boat_5");

        Boat boat = data.HMBoat.get("boat_6").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_6
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1");
        G.initLv(15, "boat_2", "boat_3");
        G.initLv(10, "boat_4", "boat_5");
        G.target += 10;

      }

    }
    else if (wave > 25 && wave <= 30) {

      if (wave == 30) {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1", "boat_7");
        G.initLv(15, "boat_2", "boat_3");
        G.initLv(10, "boat_4", "boat_5", "boat_6");

        Boat boat = data.HMBoat.get("boat_7").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_7
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1");
        G.initLv(15, "boat_2", "boat_3");
        G.initLv(10, "boat_4", "boat_5", "boat_6");
        G.target += 10;

      }

    }
    else if (wave > 30 && wave <= 35) {

      if (wave == 35) {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1", "boat_8");
        G.initLv(15, "boat_2", "boat_3", "boat_6");
        G.initLv(10, "boat_4", "boat_5", "boat_7");

        Boat boat = data.HMBoat.get("boat_8").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_8
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_0", "boat_1");
        G.initLv(15, "boat_2", "boat_3", "boat_6");
        G.initLv(10, "boat_4", "boat_5", "boat_7");
        G.target += 10;

      }

    }
    else if (wave > 35 && wave <= 40) {

      if (wave == 40) {

        G.listBoat.clear();
        G.initLv(5, "boat_8", "boat_9");
        G.initLv(15, "boat_2", "boat_3", "boat_6");
        G.initLv(10, "boat_4", "boat_5", "boat_7");

        Boat boat = data.HMBoat.get("boat_9").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_9
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_8");
        G.initLv(15, "boat_2", "boat_3", "boat_6");
        G.initLv(10, "boat_4", "boat_5", "boat_7");
        G.target += 10;

      }

    }
    else if (wave > 40 && wave <= 45) {

      if (wave == 45) {

        G.listBoat.clear();
        G.initLv(5, "boat_9", "boat_10");
        G.initLv(15, "boat_3", "boat_6", "boat_8");
        G.initLv(10, "boat_4", "boat_5", "boat_7");

        Boat boat = data.HMBoat.get("boat_10").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_10
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_9");
        G.initLv(15, "boat_3", "boat_6", "boat_8");
        G.initLv(10, "boat_4", "boat_5", "boat_7");
        G.target += 10;

      }

    }
    else if (wave > 45 && wave <= 50) {

      if (wave == 50) {

        G.listBoat.clear();
        G.initLv(5, "boat_9");
        G.initLv(15, "boat_3", "boat_6", "boat_8");
        G.initLv(10, "boat_4", "boat_5", "boat_7", "boat_10", "boat_11");

        Boat boat = data.HMBoat.get("boat_11").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_11
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_9");
        G.initLv(15, "boat_3", "boat_6", "boat_8");
        G.initLv(10, "boat_4", "boat_5", "boat_7", "boat_10");
        G.target += 10;

      }

    }
    else if (wave > 50 && wave <= 60) {

      if (wave == 60) {

        G.listBoat.clear();
        G.initLv(5, "boat_9", "boat_11");
        G.initLv(15, "boat_3", "boat_6", "boat_8");
        G.initLv(10, "boat_4", "boat_5", "boat_7", "boat_10");
        G.initLv(7, "boat_12");

        Boat boat = data.HMBoat.get("boat_12").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_12
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_9", "boat_11");
        G.initLv(15, "boat_3", "boat_6", "boat_8");
        G.initLv(10, "boat_4", "boat_5", "boat_7", "boat_10");
        G.target += 10;

      }

    }
    else if (wave > 60 && wave <= 66) {

      if (wave == 66) {

        G.listBoat.clear();
        G.initLv(5, "boat_4", "boat_9", "boat_11");
        G.initLv(15, "boat_6", "boat_8");
        G.initLv(10, "boat_7", "boat_10", "boat_12");
        G.initLv(7, "boat_13");

        Boat boat = data.HMBoat.get("boat_13").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_13
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_4", "boat_9", "boat_11");
        G.initLv(15, "boat_6", "boat_8");
        G.initLv(10, "boat_7", "boat_10", "boat_12");
        G.target += 10;

      }

    }
    else if (wave > 66 && wave <= 75) {

      if (wave == 75) {

        G.listBoat.clear();
        G.initLv(5, "boat_9", "boat_11", "boat_13");
        G.initLv(15, "boat_6", "boat_8");
        G.initLv(10, "boat_7", "boat_10", "boat_12");
        G.initLv(7, "boat_14");

        Boat boat = data.HMBoat.get("boat_14").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_14
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_9", "boat_11", "boat_13");
        G.initLv(15, "boat_6", "boat_8");
        G.initLv(10, "boat_7", "boat_10", "boat_12");
        G.target += 10;

      }

    }
    else if (wave > 75 && wave <= 80) {

      if (wave == 80) {

        G.listBoat.clear();
        G.initLv(5, "boat_9", "boat_11", "boat_13");
        G.initLv(15, "boat_8", "boat_7");
        G.initLv(10, "boat_10", "boat_12", "boat_14");
        G.initLv(7, "boat_15");

        Boat boat = data.HMBoat.get("boat_15").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_15
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_9", "boat_11", "boat_13");
        G.initLv(15, "boat_8", "boat_7");
        G.initLv(10, "boat_10", "boat_12", "boat_14");
        G.target += 10;

      }

    }
    else if (wave > 80 && wave <= 85) {

      if (wave == 85) {

        G.listBoat.clear();
        G.initLv(5, "boat_10", "boat_11", "boat_13", "boat_16");
        G.initLv(15, "boat_8", "boat_9");
        G.initLv(10, "boat_12", "boat_14", "boat_15");

        Boat boat = data.HMBoat.get("boat_16").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_16
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_10", "boat_11", "boat_13");
        G.initLv(15, "boat_8", "boat_9");
        G.initLv(10, "boat_12", "boat_14", "boat_15");
        G.target += 10;

      }

    }
    else if (wave > 85 && wave <= 90) {

      if (wave == 90) {

        G.listBoat.clear();
        G.initLv(5, "boat_11", "boat_13", "boat_16", "boat_17");
        G.initLv(15, "boat_8", "boat_9", "boat_10");
        G.initLv(10, "boat_12", "boat_14", "boat_15");

        Boat boat = data.HMBoat.get("boat_17").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_17
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_11", "boat_13", "boat_16");
        G.initLv(15, "boat_8", "boat_9", "boat_10");
        G.initLv(10, "boat_12", "boat_14", "boat_15");
        G.target += 10;

      }

    }
    else if (wave > 90 && wave <= 100) {

      if (wave == 100) {

        G.listBoat.clear();
        G.initLv(5, "boat_11", "boat_13", "boat_16");
        G.initLv(15, "boat_8", "boat_9", "boat_10", "boat_17");
        G.initLv(10, "boat_12", "boat_14", "boat_15", "boat_18");

        Boat boat = data.HMBoat.get("boat_18").get(0);
        G.gamePlayUI.showGUnlockWeaponOrBoat(boat);

      } //unlock boat_18
      else {

        G.listBoat.clear();
        G.initLv(5, "boat_11", "boat_13", "boat_16");
        G.initLv(15, "boat_8", "boat_9", "boat_10", "boat_17");
        G.initLv(10, "boat_12", "boat_14", "boat_15");
        G.target += 10;

      }

    }
    else {

      G.listBoat.clear();
      G.initLv(5, "boat_11", "boat_13", "boat_16");
      G.initLv(15, "boat_8", "boat_9", "boat_10", "boat_17");
      G.initLv(10, "boat_12", "boat_14", "boat_15", "boat_18");

      if (G.target < 300)
        G.target += 10;
      else
        G.target = 300;
    }

  }

  public ItemShop getItemShop(int idCannon) {

    for (ItemShop item : G.gamePlayUI.listItemShop)
      if (item.getIdCannon() == idCannon)
        return item;
    return null;

  }

  public String getStringNewWeapon(String s) {

    String ss = "";

    switch (s) {

      case "cannon_1": ss = C.lang.cannon_1; break;
      case "cannon_2": ss = C.lang.cannon_2; break;
      case "cannon_3": ss = C.lang.cannon_3; break;
      case "cannon_4": ss = C.lang.cannon_4; break;
      case "cannon_5": ss = C.lang.cannon_5; break;
      case "cannon_6": ss = C.lang.cannon_6; break;
      case "cannon_7": ss = C.lang.cannon_7; break;
      case "cannon_8": ss = C.lang.cannon_8; break;
      case "cannon_9": ss = C.lang.cannon_9; break;
      case "cannon_10": ss = C.lang.cannon_10; break;
      case "cannon_11": ss = C.lang.cannon_11; break;
      case "cannon_12": ss = C.lang.cannon_12; break;
      case "cannon_13": ss = C.lang.cannon_13; break;
      case "cannon_14": ss = C.lang.cannon_14; break;
      case "cannon_15": ss = C.lang.cannon_15; break;
      case "cannon_16": ss = C.lang.cannon_16; break;
      case "cannon_17": ss = C.lang.cannon_17; break;
      case "cannon_18": ss = C.lang.cannon_18; break;
      case "cannon_19": ss = C.lang.cannon_19; break;
      case "cannon_20": ss = C.lang.cannon_20; break;
      case "cannon_21": ss = C.lang.cannon_21; break;
      case "cannon_22": ss = C.lang.cannon_22; break;
      case "cannon_23": ss = C.lang.cannon_23; break;
      case "cannon_24": ss = C.lang.cannon_24; break;
      case "cannon_25": ss = C.lang.cannon_25; break;
      case "cannon_26": ss = C.lang.cannon_26; break;
      case "cannon_27": ss = C.lang.cannon_27; break;
      case "cannon_28": ss = C.lang.cannon_28; break;
      case "cannon_29": ss = C.lang.cannon_29; break;
      case "cannon_30": ss = C.lang.cannon_30; break;
      case "cannon_31": ss = C.lang.cannon_31; break;
      case "cannon_32": ss = C.lang.cannon_32; break;
      case "cannon_33": ss = C.lang.cannon_33; break;

    }

    return ss;

  }

  public String getStringNewBoat(String s) {

    String ss = "";

    switch (s) {

      case "boat_1": ss = C.lang.boat_1; break;
      case "boat_2": ss = C.lang.boat_2; break;
      case "boat_3": ss = C.lang.boat_3; break;
      case "boat_4": ss = C.lang.boat_4; break;
      case "boat_5": ss = C.lang.boat_5; break;
      case "boat_6": ss = C.lang.boat_6; break;
      case "boat_7": ss = C.lang.boat_7; break;
      case "boat_8": ss = C.lang.boat_8; break;
      case "boat_9": ss = C.lang.boat_9; break;
      case "boat_10": ss = C.lang.boat_10; break;
      case "boat_11": ss = C.lang.boat_11; break;
      case "boat_12": ss = C.lang.boat_12; break;
      case "boat_13": ss = C.lang.boat_13; break;
      case "boat_14": ss = C.lang.boat_14; break;
      case "boat_15": ss = C.lang.boat_15; break;
      case "boat_16": ss = C.lang.boat_16; break;
      case "boat_17": ss = C.lang.boat_17; break;
      case "boat_18": ss = C.lang.boat_18; break;

    }

    return ss;

  }

}

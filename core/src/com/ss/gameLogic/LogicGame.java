package com.ss.gameLogic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.data.Data;
import com.ss.gameLogic.effect.EffectGame;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;
import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;

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

        Weapon weTemp = pTo.getWeapon();
        pTo.setWeapon(pFrom.getWeapon());
        pFrom.setWeapon(weTemp);

        pFrom.getWeapon().setPosWeapon(pFrom.pos);
        pTo.getWeapon().setPosWeapon(pTo.pos);

      }
      else { //idCannon equal => weapon level up and update weapon at vTo, release vFrome

        //todo: particle merge weapon

        Vector2 pos = getPosByIdCannon(pTo.pos, pTo.getWeapon()); //get pos to make effect merge weapon
        Weapon w1 = pFrom.getWeapon();
        Weapon w2 = pTo.getWeapon();

        Runnable onComplete = () -> {

          pFrom.getWeapon().removeWeapon();
          pFrom.setWeapon(null);
          pFrom.setEmpty(true);

          int idCannon = pTo.getWeapon().getIdCannon() + 1;
          Weapon weapon = updateWeapon(idCannon);

          pTo.getWeapon().removeWeapon();
          weapon.isOn = true;
          pTo.setWeapon(weapon);

          pTo.getWeapon().addBulletToScene();
          pTo.getWeapon().addCannonToScene();
          pTo.getWeapon().setPosWeapon(pTo.pos);

        };

        effectGame.eftMergeWeapon(pos, w1, w2, onComplete);

      }

    }
    catch (Exception ex) {  } //error

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

    for (Weapon w : data.HMWeapon.get("cannon_" + idCannon))
      if (!w.isOn)
        weapon = w;

    return weapon;
  }

  public boolean chkWinGame(List<Boat> boats) {

    for (Boat boat : boats)
      if (boat.isAlive)
        return false;
    return true;

  }

  private Vector2 getPosByIdCannon(Vector2 pos, Weapon weapon) {

    Vector2 tempPos = new Vector2();

    switch (weapon.getIdCannon()) {

      case 0: case 1: case 2: case 3: case 20:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 25;
        tempPos.y = pos.y - 20;
        break;
      case 4:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 + 5;
        tempPos.y = pos.y - 60;
        break;
      case 5:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2;
        tempPos.y = pos.y - 60;
        break;
      case 6:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 10;
        tempPos.y = pos.y - 40;
        break;
      case 7:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 25;
        tempPos.y = pos.y - 40;
        break;
      case 8:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 20;
        tempPos.y = pos.y - 40;
        break;
      case 9:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 35;
        tempPos.y = pos.y - 30;
        break;
      case 10:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 25;
        tempPos.y = pos.y - 30;
        break;
      case 11:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 23;
        tempPos.y = pos.y - 30;
        break;
      case 12: case 13:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 30;
        tempPos.y = pos.y - 25;
        break;
      case 14:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 45;
        tempPos.y = pos.y;
        break;
      case 15:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 45;
        tempPos.y = pos.y - 15;
        break;
      case 16:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 25;
        tempPos.y = pos.y - 50;
        break;
      case 17: case 18:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 23;
        tempPos.y = pos.y - 50;
        break;
      case 19:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 25;
        tempPos.y = pos.y - 35;
        break;
      case 21:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 28;
        tempPos.y = pos.y - 15;
        break;
      case 22:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 20;
        tempPos.y = pos.y - 15;
        break;
      case 23:
        tempPos.x = pos.x + weapon.getCannon().getWidth()/2 - 23;
        tempPos.y = pos.y - 15;
        break;

    }

    return tempPos;

  }

}

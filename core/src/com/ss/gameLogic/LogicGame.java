package com.ss.gameLogic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;

import java.util.List;

public class LogicGame {

  private static LogicGame instance;
  private Game G;

  public static LogicGame getInstance(Game G) {
    return instance == null ? instance = new LogicGame(G) : instance;
  }

  private LogicGame(Game G) {
    this.G = G;
  }

  private PosOfWeapon getPosWeaponAtV(Vector2 v, List<PosOfWeapon> list) {
    for (int i=0; i<list.size(); i++)
      if (list.get(i).pos == v)
        return list.get(i);
    return null;
  }

  private void chkIdCannonAndMerge(Vector2 vFrom, Vector2 vTo, List<PosOfWeapon> list) {

    PosOfWeapon pFrom = getPosWeaponAtV(vFrom, list);
    PosOfWeapon pTo = getPosWeaponAtV(vTo, list);

    try {
      if (pFrom.getWeapon().getIdCannon() != pTo.getWeapon().getIdCannon()) {

        Weapon weTemp = pTo.getWeapon();
        pTo.setWeapon(pFrom.getWeapon());
        pFrom.setWeapon(weTemp);

        pFrom.getWeapon().setPosWeapon(pFrom.pos);
        pTo.getWeapon().setPosWeapon(pTo.pos);

      }
      else { //idCannon equal => weapon level up and update weapon at vTo, release vFrome

      }

    }
    catch (Exception ex) {  } //error

  }

  public void chkMergeWeapon(Vector2 vFrom, Vector2 vTo, List<PosOfWeapon> list) {
    PosOfWeapon pTo = getPosWeaponAtV(vTo, list);
    PosOfWeapon pFrom = getPosWeaponAtV(vFrom, list);

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

  public boolean chkCollision(Image bullet, Image boat) {
    return bullet.getY() < boat.getY()+boat.getHeight();
  }
}

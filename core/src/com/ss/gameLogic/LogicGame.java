package com.ss.gameLogic;

import com.badlogic.gdx.math.Vector2;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;

import java.util.List;

public class LogicGame {

  private static LogicGame instance;

  public static LogicGame getInstance() {
    return instance == null ? instance = new LogicGame() : instance;
  }

  private PosOfWeapon getPosWeaponAtV(Vector2 v, List<PosOfWeapon> list) {
    for (int i=0; i<list.size(); i++)
      if (list.get(i).pos == v)
        return list.get(i);
    return null;
  }

  private void chkIdCannonAndMerge(PosOfWeapon pFrom, PosOfWeapon pTo) {

    Weapon wFrom = pFrom.getWeapon();
    Weapon wTo = pTo.getWeapon();

    if (wFrom.getIdCannon() != wTo.getIdCannon())
      System.out.println(wFrom.getIdCannon() + "  " + wTo.getIdCannon());
  }

  public void checkMergeWeapon(Vector2 vFrom, Vector2 vTo, List<PosOfWeapon> list) {
    PosOfWeapon pTo = getPosWeaponAtV(vTo, list);
    PosOfWeapon pFrom = getPosWeaponAtV(vFrom, list);

    if (pTo != null && pFrom != null) {

      if (pTo.getIsEmpty()) {
        pTo.setEmpty(false);
        pTo.setWeapon(pFrom.getWeapon());

        pFrom.setWeapon(null);
        pFrom.setEmpty(true);
      }
      else
        chkIdCannonAndMerge(pFrom, pTo);
    }
  }
}

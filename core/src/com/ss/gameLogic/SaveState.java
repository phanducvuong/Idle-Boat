package com.ss.gameLogic;

import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.ss.GMain;
import com.ss.data.ItemShop;
import com.ss.gameLogic.objects.PosOfWeapon;
import com.ss.gameLogic.objects.Weapon;

import java.util.ArrayList;
import java.util.List;

public class SaveState {

  class TempItemShop {

    @SerializedName("idCannon")
    private int idCannon;

    @SerializedName("coin")
    private long coin;

    @SerializedName("unlock")
    private boolean unlock;

    TempItemShop(int idCannon, long coin, boolean unlock) {
      this.idCannon = idCannon;
      this.coin = coin;
      this.unlock = unlock;
    }
  }

  class weapon {

    @SerializedName("cannon")
    private String cannon;

    @SerializedName("pos")
    private int pos;

    weapon(String cannon, int pos) {
      this.cannon = cannon;
      this.pos = pos;
    }

  }

  private static SaveState instance;
  public Preferences prefs = GMain.pref;
  private Gson gson = new Gson();

  public static SaveState getInstance() {
    return instance == null ? instance = new SaveState() : instance;
  }

  public void listItemShop(String key, List<ItemShop> listShop) {

    List<TempItemShop> listTempItem = new ArrayList<>();

    for (ItemShop itemShop : listShop) {
      TempItemShop t = new TempItemShop(itemShop.getIdCannon(), itemShop.getCoin(), itemShop.isUnlock());
      listTempItem.add(t);
    }

    String json = gson.toJson(listTempItem);
    prefs.putString(key, json);
    prefs.flush();

  }

  public void listPosOfWeapon(String key, List<PosOfWeapon> listPos) {

    List<weapon> listWeapon = new ArrayList<>();

    for (PosOfWeapon pos : listPos)
      if (pos.getWeapon() != null) {
        weapon w = new weapon(pos.getWeapon().nameWeapon, listPos.indexOf(pos));
        listWeapon.add(w);
      }

    String json = gson.toJson(listWeapon);
    prefs.putString(key, json);
    prefs.flush();

  }

  public void idBestPowerCannon(String key, int value) {
    prefs.putInteger(key, value);
    prefs.flush();
  }

  public void waveAndTarget(String key, int value) {
    prefs.putInteger(key, value);
    prefs.flush();
  }

  public void coinCollection(String key, long value) {
    prefs.putLong(key, value);
    prefs.flush();
  }

}

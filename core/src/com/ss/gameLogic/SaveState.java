package com.ss.gameLogic;

import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.ss.GMain;
import com.ss.data.Data;
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
  private Preferences prefs = GMain.pref;
  private Gson gson = new Gson();
  private Data data = Data.getInstance();

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

  public void loadListItemShop(List<ItemShop> listItemShop) {

    String jsonString = prefs.getString("list_item_shop");
    JsonArray jsonArrItemShop = gson.fromJson(jsonString, JsonArray.class);

    try {

      for (JsonElement jsonE : jsonArrItemShop) {

        TempItemShop t = gson.fromJson(jsonE, TempItemShop.class);
        listItemShop.get(t.idCannon).setUnlock(t.unlock);
        listItemShop.get(t.idCannon).setCoin(t.coin);

      }

    }
    catch (Exception ex) {  }

  }

  public void loadListPosOfWeapon(List<PosOfWeapon> listPos) {

    String arrPos = prefs.getString("list_pos_weapon");
    JsonArray jsonArrPos = gson.fromJson(arrPos, JsonArray.class);

    if (jsonArrPos != null) {

      clrListPosOfWeapon(listPos);

      for (JsonElement jsonE : jsonArrPos) {

        weapon w = gson.fromJson(jsonE, weapon.class);
        Weapon weapon = data.HMWeapon.get(w.cannon).get(w.pos);
        weapon.addCannonToScene();
        weapon.setPosWeapon(listPos.get(w.pos).pos);
        weapon.addBulletToScene();
        weapon.isOn = true;

        listPos.get(w.pos).setWeapon(weapon);
        listPos.get(w.pos).setEmpty(false);

      }

    }

  }

  public int loadIdBestPowerCannon() {
    return prefs.getInteger("id_best_power_cannon");
  }

  public long loadCoinCollection() {
    return prefs.getLong("coin_collection");
  }

  public int loadWave() {
    return prefs.getInteger("wave");
  }

  public int loadTarget() {
    return prefs.getInteger("target");
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

  private void clrListPosOfWeapon(List<PosOfWeapon> listPos) {

    for (PosOfWeapon pos : listPos) {
      if (pos.getWeapon() != null) {
        pos.getWeapon().removeWeapon();
        pos.setEmpty(true);
        pos.setWeapon(null);
      }
    }

  }

}

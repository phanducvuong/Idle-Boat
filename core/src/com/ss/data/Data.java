package com.ss.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class Data {

  private static Data instance;
  private static final FileHandle fh = Gdx.files.internal("data/Weapon.json");

  public List<WeaponJson> listDataWeapon;
  public List<BoatJson> listDataBoat;

  public static Data getInstance() {
    return instance == null ? instance = new Data() : instance;
  }

  private Data() {
    listDataWeapon = new ArrayList<>();
    listDataBoat = new ArrayList<>();

    initData();
  }

  private void initData() {
    Gson gson = new Gson();

    String json = fh.readString();
    JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

    for (int i=0; i<jsonArray.size(); i++) {
      WeaponJson w = gson.fromJson(jsonArray.get(i), WeaponJson.class);
      listDataWeapon.add(w);
    }
  }
}

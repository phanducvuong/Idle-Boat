package com.ss.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.Weapon;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.List;

public class Data {

  private static Data instance;
  private static final FileHandle fhWeapon = Gdx.files.internal("data/Weapon.json");
  private static final FileHandle fhBoat = Gdx.files.internal("data/Boat.json");

  private List<WeaponJson> listDataWeapon;
  private List<BoatJson> listDataBoat;

  public List<Weapon> listWeapon;
  public List<Boat> listBoat;

  public static Data getInstance() {
    return instance == null ? instance = new Data() : instance;
  }

  private Data() {
    listDataWeapon = new ArrayList<>();
    listDataBoat = new ArrayList<>();
    listWeapon = new ArrayList<>();
    listBoat = new ArrayList<>();

    initData();
  }

  private void initData() {
    Gson gson = new Gson();

    //WEAPON
    String jsonWeapon = fhWeapon.readString();
    JsonArray jsonArrWeapon = gson.fromJson(jsonWeapon, JsonArray.class);

    for (int i=0; i<jsonArrWeapon.size(); i++) {
      WeaponJson w = gson.fromJson(jsonArrWeapon.get(i), WeaponJson.class);
      listDataWeapon.add(w);
    }

    //BOAT
    String jsonBoat = fhBoat.readString();
    JsonArray jsonArrBoat = gson.fromJson(jsonBoat, JsonArray.class);

    for (int i=0; i<jsonArrBoat.size(); i++) {
      BoatJson b = gson.fromJson(jsonArrBoat.get(i), BoatJson.class);
      listDataBoat.add(b);
    }
  }

  public void initListWeapon(Game G, Group gUI) {
    for (WeaponJson weapon : listDataWeapon) {

      String cannon = weapon.getCannon();
      String bullet = weapon.getBullet();
      float attack_bullet = weapon.getAttackBullet();
      int id_cannon = weapon.getIdCannon();
      float speed = weapon.getSpeed();

      try {
        listWeapon.add(new Weapon(G, gUI, cannon, bullet, attack_bullet, speed, id_cannon));
      }
      catch (Exception ex) {}
    }
  }

  public void initListBoat(Game G, Group gUI) {
    for (BoatJson boat : listDataBoat) {

      String imgBoat = boat.getBoat();
      int blood = boat.getBlood();
      float speed = boat.getSpeed();

      try {
        listBoat.add(new Boat(G, gUI, imgBoat, speed, blood));
      }
      catch (Exception ex) {}
    }
  }
}

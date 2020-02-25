package com.ss.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.ss.gameLogic.Game;
import com.ss.gameLogic.objects.Boat;
import com.ss.gameLogic.objects.Weapon;
import com.ss.gameLogic.ui.GamePlayUI;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data {

  private static Data instance;
  private Game G;
  private static final FileHandle fhWeapon = Gdx.files.internal("data/Weapon.json");
  private static final FileHandle fhBoat = Gdx.files.internal("data/Boat.json");

  public List<WeaponJson> listDataWeapon;
  private List<BoatJson> listDataBoat;

  public HashMap<String, List<Weapon>> HMWeapon;
  public HashMap<String, List<Boat>> HMBoat;
  public HashMap<String, List<Image>> HMMergeWeapon;

  public static Data getInstance() {
    return instance == null ? instance = new Data() : instance;
  }

  private Data() {
    listDataWeapon = new ArrayList<>();
    listDataBoat = new ArrayList<>();

    HMWeapon = new HashMap<>();
    HMBoat = new HashMap<>();
    HMMergeWeapon = new HashMap<>();

    initData();
  }

  public void setG(Game G) { this.G = G; }

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

  public void initListWeapon(Game G, GamePlayUI gamePlayUI, Group gUI) {

    for (WeaponJson weapon : listDataWeapon) {

      String cannon = weapon.getCannon();
      String bullet = weapon.getBullet();
      float attack_bullet = weapon.getAttackBullet();
      int id_cannon = weapon.getIdCannon();
      float speed = weapon.getSpeed();
      long coin = weapon.getCoin();

      List<Weapon> listWeapon = new ArrayList<>();

      for (int i=0; i<10; i++) {

        try {
          listWeapon.add(new Weapon(G, gamePlayUI, gUI, cannon, bullet, attack_bullet, speed, id_cannon, coin));
        }
        catch (Exception ex) {}

      }

      List<Image> img = new ArrayList<>();
      for (int i=0; i<2; i++) {
        Image cannonMerge = G.initWeaponMerge(cannon);
        img.add(cannonMerge);
      }
      HMMergeWeapon.put(cannon, img);

      HMWeapon.put(cannon, listWeapon);
    }
  }

  public void initListBoat(Game G, Group gUI) {

    for (BoatJson boat : listDataBoat) {

      String imgBoat = boat.getBoat();
      int blood = boat.getBlood();
      float speed = boat.getSpeed();
      int idBoat = boat.getIdBoat();
      int coin = boat.getCoin();

      List<Boat> listBoat = new ArrayList<>();

      for (int i=0; i<20; i++) {

        try {

          listBoat.add(new Boat(G, gUI, imgBoat, speed, blood, idBoat, coin));

        }
        catch (Exception ex) {}
      }

      HMBoat.put(imgBoat, listBoat);

    }
  }

}

package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Locale;
import java.util.MissingResourceException;

public class C {

    public static class remote {
        public static int adsTime = 50;
        static void initRemoteConfig() {

        }
    }

    public static class lang {
        private static I18NBundle locale;
        public static String title_shop = "";
        public static String adsTimeLbl = "";
        public static String noEnoughEmty = "";
        public static String noEnoughCoin = "";
        public static String winGame = "";
        public static String endGame = "";
        public static String newWave = "";
        public static String unlock_weapon = "";
        public static String unlock_boat = "";
        public static String damage = "";
        public static String range = "";
      public static String hitpoint = "";
      public static String speed = "";
        public static String textContinue = "";

        public static String cannon_1 = "";
        public static String cannon_2 = "";
        public static String cannon_3 = "";
        public static String cannon_4 = "";
        public static String cannon_5 = "";
        public static String cannon_6 = "";
        public static String cannon_7 = "";
        public static String cannon_8 = "";
        public static String cannon_9 = "";
        public static String cannon_10 = "";
        public static String cannon_11 = "";
        public static String cannon_12 = "";
        public static String cannon_13 = "";
        public static String cannon_14 = "";
        public static String cannon_15 = "";
        public static String cannon_16 = "";
        public static String cannon_17 = "";
        public static String cannon_18 = "";
        public static String cannon_19 = "";
        public static String cannon_20 = "";
        public static String cannon_21 = "";
        public static String cannon_22 = "";
        public static String cannon_23 = "";
        public static String cannon_24 = "";
        public static String cannon_25 = "";
        public static String cannon_26 = "";
        public static String cannon_27 = "";
        public static String cannon_28 = "";
        public static String cannon_29 = "";
        public static String cannon_30 = "";
        public static String cannon_31 = "";
        public static String cannon_32 = "";
        public static String cannon_33 = "";

        public static String boat_1 = "";
        public static String boat_2 = "";
        public static String boat_3 = "";
        public static String boat_4 = "";
        public static String boat_5 = "";
        public static String boat_6 = "";
        public static String boat_7 = "";
        public static String boat_8 = "";
        public static String boat_9 = "";
        public static String boat_10 = "";
        public static String boat_11 = "";
        public static String boat_12 = "";
        public static String boat_13 = "";
        public static String boat_14 = "";
        public static String boat_15 = "";
        public static String boat_16 = "";
        public static String boat_17 = "";
        public static String boat_18 = "";

        static void initLocalize() {
            FileHandle specFilehandle = Gdx.files.internal("i18n/lang_" + "col");
            FileHandle baseFileHandle = Gdx.files.internal("i18n/lang");

            try {
                locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
            }
            catch (MissingResourceException e) {
                locale = I18NBundle.createBundle(baseFileHandle, new Locale(""));
            }

            title_shop = locale.get("title_shop");
            adsTimeLbl = locale.format("adsTime", remote.adsTime);

            noEnoughEmty = locale.get("no_enough_empty");
            noEnoughCoin = locale.get("no_enough_coin");
            winGame = locale.get("win");
            endGame = locale.get("end");
            newWave = locale.get("new_wave");
            unlock_weapon = locale.get("unlock_weapon");
            unlock_boat = locale.get("unlock_boat");
            damage = locale.get("damage");
            range = locale.get("range");
            hitpoint = locale.get("hitpoint");
            speed = locale.get("speed");
            textContinue = locale.get("continue");

            cannon_1 = locale.get("cannon_1");
            cannon_2 = locale.get("cannon_2");
            cannon_3 = locale.get("cannon_3");
            cannon_4 = locale.get("cannon_4");
            cannon_5 = locale.get("cannon_5");
            cannon_6 = locale.get("cannon_6");
            cannon_7 = locale.get("cannon_7");
            cannon_8 = locale.get("cannon_8");
            cannon_9 = locale.get("cannon_9");
            cannon_10 = locale.get("cannon_10");
            cannon_11 = locale.get("cannon_11");
            cannon_12 = locale.get("cannon_12");
            cannon_13 = locale.get("cannon_13");
            cannon_14 = locale.get("cannon_14");
            cannon_15 = locale.get("cannon_15");
            cannon_16 = locale.get("cannon_16");
            cannon_17 = locale.get("cannon_17");
            cannon_18 = locale.get("cannon_18");
            cannon_19 = locale.get("cannon_19");
            cannon_20 = locale.get("cannon_20");
            cannon_21 = locale.get("cannon_21");
            cannon_22 = locale.get("cannon_22");
            cannon_23 = locale.get("cannon_23");
            cannon_24 = locale.get("cannon_24");
            cannon_25 = locale.get("cannon_25");
            cannon_26 = locale.get("cannon_26");
            cannon_27 = locale.get("cannon_27");
            cannon_28 = locale.get("cannon_28");
            cannon_29 = locale.get("cannon_29");
            cannon_30 = locale.get("cannon_30");
            cannon_31 = locale.get("cannon_31");
            cannon_32 = locale.get("cannon_32");
            cannon_33 = locale.get("cannon_33");

            boat_1 = locale.get("boat_1");
            boat_2 = locale.get("boat_2");
            boat_3 = locale.get("boat_3");
            boat_4 = locale.get("boat_4");
            boat_5 = locale.get("boat_5");
            boat_6 = locale.get("boat_6");
            boat_7 = locale.get("boat_7");
            boat_8 = locale.get("boat_8");
            boat_9 = locale.get("boat_9");
            boat_10 = locale.get("boat_10");
            boat_11 = locale.get("boat_11");
            boat_12 = locale.get("boat_12");
            boat_13 = locale.get("boat_13");
            boat_14 = locale.get("boat_14");
            boat_15 = locale.get("boat_15");
            boat_16 = locale.get("boat_16");
            boat_17 = locale.get("boat_17");
            boat_18 = locale.get("boat_18");

        }
    }

    public static void init() {
        remote.initRemoteConfig();
        lang.initLocalize();
    }
}

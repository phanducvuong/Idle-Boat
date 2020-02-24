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
        public static String title = "";
        public static String adsTimeLbl = "";
        public static String noEnoughEmty = "";
        public static String noEnoughCoin = "";
        public static String winGame = "";
        public static String endGame = "";
        public static String newWave = "";
        public static String unlock = "";
        public static String damage = "";
        public static String range = "";
        public static String textContinue = "";

        public static String cannon_1 = "";

        static void initLocalize() {
            FileHandle specFilehandle = Gdx.files.internal("i18n/lang_" + "col");
            FileHandle baseFileHandle = Gdx.files.internal("i18n/lang");

            try {
                locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
            }
            catch (MissingResourceException e) {
                locale = I18NBundle.createBundle(baseFileHandle, new Locale(""));
            }

            title = locale.get("title");
            adsTimeLbl = locale.format("adsTime", remote.adsTime);

            noEnoughEmty = locale.get("no_enough_empty");
            noEnoughCoin = locale.get("no_enough_coin");
            winGame = locale.get("win");
            endGame = locale.get("end");
            newWave = locale.get("new_wave");
            unlock = locale.get("unlock");
            damage = locale.get("damage");
            range = locale.get("range");
            textContinue = locale.get("continue");

            cannon_1 = locale.get("cannon_1");

        }
    }

    public static void init() {
        remote.initRemoteConfig();
        lang.initLocalize();
    }
}

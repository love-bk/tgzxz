package com.tianguo.zxz.uctils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    public static final String CONFIG = "config";
    private static final String EAST = "east";
    private static final String INSTALL = "install";
    private static String TIMES = "tiems";
    /**
     * ticket
     */
    public static final String TOKEN = "token";
    public static final String SSO = "sso";
    static String ID = "id";
    static String NICK = "name";
    static String HEDIMGE = "headimag";
    static String USERID = "useid";
    static String V4IP = "ip";
    static String onlyID = "onlyid";
    static String ReSou = "resou";
    static String SouSuo = "sousuo";
    static String Feed = "feed";
    static String Feeder = "feeder";
    static String NEWUSERTYPE = "newusertype";
    static String NEWOTHERTYPE = "newothertype";
    private static SharedPreferences sharedPreferences;
    static String IsNew = "isNew";
    private static String newbieGuide = "NewbieGuide";

    public static void saveReSou(Context context, int value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(ReSou, value).commit();
    }

    public static int getReSou(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(ReSou, 1);
    }

    public static void saveSouSuo(Context context, int value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(SouSuo, value).commit();
    }

    public static int getSouSuo(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(SouSuo, 1);
    }

    public static void saveFeed(Context context, int value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(Feed, value).commit();
    }

    public static int getFeed(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(Feed, 0);
    }

    public static void saveFeeder(Context context, int value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(Feeder, value).commit();
    }

    public static int getFeeder(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(Feeder, 1);
    }

    public static void saveolyID(Context context, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(onlyID, value).commit();
    }

    public static String getOnlyID(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(onlyID, "");
    }

    public static void saveTicketInfo(Context context, long value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putLong(TOKEN, value).commit();
    }

    public static void saveV4IP(Context context, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(V4IP, value).commit();
    }

    public static String getV4IP(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(V4IP, "");
    }

    public static void saveSSO(Context context, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(SSO, value).commit();
    }

    public static String getSSo(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(SSO, "");
    }

    public static void saveID(Context context, long value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putLong(ID, value).commit();
    }

    public static long getID(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getLong(ID, 0);
    }

    public static void saveUseid(Context context, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(USERID, value).commit();
    }

    public static String getUseid(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(USERID, "");
    }

    public static void saveTimes(Context main, String time) {
        if (sharedPreferences == null) {
            sharedPreferences = main.getSharedPreferences(TIMES, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(TIMES, time).commit();

    }

    public static String getTimes(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(TIMES, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(TIMES, "");
    }


    public static void saveSwitch(Context context, String key, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getSwitch(Context context, String key) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, null);
    }


    public static void removeSwitch(Context context, String key) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().remove(key).apply();
    }


    public static String getValue(Context context, String key) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, null);
    }

    public static boolean getAlarmClockSwitch(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(Constant.ISCHECKED, false);
    }

    public void saveAlarmClockSwitch(Context context, boolean is) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(Constant.ISCHECKED, is).commit();
    }

    public static void saveNewUserType(Context context, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(NEWUSERTYPE, value).commit();
    }

    public static String getNewUserType(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(NEWUSERTYPE, null);
    }

    public static void saveNewOtherType(Context context, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(NEWOTHERTYPE, value).commit();
    }

    public static String getNewOtherType(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(NEWOTHERTYPE, null);
    }

    public static void saveEast(Context context, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(EAST, value).commit();
    }

    public static String getEast(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(EAST, null);
    }

    public static void saveInstall(Context context,boolean value){
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(INSTALL, value).commit();
    }
    public static boolean getInstall(Context context){
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(INSTALL, true);
    }

    public static void saveIsNew(Context context, boolean value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(IsNew, value).commit();
    }

    public static boolean getIsNew(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(IsNew,false);
    }

    public static void saveNewbieGuide(Context context, boolean value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(newbieGuide, value).commit();
    }

    public static boolean getNewbieGuide(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(newbieGuide,true);
    }

    public static long getDownId(Context context, String url){
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getLong(url,-1);
    }
    public static void removeDownId(Context context, String url){
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().remove(url);
    }

    public static void saveDownId(Context context, String url, long downId) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putLong(url, downId).commit();
    }
}

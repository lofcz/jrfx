package com.company;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *  Class Lang
 *  Provides localized language lumps
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Lang  {
    private static ArrayList<LangLump> lumps = new ArrayList<LangLump>();
    public static Controller controller;

    public static void setController(Controller c) {
        controller = c;
    }

    public static void ReadLumps(String lang) {
        ClassLoader classLoader = Lang.class.getClassLoader();
        Type type = new TypeToken<ArrayList<LangLump>>(){}.getType();
        InputStream sss = controller.loadResource(lang);

        String allText = Utils.readStream(sss);
        Gson gson = new Gson();
        lumps = gson.fromJson(allText, type);
    }

    public static String getLangLump(String section, String key) {
        LangLump l = lumps.stream().filter(x -> x.value.equals(section)).findFirst().orElse(null);

        if (l == null) {
            return "[Unknown lang lump section]";
        }

        return l.keys.getOrDefault(key, "[Unknown lang lump key in section" + section + "]");
    }
}

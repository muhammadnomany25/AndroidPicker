package com.alphaapps.androidpickerapp.data.shared_prefs;

import android.content.SharedPreferences;

/**
 * @usage: The shared preferences class handler for the app
 * <p>
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */
public class UserSaver {
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private final String Preferences_LANGUAGE = "lang";

    public UserSaver(SharedPreferences preferences, SharedPreferences.Editor editor) {
        this.preferences = preferences;
        this.editor = editor;
    }

    public boolean isArabic() {
        return preferences.getString(Preferences_LANGUAGE, "ar").equals("ar");
    }

    public void setAppLanguage(String lang) {
        editor.putString(Preferences_LANGUAGE, lang).apply();
    }
}

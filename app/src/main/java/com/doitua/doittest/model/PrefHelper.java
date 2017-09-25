package com.doitua.doittest.model;

import android.content.SharedPreferences;

import com.doitua.doittest.model.token.ITokenHelper;
import com.doitua.doittest.model.user.IUserAvatarHelper;

/**
 * Created by batynchuk on 9/21/17.
 */

public class PrefHelper implements ITokenHelper, IUserAvatarHelper {

    private static final String TOKEN_PREF_KEY = "token_pref_key";
    private static final String AVATAR_PREF_KEY = "avatar_pref_key";
    private SharedPreferences sharedPreferences;

    public PrefHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void setToken(String token) {
        sharedPreferences.edit().putString(TOKEN_PREF_KEY, token).apply();
    }

    @Override
    public String getToken() {
        return sharedPreferences.getString(TOKEN_PREF_KEY, "");
    }


    @Override
    public void setAvatarUrl(String url) {
        sharedPreferences.edit().putString(AVATAR_PREF_KEY, url).apply();
    }

    @Override
    public String getAvatarUrl() {
        return sharedPreferences.getString(AVATAR_PREF_KEY, "");
    }
}

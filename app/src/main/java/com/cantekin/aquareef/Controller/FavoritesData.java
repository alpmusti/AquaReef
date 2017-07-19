package com.cantekin.aquareef.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cantekin on 19.7.2017.
 */

public class FavoritesData {
    public FavoritesData() {
    }

    public Map<String, Data> getFavorites() {
        Map<String, Data> favorites = new HashMap<>();
        Data blue = new Data();
        blue.setBlue(100);
        favorites.put("Blue", blue);

        Data allBlue = new Data();
        allBlue.setBlue(100);
        allBlue.setRoyalBlue(100);
        favorites.put("All Blues", allBlue);

        Data wihteRoyal = new Data();
        wihteRoyal.setBlue(100);
        wihteRoyal.setWhite(100);
        favorites.put("White & Royal", wihteRoyal);


        Data White = new Data();
        White.setWhite(100);
        favorites.put("White", White);


        Data WhiteRed = new Data();
        WhiteRed.setRed(100);
        WhiteRed.setWhite(100);
        favorites.put("White & Red", WhiteRed);


        Data Red = new Data();
        Red.setRed(100);
        favorites.put("Red", Red);

        return favorites;
    }

}

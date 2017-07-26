package com.cantekin.aquareef.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cantekin on 19.7.2017.
 */

public class DefaultData {
    public DefaultData() {
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

    public Schedule getSchedule() {
        Schedule sch = new Schedule();
        sch.setName("Default");
        List<DataSchedule> result = new ArrayList<>();
        DataSchedule red = new DataSchedule("Red", 'a', "#ef473a", "11:00", "12:00", "17:00", "18:00", 50);
        DataSchedule green = new DataSchedule("Green", 'b', "#33cd5f", "11:00", "12:00", "17:00", "18:00", 40);
        DataSchedule rBlue = new DataSchedule("Royal Blue", 'c', "#11c1f3", "09:00", "10:30", "20:30", "22:00", 100);
        DataSchedule blue = new DataSchedule("Blue", 'd', "#387ef5", "09:30", "11:00", "20:00", "21:30", 100);
        DataSchedule white = new DataSchedule("White", 'e', "#b2b2b2", "10:00", "11:00", "17:00", "19:00", 75);
        DataSchedule dWhite = new DataSchedule("D.White", 'f', "#ffc900", "10:30", "11:30", "17:00", "19:00", 60);
        DataSchedule uv = new DataSchedule("UV", 'g', "#886aea", "10:30", "11:30", "18:00", "20:00", 80);
        DataSchedule moon = new DataSchedule("Moon", 'h', "#040404", "01:00", "00:00", "00:00", "07:00", 0);

        result.add(red);
        result.add(green);
        result.add(rBlue);
        result.add(blue);
        result.add(white);
        result.add(dWhite);
        result.add(uv);
        result.add(moon);
        sch.setData(result);
        return sch;
    }

    public Map<String, List<DataSchedule>> getScheduleFavorites() {
        Map<String, List<DataSchedule>> resultList = new HashMap<>();
        List<DataSchedule> item = new ArrayList<>();
        DataSchedule red = new DataSchedule("Red", 'a', "#ef473a", "10:00", "12:00", "17:00", "18:00", 30);
        DataSchedule green = new DataSchedule("Green", 'b', "#33cd5f", "10:30", "12:00", "17:00", "18:00", 30);
        DataSchedule rBlue = new DataSchedule("Royal Blue", 'c', "#11c1f3", "09:30", "10:30", "20:00", "21:30", 100);
        DataSchedule blue = new DataSchedule("Blue", 'd', "#387ef5", "09:30", "10:30", "20:30", "22:00", 100);
        DataSchedule white = new DataSchedule("White", 'e', "#b2b2b2", "10:30", "12:00", "17:00", "19:30", 50);
        DataSchedule dWhite = new DataSchedule("D.White", 'f', "#ffc900", "11:00", "12:00", "17:00", "18:30", 50);
        DataSchedule uv = new DataSchedule("UV", 'g', "#886aea", "10:30", "11:30", "18:30", "20:00", 50);
        DataSchedule moon = new DataSchedule("Moon", 'h', "#040404", "01:00", "00:00", "00:00", "07:00", 5);
        item.add(red);
        item.add(green);
        item.add(rBlue);
        item.add(blue);
        item.add(white);
        item.add(dWhite);
        item.add(uv);
        item.add(moon);
        resultList.put("SPS Sert Mercan", item);
        //Marine
        item = new ArrayList<>();
        red = new DataSchedule("Red", 'a', "#ef473a", "11:00", "12:00", "17:00", "18:00", 20);
        green = new DataSchedule("Green", 'b', "#33cd5f", "11:00", "12:00", "17:00", "18:00", 20);
        rBlue = new DataSchedule("Royal Blue", 'c', "#11c1f3", "09:30", "10:30", "20:00", "21:30", 100);
        blue = new DataSchedule("Blue", 'd', "#387ef5", "09:30", "10:30", "20:30", "22:00", 100);
        white = new DataSchedule("White", 'e', "#b2b2b2", "10:30", "12:00", "17:00", "18:30", 60);
        dWhite = new DataSchedule("D.White", 'f', "#ffc900", "11:00", "12:00", "16:00", "17:30", 40);
        uv = new DataSchedule("UV", 'g', "#886aea", "10:30", "11:30", "18:30", "20:00", 40);
        moon = new DataSchedule("Moon", 'h', "#040404", "01:00", "00:00", "00:00", "07:00", 5);
        item.add(red);
        item.add(green);
        item.add(rBlue);
        item.add(blue);
        item.add(white);
        item.add(dWhite);
        item.add(uv);
        item.add(moon);
        resultList.put("Marine", item);

        //LPS+SPS Mix
        item = new ArrayList<>();
        red = new DataSchedule("Red", 'a', "#ef473a", "10:30", "12:00", "17:00", "18:00", 70);
        green = new DataSchedule("Green", 'b', "#33cd5f", "11:00", "12:00", "17:00", "18:00", 40);
        rBlue = new DataSchedule("Royal Blue", 'c', "#11c1f3", "09:30", "10:30", "20:00", "21:30", 100);
        blue = new DataSchedule("Blue", 'd', "#387ef5", "09:30", "10:30", "20:30", "22:00", 100);
        white = new DataSchedule("White", 'e', "#b2b2b2", "10:30", "12:00", "17:00", "18:30", 80);
        dWhite = new DataSchedule("D.White", 'f', "#ffc900", "11:00", "12:00", "16:00", "17:30", 60);
        uv = new DataSchedule("UV", 'g', "#886aea", "10:30", "11:30", "18:30", "20:00", 80);
        moon = new DataSchedule("Moon", 'h', "#040404", "01:00", "00:00", "00:00", "07:00", 5);
        item.add(red);
        item.add(green);
        item.add(rBlue);
        item.add(blue);
        item.add(white);
        item.add(dWhite);
        item.add(uv);
        item.add(moon);
        resultList.put("LPS+SPS Mix", item);

        //Erkan Akvaryum
        item = new ArrayList<>();
        red = new DataSchedule("Red", 'a', "#ef473a", "11:30", "12:00", "17:00", "18:00", 25);
        green = new DataSchedule("Green", 'b', "#33cd5f", "11:00", "12:00", "17:00", "18:00", 25);
        rBlue = new DataSchedule("Royal Blue", 'c', "#11c1f3", "09:30", "10:30", "20:00", "21:30", 25);
        blue = new DataSchedule("Blue", 'd', "#387ef5", "09:30", "10:30", "20:30", "22:00", 25);
        white = new DataSchedule("White", 'e', "#b2b2b2", "10:30", "12:00", "17:00", "18:30", 25);
        dWhite = new DataSchedule("D.White", 'f', "#ffc900", "11:00", "12:00", "16:00", "17:30", 25);
        uv = new DataSchedule("UV", 'g', "#886aea", "11:00", "12:00", "16:00", "17:30", 80);
        moon = new DataSchedule("Moon", 'h', "#040404", "01:00", "00:00", "00:00", "07:00", 5);
        item.add(red);
        item.add(green);
        item.add(rBlue);
        item.add(blue);
        item.add(white);
        item.add(dWhite);
        item.add(uv);
        item.add(moon);
        resultList.put("Erkan Akvaryum", item);

        return resultList;
    }
}

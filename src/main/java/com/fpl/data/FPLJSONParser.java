package com.fpl.data;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/*
TODO
1) Move the getStandingsData into a separate class - can add more routines to the class later to get other data
2) Integrate with the Google Sheets API to start writing to the sheet directly instead of copy pasting the output
3) Set up the metadata to map team ids(NOT NAMES!) to individual people.
4) Ignore Anuj's team - Delete the record from the standings data before writing to the Google Sheet
 */


public class FPLJSONParser
{
    public static void main(String[] args){
        runJSONParser();
    }

    private static void runJSONParser()
    {
        //Read from the URL later
        String fileData = getStandingsData();
        JsonObject fplData = new JsonParser().parse(fileData).getAsJsonObject();
        JsonArray results = fplData.getAsJsonObject("standings").getAsJsonArray("results");

        for(int n_peep =0; n_peep<results.size();n_peep++)
        {
            JsonObject currPeep = (JsonObject) results.get(n_peep);
            System.out.println(
                        currPeep.get("player_name") + "," +
                        currPeep.get("entry_name") + "," +
                        currPeep.get("total"));
        }

    }

    private static String getStandingsData() {
        try{
            URL website = new URL("https://fantasy.premierleague.com/drf/leagues-classic-standings/129881");
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

            return response.toString();

        }
        catch(Exception e)
        {
            return null;
        }
    }

}

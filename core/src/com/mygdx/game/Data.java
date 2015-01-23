package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by licheng5625 on 15-1-22.
 */

//this is a global data
public class Data {
    public static ArrayList<User> UsersDatas;
    public static User CurrentUser;
    public static void Init()
    {
        UsersDatas=new  ArrayList<User>();
    }
    public static void addUser(User user){
        UsersDatas.add(user);
    }
    public static void LoadUsers()
    {
        //read the josn file
        FileHandle file = Gdx.files.internal("Data.json");
        String Jsontext = file.readString();
        //decode the josn file
        try {
            //get the root node
            JSONTokener jsonParser = new JSONTokener(Jsontext);
            JSONObject JsonRoot = (JSONObject) jsonParser.nextValue();
            JSONArray Userlist=JsonRoot.getJSONArray("Users");
            //get the each user
            for(int i = 0; i < Userlist.length() ; i++) {
                JSONObject Userdata = Userlist.getJSONObject(i);
                ArrayList<String> tempWords= new ArrayList<String>();
                // get the learned words of each user
                JSONArray words=Userdata.getJSONArray("Wordlist");
                for (int j = 0; j < words.length() ; j++) {
                    String word = words.getString(j);
                    tempWords.add(word);
                }
                User tempuser=new User( Userdata.getString("Name"),tempWords);
                //add to the global stucture data
                 addUser(tempuser);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static User GetUser(String name)
    {
        for(int i=0;i<UsersDatas.size();i++)
        {
            if (UsersDatas.get(i).getName()==name)
            {
                return UsersDatas.get(i);
            }
        }
        return null;
    }
}
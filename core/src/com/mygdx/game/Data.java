package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Created by licheng5625 on 15-1-22.
 */

//this is a global data
public class Data {
    public static ArrayList<User> UsersDatas;
    public static User CurrentUser;
    private static String filename;

    public static void Init()
    {
        UsersDatas=new  ArrayList<User>();
        filename="Data.json";
    }

    public static void addUser(User user){
        if(GetUser(user.getName())==null) {
            UsersDatas.add(user);
            Collections.sort(UsersDatas);
        }
    }

    public static void LoadUsers()
    {
        //read the josn file
        FileHandle file = Gdx.files.internal(filename);
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
                User tempuser=new User( Userdata.getString("Name"),tempWords,Userdata.getBoolean("Sound"),Userdata.getBoolean("Music"));
                //add to the global stucture data
                 addUser(tempuser);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(UsersDatas);
        CurrentUser=UsersDatas.get(0);
    }
    public static void SaveData()
    {
        String Jsontext;
        JSONObject userdata = new JSONObject();
        JSONArray userlist = new JSONArray();
        try {
            userdata.put("Users",userlist);
            for(int i = 0; i < UsersDatas.size() ; i++) {
                JSONObject user = new JSONObject();
                User temp=UsersDatas.get(i);
                user.put("Name", temp.getName());
                JSONArray wordslist = new JSONArray();
                for(int j = 0; j < temp.getWordlist().size() ; j++) {
                    wordslist.put(temp.getWordlist().get(j));
                }
                user.put("Sound", temp.getSound());
                user.put("Music", temp.getMusik());
                user.put("Wordlist",wordslist);
                userlist.put(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Jsontext=userdata.toString();
        FileHandle file = Gdx.files.local(filename);
        file.writeString(Jsontext, false);
    }

    public static User GetUser(String name)
    {
        for(int i=0;i<UsersDatas.size();i++)
        {
            if (name.equals(UsersDatas.get(i).getName()))
            {
                return UsersDatas.get(i);
            }
        }
        return null;
    }



}
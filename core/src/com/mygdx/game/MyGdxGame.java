package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.json.*;
import java.util.ArrayList;

public class MyGdxGame extends Game {

	@Override
	public void create() {
		// TODO Auto-generated method stub
        Data.Init();
        Data.LoadUsers();
        this.setScreen(new MainScreen(this));
	}
    private void LoadUsers()
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
                Data.addUser(tempuser);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

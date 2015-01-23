package com.mygdx.game;


import java.util.ArrayList;

/**
 * Created by licheng5625 on 15-1-22.
 */
public class User {
    private String Name;
    private ArrayList<String> Wordlists;
    //init User with words list
    User(String Name,ArrayList<String> Wordlists)
    {
        this.Name=Name;
        this.Wordlists=Wordlists;
    }
    //init new User without words list
    User(String Name)
    {
        this.Name=Name;
        this.Wordlists=new ArrayList<String>();
    }

    public ArrayList<String> getWordlist() {
        return Wordlists;
    }

    public String getName() {
        return Name;
    }
    public void AddWord(String Word)
    {
        if (checkWord(Word))
        {
            Wordlists.add(Word);
        }
    }
    public boolean checkWord(String Word)
    {
        return  Wordlists.contains(Word);
    }
}

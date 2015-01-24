package com.mygdx.game;


import java.util.ArrayList;

/**
 * Created by licheng5625 on 15-1-22.
 */
public class User implements Comparable<User>{
    private String Name;
    private ArrayList<String> Wordlists;
    private boolean isSound;
    private boolean isMusic;

    //init User with words list
    User(String Name,ArrayList<String> Wordlists,boolean sou,boolean muk)
    {
        this.isSound=sou;
        this.isMusic=muk;
        this.Name=Name;
        this.Wordlists=Wordlists;
    }
    //init new User without words list
    User(String Name)
    {
        this.isSound=true;
        this.isMusic=true;
        this.Name=Name;
        this.Wordlists=new ArrayList<String>();
    }

    public ArrayList<String> getWordlist() {
        return Wordlists;
    }
    public boolean getSound(){return isSound;}
    public void changeSound(boolean sou){isSound=sou;}
    public boolean getMusik(){return isMusic;}
    public void changeMusik(boolean muk){isMusic=muk;}
    public String getName() {
        return Name;
    }
    public void AddWord(String Word)
    {
        if (!checkWord(Word))
        {
            Wordlists.add(Word);
        }
    }
    public boolean checkWord(String Word)
    {
        return  Wordlists.contains(Word);
    }

    @Override
    public int compareTo(User user) {
        return user.getWordlist().size()-Wordlists.size();
        }
}

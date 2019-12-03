package com.example.dungeonquest;

public class Player {

    int level=0, health=0, stamina=0, magic=0, experience=0;

    public Player(){
        level=1;
        health=100;
        stamina=50;
        magic=20;
    }

    public int getLevel(){
        return level;
    }
    public int getHealth(){
        return health;
    }
    public int getStamina(){
        return stamina;
    }
    public int getMagic(){
        return magic;
    }
    public int getExperience(){
        return experience;
    }


}

package com.example.dungeonquest;

public class Player {

    int level, health, maxhp, mana, maxmana, experience, maxexp;

    boolean casted, dead;

    public Player(){
        level=1;
        health=100;
        maxhp=100;
        mana=20;
        maxmana=20;
        experience=0;
        maxexp=10;
    }

    public int getLevel(){
        return level;
    }
    public int getHealth(){
        return health;
    }
    public int getMana(){
        return mana;
    }
    public int getExperience(){
        return experience;
    }

    public boolean checkCasted(){
        return casted;
    }
    public boolean checkDead(){
        return dead;
    }

    public void takeDamage(int amount){
        health-=amount;
        if(health<=0){
            dead=true;
        }
        else{
            dead=false;
        }
    }

    public void heal(int amount){
        if((health+amount)>maxhp){
            health=maxhp;
        }
        else{
            health+=amount;
        }
    }

    public void castSpell(int cost){
        if(mana>=cost){
            mana-=cost;
            casted=true;
        }
        else{
            casted=false;
        }
    }

    public void gainMana(int amount){
        if((mana+amount)>maxmana){
            mana=maxmana;
        }
        else{
            mana+=amount;
        }
    }

    public void gainXP(int amount){
        if((experience+amount)>maxexp){
            level++;
            experience+=amount;
            experience=experience-maxexp;
        }
        else{
            experience+=amount;
        }
    }
}

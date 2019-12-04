package com.example.dungeonquest;

public class Player {

    int level, health, maxhp, stamina, maxstamina, magic, maxmagic, experience, maxexp;

    boolean attacked, casted, dead;

    public Player(){
        level=1;
        health=100;
        maxhp=100;
        stamina=50;
        maxstamina=50;
        magic=20;
        maxmagic=20;
        experience=0;
        maxexp=10;
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

    public boolean checkAttacked(){
        return attacked;
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

    public void attack(int cost){
        if(stamina>=cost){
            stamina-=cost;
            attacked=true;
        }
        else{
            attacked=false;
        }
    }

    public void gainStamina(int amount){
        if((stamina+amount)>maxstamina){
            stamina=maxstamina;
        }
        else{
            stamina+=amount;
        }
    }

    public void castSpell(int cost){
        if(magic>=cost){
            magic-=cost;
            casted=true;
        }
        else{
            casted=false;
        }
    }

    public void gainMana(int amount){
        if((magic+amount)>maxmagic){
            magic=maxmagic;
        }
        else{
            magic+=amount;
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

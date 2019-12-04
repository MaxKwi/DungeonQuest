package com.example.dungeonquest;

public class Enemy {

    int health, ability, abilitymax;
    boolean dead, usedAbility;

    public Enemy(){
        health=50;
        ability=0;
        abilitymax=20;
    }

    public int getEnemyHealth(){
        return health;
    }
    public int getAbility(){
        return ability;
    }

    public boolean checkDead(){
        return dead;
    }
    public boolean getUsedAbility(){
        return usedAbility;
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

    public void gainAbility(int amount){
        if((ability+amount)>=abilitymax){
            ability=abilitymax;
        }
        else{
            ability+=amount;
        }
    }

    public void useAbility(){
        if(ability==abilitymax){
            ability=0;
            usedAbility=true;
        }
        else{
            usedAbility=false;
        }
    }
}

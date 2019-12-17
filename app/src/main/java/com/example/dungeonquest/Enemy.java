package com.example.dungeonquest;

public class Enemy {

    int health, maxhealth, ability, abilitymax;
    boolean dead, usedAbility;
    String name;

    public Enemy(int health_){
        health=health_;
        maxhealth=health_;
        ability=0;
        abilitymax=20;
        name="Slime";
    }

    public int getEnemyHealth(){
        return health;
    }
    public int getEnemyMaxHealth(){
        return maxhealth;
    }
    public int getAbility(){
        return ability;
    }
    public int getAbilityMax(){
        return abilitymax;
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

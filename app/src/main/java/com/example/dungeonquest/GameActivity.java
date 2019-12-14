package com.example.dungeonquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    ConstraintLayout background;

    ProgressBar healthbar, manabar, expbar, enemyhealthbar, enemyabilitybar;
    TextView health, mana, exp, enemyhealth, enemyability, alert, level, enemyname;

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private static final String TAG = "GameActivity";

    private int enemyNum=0, damageTurns, turnsDamaged;
    private int damage, damageOverTime, enemyDamage, heals, levelnum=1;
    private int specialManaCost=15, ultManaCost=50, healManaCost=30;
    private boolean attacked, healed;

    Player hero = new Player();

    ArrayList<Enemy> enemies = new ArrayList();

    final String[] Options = {"Health", "Mana"};
    AlertDialog.Builder window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);

        background = findViewById(R.id.background);
        background.setBackgroundResource(R.drawable.forest_environment);

        health = findViewById(R.id.health);
        mana = findViewById(R.id.mana);
        exp = findViewById(R.id.exp);
        enemyhealth = findViewById(R.id.enemyhealth);
        enemyability = findViewById(R.id.enemyability);
        alert = findViewById(R.id.alert);
        level = findViewById(R.id.level);
        enemyname = findViewById(R.id.enemyname);

        healthbar = findViewById(R.id.healthbar);
        manabar = findViewById(R.id.manabar);
        expbar = findViewById(R.id.expbar);
        enemyhealthbar = findViewById(R.id.enemyhealthbar);
        enemyabilitybar = findViewById(R.id.enemyabilitybar);

        enemies.add(new Enemy());
    }

    public void actionUp(){
        //BASIC ATTACK
        damage = 15;
        attacked=true;
        Action();

    }

    public void actionLeft(){
        //SPECIAL ATTACK
        hero.castSpell(specialManaCost);
        if(hero.checkCasted()){
            damage=10;
            damageOverTime=5;
            damageTurns=3;
            turnsDamaged=0;
            attacked=true;
            Action();
            hero.postCast();
        }
        else{
            alert.setText("Not Enough Mana");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alert.setText("");
                }
            }, 750); // Millisecond 1000 = 1 sec
        }
    }

    public void actionRight(){
        //HEAL
        hero.castSpell(healManaCost);
        if(hero.checkCasted()){
            heals=50;
            healed=true;
            Action();
            hero.postCast();
        }
        else{
            alert.setText("Not Enough Mana");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alert.setText("");
                }
            }, 750); // Millisecond 1000 = 1 sec
        }
    }

    public void actionDown(){
        //ULTIMATE ATTACK
        hero.castSpell(ultManaCost);
        if(hero.checkCasted()){
            damage=40;
            attacked=true;
            Action();
            hero.postCast();
        }
        else{
            alert.setText("Not Enough Mana");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alert.setText("");
                }
            }, 750); // Millisecond 1000 = 1 sec
        }
    }

    public void Action(){
        // ATTACKS
        if(attacked){
            enemies.get(enemyNum).takeDamage(damage);
            //DAMAGE OVER TIME
            if(turnsDamaged<damageTurns){
                enemies.get(enemyNum).takeDamage(damageOverTime);
                turnsDamaged++;
            }
            updateUI();
            attacked=false;
        }
        //HEALS
        if(healed){
            hero.heal(heals);
            updateUI();
            healed=false;
        }

        final Intent intent = new Intent(this, GameOver.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //SPAWNS NEW ENEMY
                if(enemies.get(enemyNum).checkDead()){
                    //UPDATE SAVE DATA HERE
                    hero.gainXP(2);
                    if(hero.getLevel()>levelnum){
                        levelUp();
                    }
                    levelnum=hero.getLevel();
                    enemies.add(new Enemy());
                    enemyNum++;
                    hero.heal(hero.getMaxHealth());
                    updateUI();
                }
                //ENEMY ATTACKS
                else{
                    enemies.get(enemyNum).gainAbility(5);
                    enemies.get(enemyNum).useAbility();
                    //SPECIAL ABILITY
                    if(enemies.get(enemyNum).getUsedAbility()){
                        enemyDamage=40;
                    }
                    //BASIC ATTACK
                    else{
                        enemyDamage=15;
                    }
                    hero.takeDamage(enemyDamage);
                    updateUI();
                }
                //REGAINS MANA
                if(hero.getMana()<hero.getMaxMana()){
                    hero.gainMana(10);
                }
                //ENDS GAME
                if (hero.checkDead()){
                    //DELETE DATABASE DATA HERE
                    startActivity(intent);
                }
            }
        }, 500); // Millisecond 1000 = 1 sec
    }

    public void updateUI(){
        health.setText(""+hero.getHealth());
        mana.setText(""+hero.getMana());
        exp.setText(""+hero.getExperience());
        enemyhealth.setText(""+enemies.get(enemyNum).getEnemyHealth());
        enemyability.setText(""+enemies.get(enemyNum).getAbility());

        healthbar.setProgress(hero.getHealth());
        manabar.setProgress(hero.getMana());
        expbar.setProgress(hero.getExperience());
        enemyhealthbar.setProgress(enemies.get(enemyNum).getEnemyHealth());
        enemyabilitybar.setProgress(enemies.get(enemyNum).getAbility());

        healthbar.setMax(hero.getMaxHealth());
        manabar.setMax(hero.getMaxMana());
        expbar.setMax(hero.getMaxExp());
//        enemyhealthbar.setMax(enemies.get(enemyNum).getMaxHealth());
//        enemyabilitybar.setMax(enemies.get(enemyNum).getMaxAbility());

        level.setText("Level   "+hero.getLevel());
//        enemyname.setText();
    }

    public void levelUp(){
        hero.increaseMaxExp(2);
        if(hero.getLevel()>=10){
            background.setBackgroundResource(R.drawable.cave_environment);
        }
        window = new AlertDialog.Builder(this);
        window.setTitle("Pick an attribute");
        window.setCancelable(false);
        window.setItems(Options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    //first option clicked, do this...

                }else if(which == 1){
                    //second option clicked, do this...

                }else{
                    //theres an error in what was selected

                }
            }
        });
        window.show();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        // Grab two events located on the plane at e1=(x1, y1) and e2=(x2, y2)
        // Let e1 be the initial event
        // e2 can be located at 4 different positions, consider the following diagram
        // (Assume that lines are separated by 90 degrees.)
        //
        //
        //         \ A  /
        //          \  /
        //       D   e1   B
        //          /  \
        //         / C  \
        //
        // So if (x2,y2) falls in region:
        //  A => it's an UP swipe
        //  B => it's a RIGHT swipe
        //  C => it's a DOWN swipe
        //  D => it's a LEFT swipe
        //

        float x1 = e1.getX();
        float y1 = e1.getY();

        float x2 = e2.getX();
        float y2 = e2.getY();

        Direction direction = getDirection(x1,y1,x2,y2);
        return onSwipe(direction);
//        return false;
    }

    /** Override this method. The Direction enum will tell you how the user swiped. */
    public boolean onSwipe(Direction direction){
        if (direction==Direction.up){
            //do your stuff
            Log.d(TAG,"onSwipe: up");
            actionUp();
        }

        if (direction==Direction.down){
            //do your stuff
            Log.d(TAG,"onSwipe: down");
            actionDown();
        }

        if (direction==Direction.left){
            //do your stuff
            Log.d(TAG,"onSwipe: left");
            actionLeft();
        }

        if (direction==Direction.right){
            //do your stuff
            Log.d(TAG,"onSwipe: right");
            actionRight();
        }

        return true;
//        return false;
    }

    /**
     * Given two points in the plane p1=(x1, x2) and p2=(y1, y1), this method
     * returns the direction that an arrow pointing from p1 to p2 would have.
     * @param x1 the x position of the first point
     * @param y1 the y position of the first point
     * @param x2 the x position of the second point
     * @param y2 the y position of the second point
     * @return the direction
     */
    public Direction getDirection(float x1, float y1, float x2, float y2){
        double angle = getAngle(x1, y1, x2, y2);
        return Direction.fromAngle(angle);
    }

    /**
     *
     * Finds the angle between two points in the plane (x1,y1) and (x2, y2)
     * The angle is measured with 0/360 being the X-axis to the right, angles
     * increase counter clockwise.
     *
     * @param x1 the x position of the first point
     * @param y1 the y position of the first point
     * @param x2 the x position of the second point
     * @param y2 the y position of the second point
     * @return the angle between two points
     */
    public double getAngle(float x1, float y1, float x2, float y2) {

        double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
        return (rad*180/Math.PI + 180)%360;
    }


    public enum Direction{
        up,
        down,
        left,
        right;

        /**
         * Returns a direction given an angle.
         * Directions are defined as follows:
         *
         * Up: [45, 135]
         * Right: [0,45] and [315, 360]
         * Down: [225, 315]
         * Left: [135, 225]
         *
         * @param angle an angle from 0 to 360 - e
         * @return the direction of an angle
         */
        public static Direction fromAngle(double angle){
            if(inRange(angle, 45, 135)){
                return Direction.up;
            }
            else if(inRange(angle, 0,45) || inRange(angle, 315, 360)){
                return Direction.right;
            }
            else if(inRange(angle, 225, 315)){
                return Direction.down;
            }
            else{
                return Direction.left;
            }

        }

        /**
         * @param angle an angle
         * @param init the initial bound
         * @param end the final bound
         * @return returns true if the given angle is in the interval [init, end).
         */
        private static boolean inRange(double angle, float init, float end){
            return (angle >= init) && (angle < end);
        }
    }
}

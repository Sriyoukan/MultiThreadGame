package Nozama_warriors;

/**
 * Created by 160538L on 8/22/2017.
 */
public abstract class Warrior extends Inhabitant  {

    public static int no_of_warrior = 0;
    public static int immobile_warrior = 0;
    boolean Swim;                                                                                                       // Swim ability of a warrior
    SwimFin[] swimfin;                                                                                                  //Swim fin
    private boolean immortal;
    private boolean dead;

    // Constructor of Warrior
    public Warrior(String name, int age) {
        super(name, age);                                                                                               // Calling Super class's constructor
        dead = false;                                                                                                   // Setting The warrior is not dead
        swimfin = new SwimFin[2];                                                               // Giving swim fin to a warrior and warrior has maximum 2 swim fins
        Swim = true;                                                                                                    // Giving swim ability true
        immortal = false;                                                                                               // Giving Immortality False
        no_of_warrior+=1;                                                                                                // adding warriors' count
    }

    public SwimFin looseSwimmingFin() {
        Swim = false;                                                                                                   // setting swim ability false
        SwimFin tempswimFin = swimfin[0];                                                                               // setting a temporary swim fin
        swimfin = null;                                                                                                 // make warrior's swim fin as null
        return tempswimFin;                                                                                             // return swim fin to warrior
    }
    /*Getters and setters for the Warrior's attributes*/
    //getter
    public boolean isImmortal() {
        return immortal;
    }

    //setter
    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }
    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public boolean isSwim() {
        return Swim;
    }
    public void setSwim(boolean swim) {
        Swim = swim;
    }

    /*method to update the notifcations*/
    public void update(LotusFlower lotusFlower) {
        lotusFlower.pickPetal(this);
    }
}
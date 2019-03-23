package Nozama_warriors;


/**
 * Created by 160538L on 8/22/2017.
 */
public class KillerFish extends Fish {
    public KillerFish(String name, int age) {                                                                           //setting the name and age for killer Fish
        super(name, age);
    }

    public void kill(Warrior warrior) {
        warrior.setDead(true);
        System.out.println(getName() + " Killed " + warrior.getName()
                + "\n");
    }

    @Override
    public void update(Warrior warrior) {
        if (warrior.isImmortal()) {                                                                                     //Check warriors cannot be killed
            eatSwimFin(warrior);
            MakeSound makeSoundfinish0 = new MakeSound();
            makeSoundfinish0.playSound("Rubber_Eat.wav");
        } else {                                                                                                        //if yes
            eatSwimFin(warrior);                                                                                        //eating Swim fin
            MakeSound makeSoundfinish1 = new MakeSound();
            makeSoundfinish1.playSound("Rubber_Eat.wav");
            kill(warrior);                                                                                              //Killing Warrior
            Warrior.no_of_warrior= Warrior.no_of_warrior-1;
            if (Warrior.no_of_warrior==1){
                System.out.println("Now "+Warrior.no_of_warrior+" Warrior is in the Lake Nozama lake");}
            else {
            System.out.println("Now "+Warrior.no_of_warrior+" Warriors are in the Lake Nozama lake");}
            MakeSound makeSoundfinish2 = new MakeSound();
            makeSoundfinish2.playSound("Kill.wav");

        }
    }
}

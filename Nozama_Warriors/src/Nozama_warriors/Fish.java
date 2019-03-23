package Nozama_warriors;

/**
 * Created by 160538L on 8/22/2017.
 */
public abstract class Fish extends Inhabitant implements Observer {
    private SwimFin[] swimfin;                                                                                          //if fish get swim fin
    private int SwimFinCount;                                                                                           // the no of swim fin it got

    public Fish(String name, int age) {
        super(name, age);
        swimfin = new SwimFin[8];
        SwimFinCount = 0;
    }

    public void eatSwimFin(Warrior warrior) {
        if (SwimFinCount <= 8) {
            SwimFinCount=SwimFinCount+2;
            swimfin[SwimFinCount]= warrior.looseSwimmingFin();                                                          //loosing SwimFin
        }
        System.out.println(getName() + " ate the Swim Fin of "
                + warrior.getName());                                                                                   //print ate the SwimFin
        System.out.println(warrior.getName() + " Becomes Inmobile...\n");
        Warrior.immobile_warrior+=1;
        /* if one warrior can't swim that is a singular so in here i check only the grammar */
        if (Warrior.immobile_warrior==1){
            System.out.println("There is a "+Warrior.immobile_warrior+" warrior can't swim.");
        }else {
        System.out.println("There are "+Warrior.immobile_warrior+" warriors can't swim.");}
    }

    @Override
    public void update(Warrior warrior) {
    }
}

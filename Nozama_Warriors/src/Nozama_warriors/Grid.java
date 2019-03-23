package Nozama_warriors;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Grid implements Observable {

    public static boolean notreached = true;                                                                            // Warrior reached or not to the treasure chest
    public Node[][] node;                                                                                               // there are 11*11 coordinates.
    TreasureChest treasureChest;                                                                                        // there is a Treasure Chest in (5,5)
    private static final Object lock1 = new Object();                                                                   // Static lock to To avoid racing conditions
    private static final Object lock2 = new Object();                                                                   // Static lock to To avoid racing conditions


    /* Constructor  of the Grid Class to create the node */
    public Grid() {
        node = new Node[11][11];                                                                                        // initializing the node up to 10*10
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                node[i][j] = new Node(i, j, 0);                                             // calling the node constructor and set initial value 0,0
                node[i][j].setCheckobj(false);
            }
        }
    }
    /**
     * ****************************
     * Mathods to Add inhabitant
     * @param inhabitant means the inhabitant what you want to add.
     * @param x is the x coordinate value.
     * @param y is the y coordinate value.
     * @param objectid object id is used for find the inhabitant.
     * @param inhabitantname every inhabatant has a name.
     * ****************************
     */
    /* Function to Add Inhabbitant */
    public void addInhabitant(Inhabitant inhabitant, int x, int y, int objectid, String inhabitantname) {
        if (node[x][y].isCheckobj() == false) {                                                                         //check if there any objects
            node[x][y].add(objectid, inhabitant, inhabitantname);                                                       //add inhabitant
        }
    }

    //Function to Add New Inhabitant to node
    public void settemp(Inhabitant inhabitant, int x, int y) {
        inhabitant.setX(x);                                                                                             //update inhabitant's x value
        inhabitant.setY(y);                                                                                             //update inhabitant's y value
    }

    //Reset the coordinate to null object
    public void resetcoordinate(int x, int y) {                                                                         //Resetting last Coordinate warrior in
        if (node[x][y].getInhabitant() instanceof Warrior) {                                                            //Check last coordinate is warrior's
            node[x][y].setInhabitant(null);                                                                             //Clean object
            node[x][y].setCheckobj(false);                                                                              //Object is not there
            node[x][y].setObjectid(0);                                                                                  //0 - is id for no objects
        }
    }

    //getter object ID
    public int getobjID(int x, int y) {
        return node[x][y].getObjectid();
    }

    //getter Inhabitant
    public Inhabitant getinhabitant(int x, int y) {
        return node[x][y].getInhabitant();
    }

    //clear the node
    public void clearnode(int x, int y) {
        node[x][y].add(0, null, null);
    }
    /**
     * ****************************
     * Mathods to Check inhabitant*
     *
     * @param x  is x value of an object
     * @param y is y value of an object
     * @return is there any object check the node's object id
     */
    /*
	 * Return if any objects in the coordinate
     */
    public boolean checkcordinate(int x, int y) {
        return node[x][y].isCheckobj();
    }

    //Return if any Warriors in the coordinate
    public boolean checkWarrior(int x, int y) {
        return node[x][y].getObjectid() == 1 || node[x][y].getObjectid() == 2;
    }

    //Return if any NormalWarrior in the coordinate
    public boolean checkNormalWarrior(int x, int y) {
        return node[x][y].getObjectid() == 2;
    }

    // Return if any SuperWarrior in the coordinate
    public boolean checkSuperWariors(int x, int y) {
        return node[x][y].getObjectid() == 1;
    }

    //Return if any Fish in the coordinate
    public boolean checkFish(int x, int y) {
        return node[x][y].getInhabitant() instanceof Fish;
    }

    //Return if any Lotus Flower in the coordinate
    public boolean checkLotus(int x, int y) {
        return node[x][y].getInhabitant() instanceof LotusFlower;
    }

    /**
     * I wrote a method move
     * this method give the statement of warriors moments.
     * @param w    is refer a warrior
     * @param oldx is the previous x axis location of the warrior
     * @param oldy is the previous y axis location of the warrior
     * @param newx is the current x axis location of the warrior
     * @param newy is the current y axis location of the warrior
     */
    public boolean allowSwim(Warrior w, int oldx, int oldy, int newx, int newy) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Warrior.class.getName()).log(Level.SEVERE, null, ex);
        }
        GameDemo.gui.paintAgain();
        MakeSound makeSoundswim = new MakeSound();
        makeSoundswim.playSound("Swim.wav");
        if (checkFish(newx, newy) && (notreached)) {
            System.out.println(w.getName() + " Swims From (" + oldx + "," + oldy + ") To (" + (newx) + "," + newy + ")");
            notifyFish(((Fish) getinhabitant(newx, newy)), w);
            settemp(w, newx, newy);
            resetcoordinate(oldx, oldy);
            if ((Warrior.immobile_warrior == 4)
                    && (Warrior.no_of_warrior == 0)) {
                System.out.println("Misson Failed...");
            }
            return false;
        }

        // If warrior reached 5,5
        else if ((newx == 5 && newy == 5) && (notreached)) {
            synchronized (lock1) {
                if (notreached) {
                    System.out.println(w.getName() + " Swims From (" + oldx
                            + "," + oldy + ") To (5,5)- Swam to TreasureChest\n"
                            +"At the end there are "+Warrior.no_of_warrior+" warriors in the Nozama Lake "
                            +"and there are "+Warrior.immobile_warrior+" warriors can't swim in the Nozama Lake\n\n"
                            + notifyTreasureChest(w));
                    resetcoordinate(oldx, oldy);
                    addInhabitant(w, 5, 5, 1, w.getName());
                    GameDemo.gui.paintAgain();
                    MakeSound makeSoundfinish = new MakeSound();
                    makeSoundfinish.playSound("Finish.wav");
                    JOptionPane.showMessageDialog(null,
                            "Whooo Yeah !!!!! , Mission Accomplished...",
                            "Statement",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                return false;
            }
        } else if ((checkLotus(newx, newy)) && (notreached) && (getinhabitant(newx, newy) instanceof NormalWarrior)) {  // If normal warrior reached in to Lotus Flower
            ((LotusFlower) getinhabitant(newx, newy)).pickPetal(w);
            System.out.println(w.getName() + " Swims From (" + oldx + ","
                    + oldy + ") To (" + (newx) + "," + (newy) + ")...");
            settemp(w, newx, newy);
            notifyWarrior(((LotusFlower) getinhabitant(newx, newy)), w);
            return true;
        }

        //If there are warriors in adjacent coordinates
        else if ((checkWarrior((newx), (newy))) && (notreached)) {
            return true;                                                                                                // Return you can't move
        }

        //If there are no inhabitants in adjacent coordinates
        else if ((!(checkWarrior((newx), (newy)))) && (notreached)) {
            synchronized (lock2) {                                                                         //If Warrior Moved one cell other's can't at the time -lock
                System.out.println(w.getName() + " Swims From (" + oldx + ","
                        + oldy + ") To (" + (newx) + "," + newy + ")...\n");
                resetcoordinate(oldx, oldy);                                                                            //Resetting Coordinate
                addInhabitant(w, (newx), newy, 2, w.getName());
                return true;
            }
        } else {
            return true;
        }
    }

    public void lookforLotus(Warrior w, int oldx, int oldy) {
        GameDemo.gui.paintAgain();
        if (((SuperWarrior) w).useBinocular(this, oldx, oldy)) {                                          //Super Warrior used Binocular to see Lotus Flower
            if (checkLotus(oldx + 1, oldy)) {
                System.out.println(w.getName() + " Swims From (" + w.getX()
                        + "," + w.getY() + ") To ("
                        + (w.getX() + 1) + "," + (w.getY()) + ")");                                                     //printing he moved to that place
                settemp(w, oldx + 1, oldy);                                                                          //Set temporary Location
                notifyWarrior(((LotusFlower) getinhabitant(oldx + 1, oldy)), w);                                     //notifying the Warrior
            } else if (checkLotus(oldx, oldy + 1)) {
                System.out.println(w.getName() + " Swims From ("
                        + w.getX() + "," + w.getY() + ") To ("
                        + (w.getX()) + "," + (w.getY() + 1) + ")");
                settemp(w, oldx, oldy + 1);
                notifyWarrior(((LotusFlower) getinhabitant(oldx, oldy + 1)), w);
            } else if (checkLotus(oldx - 1, oldy)) {
                ((LotusFlower) getinhabitant(oldx - 1, oldy)).pickPetal(w);
                System.out.println(w.getName() + " Swims From ("
                        + w.getX() + "," + w.getY() + ") To ("
                        + (w.getX() - 1) + "," + (w.getY()) + ")");
                settemp(w, oldx - 1, oldy);
                notifyWarrior(((LotusFlower) getinhabitant(oldx - 1, oldy)), w);
            } else if (checkLotus(oldx, oldy - 1)) {
                System.out.println(w.getName() + " Swims From ("
                        + w.getX() + "," + w.getY() + ") To (" + (w.getX())
                        + "," + (w.getY() - 1) + ")");
                settemp(w, oldx, oldy - 1);
                notifyWarrior(((LotusFlower) getinhabitant(oldx, oldy - 1)), w);
            }
        }
    }

    //telling the special characters
    @Override
    public void notifyFish(Fish fish, Warrior warrior) {
        //if Fish is innocent
        if (fish instanceof InnocentFish) {
            ((InnocentFish) fish).update(warrior);
            GameDemo.gui.paintAgain();

        }
        // If Fish is Killer
        else if (fish instanceof KillerFish) {
            ((KillerFish) fish).update(warrior);
            GameDemo.gui.paintAgain();
        } // If Fish is RubberEat
        else {
            ((RubberEatFish) fish).update(warrior);
            GameDemo.gui.paintAgain();
        }
    }

    @Override
    public void notifyWarrior(LotusFlower lotusFlower, Warrior warrior) {
        warrior.update(lotusFlower);
        GameDemo.gui.paintAgain();
    }

    @Override
    public String notifyTreasureChest(Warrior warrior) {
        treasureChest = new TreasureChest();
        return (treasureChest.update(warrior));
    }
}
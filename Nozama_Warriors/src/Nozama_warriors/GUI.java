package Nozama_warriors;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JPanel {
    private static ArrayList<Inhabitant> inhabitantArrayList = new ArrayList<>();
    public JFrame jf;
    GUI(JFrame jf) {
        this.jf = jf;
        jf.setTitle("Nozama Warriors");
        jf.add(this);
        jf.setVisible(true);
        jf.setBackground(new Color(0, 102,204));
        jf.setBounds(0, 0, 890, 890);
        jf.setIconImage(new ImageIcon("Warrior.png").getImage());

    }


    @Override
    public void paintComponent(Graphics g) {
        /**get the images from directory their
         * these images are used for indicate inhabitants
         * Every inhabitant has unique images
         */
        Image nwImg = Toolkit.getDefaultToolkit().getImage("ICON\\W.png");
        Image swImg = Toolkit.getDefaultToolkit().getImage("ICON\\SW.png");
        Image infImg = Toolkit.getDefaultToolkit().getImage("ICON\\inf.png");
        Image kfImg = Toolkit.getDefaultToolkit().getImage("ICON\\kf.png");
        Image rfImg = Toolkit.getDefaultToolkit().getImage("ICON\\rf.png");
        Image lfImg = Toolkit.getDefaultToolkit().getImage("ICON\\lf.png");
        Image tImg = Toolkit.getDefaultToolkit().getImage("ICON\\t.png");
        Image finalImg = Toolkit.getDefaultToolkit().getImage("ICON\\final.png");
        //Draw the grid lines
        g.setColor(Color.black);
        for (int i = 50; i < 875; i += 75) {
            g.drawLine(i, 50, i, 800);
            g.drawLine(50, i, 800, i);
        }
        //to indicate the nodes as rectangle in black color
        g.setColor(new Color(0, 0, 0));
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                g.fill3DRect((i * 75) + 45, (j * 75) + 45, 10, 10, true);
            }
        }
        // indicate the center node ( the node which contains the treasure chest ) as rectangle in green color
        g.drawImage(tImg,395,375,65,65,this);

        /* Paint every images of the inhabitant in JFrame */
        for (int i = 0; i < inhabitantArrayList.size(); i++) {
            if (inhabitantArrayList.get(i) instanceof SuperWarrior) {
                g.drawImage(swImg, (((SuperWarrior) inhabitantArrayList.get(i)).getX() * 75) + 20, (((SuperWarrior) inhabitantArrayList.get(i)).getY() * 75) + 5, 50, 50, this);
            }

            else if (inhabitantArrayList.get(i) instanceof NormalWarrior) {
                g.drawImage(nwImg, (((NormalWarrior) inhabitantArrayList.get(i)).getX() * 75) + 30, (((NormalWarrior) inhabitantArrayList.get(i)).getY() * 75) + 10, 50, 50, this);
            } else if (inhabitantArrayList.get(i) instanceof KillerFish) {
                g.drawImage(kfImg, (((KillerFish) inhabitantArrayList.get(i)).getX() * 75) + 20, (((KillerFish) inhabitantArrayList.get(i)).getY() * 75) + 20, 65, 65, this);
            } else if (inhabitantArrayList.get(i) instanceof RubberEatFish) {
                g.drawImage(rfImg, (((RubberEatFish) inhabitantArrayList.get(i)).getX() * 75) + 20, (((RubberEatFish) inhabitantArrayList.get(i)).getY() * 75) + 10, 65, 65, this);
            } else if (inhabitantArrayList.get(i) instanceof InnocentFish) {
                g.drawImage(infImg, (((InnocentFish) inhabitantArrayList.get(i)).getX() * 75) + 25, (((InnocentFish) inhabitantArrayList.get(i)).getY() * 75) + 15, 50, 50, this);
            } else if (inhabitantArrayList.get(i) instanceof LotusFlower) {
                g.drawImage(lfImg, (((LotusFlower) inhabitantArrayList.get(i)).getX() * 75) + 25, (((LotusFlower) inhabitantArrayList.get(i)).getY() * 75) + 10, 50, 50, this);
            }
        }
        if (Grid.notreached==false){
            g.drawImage(finalImg,220,220,400,400,this);
        }
    }

    public static void attach(Inhabitant inhabit) {
        inhabitantArrayList.add(inhabit);
    }

    public void paintAgain() {
        jf.repaint();
    }
}
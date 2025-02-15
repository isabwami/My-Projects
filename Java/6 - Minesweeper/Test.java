//import javax.swing.*;
//import java.awt.*;
import java.util.*;

public class Test {

    public Test() {
        /*
        // info panel
        JPanel info = new JPanel();
        BoxLayout bLayout= new BoxLayout(info, BoxLayout.X_AXIS);
        info.setLayout(bLayout);
        info.setMaximumSize(new Dimension(500,50));

        // difficulty settinigs
        JLabel diffLabel = new JLabel("Choose your difficulty:  ");
        info.add(diffLabel);

        String[] diffOptions = {"Easy", "Medium", "Hard", "Extreme"};
        JComboBox<String> difficulty = new JComboBox<>(diffOptions);
        
        info.add(difficulty);
        root.add(info);
        */
        
        /*
        // count Mines
        if (N.getIcon() != null) {
            if (N.getIcon().equals(defaultMineIcon)) {
                numSurrMines++;
            }
        }
        if (E.getIcon() != null) {
            if (E.getIcon().equals(defaultMineIcon)) {
                numSurrMines++;
            }
        }
        if (S.getIcon() != null) {
            if (S.getIcon().equals(defaultMineIcon)) {
                numSurrMines++;
            }
        }
        if (W.getIcon() != null) {
            if (W.getIcon().equals(defaultMineIcon)) {
                numSurrMines++;
            }
        }
        if (NE.getIcon() != null) {
            if (NE.getIcon().equals(defaultMineIcon)) {
                numSurrMines++;
            }
        }
        if (SE.getIcon() != null) {
            if (SE.getIcon().equals(defaultMineIcon)) {
                numSurrMines++;
            }
        }
        if (NW.getIcon() != null) {
            if (NW.getIcon().equals(defaultMineIcon)) {
                numSurrMines++;
            }
        }
        if (SW.getIcon() != null) {
            if (SW.getIcon().equals(defaultMineIcon)) {
                numSurrMines++;
            }
        }
        */        

        /*
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10,10));

        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                JButton button = new JButton("[" + j + ", " + i + "]");
                panel.add(button);
            }
        }

        
        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        */
    }

    static int counter;

    public static void main(String[] args) {
        //new Test();

        Timer timer = new Timer();
        counter = 1;

        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println(counter);
                counter++;
            }
        };


        timer.schedule(task, 0, 1000);

    }
    
}

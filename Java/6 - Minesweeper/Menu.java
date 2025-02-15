import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import java.awt.Image;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.GridLayout;

public class Menu {
    
    public Menu() {
        JFrame frame = new JFrame();

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        
        JPanel panel = new JPanel();
        

        JLabel title = new JLabel("Minesweeper");
        panel.add(title);

        JButton easy = new JButton("Easy");
        JButton medium = new JButton("Medium");
        JButton hard = new JButton("Hard");

        panel.add(easy);
        panel.add(medium);
        panel.add(hard);

        //panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(panel);
        frame.setContentPane(root);
        frame.pack();
        frame.setSize(1000, 1000);
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Menu();
    }

}

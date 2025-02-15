import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.GridLayout;

public class Minesweeper {
    
    private int numSurrMines;
    private int ind1;
    private int ind2;
    private int gridSize;
    private int dimSize;
    private int maxMines;
    private int numMines;
    private static int counter;
    
    private Random rng;
    private JButton[][] buttonsList;
    private ArrayList<JButton> surrButtons;
    private ArrayList<JButton> revealedButtons = new ArrayList<>();
    private ArrayList<JButton> processedButtons = new ArrayList<>();
    private Boolean gameOver;
    private Timer timer;
    
    private ImageIcon defaultIcon;
    private ImageIcon defaultMineIcon;
    private ImageIcon clickedIcon;
    private ImageIcon mineClickedIcon;
    private ImageIcon oneMine;
    private ImageIcon twoMines;
    private ImageIcon threeMines;
    private ImageIcon fourMines;
    private ImageIcon fiveMines;
    private ImageIcon sixMines;
    private ImageIcon sevenMines;
    private ImageIcon eightMines;
    private ImageIcon flag;
    private ImageIcon prevIcon;

    public Minesweeper() {
    
        JFrame frame = new JFrame("Minesweeper");
        frame.setBackground(Color.white);
        rng = new Random();

        showHelpScreen(frame);

        startGame(frame);

        frame.pack();
        frame.setSize(1080, 1080);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    } // end Minesweeper() constructor

    public void startGame(JFrame frame) {

        gameOver = false;

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(Color.white);

        // info panel
        JPanel info = new JPanel();
        BoxLayout bLayout= new BoxLayout(info, BoxLayout.X_AXIS);
        info.setLayout(bLayout);
        info.setMaximumSize(new Dimension(500,50));
        info.setBackground(Color.white);
        root.add(info);

        // default options (Easy)
        gridSize = 9;
        maxMines = 10;
        dimSize = 800;
        
        // display difficulty options
        info.add(displayGameOptions(frame));

        // icon setup 
        configureIcons();

        // minefield panel
        JPanel mineField = new JPanel();
        mineField.setLayout(new GridLayout(gridSize, gridSize));
        mineField.setMaximumSize(new Dimension(800, 800));
        mineField.setBackground(Color.white);
        root.add(mineField);

        // create grid
        createGrid(mineField);

        // place mines
        placeMines(maxMines, gridSize);

        // configure buttons
        configureButtons(frame, mineField);

        // start timer
        info.add(startTimer());

        frame.setContentPane(root);

    } // end startGame() method


    public void createGrid(JPanel mineField) {

        buttonsList = new JButton[gridSize][gridSize];

        for (int i=0; i<gridSize; i++) {
            for (int j=0; j<gridSize; j++) {
                JButton button = new JButton(defaultIcon);
                button.setBackground(Color.lightGray);
                buttonsList[j][i] = button;
                mineField.add(button);
            } // end j-for-loop             
        } // end i-for-loop

    } // end createGrid() method

    public void placeMines(int maxMines, int gridSize) {

        numMines=0;
        while (numMines < maxMines) {
            int row = rng.nextInt(gridSize-1);
            int col = rng.nextInt(gridSize-1);
            if (buttonsList[row][col].getIcon() != defaultMineIcon) {
                buttonsList[row][col].setIcon(defaultMineIcon);
                numMines++;
            } // end if-statement
        } // end while-loop

    } // end placeMines() method

    // Timer https://www.youtube.com/watch?v=QEF62Fm81h4&ab_channel=BroCode
    public JLabel startTimer() {
        
        // time label that will be displayed above the grid
        JLabel time = new JLabel("Time: ");

        counter = 0;

        timer = new Timer();

        // task to complete when the timer runs
        TimerTask task = new TimerTask() {
            public void run() {
                if (!gameOver) {
                    counter++;
                    time.setText("Time: " + counter);
                } // end if-statement
                else {
                    time.setText("Time: " + counter);
                } // end else-statement
                
            } // end run() method
        }; // end TimerTask
        
        timer.schedule(task, 0, 1000);

        return time;

    } // end startTimer() method

    public void verifyActiveButton(JButton activeButton) {
        
        for (int x=0; x<gridSize; x++) { 
            for (int y=0; y<gridSize; y++) {
                if (buttonsList[x][y].equals(activeButton)) {
                    ind1 = x;
                    ind2 = y;
                    break;
                } // end if-statement
            } // end y-for-loop
        } // end x-for-loop

    } // end verifyActiveButton() method

    public void configureButtons(JFrame frame, JPanel mineField) {

        for (int i=0; i<gridSize; i++) {
            for (int j=0; j<gridSize; j++) {
                ind1 = j;
                ind2 = i;
                JButton activeButton = buttonsList[j][i];

                activeButton.addActionListener(e -> {

                    // check which button was pressed
                    JButton pressedButton = (JButton) e.getSource();

                    // if the button was already pressed before, nothing else needs to be done
                    if (revealedButtons.contains(pressedButton)) {
                        return;
                    } // end if-statement

                    verifyActiveButton(activeButton);

                    // determine surrounding buttons
                    getSurrButtons();
                    
                    processedButtons.clear();

                    // Button actions
                    numSurrMines = 0;

                    // if a mine is clicked the game will end and the player loses
                    if (gameOver(activeButton)) {
                        gameOver = true;
                        gameOverMessage(frame, mineField, maxMines);
                    } // end if-statement

                    // if all non-mine buttons are clicked the game will end and the player wins
                    if (hasWon(buttonsList)) {
                        gameOver = true;
                        hasWonMessage(frame, mineField, counter, maxMines);
                    } // end if-statement

                }); // end ActionListener
            
                // Right click mouse listener (mouseClicked and isRightMouseButton) https://www.daniweb.com/programming/software-development/threads/456771/mouse-right-click-in-java
                activeButton.addMouseListener(new MouseAdapter() {
                    
                    public void mouseClicked(MouseEvent e) {
                        verifyActiveButton(activeButton);
                        if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {

                            // set previous icon of flagged button
                            if (activeButton.getIcon() == defaultMineIcon) {
                                prevIcon = defaultMineIcon;
                            } // end if-statement
                            else if (activeButton.getIcon() == defaultIcon) {
                                prevIcon = defaultIcon;
                            } // end else if-statement

                            // set/reset flagged button
                            if (activeButton.getIcon() == defaultIcon || activeButton.getIcon() == defaultMineIcon) {
                                activeButton.setIcon(flag);
                            } // end else if-statment
                            else if (activeButton.getIcon() == flag) {
                                activeButton.setIcon(prevIcon);
                            } // end else if-statement
                        } // end if-statement
                    } // end mouseClicked() method
                }); // end MouseListener

            } // end for-loop
        } // end for-loop
    } // end configureButtons() method

    public void getSurrButtons() {

        surrButtons = new ArrayList<>();

        // Surrounding button coordinate system
        JButton N = new JButton(); JButton E = new JButton();
        JButton S = new JButton(); JButton W = new JButton(); 
        JButton NE = new JButton(); JButton SE = new JButton();
        JButton NW = new JButton(); JButton SW = new JButton();

        // test edge cases to see if surrounding buttons are possible 
        if (ind2 != 0) {
            N = buttonsList[ind1][ind2-1];
            surrButtons.add(N);
        } // end if-statement
        if (ind1 != gridSize-1) {
            E = buttonsList[ind1+1][ind2];
            surrButtons.add(E);
        } // end if-statement
        if (ind2 != gridSize-1) {
            S = buttonsList[ind1][ind2+1];
            surrButtons.add(S);
        } // end if-statement
        if (ind1 != 0) {
            W = buttonsList[ind1-1][ind2];
            surrButtons.add(W);
        } // end if-statement
        if (ind1 != gridSize-1 && ind2 != 0) {
            NE = buttonsList[ind1+1][ind2-1];
            surrButtons.add(NE);
        } // end if-statement
        if (ind1 != gridSize-1 && ind2 != gridSize-1) {
            SE = buttonsList[ind1+1][ind2+1];
            surrButtons.add(SE);
        } // end if-statement
        if (ind1 != 0 && ind2 != 0) {
            NW = buttonsList[ind1-1][ind2-1];
            surrButtons.add(NW);
        } // end if-statement
        if (ind1 != 0 && ind2 != gridSize-1) {
            SW = buttonsList[ind1-1][ind2+1];
            surrButtons.add(SW);
        } // end if-statement
    } // end getSurrButtons() method

    public JLabel displayGameOptions(JFrame frame) {

        // difficulty settinigs
        JButton easy = new JButton("Easy");
        easy.setBackground(Color.lightGray);
        JButton medium = new JButton("Medium");
        medium.setBackground(Color.lightGray);
        JButton hard = new JButton("Hard");
        hard.setBackground(Color.lightGray);

        // display difficulty options and set grid size and max mines based on response
        JOptionPane diffSelector = new JOptionPane();
        diffSelector.setLayout(new GridLayout(2,1));
        diffSelector.setMessage("Choose your difficulty");
        diffSelector.setOptions(new Object[] { easy, medium, hard });

        JDialog diffDialog = diffSelector.createDialog(frame, "Difficulty");
        
        // action lister for each button in the JOptionPane
        for (Object o : diffSelector.getOptions()) {
            if (o instanceof JButton) {
                ((JButton) o).addActionListener( e ->{
                    if (o.equals(easy)) {
                        gridSize = 9;
                        maxMines = 10;                        
                    } // end if-statement
                    else if (o.equals(medium)) {
                        gridSize = 16;
                        maxMines = 20;
                    } // end else if-statement
                    else if (o.equals(hard)) {
                        gridSize = 20;
                        maxMines = 30;
                    } // end else if-statement

                    diffDialog.dispose();
                }); // end ActionListener
            } // end if-statement
        } // end for-loop

        diffDialog.setVisible(true);

        // set difficulty label above grid based on detected grid size 
        String difficulty = "Difficulty: ";

        if (gridSize == 9) {
            difficulty += "Easy";
        } // end if-statement
        else if (gridSize == 16) {
            difficulty += "Medium";
        } // end else if-statement
        else if (gridSize == 20) {
            difficulty += "Hard";
        } // end else if-statement

        JLabel diffSelected = new JLabel(difficulty);
        diffSelected.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 300));

        return diffSelected;

    } // end displayGameOptions() method

    public void endGameDialog(JFrame frame, String optionPaneMessage, String dialogTitle) {

        JButton playAgainButton = new JButton("Play again");
        JButton quitButton = new JButton("Quit game");

        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(optionPaneMessage);
        optionPane.setOptions(new Object[] { playAgainButton, quitButton });
        
        JDialog dialog = optionPane.createDialog(frame, dialogTitle);

        // action lister for each button in the JOptionPane
        for (Object o : optionPane.getOptions()) {
            if (o instanceof JButton) {
                ((JButton) o).addActionListener( e ->{
                    if (o.equals(playAgainButton)) {
                        timer.cancel();                        
                        frame.getContentPane().removeAll();
                        startGame(frame);                    
                    } // end if-statement
                    else if (o.equals(quitButton)) {
                        System.exit(0);
                    } // end else if-statement

                    dialog.dispose();
                }); // end ActionListener
            } // end if-statement
        } // end for-loop

        dialog.setVisible(true);

    } // end endGameDialog() method

    public void gameOverMessage(JFrame frame, JPanel root, int maxMines) {
        
        String optionPaneMessage = "Would you like to play again?";
        String dialogTitle = "Game Over";

        // show loss message
        endGameDialog(frame, optionPaneMessage, dialogTitle);
        
    } // end gameOverMessage() method

    public void hasWonMessage(JFrame frame, JPanel root, int score, int maxMines) {

        String optionPaneMessage = "Your Score: " + score;
        String dialogTitle = "You Won!";
        
        // show win message
        endGameDialog(frame, optionPaneMessage, dialogTitle);

    } // end hasWonMessage() method

    public Boolean hasWon(JButton[][] buttonsList) {

        int maxMines;
        int safeButtons = 0;

        // determine maximum mine count based on grid size
        if (gridSize == 9) {
            maxMines = 10;
        } // end if-statement
        else if (gridSize == 16) {
            maxMines = 20;
        } // end else if-statement
        else {
            maxMines = 30;
        } // end else-statement
        
        // for any button that is not a mine or flag, increase number of safe buttons
        for (int i=0; i<gridSize; i++) {
            for (int j=0; j<gridSize; j++) {
                if (buttonsList[j][i].getIcon() != defaultIcon && buttonsList[j][i].getIcon() != defaultMineIcon && buttonsList[j][i].getIcon() != flag) {
                    safeButtons++;
                } // end if-statement
            } // end j for-loop
        } // end i for-loop

        // if the numer of safe buttons equals the total number of buttons minus the number of mines, the player wins
        if (safeButtons == ((gridSize * gridSize) - maxMines)) {
            return true;
        } // end if-statement
        else {
            return false;
        } // end else-statement
    } // end hasWon() method

    public Boolean gameOver(JButton activeButton) {

        if (processedButtons.contains(activeButton)) {
            return false;
        } // end if-statement

        processedButtons.add(activeButton);
        revealedButtons.add(activeButton);

        getSurrButtons();

        // if a mine is clicked, the player loses~
        if (activeButton.getIcon() == defaultMineIcon) {
            revealAllMines();
            
            return true;
        } // end if-statement
        else if (activeButton.getIcon() == defaultIcon) {
            setNumSurrMines(activeButton, countMines());
            showClearSurrButtons(activeButton);

            return false;
        } // end else-statement 
        else if (activeButton.getIcon() == flag) {
            if (prevIcon == defaultMineIcon) {
                activeButton.setIcon(defaultMineIcon);
                revealAllMines();
    
                return true;
            } // end if-statement
            else {
                setNumSurrMines(activeButton, countMines());
                showClearSurrButtons(activeButton);

                return false;
            } // end else-statement
        } // end else if-statement
        else {
            return false;
        } // end else-statement

    } // end gameOver() method

    public void revealAllMines() {

        for (int i=0; i<gridSize; i++) {
            for (int j=0; j<gridSize; j++) {
                if (buttonsList[j][i].getIcon() == defaultMineIcon) {
                    buttonsList[j][i].setIcon(mineClickedIcon);
                } // end if-statement
                else if (buttonsList[j][i].getIcon() == flag) {
                    if (prevIcon == defaultMineIcon) {
                        buttonsList[j][i].setIcon(mineClickedIcon);
                    } // end if-statment
                } // end else if-statement
            } // end for-loop
        } // end for-loop
    
    } // end revealAllMines() method

    public void showClearSurrButtons(JButton activeButton) {

        int numMinesArroundButton;

        // reveal a chain of 'clear' buttons around the active buttton
        for (JButton b : surrButtons) {

            if (b.getIcon() != null) {
                if (b.getIcon() == defaultIcon) {
                    verifyActiveButton(b);
                    getSurrButtons();
                    numMinesArroundButton = countMines();

                    if (numMinesArroundButton == 0) {
                        setNumSurrMines(b, numMinesArroundButton);
                        showClearSurrButtons(b);
                    } // end if-statement                    
                } // end if-statement
                else if (b.getIcon() == flag) {
                    if (prevIcon == defaultIcon) {
                        verifyActiveButton(b);
                        getSurrButtons();
                        numMinesArroundButton = countMines();

                        if (numMinesArroundButton == 0) {
                            setNumSurrMines(b, numMinesArroundButton);
                            showClearSurrButtons(b);
                        } // end if-statement
                    } // end if-statement
                    else {
                        break;
                    } // end else-statement
                } // end else if-statement
                else {
                    break;
                } // end else-statement
            } // end if-statement
            else {
                break;
            } // end else-statement
        } // end for-loop

    } // end showClearSurrButtons() method

    public int countMines() {

        numSurrMines = 0;
        for (JButton b : surrButtons) {
            if (b.getIcon() != null) {
                if (b.getIcon() == flag) {
                    if (prevIcon == defaultMineIcon) {
                        numSurrMines++;
                    } // end if-statement
                } // end if-statement
                if (b.getIcon() == defaultMineIcon) {
                    numSurrMines++;
                } // end if-statement
            } // end if-statement
        } // end for-loop

        return numSurrMines;

    } // end countMines() method

    public void setNumSurrMines(JButton activeButton, int numSurrMines) {

        // set the number of mines around the active button 
        if (numSurrMines == 1) {
            activeButton.setIcon(oneMine);
        } // end if-statement
        else if (numSurrMines == 2) {
            activeButton.setIcon(twoMines);
        } // end else if-statement
        else if (numSurrMines == 3) {
            activeButton.setIcon(threeMines);
        } // end else if-statement
        else if (numSurrMines == 4) {
            activeButton.setIcon(fourMines);
        } // end else if-statement
        else if (numSurrMines == 5) {
            activeButton.setIcon(fiveMines);
        } // end else if-statement
        else if (numSurrMines == 6) {
            activeButton.setIcon(sixMines);
        } // end else if-statement
        else if (numSurrMines == 7) {
            activeButton.setIcon(sevenMines);
        } // end else if-statement
        else if (numSurrMines == 8) {
            activeButton.setIcon(eightMines);
        } // end else if-statement
        else if (numSurrMines == 0) {
            activeButton.setIcon(clickedIcon);
        } // end else-statement
    
    } // end setNumSurrMines() method

    // resize image icon https://coderanch.com/t/331731/java/Resize-ImageIcon
    public ImageIcon resizeButtonIcon(ImageIcon origImage, int dimSize, int numButtonsInRow) {

        Image image = origImage.getImage().getScaledInstance((dimSize/numButtonsInRow), (dimSize/numButtonsInRow), java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(image);
        return scaledImage;

    } // end resizeButtonIcon() method

    public void configureIcons() {

        // initialize and scale each icon
        defaultIcon = resizeButtonIcon(new ImageIcon("./Icons/Default.png"), dimSize, gridSize);
        defaultMineIcon = resizeButtonIcon(new ImageIcon("./Icons/MineDefault.png"), dimSize, gridSize);
        clickedIcon = resizeButtonIcon(new ImageIcon("./Icons/Clicked.png"), dimSize, gridSize);
        mineClickedIcon = resizeButtonIcon(new ImageIcon("./Icons/MineClicked.png"), dimSize, gridSize);
        oneMine = resizeButtonIcon(new ImageIcon("./Icons/1.png"), dimSize, gridSize);
        twoMines = resizeButtonIcon(new ImageIcon("./Icons/2.png"), dimSize, gridSize);
        threeMines = resizeButtonIcon(new ImageIcon("./Icons/3.png"), dimSize, gridSize);
        fourMines = resizeButtonIcon(new ImageIcon("./Icons/4.png"), dimSize, gridSize);
        fiveMines = resizeButtonIcon(new ImageIcon("./Icons/5.png"), dimSize, gridSize);
        sixMines = resizeButtonIcon(new ImageIcon("./Icons/6.png"), dimSize, gridSize);
        sevenMines = resizeButtonIcon(new ImageIcon("./Icons/7.png"), dimSize, gridSize);
        eightMines = resizeButtonIcon(new ImageIcon("./Icons/8.png"), dimSize, gridSize);
        flag = resizeButtonIcon(new ImageIcon("./Icons/Flag.png"), dimSize, gridSize);

    } // end configureIcons() method

    public void showHelpScreen(JFrame frame) {
        
        JButton continueButton = new JButton("Continue");
        
        continueButton.setBackground(Color.lightGray);
        JOptionPane optionPane = new JOptionPane();
        
        String optionPaneMessage = "Welcome to Minesweeper! The goal is to uncover all spaces on the grid as fast as possible without uncovering any of the mines. Left clicking a space will"
        + "\nuncover it and if any spaces around it are armed with a mine, the selected space will display the number of surrounding mines. If there are no mines, the space will" 
        + "\nbe blank. Any time a space is uncovered, all spaces around it with zero mines around them will also be uncovered. Since the score is how long it takes for all non-"
        + "\nmine spaces to be uncovered, the aim to get as low a score as possible. If a mine is uncovered, all mines will be revealed and the game will end. If the player" 
        + "\nthinks they know where a mine is, the space can be flagged by right clicking the space (right clicking again will remove the flag). There are three difficulty"
        + "\nlevels: Easy (9 x 9 grid with 10 mines), Medium (16 x 16 grid with 20 mines), and Hard (20 x 20 grid with 30 mines). Once the game begins, the selected difficulty" 
        + "\nand time will be displayed above the grid. Good luck!"
        + "\nHint: It is better to work right to left as more 'safe' spaces will be revealed";

        optionPane.setMessage(optionPaneMessage);
        optionPane.setOptions(new Object[] { continueButton });
        
        JDialog dialog = optionPane.createDialog(frame, "Instructions");

        for (Object o : optionPane.getOptions()) {
            if (o instanceof JButton) {
                ((JButton) o).addActionListener( e ->{
                    dialog.dispose();
                }); // end ActionListener
            } // end if-statement
        } // end for-loop

        dialog.setVisible(true);

    } // end showHelpScreen() method

    public static void main(String[] args) {

        new Minesweeper();
    
    } // end main() method

} // end Minesweeper class
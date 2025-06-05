import javax.swing.*;//GUI component -for button ,label ,frame//part of jfc java foundation classes which provide GUI
import java.awt.*;//layout and design,color,font//abstract window tool kit
import java.awt.event.ActionEvent;//handle button click events(represent a event when click button)
import java.awt.event.ActionListener;//it define what action perform when event occur
import java.util.Random;//to roll dice randomly(used to genrate the random number)
//actionevent and actionlistener are not a part of core AWT component library they are place in a separate sub package for  better organization
class Dice {
    Random random = new Random();

    public int roll() {//it return the dice value
        return random.nextInt(6) + 1;//random number between 1 to 6
    }
}

class Player {
    String name;//name of player
    int position;//position of dice on the board(started from zero)
    boolean hasStarted;//needs 6 to start in ludo
    int consecutiveSixes;//count how many time player roll 6 continously

    public Player(String name) {//constructor of the class
        this.name = name;
        this.position = 0;
        this.hasStarted = false;
        this.consecutiveSixes=0;
    }

    public void move(int steps) {//move the player according to the dice roll two condition for one started dice and one for dice which not started yet
        if (hasStarted) {//if already started 
            position += steps;
            if (position > 50) {//supppose we r at 47 and dice roll 6 then 50-(53-50)=50-3=47
                position = 50 - (position - 50); // bounce back
            }
        } else {//if not started
            if (steps == 6) {
                hasStarted = true;
                position = 1;
            }
        }
    }

    public boolean hasWon() {
        return position == 50;
    }
}

public class LudoGameGUI extends JFrame implements ActionListener {//make window (jframe is window in which gui component are placed)
    private JLabel player1Label, player2Label, diceResultLabel, statusLabel;//label to show text
    private JButton rollDiceButton;//button to roll dice
    private Player player1, player2, currentPlayer;
    private Dice dice;

    public LudoGameGUI() {//set window title,size layout
        setTitle("Ludo Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Set background color of the frame (getcontentpane refer to the main container of jframe)
        getContentPane().setBackground(Color.LIGHT_GRAY); // Set the background color of the window

        player1 = new Player("Priti");
        player2 = new Player("Diya");
        currentPlayer = player1;
        dice = new Dice();

        player1Label = new JLabel("Player 1 Position: 0", SwingConstants.CENTER);
        player2Label = new JLabel("Player 2 Position: 0", SwingConstants.CENTER);
        diceResultLabel = new JLabel("Roll the dice!", SwingConstants.CENTER);
        statusLabel = new JLabel("Player 1's turn", SwingConstants.CENTER);
        rollDiceButton = new JButton("Roll Dice");

        // Set color and font for labels and button
        player1Label.setForeground(Color.RED);  // Set text color for player1 label
        player2Label.setForeground(Color.BLUE); // Set text color for player2 label
        diceResultLabel.setForeground(Color.RED);  // Set text color for dice result label
        statusLabel.setForeground(Color.BLUE); // Set text color for status label
        rollDiceButton.setBackground(Color.PINK); // Set background color of the button
        rollDiceButton.setForeground(Color.BLACK); // Set text color for the button

        // Set font for all labels and button
        Font font = new Font("Arial", Font.BOLD, 14);
        player1Label.setFont(font);
        player2Label.setFont(font);
        diceResultLabel.setFont(font);
        statusLabel.setFont(font);
        rollDiceButton.setFont(font);

        rollDiceButton.addActionListener(this);//when button is click it called the action performed

        add(player1Label);//add all component in window
        add(player2Label);
        add(diceResultLabel);
        add(statusLabel);
        add(rollDiceButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int roll = dice.roll();//function
        diceResultLabel.setText(currentPlayer.name + " rolled a " + roll);
        if (roll==6){//if roll==6 then increment consecutiveSixes
            currentPlayer.consecutiveSixes++;
        }
        else{
            currentPlayer.consecutiveSixes=0;
        }
        if(currentPlayer.consecutiveSixes==5){
            statusLabel.setText(currentPlayer.name+"wins by rolling five 6s!");
            rollDiceButton.setEnabled(false);
            return;
        }

        currentPlayer.move(roll);//function

        player1Label.setText("Player 1 Position: " + player1.position);//update position
        player2Label.setText("Player 2 Position: " + player2.position);

        if (currentPlayer.hasWon()) {
            statusLabel.setText(currentPlayer.name + " wins!");//when player win then disablec the dice button
            rollDiceButton.setEnabled(false);
            return;
        }

        if (roll != 6) {
            currentPlayer = (currentPlayer == player1) ? player2 : player1;//ternary operator condfition?true:false
        }

        statusLabel.setText(currentPlayer.name + "'s turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {//ensure program follow the rule and display the window safely
            LudoGameGUI game = new LudoGameGUI();//constructor
            game.setVisible(true);//object game /window show on a screen 
        });//it tells the program to display the window that u just created
    }
}
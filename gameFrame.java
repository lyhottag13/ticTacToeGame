import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;

public class gameFrame extends JFrame implements ActionListener {
    // declaration for the frame.
    private JFrame frame;

    // for the two different areas of the app, the top (board) and the bottom (prompts).
    private JPanel background = new JPanel();
    private JPanel board = new JPanel();
    private JPanel prompts = new JPanel();

    // for the prompts.
    private JLabel where = new JLabel("Where would you like to move?");
    private JTextField field = new JTextField(4);
    private JButton confirm = new JButton("Confirm");
    private JLabel warning = new JLabel("");

    // for the board pieces. These will display either X, O, or nothing.
    private JLabel one = new JLabel("1");
    private JLabel two = new JLabel("2");
    private JLabel three = new JLabel("3");
    private JLabel four = new JLabel("4");
    private JLabel five = new JLabel("5");
    private JLabel six = new JLabel("6");
    private JLabel seven = new JLabel("7");
    private JLabel eight = new JLabel("8");
    private JLabel nine = new JLabel("9");
    Font font = new Font("Cambria", Font.PLAIN, 230);

    // other logical variables for game progression.
    boolean turnDone;
    ArrayList<Integer> usedBoardPlaces = new ArrayList<>();
    int turn = 0;
    

    public gameFrame() {
        frame = new JFrame("Tic-Tac-Toe Game");
        frame.add(background);
        background.setLayout(new BorderLayout());
        background.add(board, BorderLayout.NORTH);
        background.add(prompts, BorderLayout.CENTER);

        board.setLayout(new GridLayout(3, 3));

        board.add(one);
        board.add(two);
        board.add(three);
        board.add(four);
        board.add(five);
        board.add(six);
        board.add(seven);
        board.add(eight);
        board.add(nine);

        one.setFont(font);
        two.setFont(font);
        three.setFont(font);
        four.setFont(font);
        five.setFont(font);
        six.setFont(font);
        seven.setFont(font);
        eight.setFont(font);
        nine.setFont(font);

        prompts.add(where);
        prompts.add(field);
        prompts.add(confirm);
        prompts.add(warning);
        warning.setFont(new Font("Cambria", Font.BOLD, 30));
        where.setFont(new Font("Cambria", Font.PLAIN, 33));

        frame.setSize(new Dimension(500, 1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        confirm.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == confirm) {
            int selection;
            // checks for a valid input.
            try {
                selection = Integer.parseInt(field.getText());
                field.setText("");
                // checks for a valid input (again)
                if (selection <= 9 && selection >= 1 && !usedBoardPlaces.contains(selection)) {
                    // goes to the next turn and plays the player and bot turns. If either wins before the other, then the game ends.
                    warning.setText("");
                    turn++;
                    setBoard(selection, "X");
                    if (gameEndCheck("X")) {
                        return;
                    }
                    botTurn();
                    if (gameEndCheck("O")) {
                        return;
                    }
                    // invalid input catch.
                } else {
                    warning.setText("Enter a valid number from 1 to 9!");
                }
                // invalid input catch.
            } catch (Exception e) {
                warning.setText("Enter a valid number from 1 to 9!");
            }
        }
    }

    // method to set each individual board piece to the desired selection, either X or O.
    public void setBoard(int boardSelection, String swapTo) {
        switch (boardSelection) {
            case 1:
                one.setText(swapTo);
                break;
            case 2:
                two.setText(swapTo);
                break;
            case 3:
                three.setText(swapTo);
                break;
            case 4:
                four.setText(swapTo);
                break;
            case 5:
                five.setText(swapTo);
                break;
            case 6:
                six.setText(swapTo);
                break;
            case 7:
                seven.setText(swapTo);
                break;
            case 8:
                eight.setText(swapTo);
                break;
            case 9:
                nine.setText(swapTo);
                break;
            default:
        }
        usedBoardPlaces.add(boardSelection);
    }

    public void botTurn() {
        if (turn == 5) {
            prompts.remove(confirm);
            prompts.remove(where);
            prompts.remove(field);
            frame.revalidate();
            frame.repaint();
            warning.setText("Draw!");
            return;
        }
        Random random = new Random();
        int botTurn = 0;
        do {
            botTurn = random.nextInt(9) + 1;
        } while (usedBoardPlaces.contains(botTurn));
        setBoard(botTurn, "O");
    }

    public boolean gameEndCheck(String check) {
        // this statement is long, but it checks all eight winning positions.
        if (check(one, two, three, check) || check(four, five, six, check) || check(seven, eight, nine, check) || check(one, four, seven, check) || check(two, five, eight, check) || check(three, six, nine, check) || check(one, five, nine, check) || check(three, five, seven, check)) {
            prompts.remove(confirm);
            prompts.remove(where);
            prompts.remove(field);
            frame.revalidate();
            frame.repaint();
            if (check.equals("X")) {
                warning.setText("You win!");
            } else {
                warning.setText("Bot wins!");
            }
            return true;
        }
        return false;
    }

    // used to check if any of the winning positions are in play.
    public boolean check(JLabel first, JLabel second, JLabel third, String check) {
        if (first.getText().equals(check) && second.getText().equals(check) && third.getText().equals(check)) {
            return true;
        }
        return false;
    }
}

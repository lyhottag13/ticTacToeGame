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

    // for the board pieces. These will display either X, O, or the position.
    private JLabel[] boardPieces = new JLabel[9];

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

        for (int i = 0; i < boardPieces.length; i++) {
            boardPieces[i] = new JLabel(String.valueOf(i + 1));
            board.add(boardPieces[i]);
            boardPieces[i].setFont(font);
        }

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
        boardPieces[boardSelection - 1].setText(swapTo);
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
        // Choose a spot until it selects an unchosen spot.
        do {
            botTurn = random.nextInt(9) + 1;
        } while (usedBoardPlaces.contains(botTurn));
        setBoard(botTurn, "O");
    }

    public boolean gameEndCheck(String check) {
        // This statement is long, but it checks all eight winning positions.
        if (check(boardPieces[0], boardPieces[1], boardPieces[2], check) || check(boardPieces[3], boardPieces[4], boardPieces[5], check) || check(boardPieces[6], boardPieces[7], boardPieces[8], check) || check(boardPieces[0], boardPieces[3], boardPieces[6], check) || check(boardPieces[1], boardPieces[4], boardPieces[7], check) || check(boardPieces[2], boardPieces[5], boardPieces[8],check) || check(boardPieces[0], boardPieces[4], boardPieces[8], check) || check(boardPieces[2], boardPieces[4], boardPieces[6], check)) {
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

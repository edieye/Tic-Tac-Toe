/**
 * Created by edieye on 2018-05-09.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener {

    private static int BOARD_SIZE = 3;

    private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];

    private boolean whoseTurn = true;

    private enum GameStatus {
        Draw, Incomplete, XWins, OWins
    }

    public TicTacToe() {
        // set up the board
        super.setTitle("Tic Tac Toe Game");
        super.setSize(600, 600);
        GridLayout gridLayout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
        super.setLayout(gridLayout);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                JButton button = new JButton("");
                buttons[i][j] = button;
                button.addActionListener(this);
                super.add(button);
            }
        }
        super.setResizable(false);
        super.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String text = clickedButton.getText();
        if (text.length() > 0) {
            JOptionPane.showMessageDialog(this, "Invalid Move!");
        }
        else {
            if (whoseTurn) {
                clickedButton.setText("X");
            } else {
                clickedButton.setText("0");
            }
            whoseTurn = !whoseTurn;
        }

        GameStatus gs = this.getGameStatus();
        if (gs == GameStatus.Incomplete) {
            return;
        }
        declareWinner(gs);

        int choice = JOptionPane.showConfirmDialog(this, "Do you want to play again?");
        if (choice == JOptionPane.YES_OPTION) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    buttons[i][j].setText("");
                }
            }
            whoseTurn = true;
        } else
            super.dispose();
    }

    private void declareWinner(GameStatus gameStatus) {
        if (gameStatus == GameStatus.XWins) {
            JOptionPane.showMessageDialog(this, "X Won! Game Over!");
        }
        else if (gameStatus == GameStatus.OWins) {
            JOptionPane.showMessageDialog(this, "0 Wins! Game Over!");
        }
        else {
            JOptionPane.showMessageDialog(this, "It's a Tie! Game Over!");
        }
    }

    private GameStatus getGameStatus() {
        String check; int i;

        // check for win in rows
        for (i = 0; i < BOARD_SIZE; i++) {
            check = buttons[i][0].getText();
            if (check.equals("X") || check.equals("0")) {
                if (check.equals(buttons[i][1].getText())
                        && check.equals(buttons[i][2].getText())) {
                    return getStatus(check);
                }
            }
        }
        // check for win in columns
        for (i = 0; i < BOARD_SIZE; i++) {
            check = buttons[0][i].getText();
            if (check.equals("X") || check.equals("0")) {
                if (check.equals(buttons[1][i].getText())
                        && check.equals(buttons[2][i].getText())) {
                    return getStatus(check);
                }
            }
        }
        // check for first diagonal
        check = buttons[0][0].getText();
        if (check.equals("X") || check.equals("0")) {
            if (check.equals(buttons[1][1].getText()) && check.equals(buttons[2][2].getText())) {
                return getStatus(check);
            }
        }

        // check for second diagonal
        check = buttons[0][2].getText();
        if (check.equals("X") || check.equals("0")) {
            if (check.equals(buttons[1][1].getText()) && check.equals(buttons[2][0].getText())) {
               return getStatus(check);
            }
        }

        //check for incomplete game
        for (i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                check = buttons[i][j].getText();
                if (check.length() == 0) {
                    return GameStatus.Incomplete;
                }
            }
        }

        return GameStatus.Draw;
    }

    private GameStatus getStatus(String check) {
        if (check.equals("X")) {
            return GameStatus.XWins;
        }
        return GameStatus.OWins;
    }
}
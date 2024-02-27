import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToefull {
    static boolean x = true;

    public static void main(String... rs) {
        JFrame f = new JFrame("Tic Tac Toe");
        f.setSize(700, 700);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(0, 0, 255, 50));
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel msg = new JLabel("Copyright Reserved @ Rajveer Singh");
        f.add(msg, BorderLayout.SOUTH);

        JButton[] arr = new JButton[9];
        GridLayout gl = new GridLayout(3, 3);
        JPanel p = new JPanel(gl);

        for (int i = 0; i < 9; i++) {
            JButton b = new JButton("");
            b.setFont(new Font("Arial", Font.PLAIN, 40));
            b.setFocusPainted(false);
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (b.getText().equals("") && x) {
                        b.setText("X");
                    } else if (b.getText().equals("") && !x) {
                        b.setText("O");
                    }

                    x = !x;

                    if (checkForWin(arr)) {
                        JOptionPane.showMessageDialog(f, (x ? "O" : "X") + " wins!");
                        resetGame(arr);
                    } else if (isBoardFull(arr)) {
                        JOptionPane.showMessageDialog(f, "It's a draw!");
                        resetGame(arr);
                    }
                }
            });

            arr[i] = b;
            p.add(b);
        }

        f.add(p);
        f.setVisible(true);
    }

    private static boolean checkForWin(JButton[] arr) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (!arr[i * 3].getText().equals("") &&
                arr[i * 3].getText().equals(arr[i * 3 + 1].getText()) &&
                arr[i * 3 + 1].getText().equals(arr[i * 3 + 2].getText())) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (!arr[i].getText().equals("") &&
                arr[i].getText().equals(arr[i + 3].getText()) &&
                arr[i + 3].getText().equals(arr[i + 6].getText())) {
                return true;
            }
        }

        // Check diagonals
        if (!arr[0].getText().equals("") &&
            arr[0].getText().equals(arr[4].getText()) &&
            arr[4].getText().equals(arr[8].getText())) {
            return true;
        }

        if (!arr[2].getText().equals("") &&
            arr[2].getText().equals(arr[4].getText()) &&
            arr[4].getText().equals(arr[6].getText())) {
            return true;
        }

        return false;
    }

    private static boolean isBoardFull(JButton[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    private static void resetGame(JButton[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i].setText("");
        }
        x = true;
    }
}

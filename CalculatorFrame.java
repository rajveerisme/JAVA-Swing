import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorFrame extends JFrame implements ActionListener {

    private JTextField display;
    private StringBuilder currentInput;
    private double result;
    private char lastOperator;

    public CalculatorFrame() {
        super("Calculator");

        // Create the display field
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);

        // Initialize variables
        currentInput = new StringBuilder();
        result = 0.0;
        lastOperator = ' ';

        // Create buttons for numbers 0 to 9
        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
        }

        // Create buttons for mathematical operators
        JButton addButton = new JButton("+");
        JButton subtractButton = new JButton("-");
        JButton multiplyButton = new JButton("*");
        JButton divideButton = new JButton("/");
        JButton equalsButton = new JButton("=");

        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        divideButton.addActionListener(this);
        equalsButton.addActionListener(this);

        // Create a Clear button and a Backspace button
        JButton clearButton = new JButton("C");
        JButton backspaceButton = new JButton("⌫");

        clearButton.addActionListener(this);
        backspaceButton.addActionListener(this);

        // Set layout for the frame
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));

        // Add number buttons to the panel
        for (int i = 7; i <= 9; i++) {
            buttonPanel.add(numberButtons[i]);
        }
        buttonPanel.add(divideButton);

        for (int i = 4; i <= 6; i++) {
            buttonPanel.add(numberButtons[i]);
        }
        buttonPanel.add(multiplyButton);

        for (int i = 1; i <= 3; i++) {
            buttonPanel.add(numberButtons[i]);
        }
        buttonPanel.add(subtractButton);

        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(equalsButton);
        buttonPanel.add(addButton);

        // Add the Clear button and Backspace button to the right of the equals button
        buttonPanel.add(clearButton);
        buttonPanel.add(backspaceButton);

        // Add components to the frame
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorFrame());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = ((JButton) e.getSource()).getText();

        if (isNumeric(buttonText)) {
            // If the button clicked is a number, replace the current input with the new number
            currentInput.setLength(0);
            currentInput.append(buttonText);
            display.setText(currentInput.toString());
        } else if (isOperator(buttonText)) {
            // If the button clicked is an operator, perform the previous calculation if any
            performCalculation();
            lastOperator = buttonText.charAt(0);
        } else if (buttonText.equals("=")) {
            // If the "=" button is clicked, perform the final calculation
            performCalculation();
            display.setText(String.valueOf(result));
            currentInput.setLength(0);
            currentInput.append(result);
            lastOperator = ' ';
        } else if (buttonText.equals("C")) {
            // If the "C" button is clicked, clear the display and reset variables
            display.setText("");
            currentInput.setLength(0);
            result = 0.0;
            lastOperator = ' ';
        } else if (buttonText.equals("⌫")) {
            // If the "⌫" (Backspace) button is clicked, remove the last character from the current input
            if (currentInput.length() > 0) {
                currentInput.deleteCharAt(currentInput.length() - 1);
                display.setText(currentInput.toString());
            }
        }
    }

    private boolean isNumeric(String input) {
        return input.matches("[0-9]");
    }

    private boolean isOperator(String input) {
        return input.matches("[+\\-*/]");
    }

    private void performCalculation() {
        if (currentInput.length() > 0) {
            double currentNumber = Double.parseDouble(currentInput.toString());

            switch (lastOperator) {
                case '+':
                    result += currentNumber;
                    break;
                case '-':
                    result -= currentNumber;
                    break;
                case '*':
                    result *= currentNumber;
                    break;
                case '/':
                    if (currentNumber != 0) {
                        result /= currentNumber;
                    } else {
                        display.setText("Error");
                        currentInput.setLength(0);
                        result = 0.0;
                    }
                    break;
                default:
                    result = currentNumber;
                    break;
            }
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GuessTheNumberGame extends JFrame {
    private int answer, noOfGuesses;
    private List<Integer> guessedNums;

    private JTextField guessInput;
    private JLabel hintLabel;
    private JLabel noOfGuessesLabel;
    private JLabel guessedNumsLabel;
    private JButton checkButton;
    private JButton restartButton;

    public GuessTheNumberGame() {
        setTitle("Guess The Number Game");
        setSize(500, 500); // Fixed size for the window
        setResizable(false); // Prevent resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on screen
        setLayout(null); // Disable the layout manager

        // Instructions label
        JLabel instructions = new JLabel("I am thinking of a number between 1-100.");
        instructions.setBounds(40, 20, 420, 40); // Set bounds manually
        instructions.setHorizontalAlignment(SwingConstants.CENTER);
        instructions.setFont(new Font("Poppins", Font.PLAIN, 18)); // Use Poppins font
        add(instructions);

        JLabel instructions1 = new JLabel("Can you guess it?");
        instructions1.setBounds(40, 50, 420, 40); // Set bounds manually
        instructions1.setHorizontalAlignment(SwingConstants.CENTER);
        instructions1.setFont(new Font("Poppins", Font.PLAIN, 18)); // Use Poppins font
        add(instructions1);

        // Input field with curved rectangle
        guessInput = new CurvedTextField(20); // 20 is the corner radius
        guessInput.setBounds(200, 100, 100, 50); // Set bounds manually
        guessInput.setHorizontalAlignment(JTextField.CENTER);
        guessInput.setFont(new Font("Arial", Font.PLAIN, 18)); // Use Poppins font
        add(guessInput);

        // Guess button with curved rectangle
        checkButton = new CurvedButton("Guess", 20); // 20 is the corner radius
        checkButton.setBounds(200, 160, 100, 50); // Set bounds manually
        checkButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Use Poppins font
        checkButton.setForeground(Color.WHITE);
        checkButton.setBackground(new Color(60, 111, 255)); // Background color
        add(checkButton);

        // Guesses label
        noOfGuessesLabel = new JLabel("No. Of Guesses: 0");
        noOfGuessesLabel.setBounds(40, 220, 420, 40); // Set bounds manually
        noOfGuessesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noOfGuessesLabel.setFont(new Font("Poppins", Font.PLAIN, 16)); // Use Poppins font
        noOfGuessesLabel.setForeground(new Color(86, 91, 112)); // Text color
        add(noOfGuessesLabel);

        // Guessed numbers label
        guessedNumsLabel = new JLabel("Guessed Numbers are: None");
        guessedNumsLabel.setBounds(40, 250, 420, 40); // Set bounds manually
        guessedNumsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        guessedNumsLabel.setFont(new Font("Poppins", Font.PLAIN, 16)); // Use Poppins font
        guessedNumsLabel.setForeground(new Color(86, 91, 112)); // Text color
        add(guessedNumsLabel);

        // Hint label
        hintLabel = new JLabel("");
        hintLabel.setBounds(40, 280, 420, 70); // Set bounds manually
        hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hintLabel.setFont(new Font("Poppins", Font.PLAIN, 18)); // Use Poppins font
        add(hintLabel);

        // Restart button with curved rectangle
        restartButton = new CurvedButton("Restart", 20); // 20 is the corner radius
        restartButton.setBounds(200, 370, 100, 50); // Set bounds manually
        restartButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Use Poppins font
        restartButton.setBackground(new Color(60, 111, 255)); // Background color
        restartButton.setForeground(Color.WHITE); // Text color
        restartButton.setVisible(false);
        add(restartButton);

        // Add action listeners
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        guessInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initGame();
            }
        });

        // Initialize the game
        initGame();
    }

    private void play() {
        try {
            int userGuess = Integer.parseInt(guessInput.getText());
            if (userGuess < 1 || userGuess > 100) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number between 1 and 100.");
                return;
            }

            guessedNums.add(userGuess);
            noOfGuesses++;
            updateLabels();

            if (userGuess < answer) {
                hintLabel.setText("Too low. Try Again!");
                hintLabel.setForeground(Color.RED);
            } else if (userGuess > answer) {
                hintLabel.setText("Too high. Try Again!");
                hintLabel.setForeground(Color.RED);
            } else {
                hintLabel.setText("<html>Congratulations!<br>The number was " + answer + ".<br>You guessed it in " + noOfGuesses + " tries.</html>");
                hintLabel.setForeground(new Color(5, 196, 81));
                checkButton.setEnabled(false);
                guessInput.setEditable(false);
                restartButton.setVisible(true);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    private void initGame() {
        answer = (int) (Math.random() * 100) + 1;
        noOfGuesses = 0;
        guessedNums = new ArrayList<>();
        updateLabels();
        guessInput.setText("");
        guessInput.setEditable(true);
        hintLabel.setText("");
        checkButton.setEnabled(true);
        hintLabel.setForeground(Color.RED);
        restartButton.setVisible(false);
    }

    private void updateLabels() {
        noOfGuessesLabel.setText("No. Of Guesses: " + noOfGuesses);
        String guessedNumbers = guessedNums.isEmpty() ? "None" : guessedNums.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        guessedNumsLabel.setText("Guessed Numbers are: " + guessedNumbers);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuessTheNumberGame().setVisible(true);
            }
        });
    }

    // Custom JTextField with curved edges
    class CurvedTextField extends JTextField {
        private int radius;

        CurvedTextField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder()); // Remove default border
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No border needed
        }
    }

    // Custom JButton with curved edges
    class CurvedButton extends JButton {
        private int radius;

        CurvedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No border needed
        }
    }
}

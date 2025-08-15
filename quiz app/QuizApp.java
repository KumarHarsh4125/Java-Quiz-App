import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizApp extends JFrame {

    private String[] questions = {
            "What is the capital of France?",
            "Which planet is known as the Red Planet?",
            "Who wrote 'Romeo and Juliet'?",
            "What is the boiling point of water?",
            "What is the largest mammal?"
    };

    private String[][] options = {
            {"Berlin", "Madrid", "Paris", "London"},
            {"Earth", "Mars", "Jupiter", "Venus"},
            {"William Shakespeare", "Charles Dickens", "Mark Twain", "Leo Tolstoy"},
            {"100°C", "90°C", "120°C", "80°C"},
            {"Elephant", "Blue Whale", "Giraffe", "Hippopotamus"}
    };

    private int[] answers = {2, 1, 0, 0, 1};  // Correct options indexes

    private int currentQuestion = 0;
    private int score = 0;

    private JLabel lblQuestion;
    private JRadioButton[] radioOptions;
    private ButtonGroup optionsGroup;
    private JButton btnNext;
    private JLabel lblTimer;

    private int timeRemaining = 300; // 5 minutes = 300 seconds
    private Timer timer;

    public QuizApp() {
        setTitle("Simple Quiz Application");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Timer label
        lblTimer = new JLabel("Time left: 05:00", SwingConstants.CENTER);
        lblTimer.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTimer.setForeground(Color.RED);
        add(lblTimer, BorderLayout.SOUTH);

        // Question label
        lblQuestion = new JLabel("Question");
        lblQuestion.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblQuestion.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(lblQuestion, BorderLayout.NORTH);

        // Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        radioOptions = new JRadioButton[4];
        optionsGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            radioOptions[i] = new JRadioButton();
            radioOptions[i].setFont(new Font("SansSerif", Font.PLAIN, 16));
            optionsGroup.add(radioOptions[i]);
            optionsPanel.add(radioOptions[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        // Next button
        btnNext = new JButton("Next");
        btnNext.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(btnNext, BorderLayout.EAST);

        btnNext.addActionListener(e -> {
            if (isAnswerSelected()) {
                checkAnswer();
                currentQuestion++;
                if (currentQuestion < questions.length) {
                    loadQuestion(currentQuestion);
                } else {
                    endQuiz();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an answer.");
            }
        });

        // Start quiz
        loadQuestion(currentQuestion);
        startTimer();
    }

    private void loadQuestion(int index) {
        lblQuestion.setText("Q" + (index + 1) + ": " + questions[index]);
        optionsGroup.clearSelection();
        for (int i = 0; i < 4; i++) {
            radioOptions[i].setText(options[index][i]);
        }
        if (index == questions.length - 1) {
            btnNext.setText("Submit");
        } else {
            btnNext.setText("Next");
        }
    }

    private boolean isAnswerSelected() {
        for (JRadioButton radio : radioOptions) {
            if (radio.isSelected()) {
                return true;
            }
        }
        return false;
    }

    private void checkAnswer() {
        for (int i = 0; i < 4; i++) {
            if (radioOptions[i].isSelected() && i == answers[currentQuestion]) {
                score++;
            }
        }
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            timeRemaining--;
            int minutes = timeRemaining / 60;
            int seconds = timeRemaining % 60;
            lblTimer.setText(String.format("Time left: %02d:%02d", minutes, seconds));

            if (timeRemaining <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Time's up!");
                endQuiz();
            }
        });
        timer.start();
    }

    private void endQuiz() {
        if (timer != null) {
            timer.stop();
        }
        JOptionPane.showMessageDialog(this, "Quiz Over! Your score: " + score + "/" + questions.length);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuizApp().setVisible(true);
        });
    }
}

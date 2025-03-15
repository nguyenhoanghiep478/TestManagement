package GUI;

import DAO.impl.TestDAO;
import Service.ITestService;
import Service.impl.TestService;
import Utils.Mapper.impl.TestMapper;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestForm {
    private JFrame frame;
    private JPanel mainPanel, questionPanel, optionsPanel, navigationPanel;
    private JLabel questionLabel, timerLabel;
    private JButton submitButton, prevButton, nextButton;
    private JButton[] questionButtons;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;
    private int currentQuestion = 0;
    private int timeLeft;
    private Timer timer;
    private List<String> questions;
    private List<List<String>> options;
    private List<Integer> correctAnswers;
    private int[] selectedAnswers;
    private ITestService testService;
    private String testCode;
    private int userId;
    private String exCode;

    public TestForm(String testCode,int userId,String exCode) {
        this.testCode = testCode;
        this.exCode = exCode;
        this.testService = new TestService(new TestDAO(new TestMapper()));
        this.userId = userId;
        loadTestData();

        frame = new JFrame("Test Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 680);
        frame.setLayout(new BorderLayout());
        frame.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
                testService.logAction("Người dùng quay lại ứng dụng",userId,testCode);
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                testService.logAction("Người dùng rời khỏi ứng dụng",userId,testCode);
            }
        });
        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        timerLabel = new JLabel("Thời gian còn lại: " + formatTime(timeLeft), SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(timerLabel, BorderLayout.NORTH);

        navigationPanel = new JPanel(new BorderLayout());
        JPanel questionButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        prevButton = new JButton("←");
        prevButton.setFont(new Font("Arial", Font.BOLD, 14));
        prevButton.setPreferredSize(new Dimension(50, 40));
        prevButton.addActionListener(e -> navigateQuestion(-1));
        navigationPanel.add(prevButton, BorderLayout.WEST);

        questionButtons = new JButton[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            final int index = i;
            questionButtons[i] = new JButton(String.valueOf(i + 1));
            questionButtons[i].setPreferredSize(new Dimension(35, 35));
            questionButtons[i].setFont(new Font("Arial", Font.BOLD, 12));
            questionButtons[i].addActionListener(e -> setCurrentQuestion(index));
            questionButtons[i].setBackground(Color.LIGHT_GRAY);
            questionButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            questionButtonPanel.add(questionButtons[i]);
        }

        JScrollPane scrollPane = new JScrollPane(questionButtonPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(800, 50));
        navigationPanel.add(scrollPane, BorderLayout.CENTER);

        nextButton = new JButton("→");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(50, 40));
        nextButton.addActionListener(e -> navigateQuestion(1));
        navigationPanel.add(nextButton, BorderLayout.EAST);

        submitButton = new JButton("Nộp bài");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(120, 40));
        submitButton.addActionListener(e -> submitTest());
        navigationPanel.add(submitButton, BorderLayout.SOUTH);

        mainPanel.add(navigationPanel, BorderLayout.SOUTH);

        questionPanel = new JPanel(new GridLayout(2, 1));
        questionLabel = new JLabel(questions.get(currentQuestion), SwingConstants.CENTER);
        questionPanel.add(questionLabel);

        optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
            final int index = i;
            optionButtons[i].addActionListener(e -> {
                testService.logAction("Người dùng chọn đáp án " + (char)('A' + index) + " cho câu " + (currentQuestion + 1),userId,testCode);
                selectedAnswers[currentQuestion] = index;
                updateQuestionButtons();
            });
            optionGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        questionPanel.add(optionsPanel);
        mainPanel.add(questionPanel, BorderLayout.CENTER);

        timer = new Timer(1000, new TimerListener());
        timer.start();

        frame.setVisible(true);
        updateQuestionButtons();
    }


    private void loadTestData() {
        timeLeft = testService.getTestTime(testCode) * 60;
        questions = testService.getQuestions(testCode);
        options = testService.getAnswers(testCode);
        correctAnswers = testService.getCorrectAnswers(testCode);
        selectedAnswers = new int[questions.size()];
        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1;
        }
    }

    private void setCurrentQuestion(int index) {
        currentQuestion = index;
        questionLabel.setText(questions.get(currentQuestion));
        optionGroup.clearSelection();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options.get(currentQuestion).get(i));
            if (selectedAnswers[currentQuestion] == i) {
                optionButtons[i].setSelected(true);
            }
        }
        updateQuestionButtons();
    }

    private void navigateQuestion(int direction) {
        int newIndex = currentQuestion + direction;
        if (newIndex >= 0 && newIndex < questions.size()) {
            setCurrentQuestion(newIndex);
        }
    }

    private void updateQuestionButtons() {
        for (int i = 0; i < questions.size(); i++) {
            if (i == currentQuestion) {
                questionButtons[i].setBackground(Color.BLUE);
                questionButtons[i].setForeground(Color.WHITE);
            } else if (selectedAnswers[i] != -1) {
                questionButtons[i].setBackground(Color.GREEN);
                questionButtons[i].setText("✔");
            } else {
                questionButtons[i].setBackground(Color.LIGHT_GRAY);
                questionButtons[i].setText(String.valueOf(i + 1));
                questionButtons[i].setForeground(Color.BLACK);
            }
        }
    }

    private void submitTest() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn nộp bài?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int correctCount = 0;
            for (int i = 0; i < selectedAnswers.length; i++) {
                if (selectedAnswers[i] == correctAnswers.get(i)) {
                    correctCount++;
                }
            }
            double score = 0.4 * correctCount;
            String answersJson = "[" + Arrays.stream(selectedAnswers)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(",")) + "]";

            this.testService.saveResult(answersJson,this.userId,this.exCode,score);
            showResultPage();
        }
    }

    private void showResultPage() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JLabel resultLabel = new JLabel("Kết quả bài làm của bạn:", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(resultLabel, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        for (int i = 0; i < questions.size(); i++) {
            JPanel questionResultPanel = new JPanel();
            questionResultPanel.setLayout(new BoxLayout(questionResultPanel, BoxLayout.Y_AXIS));
            questionResultPanel.setBorder(BorderFactory.createTitledBorder(questions.get(i)));
            questionResultPanel.setBackground(Color.WHITE);

            JPanel optionsResultPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            for (int j = 0; j < 4; j++) {
                JLabel optionLabel = new JLabel(options.get(i).get(j), SwingConstants.CENTER);
                optionLabel.setOpaque(true);
                optionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                optionLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                optionLabel.setPreferredSize(new Dimension(200, 40));

                if (j == correctAnswers.get(i)) {
                    optionLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    optionLabel.setBackground(new Color(200, 255, 200));
                }
                if (selectedAnswers[i] == j && selectedAnswers[i] != correctAnswers.get(i)) {
                    optionLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                    optionLabel.setBackground(new Color(255, 200, 200));
                }
                optionsResultPanel.add(optionLabel);
            }

            questionResultPanel.add(optionsResultPanel);
            resultPanel.add(questionResultPanel);
        }

        frame.revalidate();
        frame.repaint();
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timeLeft > 0) {
                timeLeft--;
                timerLabel.setText("Thời gian còn lại: " + formatTime(timeLeft));
            } else {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Hết thời gian!");
                submitTest();
            }
        }
    }


}

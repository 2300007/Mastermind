package com.mycompany.mastermindapp;

import javax.swing.*;
import java.awt.*;

public class MastermindView extends JFrame {

    private final MastermindGame game;
    private final JTextArea resultsTextArea;
    private final JLabel attemptsLabel;
    private final JButton guessButton;
    private final ColorCirclePanel[] colorSelectors;

    public MastermindView(MastermindGame game, int codeLength) {
        this.game = game;

        setTitle("Mastermind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top instructions
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Click a circle to change its color, then press 'Guess'."));
        add(topPanel, BorderLayout.NORTH);

        // Center area: color selectors and results
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Create the panels that let the user pick colors
        JPanel selectorsPanel = new JPanel(new FlowLayout());
        colorSelectors = new ColorCirclePanel[codeLength];
        for (int i = 0; i < codeLength; i++) {
            colorSelectors[i] = new ColorCirclePanel();
            selectorsPanel.add(colorSelectors[i]);
        }

        guessButton = new JButton("Guess");
        JPanel inputPanel = new JPanel();
        inputPanel.add(selectorsPanel);
        inputPanel.add(guessButton);

        resultsTextArea = new JTextArea(20, 30);
        resultsTextArea.setEditable(false);

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(resultsTextArea), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom: show attempts left
        attemptsLabel = new JLabel("Attempts: 0/" + game.getMaxAttempts());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(attemptsLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Handle "Guess" button clicks
        guessButton.addActionListener(e -> makeGuess());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void makeGuess() {
        if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game over! Restart to try again.");
            return;
        }

        // Build the guess string from the chosen colors
        StringBuilder guessBuilder = new StringBuilder();
        for (ColorCirclePanel panel : colorSelectors) {
            guessBuilder.append(panel.getColorCode());
        }

        // Check the guess
        int[] feedback = game.guess(guessBuilder.toString());
        int blackPins = feedback[0];
        int whitePins = feedback[1];

        // Show feedback in the text area
        resultsTextArea.append("Guess: " + guessBuilder + " | Black: " + blackPins + " | White: " + whitePins + "\n");
        attemptsLabel.setText("Attempts: " + game.getAttemptsMade() + "/" + game.getMaxAttempts());

        // Check if the game has ended (win or lose)
        if (game.isGameWon()) {
            JOptionPane.showMessageDialog(this, "You cracked the code!");
            resultsTextArea.append("You won! The code was: " + new String(game.getSecretCode()) + "\n");
        } else if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, "No more attempts left!");
            resultsTextArea.append("You lost! The code was: " + new String(game.getSecretCode()) + "\n");
        }
    }
}

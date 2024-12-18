package com.mycompany.mastermindapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A panel that shows a colored circle. Each time the user clicks on it,
 * the circle changes to the next color in the sequence.
 */
public class ColorCirclePanel extends JPanel {

    // The characters representing each color option.
    private static final char[] COLOR_CODES = {'R', 'G', 'B', 'Y', 'O', 'P'};

    // The actual colors used to fill the circle.
    private static final Color[] COLORS = {
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
        Color.ORANGE,
        new Color(128, 0, 128) // Purple
    };

    // Keeps track of the current color index
    private int currentIndex = 0;

    public ColorCirclePanel() {
        // Give the panel a size and a white background.
        setPreferredSize(new Dimension(50, 50));
        setBackground(Color.WHITE);

        // Add a mouse listener so that when the panel is clicked, we cycle to the next color.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cycleColor();
            }
        });
    }

    /**
     * Move to the next color in the list. If we reach the end, start over at the beginning.
     */
    private void cycleColor() {
        currentIndex = (currentIndex + 1) % COLORS.length;
        repaint(); // Tell Swing to redraw the panel with the new color.
    }

    /**
     * Returns the character code of the current color.
     */
    public char getColorCode() {
        return COLOR_CODES[currentIndex];
    }

    /**
     * Draw the colored circle. We find the largest possible circle that fits
     * inside the panel, leaving a small margin.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(COLORS[currentIndex]);

        int diameter = Math.min(getWidth(), getHeight()) - 10;
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        g.fillOval(x, y, diameter, diameter);
    }
}

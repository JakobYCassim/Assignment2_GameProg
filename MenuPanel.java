import java.awt.*;
import javax.swing.*;

public class MenuPanel extends JPanel {
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JButton quitButton;
    private Game game;

    public MenuPanel(Game game) {
        this.game = game;
        setLayout(new GridLayout(4, 1));

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");
        quitButton = new JButton("Quit");


        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        startButton.setFont(buttonFont);
        pauseButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        quitButton.setFont(buttonFont);

        startButton.setBackground(Color.GREEN);
        pauseButton.setBackground(Color.YELLOW);
        resetButton.setBackground(Color.GRAY);
        quitButton.setBackground(Color.RED);

        startButton.addActionListener(e -> game.startGame());
        pauseButton.addActionListener(e -> game.pauseGame());
        resetButton.addActionListener(e -> game.resetGame());
        quitButton.addActionListener(e -> System.exit(0));

        add(startButton);
        add(pauseButton);
        add(resetButton);
        add(quitButton);
    }
}

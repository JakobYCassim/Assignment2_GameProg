import java.awt.BorderLayout;
import javax.swing.JFrame;

public class GameApplication {
    
    public static void main(String[] args) {
       Game game = new Game();

       MenuPanel menuPanel = new MenuPanel(game);
        JFrame frame = new JFrame("Treasure Hunt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.add(menuPanel, BorderLayout.WEST);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        game.startGame();
    }
}

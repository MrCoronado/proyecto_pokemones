import javax.swing.SwingUtilities;
import gui.BattleGUI;

public class BattleMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BattleGUI();
        });
    
    }
}

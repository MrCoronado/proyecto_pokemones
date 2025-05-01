import javax.swing.SwingUtilities;
import gui.BattleGUI;

public class BattleMain {
    public static void main(String[] args) {
        System.out.println("El programa ha iniciado."); // Mensaje de depuración
        SwingUtilities.invokeLater(() -> {
            new BattleGUI();
        });
    }
}

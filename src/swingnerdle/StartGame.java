
package swingnerdle;

import java.awt.HeadlessException;
import javax.swing.JFrame;


public class StartGame extends JFrame{
private static final StartGame screen=new StartGame("Nerdle Game");
    public StartGame(String title) throws HeadlessException {
        super(title);
    }
    
    public static void closeMenu(){
        screen.setVisible(false);
    }
    
    public static void main(String[] args) {
        MainScreen menu=new MainScreen(screen);
        menu.requestFocus();
        menu.setFocusable(true);
        menu.setFocusTraversalKeysEnabled(false);
        screen.setResizable(false);
        screen.setSize(600, 900);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setLocationRelativeTo(null);
        screen.add(menu);
        screen.setVisible(true);
    }
    
}

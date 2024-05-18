import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardKeybind extends Keybind {
    private final int keyCode;
    public static boolean anyKeyPressed = false;

    public KeyboardKeybind(int keyCode){
        this.keyCode = keyCode;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((ke) -> {
            switch (ke.getID()){
                case KeyEvent.KEY_PRESSED -> {
                    keyPressed(ke);
                    anyKeyPressed = true;
                }
                case KeyEvent.KEY_RELEASED -> keyReleased(ke);
            }
            return false;
        });
    }


    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == keyCode){
            pressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == keyCode){
            pressed = false;
        }
    }
}

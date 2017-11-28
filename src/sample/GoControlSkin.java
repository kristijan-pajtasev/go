package sample;
import javafx.scene.control.SkinBase;

//class definition for a skin for the Go control
//NOTE: to keep JavaFX happy we don't use the skin here
class GoControlSkin extends SkinBase <GoControl> {
    // default constructor for the class
    public GoControlSkin(GoControl rc) {
        super(rc);
    }
}
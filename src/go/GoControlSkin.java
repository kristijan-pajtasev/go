package go;

import javafx.scene.control.SkinBase;

/**
 * Created by Kristijan Pajtasev
 * 31/10/2017.
 */
//class definition for a skin for the reversi control
//NOTE: to keep JavaFX happy we dont use the skin here
class GoControlSkin extends SkinBase<GoControl> {
    // default constructor for the class
    public GoControlSkin(GoControl rc) {
        super(rc);
    }
}


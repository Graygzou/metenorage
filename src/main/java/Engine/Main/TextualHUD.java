package Engine.Main;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.System.Graphics.Component.Text2D;
import Engine.System.Graphics.HUD;
import Engine.Window;
import org.joml.Vector4f;

public class TextualHUD implements HUD {
    private static int FONT_COLUMNS = 8;

    private static int FONT_ROWS = 8;

    private static final String FONT_TEXTURE = "/Game/Fonts/Segoe.png";

    private final Text2D[] textMeshes;

    private final Text2D statusText;

    public TextualHUD(String characters) throws Exception {
        this.statusText = new Text2D(new Entity(), characters, FONT_TEXTURE, FONT_COLUMNS, FONT_ROWS);
        this.statusText.setTextColor(new Vector4f(1, 1, 1, 1));
        textMeshes = new Text2D[]{statusText};
    }

    public void setStatusText(String statusText) {
        this.statusText.setText(statusText);
    }

    public void updateSize(Window window) {
        this.statusText.getEntity().setPosition(10f, window.getHeight() - 50f, 0);
    }

    @Override
    public Text2D[] getHUDMeshes() {
        return textMeshes;
    }
}

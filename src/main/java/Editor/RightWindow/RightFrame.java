package Editor.RightWindow;

import Engine.GameEngine;

import javax.swing.*;
import java.awt.*;

public class RightFrame extends JFrame {

    private GameEngine gameEngine;
    private ComponentsPanel componentsPanel;

    public RightFrame(GameEngine gameEngine) {

        this.gameEngine = gameEngine;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Get the screen size
        GraphicsConfiguration gc = this.getGraphicsConfiguration();
        Rectangle bounds = gc.getBounds();

        // Create and pack the Elements
        this.getContentPane().setLayout(new BorderLayout());

        // Add the list at the top of the frame
        this.getContentPane().add(new TopPanel(), BorderLayout.NORTH);

        // Add the component Panel
        this.componentsPanel = new ComponentsPanel();
        this.getContentPane().add(this.componentsPanel, BorderLayout.CENTER);

        this.pack();

        // Set the Location and Activate
        this.setSize(350, 480);
        int y = (int) ((bounds.getHeight() - this.getHeight()) / 2);
        this.setLocation((int)bounds.getWidth() - this.getWidth(), y);

        this.setResizable(false);
        this.setVisible(true);

    }

}

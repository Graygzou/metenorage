package Editor.LeftWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

/**
 * Gregoire Boiron
 */
public class ToolsPanel extends JPanel {

    public ToolsPanel(LeftFrame parent) {
        this.setLayout(new FlowLayout());

        this.add(this.createTool("Player","Editor/playerCube.PNG", parent));
        this.add(this.createTool("Grass","Editor/grassCube.PNG", parent));
        this.add(this.createTool("Ground","Editor/groundCube.PNG", parent));
        this.add(this.createTool("LifeItem","Editor/healthCube.PNG", parent));
    }

    private Component createTool(String name, String resourcePath, LeftFrame parent) {
        JButton cube = new JButton();
        cube.setActionCommand(name);
        try {
            File f = new File(Paths.get("./resources/", resourcePath).toUri());
            Image img = ImageIO.read(f);
            img = img.getScaledInstance(50,50, Image.SCALE_SMOOTH);
            cube.setIcon(new ImageIcon(img));
            // Set the action listener of the button
            cube.addActionListener(parent);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return cube;
    }
}

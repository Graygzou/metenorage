package Editor.LeftWindow;

import javax.swing.*;
import java.awt.*;

/**
 * @author Gregoire Boiron
 */
public class LaunchGamePanel extends JPanel {

    public LaunchGamePanel() {
        this.setLayout(new FlowLayout());

        // Play Button
        this.add(new JButton("Play"));

        // Pause Button
        this.add(new JButton("Pause"));

        // Pause Button
        this.add(new JButton("Stop"));
    }

}

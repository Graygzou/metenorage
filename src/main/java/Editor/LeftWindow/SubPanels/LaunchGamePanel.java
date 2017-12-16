package Editor.LeftWindow.SubPanels;

import Editor.Editor;
import Editor.LeftWindow.LeftFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Gregoire Boiron
 */
public class LaunchGamePanel extends JPanel {

    LeftFrame parent;

    public LaunchGamePanel(LeftFrame parent) {
        this.parent = parent;

        this.setLayout(new FlowLayout());

        // Play Button
        this.add(new JButton("Play"));

        // Pause Button
        this.add(new JButton("Stop"));

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parent.writeDataFile();
            }
        });
        this.add(saveButton);
    }
}

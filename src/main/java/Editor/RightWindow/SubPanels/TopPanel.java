package Editor.RightWindow.SubPanels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Gregoire Boiron
 */
public class TopPanel extends JPanel {

    public TopPanel() {
        this.setLayout(new BorderLayout());

        //create the model and add elements
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("Transform");
        listModel.addElement("Mesh3D");
        listModel.addElement("KeyboardListener");
        listModel.addElement("RigidBodyComponent");
        listModel.addElement("Script");
        listModel.addElement("Source");

        // create the list
        JList componentList = new JList(listModel);
        componentList.setVisibleRowCount(1);
        this.add(new JScrollPane(componentList), BorderLayout.CENTER);

        // Create the add button
        this.add(new JButton(), BorderLayout.EAST);
    }

}

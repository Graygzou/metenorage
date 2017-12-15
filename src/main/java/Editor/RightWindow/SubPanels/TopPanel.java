package Editor.RightWindow.SubPanels;

import Editor.RightWindow.RightFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Gregoire Boiron
 */
public class TopPanel extends JPanel {

    private RightFrame parent;
    private JList componentList;

    public TopPanel(RightFrame parent) {
        this.setLayout(new BorderLayout());

        //create the model and add elements
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("KeyboardListener");
        listModel.addElement("RigidBodyComponent");
        listModel.addElement("Script");
        listModel.addElement("Source");

        // create the list
        this.componentList = new JList(listModel);
        this.componentList.setVisibleRowCount(1);
        this.add(new JScrollPane(this.componentList), BorderLayout.CENTER);

        // Create the add button
        JButton buttonAddComponent = new JButton("Add");
        buttonAddComponent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //int index = componentList.getSelectedIndex();
                //System.out.println("Index Selected: " + index);
                String name = (String) componentList.getSelectedValue();
                if(name != null) {
                    parent.addComponentToEntity(name);
                }
            }
        });
        this.add(buttonAddComponent, BorderLayout.EAST);
    }

}

package Editor.RightWindow.SubPanels;

import Editor.RightWindow.RightFrame;
import Editor.RightWindow.SubPanels.ComponentsPanel.TransformPanel;
import Engine.Main.Entity;
import Engine.System.Component.Component;
import Engine.System.Component.Transform;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Physics.Component.BoxRigidBodyComponent;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregoire Boiron
 */
public class CenterPanel extends JPanel implements ItemListener {

    private RightFrame parent;
    private GridBagConstraints constraints;

    public CenterPanel(RightFrame parent) {
        this.parent = parent;

        this.setLayout(new GridBagLayout());
        this.constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

    }

    public void cleanPanel() {
        this.removeAll();
        this.constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
    }

    public void showComponents(Entity entity) {
        List<Component> components;
        // To have a nice layout
        this.add(new JLabel(entity.getName()), constraints);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), constraints);
        if((components = entity.getComponents()).isEmpty()) {
            this.constraints.weighty = 1.0;
        }

        // Add the transform component
        Transform transform;
        if((transform = entity.getTransform()) != null) {
            TransformPanel transformPanel = new TransformPanel(this.parent, transform.getPosition(),
                    transform.getRotation(), transform.getScale());
            this.add(transformPanel, constraints);
            constraints.insets.top = 5;
        }

        // Add all the others components
        for(int i = 0; i < components.size(); i++) {
            if (components.get(i).getClass() == Mesh3D.class) {
                //TODO
                //TransformPanel transformPanel = new TransformPanel();
                //this.add(transformPanel, constraints);
            } else if (components.get(i).getClass() == BoxRigidBodyComponent.class) {
                //TODO
                //TransformPanel transformPanel = new TransformPanel();
                //this.add(transformPanel, constraints);

            }
            constraints.insets.top = 5;
            // To have a nice layout
            if(i == components.size()-2) {
                constraints.weighty = 1.0;
            }
        }
        this.revalidate();
        this.repaint();
    }


}

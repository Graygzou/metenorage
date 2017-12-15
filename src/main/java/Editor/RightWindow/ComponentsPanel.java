package Editor.RightWindow;

import Editor.RightWindow.SubPanels.TransformPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComponentsPanel extends JPanel implements ItemListener {

    protected static int NUM_COMPONENTS = 3;
    protected static float[] xAlignment = {
            Component.LEFT_ALIGNMENT,
            Component.CENTER_ALIGNMENT,
            Component.RIGHT_ALIGNMENT};

    public ComponentsPanel() {
        /*
        this.setLayout(new GridLayout(1,0));

        JPanel t = new JPanel();
        t.setBackground(Color.blue);
*/
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;



        TransformPanel transformPanel = new TransformPanel();
        this.add(transformPanel, c);

        transformPanel = new TransformPanel();
        this.add(transformPanel, c);

        JButton button = new JButton("Long-Named Button 4");
        this.add(button, c);

        button = new JButton("Long-Named Button 4");
        this.add(button, c);

        c.insets.top = 5;
        c.weighty = 1.0;
        button = new JButton("Long-Named Button 4");
        this.add(button, c);


        //this.add(t);

        //create the model and add elements
        //panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        /*
        //Create the rectangles.
        JPanel pane = new JPanel();
        String title;

        JComponent component1 = new JPanel();
        Dimension size = new Dimension(100, 50);
        component1.setMaximumSize(size);
        component1.setPreferredSize(size);
        component1.setMinimumSize(size);
        TitledBorder border = new TitledBorder(
                new LineBorder(Color.black),
                "A JPanel",
                TitledBorder.CENTER,
                TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.black);
        component1.setBorder(border);

        JComponent component2 = new JPanel();
        size = new Dimension(200, 100);
        component2.setMaximumSize(size);
        component2.setPreferredSize(size);
        component2.setMinimumSize(size);
        border = new TitledBorder(new LineBorder(Color.black),
                "A JPanel",
                TitledBorder.CENTER,
                TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.black);
        component2.setBorder(border);

        title = "Matched";

        component1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        component1.setAlignmentX(Component.LEFT_ALIGNMENT);

        pane.setBorder(BorderFactory.createTitledBorder(title));
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
        pane.add(component1);
        pane.add(component2);

        panel.setBorder(BorderFactory.createLineBorder(Color.red));

        this.add(pane);*/
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

    }
}

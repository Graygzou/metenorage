package Editor.RightWindow.SubPanels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TransformPanel extends JPanel {

    public TransformPanel() {
        this.setLayout(new BorderLayout());

        this.add(new JLabel("Transform :"), BorderLayout.NORTH);

        Dimension size = new Dimension(0, 70);
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);

        // Add the legend
        JPanel legends = new JPanel();
        legends.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        legends.setLayout(new GridLayout(3,2));

        JLabel pos = new JLabel("Position");
        pos.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        legends.add(pos);

        JLabel rot = new JLabel("Rotation");
        rot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        legends.add(rot);

        JLabel scale = new JLabel("Scale");
        scale.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        legends.add(scale);

        // Add the actual coordinates
        JPanel coordinates = new JPanel();
        coordinates.setLayout(new GridLayout(1,3));

        JPanel X = new JPanel();
        X.setLayout(new GridLayout(3,1,3,3));
        // X position
        JPanel pX = new JPanel();
        pX.setLayout(new BorderLayout());
        pX.add(new JLabel("X"), BorderLayout.WEST);
        pX.add(new JSpinner());
        X.add(pX);
        // X rotation
        JPanel rX = new JPanel();
        rX.setLayout(new BorderLayout());
        rX.add(new JLabel("X"), BorderLayout.WEST);
        rX.add(new JSpinner());
        X.add(rX);
        // X scale
        JPanel sX = new JPanel();
        sX.setLayout(new BorderLayout());
        sX.add(new JLabel("X"), BorderLayout.WEST);
        sX.add(new JSpinner());
        X.add(sX);
        coordinates.add(X);

        JPanel Y = new JPanel();
        Y.setLayout(new GridLayout(3,1, 3,3));
        Y.add(new JSpinner());
        Y.add(new JSpinner());
        Y.add(new JSpinner());
        coordinates.add(Y);

        JPanel Z = new JPanel();
        Z.setLayout(new GridLayout(3,1,3,3));
        Z.add(new JSpinner());
        Z.add(new JSpinner());
        Z.add(new JSpinner());
        coordinates.add(Z);

        this.add(legends, BorderLayout.WEST);

        this.add(coordinates, BorderLayout.CENTER);

        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
    }
}

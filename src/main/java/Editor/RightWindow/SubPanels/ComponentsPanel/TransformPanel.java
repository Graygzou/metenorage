package Editor.RightWindow.SubPanels.ComponentsPanel;

import Editor.RightWindow.RightFrame;
import javafx.scene.control.Spinner;
import org.joml.Vector3f;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Gregoire Boiron
 */
public class TransformPanel extends JPanel implements ChangeListener {

    private RightFrame parentFrame;

    private List<JSpinner> spinners;

    public TransformPanel(RightFrame parentFrame) {
        this(parentFrame, new Vector3f(0,0,0),
                new Vector3f(0,0,0),
                new Vector3f(0,0,0));
    }

    public TransformPanel(RightFrame parentFrame, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.parentFrame = parentFrame;
        this.spinners = new ArrayList<>();

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        this.add(new JLabel("Transform :"), BorderLayout.NORTH);

        Dimension size = new Dimension(0, 70);
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);

        // Add the actual coordinates
        JPanel coordinates = new JPanel();
        coordinates.setLayout(new GridLayout(1,3));

        // X coordinate
        coordinates.add(createCoordinatesPanel("X", position.x, rotation.x, scale.x));
        // Y coordinate
        coordinates.add(createCoordinatesPanel("Y", position.y, rotation.y, scale.y));
        // Z coordinate
        coordinates.add(createCoordinatesPanel("Z", position.z, rotation.z, scale.z));

        // Add the legend
        this.add(createLegend(), BorderLayout.WEST);

        this.add(coordinates, BorderLayout.CENTER);

        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
    }

    private JPanel createLegend() {
        JPanel result = new JPanel();
        result.setLayout(new GridLayout(3,2));
        result.add(new JLabel("Position"));
        result.add(new JLabel("Rotation"));
        result.add(new JLabel("Scale"));
        result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return result;
    }

    private JPanel createCoordinatesPanel(String legend, float position, float rotation, float scale) {
        double min = -Float.MAX_VALUE;
        double max = Float.MAX_VALUE;
        double stepSize = 0.1;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1,3,3));

        // -------- Position coordinate ------------------
        JPanel pX = new JPanel();
        pX.setLayout(new BorderLayout());
        pX.add(new JLabel(legend), BorderLayout.WEST);

        // Define the float Spinner
        SpinnerNumberModel model = new SpinnerNumberModel(position, min, max, stepSize);
        JSpinner posSpinner = new JSpinner(model);
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor)posSpinner.getEditor();
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(3);

        posSpinner.setName("Transform");
        this.spinners.add(posSpinner);
        posSpinner.addChangeListener(this);
        pX.add(posSpinner);
        panel.add(pX);

        // ------ Rotation coordinate -------------
        JPanel rX = new JPanel();
        rX.setLayout(new BorderLayout());
        rX.add(new JLabel(legend), BorderLayout.WEST);

        // Define the float Spinner
        model = new SpinnerNumberModel(rotation, min, max, stepSize);
        JSpinner rotSpinner = new JSpinner(model);
        editor = (JSpinner.NumberEditor)rotSpinner.getEditor();
        format = editor.getFormat();
        format.setMinimumFractionDigits(3);

        rotSpinner.setName("Transform");
        this.spinners.add(rotSpinner);
        rotSpinner.addChangeListener(this);
        rX.add(rotSpinner);
        panel.add(rX);

        // ------- Scale coordinate ----------------
        JPanel sX = new JPanel();
        sX.setLayout(new BorderLayout());
        sX.add(new JLabel(legend), BorderLayout.WEST);

        // Define the float Spinner
        model = new SpinnerNumberModel(scale, min, max, stepSize);
        JSpinner scaleSpinner = new JSpinner(model);
        editor = (JSpinner.NumberEditor)scaleSpinner.getEditor();
        format = editor.getFormat();
        format.setMinimumFractionDigits(3);

        scaleSpinner.setName("Transform");
        this.spinners.add(scaleSpinner);
        scaleSpinner.addChangeListener(this);
        sX.add(scaleSpinner);
        panel.add(sX);

        return panel;
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        JSpinner spinnerChanged = (JSpinner) changeEvent.getSource();
        spinnerChanged.getValue();
        switch (spinnerChanged.getName()) {
            case "Transform":
                DecimalFormat decimalFormat = new DecimalFormat("#");
                float posX = ((Double)this.spinners.get(0).getValue()).floatValue();
                float posY = ((Double)this.spinners.get(3).getValue()).floatValue();
                float posZ = ((Double)this.spinners.get(6).getValue()).floatValue();
                float rotX = ((Double)this.spinners.get(1).getValue()).floatValue();
                float rotY = ((Double)this.spinners.get(4).getValue()).floatValue();
                float rotZ = ((Double)this.spinners.get(7).getValue()).floatValue();
                float scaX = ((Double)this.spinners.get(2).getValue()).floatValue();
                float scaY = ((Double)this.spinners.get(5).getValue()).floatValue();
                float scaZ = ((Double)this.spinners.get(8).getValue()).floatValue();
                this.parentFrame.updateTransform(new Vector3f(posX, posY, posZ),
                        new Vector3f(rotX, rotY, rotZ),
                        new Vector3f(scaX, scaY, scaZ));


                break;

            default:
                System.out.println("NONONO");
                break;
        }
    }

}

package Editor.RightWindow.SubPanels.ComponentsPanel;

import Editor.RightWindow.RightFrame;
import Engine.System.Component.Component;
import Engine.System.Scripting.Component.Script;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ScriptPanel extends ComponentPanel {

    private JTextField scriptName;

    public ScriptPanel(RightFrame parentFrame, Component component) {
        this.parentFrame = parentFrame;
        this.component = component;

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        this.add(new JLabel("Script :"), BorderLayout.NORTH);

        Dimension size = new Dimension(0, 70);
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);

        // Name of the script
        scriptName = new JTextField();
        scriptName.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent focusEvent) {  }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                ((Script)component).setName(scriptName.getText());
            }
        });
        this.add(scriptName, BorderLayout.CENTER);
    }
}

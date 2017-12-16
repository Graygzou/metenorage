package Editor.LeftWindow;

import Editor.Editor;
import Editor.LeftWindow.SubPanels.HierarchyPanel;
import Editor.LeftWindow.SubPanels.LaunchGamePanel;
import Editor.LeftWindow.SubPanels.ToolsPanel;
import Editor.RightWindow.RightFrame;
import Engine.GameEngine;
import Engine.Helper.Loader.OBJLoader;
import Engine.Main.Entity;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Physics.Component.BoxRigidBodyComponent;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Gregoire Boiron
 */
public class LeftFrame extends JFrame implements TreeSelectionListener, TreeModelListener, KeyListener, ActionListener {

    private GameEngine gameEngine;
    private RightFrame componentsFrame;
    private HierarchyPanel hierarchy;

    public LeftFrame(GameEngine gameEngine, RightFrame componentsFrame) {
        this.gameEngine = gameEngine;
        this.componentsFrame = componentsFrame;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Get the screen size
        GraphicsConfiguration gc = this.getGraphicsConfiguration();
        Rectangle bounds = gc.getBounds();

        // Create and pack the Elements
        this.getContentPane().setLayout(new BorderLayout());

        // Add the playable menu at the top
        this.getContentPane().add(new LaunchGamePanel(this), BorderLayout.NORTH);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        // First add an hierarchie of components.
        this.hierarchy = new HierarchyPanel(this, splitPane);

        // Second, add the list of tools.
        splitPane.setBottomComponent(new ToolsPanel(this));

        this.getContentPane().add(splitPane, BorderLayout.CENTER);

        this.pack();

        // Set the Location and Activate
        //this.setSize(this.getSize().width, (int)bounds.getHeight());
        int y = (int) ((bounds.getHeight() - this.getHeight()) / 2);
        this.setLocation(0, y);

        this.setResizable(false);
        this.setVisible(true);

    }

    /**
     * Manage input of Tool Buttons.
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "Player":
                // Create the element in the game
                Mesh3D cubeMesh = null;
                try {
                    cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                    cubeMesh.setMaterial(Editor.materials.get(0));

                    Entity blockPlayer = new Entity("Player");
                    cubeMesh.setEntity(blockPlayer);
                    blockPlayer.addComponent(cubeMesh);
                    //Rigidbody MUST BE attached before EntityKeyboard
                    //blockPlayer.addComponent(new BoxRigidBodyComponent(blockPlayer, 1, 0.2f,0.2f,0.2f));
                    blockPlayer.getTransform().setPosition(1f, -1f, -3.5f);
                    blockPlayer.getTransform().setScale(0.2f);
                    //blockPlayer.addComponent(new Script(blockPlayer, scriptPlayer));
                    blockPlayer.setTag("player");
                    // Add the bloc
                    gameEngine.addEntity(blockPlayer);

                    // Add it to the hierarchy
                    hierarchy.createNode(0, "Player", blockPlayer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Grass":
                // Create the element in the game
                Entity block = null;
                try {
                    cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                    cubeMesh.setMaterial(Editor.materials.get(1));

                    block = new Entity("My block");
                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);
                    block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                    block.getTransform().setPosition(1f, -2f, -3.5f);
                    block.getTransform().setScale(0.5f);
                    // Add the bloc
                    gameEngine.addEntity(block);

                    // Add it to the hierarchy
                    hierarchy.createNode(1, "Grass", block);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Ground":
                // Create the element in the game
                block = null;
                try {
                    cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                    cubeMesh.setMaterial(Editor.materials.get(2));

                    block = new Entity("My block");
                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);
                    block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                    block.getTransform().setPosition(1f, 0f, -3.5f);
                    block.getTransform().setScale(0.5f);
                    // Add the bloc
                    gameEngine.addEntity(block);

                    // Add it to the hierarchy
                    hierarchy.createNode(2, "Ground", block);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "LifeItem":
                try {
                    cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                    cubeMesh.setMaterial(Editor.materials.get(3));
                    block = new Entity("My block");
                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);
                    block.addComponent(new BoxRigidBodyComponent(block, 0, 0.2f, 0.2f, 0.2f));
                    block.getTransform().setPosition(11.2f, 0.7f, -8.5f);
                    block.getTransform().setScale(0.2f);
                    gameEngine.addEntity(block);

                    hierarchy.createNode(3, "LifeItem", block);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * Managed Tree changes from the Hierarchy views
     */
    @Override
    public void treeNodesChanged(TreeModelEvent treeModelEvent) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)
                (treeModelEvent.getTreePath().getLastPathComponent());

        /*
         * If the event lists children, then the changed
         * node is the child of the node we have already
         * gotten.  Otherwise, the changed node and the
         * specified node are the same.
         */
        try {
            int index = treeModelEvent.getChildIndices()[0];
            node = (DefaultMutableTreeNode)
                    (node.getChildAt(index));
        } catch (NullPointerException exc) {
            System.out.println("Error");
        }

        System.out.println("The user has finished editing the node.");
        System.out.println("New value: " + node.getUserObject());

        // Update the Component View here
    }

    @Override
    public void treeNodesInserted(TreeModelEvent treeModelEvent) {

    }

    @Override
    public void treeNodesRemoved(TreeModelEvent treeModelEvent) {

    }

    @Override
    public void treeStructureChanged(TreeModelEvent treeModelEvent) {
    }

    /**
     * Managed key actions on the hierarchie
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
            Entity entity = this.hierarchy.removeNode();
            if(entity != null) {
                gameEngine.removeEntity(entity);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    @Override
    public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
        // Get the node selected
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                (treeSelectionEvent.getPath().getLastPathComponent());

        System.out.println(node.toString());

        // Get the corresponding entity.
        Entity entity = this.hierarchy.getEntityFromTreeNode(node);

        // Notice
        if(entity != null) {
            this.componentsFrame.showComponentsFromEntity(entity);
        }
    }

    public static void writeDataFile() {
        List<String> lines = new LinkedList<>();

        // Write data of the gameEngine
        lines.addAll(Editor.writeDataFile());

        // Write entities
        lines.addAll(HierarchyPanel.writeEntitiesFile());

        lines.add("}");
        try {
            // Write materials inside the file
            Files.write(Editor.outputFile, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

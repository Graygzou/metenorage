package Editor.LeftWindow;

import Editor.Editor;
import Engine.GameEngine;
import Engine.Helper.Loader.OBJLoader;
import Engine.Main.Entity;
import Engine.Main.Material;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.System.Scripting.Component.Script;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gregoire Boiron
 */
public class LeftFrame extends JFrame implements TreeModelListener, ActionListener {

    private Hierarchy hierarchy;

    private GameEngine gameEngine;

    public LeftFrame(GameEngine gameEngine) {

        this.gameEngine = gameEngine;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Get the screen size
        GraphicsConfiguration gc = this.getGraphicsConfiguration();
        Rectangle bounds = gc.getBounds();

        // Create and pack the Elements
        this.getContentPane().setLayout(new GridLayout(1,0));

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.getContentPane().add(splitPane);

        // First add an hierarchie of components.
        this.hierarchy = new Hierarchy(this, splitPane);

        // Second, add the list of tools.
        splitPane.setBottomComponent(new Tools(this));

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
                // Create the element in the hierarchy
                hierarchy.createNode(0, "Player");

                // Create the element in the game
                Mesh3D cubeMesh = null;
                try {
                    cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                    cubeMesh.setMaterial(Editor.materials.get(0));
                    Entity blockPlayer = new Entity("Player");
                    cubeMesh.setEntity(blockPlayer);
                    blockPlayer.addComponent(cubeMesh);
                    //Rigidbody MUST BE attached before EntityKeyboard
                    blockPlayer.addComponent(new BoxRigidBodyComponent(blockPlayer, 1, 0.2f,0.2f,0.2f));
                    blockPlayer.getTransform().setPosition(1f, -1f, -3.5f);
                    blockPlayer.getTransform().setScale(0.2f);
                    //blockPlayer.addComponent(new Script(blockPlayer, scriptPlayer));
                    blockPlayer.setTag("player");
                    gameEngine.addEntity(blockPlayer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Ground":
                hierarchy.createNode(1, "Ground");
                break;
            case "LifeItem":
                hierarchy.createNode(2, "LifeItem");
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


}

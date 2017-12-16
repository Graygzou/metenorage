package Editor.LeftWindow.SubPanels;

import Editor.Editor;
import Editor.LeftWindow.LeftFrame;
import Engine.Main.Entity;
import Engine.System.Component.Component;
import Engine.System.Component.Transform;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.System.Physics.Component.RigidBodyComponent;
import Engine.System.Scripting.Component.Script;
import Engine.System.Sound.Component.Source;
import Game.Input.PlayerKeyboard;
import javafx.scene.shape.Mesh;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

/**
 * @author Gregoire Boiron
 */
public class HierarchyPanel {

    private static HashMap<MutableTreeNode, Entity> entitiesHashMap;

    private List<DefaultMutableTreeNode> categories;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private JTree tree;

    public HierarchyPanel(LeftFrame parent, JSplitPane t) {

        this.categories = new ArrayList<>();
        this.entitiesHashMap = new HashMap<>();

        this.rootNode = new DefaultMutableTreeNode("Game Entities");
        this.treeModel = new DefaultTreeModel(rootNode);
        this.treeModel.addTreeModelListener(parent);

        //Create the folders.
        this.createFolders();

        //Create a tree that allows one selection at a time.
        this.tree = new JTree(this.treeModel);
        this.tree.addKeyListener(parent);
        this.tree.addTreeSelectionListener(parent);
        this.tree.setEditable(true);
        this.tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.tree.setShowsRootHandles(true);

        //Create the scroll pane and add the tree to it.
        JScrollPane j = new JScrollPane(tree);
        t.setTopComponent(j);
    }

    private void createFolders() {
        // First folder : the player one
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Player");
        this.rootNode.add(category);
        categories.add(category);
        // Second folder : grass element
        category = new DefaultMutableTreeNode("Grass");
        this.rootNode.add(category);
        categories.add(category);
        // Third folder : ground element
        category = new DefaultMutableTreeNode("Gound");
        this.rootNode.add(category);
        categories.add(category);
        // Forth folder : items
        category = new DefaultMutableTreeNode("Items");
        this.rootNode.add(category);
        categories.add(category);
    }

    public void createNode(int i, String name, Entity entity) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(name + this.categories.get(i).getChildCount());

        //There is no selection. Default to the root node.
        DefaultMutableTreeNode parentNode = this.categories.get(i);

        this.treeModel.insertNodeInto(node, parentNode, parentNode.getChildCount());

        this.tree.scrollPathToVisible(new TreePath(node.getPath()));

        this.entitiesHashMap.put(node, entity);
    }

    public Entity removeNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                    (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null && !this.categories.contains(currentNode)) {
                this.treeModel.removeNodeFromParent(currentNode);
                return this.entitiesHashMap.remove(currentNode);
            }
        }
        return null;
    }

    public Entity getEntityFromTreeNode(DefaultMutableTreeNode node) {
        return this.entitiesHashMap.get(node);
    }

    public static Collection<String> writeEntitiesFile() {
        List<String> lines = new LinkedList<>();
        lines.add("\"Game\": [");
        // Write entity of the game
        List<Entity> entities = new ArrayList<>(HierarchyPanel.entitiesHashMap.values());
        for(int i = 0; i < entities.size(); i++) {
            lines.add("{");
            lines.add("\"Name\": \"" + entities.get(i).getName() + "\",");
            lines.add("\"Tag\": \"" + entities.get(i).getTag() + "\",");
            // Position
            lines.add("\"Position\": {");
            lines.add("\"x\": " + entities.get(i).getTransform().getPosition().x + ",");
            lines.add("\"y\": " + entities.get(i).getTransform().getPosition().y + ",");
            lines.add("\"z\": " + entities.get(i).getTransform().getPosition().z);
            lines.add("},");
            // Rotation
            lines.add("\"Rotation\": {");
            lines.add("\"x\": " + entities.get(i).getTransform().getRotation().x + ",");
            lines.add("\"y\": " + entities.get(i).getTransform().getRotation().y + ",");
            lines.add("\"z\": " + entities.get(i).getTransform().getRotation().z);
            lines.add("},");
            // Scale
            lines.add("\"Scale\": {");
            lines.add("\"x\": " + entities.get(i).getTransform().getScale().x + ",");
            lines.add("\"y\": " + entities.get(i).getTransform().getScale().y + ",");
            lines.add("\"z\": " + entities.get(i).getTransform().getScale().z);
            lines.add("},");

            lines.addAll(writeComponentsFile(entities.get(i).getComponents()));

            if(i < HierarchyPanel.entitiesHashMap.values().size()-1) {
                lines.add("},");
            } else {
                lines.add("}");
            }
        }
        lines.add("]");
        return lines;
    }

    public static Collection<String> writeComponentsFile(List<Component> components) {
        List<String> lines = new LinkedList<>();
        // Write component of each entity
        lines.add("\"Components\": [");
        for(int i = 0; i < components.size(); i++) {
            lines.add("{");
            if (components.get(i).getClass() == Mesh3D.class) {
                // Create Mesh3D
                Mesh3D component = (Mesh3D) components.get(i);
                lines.add("\"Type\": \"Mesh3D\",");
                lines.add("\"Model\": \"/Game/Models/cube.obj\",");
                lines.add("\"Material\": \"" + component.getMaterial().getTextureName() + "\"");
            } else if (components.get(i).getClass() == BoxRigidBodyComponent.class) {
                // Create RigidBody
                BoxRigidBodyComponent component = (BoxRigidBodyComponent) components.get(i);
                lines.add("\"Type\": \"BoxRigidBodyComponent\",");
                lines.add("\"Mass\": " + component.getMass() + ",");
                lines.add("\"Scale\": {");
                lines.add("\"dx\": " + component.getScale().x + ",");
                lines.add("\"dy\": " + component.getScale().y + ",");
                lines.add("\"dz\": " + component.getScale().z);
                lines.add("}");
            } else if(components.get(i).getClass() == Script.class) {
                // Create Script
                Script component = (Script) components.get(i);
                lines.add("\"Type\": \"ScriptComponent\",");
                lines.add("\"Name\": " + component.getName() + ",");
            } else if(components.get(i).getClass() == Source.class) {
                // Create Source
                Source component = (Source) components.get(i);
                lines.add("\"Type\": Source\",");
                lines.add("\"Name\": " + component.getSoundName() + ",");
                lines.add("\"Path\": " + component.getSoundPath());
            } else if(components.get(i).getClass() == PlayerKeyboard.class) {
                // TODO
            }
            if(components.get(i).getClass() != Transform.class) {
                if (i < components.size() - 1) {
                    lines.add("},");
                } else {
                    lines.add("}");
                }
            }
        }
        lines.add("]");
        return lines;
    }
}

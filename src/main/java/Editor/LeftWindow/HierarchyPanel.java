package Editor.LeftWindow;

import Engine.Main.Entity;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Gregoire Boiron
 */
public class HierarchyPanel {

    private HashMap<MutableTreeNode, Entity> entitiesHashMap;

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
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

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
}

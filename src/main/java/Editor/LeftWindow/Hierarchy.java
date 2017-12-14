package Editor.LeftWindow;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Gregoire Boiron
 */
public class Hierarchy implements KeyListener {

    private List<DefaultMutableTreeNode> categories;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private JTree tree;

    public Hierarchy(LeftFrame parent, JSplitPane t) {

        this.categories = new ArrayList<>();

        rootNode = new DefaultMutableTreeNode("Game Entities");
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(parent);

        //Create the folders.
        this.createFolders();

        //Create a tree that allows one selection at a time.
        this.tree = new JTree(this.treeModel);
        this.tree.addKeyListener(this);
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
        // Second folder : ground element
        category = new DefaultMutableTreeNode("Gound");
        this.rootNode.add(category);
        categories.add(category);
        // Third folder : items
        category = new DefaultMutableTreeNode("Items");
        this.rootNode.add(category);
        categories.add(category);
    }

    public void createNode(int i, String name) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(name + this.categories.get(i).getChildCount());

        //There is no selection. Default to the root node.
        DefaultMutableTreeNode parentNode = this.categories.get(i);

        this.treeModel.insertNodeInto(node, parentNode, parentNode.getChildCount());

        this.tree.scrollPathToVisible(new TreePath(node.getPath()));

    }

    public void removeNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                    (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null && !this.categories.contains(currentNode)) {
                this.treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }
    }

    /**
     * Managed key actions on the hierarchie
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
            this.removeNode();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }
}

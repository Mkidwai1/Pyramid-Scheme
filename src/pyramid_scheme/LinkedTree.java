package pyramid_scheme;

import ADTs.TreeADT;
import DataStructures.MultiTreeNode;
import Exceptions.ElementNotFoundException;
import java.util.ArrayList;

/**
 * Generic LinkedTree class for ITSC 2214.
 * 
 * @author nblong
 * @param <T>
 * @version Oct. 2021
 * 
 * Adapted from Perrin Mele's excellent work in the spring semester of 2019
 */
public class LinkedTree<T> implements TreeADT<T> {

    /**
     * The root of the tree is a generic MultiTreeNode.
     */
    protected MultiTreeNode<T> root;

    /**
     * Constructor with element provided.
     *
     * @param startElem - The starting element of this tree.
     */
    public LinkedTree(T startElem) {
        root = new MultiTreeNode<>(startElem);
    }

    /**
     * Constructor with node provided.
     *
     * @param startNode - The starting node of this tree.
     */
    public LinkedTree(MultiTreeNode<T> startNode) {
        root = startNode;
    }

    /**
     * Returns the element stored at the root.
     *
     * @return root element
     */
    @Override
    public T getRootElement() {
        // return the element at root
        return root.getElement();
    } 
    
    

    /**
     * Step 1 of a two-step process to add a node containing the child element 
     * to an existing node that contains the parent element. This particular
     * overload of the addChild method is responsible for locating the parent
     * node then calling the other overload to actually add the child node.
     *
     * @param parent An element contained by a particular node in the tree,
     * which will ultimately be given a child node
     * @param child The element to be packaged into a node and added to the tree
     * @throws ElementNotFoundException
     */
    public void addChild(T parent, T child) throws ElementNotFoundException {
        //Find the Node with the parent element
        MultiTreeNode<T> parentNode = findNode(parent);
        
        //If the node with the parent element exsist the if statement is triggered
        if(parentNode !=null){
        //The other overloaded addChild method is called to actually add the 
        addChild(parentNode,child);
        }
        // if element isnt found an exception is thrown
        else throw new ElementNotFoundException("Parent element not in the tree");
    }

    /**
     * Step 2 of a two-step process to add a node containing the child element 
     * to an existing node that contains the parent element. This particular
     * overload of the addChild method is responsible for creating a node for
     * the child and adding it to the children of the parent node.  Note the
     * addChild method exists in multiple classes.
     *
     * @param parentNode the node receiving a child node
     * @param child the element to be packaged and added as a child node
     */
    protected void addChild(MultiTreeNode<T> parentNode, T child) {
        // create a node with the child element
        MultiTreeNode<T> childNode = new MultiTreeNode<>(child);
        // add the child node to the parent
        parentNode.addChild(childNode);
    }

    /**
     * Finds the node that contains the target element by calling the recursive
     * overload to search for the target starting at the root of the tree.
     *
     * @param target the element being searched for
     * @return the MultiTreeNode containing the target or null if not found
     */
    public MultiTreeNode<T> findNode(T target) {
        return findNode(root, target);
    }

    /**
     * Finds the node that contains a target element.  Checks the current node
     * and if it contains the target, returns it.  Otherwise, recursively
     * check each of the current node's children, to see if they can find it.
     * If none of this node's children can find it then null is returned.
     *
     * @param node the node currently being examined
     * @param target the element being searched for
     * @return the MultiTreeNode containing the target, or null if not found
     */
    private MultiTreeNode<T> findNode(MultiTreeNode<T> node, T target) {
        // If this node is the one containing the target...
        if (node.getElement().equals(target)) {
            return node; // Return this node
        } else { //Otherwise...
            MultiTreeNode<T> temp;
            // For each child of this node...
            for (MultiTreeNode<T> child : node.getChildren()) {
                // Call this method to see if the target is in a child node
                temp = findNode(child, target);
                // If findNode doesn't return null then the target was found
                if (temp != null) {
                    return temp; // Return node where target was found
                }
            }
            // If none of the children contained the target return null
            return null; //The target wasn't found
        }
    }

    /**
     * Tries to find a node that contains the target element and returns true
     * if so, false otherwise
     *
     * @param target the element being searched for.
     * @return a boolean representing whether or not the tree has a node
     * containing the target element.
     */
    @Override
    public boolean contains(T target) {
        // Start the search from the root of the tree
        MultiTreeNode<T> node = this.root;
        
        // Find the node with the target element
        MultiTreeNode<T> finalNode = findNode(node,target);
        
        // Return true if the target element was found in the tree, false otherwise
        return finalNode!=null;
    }

    /**
     * Returns the size of the tree measured by the number of nodes in it.  
     * Calls the recursive countDown method to determine this.
     *
     * @return the size of the tree.
     */
    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        return countDown(root);
    }

    /**
     * Recursively determines the number of nodes in the tree.  Starts with 
     * the current node then recursively calls itself for each of that node's
     * children and so on.  When originally called on the root node it will
     * return the number of nodes in the entire tree.  Used by size() to 
     * determine how many nodes are in the tree but could be used starting at
     * any node to determine how many nodes there are in that subtree.
     *
     * @param node the node currently being examined.
     * @return the amount of nodes from the starting node down.
     */
    private int countDown(MultiTreeNode<T> node) {
        // Base case if the node is null then there is nothing to count
        if ( node == null){
            return 0;
        }
        //if the base case isnt triggered the node has to be counted so the counter is started from 1
        int total = 1;
        
        // Recursively count all nodes in the subtree by going thru its children
        for(MultiTreeNode<T> child: node.getChildren()){
            total+=countDown(child);
        }
            return total;
    }

    /**
     * String representation of a LinkedTree.
     * 
     * @return a series of Strings illustrating the elements in each level of
     * the tree.
     */
    @Override
    public String toString() {
        String print = "Linked Tree: \nLayer 1 (Root): " + root.getElement();
        ArrayList<MultiTreeNode<T>> layer = root.getChildren();
        ArrayList<MultiTreeNode<T>> nextLayer;
        int layerNum = 2;
        while (!layer.isEmpty()) {
            print += "\nLayer " + layerNum + ":";
            nextLayer = new ArrayList<>();
            for (MultiTreeNode<T> node : layer) {
                nextLayer.addAll(node.getChildren());
                print += " " + node;
            }
            layer = nextLayer;
            layerNum++;
        }
        return print;
    }
}

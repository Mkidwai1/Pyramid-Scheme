package pyramid_scheme;

import DataStructures.MultiTreeNode;
import Exceptions.ElementNotFoundException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Solution Test file for LinkedTree
 *
 * @author pmele
 * @version 3/28/2019
 */
public class LinkedTreeTest {

    private LinkedTree<String> instance;
    private MultiTreeNode<String> root;
    private String s01;
    private String s02;
    private String s03;
    private String s04;
    private String s05;

    /**
     * Sets up the later tests.
     */
    @Before
    public void setUp() {
        s01 = "Elem 1";
        s02 = "Elem 2";
        s03 = "Elem 3";
        s04 = "Elem 4";
        s05 = "Elem 5";
        root = new MultiTreeNode<>(s01);
        instance = new LinkedTree<>(root);
    }

    /**
     * Test of getRootElement method, of class LinkedTree.
     */
    @Test
    public void testGetRootElement()throws ElementNotFoundException {
        System.out.println("testGetRootElement");
        //addign elements to the root
        instance.addChild(s01, s02);
        //testing if the root is equal to s01
        assertEquals(root.getElement(), s01);    }

    /**
     * Test of addChild method, of class LinkedTree.
     * @throws Exceptions.ElementNotFoundException
     */
    @Test
    public void testAddChild() throws ElementNotFoundException {
        try {
        // Attempting to add the same element as both parent and child
        instance.addChild("test", "test");
        fail("Expected ElementNotFoundException, but no exception was thrown");

    } catch (ElementNotFoundException ex) {
        // Ensure the correct exception is thrown with the expected message
        assertEquals("The element Parent element not in the treewas not found in the collection.", ex.getMessage());
    }

        // Checking size
        assertEquals(1, instance.size());

        // Adding a valid child
        instance.addChild(s01, s02);

        // Checking size (should now be 2, considering the root element and the added child)
        assertEquals(2, instance.size());

        // Checking if the added child can be found in the tree
        assertTrue(instance.contains(s02));

        // Checking if an element not present returns false
        assertFalse(instance.contains("nonexistentElement"));
    }

    /**
     * Test of findNode method, of class LinkedTree.
     */
    @Test
    public void testFindNode() {
        System.out.println("testFindNode");
        try {
            //Can find root
            assertEquals(s01, instance.findNode(s01).getElement());
            instance.addChild(s01, s02);
            //System.out.println("Successfully added " + s02 + " to " + s01);
            //Can find a child node
            assertEquals(s02, instance.findNode(s02).getElement());
            instance.addChild(s01, s03); //Add several deep
            instance.addChild(s02, s04);
            instance.addChild(s04, s05);
            //Can find a child node deep within tree
            assertEquals(s05, instance.findNode(s05).getElement());
            //Trying to find things not in the tree returns null
            assertEquals(null, instance.findNode("Unreal element"));
        } catch (Exception ex) {
            fail("Unexpected Exception");
        }
    }

    /**
     * Test of contains method, of class LinkedTree.
     * @throws Exceptions.ElementNotFoundException
     */
    @Test
    public void testContains() throws ElementNotFoundException {
        //adding elements to the instance
        instance.addChild(s01, s03);
        // testing to make sure thr root and its child are in the tree
        assertTrue(instance.contains(s01));
        assertTrue(instance.contains(s03));
        //testing to make sure a node not added to the tree doesnt come up as in the tree
        assertFalse(instance.contains(s04));
    }

    /**
     * Test of size method, of class LinkedTree.
     */
    @Test
    public void testSize() {
        if (root == null) {
            assertEquals(0, instance.size());
        }   
        //testing if adding nodes increases the size
        instance.addChild(root, s01);
        assertEquals(2, instance.size());
        instance.addChild(root, s02);
        assertEquals(3, instance.size());
        
    }

}

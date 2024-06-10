
package material.tree.binarytree;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import material.Position;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class FunHandlingTest {
    
    public FunHandlingTest() {
    }

    private LinkedBinaryTree<Integer> t1(){
        LinkedBinaryTree<Integer> t = new LinkedBinaryTree<>();
        Position<Integer> r = t.addRoot(5);
        Position<Integer> h3 = t.insertLeft(r, 3);
        t.insertRight(r,6);
        t.insertLeft(h3,2);
        t.insertRight(h3,4);
        
        return t;
    }
    
     private LinkedBinaryTree<Integer> t2(){
        LinkedBinaryTree<Integer> t = new LinkedBinaryTree<>();
        Position<Integer> r = t.addRoot(2);
        Position<Integer> h7 = t.insertLeft(r, 7);
        Position<Integer> h5 = t.insertRight(r,5);
        Position<Integer> h9 = t.insertRight(h7,9);
        t.insertLeft(h9,11);
        t.insertRight(h9, 4);
        t.insertRight(h5, 1);
        return t;
    }
     
     private LinkedBinaryTree<Integer> t3(){
        LinkedBinaryTree<Integer> t = new LinkedBinaryTree<>();
        Position<Integer> r = t.addRoot(2);
        Position<Integer> h7 = t.insertLeft(r, 7);
        Position<Integer> h5 = t.insertRight(r,5);
        t.insertLeft(h5, 1);
        t.insertRight(h7, 9);
        return t;
    }
    private LinkedBinaryTree<Integer> t4(){
        LinkedBinaryTree<Integer> t = new LinkedBinaryTree<>();
        Position<Integer> r = t.addRoot(2);
        Position<Integer> h7 = t.insertLeft(r, 7);
        Position<Integer> h5 = t.insertRight(r,5);
        t.insertLeft(h5, 1);
        t.insertLeft(h7, 9);
        return t;
    }
    /**
     * Test of removeHalfNodes method, of class FunHandling.
     */
    @Test
    public void testRemoveHalfNodes() {
        System.out.println("removeHalfNodes");
        FunHandling<Integer> instance = new FunHandling<>();
        LinkedBinaryTree<Integer> t = t1();
        List<Integer> l = Arrays.asList(2,3,4,5,6);
        Iterator<Integer> ite = l.iterator();
        for (Position<Integer> pos : t) {
            assertEquals(pos.getElement(),ite.next());
        }
        instance.removeHalfNodes(t);
        ite = l.iterator();
        for (Position<Integer> pos : t) {
            assertEquals(pos.getElement(),ite.next());
        }
        
        t = t2();
        l = Arrays.asList(7,11,9,4,2,5,1);
        ite = l.iterator();
        for (Position<Integer> pos : t) {
            assertEquals(pos.getElement(),ite.next());
        }
        instance.removeHalfNodes(t);
        l = Arrays.asList(11,9,4,2,1);
        ite = l.iterator();
        for (Position<Integer> pos : t) {
            assertEquals(pos.getElement(),ite.next());
        }
    }

    
    
}

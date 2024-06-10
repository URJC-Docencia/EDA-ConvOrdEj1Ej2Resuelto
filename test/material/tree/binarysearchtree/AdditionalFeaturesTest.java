
package material.tree.binarysearchtree;

import java.util.Arrays;
import java.util.Iterator;
import material.Position;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class AdditionalFeaturesTest {

    public AdditionalFeaturesTest() {
    }

    private LinkedBinarySearchTree<Integer> t1(){
        LinkedBinarySearchTree<Integer> t = new LinkedBinarySearchTree<>();
        Position<Integer> r = t.insert(5);
        t.insert(3);
        t.insert(6);
        t.insert(2);
        t.insert(4);

        return t;
    }

    private LinkedBinarySearchTree<Integer> t2(){
        LinkedBinarySearchTree<Integer> t = new LinkedBinarySearchTree<>();
        Position<Integer> r = t.insert(2);
        t.insert(1);
        t.insert(3);
        t.insert(7);
        t.insert(6);

        return t;
    }
    /**
     * Test of merge method, of class AdditionalFeatures.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");
        AdditionalFeatures<Integer> instance = new AdditionalFeatures<>();
        Iterable<Integer> expResult = Arrays.asList(1,2,2,3,3,4,5,6,6,7);
        Iterator<Integer> ite = expResult.iterator();
        Iterable<Integer> result = instance.merge(t1(),t2());
        for (Integer integer : result) {
            assertEquals(integer,ite.next());
        }
        assertEquals(null,instance.merge(null, null));

        result = instance.merge(t1(), new LinkedBinarySearchTree<>());
        expResult = Arrays.asList(2,3,4,5,6);
        ite = expResult.iterator();
        for (Integer integer : result) {
            assertEquals(integer,ite.next());
        }

        result = instance.merge(t1(), null);
        expResult = Arrays.asList(2,3,4,5,6);
        ite = expResult.iterator();
        for (Integer integer : result) {
            assertEquals(integer,ite.next());
        }
    }

}

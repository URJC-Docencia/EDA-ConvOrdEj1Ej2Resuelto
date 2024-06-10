package material.tree.binarysearchtree;

import material.Position;
import material.tree.binarytree.LinkedBinaryTree;

/**
 * LinkedBinarySearchTree that implements the tri-node restructuration
 * @author jvelez
 */
      
class Reestructurator {
    /**
     * Performs a tri-node restructuring. Assumes the nodes are in one of
     * following configurations:
     *
     * <pre>
     *          z=c       z=c        z=a         z=a
     *         /  \      /  \       /  \        /  \
     *       y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *      /  \      /  \           /  \         /  \
     *    x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *   /  \          /  \       /  \             /  \
     *  t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     *
     * @return the new root of the restructured subtree
     */
    public Position restructure(Position posNode, LinkedBinaryTree binTree) {
        LinkedBinaryTree lowKey, midKey, highKey, t1, t2, t3, t4;

        final Position posParent = binTree.parent(posNode); // assumes x has a parent
        final Position posGrandParent = binTree.parent(posParent); // assumes y has a parent

        final Position zParent = binTree.isRoot(posGrandParent) ? null : binTree.parent(posGrandParent);
        final boolean leftCase = zParent == null ? false : posGrandParent == binTree.left(zParent);
        
        final boolean nodeLeft = (binTree.hasLeft(posParent) && (posNode == binTree.left(posParent)));
        final boolean parentLeft = (binTree.hasLeft(posGrandParent) && (posParent == binTree.left(posGrandParent)));
        
        if (nodeLeft && parentLeft) {// Desequilibrio izda-izda
            lowKey = binTree.subTree(posNode);
            midKey = binTree.subTree(posParent);
            highKey = binTree.subTree(posGrandParent);
            t1 = (lowKey.hasLeft(lowKey.root())) ? lowKey.subTree(lowKey.left(lowKey.root())) : null;            
            t2 = (lowKey.hasRight(lowKey.root())) ? lowKey.subTree(lowKey.right(lowKey.root())) : null;

            t3 = (midKey.hasRight(midKey.root())) ? midKey.subTree(midKey.right(midKey.root())) : null;
            t4 = (highKey.hasRight(highKey.root())) ? highKey.subTree(highKey.right(highKey.root())) : null;
        } else if (!nodeLeft && parentLeft) {// Desequilibrio izda-dcha
            midKey = binTree.subTree(posNode);
            lowKey = binTree.subTree(posParent);
            highKey = binTree.subTree(posGrandParent);
            t1 = (lowKey.hasLeft(lowKey.root())) ? lowKey.subTree(lowKey.left(lowKey.root())) : null;            
            t2 = (midKey.hasLeft(midKey.root())) ? midKey.subTree(midKey.left(midKey.root())) : null;

            t3 = (midKey.hasRight(midKey.root())) ? midKey.subTree(midKey.right(midKey.root())) : null;
            t4 = (highKey.hasRight(highKey.root())) ? highKey.subTree(highKey.right(highKey.root())) : null;
        } else if (nodeLeft && !parentLeft) {// Desequilibrio dcha-izda
            midKey = binTree.subTree(posNode);
            highKey = binTree.subTree(posParent);
            lowKey = binTree.subTree(posGrandParent);
            t1 = (lowKey.hasLeft(lowKey.root())) ? lowKey.subTree(lowKey.left(lowKey.root())) : null;
            t2 = (midKey.hasLeft(midKey.root())) ? midKey.subTree(midKey.left(midKey.root())) : null;

            t3 = (midKey.hasRight(midKey.root())) ? midKey.subTree(midKey.right(midKey.root())) : null;
            t4 = (highKey.hasRight(highKey.root())) ? highKey.subTree(highKey.right(highKey.root())) : null;
        } else { // Desequilibrio dcha-dcha
            highKey = binTree.subTree(posNode);
            midKey = binTree.subTree(posParent);
            lowKey = binTree.subTree(posGrandParent);
            t1 = (lowKey.hasLeft(lowKey.root())) ? lowKey.subTree(lowKey.left(lowKey.root())) : null;
            t2 = (midKey.hasLeft(midKey.root())) ? midKey.subTree(midKey.left(midKey.root())) : null;

            t3 = (highKey.hasLeft(highKey.root())) ? highKey.subTree(highKey.left(highKey.root())) : null;
            t4 = (highKey.hasRight(highKey.root())) ? highKey.subTree(highKey.right(highKey.root())) : null;
        }
         
        
        // place the rest of the nodes and their children
        lowKey.attachLeft(lowKey.root(), t1);
        lowKey.attachRight(lowKey.root(), t2);
        highKey.attachLeft(highKey.root(), t3);
        highKey.attachRight(highKey.root(), t4);
                
        midKey.attachLeft(midKey.root(),lowKey);
        midKey.attachRight(midKey.root(), highKey);

        Position output = midKey.root();

        //If zparent is root binTree isEmpty and it must be replaced by midkey
        //If zparent not is null you must attach midkey in the corresponding position of zparent        
        if (zParent == null) {
            binTree.attach(midKey);
        } else { 
            if (leftCase) {
                binTree.attachLeft(zParent,midKey);
            } else {
                binTree.attachRight(zParent,midKey);
            }
        }
                
        return output;
    }
    
}

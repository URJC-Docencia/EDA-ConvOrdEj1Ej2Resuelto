package material.tree.binarysearchtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import material.Position;

/**
 * Realization of a red-black Tree by extending a binary search tree.
 * @author A. Duarte, J. Vélez
 * @param <E>
 */
public class RBTree<E> implements BinarySearchTree<E> {

//Esta clase es necesaria para guardar el valor de la altura AVL en los nodos BTNodes
    private class RBInfo<T> implements Comparable<RBInfo<T>>, Position<T> {

        private boolean isRed; // we add a color field to a BTNode
        private final T element;
        private Position<RBInfo<T>> pos;

        RBInfo(T element) {
            this.element = element;
        }

        public void setTreePosition(Position<RBInfo<T>> pos) {
            this.pos = pos;
        }

        public Position<RBInfo<T>> getTreePosition() {
            return this.pos;
        }

        public boolean isRed() {
            return isRed;
        }

        public void setRed(boolean color) {
            isRed = color;
        }

        @Override
        public T getElement() {
            return element;
        }

        @Override
        public boolean equals(Object obj) {
            RBInfo<T> info = (RBInfo<T>) obj;
            return element.equals(info.getElement());
        }

        @Override
        public int compareTo(RBInfo<T> o) {
            if (element instanceof Comparable && o.element instanceof Comparable) {
                Comparable<T> c1 = (Comparable<T>) element;
                return c1.compareTo(o.element);

            } else {
                throw new ClassCastException("Element is not comparable");
            }
        }
    }

    private class RBTreeIterator<T> implements Iterator<Position<T>> {

        private final Iterator<Position<RBInfo<T>>> it;

        public RBTreeIterator(Iterator<Position<RBInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<RBInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }

    private final LinkedBinarySearchTree<RBInfo<E>> resBT = new LinkedBinarySearchTree<>();
    private final Reestructurator reestructurator = new Reestructurator();

    @Override
    public Position<E> find(E value) {
        RBInfo<E> searchedValue = new RBInfo<>(value);
        Position<RBInfo<E>> output = resBT.find(searchedValue);

        if (output == null) {
            return null;
        }
        return output.getElement();
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
        RBInfo<E> searchedValue = new RBInfo<>(value);
        List<Position<E>> aux = new ArrayList<>();
        for (Position<RBInfo<E>> n : resBT.findAll(searchedValue)) {
            aux.add(n.getElement());
        }
        return aux;
    }

    @Override
    public boolean isEmpty() {
        return resBT.isEmpty();
    }

    @Override
    public int size() {
        return resBT.size();
    }

    /**
     * Inserts an element into the RBTree and returns the newly created postion.
     * @param e
     */
    @Override
    public Position<E> insert(E e) {
        RBInfo<E> aux = new RBInfo<>(e);

        Position<RBInfo<E>> posZ = resBT.insert(aux);
        aux.setTreePosition(posZ);
        aux.setRed(true);

        if (this.resBT.binTree.isRoot(posZ)) {
            aux.setRed(false);
        } else {
            remedyDoubleRed(aux); // fix a double-red color violation
        }
        return aux;
    }

    /**
     * Remedies a double red violation at a given node caused by insertion.
     * @param nodeZ
     */
    protected void remedyDoubleRed(RBInfo<E> nodeZ) {
        Position<RBInfo<E>> posV = this.resBT.binTree.parent(nodeZ.getTreePosition());
        RBInfo<E> nodeV = posV.getElement();

        if (!nodeV.isRed()) {
            return;
        }
        // we have a double red: posZ and posV
        Position<RBInfo<E>> grandParent = resBT.binTree.parent(posV);
        boolean hasSibling = resBT.binTree.hasLeft(grandParent) && resBT.binTree.hasRight(grandParent);
        boolean blackUncle = true;
        if (hasSibling) {
            Position<RBInfo<E>> uncleZ = this.resBT.binTree.sibling(posV);
            blackUncle = !uncleZ.getElement().isRed;
        }
        if (blackUncle) { // Case 1: trinode restructuring
            posV = reestructurator.restructure(nodeZ.getTreePosition(), resBT.binTree);
            posV.getElement().setRed(false);
            this.resBT.binTree.left(posV).getElement().setRed(true);
            this.resBT.binTree.right(posV).getElement().setRed(true);
        } else { // Case 2: recoloring
            nodeV.setRed(false);
            if (hasSibling) {
                this.resBT.binTree.sibling(posV).getElement().setRed(false);
            }
            Position<RBInfo<E>> posU = resBT.binTree.parent(posV);
            if (this.resBT.binTree.isRoot(posU)) {
                return;
            }
            RBInfo<E> nodeU = posU.getElement();
            nodeU.setRed(true);
            remedyDoubleRed(nodeU);
        }
    }

    @Override
    public void remove(Position<E> pos) throws IllegalStateException {
        RBInfo<E> rbInfo = (RBInfo<E>) pos;
        Position<RBInfo<E>> treePos = rbInfo.getTreePosition();

        Position<RBInfo<E>> parent = null;

        if (resBT.binTree.isLeaf(treePos) || !resBT.binTree.hasLeft(treePos) || !resBT.binTree.hasRight(treePos)) {
            if (resBT.binTree.root() != treePos) {
                parent = resBT.binTree.parent(treePos);
            }
            resBT.remove(treePos);
        } else {
            Position<RBInfo<E>> sucessor = resBT.sucessor(treePos);
            resBT.binTree.swap(sucessor, treePos);
            final boolean colorSuccesor = sucessor.getElement().isRed;
            sucessor.getElement().setRed(treePos.getElement().isRed());
            treePos.getElement().setRed(colorSuccesor);
            if (resBT.binTree.root() != treePos) {
                parent = resBT.binTree.parent(treePos);
            }
            resBT.remove(treePos);
        }

        Position<RBInfo<E>> nodeR = null;
        if (resBT.binTree.hasLeft(treePos))
            nodeR = resBT.binTree.left(treePos);
        else if (resBT.binTree.hasRight(treePos))
            nodeR = resBT.binTree.right(treePos);
        
        if ((nodeR != null) && (nodeR.getElement().isRed)) {
            nodeR.getElement().setRed(false);
            return;
        }
        
        if (treePos.getElement().isRed) {
            return;
        }
        
        if (resBT.isEmpty()) {
            return;
        }
        
        remedyDoubleBlack(parent, nodeR);
    }

    /**
     * Remedies a double black violation at a given node caused by removal.
     */
    protected void remedyDoubleBlack(Position<RBInfo<E>> doubleBlackParent, Position<RBInfo<E>> doubleBlack) {
        Position<RBInfo<E>> posX = doubleBlackParent;
        Position<RBInfo<E>> posZ = null;
        Position<RBInfo<E>> posY = null;
        boolean YisLeftChildOfX;
        
        if (resBT.binTree.hasLeft(posX) && resBT.binTree.left(posX) != doubleBlack) {
            posY = resBT.binTree.left(posX);
            YisLeftChildOfX = true;
        } else {
            posY = resBT.binTree.right(posX);
            YisLeftChildOfX = false;
        }

        RBInfo<E> nodeX = doubleBlackParent.getElement();
        RBInfo<E> nodeY = posY.getElement();

        if (!nodeY.isRed()) {
            posZ = hasRedChild(posY); //posZ != null means that it at least one red childpren
            if (posZ != null) { // Case 1: trinode restructuring
                boolean oldColor = nodeX.isRed();
                Position<RBInfo<E>> posB = reestructurator.restructure(posZ, resBT.binTree); //After restrusturing posZ gets the value of midKey
                posB.getElement().setRed(oldColor);
                resBT.binTree.left(posB).getElement().setRed(false);
                resBT.binTree.right(posB).getElement().setRed(false);
                return;
            } else { // Case 2: recoloring
                nodeY.setRed(true);
                if (!nodeX.isRed()) { //If X is black we propagate the doble black problem to the root
                    if (!resBT.binTree.isRoot(posX)) { //If X is root the problem is solved
                        remedyDoubleBlack(resBT.binTree.parent(posX),posX);  //In other case propagate
                    }
                    return;
                }
                nodeX.setRed(false);
                return;
            }
        } // Case 3: adjustment
        
        if (YisLeftChildOfX && resBT.binTree.hasLeft(posY)) {
            posZ = resBT.binTree.left(posY);
        } else if (resBT.binTree.hasRight(posY)) {
            posZ = resBT.binTree.right(posY);
        }
        reestructurator.restructure(posZ, resBT.binTree);
        nodeY.setRed(false);
        nodeX.setRed(true);
        remedyDoubleBlack(doubleBlackParent, doubleBlack);
    }

    /**
     * Returns a red child of a node.
     * @param pos
     * @return 
     */
    protected Position<RBInfo<E>> hasRedChild(Position<RBInfo<E>> pos) {
        Position<RBInfo<E>> child;
        if (resBT.binTree.hasLeft(pos)) {
            child = resBT.binTree.left(pos);
            final boolean redLeftChild = child.getElement() != null && child.getElement().isRed();
            if (redLeftChild) {
                return child;
            }
        }

        if (resBT.binTree.hasRight(pos)) {
            child = resBT.binTree.right(pos);
            final boolean redRightChild = child.getElement() != null && child.getElement().isRed();
            if (redRightChild) {
                return child;
            }
        }
        return null;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        Iterator<Position<RBInfo<E>>> bstIt = resBT.iterator();
        RBTreeIterator<E> it = new RBTreeIterator<>(bstIt);
        return it;
    }

    /**
     * Returns the position with the smallest value in the tree.
     * @return 
     */
    public Position<E> first() {

        //Se guarda en el position
        Position<E> nodo = resBT.first().getElement();
        //Se devuelve
        return nodo;
    }

    /**
     * 
     * @return 
     */
    public Position<E> last() {
        Position<E> nodo = resBT.last().getElement();
        //Se devuelve
        return nodo;
    }

    @Override
    public Iterable<? extends Position<E>> rangeIterator(E m, E M) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

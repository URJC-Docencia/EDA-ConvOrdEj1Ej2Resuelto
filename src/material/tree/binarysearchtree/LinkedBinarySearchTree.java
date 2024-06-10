package material.tree.binarysearchtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import material.Position;
import material.tree.binarytree.InorderBinaryTreeIterator;
import material.tree.binarytree.LinkedBinaryTree;

/**
 * Realization of a dictionary by means of a binary search tree.
 *
 * @author R. Cabido, A. Duarte, J. Vélez and J. Sánchez-Oro
 * @param <E> the type of the elements stored in the tree
 */
public class LinkedBinarySearchTree<E> implements BinarySearchTree<E> {

    LinkedBinaryTree<E> binTree;
    Comparator<E> comparator;
    int size;

    /**
     * Creates a BinarySearchTree with the default comparator.
     */
    public LinkedBinarySearchTree() {
        this(null);
    }

    /**
     * Creates a BinarySearchTree with the given comparator.
     *
     * @param c the comparator used to sort the nodes in the tree
     */
    public LinkedBinarySearchTree(Comparator<E> c) {
        if (c == null) {
            this.comparator = new DefaultComparator<>();
        } else {
            this.comparator = c;
        }
        this.binTree = new LinkedBinaryTree<>();
    }

    /**
     * Auxiliary method used by find, insert, and remove.
     *
     * @param value the value searched
     * @param pos the position to start the search
     * @return the position where value is stored
     */
    protected Position<E> treeSearch(E value, Position<E> pos) throws IllegalStateException, IndexOutOfBoundsException {
        E curValue = pos.getElement();
        int comp = comparator.compare(value, curValue);
        if ((comp < 0) && this.binTree.hasLeft(pos)) {
            return treeSearch(value, this.binTree.left(pos)); // search left
        } else if ((comp > 0) && this.binTree.hasRight(pos)) {
            return treeSearch(value, this.binTree.right(pos)); // search right
        }
        return pos;
    }

    /**
     * Adds to L all entries in the subtree rooted at pos having keys equal to
     * k.
     *
     * @param l the position to add all new entries
     * @param pos the starting position to scan entries
     * @param value the value of the nodes to be added to l
     */
    protected void addAll(List<Position<E>> l, Position<E> pos, E value) {
        Position<E> p = treeSearch(value, pos);

        if (comparator.compare(value, p.getElement()) == 0) {
            l.add(p);
            if (this.binTree.hasLeft(p)) {
                addAll(l, this.binTree.left(p), value);
            }
            if (this.binTree.hasRight(p)) {
                addAll(l, this.binTree.right(p), value);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Position<E> find(E value) {
        
        if (size == 0)
            return null;
        
        Position<E> curPos = treeSearch(value, this.binTree.root());

        if (comparator.compare(value, curPos.getElement()) == 0) {
            return curPos;
        } else {
            return null;
        }
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
        List<Position<E>> l = new ArrayList<>();
        addAll(l, this.binTree.root(), value);
        return l;
    }

    @Override
    public Position<E> insert(E value) {
        if (this.binTree.isEmpty()) {
            size = 1;
            return this.binTree.addRoot(value);
        }
        Position<E> insPos = treeSearch(value, this.binTree.root());

        if (comparator.compare(value, insPos.getElement()) == 0) {
            // To consider nodes already in the tree with the same key
            while (!this.binTree.isLeaf(insPos) && this.binTree.hasRight(insPos)) {
                insPos = treeSearch(value, this.binTree.right(insPos)); // iterative search for insertion position
            }
        }
        Position<E> retPos;
        if (this.comparator.compare(value, insPos.getElement()) < 0) {
            retPos = this.binTree.insertLeft(insPos, value);
        } else {
            retPos = this.binTree.insertRight(insPos, value);
        }
        size++;
        return retPos;
    }

    public Position<E> minimum(Position<E> pos) {
        while (this.binTree.hasLeft(pos)) {
            pos = this.binTree.left(pos);
        }
        return pos;
    }

    public Position<E> sucessor(Position<E> pos) {
        if (this.binTree.hasRight(pos)) {
            return minimum(this.binTree.right(pos));
        }
        
        if (this.binTree.root() == pos)
            return null;
                
        Position<E> aux = this.binTree.parent(pos);
        Position<E> rightChild = this.binTree.hasRight(aux) ? this.binTree.right(aux) : null;
        while (aux != null && pos == rightChild) {
            pos = aux;
            aux = this.binTree.root() == pos ? null : this.binTree.parent(pos);
            if (aux != null)
                rightChild = this.binTree.hasRight(aux) ? this.binTree.right(aux) : null;
        }
        
        return aux;
    }
    
    @Override
    public void remove(Position<E> pos) throws IllegalStateException {
        if (this.binTree.isLeaf(pos) || !this.binTree.hasLeft(pos) || !this.binTree.hasRight(pos)) {
            this.binTree.remove(pos);            
        } else {
            Position<E> sucessor = sucessor(pos);
            this.binTree.swap(sucessor,pos);
            this.binTree.remove(pos);
        }
        size--;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new InorderBinaryTreeIterator<>(this.binTree);
    }

    public Position<E> first() {
        //Comenzamos en la raiz
        Position<E> nodo = binTree.root();
        //mientras tenga hasnext, nodo pasara a ser el nodo hijo izquierdo    
        while (binTree.hasLeft(nodo)) {
            nodo = binTree.left(nodo);
        }
        return nodo;
    }

    public Position<E> last() {
        //Comenzamos en la raiz
        Position<E> nodo = binTree.root();
        //mientras tenga hasnext, nodo pasara a ser el nodo hijo derecho    
        while (binTree.hasRight(nodo)) {
            nodo = binTree.right(nodo);
        }
        return nodo;
    }

    @Override
    public Iterable<? extends Position<E>> rangeIterator(E m, E M) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}


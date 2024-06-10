/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import material.Position;

/**
 *
 * @author jvelez
 */
public class BreadthFirstTreeIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> nodeQueue = new LinkedList<>();
    private final Tree<T> tree;

    public BreadthFirstTreeIterator(Tree<T> tree, Position<T> root) {
        this.tree = tree;
        nodeQueue.add(root);
    }
    public BreadthFirstTreeIterator(Tree<T> tree) {
        this(tree, tree.root());
    }
    
    @Override
    public boolean hasNext() {
        return (!nodeQueue.isEmpty());
    }

    /**
     * This method visits the nodes of a tree by following a breath-first order
     */
    @Override
    public Position<T> next() {
        Position<T> aux = nodeQueue.pollFirst();
        for (Position<T> node : tree.children(aux)) {
            nodeQueue.addLast(node);
        }
        return aux;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree;

import material.Position;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author jvelez
 * @param <T>
 */
public class PreOrderTreeIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> nodeQueue = new LinkedList<>();
    private final Tree<T> tree;

    public PreOrderTreeIterator(Tree<T> tree) {
        this(tree, tree.root());
    }

    public PreOrderTreeIterator(Tree<T> tree, Position<T> root) {
        this.tree = tree;
        nodeQueue.add(root);
    }

    @Override
    public boolean hasNext() {
        return (!nodeQueue.isEmpty());
    }

    /**
     * This method visits the nodes of a tree by following a pre-order
     */
    @Override
    public Position<T> next() {
        Position<T> aux = nodeQueue.pollFirst();
        
        Deque<Position<T>> reverseList = new LinkedList<>();        
        for (Position<T> node : tree.children(aux)) {
            reverseList.addFirst(node);
        }
        for (Position<T> node : reverseList) {
            nodeQueue.addFirst(node);
        }
        return aux;
    }

}

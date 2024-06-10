/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree.binarysearchtree;

import material.Position;

/**
 *
 * @author A. Duarte, J. Vélez
 * @param <E>
 * 
 */
public interface BinarySearchTree<E> extends Iterable<Position<E>> {

    /**
     * Returns an entry containing the given key. Returns null if no such entry
     * exists.
     * @param value
     * @return 
     */
    Position<E> find(E value);

    /**
     * Returns an iterable collection of all the entries containing the given
     * key.
     * @param value
     * @return 
     */
    Iterable<? extends Position<E>> findAll(E value);

    /**
     * Inserts an entry into the tree and returns the newly created entry.
     * @param value
     * @return 
     */
    Position<E> insert(E value);

    /**
     * Returns whether the tree is empty.
     * @return 
     */
    boolean isEmpty();


    /**
     * Removes a position.
     * @param pos the position to be removed.
     */
    void remove(Position<E> pos);

    /**
     * Returns the number of entries in the tree.
     * @return 
     */
    int size();
    
    /**
     * 
     * @param m minimun value
     * @param M maximun value
     * @return an iterator from m to M (both included)
     */
    Iterable<? extends Position<E>> rangeIterator(E m, E M);
}

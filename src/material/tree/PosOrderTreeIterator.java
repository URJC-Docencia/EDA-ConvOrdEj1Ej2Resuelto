package material.tree;

import material.Position;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import material.utils.Pair;

/**
 *
 * @author jvelez
 * @param <T>
 */
public class PosOrderTreeIterator<T> implements Iterator<Position<T>> {

    private final Deque< Pair<Position<T>,Iterator >> nodeStack = new LinkedList<>();
    private final Tree<T> tree;

    public PosOrderTreeIterator(Tree<T> tree) {
        this(tree, tree.root());
    }

    public PosOrderTreeIterator(Tree<T> tree, Position<T> root) {
        this.tree = tree;
        Iterator p = tree.children(root).iterator();
        nodeStack.add(new Pair <>(root,p));
    }

    @Override
    public boolean hasNext() {
        return (!nodeStack.isEmpty());
    }

    /**
     * This method visits the nodes of a tree by following a pos-order
     */
    @Override
    public Position<T> next() {
        if (nodeStack.isEmpty())
            throw new RuntimeException("No next element");
        
        Pair<Position<T>,Iterator> element = nodeStack.getLast();
        Position<T> node = element.getFirst();
        Iterator iterator = element.getSecond();
        
        while (tree.isInternal(node) && iterator.hasNext()) {
            node = (Position<T>) iterator.next();
            iterator = tree.children(node).iterator();
            nodeStack.addLast(new Pair <>(node,iterator));
        }

        nodeStack.removeLast();
        return node;

    }

}

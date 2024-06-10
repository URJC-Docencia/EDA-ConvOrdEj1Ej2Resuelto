
package material.tree.binarytree;

import material.Position;

/**
 *
 * @author escribe_aquí_tu_nombre
 * @param <T>
 */
public class FunHandling<T> {
    
    /**
     * Given A binary Tree removes all the half nodes. A hafl node has only one child.
     * @param t 
     */
    public void removeHalfNodes(BinaryTree<T> t){
        // comprar que el árbol no está vacio ni es nullo
        if (!t.isEmpty()){


            // recorrer el árbol
            var it = t.iterator();
            while (it.hasNext()) {

                Position<T> p = it.next();

                // detectar que se cumple la condición: UN ÚNICO HIJO
                if (t.hasLeft(p) && !t.hasRight(p)) {
                    // si se cumple: lo elimino
                    t.remove(p);
                } else if (!t.hasLeft(p) && t.hasRight(p)) {
                    // si se cumple: lo elimino
                    t.remove(p);
                }
            }
        }

    }
       
}

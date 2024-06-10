
package material.tree.binarysearchtree;

import material.Position;

import java.util.ArrayList;

/**
 * @author escribe_aquí_tu_nombre
 * @param <T>
 */
public class AdditionalFeatures<T> {

    /***
     * Given two Binary Search Trees, returns an iterable data structure with the elements of both Binary Search Trees sorted.
     * @param t1
     * @param t2
     * @return
     */
    public Iterable<T> merge(BinarySearchTree<T> t1, BinarySearchTree<T> t2){

        ArrayList<T> list = new ArrayList<>();


        // Casos raros: situaciones que hay que contrar y que no están explicadas en el enunciado

        // Que un árbol sean null
        // Que un árbol esté vacio

        // Los dos son null
        if (t1 == null && t2 == null){
            return null;
        }

        // T1 sea null

        if(t1 == null){
            if (t2.isEmpty()){
                return list;
            }
            for(Position<T> e : t2){
                list.add(e.getElement());
            }
            return list;
        }

        // T2 sea null
        if(t2 == null){
            if (t1.isEmpty()){
                return list;
            }
            for(Position<T> e : t1){
                list.add(e.getElement());
            }
            return list;
        }

        // Los dos vacios
        if (t1.isEmpty() && t2.isEmpty()){
            return list;
        }


        // T1 vacio
        if(t1.isEmpty()){
            for(Position<T> e : t2){
                list.add(e.getElement());
            }
            return list;
        }

        // T2 vacio
        if(t2.isEmpty()){
            for(Position<T> e : t1){
                list.add(e.getElement());
            }
            return list;
        }





        // Caso Esperado: lo que me pide el enunciado

        // itero los árboles al mismo tiempo
        var iterT1 = t1.iterator();
        var iterT2 = t2.iterator();

        // saco los elementos del árbol
        T e1 = iterT1.next().getElement();
        T e2 = iterT2.next().getElement();

        // itero lo árboles hasta que estén vacios, no haya más elementos
        while (e1 != null && e2 != null){
            // comparo los elementos
            if (((Comparable) e1).compareTo(e2) < 0){
                // el más pequeño lo meto en la lista
                list.add(e1);

                // del árbol que he sacado el elemento, me muevo al siguiente
                // hay elemento siguiente
                if (iterT1.hasNext()){
                    e1 = iterT1.next().getElement();
                    // no hay más elementos
                } else {
                    // e1 = null; Opción 1

                    // Opción 2
                    while (e2 != null){
                        list.add(e2);
                        e2 = iterT2.hasNext() ? iterT2.next().getElement() : null;
                    }
                }
            } else {
                list.add(e2);

                // del árbol que he sacado el elemento, me muevo al siguiente

                if (iterT2.hasNext()){
                    e2 = iterT2.next().getElement();
                } else {
                    // e2 = null; Opción 1

                    // Opción 2
                    while (e1 != null){
                        list.add(e1);
                        e1 = iterT1.hasNext() ? iterT1.next().getElement() : null;
                    }
                }
            }


        }

/*        Opción 1
           while (e1 == null && e2 != null){
            list.add(e2);
            if (iterT2.hasNext()){
                e2 = iterT2.next().getElement();
            } else {
                e2 = null;
            }
        }

        while (e2 == null && e1 != null){
            list.add(e1);
            if (iterT1.hasNext()){
                e1 = iterT1.next().getElement();
            } else {
                e1 = null;
            }
        }*/
        return list;
    }
}

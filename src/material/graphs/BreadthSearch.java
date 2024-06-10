package material.graphs;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import material.Position;
import material.tree.Tree;
import material.tree.narytree.LinkedTree;
import material.utils.Pair;

/**
 *
 * @author jvelez
 * @param <V>
 * @param <E>
 */
public class BreadthSearch<V, E> {

    private List<Edge<E>> pathToRoot(Digraph<V, E> diGraph, Position<Vertex<V>> node, LinkedTree<Vertex<V>> tree) {
        List<Edge<E>> result = new LinkedList<>();

        while (node != tree.root()) {
            Position<Vertex<V>> parent = tree.parent(node);
            Edge<E> edge = aristaEntreDosNodos(diGraph,node,parent);
            if (edge == null)
                throw new RuntimeException("There is no way");
            result.add(0, edge);
            node = parent;
        }

        return result;
    }

    private Edge<E> aristaEntreDosNodos(Digraph<V, E> diGraph, Position<Vertex<V>> node, Position<Vertex<V>> parent) {
        Collection<? extends Edge<E>> incidentEdges = diGraph.incidentEdges(node.getElement());
        for (Edge<E> incidentEdge : incidentEdges) {
            if (diGraph.opposite(node.getElement(), incidentEdge) == parent.getElement())
                return incidentEdge;
            }
        return null;
    }

    enum Label {
        DISCOVERY, CROSS
    };

    private List<Edge<E>> pathToRoot(Graph<V, E> g, Position<Vertex<V>> node, LinkedTree<Vertex<V>> tree) {
        List<Edge<E>> result = new LinkedList<>();

        while (node != tree.root()) {
            Position<Vertex<V>> parent = tree.parent(node);
            Edge<E> edge = g.areAdjacent(node.getElement(), parent.getElement());
            result.add(0, edge);
            node = parent;
        }

        return result;
    }

    /**
     * Get the path between two vertex with minimun number of nodes.
     *
     * @param graph
     * @param startVertex
     * @param endVertex
     * @return The edge list
     */
    public List<Edge<E>> getPath(Graph<V, E> graph, Vertex<V> startVertex, Vertex<V> endVertex) {
        LinkedTree<Vertex<V>> tree = new LinkedTree<>();
        HashMap<Edge<E>, Label> edgeLabels = new HashMap<>();

        Queue<Position<Vertex<V>>> queue = new LinkedList<>();
        HashSet<Vertex<V>> visitedNodes = new HashSet<>();

        visitedNodes.add(startVertex);
        tree.addRoot(startVertex);
        queue.add(tree.root());

        while (!queue.isEmpty()) {
            Position<Vertex<V>> vetexPos = queue.poll();
            Vertex<V> vertex = vetexPos.getElement();
            for (Edge<E> edge : graph.incidentEdges(vertex)) {
                if (edgeLabels.get(edge) == null) {
                    Vertex<V> nextNode = graph.opposite(vertex, edge);
                    if (!visitedNodes.contains(nextNode)) {
                        edgeLabels.put(edge, Label.DISCOVERY);
                        visitedNodes.add(nextNode);
                        Position<Vertex<V>> treeNode = tree.add(nextNode, vetexPos);
                        queue.add(treeNode);
                        if (nextNode == endVertex) {
                            return pathToRoot(graph, treeNode, tree);
                        }
                    } else {
                        edgeLabels.put(edge, Label.CROSS);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Devuelve el conjunto de vértices visitados al hacer un recorrido en profundidad partiendo de root
     * @param g
     * @param root
     * @return 
     */
    public Set<Vertex<V>> traverse(Graph<V, E> g, Vertex<V> root) {
        Set<Vertex<V>> visited = new HashSet<>();
        Queue<Vertex<V>> q = new ArrayDeque<>();
        q.add(root);
        visited.add(root);
        while (!q.isEmpty()) {
            Vertex<V> v = q.peek();
            visited.add(v);
            for (Edge<E> e : g.incidentEdges(v)) {
                Vertex<V> op = g.opposite(v, e);
                if (!visited.contains(op)) {
                    q.add(op);
                    visited.add(op);
                }
            }
            q.poll();
        }
        return visited;
    }
    
    /**
     * Devuelve el árbol que se genera al realizar el recorrido en profundidad
     * 
     * @param graph
     * @param source
     * @return 
     */
    
    public Tree<Vertex<V>> depthTravel(Graph<V,E> graph, Vertex<V> source){
        Set<Vertex<V>> visited=new HashSet<>();
        LinkedTree<Vertex<V>> result=new LinkedTree<>();
        
        //Position<Vertex<V>> root = result.addRoot(source);
        
        Map<Vertex<V>,Position<Vertex<V>>> reference=new HashMap<>();
        //reference.put(source,root);
        
        Stack<Pair<Vertex<V>,Vertex<V>>> pila=new Stack<>();
        pila.add(new Pair<>(source,null));//la raiz no tiene padre
        while(!pila.isEmpty()){
            Pair<Vertex<V>, Vertex<V>> pop = pila.pop();
            Vertex<V> node=pop.getFirst();
            Vertex<V> parent=pop.getSecond();
            if(!visited.contains(node)){
                Collection<? extends Edge<E>> incidentEdges = graph.incidentEdges(node);
                for(Edge<E> e : incidentEdges){
                    Vertex<V> opposite = graph.opposite(node, e);
                    pila.add(new Pair<>(opposite,node));
                }
                visited.add(node);
                if(parent==null){//caso raiz
                    reference.put(node, result.addRoot(node));
                }else{
                    Position<Vertex<V>> padrePos = reference.get(parent);
                    Position<Vertex<V>> posHijo = result.add(node, padrePos);
                    reference.put(node, posHijo);
                }
            }
        }
        
        
        return result;
    }
    
    
    /**
     * Devuelve el árbol que se genera al realizar el recorrido en anchura
     * 
     * @param graph
     * @param source
     * @return 
     */
    
    public Tree<Vertex<V>> widthTravel(Graph<V,E> graph, Vertex<V> source){
        Set<Vertex<V>> visited=new HashSet<>();
        LinkedTree<Vertex<V>> result=new LinkedTree<>();
        
       
        
        Map<Vertex<V>,Position<Vertex<V>>> reference=new HashMap<>();
        
        
        Deque<Pair<Vertex<V>,Vertex<V>>> cola=new LinkedList<>();
        cola.addLast(new Pair<>(source,null));//la raiz no tiene padre
        while(!cola.isEmpty()){
            Pair<Vertex<V>, Vertex<V>> pop = cola.pollFirst();
            Vertex<V> node=pop.getFirst();
            Vertex<V> parent=pop.getSecond();
            if(!visited.contains(node)){
                Collection<? extends Edge<E>> incidentEdges = graph.incidentEdges(node);
                for(Edge<E> e : incidentEdges){
                    Vertex<V> opposite = graph.opposite(node, e);
                    cola.addLast(new Pair<>(opposite,node));
                }
                visited.add(node);
                if(parent==null){//caso raiz
                    reference.put(node, result.addRoot(node));
                }else{
                    Position<Vertex<V>> padrePos = reference.get(parent);
                    Position<Vertex<V>> posHijo = result.add(node, padrePos);
                    reference.put(node, posHijo);
                }
            }
        }
        
        
        
        return result;
    
    }

    /**
     * Get the path between two vertex with minimun number of nodes.
     *
     * @param diGraph
     * @param startVertex
     * @param endVertex
     * @return The edge list
     */
    public List<Edge<E>> getPath(Digraph<V, E> diGraph, Vertex<V> startVertex, Vertex<V> endVertex) {
        LinkedTree<Vertex<V>> tree = new LinkedTree<>();
        HashMap<Edge<E>, Label> edgeLabels = new HashMap<>();

        Queue<Position<Vertex<V>>> queue = new LinkedList<>();
        HashSet<Vertex<V>> visitedNodes = new HashSet<>();

        visitedNodes.add(startVertex);
        tree.addRoot(startVertex);
        queue.add(tree.root());

        while (!queue.isEmpty()) {
            Position<Vertex<V>> vetexPos = queue.poll();
            Vertex<V> vertex = vetexPos.getElement();
            for (Edge<E> edge : diGraph.outputEdges(vertex)) {
                if (edgeLabels.get(edge) == null) {
                    Vertex<V> nextNode = diGraph.opposite(vertex, edge);
                    if (!visitedNodes.contains(nextNode)) {
                        edgeLabels.put(edge, Label.DISCOVERY);
                        visitedNodes.add(nextNode);
                        Position<Vertex<V>> treeNode = tree.add(nextNode, vetexPos);
                        queue.add(treeNode);
                        if (nextNode == endVertex) {
                            return pathToRoot(diGraph, treeNode, tree);
                        }
                    } else {
                        edgeLabels.put(edge, Label.CROSS);
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Devuelve el árbol que se genera al realizar el recorrido en anchura sobre un Digrafo
     * raph
     * @param diGraph
     * @param source
     * @return 
     */
    public Tree<Vertex<V>> widthTravel(Digraph<V,E> diGraph, Vertex<V> source){
        
        Set<Vertex<V>> visited=new HashSet<>();
        LinkedTree<Vertex<V>> result=new LinkedTree<>();
        
        //Position<Vertex<V>> root = result.addRoot(source);
        
        Map<Vertex<V>,Position<Vertex<V>>> reference=new HashMap<>();
        //reference.put(source,root);
        
        Deque<Pair<Vertex<V>,Vertex<V>>> cola=new LinkedList<>();
        cola.addLast(new Pair<>(source,null));//la raiz no tiene padre
        while(!cola.isEmpty()){
            Pair<Vertex<V>, Vertex<V>> pop = cola.pollFirst();
            Vertex<V> node=pop.getFirst();
            Vertex<V> parent=pop.getSecond();
            if(!visited.contains(node)){
                Collection<? extends Edge<E>> outputEdges = diGraph.outputEdges(node);
                for(Edge<E> e : outputEdges){
                    Vertex<V> opposite = diGraph.opposite(node, e);
                    cola.addLast(new Pair<>(opposite,node));
                }
                visited.add(node);
                if(parent==null){//caso raiz
                    reference.put(node, result.addRoot(node));
                }else{
                    Position<Vertex<V>> padrePos = reference.get(parent);
                    Position<Vertex<V>> posHijo = result.add(node, padrePos);
                    reference.put(node, posHijo);
                }
            }
        }
        return result;
    
    }
    
    /**
     * Devuelve el árbol que se genera al realizar el recorrido en profundidad sobre un Digrafo
     * raph
     * @param diGraph
     * @param source
     * @return 
     */
    
    public Tree<Vertex<V>> depthTravel(Digraph<V,E> diGraph, Vertex<V> source){
        Set<Vertex<V>> visited=new HashSet<>();
        LinkedTree<Vertex<V>> result=new LinkedTree<>();
        
        //Position<Vertex<V>> root = result.addRoot(source);
        
        Map<Vertex<V>,Position<Vertex<V>>> reference=new HashMap<>();
        //reference.put(source,root);
        
        Stack<Pair<Vertex<V>,Vertex<V>>> pila=new Stack<>();
        pila.add(new Pair<>(source,null));//la raiz no tiene padre
        while(!pila.isEmpty()){
            Pair<Vertex<V>, Vertex<V>> pop = pila.pop();
            Vertex<V> node=pop.getFirst();
            Vertex<V> parent=pop.getSecond();
            if(!visited.contains(node)){
                Collection<? extends Edge<E>> outputEdges = diGraph.outputEdges(node);
                for(Edge<E> e : outputEdges){
                    Vertex<V> opposite = diGraph.opposite(node, e);
                    pila.add(new Pair<>(opposite,node));
                }
                visited.add(node);
                if(parent==null){//caso raiz
                    reference.put(node, result.addRoot(node));
                }else{
                    Position<Vertex<V>> padrePos = reference.get(parent);
                    Position<Vertex<V>> posHijo = result.add(node, padrePos);
                    reference.put(node, posHijo);
                }
            }
        }
        
        
        return result;
    }
}

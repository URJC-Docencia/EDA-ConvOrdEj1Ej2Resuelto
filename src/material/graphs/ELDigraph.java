
package material.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author mayte
 */
public class ELDigraph<V,E> implements Digraph <V,E> {

    private ELEdge<E, V> checkEdge(Edge<E> e) {
        if ((e == null)||!(e instanceof Edge))
            throw new RuntimeException("This is an invalid edge ");
        ELEdge<E,V> arista = (ELEdge<E,V>) e;
        if (arista.graph != this)
            throw new RuntimeException("This edge does not belong to this graph");
        return arista; 
    }
    
    private ELVertex<V> checkVertex(Vertex<V> v){
        if ((v == null)||!(v instanceof Vertex))
            throw new RuntimeException("This is an invalid vertex");
        ELVertex<V> node = (ELVertex<V>) v;
        if (node.graph != this)
            throw new RuntimeException("This vertex does not belong to this graph");
        return node;
    }
    
    private class ELVertex<V> implements Vertex<V> {

        private V vertexValue;
        private final Digraph graph;

        @Override
        public V getElement() {
            return vertexValue;
        }

        public void getElement(V value) {
            vertexValue = value;
        }

        public ELVertex(V value, Digraph graph) {
            vertexValue = value;
            this.graph = graph;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 37 * hash + Objects.hashCode(this.vertexValue);
            hash = 37 * hash + Objects.hashCode(this.graph);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ELVertex<V> other = (ELVertex<V>) obj;
            if (!Objects.equals(this.vertexValue, other.vertexValue)) {
                return false;
            }
            return Objects.equals(this.graph, other.graph);
        }
        
        /**
         * @return the graph
         */
        public Digraph getGraph() {
            return graph;
        }
    }
    private class ELEdge<E, V> implements Edge<E> {

        private E edgeValue;
        private final Digraph graph;

        private final Vertex<V> startVertex;
        private final Vertex<V> endVertex;

        @Override
        public int hashCode() {
            int hash = 3;
            final int min = Math.min(this.startVertex.hashCode(), this.endVertex.hashCode());
            final int max = Math.max(this.startVertex.hashCode(), this.endVertex.hashCode());
            
            hash = 59 * hash + Objects.hashCode(this.graph);
            hash = 59 * hash + min;
            hash = 59 * hash + max;
            return hash;
        }

        

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }

            final ELEdge<E, V> other = (ELEdge<E, V>) obj;
            if (!Objects.equals(this.graph, other.graph)) {
                return false;
            }

            if (!this.startVertex.equals(other.startVertex)) {
                return false;
            }
            if (!this.endVertex.equals(other.endVertex)) {
                return false;
            }
            return true;
        }

        @Override
        public E getElement() {
            return edgeValue;
        }

        public ELEdge(E value, Vertex<V> startVertex, Vertex<V> endVertex, Digraph graph) {
            edgeValue = value;
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.graph = graph;
        }

        public void setElement(E value) {
            edgeValue = value;
        }

        /**
         * @return the startVertex
         */
        public Vertex<V> getStartVertex() {
            return startVertex;
        }

        /**
         * @return the endVertex
         */
        public Vertex<V> getEndVertex() {
            return endVertex;
        }

        /**
         * @return the graph
         */
        public Digraph getGraph() {
            return graph;
        }
    }


    private final Set <ELVertex<V>> vertexList = new HashSet<>();
    private final Set <ELEdge<E,V>> edgeList = new HashSet<>();
    private final HashMap<ELVertex<V>,List<ELEdge<E,V>>> incidentEdges = new HashMap<>();
    private final HashMap<ELVertex<V>,List<ELEdge<E,V>>> outputEdges = new HashMap<>();
    
    @Override
    public Collection<? extends Vertex<V>> vertices() {
        return Collections.unmodifiableCollection(vertexList);
    }

    @Override
    public Collection<? extends Edge<E>> edges() {
        return Collections.unmodifiableCollection(edgeList);
    }

    @Override
    public Collection<? extends Edge<E>> incidentEdges(Vertex<V> v) {
        ELVertex<V> node = checkVertex(v);
        return Collections.unmodifiableList(incidentEdges.get(node));
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        ELEdge<E, V> elv = checkEdge(e);

        if (elv.getStartVertex() == v) {
            return elv.getEndVertex();
        } else if (elv.getEndVertex() == v) {
            return elv.getStartVertex();
        } else {
            throw new RuntimeException("The vertex is not in the edge");
        }
    }

    @Override
    public Vertex<V> endVertice(Edge<E> edge) {
        ELEdge<E, V> elv = checkEdge(edge);
        return elv.getEndVertex();
    }

    @Override
    public Vertex<V> startVertice(Edge<E> edge) {
        ELEdge<E, V> elv = checkEdge(edge);
        return elv.getStartVertex();
    }

    @Override
    public boolean areAdjacent(Vertex<V> v1, Vertex<V> v2) {
        ELVertex<V> node = checkVertex(v1);
        List<ELEdge<E, V>> get = outputEdges.get(node);
        if (!(get == null || get.isEmpty())){            
            for (ELEdge<E, V> eLEdge : get) {
                if (eLEdge.endVertex == v2)
                    return true;
            }
        }
        get = incidentEdges.get(node);
        if (get == null || get.isEmpty())
            return false;
        for (ELEdge<E, V> eLEdge : get) {
            if (eLEdge.startVertex == v2)
                return true;
        }
        return false;
    }

    @Override
    public V replace(Vertex<V> vertex, V vertexValue) {
        ELVertex<V> node = checkVertex(vertex);     
        V ant = node.getElement();
        node.vertexValue = vertexValue;
        return ant;
            
    }

    @Override
    public E replace(Edge<E> edge, E edgeValue) {
        ELEdge<E,V> arista = checkEdge(edge);
        E ant = arista.getElement();
        arista.edgeValue = edgeValue;
        return ant;
    }

    @Override
    public Vertex<V> insertVertex(V value) {
        ELVertex<V> nodo = new ELVertex<>(value,this);
        if (vertexList.contains(nodo))
            throw new RuntimeException("This graph already contains this vertex");
        vertexList.add(nodo);
        incidentEdges.put(nodo, new ArrayList<>());
        outputEdges.put(nodo, new ArrayList<>());
        return nodo;
        
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> v1, Vertex<V> v2, E edgeValue) {
        ELVertex<V> start = checkVertex(v1);
        ELVertex<V> end = checkVertex(v2);
        ELEdge<E,V> arista = new ELEdge<>(edgeValue,start,end,this);
        if (!edgeList.contains(arista)){//nueva arista
            edgeList.add(arista);
            outputEdges.get(start).add(arista);
            incidentEdges.get(end).add(arista);
        }else{//la arista existe y hay que cambiar el valor asociado
            for (ELEdge<E, V> e : outputEdges.get(start)) {
                if (e.equals(arista)){
                    e.edgeValue = edgeValue;
                    return arista;
                }
            }
            
        }
        return arista;
    }

    /*private final Set <ELVertex<V>> vertexList = new HashSet<>();
    private final Set <ELEdge<E,V>> edgeList = new HashSet<>();
    private final HashMap<ELVertex<V>,List<ELEdge<E,V>>> incidentEdges = new HashMap<>();
    private final HashMap<ELVertex<V>,List<ELEdge<E,V>>> outputEdges = new HashMap<>();
    */
    @Override
    public List<Edge<E>> outputEdges(Vertex<V> v) {
        ELVertex<V> nodo = checkVertex(v);
        return Collections.unmodifiableList(outputEdges.get(nodo));
    }

    
    @Override
    public V removeVertex(Vertex<V> vertex) {
        ELVertex<V> nodo = checkVertex(vertex);
        vertexList.remove(nodo);//eliminamos el nodo del conjunto de nodos
        for (ELEdge<E,V> arista: incidentEdges.get(nodo)){
            edgeList.remove(arista);//eliminamos las aristas que tienen el nodo como nodo final
            outputEdges.get(arista.startVertex).remove(arista);
        }
        for (ELEdge<E,V> arista: outputEdges.get(nodo)){
            edgeList.remove(arista);//eliminamos las aristas que tienen el nodo como nodo inicial
            incidentEdges.get(arista.endVertex).remove(arista);
        }
        incidentEdges.remove(nodo);
        outputEdges.remove(nodo);
        
        return nodo.vertexValue;
    }

    @Override
    public E removeEdge(Edge<E> edge) {
        ELEdge<E,V> arista = checkEdge(edge);
        edgeList.remove(edge);
        incidentEdges.get(arista.endVertex).remove(arista);
        outputEdges.get(arista.startVertex).remove(arista);
        
        return arista.edgeValue;
    }

    
    
}

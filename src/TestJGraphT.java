import java.net.*;
import java.util.List;

import org.jgrapht.*;
import org.jgrapht.alg.cycle.JohnsonSimpleCycles;
import org.jgrapht.graph.*;

public class TestJGraphT {
    private TestJGraphT()
    {
    } // ensure non-instantiability.

    /**
     * The starting point for the demo.
     *
     * @param args ignored.
     */
    public static void main(String[] args)
    {
        DirectedGraph<String, DefaultEdge> stringGraph = createStringGraph();

        // note undirected edges are printed as: {<v1>,<v2>}
        System.out.println(stringGraph.toString());

        // create a graph based on URL objects
        //DirectedGraph<URL, DefaultEdge> hrefGraph = createHrefGraph();

        // note directed edges are printed as: (<v1>,<v2>)
        //System.out.println(hrefGraph.toString());
        
        JohnsonSimpleCycles<String, DefaultEdge> johnsonSimpleCyclesFinder = new JohnsonSimpleCycles(stringGraph) ;
        List< List<String> > cycles = johnsonSimpleCyclesFinder.findSimpleCycles();
        
        // output will be [[v1], [v2,v1], [V4,v3,v2,v1]] 
        for (int i=0; i<cycles.size();i++){
        	List<String> currentCycle = cycles.get(i);
        	for (int j=0;j<currentCycle.size(); j++){
        		System.out.print(currentCycle.get(j)+" ");
        	}
        	System.out.println();
        }
    }

    /**
     * Creates a toy directed graph based on URL objects that represents link structure.
     *
     * @return a graph based on URL objects.
     */
    private static DirectedGraph<URL, DefaultEdge> createHrefGraph()
    {
        DirectedGraph<URL, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

        try {
            URL amazon = new URL("http://www.amazon.com");
            URL yahoo = new URL("http://www.yahoo.com");
            URL ebay = new URL("http://www.ebay.com");

            // add the vertices
            g.addVertex(amazon);
            g.addVertex(yahoo);
            g.addVertex(ebay);

            // add edges to create linking structure
            g.addEdge(yahoo, amazon);
            g.addEdge(yahoo, ebay);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return g;
    }

    /**
     * Create a toy graph based on String objects.
     * There are 3 cycles in this graph: v1 (to it self), v1-v2, v1-v2-v3-v4 
     *
     * @return a graph based on String objects.
     */
    private static DirectedGraph<String, DefaultEdge> createStringGraph()
    {
        DirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        // add edges to create a circuit
        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v4);
        g.addEdge(v4, v1);
        g.addEdge(v1, v1); //a cycle from a vertex back to it self
        g.addEdge(v2, v1); //a cycle of two vertices

        return g;
    }
}

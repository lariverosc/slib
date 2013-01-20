package slib.sglib.algo.graph.extraction.rvf.instances.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.openrdf.model.vocabulary.RDF;
import slib.sglib.algo.graph.extraction.rvf.AncestorEngine;
import slib.sglib.algo.graph.extraction.rvf.DescendantEngine;
import slib.sglib.algo.graph.extraction.rvf.instances.InstancesAccessor;
import slib.sglib.model.graph.G;
import slib.sglib.model.graph.elements.V;
import slib.sglib.model.graph.elements.type.VType;
import slib.sglib.model.graph.utils.Direction;

/**
 *
 * @author seb
 */
public class InstanceAccessor_RDF_TYPE implements InstancesAccessor {

    G graph;
    DescendantEngine descendantsEngine;

    /**
     * @param graph
     */
    public InstanceAccessor_RDF_TYPE(G graph) {
        this.graph = graph;
        this.descendantsEngine = new DescendantEngine(graph);
    }

    @Override
    public Set<V> getDirectInstances(V v) {
        return graph.getV(v, RDF.TYPE, Direction.IN);
    }

    @Override
    public long getInstancesNumber(V v) {
        // TODO Auto-generated method stub Aug 30, 2012
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<V, Long> getInferredInstancesNumberMapping() {
        // TODO Auto-generated method stub Aug 30, 2012
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public long getDirectInstancesNumber(V v) {
        return graph.getV(v, RDF.TYPE, Direction.IN).size();
    }

    @Override
    public Map<V, Long> getDirectInstancesNumberMapping() {
        // TODO Auto-generated method stub Aug 30, 2012
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public Set<V> getInstances() {
        return graph.getV(VType.INSTANCE);
    }

    @Override
    public Set<V> getDirectClass(V v) {
        return graph.getV(v, RDF.TYPE, Direction.OUT);
    }

    @Override
    public Set<V> getInstances(V v) {
        
        
        Set<V> instances = new HashSet<V>();
        
        instances.addAll(getDirectInstances(v));
        System.out.println("Processing "+v);
        System.out.println("Number of decendants \t"+descendantsEngine.getDescendants(v).size());
        for(V d : descendantsEngine.getDescendants(v)){
            instances.addAll(getDirectInstances(d));
        }
        
        // TO remove just to test coherency of results
        System.out.println("Instance number: "+instances.size());
        Set<V> i = graph.getV(VType.INSTANCE);
        i.removeAll(instances);
        
        V instance = i.iterator().next();
        System.out.println(instance);
        Set<V> classes = graph.getV(instance, RDF.TYPE, Direction.OUT);
        System.out.println(classes);
        return instances;
    }
}
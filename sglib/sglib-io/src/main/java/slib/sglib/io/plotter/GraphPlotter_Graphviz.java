/*

Copyright or © or Copr. Ecole des Mines d'Alès (2012) 

This software is a computer program whose purpose is to 
process semantic graphs.

This software is governed by the CeCILL  license under French law and
abiding by the rules of distribution of free software.  You can  use, 
modify and/ or redistribute the software under the terms of the CeCILL
license as circulated by CEA, CNRS and INRIA at the following URL
"http://www.cecill.info". 

As a counterpart to the access to the source code and  rights to copy,
modify and redistribute granted by the license, users are provided only
with a limited warranty  and the software's author,  the holder of the
economic rights,  and the successive licensors  have only  limited
liability. 

In this respect, the user's attention is drawn to the risks associated
with loading,  using,  modifying and/or developing or reproducing the
software by the user in light of its specific status of free software,
that may mean  that it is complicated to manipulate,  and  that  also
therefore means  that it is reserved for developers  and  experienced
professionals having in-depth computer knowledge. Users are therefore
encouraged to load and test the software's suitability as regards their
requirements in conditions enabling the security of their systems and/or 
data to be ensured and,  more generally, to use and operate it in the 
same conditions as regards security. 

The fact that you are presently reading this means that you have had
knowledge of the CeCILL license and that you accept its terms.

 */
 
 
package slib.sglib.io.plotter;

import java.util.Collection;
import java.util.HashMap;

import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDFS;

import slib.indexer.Indexer;
import slib.sglib.model.graph.G;
import slib.sglib.model.graph.elements.E;
import slib.sglib.model.graph.elements.V;

/**
 * TODO 
 * modification shape utilser rectangle
 * changer la couleur des noeuds ajoutes en blanc
 * ajouter label au id de la GO
 * coloration des liens http://www.ensembl.org/Homo_sapiens/Transcript/Ontology/Image?db=core;g=ENSG00000139618;r=13:32889611-32973805;t=ENST00000380152
 * subclass of -> isa; part-of en bleu
 * @author seb
 *
 */
public class GraphPlotter_Graphviz {


	public static String plot(G graph, Collection<Value> VertexUriColored, boolean showLabels){
		return plot(graph,VertexUriColored,showLabels,null);
	}

	public static String plot(G graph,Collection<? extends Value> VertexUriColored, boolean showLabels,Indexer indexer){
		
		HashMap<URI, String> relColor = new HashMap<URI, String>();

		relColor.put(RDFS.SUBCLASSOF, "black");

//		relColor.put(new URI("part_of"), "red");
//		relColor.put(new URI("http://purl.org/obo/owl/OBO_REL#part_of"), "red");
//
//		relColor.put(new URI("part_of_opposite"), "orange");
//		relColor.put(new URI("http://purl.org/obo/owl/OBO_REL#part_of_opposite"), "orange");

		String defColor_v 		= "\"white\"";//"\"#6583DC\""; // added node  blue
		String defColor_q_v 	= "\"#FAAB9F\""; // query node  white
		String defColor_e 		= "black"; // query node  white

		String style = "\n\trankdir=BT;\n\tsize=\"6,6\";\n\tnode [style=filled,shape=rect]\n\n";

		String out = "digraph plottedgraph {\n";
		out += style;
		
		String color;

		for(V v : graph.getV()){
			
			color = defColor_v;
			
			if(VertexUriColored != null && VertexUriColored.contains(v)){
				color = defColor_q_v;
			}
			
			if(indexer == null)
				out += "\t\""+v.getValue().stringValue()+"\"[color="+color+"];\n";
			else{
				String splittedLabel = GraphPlotter_Graphviz.splitString(indexer.valuesOf(v.getValue()).toString(), 20);
				out += "\t\""+splittedLabel+"\"[fillcolor="+color+"];\n";
			}
		}

		for (E e : graph.getE()) {

			URI eType = e.getURI();
			color = defColor_e;

//			if(eType.isNative()){

				if(relColor.containsKey(eType))
					color = relColor.get(eType);

				String info = "";
				
				if(showLabels)
					info = "[label=\""+eType.getLocalName()+"\",color="+color+"]";
				
				if(indexer == null)
					out += "\t\""+e.getSource().getValue().stringValue()+"\" -> \""+e.getTarget().getValue().stringValue()+"\" "+info+";\n";
				else{
					String splittedLabel_src = GraphPlotter_Graphviz.splitString(indexer.valuesOf(e.getSource().getValue()).toString(), 20);
					String splittedLabel_target = GraphPlotter_Graphviz.splitString(indexer.valuesOf(e.getTarget().getValue()).toString(), 20);
					out += "\t\""+splittedLabel_src+"\" -> \""+splittedLabel_target+"\" "+info+";\n";
				}
			}
//		}
		out += "}\n";

		return out;
	}
	
	public static String splitString(String in,int max_num_per_string){
		
		String[] data = in.split(" "); 
		String newLabel = "";
		int curLineLength  = 0;
		
		for(String d : data){
			
			
			
			if(curLineLength + d.length()+1 <= max_num_per_string ){
				newLabel += d+" ";
				curLineLength += d.length()+1;
			}
			else if(curLineLength == 0 && d.length() > max_num_per_string){
				newLabel += d+"\\n";
				curLineLength = 0;
			}
			else if(curLineLength + d.length()+1 > max_num_per_string){
				newLabel += "\\n"+d+" ";
				curLineLength = d.length()+1;
			}

		}
		return newLabel;
		
	}
	
	public static void main(String[] args) {
		
		String in = "This is a test showing the behaviour of this method front of big strings [GO:009059]";
		GraphPlotter_Graphviz.splitString(in,25);
		
		
	}
}
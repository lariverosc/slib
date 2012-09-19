package slib.sglib.io.loader.rdf;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.ntriples.NTriplesParser;
import org.openrdf.rio.rdfxml.RDFXMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import slib.sglib.io.conf.GDataConf;
import slib.sglib.io.conf.GraphConf;
import slib.sglib.io.loader.GraphLoaderGeneric;
import slib.sglib.io.loader.IGraphLoader;
import slib.sglib.io.util.GFormat;
import slib.sglib.model.graph.G;
import slib.sglib.model.repo.impl.DataRepository;
import slib.utils.ex.SGL_Ex_Critic;
import slib.utils.ex.SGL_Exception;

public class RDFLoader implements IGraphLoader{

	RDFParser parser = null;
	

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public RDFLoader(){}

	public RDFLoader(RDFFormat format) throws SGL_Ex_Critic{

		loadFormat(format);

	}
	
	
	public G load(GraphConf conf) throws SGL_Exception {
		return GraphLoaderGeneric.load(conf);
	}
	
	public void populate(GDataConf conf, G g) throws SGL_Exception {
		
		loadConf(conf);
		
		
		logger.info("Populate graph "+g.getURI());
		load(g,conf.getLoc());
		
	}

	private void loadConf(GDataConf conf) throws SGL_Ex_Critic {
		
		GFormat format = conf.getFormat();
		if(format == GFormat.RDF_XML)
			loadFormat(RDFFormat.RDFXML);
		else if(format == GFormat.NTRIPLES)
			loadFormat(RDFFormat.NTRIPLES);
		else 
			throw new SGL_Ex_Critic("Unsupported RDF format "+format);
	}


	

	private void loadFormat(RDFFormat format) throws SGL_Ex_Critic{
		if(format.equals(RDFFormat.NTRIPLES)){
			parser = new NTriplesParser(DataRepository.getSingleton());
		}
		else if(format.equals(RDFFormat.RDFXML)){
			parser = new RDFXMLParser(DataRepository.getSingleton());
			parser.setStopAtFirstError(false);
		}
		else 
			throw new SGL_Ex_Critic("Unsupported RDF format "+format);
	}

	public void load(G g, String file) throws SGL_Ex_Critic{


		RDFHandler rdfHandler = new SglRdfHandler(g);
		try{
			parser.setRDFHandler(rdfHandler);

			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in 		= new DataInputStream(fstream);
			BufferedReader br 		= new BufferedReader(new InputStreamReader(in));

			parser.parse(br, "");
		}
		catch (Exception e) {
			throw new SGL_Ex_Critic(e.getMessage());
		}
	}
	
	public void load(G g, String file, RDFFormat format) throws SGL_Ex_Critic{

		loadFormat(format);
		load(g, file);
	}

	public void load(G g, Map<String,RDFFormat> rdfFileConf) throws SGL_Ex_Critic{

		for(Entry<String, RDFFormat> e : rdfFileConf.entrySet())
			load(g, e.getKey(),e.getValue());
	}



}
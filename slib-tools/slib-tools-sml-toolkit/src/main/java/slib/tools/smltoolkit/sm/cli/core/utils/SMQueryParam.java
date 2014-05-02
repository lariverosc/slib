/* 
 *  Copyright or © or Copr. Ecole des Mines d'Alès (2012-2014) 
 *  
 *  This software is a computer program whose purpose is to provide 
 *  several functionalities for the processing of semantic data 
 *  sources such as ontologies or text corpora.
 *  
 *  This software is governed by the CeCILL  license under French law and
 *  abiding by the rules of distribution of free software.  You can  use, 
 *  modify and/ or redistribute the software under the terms of the CeCILL
 *  license as circulated by CEA, CNRS and INRIA at the following URL
 *  "http://www.cecill.info". 
 * 
 *  As a counterpart to the access to the source code and  rights to copy,
 *  modify and redistribute granted by the license, users are provided only
 *  with a limited warranty  and the software's author,  the holder of the
 *  economic rights,  and the successive licensors  have only  limited
 *  liability. 

 *  In this respect, the user's attention is drawn to the risks associated
 *  with loading,  using,  modifying and/or developing or reproducing the
 *  software by the user in light of its specific status of free software,
 *  that may mean  that it is complicated to manipulate,  and  that  also
 *  therefore means  that it is reserved for developers  and  experienced
 *  professionals having in-depth computer knowledge. Users are therefore
 *  encouraged to load and test the software's suitability as regards their
 *  requirements in conditions enabling the security of their systems and/or 
 *  data to be ensured and,  more generally, to use and operate it in the 
 *  same conditions as regards security. 
 * 
 *  The fact that you are presently reading this means that you have had
 *  knowledge of the CeCILL license and that you accept its terms.
 */
package slib.tools.smltoolkit.sm.cli.core.utils;

/**
 * @author Sébastien Harispe
 */
public class SMQueryParam {

    final String id;
    ActionsParams noAnnotAction;
    ActionsParams notFoundAction;
    double noAnnotationScore;
    double notFoundScore;
    boolean outputBaseName;
    boolean useLoadedURIprefixes;
    boolean useLoadedURIprefixesOutput; // The URIs will be shortned in the output file
    String infile;
    String outfile;
    String type;

    @Override
    public String toString() {
        String out = "Query :" + id + "\n";
        out += "Not Found : " + notFoundAction + "\n";

        if (notFoundAction == ActionsParams.SET) {
            out += "score associated to entries for which an element is not found in the knowledge base: " + notFoundScore + "\n";
        }

        out += "No Annotations : " + noAnnotAction + "\n";

        if (noAnnotAction == ActionsParams.SET) {
            out += "score associated to entities with no annotations : " + noAnnotAction + "\n";
        }

        out += "infile = " + infile + "\n";
        out += "outfile = " + outfile + "\n";
        out += "type = " + type + "\n";
        out += "use URI prefixes = " + useLoadedURIprefixes + "\n";
        out += "use URI prefixes (output)= " + useLoadedURIprefixesOutput + "\n";

        return out;
    }

    public SMQueryParam(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ActionsParams getNoAnnotAction() {
        return noAnnotAction;
    }

    public SMQueryParam setNoAnnotAction(ActionsParams noAnnotAction) {
        this.noAnnotAction = noAnnotAction;
        return this;
    }

    public ActionsParams getNoFoundAction() {
        return notFoundAction;
    }

    public SMQueryParam setNoFoundAction(ActionsParams noFoundAction) {
        this.notFoundAction = noFoundAction;
        return this;
    }

    public double getNoAnnotationScore() {
        return noAnnotationScore;
    }

    public SMQueryParam setNoAnnotationScore(double noAnnotationScore) {
        this.noAnnotationScore = noAnnotationScore;
        return this;
    }

    public double getNoFoundScore() {
        return notFoundScore;
    }

    public SMQueryParam setNoFoundScore(double noFoundScore) {
        this.notFoundScore = noFoundScore;
        return this;
    }

    public boolean isOutputBaseName() {
        return outputBaseName;
    }

    public boolean isUseLoadedURIprefixes() {
        return useLoadedURIprefixes;
    }

    public SMQueryParam setUseLoadedURIprefixes(boolean useLoadedURIprefixes) {
        this.useLoadedURIprefixes = useLoadedURIprefixes;
        return this;
    }

    public boolean isUseLoadedURIprefixesOutput() {
        return useLoadedURIprefixesOutput;
    }

    public SMQueryParam setUseLoadedURIprefixesOutput(boolean useLoadedURIprefixes) {
        this.useLoadedURIprefixesOutput = useLoadedURIprefixes;
        return this;
    }

    public SMQueryParam setOutputBaseName(boolean outputBaseName) {
        this.outputBaseName = outputBaseName;
        return this;
    }

    public String getOutfile() {
        return outfile;
    }

    public SMQueryParam setOutfile(String outfile) {
        this.outfile = outfile;
        return this;
    }

    public String getType() {
        return type;
    }

    public SMQueryParam setType(String type) {
        this.type = type;
        return this;
    }

    public String getInfile() {
        return infile;
    }

    public SMQueryParam setInfile(String infile) {
        this.infile = infile;
        return this;
    }
}

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * SIR model.
 *
 * @author Jeffrey Chan, 2021.
 */
public class SIRModel {

    /**
     * Default constructor, modify as needed.
     */
    public SIRModel() {

    } // end of SIRModel()

    /**
     * Run the SIR epidemic model to completion, i.e., until no more changes to the
     * states of the vertices for a whole iteration.
     *
     * @param graph             Input contracts graph.
     * @param seedVertices      Set of seed, infected vertices.
     * @param recoveredVertices 
     * @param infectionProb     Probability of infection.
     * @param recoverProb       Probability that a vertex can become recovered.
     * @param sirModelOutWriter PrintWriter to output the necessary information per
     *                          iteration (see specs for details).
     */
    public void runSimulation(ContactsGraph graph, String[] seedVertices, float infectionProb, float recoverProb,
            PrintWriter sirModelOutWriter) {

    	String s1=seedVertices.length>0?Arrays.toString(seedVertices):"[]";
    	String s2="[]";
    	sirModelOutWriter.println(s1+":"+s2);

        runStep(graph, seedVertices, new String[0], infectionProb, recoverProb,sirModelOutWriter);
        
    } // end of runSimulation()
    
    public void runStep(ContactsGraph graph, String[] seedVertices,String[] recoveredVertices, float infectionProb, float recoverProb,
            PrintWriter sirModelOutWriter) {
    	
    	if(seedVertices.length==0) return;
    	
    	String[] newlyRecovered=new String[0];
    	//loop for recovery
        for(int i=0;i<seedVertices.length;i++)
    	{
        	double random = Math.random(); //random value between 0 and 1
            if(random<recoverProb)
            {
                //graph.toggleVertexState(seedVertices[i]);
            	String[] tmp=new String[recoveredVertices.length+1];
            	int z=0;
            	for(z=0;z<recoveredVertices.length;z++)
            	{
            		tmp[z]=recoveredVertices[z]; 
            	}
            	tmp[z]=seedVertices[i]; //add it it's infected
            	recoveredVertices=tmp;
            	
            	tmp=new String[newlyRecovered.length+1];
            	z=0;
            	for(z=0;z<newlyRecovered.length;z++)
            	{
            		tmp[z]=newlyRecovered[z]; 
            	}
            	tmp[z]=seedVertices[i]; //add the recovered one
            	newlyRecovered=tmp;
            	
            	tmp=new String[seedVertices.length-1];
            	z=0;
            	for(String s:seedVertices)
            	{
            		if(s!=seedVertices[i])
            		{
            			tmp[z]=s;
            			z++;
            		}
            	}
            	seedVertices=tmp;
            	
            	i--;
            }
    	}
        
        int l=seedVertices.length;
        String[] newlyInfected=new String[0];
        //loop for infection
        for(int i=0;i<l;i++)
    	{
	        String [] s=graph.kHopNeighbours(1, seedVertices[i]);
	        for(int j=0;j<s.length;j++)
	        {
	        	if(!Arrays.asList(seedVertices).contains(s[j]) && !Arrays.asList(recoveredVertices).contains(s[j]))
	        	{
	        		double random = Math.random(); //random value between 0 and 1
	                if(random<infectionProb)
	                {
	                	//graph.toggleVertexState(s[j]);
	                	String[] tmp=new String[seedVertices.length+1];
	                	int z=0;
	                	for(z=0;z<seedVertices.length;z++)
	                	{
	                		tmp[z]=seedVertices[z]; 
	                	}
	                	tmp[z]=s[j]; //add it it's infected
	                	seedVertices=tmp;
	                	
	                	tmp=new String[newlyInfected.length+1];
	                	z=0;
	                	for(z=0;z<newlyInfected.length;z++)
	                	{
	                		tmp[z]=newlyInfected[z]; 
	                	}
	                	tmp[z]=s[j]; //add it it's infected
	                	newlyInfected=tmp;
	                	
	                }
		        	
	        	}
	        }
    	}
        
        String s1=newlyInfected.length>0?Arrays.toString(newlyInfected):"[]";
    	String s2=newlyRecovered.length>0?Arrays.toString(newlyRecovered):"[]";
    	sirModelOutWriter.println(s1+":"+s2);
        runStep(graph, seedVertices,recoveredVertices, infectionProb, recoverProb,sirModelOutWriter);
    } 
    
    
    
    
} // end of class SIRModel

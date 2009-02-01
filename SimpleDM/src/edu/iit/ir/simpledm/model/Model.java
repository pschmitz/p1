/*
 *																		
 *							SimpleDM										
 *																		
 *					Illinois Institute of Technology	
 *					Information Retrieval Laboratory
 *		
 *					CS422 Introduction To Data Mining					
 *					Professor: Dr. Nazli Goharian						
 *																		
 *					Copyright (c) 2003									
 *					@author		Mi Sun Chung							
 *					@version	1.3										
 *																		
 *					Model.java											
 */

 

package edu.iit.ir.simpledm.model;

import edu.iit.ir.simpledm.preprocess.*;
import edu.iit.ir.simpledm.data.*;
//import edu.iit.ir.simpledm.gui.*;

import javax.swing.JTextArea;
import java.util.StringTokenizer;
import java.io.*;


/**
 * Abstract model class that will be the super class of all models. <br>
 *
 * A student must build the following four abstract functions,
 * which are 'buildModel', 'testModel', 'showResult', and 'writeIntro' to extend this model class.
 *
 * 'buildModel' and 'testModel' will build and test the model with a given SampleSet.
 * SampleSet is a set of instances of which the maximum size is SampleSet.maxRcdsMemory, 
 * assuming that the data file can be too big to fit in memory.
 * Of course, these functions must take care of the scalability.<br>
 *
 * 'writeIntro' will write the name of the model and parameter information. It will
 * be called once for a testing.
 * 'showResult' will write the resulting accuracy of each fold cross validation. It will
 * be called at every folds.
 */
public abstract class Model implements Cloneable
{

	/** Data Set that contains the information about the data. */ 
	protected DataSet m_data;
	
	/** Text area on which the results of testing the model will be displayed. */
//	protected JTextArea m_resultText;

	/* Preprocessor for a SampleSet*/
	protected Preprocessor m_pre;

	/** Logger to show the status of processing */
//	protected Logger m_logger;	

	/** ModelPanel in order to control termination with error or resetting the buttons */ 
//	protected ModelPanel m_panel;

	/** Output file path */
	private String m_outFilePath = "output/output.txt";


	/** Constructor */
	public Model(DataSet dataset){
		m_data = dataset;
	}

	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}


	/**
	 * Builds a model with a given SampleSet.
	 * A student must implement this abstract function to extend this model class. 
	 * A new algorithm will be implemented here. 
	 * Called by startBuildingModel(int,int).
	 * 
	 * @param: set A SampleSet of training data.
	 */
	public abstract void buildModel(SampleSet set);



	/**
	 * Tests a model with a given SampleSet. 
	 * A student must implement this abstract function to extend this model class.
	 * This function will test the implemented algorithm(model) with a given SampleSet.
	 * Called by startTestingModel(int,int).
	 *
	 * @param set A SampleSet of testing data.
	 */
	public abstract void testModel(SampleSet set);



	/**
	 * Displays the resulting accuracy of each test on the 'resultText' member.
	 * A student must build this abstract function to extend this model class.
	 * Called by Distributor.doCrossValidation(Model, DataSet)
	 *
	 * @param None
	 */
	public abstract void showResults();



	/**
	 * Writes some information about the model
	 * such as the name of the algorithm and its parameters.
	 * It will be called first at the beginning of modeling.
	 * A student must build this abstract function to extend this model class.
	 * Called by Distributor.doCrossValidation(Model,DataSet)
	 *
	 * @param None
	 */
	public abstract void writeIntro();




	/**
	 * Reads a chunk of training data into SampleSet,
	 * then preprocesses the SampleSet and builds a model with it.
	 * Repeats it until there are no more training data to read.
	 * Called by <code>Distributor.doCrossValidation(Model model, DataSet data)</code>.
	 *
	 * @param: startlnOfTestBlock - Starting line of test block to avoid reading test block.
	 * @param: endlnOfTestBlock - Ending line of test block to avoid reading test block. 
	 */
	public void startBuildingModel(int startlnOfTestBlock, int endlnOfTestBlock)
	{
		int startln = startlnOfTestBlock; //Starting line of test block to avoid reading test block
		int endln   = endlnOfTestBlock;   //Ending line of test block to avoid reading test block
		int currentLine= 0;			      // current line in the data file	  
		int totalRecords = (int)m_data.getTotalNumOfInstances();  // total number of records in the data file 
		SampleSet sampleSet = new SampleSet();  // sampleSet that contains a chunk of training data 
		int linesOfHeader = m_data.getLoader().getNumLinesOfHeader(); // the number of lines of header in a file
		String line = new String(); 

		try{
			BufferedReader in = new BufferedReader(new FileReader(m_data.getLoader().getFilepath()));
			for(int i=0; i<linesOfHeader; ++i)  // skip the header of the file
				in.readLine();          

			// read before the starting line of testing block  
			while(currentLine<startln && (line = in.readLine())!= null)
			{
				Sample sample = createSample(line);

				// if the sample set is full, 
				if(sampleSet.add(sample) == false)   
				{
					sampleSet = m_pre.preprocess(sampleSet,true);       // preprocess on the set
					buildModel(sampleSet);           // build a model on the set

					sampleSet = new SampleSet();     // create a new Sample Set for next block of data
					sampleSet.add(sample);           // this sample was not added
				}
				++currentLine;	
			}

			// if this is the end of training data, send the current sampleSet to build a model  
			if((endln == totalRecords) && (sampleSet.size() > 0))
			{
				sampleSet = m_pre.preprocess(sampleSet,true);       // preprocess on the set
				buildModel(sampleSet);           // build a model on the set
				return;
			}

			// skip this block! this area is for testing  
			for(int i=0; i<(endln - startln); ++i)
			{
				in.readLine();
				++currentLine;
			}
	
			// reads samples until the end of the file             
			while((line = in.readLine()) != null && currentLine<totalRecords)
			{
				Sample sample = createSample(line);
				if(sampleSet.add(sample) == false)  
				{
					sampleSet = m_pre.preprocess(sampleSet,true);       
					buildModel(sampleSet);           

					sampleSet = new SampleSet();     
					sampleSet.add(sample); 
				}
				++currentLine;
			}
			
			if(sampleSet.size() > 0)
			{
				sampleSet = m_pre.preprocess(sampleSet,true);       
				buildModel(sampleSet);           
			}		
		}			
		catch(IOException e)
		{
			System.out.print("Error: "+e);
			System.exit(1);
		}
	}



	/**
	 * Reads a chunk of testing data into SampleSet,
	 * then preprocesses the SampleSet and tests the model with it.
	 * Repeats it until there are no more testing data to read.
	 * Called by <code>Distributor.doCrossValidation(Model model, DataSet data)</code>.
	 *
	 * @param: startlnOfTestBlock - Starting line of test block.
	 * @param: endlnOfTestBlock - Ending line of test block. 
	 */
	public void startTestingModel(int startlnOfTestBlock, int endlnOfTestBlock)
	{
		int startln = startlnOfTestBlock;  // start line of testing block 
		int endln   = endlnOfTestBlock;    // end line of testing block 
		String line;
		SampleSet sampleSet = new SampleSet();
		
		try{
				BufferedReader in = new BufferedReader(new FileReader(m_data.getLoader().getFilepath()));
				for(int i=0; i<m_data.getLoader().getNumLinesOfHeader(); ++i)
					in.readLine();          // read the header of the file

				int currentLine=0;
				// skip the training data blocks
				for(int i=0; i<startln; ++i)
				{
					in.readLine();
					++currentLine;
				}

				// read the testing data block
				while( (line=in.readLine()) != null && currentLine < endln)
				{
					Sample sample = createSample(line);
					if(sampleSet.add(sample) == false)  
					{
						sampleSet = m_pre.preprocess(sampleSet,false);       
						testModel(sampleSet);           

						sampleSet = new SampleSet();     
						sampleSet.add(sample);           
					}			
					++currentLine;
				}

				if(sampleSet.size() > 0)
				{
					sampleSet = m_pre.preprocess(sampleSet,false);       
					testModel(sampleSet);           
				}		
			}
			catch(IOException DTSe)
			{
				System.out.print("Error: Testing data");
				System.exit(1);
			}	
	}





	/**
	 * The given String parameter is tokened as attributes,
	 * and saved into a new Sample object.
	 * Returns the Sample object.
	 *
	 * @param s - One instance as a String
	 */
	protected Sample createSample(String s)
	{
		int numAttributes = m_data.getNumAttributes();  // number of attributes 
		AttributeValue [] attributes = new AttributeValue[numAttributes];
		StringTokenizer t = new StringTokenizer(s,",");
		String token;

		for(int noAttr=0;noAttr<numAttributes;++noAttr)  // for each attribute
		{
			token = t.nextToken().trim();
			AttributeValue attribute;

			// if this attribute is categorical, create CategoricalAttributeValue object.
			if(m_data.getAttribute(noAttr).getType() == AttributeValue.TYPE_CATEGORICAL)
				attribute = new CategoricalAttributeValue(token);

			// if this attribute is continuous, create ContinuousAttributeValue object.
			else if(m_data.getAttribute(noAttr).getType() == AttributeValue.TYPE_CONTINUOUS)
			{
				if(token.equals(AttributeValue.MISSING_VALUE)){
					attribute = new ContinuousAttributeValue(Double.NaN);
				}
				else{
					double value = Double.parseDouble(token);
					attribute = new ContinuousAttributeValue(value);
				}
			}

			// if ordinal, create CategoricalAttributeValue object and tell it is ordinal.
			else 
			{
				attribute = new CategoricalAttributeValue(token);
				((CategoricalAttributeValue)attribute).setOrdinal(true);
			}
			attributes[noAttr] = attribute;
		} // end of for loop
		return new Sample(attributes);
	}


	/**
	 * Sets a preprocessor.
	 */
	public void setPreprocessor(Preprocessor p){
		m_pre = p;
	}



	/** Sets Logger to show the status of processing. */
//	public void setLogger(Logger l){m_logger = l;}



	/**
	 * Sets a result text on which the results of test will be displayed.
	 */
/*	public void setResultText(JTextArea text){
		m_resultText = text;
	}
*/


	/**
	 * Sets the ModelPanel to control termination with error
	 * or resetting the buttons.
	 */
/*	public void setModelPanel(ModelPanel panel){
		m_panel = panel;
	}
*/

	/**
	* Save the string into a output file("out.txt"). 
	* Added at the version 1.3.
	*/
	public void writeFile(String s)
	{
		try
		{
			PrintWriter out = new PrintWriter(new FileWriter(m_outFilePath,true));
			out.print(s);
			out.close();
		}
		catch (IOException e)
		{	
			System.out.print("Error: "+e);
		}
	}




}

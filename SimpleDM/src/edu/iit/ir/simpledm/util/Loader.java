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
 *					Preprocess.java										
 */




package edu.iit.ir.simpledm.util;

import edu.iit.ir.simpledm.preprocess.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.io.StreamTokenizer;



/**
 * Saves the file paths, 
 * and sets DataSet by reading the header of a ARFF file.
 */
public class Loader 
{
	/** file path */ 
	protected String filepath;
	
	/** data set */
	private DataSet data;

	/** number of lines of the header  */
	private int linesOfHeader =0;
	
	
	/**
	 * Constructor that passes file path.
	 */
	public Loader(String f){
		filepath =f;
	}





	/**
	 * Sets DataSet and call readHeader() function to reads an ARFF file 
	 * from a reader, and assigns information of attribute names and types. 
	 */
	public void setDataSet(DataSet d) 
	{
		data = d;

		try {
			Reader reader = new BufferedReader(new FileReader(filepath));
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			initTokenizer(tokenizer);
			readHeader(tokenizer);
			data.setTotalNumOfInstances(getTotalNumInstances());
		} catch (Exception ex) {
			System.out.println("Problem reading " + filepath
					+ " as an arff file.");
		}
	}
	




  /**
   * Initializes the StreamTokenizer used for reading the ARFF file.
   * Copied from WEKA.
   *
   * @param tokenizer the stream tokenizer
   */
  private void initTokenizer(StreamTokenizer tokenizer){

    tokenizer.resetSyntax();         
    tokenizer.whitespaceChars(0, ' ');    
    tokenizer.wordChars(' '+1,'\u00FF');
    tokenizer.whitespaceChars(',',',');
    tokenizer.commentChar('%');
    tokenizer.quoteChar('"');
   // tokenizer.quoteChar('\'');
    tokenizer.ordinaryChar('{');
    tokenizer.ordinaryChar('}');
    tokenizer.eolIsSignificant(true);
  }
 



 /**
   * Reads and stores the header of an ARFF file.
   * Copied from WEKA3-2-3.
   *
   * @param tokenizer the stream tokenizer
   * @exception IOException if the information is not read 
   * successfully
   */ 
  protected void readHeader(StreamTokenizer tokenizer) 
     throws IOException{
	 
    
   linesOfHeader = 0;
	// Create vectors to hold information temporarily.
	Vector attributeNames = new Vector();
	Vector attributeTypes = new Vector();
	Vector attributeLabels = new Vector();

    // Get name of relation.
    getFirstToken(tokenizer);
    if (tokenizer.ttype == StreamTokenizer.TT_EOF) {
		errms(tokenizer,"premature end of file");
    }
    if (tokenizer.sval.equalsIgnoreCase("@relation"))
	{
		getNextToken(tokenizer);
		data.setRelationName(tokenizer.sval);
		getLastToken(tokenizer,false);
    } 
	else {
		errms(tokenizer,"keyword @relation expected");
    }

    // Get attribute declarations.
    getFirstToken(tokenizer);
    if (tokenizer.ttype == StreamTokenizer.TT_EOF) {
		errms(tokenizer,"premature end of file");
    }

    while (tokenizer.sval.equalsIgnoreCase("@attribute")) 
	{
	
		// Get attribute name.
		getNextToken(tokenizer);
		attributeNames.addElement(tokenizer.sval);
		getNextToken(tokenizer);

		// Check if attribute is nominal.
		if (tokenizer.ttype == StreamTokenizer.TT_WORD) 
		{
			// Attribute is real, integer, or string.
			if (tokenizer.sval.equalsIgnoreCase("real") ||
				tokenizer.sval.equalsIgnoreCase("integer") ||
				tokenizer.sval.equalsIgnoreCase("numeric")) 
			{
				attributeTypes.addElement(new Integer(DataSet.TYPE_CONTINUOUS));
				readTillEOL(tokenizer);
			} 
			else if (tokenizer.sval.equalsIgnoreCase("string")) 
			{
				attributeTypes.addElement(new Integer(DataSet.TYPE_CATEGORICAL));
				readTillEOL(tokenizer);
			} 
			else {
				errms(tokenizer,"no valid attribute type or invalid "+"enumeration");
			}
		} 
		else {
			// Attribute is nominal.
			Vector labels = new Vector();
			tokenizer.pushBack();
	
			// Get values for nominal attribute.
			if (tokenizer.nextToken() != '{') {
				errms(tokenizer,"{ expected at beginning of enumeration");
			}
			while (tokenizer.nextToken() != '}') 
			{
				if (tokenizer.ttype == StreamTokenizer.TT_EOL) {
					errms(tokenizer,"} expected at end of enumeration");
				} 
				else {
					labels.addElement(tokenizer.sval);
				}
			}
			if (labels.size() == 0) {
				errms(tokenizer,"no nominal values found");
			}
			attributeTypes.addElement(new Integer(DataSet.TYPE_CATEGORICAL));
			attributeLabels.addElement(labels);
		}
		getLastToken(tokenizer,false);
		getFirstToken(tokenizer);
		if (tokenizer.ttype == StreamTokenizer.TT_EOF)
			errms(tokenizer,"premature end of file");
	}


	// Check if data part follows. We can't easily check for EOL.
	if (!tokenizer.sval.equalsIgnoreCase("@data")) {
		errms(tokenizer,"keyword @data expected");
	}
    
	// Check if any attributes have been declared.
	if (attributeNames.size() == 0) {
		errms(tokenizer,"no attributes declared");
	}
   

	// save attribute names in DataSet.
	int numAttributes = attributeNames.size();
	String [] names = new String[numAttributes];
	int [] types = new int[numAttributes];

	Enumeration names_e = attributeNames.elements();
	for(int i=0; i<numAttributes; i++){
		names [i] = (String)names_e.nextElement();
	}
	data.setAttributeNames(names);
	   	
	// save attribute types in DataSet.
	Enumeration types_e = attributeTypes.elements();
	Enumeration labels_e = attributeLabels.elements();
	for(int i=0; i< numAttributes; i++)
	{
	   types [i] = ((Integer)types_e.nextElement()).intValue();
	   if(types[i] == DataSet.TYPE_CATEGORICAL){ 
		   data.getAttribute(i).getItemInfo().setLabels((Vector)labels_e.nextElement());
		}
	}
	data.setTypesOfAttributes(types);
	System.out.println("The number of lines of the header : "+linesOfHeader+"\n");
  }





  /**
   * Throws error message with line number and last token read.
   * Copied from WEKA3-2-3.
   *
   * @param theMsg the error message to be thrown
   * @param tokenizer the stream tokenizer
   * @throws IOExcpetion containing the error message
   */
  private void errms(StreamTokenizer tokenizer, String theMsg) 
       throws IOException {
    
    throw new IOException(theMsg + ", read " + tokenizer.toString());
  }
 




  /**
   * Gets next token, skipping empty lines.
   * Copied from WEKA3-2-3.
   *
   * @param tokenizer the stream tokenizer
   * @exception IOException if reading the next token fails
   */
  private void getFirstToken(StreamTokenizer tokenizer) 
    throws IOException{
    
    while (tokenizer.nextToken() == StreamTokenizer.TT_EOL){
		++ linesOfHeader;
	}
    if (//(tokenizer.ttype == '\') ||
	(tokenizer.ttype == '"')) {
      tokenizer.ttype = StreamTokenizer.TT_WORD;
    } else if ((tokenizer.ttype == StreamTokenizer.TT_WORD) &&
	       (tokenizer.sval.equals("?"))){
      tokenizer.ttype = '?';
    }
	++ linesOfHeader;
  }


  /**
   * Gets token and checks if its end of line.
   * Copied from WEKA3-2-3.
   *
   * @param tokenizer the stream tokenizer
   * @exception IOException if it doesn't find an end of line
   */
  private void getLastToken(StreamTokenizer tokenizer, boolean endOfFileOk) 
       throws IOException{

    if ((tokenizer.nextToken() != StreamTokenizer.TT_EOL) &&
	((tokenizer.nextToken() != StreamTokenizer.TT_EOF) || !endOfFileOk)) {
      errms(tokenizer,"end of line expected");
    }
  }

  /**
   * Gets next token, checking for a premature and of line.
   * Copied from WEKA3-2-3.
   *
   * @param tokenizer the stream tokenizer
   * @exception IOException if it finds a premature end of line
   */
  private void getNextToken(StreamTokenizer tokenizer) 
       throws IOException{
    
    if (tokenizer.nextToken() == StreamTokenizer.TT_EOL) {
      errms(tokenizer,"premature end of line");
    }
    if (tokenizer.ttype == StreamTokenizer.TT_EOF) {
      errms(tokenizer,"premature end of file");
    } else if (//(tokenizer.ttype == '\') ||
	       (tokenizer.ttype == '"')) {
      tokenizer.ttype = StreamTokenizer.TT_WORD;
    } else if ((tokenizer.ttype == StreamTokenizer.TT_WORD) &&
	       (tokenizer.sval.equals("?"))){
      tokenizer.ttype = '?';
    }
  }

  /**
   * Reads and skips all tokens before next end of line token.
   *
   * @param tokenizer the stream tokenizer
   */
  private void readTillEOL(StreamTokenizer tokenizer) 
       throws IOException{
    
    while (tokenizer.nextToken() != StreamTokenizer.TT_EOL) {};
    tokenizer.pushBack();
  }







	/**
	  * Return total number of instances in the chunk
	  * Read into the totalLine of chunk data from startline
	  */
	public int getChunk(String [][] chunk, int startLine, int totalLine)
	{
		String s;
		int numInChunk=0;
		try{
			BufferedReader in = new BufferedReader(new FileReader(filepath));

			for(int i=0; i<(linesOfHeader+startLine);++i)
				in.readLine();
			int i=0;
			while((s=in.readLine()) != null && i<totalLine)
			{
	
				StringTokenizer t = new StringTokenizer(s,",");
				int noAttribute = 0;
				while(t.hasMoreElements())
				{
					chunk[i][noAttribute] = t.nextToken().trim();
					++noAttribute;
				}
				++i;
			}
			numInChunk = i;
		}
		catch(IOException e)
		{
			System.out.print("Loader.java::getChunk Error: "+e);
			System.exit(1);
		}
		return numInChunk;
	}


	/**
	 * Returns the total number of instances. 
	 */
	private double getTotalNumInstances()
	{
		double count=0;
		try{
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			String s;
			for(int i=0; i<linesOfHeader; ++i)
				in.readLine();
		
			while((s=in.readLine()) != null)
			{
				++count;
			}
		}
		catch(IOException e)
		{
			System.out.print("Loader.java::getTotalNumInstances Error: "+e);
			System.exit(1);
		}
		return count;
	}

	/**
     * Returns the number of lines of header.
	 */
	public int getNumLinesOfHeader(){
		return linesOfHeader;
	}


	/** Returns file path. */
	public String getFilepath(){
		return filepath;
	}
	
}

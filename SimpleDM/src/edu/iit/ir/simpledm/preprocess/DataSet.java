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
 *					DataSet.java										
 */


package edu.iit.ir.simpledm.preprocess;

import edu.iit.ir.simpledm.util.*;
import edu.iit.ir.simpledm.preprocess.*;
import edu.iit.ir.simpledm.exception.*;

import java.util.*;

/**
 * Contains general data information such as relation name, 
 * the number of attributes and number of instances.
 * Also has the array of Attribute.				 
 *				
 *				
 */
public class DataSet
{
	/** Missing value. */
	public static final String MISSING_VALUE = new String("?");

	/** Represents an unknown value. */
    public static final int TYPE_MISSING     = -1;

    /** Represents a categorical value */
    public static final int TYPE_CATEGORICAL = 0;

    /** Represents a continuous value */
    public static final int TYPE_CONTINUOUS  = 1;

	/** Represents a ordinal value */
	public static final int TYPE_ORDINAL = 2;



	/** Relation name */
	protected String relationName;

	/** Array of Attributes */
	protected Attribute [] attributes;

	/** Total number of attributes */
	protected int numAttributes;   

	/** Total number of instances */
	protected double numInstances; 

	/** Loader of a data file. Used to estimate the statistics. */
	private Loader loader;


	/** 
	 * Default constructor 
	 */
	public DataSet()
	{
		numAttributes = -1;
		numInstances = -1;
	}


	/**  Returns the total number of instances.   */
	public double getTotalNumOfInstances(){
		return numInstances;
	}

	/**  Returns the total number of attributes.  */
	public  int getNumAttributes() {
		return numAttributes; 
	}
	

	/** Returns Loader of this data set. */
	public Loader getLoader(){
		return loader;
	}


	/**  Returns the relation name.       */
	public String getRelationName(){
		return relationName;
	}


	/** 
	 * Returns an Attribute of the given attribute name.  
	 *
	 * @param name - String. Name of an attribute.
	 */
	public Attribute getAttribute(String name)
	{
		int i = index(name);
		if(i>=0)
			return attributes[i];
		else
			throw new UnassignedClassException("The attribute ("+name+") does not exists.");
	}


	/** 
	 * Returns an Attribute of the given attribute index. 
	 *
	 * @param index - int. The index of an attribute. Index starts from zero.
	 */
	public Attribute getAttribute(int index)
	{
		if(index <0 || index >= attributes.length)
			throw new IllegalArgumentException("Index out of range");

		return attributes[index];
	}

	
	/** 
	 * Returns index of the given attribute name. Index starts from zero.
	 *
	 * @param name - String. The name of an attribute.
	 */
	public int index(String name)
	{
		for(int i=0; i<numAttributes;++i)
		{
			if((attributes[i].getName()).equals(name))
				return i;
		}
		return -1;
	}

	
	/** Sets relation name of the data set. */
	public void setRelationName(String name){
		relationName = name;
	}

	/** Sets Loader of the data set */
	public void setLoader(Loader l){
		loader = l;
	}



	/**
	 * Set the names of all attributes and the number of attributes(size).
	 *
	 * @param names - String []. Array of attribute names.
	 */
	public void setAttributeNames(String [] names)
	{
		numAttributes = names.length;
		attributes = new Attribute[numAttributes];
		for(int i=0; i<numAttributes; ++i){
			attributes[i] = new Attribute(names[i]);
		}
	}



	/** Sets the number of total instances */
	public void setTotalNumOfInstances(double d) {
		numInstances = d;
	}


	
	/**
	 * Sets the types of each attribute. Each Attribute object will have the type.
	 *
	 * @param types - int[]. Array of attribute types.
	 */
	public void setTypesOfAttributes(int [] types)
	{
		for( int i=0; i<types.length;++i)
			attributes[i].setType(types[i]);
	}


	



	/**
	 * Estimates the statistics for the data set such as AttributeStats and ItemInfo.
	 */
	public void setStats()
	{
		// calculate statistics for continuous variables
		// calculate distribution of labels for categorical variables
		makeStats(); 
		// calculate statistics for categorical variables
		// this information will be used for transformation of categorical to continuous type
		makeStatsForCategoricalVariables();
		// make discretizer for continuous varibles.
		// this will be used for transformation of continuous to categorical type
		makeDiscretizer();

		// display the informations of each attributes
		System.out.println(this);
		
	}


	/**
	 * Estimates Statistics such as min,max,avg, and standard deviation for categorical variables,
	 * and saves into AttributeStats object in Attribute.
	 * This information will be used for transformation of categorical to continuous type.
	 */
	private void makeStatsForCategoricalVariables()
	{
		for(int i_attr=0; i_attr < numAttributes; i_attr++)  
		{
			// for each categorical variables..
			if(attributes[i_attr].getType() == TYPE_CATEGORICAL )
			{
				double min=0;
				double max=0;
				double avg=0;
				double stddev=0;
				double sum=0;

				ItemInfo itemInfo = attributes[i_attr].getItemInfo();
				max = itemInfo.size()-1;

				// calculate average 
				for(int i_label=0; i_label< itemInfo.size(); i_label++){
					sum += itemInfo.count(i_label);
				}
				avg = sum/numInstances;
	
				// calculate standard deviation
				sum=0;
				for(int i_label=0; i_label< itemInfo.size(); i_label++)
				{
					double tmp = Math.pow( (i_label - avg) , 2 );
					sum += (tmp * itemInfo.count(i_label));
				}
				stddev = Math.sqrt(sum / (numInstances -1));

				// save the statistics 
				attributes[i_attr].getAttributeStats().setStats(min,max,avg,stddev);
			}
		}
	}




	/** 
	 * Makes discretizer for each continuous attributes.
	 * This will be used for transformation of continuous to categorical type.
	 */
	private void makeDiscretizer()
	{
		// calls set() function of discretizer to make seperators and labels.
		System.out.println("  <   Discretizer information   >");
		for(int i_attr =0; i_attr < numAttributes; i_attr++)
		{
			if(attributes[i_attr].getType() == TYPE_CONTINUOUS){
				attributes[i_attr].getDataDiscretizer().set();
				System.out.println("< "+attributes[i_attr].getName()+" >");
				System.out.println(attributes[i_attr].getDataDiscretizer().toString());

				// reset the iteminfo
				ItemInfo itemInfo = attributes[i_attr].getItemInfo();
				itemInfo.setLabels(new Vector());
			}
		}

		// reads the file and counts the labels. 
		int currentLine=0;
		while( currentLine < numInstances)
		{
			String [][] chunkData = new String [2000][attributes.length];
			int totalInChunk  = loader.getChunk(chunkData,currentLine,2000);
			
			for(int i_attr =0; i_attr < attributes.length; i_attr++ )
			{
				// for each continuous types...
				if(attributes[i_attr].getType() == DataSet.TYPE_CONTINUOUS)  
				{
					DataDiscretizer discretizer = attributes[i_attr].getDataDiscretizer();
					ItemInfo itemInfo = attributes[i_attr].getItemInfo();
					int noInstance=0;

					while( noInstance < totalInChunk )
					{
						// convert the number as a label and increase the number of the label by 1.
						String value = chunkData[noInstance][i_attr];
						if(value.equals(MISSING_VALUE))
							itemInfo.insertItem(value);
						else 
							itemInfo.insertItem(discretizer.getNumLabel(Double.parseDouble(value)));
			
						++noInstance;
					}
				} // end of if
			} // end of for
			currentLine += totalInChunk;
		}
	}




	/**
	 * Scans the entire data file.
	 * Estimates the statistics such as min,max,avg,standard deviation for each continuous variables,
	 * and save them into AttributeStats object in Attribute.
	 * Estimates the distribution of labels for each categorical variables,
	 * and save them into ItemInfo object in Attribute.
	 */
	private void makeStats()
	{
		double total =  numInstances;
		String s;
		double[] sum = new double[attributes.length];
		double[] avg = new double[attributes.length];
		double[] stddev= new double[attributes.length];
		double[] min = new double[attributes.length];
		double[] max = new double[attributes.length];


		AttributeStats [] attributeStats = new AttributeStats[attributes.length];
		for(int i=0; i<attributes.length;++i)
		{
			attributeStats[i] = attributes[i].getAttributeStats();
			// reset the labels
			if(attributes[i].getType() == TYPE_CATEGORICAL)	{
				attributes[i].getItemInfo().setLabels(new Vector());
			}
		}
		
		int currentLine=0;
		while( currentLine < total)
		{
			int noInstance=0;
			String [][] chunkData = new String [2000][attributes.length];
			int totalInChunk  = loader.getChunk(chunkData,currentLine,2000);
			
			while(noInstance<totalInChunk && noInstance<2000)
			{
				for(int i=0; i<attributes.length;++i)
				{
					// for each categorical or ordinal variable, count the item(label)
					if(attributes[i].getType() == DataSet.TYPE_CATEGORICAL
							|| attributes[i].getType() == DataSet.TYPE_ORDINAL){
						attributes[i].getItemInfo().insertItem(chunkData[noInstance][i]);
					}
					else{  // continuous type
						String value_s = chunkData[noInstance][i];
						double value;
						if(value_s.equals("?")){
							value=-1;
						}
						else {
							value = Double.parseDouble(value_s);
							sum[i] += value;
							if(currentLine == 0){  // estimate minimum and maximum value
								min[i] = value;
								max[i] = value;
							}
							else{
								if(min[i]>value)
									min[i] = value;
								if(max[i]<value)
									max[i] = value;
							}
						}
					}
				}
				++noInstance;
				++currentLine;
			}
		}

		// calculate the averages of each continuous types
		for(int i=0; i<attributes.length;++i){
			if(attributes[i].getType()==DataSet.TYPE_CONTINUOUS)
				avg[i] = sum[i] / total;
		}

		for(int i =0; i<attributes.length;++i){
			if(attributes[i].getType()==DataSet.TYPE_CONTINUOUS)
				sum[i] = 0;
		}

		// this is for calculating standard deviations....
		currentLine = 1;
		while( currentLine < total)
		{
			int noInstance=0;
			String [][] chunkData = new String [2000][attributes.length];
			int totalInChunk  = loader.getChunk(chunkData,currentLine,2000);
			currentLine+=totalInChunk;

			while(noInstance<totalInChunk && noInstance<2000)
			{
				for(int i=0; i<attributes.length;++i){
					if (attributes[i].getType() ==DataSet.TYPE_CONTINUOUS && !chunkData[noInstance][i].equals("?"))	
						sum[i] += Math.pow(Double.parseDouble(chunkData[noInstance][i])-avg[i], 2);
				}
				++noInstance;
			}
		}
		for(int i=0; i<attributes.length;++i){
			if(attributes[i].getType()==DataSet.TYPE_CONTINUOUS)
				stddev[i] = Math.sqrt( sum[i]/(total-1) );
		}

		// set ave,min,max,stddev into the attribute statistics
		for( int i=0; i<attributes.length;++i){
			if(attributes[i].getType() == DataSet.TYPE_CONTINUOUS)
				attributeStats[i].setStats(min[i],max[i],avg[i],stddev[i]);
		}
	}


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n    <   Data Set Information    >\n");
		sb.append("\nRelation Name : "+relationName);
		sb.append("\nThe number of instances : "+numInstances);
		sb.append("\nThe number of attributes : "+numAttributes);
		for(int i=0; i<numAttributes; ++i)
		{
			Attribute attr = getAttribute(i);
			sb.append("\n\nNo. "+i);
			sb.append("\nAttribute Name : " + attr.getName());
			if(attr.getType() == TYPE_CATEGORICAL)
			{
				sb.append("\nType : Categorical ");
				sb.append(attr.getItemInfo().toString());
			}
			else if(attr.getType() == TYPE_CONTINUOUS)
			{
				sb.append("\nType : Continuous");
				sb.append(attr.getAttributeStats().toString());
			}
			else if(attr.getType() == TYPE_ORDINAL)
			{
				sb.append("\nType : Ordinal ");
				sb.append(attr.getItemInfo().toString());
			}
		}
		sb.append("\n\n");
		return sb.toString();
	}






}
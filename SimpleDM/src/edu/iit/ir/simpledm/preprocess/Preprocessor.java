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
 *					Preprocessor.java									
 */


package edu.iit.ir.simpledm.preprocess;

import edu.iit.ir.simpledm.util.*;
import edu.iit.ir.simpledm.data.*;
//import edu.iit.ir.simpledm.gui.Logger;

import javax.swing.JOptionPane;



/**						
 * Contains preprocessing services and  boolean values of each service
 * indicating if the preprocessing is selected by a user or not. 
 * PreprocessPanel.java calls this class to set the selected options.
 * <br> There are several preprocessing services.
 *<br><ul>
 * <li>Removes Over-fitting in a SampleSet.
 * <li>Replaces the missing values. (Empty for version 1.1)
 * <li>Transforms from categorical attributes to continuous 
 *     or continuous to categorical(discretization).
 * <li>Normalizes the continuous values.(Z-score function is empty for version 1.1)
 * </ul><br>
 * 
 * <br>Each preprocessing service is performed by one function.
 * <br>
 * <br>However, <code>void preprocess(SampleSet, boolean)</code> performs 
 * every selected preprocesssing. This function is called by Model.java
 * before building a model with the preprocessed SampleSet.
 * 
 */
public class Preprocessor 
{

	/** Represents MIN_MAX normalization */
	 public static final int MIN_MAX  = 1;

	/** Represents Z-Score normalization */
	 public static final int Z_SCORE  = 2;

	/** Represents transformation from categorical type to continuous */
	 public static final int TO_CATEGORICAL  = 5;

	/** Represents transformation from continuous type to categorical */
	 public static final int TO_CONTINUOUS  = 6;




	/** Represents if the option of 'Remove Over-Fitting' is selected or not.          */
	protected boolean OverFitSelected = false;

	/** Represents if the option of 'Replacement of Missing Values' is selected or not */
	protected boolean MissingValueSelected = false;

	/** Represents if the option of 'Transformation' is selected or not.               */
	protected boolean TransformationSelected = false;

	/** Represents the transformation type. Default is TO_CATEGORICAL.                 */
	protected int transformType = TO_CATEGORICAL;

	/** Represents if the option of 'Normalization' is selected or not				   */
	protected boolean NormalizationSelected = false;

	/** Represents the method of normalization. Default is MIN_MAX.					   */
	protected int normalizer = MIN_MAX;




	/** Data Set for this preprocessor */
	private DataSet data;

	/** Logger */
//	private Logger logger;




	/**
	 * Default constructor.
	 */
	 public Preprocessor(){}


	/**
	 * Constructor that sets the DataSet.
	 */
	public Preprocessor(DataSet d){
		data = d;
	}

	


	/**
	 * Performs the selected preprocessing among '<code>removing over-fitting</code>',
	 * '<code>replacing missing values</code>', '<code>transformation</code>' and '<code>normalization</code>'
	 * on the given <code>SampleSet</code>. The order of calling selected function is 
	 * '<code>removeOverFit(SampleSet)</code>', '<code>replaceMissingValues(SampleSet)</code>',
	 * '<code>transformToCategorical(SampleSet)</code>' or '<code>transformToContinuous(SampleSet)</code>'
	 * '<code>normalizeMinmax(SampleSet)</code>'.
	 *
	 * @param samples - SampleSet.
	 * @param trainData - boolean which is true if the SampleSet is training data.
	 */
	 public SampleSet preprocess(SampleSet samples, boolean trainData)
	{
		 /* remove over-fitting of the given sample set if the set is training data set */
		 if((OverFitSelected == true) && (trainData == true )){
			 samples = removeOverFit(samples);
			 System.out.println("Removing Over-fit: size of samples "+samples.size());
		}
		
		/* replace missing values */
		if(MissingValueSelected == true){
			replaceMissingValues(samples);
		}

		/* transformation */
		if(TransformationSelected == true)
		{
			if(transformType == TO_CATEGORICAL){
				transformToCategorical(samples);
			}
			else if(transformType == TO_CONTINUOUS){
				transformToContinuous(samples);
			}
		}
		
		/* normalization */
		if(NormalizationSelected == true)
		{
			if(normalizer == MIN_MAX ){
				normalizeMinmax(samples);
			}
			else if(normalizer == Z_SCORE ){
				normalizeZscore(samples);
			}
		}
		return samples;
	}
			


	/**
     * Replace the missing values. It is empty in Versoin 1.1.
	 */
	public void replaceMissingValues(SampleSet set)
	{
		System.out.println(" Error: Replacement of missing values is not implemented yet.");
	}




	/**
	 * Normalize the given SampleSet using Z-Score algorithm.
	 * It is empty in Version 1.1.
	 */
	 public void normalizeZscore(SampleSet set){
		 System.out.println(" Error: Z-Score normalization is not implemented yet.");
	}



	/** 
	 * Normalizes the SampleSet with Min_Max method. 
	 */
	public void normalizeMinmax(SampleSet set)
	{
		Sample[] samples = set.getSamples();
		ContinuousAttributeValue attributeValue;
		double max,min,gap;
		
		/* for each continuous attribute, do min_max normalization */
		for(int i_attr=0; i_attr < samples[0].size(); i_attr++)
		{
			if(samples[0].get(i_attr).getType() ==  AttributeValue.TYPE_CONTINUOUS)
			{
				max = data.getAttribute(i_attr).getAttributeStats().getMax();
				min = data.getAttribute(i_attr).getAttributeStats().getMin();
				gap = max-min;

				for(int i_sample=0; i_sample < samples.length; i_sample++)
				{
					attributeValue = (ContinuousAttributeValue)samples[i_sample].get(i_attr);
					attributeValue.setDoubleValue((attributeValue.doubleValue()-min) / gap);
				}
			}
		}
		System.out.println("Min_Max normalization on the given SampleSet is completed.");
	}






	/**
	 * Transforms categorical variables to continuous.<br>
	 * Each label will be numbered using the index of a list of labels,
	 * which are stored in ItemInfo object of the attribute.
	 * 
	 * 
	 */
	 public void transformToContinuous(SampleSet set)
	{
		 Sample[] samples = set.getSamples();

		 for(int i_attr=0; i_attr < samples[0].size() ; i_attr++)
		 {	 /* for each categorical variables */
			 if(samples[0].get(i_attr).getType() == AttributeValue.TYPE_CATEGORICAL)
			{
				 ItemInfo itemInfo = data.getAttribute(i_attr).getItemInfo();
				 /* for each values of the categorical attribute */
				 for(int i_sample=0; i_sample < samples.length; i_sample++)
				{
					 String value = ((CategoricalAttributeValue)samples[i_sample].get(i_attr)).getValue();
					 // use the index within the array of items to transform to numeric value
					 double index = itemInfo.indexOfItem(value);  
					 samples[i_sample].set(i_attr, new ContinuousAttributeValue(index));
					 //samples[i_sample].get(i_attr).setType(AttributeValue.TYPE_CONTINUOUS);
				}
			}
		}
		System.out.println("Transformation from categorical to continuous on the given SampleSet is completed.");
	}


	/**
	 * Transforms continuous variables to categorica using DataDiscretizer.
	 */
	public void transformToCategorical(SampleSet set)
	{
		Sample[] samples = set.getSamples();

		for(int i_attr=0; i_attr < samples[0].size() ; i_attr++ )
		{	 /* for each continuous variables */
			 if( samples[0].get(i_attr).getType() == AttributeValue.TYPE_CONTINUOUS )
			{
				 DataDiscretizer discretizer = data.getAttribute(i_attr).getDataDiscretizer();
				 /* for each values of this attribute */
				 for(int i_sample=0; i_sample < samples.length; i_sample++)
				 {
					 double value = ((ContinuousAttributeValue)samples[i_sample].get(i_attr)).doubleValue();
					 samples[i_sample].set(i_attr, 
							new CategoricalAttributeValue(discretizer.getNumLabel(value)));
					 //samples[i_sample].get(i_attr).setType(AttributeValue.TYPE_CATEGORICAL);
				 }
			}
		}
		System.out.println("Transformation from continuous to categorical on the given SampleSet is completed.");
	}





	/**
	 * Stores the same number of records for each class 
	 * into a new SampleSet.<br>
	 * Returns the new SampleSet without over-fitting.
	 * 
	 */
	public SampleSet removeOverFit(SampleSet set)
	{
		Sample[] samples = set.getSamples();
		SampleSet newSet = new SampleSet();   // returns this new SampleSet
		int numAttributes = samples[0].size();
		ItemInfo classInfo = data.getAttribute(numAttributes-1).getItemInfo();
		int noClasses = classInfo.size();
		int [] countClasses = new int[noClasses];  // counts of each class
		int type = samples[0].get(numAttributes-1).getType(); // type of class
	
		for(int i=0; i<noClasses; ++i)
			countClasses[i] = 0;

		// counts the distribution of classes
		for(int i=0; i<samples.length;++i)    
		{
			if(type == AttributeValue.TYPE_CATEGORICAL)
			{
				CategoricalAttributeValue classAttr = (CategoricalAttributeValue)samples[i].get(numAttributes-1);
				++countClasses[classInfo.indexOfItem(classAttr.getValue())];
			}
			else
			{
				ContinuousAttributeValue classAttr = (ContinuousAttributeValue)samples[i].get(numAttributes-1);
				++countClasses[classAttr.intValue()];
			}
		}

		// find the size of a class which is minimum number.
		int minNoClass = countClasses[0];
		for(int i=1; i<noClasses;++i)
		{
			if(minNoClass > countClasses[i])
				minNoClass = countClasses[i];
		}
		System.out.println("Size of smaller class: "+minNoClass);

		// reset the counts of classes as 0
		for(int i=0; i<noClasses; ++i)
			countClasses[i] = 0;

		// remove the overfitting data
		int i_class;
		for(int i_sample = 0; i_sample < samples.length ; i_sample++ )
		{
			if( type == AttributeValue.TYPE_CATEGORICAL)
			{
				CategoricalAttributeValue classAttr = (CategoricalAttributeValue)samples[i_sample].get(numAttributes-1);
				i_class = classInfo.indexOfItem(classAttr.getValue());
			}
			else
			{
				ContinuousAttributeValue classAttr = (ContinuousAttributeValue)samples[i_sample].get(numAttributes-1);
				i_class = classAttr.intValue();
			}
			if(countClasses[i_class] <minNoClass)
			{
				newSet.add(samples[i_sample]);
				++countClasses[i_class];
			}
		}
		System.out.println("Size of the new set without over-fitting: "+newSet.size());
		System.out.println("Removing over-fitting on the given SampleSet is completed.");

		if(newSet.size() == 0)
		{
/*			JOptionPane.showMessageDialog(null,
					"The size of SampleSet without data over-fitting is zero.",
					"Improper data file",
					JOptionPane.ERROR_MESSAGE);
*/			System.out.println("Error: The size of SampleSet without data over-fitting is zero.");
		}

		return newSet;	
	}


		


	/**
	 * Sets the DataSet.
	 */
	public void setDataSet(DataSet d)
	{
		data = d;
	}

   /**
    * Sets the OverFitSelected.
	*/
	public void setOverFitSelected(boolean select)
	{
		OverFitSelected = select;
	}


   /**
    * Sets the OverFitSelected.
	*/
	public void setMissingValueSelected(boolean select)
	{
		MissingValueSelected = select;
	}


   /**
    * Sets the TransformationSelected. 
	*/
	public void setTransformationSelected(boolean select)
	{
		TransformationSelected  = select;
	}



   /**
    * Sets the TransformationSelected and its type.
	*/
	public void setTypeOfTransformation(int type)
	{
		transformType = type;
	}



   /**
    * Sets the NormalizationSelected.
	*/
	public void setNormalizationSelected(boolean select)
	{
		NormalizationSelected  = select;
	}


   /**
    * Sets the NormalizationSelected and normalizer.
	*/
	public void setNormalizer(int method)
	{
		normalizer = method;
	}


	/**
	 * Returns true if OverFitSelected is true. Otherwise, returns false.
	 */
	 public boolean isOverFitSelected(){return OverFitSelected;}

	/**
	 * Returns true if MissingValueSelected is true. Otherwise, returns false.
	 */
	 public boolean isMissingValueSelected(){return MissingValueSelected;}


	/**
	 * Returns true if TransformationSelected is true. Otherwise, returns false.
	 */
	 public boolean isTransformationSelected(){return TransformationSelected;}

	/**
	 * Returns the type of transformation.
	 */
	 public int getTypeOfTransformation(){return transformType;}

	/**
	 * Returns true if NormalizationSelected is true. Otherwise, returns false.
	 */
	 public boolean isNormalizationSelected(){return NormalizationSelected;}

	/**
	 * Returns the normalizer.
	 */
	 public int getNormalizer(){return normalizer;}

	/**
	 * Sets Logger
	 */
//	 public void setLogger(Logger l){logger = l;}


}

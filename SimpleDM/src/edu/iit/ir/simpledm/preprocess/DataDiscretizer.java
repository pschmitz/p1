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
 *					DataDiscretizer.java								
 */


package edu.iit.ir.simpledm.preprocess;


/**
 *	Discretizes the continuous variables.	
 *	DataDiscretizer is an abstract class that will be the super class 
 *  of SimpleDataDiscretizer or other discretization classes. 
 *	Each continuous attribute contains this object to transform
 *	to categorical type. 
 */
public abstract class DataDiscretizer 
{
	/** 
	 * Sets the seperators that categorize the numeric data 
	 * and the corresponding labels.   
	 */
	public abstract void set();


	/** 
	 * Returns the corresonding Label of the given double value. 
	 */
	public abstract String getNumLabel(double d);


	/** 
	 * Returns a String that shows the seperators and labels. 
	 */
	public abstract String toString();
	
}

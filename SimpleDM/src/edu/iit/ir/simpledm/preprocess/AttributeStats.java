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
 *					AttributeStats.java									
 */


package edu.iit.ir.simpledm.preprocess;

import java.util.Vector;

/**
 * Contains statistics of the attribute
 * such as minimum, maximum, average and standard deviation.				 				
 */
public class AttributeStats 
{
	/** Minimum value of the attribute */
	protected double min;

	/** Maximum value of the attribute */
	protected double max;

	/** Average value of the attribute */
	protected double avg;

	/** Standard deviation value of the attribute */
	protected double stddev;

	/** 
	 * Default constructor. 
	 */
	public AttributeStats()
	{
		min = Double.NaN;
		max = Double.NaN;
		avg = Double.NaN;
		stddev = Double.NaN;
	}

	/** 
	 * Constructor that passes statistics values of the attribute. 
	 */
	public AttributeStats(double minimum,double maximum,double average,double standard)
	{
		min = minimum;
		max = maximum;
		avg = average;
		stddev = standard;
	}

	

	/** Returns minimum value of the attribute */
	public double getMin(){
		return min;
	}

	/** Returns maximum value of the attribute */
	public double getMax(){
		return max;
	}

	/** Returns average value of the attribute */
	public double getAvg(){
		return avg;
	}

	/** Returns standard deviation value of the attribute */
	public double getStddev(){
		return stddev;
	}

	/**
	 * Sets all statistics values of the attribute.
	 */
	public void setStats(double minimum,double maximum,double average,double standard)
	{
		min = minimum;
		max = maximum;
		avg = average;
		stddev = standard;
	}

	/** Sets minimum value. */
	public void setMin(double d){
		min = d;
	}

	/** Sets maximum value. */
	public void setMax(double d){
		max = d;
	}

	/** Sets average value. */
	public void setAvg(double d){
		avg = d;
	}

	/** Sets standard deviation value. */
	public void setStddev(double d){
		stddev = d;
	}

	/**
     * toString that gives information about the attribute.
     */
	  public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\nMinimum value : "+ min);
		sb.append("\nMaximum value : "+max);
		sb.append("\nAverage value : "+avg);
		sb.append("\nStandard deviation value : "+stddev+"\n");
		return sb.toString();
	 }

}

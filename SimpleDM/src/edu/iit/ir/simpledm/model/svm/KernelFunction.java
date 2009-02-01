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
 *					Copyright (c) 2002									
 *					@author		Mi Sun Chung							
 *					@version	1.3										
 *																		
 *					KernelFunction.java									
 */


package edu.iit.ir.simpledm.model.svm;

import edu.iit.ir.simpledm.data.*;



/**
 * This is an abstract KernelFunction class that will be the
 * super class of all kernel functions. 					
 *				
 */
public abstract class KernelFunction
{
	/** Calculate the distance of two instances(samples). */
	public abstract double calculate(Sample x, Sample y);
}





/**
 * Polynomial function.
 */
class Polynomial extends KernelFunction
{
	/**  default degree is 0.16	 */
	protected double degree=0.16;  

	/** Sets degree. */
	public void setDegree(double d){
		degree= d;
	}

	/** Gets degree. */
	public double getDegree(){
		return degree;
	}


	/**
	 *	 degree D polynominal kernel function
	 *        : parameters are the index of number of the training data
	 */
	public double calculate(Sample sample1, Sample sample2)
	{
		int numAttributes = sample1.size();
		double sum = 0;
		for(int i=0; i<numAttributes-1; ++i)
		{
			sum += (((ContinuousAttributeValue)sample1.get(i)).doubleValue()
				*((ContinuousAttributeValue)sample2.get(i)).doubleValue());
		}

		sum+=1;
		return Math.pow(sum,degree);
	}
}





/**
 * Radial Basis Functions(RBF)-
 *         : parameters are the index of number of the training data
 */
class RadioBasis extends KernelFunction
{
	/** Sigma. Default is 0.15 */
	protected double sigma=0.15;
	
	/** Sets new sigma. */
	public void setSigma(double s){
		sigma = s;
	}

	/** Gets a sigma. */
	public double getSigma(){
		return sigma;
	}

	/** Calculate the distance of two instances(samples). */
	public double calculate(Sample sample1, Sample sample2)
	{
		int numAttributes = sample1.size();
		double tmp=0;
		double sum=0;
		for(int i=0; i<numAttributes-1; ++i)
		{
			tmp = (((ContinuousAttributeValue)sample1.get(i)).doubleValue()
				- ((ContinuousAttributeValue)sample2.get(i)).doubleValue());
			sum+= Math.pow(tmp,2);
		}
		sum = sum/(2*sigma*sigma);
		return Math.exp(-1 * sum);
	}
}

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
 *					SimpleDataDiscretizer.java							
 */




package edu.iit.ir.simpledm.preprocess;

import java.util.*;
import java.text.DecimalFormat;


/**
 *
 *  Segments numeric data into several labels.
 *  Using minimum, maximum, average and standard deviation,
 *  the data will be separated into the following five or less categories.
 *  <br><br>
 *  lower  : [min]                              ~ [average - ( 2 * standard deviation )]<br>
 *  low    : [average - (2*standard deviation)] ~ [average - standard deviation]<br>
 *  middle : [average - standard deviation]     ~ [average + standard deviation]<br>
 *  high   : [average + standard deviation]     ~ [average + (2*standard deviation)]<br>
 *  higher : [average + (2*standard deviation)] ~ [max]<br>
 *  <br>
 *  The labels and seperators will be stored, and used later when the Preprocessor
 *  transform the type of attributes from continuous to categorical.
 *  <br><br>
 *  To add more sophisticated categorization, another discretizer can be
 *  implemented by extending DataDiscretizer class.
 *
 */
public class SimpleDataDiscretizer extends DataDiscretizer
{
	
	/** A list of labels  */
	protected String[] labels;      

	/** A list of seperators */
	protected double[] seperators;   

	/** attribute statistics of the corresponding attribute */
	private AttributeStats stats; 



	/** Constructor */
	public SimpleDataDiscretizer(AttributeStats statistics){
		stats = statistics;
	}


	/** 
	 * Sets the seperators that categorize the continuous attribute
	 * and sets the names of labels, using statistics.   
	 */
	public void set()
	{
		Vector sep_v = new Vector();          // save seperators temporarily 
		Vector label_v = new Vector();        // save labels temporarily
		DecimalFormat df=new DecimalFormat(".00");

		double avg = stats.getAvg();          // average of this attribute
		double stddev = stats.getStddev();    // standard deviation
		double min = stats.getMin();          // minimum
		double max = stats.getMax();          // maximum
		boolean finish = false;

		sep_v.addElement(new Double(min));    // smallest seperator is minimum value.

		if( min < (avg - (2*stddev)) )        
		{
			sep_v.addElement(new Double(df.format(new Double(avg - (2*stddev)))));
			label_v.addElement(new String("lower"));  
		}
		if( min < (avg - stddev) ) 
		{
			sep_v.addElement(new Double(df.format(new Double(avg - stddev))));
			label_v.addElement(new String("low"));   
		}
		if( max < (avg + stddev) ) // 
		{
			sep_v.addElement(new Double(max));
			label_v.addElement(new String("middle")); 
			finish = true;
		}
		else
		{
			sep_v.addElement(new Double(df.format(new Double(avg + stddev))));
			label_v.addElement(new String("middle"));
		}
		if(finish == false && (max < (avg+(2*stddev))))
		{
			sep_v.addElement(new Double(max));
			label_v.addElement(new String("high"));
			finish = true;
		}
		else if(finish == false)
		{
			sep_v.addElement(new Double(df.format(new Double(avg+(2*stddev)))));
			label_v.addElement(new String("high"));
		}
		if(finish == false)
		{
			sep_v.addElement(new Double(max));
			label_v.addElement(new String("higher"));
		}

		Enumeration sep_enum = sep_v.elements();
		Enumeration label_enum = label_v.elements();
		seperators = new double[sep_v.size()];
		labels = new String[label_v.size()];
		
		for(int i=0; i<sep_v.size(); i++){   // set seperators
			seperators[i] = ((Double)sep_enum.nextElement()).doubleValue();
		}
		for(int i=0; i<label_v.size(); i++){  // set labels
			labels[i] = (String)label_enum.nextElement();
		}

	}


	/**
	 * Returns a corresponding label of the given double value.
	 */
	 public String getNumLabel(double d)
	{
		 if(d == stats.getMin())
			 return labels[0];

		 for(int i=0; i < seperators.length; i++)
		{
			 if( d <= seperators[i] )
				 return labels[i-1];
		}
		return DataSet.MISSING_VALUE;  // in case of Double.NaN
	}



	/** 
	 * Returns a String that shows the seperators and labels. 
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<labels.length; ++i)
		{
			sb.append(" "+labels[i]+" :   "+seperators[i]+"  ~  "+seperators[i+1]+" \n");
		}
		return sb.toString();
	}

}

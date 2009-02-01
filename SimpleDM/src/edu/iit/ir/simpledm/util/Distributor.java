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
 *					Distributor.java									
 */





package edu.iit.ir.simpledm.util;

//import edu.iit.ir.simpledm.gui.*;
import edu.iit.ir.simpledm.model.*;
import edu.iit.ir.simpledm.preprocess.*;

import java.text.DecimalFormat;
import javax.swing.JTextArea;


/**
 * Takes responsible for N-folds cross validation.
 * For each fold, it builds and tests a model and shows the result.  
 */
public class Distributor
{

	/** Number of folds */
	protected int noFold;

	/** Total number of records  */
	private int totalRecords;

	/** used to seperate test block from train block */
	private int [] seperators;

	/** Text area to show the results of testing. */
//	private JTextArea resultText;

	/** Logger */
//	private Logger logger;

	
	
	
	
	/** Default constructor */
	public Distributor(){}




	/**
	 * Does N-folds cross validation with given data set.
	 * Calls buildModel and testModel function of the given model.
	 * 
	 * @param model Model that will be built and tested.
	 * @param data DataSet that will be used.
	 */
	public void doCrossValidation(Model model, DataSet data)
	{

		// clones the model in initial state,
		// so that each folder has its own model.
		Model[] cloneModels = new Model[noFold];
		cloneModels[0] = model;
		try{
			for(int i=1; i<noFold; ++i){
				cloneModels[i] =(Model)model.clone();
			}
		}catch(Exception ex)	{
			System.out.println(ex.toString());
			return;
		}


		int startlnOfTestBlock,endlnOfTestBlock;
		setSeperators((int)data.getTotalNumOfInstances());
		double t0,t1,timeofBuild ; // start and end time of execution of one fold
		model.writeIntro();    // write the model name and parameters on the result text area.

		// for each fold..	
		for(int i=0; i<noFold; ++i)
		{
			model = cloneModels[i];
//			model.setLogger(logger);

			System.out.println("Fold Number  " + i + " started.");
			System.out.println("   Fold No. " + i );
			
			startlnOfTestBlock = seperators[i];
			endlnOfTestBlock = seperators[i+1];


			// set the time!!!
			t0 = System.currentTimeMillis();

			// training data
			System.out.println("Builing a model....");
			model.startBuildingModel(startlnOfTestBlock,endlnOfTestBlock); 

			// testing data
			System.out.println("Testing a model....");
			model.startTestingModel(startlnOfTestBlock,endlnOfTestBlock);

			// display the result
			System.out.println("Displaying the results");
			t1 = System.currentTimeMillis();
			timeofBuild = t1-t0; 
			System.out.println("");
			System.out.println("	Total number of training records :  "+(totalRecords-endlnOfTestBlock+startlnOfTestBlock));
			System.out.println("	Total number of testing records  :  "+(endlnOfTestBlock-startlnOfTestBlock));
			System.out.println("	Execution Time(sec) :  "+ DecimalFormat.getInstance().format(timeofBuild/1000.));
			System.out.println("");
			// records the results
			model.showResults();
		}
	}




	/**
	 * Sets number of folds for cross validation.
	 * 
	 * @param Number of folds.
	 */
	public void setNoFold(int num)	{
		noFold = num;
		seperators = new int[noFold+1];
	}





	/**
	 * Sets seperators of the N-folds.
	 * 
	 * @param total Total number of instances.
	 */
	private void setSeperators(int total){
		totalRecords = total;
		for(int i=0; i<noFold+1; ++i)
			seperators[i] = (totalRecords/noFold) * i;
	}




	/** Sets ResultText */
/*	public void setResultText(JTextArea text)	{
		resultText = text;
	}
*/



	/** Sets Lodgger */
/*	public void setLogger(Logger l){
		logger = l;

	}
*/




/*  // for testing this class 
	public static void main(String[] args) 
	{
		Distributor d = new Distributor();
		d.setNoFold(3);
		SVM model = new SVM();
		DataSet data = new DataSet();
		String filepath = new String("adult.dat");
		Loader loader = new Loader(filepath);
		loader.setDataSet(data);
		data.setLoader(loader);
		data.setTotalNumOfInstances();
		model.setDataSet(data);
		d.doCrossValidation(model,data);
	}
*/

}

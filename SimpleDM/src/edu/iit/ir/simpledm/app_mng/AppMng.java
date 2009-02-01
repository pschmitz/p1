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
 *					AppMng.java											
 */





package edu.iit.ir.simpledm.app_mng;

import edu.iit.ir.simpledm.util.*;
import edu.iit.ir.simpledm.preprocess.*;
import edu.iit.ir.simpledm.model.*;
import edu.iit.ir.simpledm.model.svm.*;

import java.io.*;


/**
 * Manages and controls the application by providing the services of
 * reading a configure file and calling classes.
 */
public class AppMng
{


	/* configure file path */
	private String m_configPath = "lib/config.txt"; 
	/* data file path */
	private String m_dataFilePath;
	/* BufferedReader to read the configure file */
	private BufferedReader m_configReader;
	/* loads the data file */
	private Loader m_loader;
	/* data set of the data file */
	private DataSet m_data;
	/* preprocessor containing the selected options in the configure file */
	private Preprocessor m_pre;
	/* the name of the selected model in the configure file */
	private String m_modelName;
	/** Selected Model */
	private Model m_model;
	/* Distributor to take care of cross folds */
	private Distributor m_distributor;
	/* the number of folds in the configure file */
	private int m_noFold;


	/** Constructor to create DataSet objtect. */
	AppMng(){
		m_data = new DataSet();
	}


	/** Reads the configure file,
	 * and start building and testing the selected model.
	 */
	public void initiate() 
	{

		// open a configure file
		try {
		m_configReader = new BufferedReader(new FileReader(m_configPath));
		}catch (Exception ex) {
			System.out.println("Problem to read configure file.");
		}
	
		// read a data file path from the configure file 
		// and load the data file and estimate it.
		openDataFile();


		// read the name of model from the configure file
		readModelName();


		// read the number of fold number
		readFoldNum();


		// read the options of preprocessing from the configure file
		// set the options of preprocessing
		setPreprocessing();


		// create the model and start building and testing
		// after completing the modeling, ask a user if he/she wants to continue or exit
		boolean exit = false;
		try{
			BufferedReader is = new BufferedReader( new InputStreamReader(System.in));
			while( exit != true )
			{
				buildModel();	// <-- start building and testing!!

				String in ;
				System.out.print("Continue(c) ? or Exit (e or enter)? : ");
				in = is.readLine();
				if(!in.equalsIgnoreCase("c")){
					exit = true;
				}
			}
			is.close();
		}catch(IOException e){
			System.out.println("IOException:  "+e);
		}
	}




	/**
	* Reads a data file from the configure file 
	* and loads the data file and estimate it.
	*/
	private void openDataFile()
	{
		// reads the data file path from the configure file
		try{
		m_dataFilePath = m_configReader.readLine();
		}catch(Exception ex) {
			System.out.println("Problem to read the data file name from config file.");
		}

		// loads the file and read the header.
		m_loader = new Loader(m_dataFilePath);
		m_loader.setDataSet(m_data);
		m_data.setLoader(m_loader);
		System.out.println("The file, " +m_dataFilePath+" , is successfully opened.");
		System.out.println("Total number of instances: "+m_data.getTotalNumOfInstances());
		System.out.println("Total number of attributes: "+m_data.getNumAttributes() );
		System.out.println("");
		m_data.setStats();
	}





	/**
	 * Reads the next line of the configure file which represents the model name.
	 */
	private void readModelName()
	{
		try{
			m_modelName = m_configReader.readLine();
		}catch(Exception ex){
			System.out.println("Problem to read a model name from config file.");
		}
	}


	/**
	 * Read the next line of the configure file which represents 
	 * the number of folds.
	 */
	private void readFoldNum()
	{
		try{
			m_noFold = Integer.parseInt(m_configReader.readLine());
		}catch(Exception ex){
			System.out.println("Problem to read a model name from config file.");
		}
	}




	/**
	 * Reads the options of preprocessing
	 * and set them in Preprocessor
	 */
	private void setPreprocessing()
	{
		m_pre = new Preprocessor(m_data);
		try{
			String s;
			while( ((s=m_configReader.readLine()) != null) &&
				(! s.equals("")) )
			{
				if(s.equals("min_max"))
				{
					m_pre.setNormalizationSelected( true );
					m_pre.setNormalizer(Preprocessor.MIN_MAX);
				}
				else if(s.equals("z-score"))
				{
					m_pre.setNormalizationSelected( true );
					m_pre.setNormalizer(Preprocessor.Z_SCORE);
				}
				else if(s.equals("categorical to continuous"))
				{
					m_pre.setTransformationSelected( true );
					m_pre.setTypeOfTransformation(Preprocessor.TO_CONTINUOUS);
				}
				else if(s.equals("continuous to categorical"))
				{
					m_pre.setTransformationSelected( true );
					m_pre.setTypeOfTransformation(Preprocessor.TO_CATEGORICAL);
				}
				else if(s.equals("replacement of missing values"))
				{
					m_pre.setMissingValueSelected( true );
				}
				else if(s.equals("remove over fit"))
				{
					m_pre.setOverFitSelected( true );
				}
				else
				{
					System.out.println("Improper option of preprocessing was read.");
					System.out.println("Check the configure file.");
					return;
				}
			}
		}catch(Exception ex){
			System.out.println("Problem to read options of preprocessing from config file.");
		}


		// print which options were selected in the configure file.
		System.out.println("< Selected options of preprocessing >");
		System.out.println("Removing Over Fitting: "+m_pre.isOverFitSelected());
		System.out.println("Replacing missing values: "+m_pre.isMissingValueSelected());
		if(m_pre.isTransformationSelected() == true)
		{
			System.out.println("Transformation of attribute type: true");
			if( m_pre.getTypeOfTransformation() == Preprocessor.TO_CATEGORICAL)
				System.out.println("Continuous to categorical transformation selected.");
			else
				System.out.println("Categorical to continuous transformation selected.");
		}
		else
		{
			System.out.println("Transformation of attribute type: false");
		}
		if(m_pre.isNormalizationSelected() == true)
		{
			System.out.println("Normalization: true");
			if( m_pre.getNormalizer() == Preprocessor.MIN_MAX)
				System.out.println("Min_Max selected.");
			else
				System.out.println("Z-Score selected.");
		}
		else
		{
			System.out.println("Normalization: false");
		}
		System.out.println("");	
	}






	/**
	 * Start building and testing the selected model.
	 * Calls Distributor class to start modeling.
	 */
	private void buildModel()
	{
		// support vecotr machine is selected.
		if(m_modelName.equals("Support Vector Machine")){
			m_model = new SVM(m_data);    // set 'model' member as a new model class
		}
		// Naive Bayes is selected.
		else if(m_modelName.equals("Naive Bayse")){
			System.out.println("****** Not implemented model!!! ********");
			return;
		}
		// Decision Tree is selected
		else if(m_modelName.equals("Decision Tree")){
			System.out.println("****** Not implemented model!!! ********");
			return;
		}
		// Neural Network is selected
		else if(m_modelName.equals("Neural Network")){
			System.out.println("****** Not implemented model!!! ********");
			return;
		}
		// Association Rule is selected
		else if(m_modelName.equals("Association Rule")){
			System.out.println("****** Not implemented model!!! ********");
			return;
		}
		//Clustering is selected
		else if(m_modelName.equals("Clustering")){
			System.out.println("****** Not implemented model!!! ********");
			return;
		}
		// no such a model..
		else{
			System.out.println("****** Not implemented model!!! ********");
			return;
		}
		System.out.print("\n\n"+m_modelName+" model selected.\n");
		System.out.println("Starting building the selected model.");
					
		m_distributor = new Distributor();
		m_distributor.setNoFold(m_noFold);
		m_model.setPreprocessor(m_pre);

		/* start buile and test the model. */
		m_distributor.doCrossValidation(m_model,m_data);
					
	}
		
		
}
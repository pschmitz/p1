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
 *					MissingCategoricalIDException.java					
 */




package edu.iit.ir.simpledm.exception;

/**
 * <code>MissingCategoricalIDException</code> is used when
 * a method is called that requires access to CategoricalAttributeValue, 
 * but that the id of the object is not available.
 * 
 */
public class MissingCategoricalIDException extends Exception
{
   /**
   * Creates a new <code>MissingCategoricalIDException</code> instance
   * with no detail message.
   */
    public MissingCategoricalIDException () {
	super();
    }

	/**
   * Creates a new <code>MissingCategoricalIDException</code> instance
   * with a specified message.
   *
   * @param messagae a <code>String</code> containing the message.
   */
    public MissingCategoricalIDException (String s) {
	super(s);
    }
}

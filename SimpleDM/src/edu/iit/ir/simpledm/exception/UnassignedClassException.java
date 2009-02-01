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
 *					SimpleDMException.java								
 */

package edu.iit.ir.simpledm.exception;

/**
 * <code>UnassignedClassException</code> is used when
 * a method requires access to the Attribute in a DataSet
 * but the DataSet does not have such an attribute assigned.
 *
 * @author <a href="mailto:len@webmind.com">Len Trigg</a> Copied from WEKA 3-2-3.
 * 
 */
public class UnassignedClassException extends RuntimeException {

  /**
   * Creates a new <code>UnassignedClassException</code> instance
   * with no detail message.
   */
  public UnassignedClassException() { 
    super(); 
  }

  /**
   * Creates a new <code>UnassignedClassException</code> instance
   * with a specified message.
   *
   * @param messagae a <code>String</code> containing the message.
   */
  public UnassignedClassException(String message) { 
    super(message); 
  }
}

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
 * <code>UnassignedDatasetException</code> is used when
 * a method is called that requires access to the DataSet, 
 * but that the DataSet does not exists.
 *
 * @author <a href="mailto:len@webmind.com">Len Trigg</a>
 * Copied from WEKA-3-2-3.
 */
public class UnassignedDatasetException extends RuntimeException {

  /**
   * Creates a new <code>UnassignedDatasetException</code> instance
   * with no detail message.
   */
  public UnassignedDatasetException() { 
    super(); 
  }

  /**
   * Creates a new <code>UnassignedDatasetException</code> instance
   * with a specified message.
   *
   * @param messagae a <code>String</code> containing the message.
   */
  public UnassignedDatasetException(String message) { 
    super(message); 
  }
}

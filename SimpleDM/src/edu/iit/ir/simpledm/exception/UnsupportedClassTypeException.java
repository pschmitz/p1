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
 *					UnsopprotedClassTypeException.java					
 */



package edu.iit.ir.simpledm.exception;

/**
 * <code>UnsupportedClassTypeException</code> is used in situations
 * where the throwing object is not able to accept DataSet with the
 * supplied structure, because the class Attribute is of the wrong type.
 *
 * @author <a href="mailto:len@webmind.com">Len Trigg</a>
 * Copied from WEKA-3-2-3.
 */
public class UnsupportedClassTypeException extends SimpleDMException {

  /**
   * Creates a new <code>UnsupportedClassTypeException</code> instance
   * with no detail message.
   */
  public UnsupportedClassTypeException() { 
    super(); 
  }

  /**
   * Creates a new <code>UnsupportedClassTypeException</code> instance
   * with a specified message.
   *
   * @param messagae a <code>String</code> containing the message.
   */
  public UnsupportedClassTypeException(String message) { 
    super(message); 
  }
}

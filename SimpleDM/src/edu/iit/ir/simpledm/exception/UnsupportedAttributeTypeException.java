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
 * <code>UnsupportedAttributeTypeException</code> is used in situations
 * where the throwing object is not able to accept Attributes with the
 * supplied structure, because one or more of the Attributes in the
 * DataSet are of the wrong type.
 *
 * @author <a href="mailto:len@webmind.com">Len Trigg</a>
 * Copied from WEKA-3-2-3.
 */
public class UnsupportedAttributeTypeException extends SimpleDMException {

  /**
   * Creates a new <code>UnsupportedAttributeTypeException</code> instance
   * with no detail message.
   */
  public UnsupportedAttributeTypeException() { 
    super(); 
  }

  /**
   * Creates a new <code>UnsupportedAttributeTypeException</code> instance
   * with a specified message.
   *
   * @param messagae a <code>String</code> containing the message.
   */
  public UnsupportedAttributeTypeException(String message) { 
    super(message); 
  }
}

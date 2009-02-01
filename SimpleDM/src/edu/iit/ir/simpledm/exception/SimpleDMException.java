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
 * <code>SimpleDMException</code> is used when some SimpleDM-specific
 * checked exception must be raised.
 *
 */
public class SimpleDMException extends Exception {

  /**
   * Creates a new <code>SimpleDMException</code> instance
   * with no detail message.
   */
  public SimpleDMException() { 
    super(); 
  }

  /**
   * Creates a new <code>SimpleDMException</code> instance
   * with a specified message.
   *
   * @param messagae a <code>String</code> containing the message.
   */
  public SimpleDMException(String message) { 
    super(message); 
  }
}

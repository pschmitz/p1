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
 *					Sample.java											
 */



package edu.iit.ir.simpledm.data;

import java.util.*;

/**
 * Contains the attribute values of one record.
 * 
 */
public class Sample 
{
	/** The array of attributes */
    private AttributeValue[] m_attributes;




    /**
     * Default constructor.
     */
    public Sample () {
    }

    /**
     * Default constructor that passes an array of
     * attributes.
     */
    public Sample (AttributeValue[] attributes) {
		m_attributes = attributes;
    }

    /**
     * Returns the number of attributes in this Sample.
     */
    public int size () {
		return m_attributes.length;
    }

    /**
     * Returns the attribute at the specified index.
     */
    public AttributeValue get (int i) {
		return m_attributes[i];
    }

    /**
     * Returns the last attribute.
     */
    public AttributeValue getLast () {
		return m_attributes[m_attributes.length - 1];
    }

	/**
	 * Sets a new attribute value.
	 * @param i - int. An index of the attribute.
	 */
	public void set(int i, AttributeValue value){
		m_attributes[i] = value;
	}


    /** 
     * Outputs this sample in string format.
     */
    public String toString () {
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		for (int i=0; i<m_attributes.length; i++) {
		    AttributeValue a = m_attributes[i];
			sb.append("[" + a + "]");
		}
		sb.append(">");
		return sb.toString();
    }


}

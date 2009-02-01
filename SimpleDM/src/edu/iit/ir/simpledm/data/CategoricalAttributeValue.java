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
 *					CategoricalAttributeValue.java						
 */


package edu.iit.ir.simpledm.data;

import edu.iit.ir.simpledm.exception.*;

/**
 * Represents a categorical attribute value.  Categorical
 * values are represented by a String that is the name of
 * value for that category.  An integer can also be assigned
 * to represent the category along with the actual String.
 */
public class CategoricalAttributeValue extends AttributeValue
{
	/** The value of this categorical attribute */
    private String m_value;

    /** The integer that uniquely identifies the attribute */
    private int m_id;



    /**
     * Default Constructor.
     */
    public CategoricalAttributeValue () {
		super(AttributeValue.TYPE_CATEGORICAL);
		m_id = -1;
    }



    /**
     * Constructor that sets the value of the category.
     */
    public CategoricalAttributeValue (String value) {
		super(AttributeValue.TYPE_CATEGORICAL);
	
		if(value.equals(AttributeValue.MISSING_VALUE ))
			super.missing = true;
	
		m_value = value;
    }

    /**
     * Constructor that sets the value and unique index
     * number for this attribute.
     */
    public CategoricalAttributeValue (String value, int id) {

		super(AttributeValue.TYPE_CATEGORICAL);
	
		if(value.equals(AttributeValue.MISSING_VALUE ))
			super.missing = true;

		m_value = value;
		m_id    = id;
    }

    /**
     * Returns the ID of this categorical attribute. 
     * Throws an exception if this was not set.
     */
    public int getID () throws MissingCategoricalIDException {
		if (m_id == -1)
		    throw new MissingCategoricalIDException();

		return m_id;
    }

    /**
     * Returns the value of this categorical attribute.
     */
    public String getValue () {
		return m_value;
    }

	/**
	 * Sets the categorical value.
	 */
	public void setValue(String value){
		m_value = value;
	}



    /**
     * toString
     */
    public String toString () {
		StringBuffer sb = new StringBuffer();
		if(m_type ==TYPE_CATEGORICAL){
			sb.append("Type = cat., ");
		}
		else if(m_type == TYPE_ORDINAL){
			sb.append("Type = ord., ");
		}
		sb.append("Value = ");
		sb.append(m_value);

		return sb.toString();
    }

	/**
	 * Set the type of attribute as ordinal. This method will be called
	 * by K-Menas algorithm.
	 *
	 * @param ordinal - boolean value which represents if ordirnal type should be
	 * concerned or not.
	 */
	public void setOrdinal(boolean ordinal)
	{
		if(ordinal == true)
		{
			super.setType(AttributeValue.TYPE_ORDINAL);
		}
	}


}

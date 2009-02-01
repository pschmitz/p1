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
 *					ContinuousAttributeValue.java						
 */



package edu.iit.ir.simpledm.data;

/**
 * Represents a continuous attribute value.  Continuous values
 * are stored as a double and as an integer.  It is up to the 
 * user to use the more appropriate one.
 */
public class ContinuousAttributeValue extends AttributeValue
{

	/** The double value of this attribute */
    private double m_doubleValue;

    /** The integer value of this attribute */
    private int m_intValue;



    /**
     * Contructor that takes a double as the value for this
     * attribute.
     */
   public ContinuousAttributeValue (double d) {
		super(AttributeValue.TYPE_CONTINUOUS);
		
		if(Double.compare(d,Double.NaN) == 0)
			super.missing = true;

		setDoubleValue(d);
    }

    /**
     * Contructor that takes an integer as the value for this
     * attribute.
     */
    public ContinuousAttributeValue (int i) {
		super(AttributeValue.TYPE_CONTINUOUS);
		
		if(i == Integer.MIN_VALUE )
			super.missing = true;

		setIntValue(i);
    }





    /**
     * Returns the attribute value as a double.
     */
    public double doubleValue () {
		return m_doubleValue;
    }

    /**
     * Returns the attribute value as an integer.
     */
    public int intValue () {
		return m_intValue;
    }

	/** 
	 * Sets the double as a value. Int value will be set, too.
	 */
	public void setDoubleValue(double d)
	{
		 m_doubleValue = d;
		 m_intValue = new Double(d).intValue();
	}

	/** 
	 * Sets the int as a value. Double value will be set, too.
	 */
	public void setIntValue(int i)
	{
		m_intValue = i;
		m_doubleValue = new Integer(i).doubleValue();
	}




    /**
     * toString that gives information about the attribute.
     */
    public String toString () 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Type = cont., ");
		sb.append("Value = ");
		sb.append(m_doubleValue);
		return sb.toString();
    }

}

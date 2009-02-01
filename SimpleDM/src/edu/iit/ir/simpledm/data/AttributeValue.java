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
 *					Attribute.java										
 */

package edu.iit.ir.simpledm.data;


/** 
 * This is an abstract attribute class that will be the
 * super class of all attributes. All sub-classes will call 
 * the constructor for this class and provide their type. <br> 
 * The type of AttributeValue in data package and 
 * the type of Attribute in preprocessing package can be
 * different if a user transformed the types on the PreprocessingPanel.
 * That is, the type of Attribute is determined by the header of a data file,
 * while the one in an AttributeValue can be transformed by a user after preprocessing. 
 * Therefore, when a model is created and tested, 
 * the type in an AttributeValue should be considered regarding to the types of attributes. 
 */
public abstract class AttributeValue
{
    /** The type of value of this attribute.
     */
    protected int m_type;

    /** Represents a categorical value
     */
    public static final int TYPE_CATEGORICAL = 0;

    /** Represents a continuous value
     */
    public static final int TYPE_CONTINUOUS  = 1;

	/** Represents a ordinal value
	 */
	public static final int TYPE_ORDINAL = 2;

	/** Represent the missing value in arecord
	 */
	public static final String MISSING_VALUE = new String("?");

	/** True if this attribute value is missing. Otherwise, false
	 */
	protected boolean missing = false;



    /** 
     * Constructor that sets the type of this attribute value.
     * 
     * @param type 
     */
    public AttributeValue (int type) 
	{
		m_type = type;
    }

    /** 
     * Returns the type of this attribute.
     */
    public int getType () 
	{ 
		return m_type;
    }

	 /** 
	  * Sets the type of this attribute.
	  * 
	  * @param type 
	  */
	 public void setType(int type)
	{
		 m_type = type;
	}

	/** 
	 * Returns boolean representing if the value is missing or not.
	 */
	public boolean isMissingValue()
	{
		return missing;
	}


}

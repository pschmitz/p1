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



package edu.iit.ir.simpledm.preprocess;

import java.util.Vector;


/**
 * Contains the name and type of the attribute, 
 * and AttributeStats and ItemInfo(label information).
 * If the attribute is continuous and discretized, 				 
 * Discretizer will be added as a member.				
 *				
 */
public class Attribute
{
	/** Name of the attribute */
	protected String attributeName;

	/** 
	 * Type of the attributes that was initially identified by the program.
	 * If the type is converted by a user, Sample object will get the new type.
	 */
	protected int type;

	/** Attribute Statistics informaiton  */
	protected AttributeStats stats;

	/** Items(labels) distribution information */
	protected ItemInfo items;

	/** Discretizer for a discretized continuous attribute */
	protected DataDiscretizer discretizer;





	/** 
	 * Default constructor. 
	 * Creates AttributeStats, ItemInfo and SimpleDataDiscretizer object.
	 */
	public Attribute()
	{
		type = -1;
		stats = new AttributeStats();
		items = new ItemInfo();
		discretizer = new SimpleDataDiscretizer(stats);
	}


	/** 
	 * Constructor that takes a name of this attribute.
	 * Creates AttributeStats, ItemInfo and SimpleDataDiscretizer 
	 * object of this attribute.
	 */
	public Attribute(String name)
	{
		attributeName = new String(name);
		type = -1;
		stats = new AttributeStats();
		items = new ItemInfo();
		discretizer = new SimpleDataDiscretizer(stats);
	}


	/**
	 * Returns the name of this attribute.
	 */
	public String getName(){
		return attributeName;
	}


	/** 
	 * Returns the type of this attribute. 
	 */
	public int getType(){
		return type;
	}


	/** 
	 * Returns Attribute Statistics information. 
	 */
	public AttributeStats getAttributeStats(){
		return stats;
	}

	
	/** 
	 * Returns Item distribution information. 
	 */
	public ItemInfo getItemInfo(){
		return items;
	}

	
	/**
	 * Returns DataDiscretizer of this attribute. 
	 */
	public DataDiscretizer getDataDiscretizer(){
		return discretizer;
	}

	
	/**
	 * Sets the name of this attribute.
	 */
	public void setName(String name) { 
		attributeName = new String(name);
	}

	
	/**
	 * Sets the type of this attribute.
	 */
	public void setType(int t){ 
		type  = t;
	}


	/**
	 * Sets AttributeStats.
	 */
	public void setAttributeStats(AttributeStats as){
		stats = as;
	}
	

	/**
	 * Sets ItemInfo.
	 */
	public void setItemInfo(ItemInfo ii){
		items = ii;
	}


	/**
	 * Sets DataDiscretizer.
	 */
	 public void setDataDiscretizer(DataDiscretizer d){
		 discretizer = d;
	 }

}






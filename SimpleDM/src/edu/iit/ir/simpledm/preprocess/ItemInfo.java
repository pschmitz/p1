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
 *					ItemInfo.java										
 */



package edu.iit.ir.simpledm.preprocess;

import java.util.*;


/**
 * Contains the information about items(labels) 
 * such as the name and count of each item.				 
 *			     
 */
public class ItemInfo
{
	/** Items(labels) for categorical attribute. */
	protected Vector items;

	/** Number of each item(label). */
	protected Vector numItems;




	/** Default constructor */
	public ItemInfo(){
		items = new Vector();
		numItems = new Vector();
	}
	 

	/**
	 * Inserts an item with the given label 
	 * and increase the number of the label by one.
	 */
	public void insertItem(String label)
	{
		if(items.contains(label) == false)
		{
			items.addElement(label);
			numItems.addElement(new Integer(1));
			
		}
		else
		{
			int num = ((Integer)numItems.get(items.indexOf(label))).intValue();
			num+=1;
			numItems.set(items.indexOf(label),new Integer(num));
		}
	}
	

	/**
	 * Returns an index of the given label(item).
	 */
	public int indexOfItem(String item){
		return items.indexOf(item);
	}


	/**
	 * Returns the count of the given item.
	 */
	public int count(String item){
		return ((Integer)numItems.get(items.indexOf(item))).intValue();
	}


	/**
	 * Returns the count of the given item index.
	 */
	public int count(int index){
		return ((Integer)numItems.get(index)).intValue();
	}


	/**
	 * Returns the total number of items.
	 */
	public int size(){
		return items.size();
	}



	/**
	 * Returns the name of an item.
	 *
	 * @param index - int. Index of the item.
	 */
	public String getLabel(int index){
		return (String)items.get(index);
	}



	/**
	 * Sets new items and sets the counts as zero.
	 *
	 * @param labels - Vector that has all labels(items).
	 */
	 public void setLabels(Vector labels){
		 items = labels;
		 numItems = new Vector();
		 for(int i=0; i<items.size(); ++i){
			 numItems.addElement(new Integer(0));
		 }
	 }


	 /**
     * toString that gives information about the attribute.
     */
	  public String toString(){
		StringBuffer sb = new StringBuffer();
		Enumeration e = items.elements();
		Enumeration e_counts = numItems.elements();
		sb.append("\n--------------------");
		sb.append("\nName       Count");
		sb.append("\n--------------------");
		while(e.hasMoreElements())
		{
			sb.append("\n"+(String)e.nextElement());
			sb.append("    "+(Integer)e_counts.nextElement());
		}
		sb.append("\n");
		return sb.toString();
	 }






}

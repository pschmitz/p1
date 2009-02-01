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
 *					SampleSet.java										
 */


/*
 * SampleSet.java
 *
 */
package edu.iit.ir.simpledm.data;

import java.util.*;

/**
 * Stores a block of data records.<br>
 * The SampleSet class contains a set of Samples. 
 */
public class SampleSet
{
	/** Maximum number of instances fit into memory */
	public final static int maxRcdsMemory = 5000;

	/** The set of all Samples */
    protected Set m_set;

    /** Tells whether or not the set has the maximum number of records.*/
    protected boolean m_setFull;



    /**
     * Default constructor.
     */
    public SampleSet () {
		m_set = new HashSet();
		m_setFull = false;
    }

    /**
     * Constructs a SampleSet with the data members
     * provides.
     */
    public SampleSet (Set s, boolean b) {
		m_set = s;
		m_setFull = b;
    }

    /**
     * Adds a Sample to the set until it is full.
     */
    public boolean add (Sample s) {
		if(m_set.size() >= maxRcdsMemory)
			m_setFull = true;

		if (!m_setFull)
		{
		    m_set.add(s);
			return true;
		} 
		else 
		{
			return false;
		}
    }

    /**
     * Shuts off the ability to add anything to
     * the set.
     */
    public void suspend () {
		m_setFull = true;
    }

    /**
     * Returns the number of Samples in the set.
     */
    public int size () {
		return m_set.size();
    }

    /**
     * Returns an array representation of 
     * our set of samples.
     */
    public Sample[] getSamples () {
		Sample[] s = new Sample[m_set.size()];
		Iterator sampleIter = m_set.iterator();

		for (int i=0; sampleIter.hasNext(); i++) 
			s[i] = (Sample)sampleIter.next();

		return s;
    }
}

package com.coders.generic.wavemaker_minifier.interfaces;

import java.util.List;

/**
 * 
 * @author Diego Nunez
 *
 */
public interface IPageConfiguration {

	public String getName();
	
	public String getType();
	
	public List<IParameter> getParameters();
	
}

package com.coders.generic.wavemaker_minifier.interfaces;

import java.util.List;

/**
 * 
 * @author Diego Nunez
 *
 */
public interface IPagesConfiguration {

	public List<IPageConfiguration> getConfiguration();

	public boolean isPage(String name);

}

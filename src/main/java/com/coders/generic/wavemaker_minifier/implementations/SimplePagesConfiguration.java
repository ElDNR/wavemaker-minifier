package com.coders.generic.wavemaker_minifier.implementations;

import java.util.ArrayList;
import java.util.List;

import com.coders.generic.wavemaker_minifier.interfaces.IPageConfiguration;
import com.coders.generic.wavemaker_minifier.interfaces.IPagesConfiguration;

/**
 * 
 * @author NunezElite
 *
 */
public class SimplePagesConfiguration implements IPagesConfiguration {

	public List<IPageConfiguration> getConfiguration() {
		return new ArrayList<IPageConfiguration>();
	}

	public boolean isPage(String name) {
		return true;
	}

}

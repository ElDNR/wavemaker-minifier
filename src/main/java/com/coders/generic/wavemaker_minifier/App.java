package com.coders.generic.wavemaker_minifier;

import com.coders.generic.wavemaker_minifier.implementations.SimpleMinifier;
import com.coders.generic.wavemaker_minifier.interfaces.IMinifier;

/**
 * 
 * @author Diego Nunez
 *
 */
public class App {

	public static void main(String[] args) {
		IMinifier minifier = new SimpleMinifier();

		if (args.length > 0) {
			minifier.minify(args[0]);
		}
	}

}

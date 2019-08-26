# Java Wrappers for the OpenMM C API.
Java Wrappers for the OpenMM C API automatically generated using Jnaerator.

Author: Hernan V Bernabe  
Email: hernan-bernabe@uiowa.edu

Author: Michael J Schnieders
Email: michael-schnieders@uiowa.edu

## Introduction	
This project contains Java Wrappers for the OpenMM C API, which are automatically generated using Jnaerator.

## Generation of the Java OpenMM Wrappers using Jnaerator

Ensure that the latest version of OpenMM is installed on your machine. This can be done, for example, by using the Anaconda command: 

	conda install -c omnia openmm


The following Jnaerator command can then be used: 

	java -jar jnaerator-0.12-shaded.jar config.jnaerator

where the contents of "config.jnaerator" are given by:

	-limitComments

	-runtime JNA

	-direct

	-skipDeprecated

	-mode Jar

	-jar openmm.jar

	-package edu.uiowa.jopenmm

	-library OpenMM
	../include/OpenMMCWrapper.h

	-library AmoebaOpenMM
	../include/AmoebaOpenMMCWrapper.h

The configuration file contains the various flags needed to set up generation of the wrappers as well as information about where to locate the OpenMM header files. Some Important flags include:

* -runtime sets the runtime library that will be used to generate the wrapper classes, in this case JNA

* -direct tells the Jnaerator to use the fastest direct call convention for library generation

* -skipDeprecated skips the generation of any deprecated members

* -mode specifies the output mode for the jnaerator

* -package sets the java package where all the generated output will reside (our package is called simtk.openmm)

* -library sets the name of the output library. Importantly, after the library flag you must specifiy (i.e. set the path to) C API header files.

For additional documentation on flag options available to edit the configuration file visit Jnaerator Wiki:
https://github.com/nativelibs4java/JNAerator/wiki/Command-Line-Options-And-Environment-Variables

## Using the JOpenMM Library

To use the Java OpenMM Wrappers, please first initialize the library using the OpenMMUtils class:
	
      OpenMMUtils.init();

This will extract the OpenMM binary libraries from the openmm-fat.jar file to a temporary directory and configure JNA to find them. OpenMM plugins can be loaded as follows:

      PointerByReference plugins = OpenMM_Platform_loadPluginsFromDirectory(OpenMMUtils.OPENMM_PLUGIN_DIR);


# Java Wrappers for the OpenMM C API.
Java Wrappers for the OpenMM C API automatically generated using Jnaerator.

Author: Hernan V Bernabe Email: hernan-bernabe@uiowa.edu

## Introduction	
This project contains Java Wrappers for the OpenMM C API, which are automatically generated using Jnaerator.

## Generation of the Java Wrappers for OpenMM using Jnaerator

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

	-jar openmm-7.1.1.jar

	-package simtk.openMM

	-library OpenMM
	/Applications/anaconda3/pkgs/opnemm-7.1.1-py36_0/include/OpenMMCWrapper.h

	-library AmoebaOpenMM
	/Applications/anaconda3/pkgs/openmm-7.1.1-py36_0/include/AmoebaOpenMMCWrapper.h

The configuration file contains the various flags needed to set up generation of the wrappers as well as information about where to locate the OpenMM X header files. Some Important flags include:

* -runtime sets the runtime library that will be used to generate the wrapper classes, in this case JNA

* -direct tells the Jnaerator to use the fastest direct call convention for library generation

* -skipDeprecated skips the generation of any deprecated members

* -mode specifies the output mode for the jnaerator

* -package sets the java package where all the generated output will reside (our package is called simtk.openmm)

* -library sets the name of the output library. Importantly, after the library flag you must specifiy (ie set the path to) where the CWrapper.h files are located. There are two libraries we use to generate our wrapper classes for OpenMM.

For additional documentation on flag options available to edit the configuration file visit Jnaerator Wiki:
https://github.com/nativelibs4java/JNAerator/wiki/Command-Line-Options-And-Environment-Variables

## Environment Variable Set Up

To use the OpenMM Java Wrappers, two environment variables need to be set (e.g. in .bash_profile) in order to invoke the OpenMM libraries from within the Java program. First, the JNA_LIBRARY_PATH variable needs to point to the location of the lib subdirectory of the OpenMM installation package:

	export JNA_LIBRARY_PATH="/Applications/anaconda3/pkgs/openmm-7.1.1-py36_0/lib"

Second, the OPENMM_PLUGIN_DIR variable needs to point to the location of the plugins subdirectory of the OpenMM installation package:

	export OPENMM_PLUGINS_DIR="/Applications/anaconda3/pkgs/openmm-7.1.1-py36_0/lib/plugins"
	


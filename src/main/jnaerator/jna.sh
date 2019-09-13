#!/bin/bash

java -jar jnaerator-0.12-shaded.jar config.jnaerator

jar -xvf openmm.jar

cp edu/uiowa/jopenmm/*java ../java/edu/uiowa/jopenmm/.

rm openmm.jar

rm -rf edu

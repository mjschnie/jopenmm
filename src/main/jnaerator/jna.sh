#!/bin/bash

java -jar jnaerator-0.12-shaded.jar config.jnaerator

tar -xvf openmm.jar

cp simtk/openmm/*java ../java/simtk/openmm/.

rm openmm.jar

rm -rf simtk

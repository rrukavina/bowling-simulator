# bowling-simulator

## Overview

A console application, which, given a valid sequence of rolls for one line of American Ten-Pin Bowling, produces the total score for the game.

## How to Build

Prerequisite is that maven and JDK is installed and in your path.

Clone the git repository to your local machine, change directory into the project's top level
directory containing the pom.xml file and run the following maven command.

$ mvn clean install

## How to Run

Change directory into the same directory that contains the pom.xml and also contains the target subdirectory
which was created after the running the build command.

To run the app execute the following command line:

$ java -jar target/bowling-simulator-1.0-SNAPSHOT.jar  <path to shots input file>

E.g.

$ java -jar target/bowling-simulator-1.0-SNAPSHOT.jar resources/perfectGameShotsInput.txt  



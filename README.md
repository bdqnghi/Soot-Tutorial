# Soot Tutorial
This is the tutorial for using Soot and Flowdroid to analyze Java and Android application. Also, it can be served as a template for future assignments. Feels free to build up your own project from this tutorial instead of creating the project from scratch.


## Prerequisites
- Intall Java 7 SDK or Java 8 SDK
- Download an IDE: Eclipse or Intellij

## Soot Installation

The first step is to download the Soot .jar file. 

My suggestion is not to download Soot from the official web page: https://www.sable.mcgill.ca/soot/soot_download.html
since the version is outdated.

Instead, building the jar from the latest source code is better: https://github.com/Sable/soot or taking from the stable prebuilt version here: https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/build/.

I have download it and put the necessary jars file into: https://github.com/bdqnghi/Soot-Tutorial/tree/master/lib
For Soot, there are 2 files: soot-3.0.1-source.jar (contains original source code) and and soot-3.0.1.jar (contains byte code). My suggestion is to import both of them into your project as the soot-3.0.1.jar will be the API for external import, and the soot-3.0.1-source.jar will be necessary if one want to understand how a function in Soot is implemented.

Next step is to import the project and add the soot-3.0.1.jar into the class path of the project. 
- To import .jar file in Eclipse, you can refer to: https://stackoverflow.com/a/3280384/2760331 
- To import .jar file in Intellij, you can refer to: https://stackoverflow.com/a/1051705/2760331

An example of correctly imported project can be seen here (in Intellij):
![alt text](/figures/project.png)


## Example

An quick example can be found in the main file ```SootIntroduction.java```, which simply configures necessary options for Soot, load a sample .jar file (located at /resource/js.jar) and print out all of the methods of the main Class of the jar.

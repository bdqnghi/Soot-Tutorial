# Soot Tutorial
This is the tutorial for using Soot and Flowdroid to analyze Java and Android application. Also, it can be served as a template for future assignments. Feels free to build up your own project from this tutorial instead of creating the project from scratch.


## Prerequisites
- Intall Java 7 SDK or Java 8 SDK
- Download an IDE: Eclipse or Intellij

## Installation

My suggestion is not to download Soot and FlowDroid separatedly as it will introduce a lot of inconsistent betwween the versions.

Instead, using the prebuilt soot-infoflow-cmd-jar-with-dependencies.jar, which has been packaged all of the necessary .jar file into one single bundle will make your life easier https://github.com/secure-software-engineering/FlowDroid/releases/download/v2.6.1/soot-infoflow-cmd-jar-with-dependencies.jar, I have downloaded and put it in here: https://github.com/bdqnghi/Soot-Tutorial/blob/master/lib/soot-infoflow-cmd-jar-with-dependencies.jar

Next step is to import the project and add the .jar into the class path of the project. 
- To import .jar file in Eclipse, you can refer to: https://stackoverflow.com/a/3280384/2760331 
- To import .jar file in Intellij, you can refer to: https://stackoverflow.com/a/1051705/2760331

An example of correctly imported project can be seen here (in Intellij):
![alt text](/figures/project.png)


## Examples

I have provided 2 simple examples:
- ```SootIntroduction.java```
- ```FlowDroidIntroduction.java```



# DemeterWatch

DemeterWatch is a tool developed in the java language with the purpose of analyzing projects developed in this same language.
The main objective of the analysis is to extract information from the project by searching for breaks in the 
[Law of Demeter (LoD)](https://www2.ccs.neu.edu/research/demeter/demeter-method/LawOfDemeter/paper-boy/demeter.pdf) in classes 
that use the JCF(Java Collection Framework). Afterwards, the user will be shown which methods cause the confinement to 
break and change the object's state.

## Getting Started

### Requirements

* JDK17; *the version used to build this tool was the OpenJDK (build 17.0.1+12-39)*
* Maven;

### Installation

First, download the source from this repository, you can do it using git:
```shell
git clone https://github.com/youngkaneda/DemeterWatch
```
Next, you need to build the Java package using maven, this will also install the maven dependencies of the project:
```shell
cd DemeterWatch/demeter-watch
mvn clean package
```
This will generate a `demeter-watch.jar` archive and you are ready to go.

### How use the tool

To see the basic usage of the tool run `java -jar demeter-watch.jar --help`. This will present all the required arguments 
to run the analysis.

Next will be presented an example of how use the tool properly, and then an explanation about its outputs:

*PS.: the classpath -cp generally is not needed since the tool will analyze the project source code, but in some cases it 
is necessary to load additional source code entries to the AST parse library used.*
```shell
java -jar demeter-watch.jar \
    -o=/home/user/output \ 
    -r=/home/user/projects/ \
    -p=tomcat-7.0.2/ \
    -s=tomcat-7.0.2/java/ \
    -cp="lib/ lib2/"
```
After the program run completely it will produce output files so the user can visualize where in the code the principle was 
broken. They are as follows:
```
elements.json
script.js
graph.html
calls.txt
```
The first three files are necessary for the visualization of the chain call of methods inside the project through a graph 
structure, the page also includes a search feature to help access the methods more easily.

The file `calls.txt` will be divided into three sections containing the method calls of the project and then filtering it:
```text
All Method Calls (62890)
...
Calls That Are Candidates (207)
...
Calls That Breaks Confinement (20)
...
<java.util.Set; remove[java.lang.Object]; boolean; org.apache.catalina.realm.JAASMemoryLoginModule; logout[]; boolean; null; subject.getPrincipals()>
```
The method call shown in the example can be read as:

`java.util.Set` - Class that own the `remove[java.lang.Object]` method;

`remove[java.lang.Object]` - The method that changed the state of an JCF object;

`boolean` - The fully qualified return type of the `remove` method;

`org.apache.catalina.realm.JAASMemoryLoginModule` - Class that has the `logout[]` method;

`logout[]` - Method that has some invocation of the `remove` method;

`boolean` - The return type of the previous method;

`null` - The next method on the method call chain, in this case there are none.

`subject.getPrincipals()` - Who incorrectly called the method, and broke the LoD.

### Visualization page

To access the graph visualization page you only need to open the `graph.html` file in any browser, be aware that since all 
files are local, you may need change the CORS origin policy in your browser configuration to accept local files, look at [here](#how-enable-cors-requests-for-local-files).

:warning: WARNING :warning:

Larger codebases may cause the visualization page to load slower than usual because `vis.js` needs to build the whole graph 
before presenting it.

![ss](https://i.imgur.com/M6GNKEf.png)

The nodes that breaks the LoD principle are painted with red, and the graph edges have weight to indicate how many times 
that method node is called.

### How enable CORS requests for local files

Modern browsers like Chromium and Firefox enforce strict security policies that prevent local files (`file://` URLs)
from making cross-origin requests (CORS) to other resources, including other local files. However, if you need to allow
CORS requests to local files for development or testing purposes, here are some approaches:

#### Chromium-Based Browsers (Chrome, Edge, Brave, etc.)

Run the browser with the `--disable-web-security` and `--allow-file-access-from-files` flags:

```
google-chrome --disable-web-security --allow-file-access-from-files --user-data-dir=/tmp/chrome_dev
```

The `--user-data-dir` is needed to prevent profile conflicts.

#### Firefox

Use `about:config` Settings:

* Open Firefox and go to `about:config`.
* Search for `security.fileuri.strict_origin_policy` and set it to `false`.
* This allows local files to make CORS requests but is insecure and should only be used for testing.


### Community guidelines

If you found a bug, something that could be improved, or a new feature to increase the functionality of this software 
you can open an issue, or fork this repository and send a PR.

If you have any question about this project, how it works, or any implementation detail, email to juan.pablo@academico.ifpb.edu.br

### Examples

This repository also contains an examples directory containing the results of running the tool with 40 randomly 
selected projects from [Qualitas.class corpus](http://java.labsoft.dcc.ufmg.br/qualitas.class/index.html) a compiled archive of 
open source Java projects.

## License

This project is licensed under [the GPL v3+ license](https://github.com/youngkaneda/DemeterWatch/blob/master/COPYING).

## About the name

This project was previously called "Method Call Parser" until the 1.0.0 release due to its high modularization, some of its 
modules can be used and extended to do other types of analysis, even be extended to use others Abstract Syntax Tree parsing 
libraries.

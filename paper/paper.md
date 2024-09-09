---
title: 'DemeterWatch: A Java tool to detect Law of Demeter violations in Java collections'

tags:
  - Java
  - software-engineering
  - metadata
  - source-code-analysis

authors:
  - name: Juan Pablo P. de Aquino
    orcid: 0009-0004-9334-5936
    equal-contrib: true
    affiliation: 1
    corresponding: true
  - name: Fernando de M. Firmino
    orcid: 0000-0001-9054-8659
    equal-contrib: true
    affiliation: 2
  - name: Diogo D. Moreira
    equal-contrib: true
    affiliation: 1
  - name: Ricardo de S. Job
    equal-contrib: true
    affiliation: 1

affiliations:
  - name: Instituto Federal de Educação Ciência e Tecnologia da Paraíba - IFPB, Brazil
    index: 1
  - name: Universidade Federal da Paraíba - UFPB, Brazil
    index: 2

date: 09 September 2024
bibliography: paper.bib
---

# Summary

Object-oriented languages brought important concepts and concerns to the software 
industry, such as inheritance, polymorphism, and coupling. Since the 1970s, 
these concepts have been studied, and in particular, coupling has emerged as 
an important mechanism that allows components to keep the details of their construction 
restricted [@PARNAS]. In this sense, when an object's encapsulation is compromised, its 
implementation becomes exposed, and other parts of the code can directly alter its 
behavior, reducing the cohesion of the software. This coupling between components 
can cause critical problems in the development cycle, increasing the costs of its 
evolution and maintenance. This work presents DemeterWatch, a tool to identify a 
specific case of coupling violations inside Java projects source code.

# Statement of need

During software development, a recurring issue is the coupling created between
classes, often due to inefficient modeling, resulting in code with classes and
methods that are not very reusable and difficult to maintain. A principle aimed
at minimizing the problems mentioned above is the Law of Demeter (LoD) or the
principle of the least knowledge [@LIEB]. When a class does not comply with the LoD,
other problems may arise. One such issue, which is the focus of this study,
is the confinement break, which occurs when an object can change the state of
another object without its knowledge, by calling methods that improperly expose
their properties.

The context in which these violations are assessed is that of data structures
belonging to the Java Collections Framework (JCF), analyzing whether there is
a breach in method calls involving these structures through the method
of static analysis. This approach is important as it alerts developers to an
issue that is not easily visible, with even equivalent and hard-to-detect forms
existing [@BLOCK]. It also offers a way to visualize the breach cases found,
allowing for corrections to be made in the source code.

# Example use case

The complete dataset of experiments is available in the repository to further
visualization containing forty randomly selected projects outputs, those 
projects are present at Qualitas.class corpus [@QUALITAS]. The following Table 1 
show how many LoD violations were found in open source projects, some of them 
are well-known projects used by Java developers:

|        Name        | Violations found |
|:------------------:|:----------------:|
| Apache Collections |        6         |
|   Apache Tomcat    |        20        |
|       Quartz       |        1         |
|      JGrapht       |        6         |
|       JMeter       |        6         |
Table 1: Example of violations found in real open source projects analyzed by the tool.

# Acknowledgements

The author would like to express gratitude to the IFPB Institute which made this 
research project possible to be developed and offered the support needed.

# References
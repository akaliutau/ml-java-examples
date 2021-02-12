About
======

This is a collection of machine-learning examples written in Java on the basis of different well-known libraries:

* Mallet (http://mallet.cs.umass.edu/) 

* Apache Mahout (https://mahout.apache.org/)

* Deeplearning4j  (https://deeplearning4j.org/)

and some others


Overview
=========

There are 3 modules with ML examples and one auxiliary module for data loading from CSV files

/data - this directory contains sample data:

/data/book-ratings.zip, /data/books.zip, /data/users.zip - zipped csv files to test recommendation system, contains data about 200k books and users' preferences

(use ```docker-compose up --build``` to create a db container)

/data/bbc, /data/stoplists - datasets for text analysing tools

/ml-data-tools - a dataloader for csv files, can be used together with examples from other modules as a data source for recommendation system.

/ml-deep-learning - an example of implementation of Neural Network 

/ml-recommendation-system - a simple recommendation system built on the basis of Apache Mahout

/ml-text - text analysing tool-kit


Building and Running the code
==============================

## Prerequisites:

You will need:
 * git
 * Java 8+  
 * Docker (optional)

## Installation notes

Download the latest WEKA binary from https://www.cs.waikato.ac.nz/ml/weka/

Optional:  on the latest versions of java (11+) there will be necessary to add tool.jar due to chain dependencies in Apache Mahout:

```
mvn install:install-file -DgroupId=jdk.tools -DartifactId=jdk.tools -Dpackaging=jar -Dversion=1.7 -Dfile=tools.jar -DgeneratePom=true
```

References
===========

https://en.wikipedia.org/wiki/International_Standard_Book_Number





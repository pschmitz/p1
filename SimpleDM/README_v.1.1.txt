===========================================================
 									
 		             SimpleDM						
 									
 		Illinois Institute of Technology
		Computer Science Department
		Information Retrieval Laboratory
		
 		CS422 Introduction To Data Mining			
 		Instructor: Dr. Nazli Goharian				
 									
 		Copyright (c) 2003					
 		@author		Mi Sun Chung				
 		@version	1.1					

SimpleDM software can only be used for the educational 
purpose and not otherwise. 									
 ==========================================================		
 

SimpleDM Objective:
-------------------
This software provides the students taking Introduction to data Mining course (CS422) 
a Data Mining shell to practice the implementation of different data preprocessing 
methods and data mining algorithms. Currently, SimpleDM contains several preprocessing
techniques and one of the data mining algorithms called Support Vector Machine (SVM).
The students will be asked to extend the software to incorporate more data preprocessing
techniques and data mining algorithms, run experiments, and measure the accuracy of the 
results.


Contents:
----------

1. How to compile and run 

2. Getting started 
   (Try SVM model with 'adult.arff' data file)
      
      - Opens a file
      - Identifies the types of attributes
      - Selects the options of preprocessing 
      - Builds and tests a model


3. How to extend a model

4. Source code

5. Bug reports



------------------------------------------------------------------

1. How to compile and run
-------------------------

In order to compile the source codes, click the 'makefile.bat' file 
in the SimpleDM folder. It will compile all packages. If you add more 
packages, you should add the path of the package in the batch file.

In order to run the program, click the 'SimpleDM.bat' file 
in the SimpleDM folder.


* If you encountered some problems installing SimpleDM at a lab, 
try the following instructions which were reported by a student.

(1)Registry Key: The registry key value for jvm (version of jvm) 
in most of the CNS labs is 1.4. SimpleDM seems works 
only with version 1.3 of jvm (or at least it verifies the version 
in registry and throws an error). Thus, following error occurred 
while initial installing:
'Software/Java Soft/Java Runtime Environment\Current Version'
has value '1.4', but '1.3' is required.
Error : could not find java.dll
Error : could not find Java 2 Runtime Environment.

Solution: 
Go to windows registry and update the value of version 
for the key ¡®Software\JavaSoft\Java Runtime Environment



(2)CLASSPATH: The class path was not set to default \bin directory 
on the machine. Moreover, access to environmental variables 
from windows -> settings is restricted on CNS machines.

Solution:
Modify the CLASSPATH variable to c:\j2sdk1.4\bin (as applicable) from DOS prompt.
Commands:
CLASSPATH=%CLASSPATH%;c:\j2sdk1.4\bin (as applicable)
Path=c:\j2sdk1.4\bin (as applicable).




-----------------------------------------------------------------

2. Getting started
------------------

There are four panels in the main frame.
  

Setting Panel
Preprocessing Panel
Building and Testing Panel
Logger Panel

Logger Panel will show the status of processing.
At the start of the program, only 'Open' button is activated.


=========================
(1) Open a File
=========================

Try:

Choose 'adult.arff' data file in the data folder.
Then AttributeTypesPanel will be activated, and display
the name and type of each attribute.

NOTE: The data file should be 'ARFF'-formatted, which contains
the relation name and the name and type of each attribtue
in its header, as well as the data records.


=======================================
(2) Identify the Types of Attributes
=======================================

The initial types of the attributes are set using the header of
the data file. Only "categorical" and "continuous" types will be
activated. "Ordinal" type is not activated at this time.
(If you choose 'Clustering' model, the column of ordinal type will
be activated. And you have to click the 'Confirm' button on the SettingPanel
to save the changes.)

You can change the types from one type to the other. However,
if the value is non-numerical, it cannot become a continuous type.

Usually you are not recommended to change the types, since the header
is supposed to have the correct types of attributes.

When you click the 'Confirm' button, the program will make statistics
for each continuous attribute and estimate the distribution of 
labels for each categorical attribute. The results will be displayed 
on the dos window and saved into AttributeStats and ItemInfo class.
If the data file is large, it will take several seconds.

For the continuous attributes, the program will discretize them,
and save the categories into the SimpleDataDiscretizer. This is for 
transforming from continuous type to categorical. 
The discretization information will be displayed on the
dos window,too.*




* Discretization *

DataDiscretizer segments numeric data into several categories. 
SimpleDataDiscretizer extends DataDiscretizer and categorizes 
the numeric value with simple method.

SimpleDataDiscretizer uses minimum, maximum, average and standard deviation,
and it separates the numeric data into the following five or less labels. 

lower  : [minimum]                          ~ [average - ( 2 * standard deviation )]
low    : [average - (2*standard deviation)] ~ [average - standard deviation]
middle : [average - standard deviation]     ~ [average + standard deviation]
high   : [average + standard deviation]     ~ [average + (2*standard deviation)]
higher : [average + (2*standard deviation)] ~ [maximum]

The labels and seperators will be stored into SimpleDataDiscretizer, 
and used later when the Preprocessor transform the type of attributes 
from continuous to categorical. 

To add more sophisticated categorization, another discretizer 
can be implemented by extending DataDiscretizer class. 



========================================
(3) Select the Options of preprocessing 
========================================

Try:

For SVM, 

a. Check 'Normalize Continous Values' and select 'MIN_MAX' option.
   This option is essential for SVM model.

b. Check 'Transform the type of attributes' and select 'Categoric to Continous' option.*

c. Check 'Avoid Over-Fitting'. If a user doesn't check this function for SVM and adult.arff file,
   it will take very long time or out of memory. It is because the number 
   of support vectors become too large to fit in memory. 

('Replace Missing Value' option is not implemented for the students, as the students are asked
 to implerment that as part of their assignment.)


'Set Options of Preprocessing' button saves the selected 
options in the Preprocessor class. Later when building and testing
a model, only selected preprocessing will be processed to a SampleSet.



* Transformation of the type from categorical to continuous*

Each label will be numbered using the index of a list of labels, 
which are stored in ItemInfo object of the attribute. 



============================
(4) Build and Test a Model
============================

Try:

Choose the "SVM" model in the list of models.

Click 'Option' panel, where the user inputs the parameters. For SVM, the parameters
are "learning rate", "kernal function", and the "function parameter".*
If you don't click the 'Option' button, the default parameter values
will be passed to SVM. 

'Option' button activates the 'ParamPanel' frame and shows the default
parameter values. Those default values are set for the 'adult.arff' file
and three-fold of cross validation.
You can change the parameter values and the number of folds
to see if the accuracy increases or not.

Click 'Start' button to start building the SVM model with the data file.
It will take a couple of minutes to finish the three-fold. Three-fold
is used to test the SVM model.




* Parameters for SVM *

For SVM, the user should try different kernel functions, its parameters,
and learning rate to produce the best classification. Good results will
be depend on good parameter setting.


1. Learning rate: 
Learning rate is typically between 0.0 and 1.0. It can affects the accuracy.

2. Kernel Functions:
A kernel is a function that returns the value of the dot product 
between the images of the two arguments. And its parameter 
will find the maximal margin hyperplane in a chosen kernel-induced feature space.
Two functions, Polynomial and Radio Basis functions were implemented.
Try differnt function and its parameters to find the appropriate setting.



-----------------------------------------------------------------

3. How to extend a model
------------------------

See 'How to extend a model' file.



-----------------------------------------------------------------

4. Source code
--------------

The source code for SimpleDM is in src/edu/iit/ir/simpledm.



-----------------------------------------------------------------

5. Bug reports
--------------

Please send a fix or a bug report to chunmi@iit.edu.
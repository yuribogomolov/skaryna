## Welcome to Project Skaryna

Here I'm going to experiment with new features of Apache Spark, and document all results/outcomes. I picked GitHub format for two simple reasons:
1. Keep history of code changes, and provide reproducible examples
2. Provide an easy way to reuse created utility functions.

## Spark SQL extensions

This section is focused on Spark SQL extensions. A more powerful version of this feature was released in Spark 2.2, but I couldn't find any documentation that cover the details, and provide examples. 

Let's take a look at the Catalyst architecture:
![DataFrames in Spark](https://user-images.githubusercontent.com/11829125/31749950-f79cc2fc-b44a-11e7-8102-598ee7f90eb5.png)

Spark SQL has two interfaces:
1. DataFrame API
2. SQL queries

and both of them are transformed into a logical plan. Let's list the main phases:
1. Logical plan analyzis
2. Logical plan optimization
3. Conversion into a Physical Plan (SparkPlan) 
4. Conversion into RDD operations

Spark extensions allow to customize all major phases: 
![Spark SQL extensions](https://user-images.githubusercontent.com/11829125/31751528-d3b9ff4a-b453-11e7-9d9e-29754bbfc45a.png)


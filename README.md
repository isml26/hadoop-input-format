## EXAMPLE COMMANDS

#### localhost:9870 for browser interface
* hdfs dfs -mkdir, No such file or directory -- Hadoop copyFromLocal: '.': No such file or directory
* hdfs dfs -mkdir -p /user/Hadoop/twitter_data 

* https://medium.com/@pedro.a.hdez.a/hadoop-3-2-2-installation-guide-for-windows-10-454f5b5c22d3

* https://issues.apache.org/jira/browse/YARN-8246

* hdfs dfsadmin -safemode leave

* hadoop fs -mkdir /input
* hadoop fs -rm -r /output
* hadoop fs -put C:\Users\Dell\Desktop\OpenSans-Regular.bin \input
* hadoop fs -put C:\Users\Dell\Desktop\example.txt \input2

* hdfs dfsadmin -report
* yarn node -list
* jps
* hadoop jar C:\Users\Dell\Desktop\binarymapper.jar com/demo/deneme/BinaryFileProcessing2 /input /output
* hadoop jar C:\Users\Dell\Desktop\hadoop-mapreduce.jar wordcount /input2 /output2



* -- worker          --master  (add history server?)
* hadoop datanode  |  hadoop namenode
* yarn nodemanager |  yarn resourcemanager

* In newer versions of Hadoop, the "workers" file has replaced the "slaves" file

#### format3 -> read 10bytes convert them to hex string and perform map-reduce

<hr>

### By using below command inspect result
* hadoop fs -cat /output/part-r-00000
<hr>

### By using below command copy to current dir
* hadoop fs -cat /output/part-r-00000 > output.txt
<hr>

### To download a file from the Hadoop Distributed File System (HDFS) to your local PC in the Hadoop ecosystem, you can use the hadoop fs -get command. Here's how to do it:
* hadoop fs -get /hdfs/path/to/output.txt local/path/to/output.txt
<hr>

### To inspect the content using the vi text editor, you can pipe the output of the hadoop fs -cat command into vi. Here's how you can do it:
* hadoop fs -cat /output/part-r-00000 | vi -
<hr>

### Example flow of to execute job
* hadoop fs -mkdir /input
* hadoop fs -put OpenSans-Regular.bin /input/
* hadoop fs -ls /input
* hadoop jar binarymapper.jar demo/BinaryFileProcess /input /output


<hr>

## Table of Contents
- [RecordReader](#recordreader)
- [Mapper](#mapper)
- [Reducer](#reducer)


### RecordReader

The RecordReader class is responsible for reading the input data and converting it into key-value pairs. The RecordReader class is specific to the input format that is being used. For example, the TextInputFormat class uses a LineRecordReader class to read the input data line by line.

### Mapper

 The Mapper class is responsible for processing the key-value pairs from the RecordReader class and producing a new set of key-value pairs. The Mapper class can perform any type of processing on the input data, such as filtering, aggregating, or transforming the data.

### Reducer

The Reducer class is responsible for aggregating the key-value pairs from the Mapper class and producing the final output. The Reducer class typically performs a single operation on the input data, such as counting the number of occurrences of each key or finding the maximum value for each key.



 +----------------+
                               |  RecordReader  |
                               +----------------+
                                    |
                                    |
                                    V
                       +---------------------------+
                       |                           |
                       |            Mapper            |
                       |                           |
                       +---------------------------+
                                    |
                                    |
                                    V
                       +---------------------------+
                       |                           |
                       |            Reducer            |
                       |                           |
                       +---------------------------+
## EXAMPLE COMMANDS


-- hdfs dfs -mkdir, No such file or directory -- Hadoop copyFromLocal: '.': No such file or directory
hdfs dfs -mkdir -p /user/Hadoop/twitter_data 

https://medium.com/@pedro.a.hdez.a/hadoop-3-2-2-installation-guide-for-windows-10-454f5b5c22d3

https://issues.apache.org/jira/browse/YARN-8246

hdfs dfsadmin -safemode leave


hadoop fs -mkdir /input
hadoop fs -rm -r /output
hadoop fs -put C:\Users\Dell\Desktop\OpenSans-Regular.bin \input
hadoop fs -put C:\Users\Dell\Desktop\example.txt \input2

hdfs dfsadmin -report
yarn node -list
jps
hadoop jar C:\Users\Dell\Desktop\binarymapper.jar com/demo/deneme/BinaryFileProcessing2 /input /output

hadoop jar C:\Users\Dell\Desktop\hadoop-mapreduce.jar wordcount /input2 /output2



-- worker          --master  (add history server?)
hadoop datanode  |  hadoop namenode
yarn nodemanager |  yarn resourcemanager

In newer versions of Hadoop, the "workers" file has replaced the "slaves" file


hadoop fs -mkdir /input
hadoop fs -ls /input
hadoop fs -put OpenSans-Regular.bin /input/
hadoop jar binarymapper.jar com/demo/deneme/BinaryFileProcessing2 /input /output
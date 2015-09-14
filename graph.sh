#!/bin/bash
# Licensed to uve!

base=$(dirname $0)
base_dir="$base/target"
GC_FILE_SUFFIX='-gc.log'
GC_LOG_FILE_NAME="graph$GC_FILE_SUFFIX"
GC_LOG_OPTS="-Xloggc:$base_dir/$GC_LOG_FILE_NAME -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps "

for file in $base_dir/locallib/*.jar;
do
  CLASSPATH=$CLASSPATH:$file
done
CLASSPATH=$CLASSPATH:$base_dir/graph-0.0.1-SNAPSHOT.jar
MAINCLASS="com.wbuve.graph.main.GraphMain"
GRAPH_HEAP_OPTS="-Xmx4G"
GRAPH_JVM_PERFORMANCE_OPTS="-server -XX:+UseG1GC"
exec java $GRAPH_HEAP_OPTS $GRAPH_JVM_PERFORMANCE_OPTS $GC_LOG_OPTS -cp $CLASSPATH $MAINCLASS 

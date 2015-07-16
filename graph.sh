base_dir="/usr/home/yangyang21/graphuve/target"
GC_FILE_SUFFIX='-gc.log'
GC_LOG_FILE_NAME="graph$GC_FILE_SUFFIX"
GC_LOG_OPTS="-Xloggc:$basedir/$GC_LOG_FILE_NAME -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps "

for file in $base_dir/locallib/*.jar;
do
  CLASSPATH=$CLASSPATH:$file
done
CLASSPATH=$CLASSPATH:$base_dir/graph-0.0.1-SNAPSHOT.jar
MAINCLASS="com.uve.graph.main.GraphMain"
KAFKA_HEAP_OPTS="-Xmx2G"
KAFKA_JVM_PERFORMANCE_OPT="-server -XX:+UseG1GC"
exec java $KAFKA_HEAP_OPTS $KAFKA_JVM_PERFORMANCE_OPTS $GC_LOG_OPTS -cp $CLASSPATH $MAINCLASS 

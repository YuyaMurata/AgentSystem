#Setting CLASS PATH
 #Program setting file
export CLASSPATH=$CLASSPATH:../property:../resource:../library/pool/commons-pool2-2.4.2.jar
 #Log setting file
export CLASSPATH=$CLASSPATH:../library/Log/logback-access-1.1.3.jar:../library/Log/logback-classic-1.1.3.jar:../library/Log/logback-core-1.1.3.jar:../library/Log/slf4j-api-1.7.12.jar
 #CSV setting file
export CLASSPATH=$CLASSPATH:../library/csv/opencsv-3.5.jar

java -cp $CLASSPATH $1
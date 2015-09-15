export CLASSPATH=$CLASSPATH:../property:../resource:../library/Log/logback-access-1.1.3.jar:../library/Log/logback-classic-1.1.3.jar:../library/Log/logback-core-1.1.3.jar:../library/Log/slf4j-api-1.7.12.jar

vmstat -n -S m -a 1 | awk '{print strftime("%H:%M:%S"), $0} { system(":") }' >> vmstat.log &
java -verbose:gc -cp $CLASSPATH rda.main.Main

killall vmstat

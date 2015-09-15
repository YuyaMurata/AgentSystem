ps -A | grep java | awk '{print $1}' > jps-tmp
jps_exec=$(tail -n 1 jps-tmp)
ps -p $jps_exec
jstack $jps_exec > exec_threaddump.txt
rm -f jps-tmp

#Setting CLASS PATH
 #Program setting file
export CLASSPATH=$CLASSPATH:../library/pool/commons-pool2-2.4.2.jar

echo $1

case "$1" in
  "CreateAgentTest" ) echo "Execute Java Program";;
esac

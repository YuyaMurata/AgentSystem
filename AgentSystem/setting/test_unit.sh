#Setting CLASS PATH
 #Program setting file
export CLASSPATH=$CLASSPATH:../library/pool/commons-pool2-2.4.2.jar



case $0 in
  1)
    echo "[INFO] enter numric value is 1";;
  [23])
    echo "[INFO] enter numric value is 2 or 3";;
  [4-9])
    echo "[INFO] enter numric value is [4-9]";;
  10)
    echo "[INFO] enter numric value is 10";;
  *)
    echo "[ERR] invalid value";;
esac

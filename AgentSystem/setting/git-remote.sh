git pull

sleep 1

while getopts f OPT
do
  case $OPT in
    "f" ) FLG_F="TRUE" ;;
  esac
done

if [ "$FLG_F" = "TRUE" ]; then
    echo "クラスの削除"
    rm -fR $CETA_HOME/classes/rda
    rm -fR $CETA_HOME/classes/test
fi

ant -f ../build.xml
\cp -fr ../build/classes/* $CETA_HOME/classes

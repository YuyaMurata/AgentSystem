git pull

sleep 1

while getopts f OPT
do
  case $OPT in
    "f" ) FLG_F="TRUE" ;;
  esac
done

if ["$FLG_F" = "TRUE"]; then
    rm -fr $CETA_HOME/classes/rda
    rm -fr $CETA_HOME/classes/test
fi

ant -f ../build.xml
\cp -fr ../build/classes/* $CETA_HOME/classes

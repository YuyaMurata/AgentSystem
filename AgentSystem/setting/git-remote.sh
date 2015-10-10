git pull

sleep 1

if [$1 = "f"]; then
    rm -fr $CETA_HOME/classes/*
fi

ant -f ../build.xml
\cp -fr ../build/classes/* $CETA_HOME/classes

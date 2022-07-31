# Get current PWD
CURRENT_DIR="$(pwd)"
echo "Packaging library..."
cd $CURRENT_DIR
mvn package
echo "Installing library..."
# Maven install command fixed for windows path
mvn install:install-file -Dfile="$(cygpath -w $CURRENT_DIR)\target\java-smartctl-1.0-SNAPSHOT.jar" -DgroupId=com.smartctl -DartifactId=smartctl -Dversion=1.0-SNAPSHOT -Dpackaging=jar

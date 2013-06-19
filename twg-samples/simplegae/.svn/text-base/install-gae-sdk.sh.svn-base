export GAE_VERSION=1.5.2

echo "Going to install AppEngine dependencies."

# Install appengine-local-runtime
mvn install:install-file -DgroupId=com.google.appengine -DartifactId=appengine-local-runtime -Dversion=${GAE_VERSION} -Dpackaging=jar -Dfile=./files/appengine-local-runtime-${GAE_VERSION}.jar
mvn install:install-file -DgroupId=com.google.appengine -DartifactId=appengine-local-runtime -Dversion=${GAE_VERSION} -Dpackaging=pom -Dfile=./files/appengine-local-runtime-${GAE_VERSION}.pom

echo "Done."


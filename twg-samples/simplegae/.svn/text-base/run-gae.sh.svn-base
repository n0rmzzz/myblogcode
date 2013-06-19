#!/bin/sh

gaehome=~/.m2/repository/com/google/appengine/appengine-java-sdk/1.5.2/appengine-java-sdk-1.5.2
echo "GAE Home: $gaehome"
if [ ! -d "$gaehome" ]; then
	echo "Please download appengine-java-sdk-1.5.2 and unpacked in the ${gaehome} path."
	echo "You can modify this script to specify where you have extracted your SDK, if it's somewhere else."
	exit
fi

mvn -Dmaven.test.skip=true install
cd simplegae-appengine
mvn -Dmaven.test.skip=true -Dgae.home=$gaehome gae:run
cd -


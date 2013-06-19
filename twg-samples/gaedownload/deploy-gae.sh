#!/bin/sh

mvn -Dmaven.test.skip=true install
cd gaedownload-appengine
mvn gae:deploy
cd -


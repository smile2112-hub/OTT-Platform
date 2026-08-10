#!/bin/sh
# ----------------------------------------------------------------------------
# Maven Wrapper
# ----------------------------------------------------------------------------
set -e

DIRNAME="`dirname "$0"`"
BASEDIR="`cd "$DIRNAME" && pwd`"
WRAPPER_JAR="$BASEDIR/.mvn/wrapper/maven-wrapper.jar"

if [ -f "$WRAPPER_JAR" ]; then
  exec java -Dmaven.multiModuleProjectDirectory="$BASEDIR" -cp "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
else
  echo "Maven wrapper JAR not found: $WRAPPER_JAR"
  exit 1
fi

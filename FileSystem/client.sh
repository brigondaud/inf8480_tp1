#!/bin/bash

# Launch the client

pushd $(dirname $0) > /dev/null
basepath=$(pwd)
popd > /dev/null

for arg in "$@"
do
case $arg in
    --ip=*)
    IPADDR="${arg#*=}"
    shift
    ;;
    *)
    ;;
esac
done

if [ -z "$IPADDR" ]
  then
    IPADDR="127.0.0.1"
fi

java -cp "$basepath"/client.jar:"$basepath"/shared.jar \
  -Djava.security.policy="$basepath"/policy \
  client.Client "$IPADDR" $*

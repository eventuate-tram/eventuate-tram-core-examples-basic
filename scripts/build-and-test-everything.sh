#! /bin/bash

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

set -e -o pipefail

mkdir -p build

LOG_FILE=build/build-and-test-everything.log

date > $LOG_FILE

for script in ${DIR?}/build-and-test-all*.sh ; do
   echo '****************************************** Running' $script
   date >> $LOG_FILE
   echo '****************************************** Running' $script >> $LOG_FILE
   $script | tee -a $LOG_FILE
done

echo 'Finished successfully!!!'

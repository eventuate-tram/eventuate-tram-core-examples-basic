#! /bin/bash

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

set -e -o pipefail

date > build-and-test-everything.log

for script in ${DIR?}/build-and-test-all*.sh ; do
   echo '****************************************** Running' $script
   date >> build-and-test-everything.log
   echo '****************************************** Running' $script >> build-and-test-everything.log
   $script | tee -a build-and-test-everything.log
done

echo 'Finished successfully!!!'

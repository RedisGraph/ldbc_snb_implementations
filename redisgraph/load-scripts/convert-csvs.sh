#!/bin/bash

echo "starting preprocessing"
source ./../environment-variables-redisgraph.sh

echo ${REDISGRAPH_DATA_DIR}

# replace headers
while read line; do
  IFS=' ' read -r -a array <<< $line
  filename=${array[0]}
  header=${array[1]}
  echo "reading ${filename}"
  sed -i.bkp "1s/.*/$header/" "${REDISGRAPH_DATA_DIR}/${filename}${POSTFIX}"
done < headers.txt

echo "preprocessing finished"

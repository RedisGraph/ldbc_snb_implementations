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

# replace labels with one starting with an uppercase letter
sed -i.bkp "s/|city$/|City/" "${REDISGRAPH_DATA_DIR}/static/place${POSTFIX}"
sed -i.bkp "s/|country$/|Country/" "${REDISGRAPH_DATA_DIR}/static/place${POSTFIX}"
sed -i.bkp "s/|continent$/|Continent/" "${REDISGRAPH_DATA_DIR}/static/place${POSTFIX}"
sed -i.bkp "s/|company|/|Company|/" "${REDISGRAPH_DATA_DIR}/static/organisation${POSTFIX}"
sed -i.bkp "s/|university|/|University|/" "${REDISGRAPH_DATA_DIR}/static/organisation${POSTFIX}"

# convert each date of format yyyy-mm-dd to a number of format yyyymmddd
sed -i.bkp "s#|\([0-9][0-9][0-9][0-9]\)-\([0-9][0-9]\)-\([0-9][0-9]\)|#|\1\2\3|#g" "${REDISGRAPH_DATA_DIR}/dynamic/person${POSTFIX}"

# convert each datetime of format yyyy-mm-ddThh:mm:ss.mmm+0000
# to a number of format yyyymmddhhmmssmmm
sed -i.bkp "s#|\([0-9][0-9][0-9][0-9]\)-\([0-9][0-9]\)-\([0-9][0-9]\)T\([0-9][0-9]\):\([0-9][0-9]\):\([0-9][0-9]\)\.\([0-9][0-9][0-9]\)+0000#|\1\2\3\4\5\6\7#g" ${REDISGRAPH_DATA_DIR}/dynamic/*${POSTFIX}

# removing .bkp files
rm ${REDISGRAPH_DATA_DIR}/*/*.bkp

echo "preprocessing finished"

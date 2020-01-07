#!/bin/bash

echo "starting preprocessing"
source ./../environment-variables-redisgraph.sh

echo ${REDISGRAPH_DATA_DIR}

# Replace headers
while read line; do
  IFS=' ' read -r -a array <<< $line
  filename=${array[0]}
  header=${array[1]}
  sed -i.bkp "1s/.*/$header/" "${REDISGRAPH_DATA_DIR}/${filename}${POSTFIX}"
done < headers.txt

# convert each date of format yyyy-mm-dd to a number of format yyyymmddd
sed -i.bkp "s#|\([0-9][0-9][0-9][0-9]\)-\([0-9][0-9]\)-\([0-9][0-9]\)|#|\1\2\3|#g" "${REDISGRAPH_DATA_DIR}/dynamic/person${POSTFIX}"

# convert each datetime of format yyyy-mm-ddThh:mm:ss.mmm+0000
# to a number of format yyyymmddhhmmssmmm
sed -i.bkp "s#|\([0-9][0-9][0-9][0-9]\)-\([0-9][0-9]\)-\([0-9][0-9]\)T\([0-9][0-9]\):\([0-9][0-9]\):\([0-9][0-9]\)\.\([0-9][0-9][0-9]\)+0000#|\1\2\3\4\5\6\7#g" ${REDISGRAPH_DATA_DIR}/dynamic/*${POSTFIX}

# Write each label to a separate file
echo "id:ID(Place)|name:STRING|url:STRING" > ${REDISGRAPH_DATA_DIR}/static/City.csv
echo "id:ID(Place)|name:STRING|url:STRING" > ${REDISGRAPH_DATA_DIR}/static/Country.csv
echo "id:ID(Place)|name:STRING|url:STRING" > ${REDISGRAPH_DATA_DIR}/static/Continent.csv
echo "id:ID(Organisation)|name:STRING|url:STRING" > ${REDISGRAPH_DATA_DIR}/static/Company.csv
echo "id:ID(Organisation)|name:STRING|url:STRING" > ${REDISGRAPH_DATA_DIR}/static/University.csv
awk -v REDISGRAPH_DATA_DIR=$REDISGRAPH_DATA_DIR 'BEGIN { FS="|"; OFS="|";} FNR > 1 {LABEL=toupper(substr($4,1,1))substr($4,2); print $1, $2, $3 >> REDISGRAPH_DATA_DIR"/static/"LABEL".csv" }' ${REDISGRAPH_DATA_DIR}/static/place${POSTFIX}
awk -v REDISGRAPH_DATA_DIR=$REDISGRAPH_DATA_DIR 'BEGIN { FS="|"; OFS="|";} FNR > 1 {LABEL=toupper(substr($2,1,1))substr($2,2); print $1, $3, $4 >> REDISGRAPH_DATA_DIR"/static/"LABEL".csv" }' ${REDISGRAPH_DATA_DIR}/static/organisation${POSTFIX}

echo "preprocessing finished"

# removing .bkp files
rm ${REDISGRAPH_DATA_DIR}/*/*.bkp

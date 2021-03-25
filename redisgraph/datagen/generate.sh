
#!/bin/bash
set -e
source ./environment-variables-redisgraph.sh

echo "Using data dir ${REDISGRAPH_DATA_DIR}"
mkdir -p $REDISGRAPH_DATAGEN_DIR
CURRENT=`pwd`
cd $REDISGRAPH_DATAGEN_DIR
rm -rf social_network/ substitution_parameters
docker run --rm --mount type=bind,source="$(pwd)/",target="/opt/ldbc_snb_datagen/out" \
                --mount type=bind,source="$PARAMS_FILE",target="/opt/ldbc_snb_datagen/params.ini" \
                ldbc/datagen:${LDB_DATAGEN_DOCKER_VERSION} 
sudo chown -R $USER:$USER social_network/ substitution_parameters/
echo "Finished generating data to ${REDISGRAPH_DATA_DIR}"
cd $CURRENT
echo "Converting the csv ${REDISGRAPH_DATA_DIR}"
./convert-csvs.sh
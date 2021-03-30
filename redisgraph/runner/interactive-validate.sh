#!/bin/bash

#!/bin/bash
set -x
set -e
DATABASE_HOST=${DATABASE_HOST:-"localhost"}
PREFIX=${PREFIX:-""}
DATABASE_PORT=${DATABASE_PORT:-6379}
REPETITIONS=${REPETITIONS:-3}
SLEEP_BETWEEN_RUNS=${SLEEP_BETWEEN_RUNS:-60}
CLIENTS=${CLIENTS:-1}
RESULTS_DIR=${RESULTS_DIR:-"./results"}
TARGET="../target"
source ./environment-variables-redisgraph.sh

run=1
mkdir -p ${RESULTS_DIR}

OUT_FULL_FILE_NAME="${RESULTS_DIR}/interactive-validate_out.txt"

java -classpath "$TARGET/redisgraph-0.4.0-SNAPSHOT.jar" \
com.ldbc.driver.Client -dm EXECUTE_WORKLOAD -P interactive-validate.properties \
 -p thread_count ${CLIENTS} \
 -p ldbc.snb.interactive.parameters_dir ${REDISGRAPH_DATAGEN_DIR}/ldbc_snb_datagen/substitution_parameters/ \
 -p ldbc.snb.interactive.updates_dir ${REDISGRAPH_DATAGEN_DIR}/ldbc_snb_datagen/social_network/ \
 -p host ${DATABASE_HOST} -p port ${DATABASE_PORT} \
 -p queryDir ${QUERIES_DIR} \
 2>&1 | tee ~/${OUT_FULL_FILE_NAME}

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

mkdir -p ${RESULTS_DIR}

for run in $(seq ${REPETITIONS}); do
  echo "Running RUN $run"
  echo "Saving results to ${OUT_FULL_FILE_NAME}"
  OUT_FULL_FILE_NAME="${RESULTS_DIR}/SF-${SF}_${CLIENTS}-clients_${PREFIX}run-${run}.txt"

  java -classpath "$TARGET/redisgraph-0.4.0-SNAPSHOT.jar" \
  com.ldbc.driver.Client -dm EXECUTE_WORKLOAD -P interactive-benchmark-SF${SF}.properties \
   -p thread_count ${CLIENTS} \
   -p ldbc.snb.interactive.parameters_dir ${REDISGRAPH_DATAGEN_DIR}/ldbc_snb_datagen/substitution_parameters/ \
   -p ldbc.snb.interactive.updates_dir ${REDISGRAPH_DATAGEN_DIR}/ldbc_snb_datagen/social_network/ \
   -p host ${DATABASE_HOST} -p port ${DATABASE_PORT} \
   2>&1 | tee ~/${OUT_FULL_FILE_NAME}

  echo "ended benchmark" >> ~/${OUT_FULL_FILE_NAME}
  echo $(date) >> ~/${OUT_FULL_FILE_NAME}

  echo "Sleeping for ${SLEEP_BETWEEN_RUNS} seconds"
  sleep ${SLEEP_BETWEEN_RUNS}

done

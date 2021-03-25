#!/bin/bash
source ./environment-variables-redisgraph.sh
set -x 
DATABASE_HOST=${DATABASE_HOST:-"localhost"}
PREFIX=${PREFIX:-""}
DATABASE_PORT=${DATABASE_PORT:-6379}
REPETITIONS=${REPETITIONS:-1}
SLEEP_BETWEEN_RUNS=${SLEEP_BETWEEN_RUNS:-60}
CLIENTS=${CLIENTS:-1}

for run in $(seq ${REPETITIONS}); do
  echo "Running RUN $run"
  echo "reseting status"
  RESULTS_DIR="results/SF_1_${CLIENTS}CLIENTS_results/${PREFIX}run_${run}"
  redis-cli -h ${DATABASE_HOST} -p ${DATABASE_PORT} config resetstat

  OUT_FULL_FILE_NAME="${PREFIX}SF_1_${CLIENTS}CLIENTS_result_run_${run}.out"
  echo "Saving results to ${OUT_FULL_FILE_NAME}"
  mkdir -p ~/${RESULTS_DIR}

  java -classpath "target/redisgraph-0.4.0-SNAPSHOT.jar" com.ldbc.driver.Client -P interactive-benchmark-SF1.properties \
   -p thread_count ${CLIENTS} \
   -p host ${DATABASE_HOST} -p port ${DATABASE_PORT} \
   2>&1 | tee ~/${OUT_FULL_FILE_NAME}

  echo "ended benchmark" >> ~/${OUT_FULL_FILE_NAME}
  echo $(date) >> ~/${OUT_FULL_FILE_NAME}
  # Retrieve command stats output
  redis-cli -h ${DATABASE_HOST} -p ${DATABASE_PORT} info commandstats >> ~/${OUT_FULL_FILE_NAME}
  redis-cli -h ${DATABASE_HOST} -p ${DATABASE_PORT} info >> ~/${OUT_FULL_FILE_NAME}
  cp -avr ./results ~/${RESULTS_DIR}
  echo "Sleeping for ${SLEEP_BETWEEN_RUNS} seconds"
  sleep ${SLEEP_BETWEEN_RUNS}

done

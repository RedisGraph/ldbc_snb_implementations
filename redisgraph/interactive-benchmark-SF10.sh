#!/bin/bash
source ./environment-variables-redisgraph.sh

DATABASE_HOST=${DATABASE_HOST:-"localhost"}
PREFIX=${PREFIX:-""}
DATABASE_PORT=${DATABASE_PORT:-6379}
REPETITIONS=${REPETITIONS:-3}
SLEEP_BETWEEN_RUNS=${SLEEP_BETWEEN_RUNS:-60}

for run in $(seq ${REPETITIONS}); do
  echo "Running RUN $run"
  echo "reseting status"
  redis-cli -h ${DATABASE_HOST} -p ${DATABASE_PORT} config resetstat

  OUT_FULL_FILE_NAME="${PREFIX}SF_10_result_run_${run}.out"
  echo "Saving results to ${OUT_FULL_FILE_NAME}"
  mkdir -p ~/results/SF_1_results/${PREFIX}run_${run}

  java -classpath "target/redisgraph-0.4.0-SNAPSHOT.jar" com.ldbc.driver.Client -P interactive-benchmark-SF10.properties 2>&1 | tee ~/${OUT_FULL_FILE_NAME}

  echo "ended benchmark" >> ~/${OUT_FULL_FILE_NAME}
  echo $(date) >> ~/${OUT_FULL_FILE_NAME}
  # Retrieve command stats output
  redis-cli -h ${DATABASE_HOST} -p ${DATABASE_PORT} info commandstats >> ~/${OUT_FULL_FILE_NAME}
  redis-cli -h ${DATABASE_HOST} -p ${DATABASE_PORT} info >> ~/${OUT_FULL_FILE_NAME}
  cp -avr ./results ~/results/SF_10_results/${PREFIX}run_${run}
  echo "Sleeping for ${SLEEP_BETWEEN_RUNS} seconds"
  sleep ${SLEEP_BETWEEN_RUNS}

done

#!/bin/bash
#
GRAPHNAME=${GRAPHNAME:-"graph"}
POSTFIX=${POSTFIX:-"_0_0.csv"}
LDB_DATAGEN_DOCKER_VERSION=0.3.2
SF=${SF:-"0.1"}
CURRENT_DIR=$(pwd)
PARAMS_FILE=${PARAMS_FILE:-"${CURRENT_DIR}/params-csv-basic-SF${SF}.ini"}
REDISGRAPH_DATAGEN_DIR=$(dirname $(readlink -e ${REDISGRAPH_DATAGEN_DIR:-"${CURRENT_DIR}/../datagen/SF${SF}/ldbc_snb_datagen/."}))
#$(readlink -e ../../../../etc/passwd))
REDISGRAPH_DATA_DIR=${REDISGRAPH_DATA_DIR:-"${REDISGRAPH_DATAGEN_DIR}/social_network"}
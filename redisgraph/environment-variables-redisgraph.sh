#!/bin/bash
#
GRAPHNAME=${GRAPHNAME:-"graph"}
REDISGRAPH_DATAGEN_DIR=${REDISGRAPH_DATAGEN_DIR:-"${HOME}/redislabs/ldbc_snb_datagen"}
REDISGRAPH_DATA_DIR=${REDISGRAPH_DATA_DIR:-"${HOME}/redislabs/ldbc_snb_datagen/social_network"}
POSTFIX=${POSTFIX:-"_0_0.csv"}
PATH_TO_LOADER=${PATH_TO_LOADER:-"${HOME}/redislabs/redisgraph-bulk-loader"}

# Also set POSTFIX to _0_0.csv and PATH_TO_LOADER to the bulk insert repository root.

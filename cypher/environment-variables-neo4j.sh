#!/bin/bash

NEO4J_HOME=${NEO4J_HOME:-"${HOME}/redislabs/ldbc_snb_implementations/cypher/neo4j-server"}
NEO4J_DB_DIR=${NEO4J_DB_DIR:-"${NEO4J_HOME}/data"}
DATAGEN_DIR=${DATAGEN_DIR:-"${HOME}/redislabs/ldbc_snb_datagen"}
NEO4J_DATA_DIR=${NEO4J_DATA_DIR:-"${DATAGEN_DIR}/social_network"}
POSTFIX=${POSTFIX:-"_0_0.csv"}

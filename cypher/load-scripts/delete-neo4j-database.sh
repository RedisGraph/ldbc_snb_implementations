#!/bin/bash

source ./../environment-variables-neo4j.sh

$NEO4J_HOME/bin/neo4j stop
echo "removing $NEO4J_DB_DIR"
rm -rf $NEO4J_DB_DIR

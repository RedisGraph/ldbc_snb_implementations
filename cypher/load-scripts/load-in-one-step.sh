#!/bin/bash

./stop-neo4j.sh && ./convert-csvs.sh && ./import-to-neo4j.sh && ./restart-neo4j.sh

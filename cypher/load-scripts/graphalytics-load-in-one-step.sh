#!/bin/bash

./stop-neo4j.sh && ./convert-csvs.sh && ./graphalytics-import-to-neo4j.sh && ./restart-neo4j.sh

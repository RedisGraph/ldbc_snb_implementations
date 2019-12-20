#!/bin/bash

mvn package  -DskipTests --projects common,cypher,redisgraph

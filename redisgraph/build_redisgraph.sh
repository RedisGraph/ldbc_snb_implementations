#!/bin/bash

cd .. && mvn clean package -DskipTests --projects common,redisgraph && cd redisgraph

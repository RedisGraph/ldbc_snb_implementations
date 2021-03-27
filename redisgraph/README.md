# LDBC SNB RedisGraph Cypher implementation

RedisGraph [(open)Cypher](http://www.opencypher.org/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

For each Scale Factor, the benchmark scripts will perform 3 runs.
With regards to the test suites detail, the included benchmark helpers, perform 40000 warm-up queries, followed by 123K queries, using 8 benchmark threads and reported as result the median (q50), q95, and  q99.
The presented latency values are in MICROSECOND scale, and include RTT as time is measured to and from the benchmarking client perspective. 

## Pre-requisites
To be able to build the driver you need to install the ldbc_snb_driver beforehand.
Outside the project root folder, do as follows:
```
git clone --depth 1 --branch dev https://github.com/ldbc/ldbc_snb_driver && cd ldbc_snb_driver && mvn install -DskipTests && cd ..
```

## Building the driver

You can use the `build_redisgraph.sh` script to automate the build.
```
./build_redisgraph.sh
```

## Loading the data set 

### Generating the data set

The data set needs to be generated and preprocessed before loading it to the database. 
To generate it, we use the `CSVBasic` serializer classes of the [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project. 

We've prepared some automation scripts to generate and pre-process the data with docker. To use then just do as follows ( replace SF=`0.1` with the proper SF you want to generate data for )

```
cd datagen
SF=0.1 ./generate.sh
```


The script will generate the data and place it on `datagen/SF-<scale factor>/...`

### Load the SNB CSVs

The redisgraph-bulk-loader tool is required to load the data. You can install vi via:
```
pip install redisgraph-bulk-loader
```

To load the data:


```bash
cd datagen
SF=0.1 ./import-to-redis.sh
```

## Running the benchmark
Assuming a RedisGraph instance is available at localhost:6379, and the data is already loaded.
Adjust the environment variables present in `runner/environment-variables-redisgraph.sh` to adjust the host, number of workers or more complex properties.

### Interactive Scale Factor 0.1

```bash
cd runner
SF=0.1 ./run.sh
```

# LDBC SNB RedisGraph Cypher implementation

RedisGraph [(open)Cypher](http://www.opencypher.org/) implementation of the [LDBC SNB BI benchmark](https://github.com/ldbc/ldbc_snb_docs).

## Starting RedisGraph

Run:

```
./environment-variables-redisgraph.sh
```

## Loading the data set

### Generating the data set

The data set needs to be generated and preprocessed before loading it to the database. To generate it, use the `CSVComposite` serializer classes of the [DATAGEN](https://github.com/ldbc/ldbc_snb_datagen/) project:

```
ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvCompositeDynamicActivitySerializer
ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvCompositeDynamicPersonSerializer
ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvCompositeStaticSerializer
```

An example configuration for scale factor 1 is given in the [`params-csv-composite.ini`](https://github.com/ldbc/ldbc_snb_datagen/blob/master/params-csv-composite.ini) file of the DATAGEN repository. For small loading experiments, we recommend using scale factor 0.1, i.e. `snb.interactive.0.1`.

### Preprocessing and loading

Go to the `load-scripts/` directory.

#### Preprocessing

Set the following environment variables appropriately:

```bash
export REDISGRAPH_DATA_DIR=/path/do/the/csv/files
export POSTFIX=_0_0.csv
```

The CSV files require a bit of pre-processing:

* replace headers with RedisGraph-compatible ones


The following script takes care of those steps:
```
pip install git+https://github.com/RedisGraph/redisgraph-bulk-loader.git
```
```bash
./convert-csvs.sh
```

#### Delete your database and load the SNB CSVs

Be careful -- this deletes all data in your database, imports the SNB data set and restarts the database.

```bash
./delete-redisgraph-database.sh
./import-to-redisgraph.sh
```

#### All-in-one loading script

If you know what you're doing, you can run all scripts with a single command:

```bash
./load-in-one-step.sh
```

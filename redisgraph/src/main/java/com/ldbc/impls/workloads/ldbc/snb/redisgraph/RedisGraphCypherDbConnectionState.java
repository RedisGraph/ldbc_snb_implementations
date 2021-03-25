package com.ldbc.impls.workloads.ldbc.snb.redisgraph;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.Log4jLoggingServiceFactory;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.redislabs.redisgraph.RedisGraphContext;
import com.redislabs.redisgraph.impl.api.RedisGraph;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Map;

public class RedisGraphCypherDbConnectionState extends BaseDbConnectionState<RedisGraphCypherQueryStore> {

    //    protected final Map<Long, RedisGraphContextGenerator> graphContexts;
    // general context api. Not bound to graph key or connection
//    private  graph;
    private String graphId;
    private String host;
    private Integer port;
    private Integer readTimeout;
    private Integer writeTimeout;
    private Integer poolMinIdle;
    private Integer poolMaxIdle;
    private Integer poolMaxTotal;
    private Integer maxThreads;
    private final LoggingService loggingService;

    protected final JedisPool pool;
    protected final GenericObjectPoolConfig poolConfig;
    protected final RedisGraph cg;

    public RedisGraphCypherDbConnectionState(Map<String, String> properties, RedisGraphCypherQueryStore store) {
        super(properties, store);
        writeTimeout = 60000;
        readTimeout = 60000;
        poolMinIdle = 128;
        poolMaxIdle = 1024;
        poolMaxTotal = 1024;
        maxThreads = 1;
        loggingService = new Log4jLoggingServiceFactory(false).loggingServiceFor(getClass().getName());
        host = properties.get("host");
        port = Integer.parseInt(properties.get("port"));
        if (properties.containsKey("thread_count")) {
            maxThreads = Integer.parseInt(properties.get("thread_count"));
        }
        if (properties.containsKey("writeTimeoutMillis")) {
            writeTimeout = Integer.parseInt(properties.get("writeTimeoutMillis"));
        }
        if (properties.containsKey("readTimeoutMillis")) {
            readTimeout = Integer.parseInt(properties.get("readTimeoutMillis"));
        }
        graphId = properties.get("graphId");
        poolConfig = new GenericObjectPoolConfig();
        if (properties.containsKey("poolMaxTotal")) {
            poolMaxTotal = Integer.parseInt(properties.get("poolMaxTotal"));
        }
        if (properties.containsKey("poolMinIdle")) {
            poolMinIdle = Integer.parseInt(properties.get("poolMinIdle"));
        }
        if (properties.containsKey("poolMaxIdle")) {
            poolMaxIdle = Integer.parseInt(properties.get("poolMaxIdle"));
        }
        poolConfig.setMaxTotal(poolMaxTotal);
        poolConfig.setMinIdle(poolMinIdle);
        poolConfig.setMaxIdle(poolMaxIdle);
        poolConfig.setMaxWaitMillis(writeTimeout);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        pool = new JedisPool(poolConfig, host, port, readTimeout);
        cg = new RedisGraph(pool);
    }

    /**
     * Generate a connection bounded api
     *
     * @return a connection bounded api
     */
    public RedisGraphContext getContext() throws DbException {
       return cg.getContext();
    }

    public String getGraphId() {
        return graphId;
    }

    @Override
    public void close() throws IOException {
//        cg.close();
    }

}

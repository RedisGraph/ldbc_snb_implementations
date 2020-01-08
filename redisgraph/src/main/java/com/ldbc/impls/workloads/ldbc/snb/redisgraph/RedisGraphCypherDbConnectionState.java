package com.ldbc.impls.workloads.ldbc.snb.redisgraph;

import com.ldbc.driver.DbException;
import com.ldbc.driver.WorkloadException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.redislabs.redisgraph.RedisGraphContext;
import com.redislabs.redisgraph.RedisGraphContextGenerator;
import com.redislabs.redisgraph.impl.api.RedisGraph;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RedisGraphCypherDbConnectionState extends BaseDbConnectionState<RedisGraphCypherQueryStore> {

    // general context api. Not bound to graph key or connection
    protected final RedisGraphContextGenerator graph;
    protected final String graphId;
    protected final Map<Long, RedisGraphContext> graphContexts;

    public RedisGraphCypherDbConnectionState(Map<String, String> properties, RedisGraphCypherQueryStore store) {
        super(properties, store);
        String host = properties.get("host");
        Integer nThreads = Integer.parseInt(properties.get("thread_count"));
        Integer port = Integer.parseInt(properties.get("port"));
        graphId = properties.get("graphId");
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(1024);
        poolConfig.setMinIdle(128);
        JedisPool pool = new JedisPool(poolConfig, host, port);
//        pool.getResource()
//        pool.getResource();
        graph = new RedisGraph( pool );
        graphContexts = new HashMap<Long, RedisGraphContext>();
//        Map<long, RedisGraphContext> ();
    }

    /**
     * Generate a connection bounded api
     *
     * @return a connection bounded api
     */
    public RedisGraphContext getContext() throws DbException {
        Thread currentThread = Thread.currentThread();
        long id = currentThread.getId();
        RedisGraphContext cont;
        if (graphContexts.containsKey(id)) {
            cont = graphContexts.get(id);
        }else{
            cont = graph.getContext();
            graphContexts.put(id,cont);
        }
        return cont;
    }

    public String getGraphId() {
        return graphId;
    }

    @Override
    public void close() throws IOException {
        graph.close();
    }

}

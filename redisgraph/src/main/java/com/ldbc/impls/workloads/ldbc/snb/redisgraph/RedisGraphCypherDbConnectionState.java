package com.ldbc.impls.workloads.ldbc.snb.redisgraph;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.redislabs.redisgraph.RedisGraphContext;
import com.redislabs.redisgraph.impl.api.RedisGraph;

import java.io.IOException;
import java.util.Map;

public class RedisGraphCypherDbConnectionState extends BaseDbConnectionState<RedisGraphCypherQueryStore> {

    // general context api. Not bound to graph key or connection
    protected final RedisGraph graph;
    protected final String graphId;

    public RedisGraphCypherDbConnectionState(Map<String, String> properties, RedisGraphCypherQueryStore store) {
        super(properties, store);
        String host = properties.get("host");
        Integer port = Integer.parseInt(properties.get("port"));
        graphId = properties.get("graphId");
        graph = new RedisGraph(host, port);
    }

    /**
     * Generate a connection bounded api
     *
     * @return a connection bounded api
     */
    public RedisGraphContext getContext() throws DbException {
        return graph.getContext();
    }

    public String getGraphId() {
        return graphId;
    }

    @Override
    public void close() throws IOException {
        graph.close();
    }

}

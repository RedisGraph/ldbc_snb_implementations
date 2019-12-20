package com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.UpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.RedisGraphCypherDbConnectionState;
import com.redislabs.redisgraph.RedisGraphContext;
//import org.neo4j.driver.v1.Session;

public abstract class CypherUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements UpdateOperationHandler<TOperation, RedisGraphCypherDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, RedisGraphCypherDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try (RedisGraphContext context = state.getContext()) {
            final String graphId = state.getGraphId();
            final String queryString = getQueryString(state, operation);
            state.logQuery(operation.getClass().getSimpleName(), queryString);
            context.query(graphId, queryString);
        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }
}

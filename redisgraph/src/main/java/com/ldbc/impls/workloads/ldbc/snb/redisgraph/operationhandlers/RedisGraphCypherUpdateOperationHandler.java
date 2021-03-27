package com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.UpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.RedisGraphCypherDbConnectionState;
import com.redislabs.redisgraph.RedisGraphContext;
import com.redislabs.redisgraph.ResultSet;

public abstract class RedisGraphCypherUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements UpdateOperationHandler<TOperation, RedisGraphCypherDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, RedisGraphCypherDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try (RedisGraphContext context = state.getContext()) {
            final String graphId = state.getGraphId();
            final String queryString = getQueryString(state, operation);
            System.out.println("STRING UpdateOperationHandler:"+queryString);
            state.logQuery(operation.getClass().getSimpleName(), queryString);
            final ResultSet StatementResult = context.query(graphId, queryString);

            if (state.isPrintResults()) {
                System.out.println(StatementResult.getHeader());
            }

        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }

}

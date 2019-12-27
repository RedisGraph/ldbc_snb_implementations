package com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.RedisGraphCypherDbConnectionState;
import com.redislabs.redisgraph.Record;
import com.redislabs.redisgraph.RedisGraphContext;
import com.redislabs.redisgraph.ResultSet;
import com.redislabs.redisgraph.exceptions.JRedisGraphCompileTimeException;

import java.io.IOException;
import java.text.ParseException;

public abstract class CypherSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, RedisGraphCypherDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, RedisGraphCypherDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {

        RedisGraphContext context = state.getContext();
        final String graphId = state.getGraphId();

        final String queryString = getQueryString(state, operation);
        state.logQuery(operation.getClass().getSimpleName(), queryString);

        try {
            TOperationResult tuple = null;
            int resultCount = 0;

            final ResultSet StatementResult = context.query(graphId, queryString);

            if (StatementResult.hasNext()) {
                final Record record = StatementResult.next();
                resultCount++;

                tuple = convertSingleResult(record);

                if (state.isPrintResults()) {
                    System.out.println(tuple.toString());
                }
            }
            context.close();

            resultReporter.report(resultCount, tuple, operation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DbException(e);
        }

        try {
            state.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract TOperationResult convertSingleResult(Record record) throws ParseException;

}

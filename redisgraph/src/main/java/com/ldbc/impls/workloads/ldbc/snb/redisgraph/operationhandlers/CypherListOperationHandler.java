package com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.RedisGraphCypherDbConnectionState;
import com.redislabs.redisgraph.Record;
import com.redislabs.redisgraph.RedisGraphContext;
import com.redislabs.redisgraph.ResultSet;
import com.redislabs.redisgraph.exceptions.JRedisGraphCompileTimeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class CypherListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, RedisGraphCypherDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, RedisGraphCypherDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        RedisGraphContext context = state.getContext();
        final String graphId = state.getGraphId();

        List<TOperationResult> results = new ArrayList<>();
        int resultCount = 0;
        results.clear();
        final String queryString = getQueryString(state, operation);
        state.logQuery(operation.getClass().getSimpleName(), queryString);
        try {
            final ResultSet result = context.query(graphId, queryString);
            while (result.hasNext()) {
                final Record record = result.next();
                resultCount++;
                TOperationResult tuple;
                try {
                    tuple = convertSingleResult(record);
                } catch (ParseException e) {
                    throw new DbException(e);
                }
                if (state.isPrintResults()) {
                    System.out.println(tuple.toString());
                }
                results.add(tuple);
            }
        } catch (JRedisGraphCompileTimeException e) {
            throw new DbException(e);
        }

        try {
            state.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultReporter.report(resultCount, results, operation);
    }

    public abstract TOperationResult convertSingleResult(Record record) throws ParseException;

}

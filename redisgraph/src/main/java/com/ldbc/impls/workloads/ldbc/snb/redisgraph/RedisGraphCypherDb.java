package com.ldbc.impls.workloads.ldbc.snb.redisgraph;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers.CypherListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers.CypherSingletonOperationHandler;
import com.redislabs.redisgraph.Record;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class RedisGraphCypherDb extends BaseDb<RedisGraphCypherQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new RedisGraphCypherDbConnectionState(properties, new RedisGraphCypherQueryStore(properties.get("queryDir")));
    }

    // Interactive complex reads

    public static class InteractiveQuery1 extends CypherListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcQuery1Result convertSingleResult(Record record) throws ParseException {

            List<String> emails = new ArrayList<>();
//            if (!Objects.isNull(record.getValue(8))) {
//                record.getValue(8)
//                for (int pos = 0; pos < record.size(); pos++){
//                    emails.add( record.getString(pos));
//                }
//                for (Object inObj : record.values()) {
//                    inObj.toString()
//                    out.add(f.apply(inObj));
//                }
//
//                emails = record.getValue(8).asList((e) -> e.asString());
//            }

            List<String> languages = new ArrayList<>();
//            if (!Objects.isNull(record.getValue(9))) {
//                languages = record.getValue(9).asList((e) -> e.asString());
//            } else {
//                languages = new ArrayList<>();
//            }
//
            List<List<Object>> universities = new ArrayList<>();
//            if (!Objects.isNull(record.getValue(11))) {
//                universities = record.getValue(11).asList((e) ->
//                        e.asList());
//            } else {
//                universities = new ArrayList<>();
//            }

            List<List<Object>> companies = new ArrayList<>();
//            if (!Objects.isNull(record.getValue(12))) {
//                companies = record.getValue(12).asList((e) ->
//                        e.asList());
//            } else {
//                companies = new ArrayList<>();
//            }
//
            long friendId = record.getValue(0);
            String friendLastName = record.getValue(1);
            int distanceFromPerson = record.getValue(2);
            long friendBirthday = record.getValue(3); //CypherConverter.convertLongDateToEpoch(record.getValue(3).asLong());
            long friendCreationDate = record.getValue(4); //CypherConverter.convertLongTimestampToEpoch(record.getValue(4).asLong());
            String friendGender = record.getString(5);
            String friendBrowserUsed = record.getString(6);
            String friendLocationIp = record.getString(7);
            String friendCityName = record.getString(10);
            return new LdbcQuery1Result(
                    friendId,
                    friendLastName,
                    distanceFromPerson,
                    friendBirthday,
                    friendCreationDate,
                    friendGender,
                    friendBrowserUsed,
                    friendLocationIp,
                    emails,
                    languages,
                    friendCityName,
                    universities,
                    companies);
        }
    }

    public static class InteractiveQuery4 extends CypherListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcQuery4Result convertSingleResult(Record record) {
            String tagName = record.getString(0);
            int postCount = Integer.parseInt(record.getString(1));
            return new LdbcQuery4Result(tagName, postCount);
        }

    }


    // Interactive short reads

    public static class ShortQuery1PersonProfile extends CypherSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getShortQuery1PersonProfile(operation);
        }

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult(Record record) throws ParseException {
            String firstName = record.getString(0);
            String lastName = record.getString(1);
            long birthday = record.getValue(2);
            String locationIP = record.getString(3);
            String browserUsed = record.getString(4);
            long cityId = record.getValue(5);
            String gender = record.getValue(6);
            long creationDate = record.getValue(7);
            return new LdbcShortQuery1PersonProfileResult(
                    firstName,
                    lastName,
                    birthday,
                    locationIP,
                    browserUsed,
                    cityId,
                    gender,
                    creationDate);
        }
    }


}

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
import java.util.Objects;

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
            // TODO: RedisGraph fix me
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
//          // TODO: RedisGraph fix me
            List<List<Object>> universities = new ArrayList<>();
//            if (!Objects.isNull(record.getValue(11))) {
//                universities = record.getValue(11).asList((e) ->
//                        e.asList());
//            } else {
//                universities = new ArrayList<>();
//            }
            // TODO: RedisGraph fix me
            List<List<Object>> companies = new ArrayList<>();
//            if (!Objects.isNull(record.getValue(12))) {
//                companies = record.getValue(12).asList((e) ->
//                        e.asList());
//            } else {
//                companies = new ArrayList<>();
//            }
//
            long friendId = Long.parseLong(record.getString(0));
            String friendLastName = record.getValue(1);
            int distanceFromPerson = record.getValue(2);
            long friendBirthday = Long.parseLong(record.getString(3)); //CypherConverter.convertLongDateToEpoch(record.getValue(3).asLong());
            long friendCreationDate = Long.parseLong(record.getString(4)); //CypherConverter.convertLongTimestampToEpoch(record.getValue(4).asLong());
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

    public static class InteractiveQuery2 extends CypherListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery2 operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcQuery2Result convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            long messageId = Long.parseLong(record.getString(3));
            String messageContent = record.getString(4);
            // TODO: RedisGraph fix me
            long messageCreationDate = record.getValue(0); //CypherConverter.convertLongTimestampToEpoch(record.get(5).asLong());

            return new LdbcQuery2Result(
                    personId,
                    personFirstName,
                    personLastName,
                    messageId,
                    messageContent,
                    messageCreationDate);
        }
    }

    public static class InteractiveQuery3 extends CypherListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery3 operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcQuery3Result convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            int xCount = Integer.parseInt(record.getString(3));
            int yCount = Integer.parseInt(record.getString(4));
            int count = Integer.parseInt(record.getString(5));
            return new LdbcQuery3Result(
                    personId,
                    personFirstName,
                    personLastName,
                    xCount,
                    yCount,
                    count);
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

    public static class InteractiveQuery5 extends CypherListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcQuery5Result convertSingleResult(Record record) {
            String forumTitle = record.getString(0);
            int postCount = Integer.parseInt(record.getString(1));
            return new LdbcQuery5Result(forumTitle, postCount);
        }
    }

    public static class InteractiveQuery6 extends CypherListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcQuery6Result convertSingleResult(Record record) {
            String tagName = record.getString(0);
            int postCount = Integer.parseInt(record.getString(1));
            return new LdbcQuery6Result(tagName, postCount);
        }
    }

    public static class InteractiveQuery7 extends CypherListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcQuery7Result convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            long likeCreationDate = Long.parseLong(record.getString(3));
            long messageId = Long.parseLong(record.getString(4));
            String messageContent = record.getString(5);
            // TODO: RedisGraph fix me
            int minutesLatency = 0; //CypherConverter.convertStartAndEndDateToLatency(record.get(6).asLong(), record.get(3).asLong());
            boolean isNew = Boolean.parseBoolean(record.getString(7));
            return new LdbcQuery7Result(
                    personId,
                    personFirstName,
                    personLastName,
                    likeCreationDate,
                    messageId,
                    messageContent,
                    minutesLatency,
                    isNew);
        }
    }

    public static class InteractiveQuery8 extends CypherListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcQuery8Result convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            long commentCreationDate = Long.parseLong(record.getString(3));
            long commentId = Long.parseLong(record.getString(4));
            String commentContent = record.getString(5);
            return new LdbcQuery8Result(
                    personId,
                    personFirstName,
                    personLastName,
                    commentCreationDate,
                    commentId,
                    commentContent);
        }
    }

    public static class InteractiveQuery9 extends CypherListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery9 operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcQuery9Result convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            long messageId = Long.parseLong(record.getString(3));
            String messageContent = record.getString(4);
            long messageCreationDate = Long.parseLong(record.getString(5));
            return new LdbcQuery9Result(
                    personId,
                    personFirstName,
                    personLastName,
                    messageId,
                    messageContent,
                    messageCreationDate);
        }
    }

    public static class InteractiveQuery10 extends CypherListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery10 operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        public LdbcQuery10Result convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            int commonInterestScore = Integer.parseInt(record.getString(3));
            String personGender = record.getString(4);
            String personCityName = record.getString(5);
            return new LdbcQuery10Result(
                    personId,
                    personFirstName,
                    personLastName,
                    commonInterestScore,
                    personGender,
                    personCityName);
        }
    }

    public static class InteractiveQuery11 extends CypherListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery11 operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        public LdbcQuery11Result convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            String organizationName = record.getString(3);
            int organizationWorkFromYear = Integer.parseInt(record.getString(4));
            return new LdbcQuery11Result(
                    personId,
                    personFirstName,
                    personLastName,
                    organizationName,
                    organizationWorkFromYear);
        }
    }

    public static class InteractiveQuery12 extends CypherListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcQuery12Result convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            List<String> tagNames = new ArrayList<>();
            //TODO: RedisGraph fix me
            if (!Objects.isNull(record.getValue(3))) {
//                tagNames = record.getValue(3).asList((e) -> e.asString());
            }
            int replyCount = Integer.parseInt(record.getString(4));
            return new LdbcQuery12Result(
                    personId,
                    personFirstName,
                    personLastName,
                    tagNames,
                    replyCount);
        }
    }

    public static class InteractiveQuery13 extends CypherSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery13 operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(Record record) {
            return new LdbcQuery13Result(Integer.parseInt(record.getString(0)));
        }
    }

    public static class InteractiveQuery14 extends CypherListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery14 operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(Record record) throws ParseException {
            List<Long> personIdsInPath = new ArrayList<>();
            //TODO: RedisGraph fix me
            if (!Objects.isNull(record.getValue(0))) {
//                personIdsInPath = record.get(0).asList((e) -> e.asLong());
            }
            double pathWight = Double.parseDouble(record.getString(1));
            return new LdbcQuery14Result(
                    personIdsInPath,
                    pathWight);
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
            long birthday = Long.parseLong(record.getValue(2));
            String locationIP = record.getString(3);
            String browserUsed = record.getString(4);
            long cityId = Long.parseLong(record.getValue(5));
            String gender = record.getValue(6);
            long creationDate = Long.parseLong(record.getValue(7));
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

    public static class ShortQuery2PersonPosts extends CypherListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getShortQuery2PersonPosts(operation);
        }

        @Override
        public LdbcShortQuery2PersonPostsResult convertSingleResult(Record record) throws ParseException {
            long messageId = Long.parseLong(record.getString(0));
            String messageContent = record.getString(1);
            long messageCreationDate = Long.parseLong(record.getString(2));
            long originalPostId = Long.parseLong(record.getString(3));
            long originalPostAuthorId = Long.parseLong(record.getString(4));
            String originalPostAuthorFirstName = record.getString(5);
            String originalPostAuthorLastName = record.getString(6);
            return new LdbcShortQuery2PersonPostsResult(
                    messageId,
                    messageContent,
                    messageCreationDate,
                    originalPostId,
                    originalPostAuthorId,
                    originalPostAuthorFirstName,
                    originalPostAuthorLastName);
        }
    }

    public static class ShortQuery3PersonFriends extends CypherListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getShortQuery3PersonFriends(operation);
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String firstName = record.getString(1);
            String lastName = record.getString(2);
            //TODO: RedisGraph fix me
            long friendshipCreationDate = Long.parseLong(record.getString(3));// CypherConverter.convertLongTimestampToEpoch(record.get(3).asLong());
            return new LdbcShortQuery3PersonFriendsResult(
                    personId,
                    firstName,
                    lastName,
                    friendshipCreationDate);
        }
    }

    public static class ShortQuery4MessageContent extends CypherSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getShortQuery4MessageContent(operation);
        }

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult(Record record) throws ParseException {
            // Pay attention, the spec's and the implementation's parameter orders are different.
            //TODO: RedisGraph fix me
            long messageCreationDate = Long.parseLong(record.getString(0)); //CypherConverter.convertLongTimestampToEpoch(record.get(0).asLong());
            String messageContent = record.getString(1);
            return new LdbcShortQuery4MessageContentResult(
                    messageContent,
                    messageCreationDate);
        }
    }

    public static class ShortQuery5MessageCreator extends CypherSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getShortQuery5MessageCreator(operation);
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            String firstName = record.getString(1);
            String lastName = record.getString(2);
            return new LdbcShortQuery5MessageCreatorResult(
                    personId,
                    firstName,
                    lastName);
        }
    }

    public static class ShortQuery6MessageForum extends CypherSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getShortQuery6MessageForum(operation);
        }

        @Override
        public LdbcShortQuery6MessageForumResult convertSingleResult(Record record) {
            long forumId = Long.parseLong(record.getString(0));
            String forumTitle = record.getString(1);
            long moderatorId = Long.parseLong(record.getString(2));
            String moderatorFirstName = record.getString(3);
            String moderatorLastName = record.getString(4);
            return new LdbcShortQuery6MessageForumResult(
                    forumId,
                    forumTitle,
                    moderatorId,
                    moderatorFirstName,
                    moderatorLastName);
        }
    }

    public static class ShortQuery7MessageReplies extends CypherListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getShortQuery7MessageReplies(operation);
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult convertSingleResult(Record record) throws ParseException {

            long commentId = Long.parseLong(record.getString(0));
            String commentContent = record.getString(1);
            //TODO: RedisGraph fix me
            long commentCreationDate = Long.parseLong(record.getString(2)); //CypherConverter.convertLongTimestampToEpoch(record.get(2).asLong());
            long replyAuthorId = Long.parseLong(record.getString(3));
            String replyAuthorFirstName = record.getString(4);
            String replyAuthorLastName = record.getString(5);
            boolean replyAuthorKnowsOriginalMessageAuthor = Boolean.parseBoolean(record.getString(6));
            return new LdbcShortQuery7MessageRepliesResult(
                    commentId,
                    commentContent,
                    commentCreationDate,
                    replyAuthorId,
                    replyAuthorFirstName,
                    replyAuthorLastName,
                    replyAuthorKnowsOriginalMessageAuthor);
        }
    }


}

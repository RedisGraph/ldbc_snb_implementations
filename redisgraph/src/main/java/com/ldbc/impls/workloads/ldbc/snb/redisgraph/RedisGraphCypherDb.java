package com.ldbc.impls.workloads.ldbc.snb.redisgraph;

import com.google.common.primitives.Ints;
import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers.CypherListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers.CypherSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers.CypherUpdateOperationHandler;
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

    // Interactive updates

    public static class Update1AddPerson extends CypherUpdateOperationHandler<LdbcUpdate1AddPerson> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate1AddPerson operation) {
            return state.getQueryStore().getUpdate1Single(operation);
        }
    }

    public static class Update2AddPostLike extends CypherUpdateOperationHandler<LdbcUpdate2AddPostLike> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate2AddPostLike operation) {
            return state.getQueryStore().getUpdate2(operation);
        }
    }

    public static class Update3AddCommentLike extends CypherUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate3AddCommentLike operation) {
            return state.getQueryStore().getUpdate3(operation);
        }
    }

    public static class Update4AddForum extends CypherUpdateOperationHandler<LdbcUpdate4AddForum> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate4AddForum operation) {
            return state.getQueryStore().getUpdate4Single(operation);
        }
    }

    public static class Update5AddForumMembership extends CypherUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate5AddForumMembership operation) {
            return state.getQueryStore().getUpdate5(operation);
        }
    }

    public static class Update6AddPost extends CypherUpdateOperationHandler<LdbcUpdate6AddPost> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate6AddPost operation) {
            return state.getQueryStore().getUpdate6Single(operation);
        }
    }

    public static class Update7AddComment extends CypherUpdateOperationHandler<LdbcUpdate7AddComment> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate7AddComment operation) {
            return state.getQueryStore().getUpdate7Single(operation);
        }
    }

    public static class Update8AddFriendship extends CypherUpdateOperationHandler<LdbcUpdate8AddFriendship> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate8AddFriendship operation) {
            return state.getQueryStore().getUpdate8(operation);
        }
    }

    // BI queries

    public static class BiQuery1 extends CypherListOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery1PostingSummary operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(Record record) {
            int year = Integer.parseInt(record.getString(0));
            boolean isComment = Boolean.parseBoolean(record.getString(1));
            int size = Integer.parseInt(record.getString(2));
            long count = Long.parseLong(record.getString(3));
            int avgLen = Integer.parseInt(record.getString(4));
            int total = Integer.parseInt(record.getString(5));
            double pct = Double.parseDouble(record.getString(6));

            return new LdbcSnbBiQuery1PostingSummaryResult(year, isComment, size, count, avgLen, total, (float) pct);
        }

    }

    public static class BiQuery2 extends CypherListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery2TopTags operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcSnbBiQuery2TopTagsResult convertSingleResult(Record record) {
            String country = record.getString(0);
            int month = Integer.parseInt(record.getString(1));
            String gender = record.getString(2);
            int ageGroup = Integer.parseInt(record.getString(3));
            String tag = record.getString(4);
            int count = Integer.parseInt(record.getString(5));
            return new LdbcSnbBiQuery2TopTagsResult(country, month, gender, ageGroup, tag, count);
        }

    }

    public static class BiQuery3 extends CypherListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery3TagEvolution operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(Record record) {
            String tagName = record.getString(0);
            int countA = Integer.parseInt(record.getString(1));
            int countB = Integer.parseInt(record.getString(2));
            int diffCount = Integer.parseInt(record.getString(3));
            return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countA, countB, diffCount);
        }

    }

    public static class BiQuery4 extends CypherListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery4PopularCountryTopics operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(Record record) throws ParseException {
            long forumId = Long.parseLong(record.getString(0));
            String title = record.getString(1);
            //TODO: RedisGraph fix me
            long creationDate = Long.parseLong(record.getString(2)); // CypherConverter.convertLongTimestampToEpoch(record.get(2).asLong());
            long moderator = Long.parseLong(record.getString(3));
            int count = Integer.parseInt(record.getString(4));
            return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, title, creationDate, moderator, count);
        }

    }

    public static class BiQuery5 extends CypherListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery5TopCountryPosters operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String firstName = record.getString(1);
            String lastName = record.getString(2);
            //TODO: RedisGraph fix me
            long creationDate = Long.parseLong(record.getString(3)); // CypherConverter.convertLongTimestampToEpoch(record.get(3).asLong());
            int count = Integer.parseInt(record.getString(4));
            return new LdbcSnbBiQuery5TopCountryPostersResult(personId, firstName, lastName, creationDate, count);
        }

    }

    public static class BiQuery6 extends CypherListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery6ActivePosters operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            int postCount = Integer.parseInt(record.getString(1));
            int replyCount = Integer.parseInt(record.getString(2));
            int likeCount = Integer.parseInt(record.getString(3));
            int score = Integer.parseInt(record.getString(4));
            return new LdbcSnbBiQuery6ActivePostersResult(personId, postCount, replyCount, likeCount, score);
        }

    }

    public static class BiQuery7 extends CypherListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            int score = Integer.parseInt(record.getString(1));
            return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, score);
        }

    }

    public static class BiQuery8 extends CypherListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery8RelatedTopics operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(Record record) {
            String tag = record.getString(0);
            int count = Integer.parseInt(record.getString(1));
            return new LdbcSnbBiQuery8RelatedTopicsResult(tag, count);
        }

    }

    public static class BiQuery9 extends CypherListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery9RelatedForums operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(Record record) {
            long forumId = Long.parseLong(record.getString(0));
            int sumA = Integer.parseInt(record.getString(1));
            int sumB = Integer.parseInt(record.getString(2));
            return new LdbcSnbBiQuery9RelatedForumsResult(forumId, sumA, sumB);
        }

    }

    public static class BiQuery10 extends CypherListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery10TagPerson operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        public LdbcSnbBiQuery10TagPersonResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            int score = Integer.parseInt(record.getString(1));
            int friendsScore = Integer.parseInt(record.getString(2));
            return new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
        }

    }

    public static class BiQuery11 extends CypherListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery11UnrelatedReplies operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            String tagName = record.getString(1);
            int countLikes = Integer.parseInt(record.getString(2));
            int countReplies = Integer.parseInt(record.getString(3));
            return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, countLikes, countReplies);
        }

    }

    public static class BiQuery12 extends CypherListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery12TrendingPosts operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            //TODO: RedisGraph fix me
            long creationDate = Long.parseLong(record.getString(1)); // CypherConverter.convertLongTimestampToEpoch(record.get(1).asLong());
            String firstName = record.getString(2);
            String lastName = record.getString(3);
            int likeCount = Integer.parseInt(record.getString(4));
            return new LdbcSnbBiQuery12TrendingPostsResult(personId, creationDate, firstName, lastName, likeCount);
        }
    }

    public static class BiQuery13 extends CypherListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(Record record) {
            int year = Integer.parseInt(record.getString(0));
            int month = Integer.parseInt(record.getString(1));
            //TODO: RedisGraph fix me
            final List<List<Object>> tagPopularitiesRaw = new ArrayList<>();// record.get(2).asList(Values.ofList());

            final List<LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity> tagPopularities = new ArrayList<>();
            for (List<Object> tagPopularityRaw : tagPopularitiesRaw) {
                final String tag = (String) tagPopularityRaw.get(0);
                final int popularity = Ints.saturatedCast((long) tagPopularityRaw.get(1));
                tagPopularities.add(new LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity(tag, popularity));
            }

            return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, tagPopularities);
        }
    }

    public static class BiQuery14 extends CypherListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery14TopThreadInitiators operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            String firstName = record.getString(1);
            String lastName = record.getString(2);
            int count = Integer.parseInt(record.getString(3));
            int threadCount = Integer.parseInt(record.getString(4));
            return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, firstName, lastName, count, threadCount);
        }
    }

    public static class BiQuery15 extends CypherListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery15SocialNormals operation) {
            return state.getQueryStore().getQuery15(operation);
        }

        @Override
        public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            int count = Integer.parseInt(record.getString(1));
            return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
        }
    }

    public static class BiQuery16 extends CypherListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
            return state.getQueryStore().getQuery16(operation);
        }

        @Override
        public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            String tag = record.getString(1);
            int count = Integer.parseInt(record.getString(2));
            return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tag, count);
        }
    }

    public static class BiQuery17 extends CypherSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery17FriendshipTriangles operation) {
            return state.getQueryStore().getQuery17(operation);
        }

        @Override
        public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(Record record) {
            int count = Integer.parseInt(record.getString(0));
            return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
        }
    }

    public static class BiQuery18 extends CypherListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery18PersonPostCounts operation) {
            return state.getQueryStore().getQuery18(operation);
        }

        @Override
        public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(Record record) {
            int postCount = Integer.parseInt(record.getString(0));
            int count = Integer.parseInt(record.getString(1));
            return new LdbcSnbBiQuery18PersonPostCountsResult(postCount, count);
        }
    }


    public static class BiQuery19 extends CypherListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery19StrangerInteraction operation) {
            return state.getQueryStore().getQuery19(operation);
        }

        @Override
        public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            int strangerCount = Integer.parseInt(record.getString(1));
            int count = Integer.parseInt(record.getString(2));
            return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, count);
        }
    }

    public static class BiQuery20 extends CypherListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery20HighLevelTopics operation) {
            return state.getQueryStore().getQuery20(operation);
        }

        @Override
        public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(Record record) {
            String tagClass = record.getString(0);
            int count = Integer.parseInt(record.getString(1));
            return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClass, count);
        }
    }

    public static class BiQuery21 extends CypherListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery21Zombies operation) {
            return state.getQueryStore().getQuery21(operation);
        }

        @Override
        public LdbcSnbBiQuery21ZombiesResult convertSingleResult(Record record) {
            long personId = Long.parseLong(record.getString(0));
            int zombieCount = Integer.parseInt(record.getString(1));
            int realCount = Integer.parseInt(record.getString(2));
            double score = Double.parseDouble(record.getString(3));
            return new LdbcSnbBiQuery21ZombiesResult(personId, zombieCount, realCount, score);
        }
    }

    public static class BiQuery22 extends CypherListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery22InternationalDialog operation) {
            return state.getQueryStore().getQuery22(operation);
        }

        @Override
        public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(Record record) {
            long personIdA = Long.parseLong(record.getString(0));
            long personIdB = Long.parseLong(record.getString(1));
            String city1Name = record.getString(2);
            int score = Integer.parseInt(record.getString(3));
            return new LdbcSnbBiQuery22InternationalDialogResult(personIdA, personIdB, city1Name, score);
        }
    }

    public static class BiQuery23 extends CypherListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery23HolidayDestinations operation) {
            return state.getQueryStore().getQuery23(operation);
        }

        @Override
        public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(Record record) {
            int messageCount = Integer.parseInt(record.getString(0));
            String country = record.getString(1);
            int month = Integer.parseInt(record.getString(2));
            return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, country, month);
        }
    }


    public static class BiQuery24 extends CypherListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery24MessagesByTopic operation) {
            return state.getQueryStore().getQuery24(operation);
        }

        @Override
        public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(Record record) {
            int messageCount = Integer.parseInt(record.getString(0));
            int likeCount = Integer.parseInt(record.getString(1));
            int year = Integer.parseInt(record.getString(2));
            int month = Integer.parseInt(record.getString(3));
            String continent = record.getString(4);
            return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continent);
        }
    }

    public static class BiQuery25 extends CypherListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery25WeightedPaths operation) {
            return state.getQueryStore().getQuery25(operation);
        }

        @Override
        public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(Record record) {
            //TODO: RedisGraph fix me
            List<Long> personIds = new ArrayList<>(); //record.get(0).asList(Values.ofLong());
            double weight = Double.parseDouble(record.getString(1));
            return new LdbcSnbBiQuery25WeightedPathsResult(personIds, weight);
        }
    }


}

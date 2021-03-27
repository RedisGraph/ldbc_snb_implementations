package com.ldbc.impls.workloads.ldbc.snb.redisgraph;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;

import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers.RedisGraphCypherListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers.RedisGraphCypherSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.operationhandlers.RedisGraphCypherUpdateOperationHandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.redislabs.redisgraph.Record;


public abstract class RedisGraphCypherDb extends BaseDb<RedisGraphCypherQueryStore> {


    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new RedisGraphCypherDbConnectionState(properties, new RedisGraphCypherQueryStore(properties.get("queryDir")));
    }

    /**
     * ------------------------------------------------------------------------
     * Interactive Complex READ Queries
     * ------------------------------------------------------------------------
     */
    /**
     * Given a start Person, find up to 20 Persons with a given first name that
     * the start Person is connected to (excluding start Person) by at most 3
     * steps via Knows relationships. Return Persons, including summaries of the
     * Persons workplaces and places of study. Sort results ascending by their
     * distance from the start Person, for Persons within the same distance sort
     * ascending by their last name, and for Persons with same last name
     * ascending by their identifier.[1]
     */
    // TODO: support InteractiveQuery1
    public static class InteractiveQuery1 extends RedisGraphCypherListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation).replace("\n", " ").replace("\r", " ");
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

    /**
     * Given a start Person, find (most recent) Posts and Comments from all of
     * that Person’s friends, that were created before (and including) a given
     * date. Return the top 20 Posts/Comments, and the Person that created each
     * of them. Sort results descending by creation date, and then ascending by
     * Post identifier.[1]
     */
    public static class InteractiveQuery2 extends RedisGraphCypherListOperationHandler<LdbcQuery2, LdbcQuery2Result> {


        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery2 operation) {
            return state.getQueryStore().getQuery2(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcQuery2Result convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getValue(2);
            int messageId = record.getValue(3);
            String messageContent = record.getString(4);
            int messageCreationDate = record.getValue(5);

            return new LdbcQuery2Result(
                    personId,
                    personFirstName,
                    personLastName,
                    messageId,
                    messageContent,
                    messageCreationDate);
        }
    }

    /**
     * Given a start Person, find Persons that are their friends and friends of
     * friends (excluding start Person) that have made Posts/Comments in both of
     * the given Countries, X and Y, within a given period. Only Persons that are
     * foreign to Countries X and Y are considered, that is Persons whose
     * Location is not Country X or Country Y. Return top 20 Persons, and their
     * Post/Comment counts, in the given countries and period. Sort results
     * descending by total number of Posts/Comments, and then ascending by Person
     * identifier.[1]
     */
    public static class InteractiveQuery3 extends RedisGraphCypherListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery3 operation) {
            return state.getQueryStore().getQuery3(operation).replace("\n", " ").replace("\r", " ");
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

    /**
     * Given a start Person, find Tags that are attached to Posts that were
     * created by that Person’s friends. Only include Tags that were attached to
     * friends’ Posts created within a given time interval, and that were never
     * attached to friends’ Posts created before this interval. Return top 10
     * Tags, and the count of Posts, which were created within the given time
     * interval, that this Tag was attached to. Sort results descending by Post
     * count, and then ascending by Tag name.[1]
     */


    public static class InteractiveQuery4 extends RedisGraphCypherListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcQuery4Result convertSingleResult(Record record) {
            String tagName = record.getString(0);
            int postCount = record.getValue(1);
            return new LdbcQuery4Result(tagName, postCount);
        }

    }

    /**
     * Given a start Person, find the Forums which that Person’s friends and
     * friends of friends (excluding start Person) became Members of after a
     * given date. Return top 20 Forums, and the number of Posts in each Forum
     * that was Created by any of these Persons. For each Forum consider only
     * those Persons which joined that particular Forum after the given date.
     * Sort results descending by the count of Posts, and then ascending by Forum
     * identifier.[1]
     */
    public static class InteractiveQuery5 extends RedisGraphCypherListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcQuery5Result convertSingleResult(Record record) {
            String forumTitle = record.getString(0);
            int postCount = Integer.parseInt(record.getString(1));
            return new LdbcQuery5Result(forumTitle, postCount);
        }
    }

    /**
     * Given a start Person and some Tag, find the other Tags that occur together
     * with this Tag on Posts that were created by start Person’s friends and
     * friends of friends (excluding start Person). Return top 10 Tags, and the
     * count of Posts that were created by these Persons, which contain both this
     * Tag and the given Tag. Sort results descending by count, and then
     * ascending by Tag name.[1]
     */
    public static class InteractiveQuery6 extends RedisGraphCypherListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcQuery6Result convertSingleResult(Record record) {
            String tagName = record.getString(0);
            int postCount = Integer.parseInt(record.getString(1));
            return new LdbcQuery6Result(tagName, postCount);
        }
    }


    /**
     * Given a start Person, find (most recent) Likes on any of start Person’s
     * Posts/Comments. Return top 20 Persons that Liked any of start Person’s
     * Posts/Comments, the Post/Comment they liked most recently, creation date
     * of that Like, and the latency (in minutes) between creation of
     * Post/Comment and Like. Additionally, return a flag indicating whether the
     * liker is a friend of start Person. In the case that a Person Liked
     * multiple Posts/Comments at the same time, return the Post/Comment with
     * lowest identifier. Sort results descending by creation time of Like, then
     * ascending by Person identifier of liker.[1]
     */
    public static class InteractiveQuery7 extends RedisGraphCypherListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcQuery7Result convertSingleResult(Record record) throws ParseException {
            long personId = Long.parseLong(record.getString(0));
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            long likeCreationDate = Long.parseLong(record.getString(3));
            long messageId = Long.parseLong(record.getString(4));
            String messageContent = record.getString(5);
            int minutesLatency = (int) (Long.parseLong(record.getString(6)) - likeCreationDate);
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

    /**
     * Given a start Person, find (most recent) Comments that are replies to
     * Posts/Comments of the start Person. Only consider immediate (1-hop)
     * replies, not the transitive (multi-hop) case. Return the top 20 reply
     * Comments, and the Person that created each reply Comment. Sort results
     * descending by creation date of reply Comment, and then ascending by
     * identifier of reply Comment.[1]
     */
    public static class InteractiveQuery8 extends RedisGraphCypherListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcQuery8Result convertSingleResult(Record record) throws ParseException {
            long personId = ((Integer) record.getValue(0)).longValue();
            String personFirstName = record.getString(1);
            String personLastName = record.getString(2);
            long commentCreationDate = ((Integer) record.getValue(3)).longValue();
            long commentId = ((Integer) record.getValue(4)).longValue();
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

    /**
     * Given a start Person, find the (most recent) Posts/Comments created by
     * that Person’s friends or friends of friends (excluding start Person). Only
     * consider the Posts/Comments created before a given date (excluding that
     * date). Return the top 20 Posts/Comments, and the Person that created each
     * of those Posts/Comments. Sort results descending by creation date of
     * Post/Comment, and then ascending by Post/Comment identifier.[1]
     */
    public static class InteractiveQuery9 extends RedisGraphCypherListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery9 operation) {
            return state.getQueryStore().getQuery9(operation).replace("\n", " ").replace("\r", " ");
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

    /**
     * Given a start Person, find that Person’s friends of friends (excluding
     * start Person, and immediate friends), who were born on or after the 21st
     * of a given month (in any year) and before the 22nd of the following month.
     * Calculate the similarity between each of these Persons and start Person,
     * where similarity for any Person is defined as follows:
     * <ul>
     * <li>common = number of Posts created by that Person, such that the Post
     * has a Tag that start Person is Interested in</li>
     * <li>uncommon = number of Posts created by that Person, such that the Post
     * has no Tag that start Person is Interested in</li>
     * <li>similarity = common - uncommon</li>
     * </ul>
     * Return top 10 Persons, their Place, and their similarity score. Sort
     * results descending by similarity score, and then ascending by Person
     * identifier.[1]
     */
    public static class InteractiveQuery10 extends RedisGraphCypherListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery10 operation) {
            return state.getQueryStore().getQuery10(operation).replace("\n", " ").replace("\r", " ");
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

    /**
     * Given a start Person, find that Person’s friends and friends of friends
     * (excluding start Person) who started Working in some Company in a given
     * Country, before a given date (year). Return top 10 Persons, the Company
     * they worked at, and the year they started working at that Company. Sort
     * results ascending by the start date, then ascending by Person identifier,
     * and lastly by Organization name descending.[1]
     */
    public static class InteractiveQuery11 extends RedisGraphCypherListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery11 operation) {
            return state.getQueryStore().getQuery11(operation).replace("\n", " ").replace("\r", " ");
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

    /**
     * Given a start Person, find the Comments that this Person’s friends made in
     * reply to Posts, considering only those Comments that are immediate (1-hop)
     * replies to Posts, not the transitive (multi-hop) case. Only consider Posts
     * with a Tag in a given TagClass or in a descendent of that TagClass. Count
     * the number of these reply Comments, and collect the Tags (with valid tag
     * class) that were attached to the Posts they replied to. Return top 20
     * Persons with at least one reply, the reply count, and the collection of
     * Tags. Sort results descending by Comment count, and then ascending by
     * Person identifier.[1]
     */
    public static class InteractiveQuery12 extends RedisGraphCypherListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12(operation).replace("\n", " ").replace("\r", " ");
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

    /**
     * Given two Persons, find the shortest path between these two Persons in the
     * subgraph induced by the Knows relationships. Return the length of this
     * path. -1 should be returned if no path is found, and 0 should be returned
     * if the start person is the same as the end person.[1]
     */
    public static class InteractiveQuery13 extends RedisGraphCypherSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery13 operation) {
            return state.getQueryStore().getQuery13(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcQuery13Result convertSingleResult(Record record) {
            return new LdbcQuery13Result(Integer.parseInt(record.getString(0)));
        }
    }

    /**
     * Given two Persons, find all (unweighted) shortest paths between these two
     * Persons, in the subgraph induced by the Knows relationship. Then, for each
     * path calculate a weight. The nodes in the path are Persons, and the weight
     * of a path is the sum of weights between every pair of consecutive Person
     * nodes in the path. The weight for a pair of Persons is calculated such
     * that every reply (by one of the Persons) to a Post (by the other Person)
     * contributes 1.0, and every reply (by ones of the Persons) to a Comment (by
     * the other Person) contributes 0.5. Return all the paths with shortest
     * length, and their weights. Sort results descending by path weight. The
     * order of paths with the same weight is unspecified.[1]
     */
    public static class InteractiveQuery14 extends RedisGraphCypherListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcQuery14 operation) {
            return state.getQueryStore().getQuery14(operation).replace("\n", " ").replace("\r", " ");
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


    /**
     * ------------------------------------------------------------------------
     * Interactive Short READ Queries
     * ------------------------------------------------------------------------
     */
    /**
     * Given a start Person, retrieve their first name, last name, birthday, IP
     * address, browser, and city of residence.[1]
     */

    public static class ShortQuery1PersonProfile extends RedisGraphCypherSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {
        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getShortQuery1PersonProfile(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult(Record record) throws ParseException {
            String firstName = record.getString(0);
            String lastName = record.getString(1);
            int birthday = record.getValue(2);
            String locationIP = record.getString(3);
            String browserUsed = record.getString(4);
            int cityId = record.getValue(5);
            String gender = record.getValue(6);
            int creationDate =record.getValue(7);
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

    /**
     * Given a start Person, retrieve the last 10 Messages (Posts or Comments)
     * created by that user. For each message, return that message, the original
     * post in its conversation, and the author of that post. If any of the
     * Messages is a Post, then the original Post will be the same Message, i.e.,
     * that Message will appear twice in that result. Order results descending by
     * message creation date, then descending by message identifier.[1]
     */
    public static class ShortQuery2PersonPosts extends RedisGraphCypherListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getShortQuery2PersonPosts(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcShortQuery2PersonPostsResult convertSingleResult(Record record) throws ParseException {
            int messageId =  record.getValue(0);
            String messageContent = record.getString(1);
            int messageCreationDate =  record.getValue(2);
            int originalPostId =  record.getValue(3);
            int originalPostAuthorId =  record.getValue(4);
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

    /**
     * Given a start Person, retrieve all of their friends, and the date at which
     * they became friends. Order results descending by friendship creation date,
     * then ascending by friend identifier.[1]
     */
    public static class ShortQuery3PersonFriends extends RedisGraphCypherListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getShortQuery3PersonFriends(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult convertSingleResult(Record record) throws ParseException {
            int personId =  record.getValue(0);
            String firstName = record.getString(1);
            String lastName = record.getString(2);
            int friendshipCreationDate = record.getValue(3);
            return new LdbcShortQuery3PersonFriendsResult(
                    personId,
                    firstName,
                    lastName,
                    friendshipCreationDate);
        }
    }

    /**
     * Given a Message (Post or Comment), retrieve its content and creation
     * date.[1]
     */
    public static class ShortQuery4MessageContent extends RedisGraphCypherSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {


        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getShortQuery4MessageContent(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult(Record record) throws ParseException {
            // Pay attention, the spec's and the implementation's parameter orders are different.
            int messageCreationDate =  record.getValue(0);
            String messageContent = record.getString(1);
            return new LdbcShortQuery4MessageContentResult(
                    messageContent,
                    messageCreationDate);
        }
    }

    /**
     * Given a Message (Post or Comment), retrieve its author.[1]
     */
    public static class ShortQuery5MessageCreator extends RedisGraphCypherSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getShortQuery5MessageCreator(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult convertSingleResult(Record record) {
            int personId =  record.getValue(0);
            String firstName = record.getString(1);
            String lastName = record.getString(2);
            return new LdbcShortQuery5MessageCreatorResult(
                    personId,
                    firstName,
                    lastName);
        }
    }

    /**
     * Given a Message (Post or Comment), retrieve the Forum that contains it and
     * the Person that moderates that forum. Since comments are not directly
     * contained in forums, for comments, return the forum containing the
     * original post in the thread which the comment is replying to.[1]
     */
    public static class ShortQuery6MessageForum extends RedisGraphCypherSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getShortQuery6MessageForum(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcShortQuery6MessageForumResult convertSingleResult(Record record) {
            int forumId = record.getValue(0);
            String forumTitle = record.getString(1);
            int moderatorId = record.getValue(2);
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

    /**
     * Given a Message (Post or Comment), retrieve the (1-hop) Comments that
     * reply to it. In addition, return a boolean flag indicating if the author
     * of the reply knows the author of the original message. If author is same
     * as original author, return false for "knows" flag. Order results
     * descending by creation date, then ascending by author identifier.[1]
     */
    public static class ShortQuery7MessageReplies extends RedisGraphCypherListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getShortQuery7MessageReplies(operation).replace("\n", " ").replace("\r", " ");
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult convertSingleResult(Record record) throws ParseException {

            long commentId = Long.parseLong(record.getString(0));
            String commentContent = record.getString(1);
            long commentCreationDate = Long.parseLong(record.getString(2));
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

    /**
     * ------------------------------------------------------------------------
     * Interactive Update Queries (WRITE)
     * ------------------------------------------------------------------------
     */

    /**
     * Add a Person to the social network. [1]
     */
    public static class Update1AddPersonRedisGraph extends RedisGraphCypherUpdateOperationHandler<LdbcUpdate1AddPerson> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate1AddPerson operation) {
            return state.getQueryStore().getUpdate1Single(operation).replace("\n", " ").replace("\r", " ");
        }
    }

    /**
     * Add a Like to a Post of the social network.[1]
     */
    public static class Update2AddPostLikeRedisGraph extends RedisGraphCypherUpdateOperationHandler<LdbcUpdate2AddPostLike> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate2AddPostLike operation) {
            return state.getQueryStore().getUpdate2(operation).replace("\n", " ").replace("\r", " ");
        }
    }

    /**
     * Add a Like to a Comment of the social network.[1]
     */
    public static class Update3AddCommentLikeRedisGraph extends RedisGraphCypherUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate3AddCommentLike operation) {
            return state.getQueryStore().getUpdate3(operation).replace("\n", " ").replace("\r", " ");
        }
    }

    /**
     * Add a Forum to the social network.[1]
     */
    public static class Update4AddForumRedisGraph extends RedisGraphCypherUpdateOperationHandler<LdbcUpdate4AddForum> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate4AddForum operation) {
            return state.getQueryStore().getUpdate4Single(operation).replace("\n", " ").replace("\r", " ");
        }
    }

    /**
     * Add a Forum membership to the social network.[1]
     */
    public static class Update5AddForumMembershipRedisGraph extends RedisGraphCypherUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate5AddForumMembership operation) {
            return state.getQueryStore().getUpdate5(operation).replace("\n", " ").replace("\r", " ");
        }
    }

    /**
     * Add a Post to the social network.[1]
     */
    public static class Update6AddPostRedisGraph extends RedisGraphCypherUpdateOperationHandler<LdbcUpdate6AddPost> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate6AddPost operation) {
            return state.getQueryStore().getUpdate6Single(operation).replace("\n", " ").replace("\r", " ");
        }
    }

    /**
     * Add a Comment replying to a Post/Comment to the social network.[1]
     */
    public static class Update7AddCommentRedisGraph extends RedisGraphCypherUpdateOperationHandler<LdbcUpdate7AddComment> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate7AddComment operation) {
            return state.getQueryStore().getUpdate7Single(operation).replace("\n", " ").replace("\r", " ");
        }
    }

    /**
     * Add a friendship relation to the social network.[1]
     */
    public static class Update8AddFriendshipRedisGraph extends RedisGraphCypherUpdateOperationHandler<LdbcUpdate8AddFriendship> {

        @Override
        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcUpdate8AddFriendship operation) {
            return state.getQueryStore().getUpdate8(operation).replace("\n", " ").replace("\r", " ");
        }
    }
//
//    // BI queries
//
//    public static class BiQuery1 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery1PostingSummary operation) {
//            return state.getQueryStore().getQuery1(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(Record record) {
//            int year = Integer.parseInt(record.getString(0));
//            boolean isComment = Boolean.parseBoolean(record.getString(1));
//            int size = Integer.parseInt(record.getString(2));
//            long count = Long.parseLong(record.getString(3));
//            int avgLen = Integer.parseInt(record.getString(4));
//            int total = Integer.parseInt(record.getString(5));
//            double pct = Double.parseDouble(record.getString(6));
//
//            return new LdbcSnbBiQuery1PostingSummaryResult(year, isComment, size, count, avgLen, total, (float) pct);
//        }
//
//    }
//
//    public static class BiQuery2 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery2TopTags operation) {
//            return state.getQueryStore().getQuery2(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery2TopTagsResult convertSingleResult(Record record) {
//            String country = record.getString(0);
//            int month = Integer.parseInt(record.getString(1));
//            String gender = record.getString(2);
//            int ageGroup = Integer.parseInt(record.getString(3));
//            String tag = record.getString(4);
//            int count = Integer.parseInt(record.getString(5));
//            return new LdbcSnbBiQuery2TopTagsResult(country, month, gender, ageGroup, tag, count);
//        }
//
//    }
//
//    public static class BiQuery3 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery3TagEvolution operation) {
//            return state.getQueryStore().getQuery3(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(Record record) {
//            String tagName = record.getString(0);
//            int countA = Integer.parseInt(record.getString(1));
//            int countB = Integer.parseInt(record.getString(2));
//            int diffCount = Integer.parseInt(record.getString(3));
//            return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countA, countB, diffCount);
//        }
//
//    }
//
//    public static class BiQuery4 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery4PopularCountryTopics operation) {
//            return state.getQueryStore().getQuery4(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(Record record) throws ParseException {
//            long forumId = Long.parseLong(record.getString(0));
//            String title = record.getString(1);
//            //TODO: RedisGraph fix me
//            long creationDate = Long.parseLong(record.getString(2)); // CypherConverter.convertLongTimestampToEpoch(record.get(2).asLong());
//            long moderator = Long.parseLong(record.getString(3));
//            int count = Integer.parseInt(record.getString(4));
//            return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, title, creationDate, moderator, count);
//        }
//
//    }
//
//    public static class BiQuery5 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery5TopCountryPosters operation) {
//            return state.getQueryStore().getQuery5(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(Record record) throws ParseException {
//            long personId = Long.parseLong(record.getString(0));
//            String firstName = record.getString(1);
//            String lastName = record.getString(2);
//            //TODO: RedisGraph fix me
//            long creationDate = Long.parseLong(record.getString(3)); // CypherConverter.convertLongTimestampToEpoch(record.get(3).asLong());
//            int count = Integer.parseInt(record.getString(4));
//            return new LdbcSnbBiQuery5TopCountryPostersResult(personId, firstName, lastName, creationDate, count);
//        }
//
//    }
//
//    public static class BiQuery6 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery6ActivePosters operation) {
//            return state.getQueryStore().getQuery6(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(Record record) {
//            long personId = Long.parseLong(record.getString(0));
//            int postCount = Integer.parseInt(record.getString(1));
//            int replyCount = Integer.parseInt(record.getString(2));
//            int likeCount = Integer.parseInt(record.getString(3));
//            int score = Integer.parseInt(record.getString(4));
//            return new LdbcSnbBiQuery6ActivePostersResult(personId, postCount, replyCount, likeCount, score);
//        }
//
//    }
//
//    public static class BiQuery7 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
//            return state.getQueryStore().getQuery7(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(Record record) {
//            long personId = Long.parseLong(record.getString(0));
//            int score = Integer.parseInt(record.getString(1));
//            return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, score);
//        }
//
//    }
//
//    public static class BiQuery8 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery8RelatedTopics operation) {
//            return state.getQueryStore().getQuery8(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(Record record) {
//            String tag = record.getString(0);
//            int count = Integer.parseInt(record.getString(1));
//            return new LdbcSnbBiQuery8RelatedTopicsResult(tag, count);
//        }
//
//    }
//
//    public static class BiQuery9 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery9RelatedForums operation) {
//            return state.getQueryStore().getQuery9(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(Record record) {
//            long forumId = Long.parseLong(record.getString(0));
//            int sumA = Integer.parseInt(record.getString(1));
//            int sumB = Integer.parseInt(record.getString(2));
//            return new LdbcSnbBiQuery9RelatedForumsResult(forumId, sumA, sumB);
//        }
//
//    }
//
//    public static class BiQuery10 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery10TagPerson operation) {
//            return state.getQueryStore().getQuery10(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery10TagPersonResult convertSingleResult(Record record) {
//            long personId = Long.parseLong(record.getString(0));
//            int score = Integer.parseInt(record.getString(1));
//            int friendsScore = Integer.parseInt(record.getString(2));
//            return new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
//        }
//
//    }
//
//    public static class BiQuery11 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery11UnrelatedReplies operation) {
//            return state.getQueryStore().getQuery11(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(Record record) {
//            long personId = Long.parseLong(record.getString(0));
//            String tagName = record.getString(1);
//            int countLikes = Integer.parseInt(record.getString(2));
//            int countReplies = Integer.parseInt(record.getString(3));
//            return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, countLikes, countReplies);
//        }
//
//    }
//
//    public static class BiQuery12 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery12TrendingPosts operation) {
//            return state.getQueryStore().getQuery12(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(Record record) throws ParseException {
//            long personId = Long.parseLong(record.getString(0));
//            //TODO: RedisGraph fix me
//            long creationDate = Long.parseLong(record.getString(1)); // CypherConverter.convertLongTimestampToEpoch(record.get(1).asLong());
//            String firstName = record.getString(2);
//            String lastName = record.getString(3);
//            int likeCount = Integer.parseInt(record.getString(4));
//            return new LdbcSnbBiQuery12TrendingPostsResult(personId, creationDate, firstName, lastName, likeCount);
//        }
//    }
//
//    public static class BiQuery13 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
//            return state.getQueryStore().getQuery13(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(Record record) {
//            int year = Integer.parseInt(record.getString(0));
//            int month = Integer.parseInt(record.getString(1));
//            //TODO: RedisGraph fix me
//            final List<List<Object>> tagPopularitiesRaw = new ArrayList<>();// record.get(2).asList(Values.ofList());
//
//            final List<LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity> tagPopularities = new ArrayList<>();
//            for (List<Object> tagPopularityRaw : tagPopularitiesRaw) {
//                final String tag = (String) tagPopularityRaw.get(0);
//                final int popularity = Ints.saturatedCast((long) tagPopularityRaw.get(1));
//                tagPopularities.add(new LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity(tag, popularity));
//            }
//
//            return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, tagPopularities);
//        }
//    }
//
//    public static class BiQuery14 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery14TopThreadInitiators operation) {
//            return state.getQueryStore().getQuery14(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(Record record) {
//            long personId = Long.parseLong(record.getString(0));
//            String firstName = record.getString(1);
//            String lastName = record.getString(2);
//            int count = Integer.parseInt(record.getString(3));
//            int threadCount = Integer.parseInt(record.getString(4));
//            return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, firstName, lastName, count, threadCount);
//        }
//    }
//
//    public static class BiQuery15 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery15SocialNormals operation) {
//            return state.getQueryStore().getQuery15(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(Record record) {
//            long personId = Long.parseLong(record.getString(0));
//            int count = Integer.parseInt(record.getString(1));
//            return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
//        }
//    }
//
//    public static class BiQuery16 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
//            return state.getQueryStore().getQuery16(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(Record record) {
//            long personId = Long.parseLong(record.getString(0));
//            String tag = record.getString(1);
//            int count = Integer.parseInt(record.getString(2));
//            return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tag, count);
//        }
//    }
//
//    public static class BiQuery17 extends RedisGraphCypherSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery17FriendshipTriangles operation) {
//            return state.getQueryStore().getQuery17(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(Record record) {
//            int count = Integer.parseInt(record.getString(0));
//            return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
//        }
//    }
//
//    public static class BiQuery18 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery18PersonPostCounts operation) {
//            return state.getQueryStore().getQuery18(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(Record record) {
//            int postCount = Integer.parseInt(record.getString(0));
//            int count = Integer.parseInt(record.getString(1));
//            return new LdbcSnbBiQuery18PersonPostCountsResult(postCount, count);
//        }
//    }
//
//
//    public static class BiQuery19 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery19StrangerInteraction operation) {
//            return state.getQueryStore().getQuery19(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(Record record) {
//            long personId = Long.parseLong(record.getString(0));
//            int strangerCount = Integer.parseInt(record.getString(1));
//            int count = Integer.parseInt(record.getString(2));
//            return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, count);
//        }
//    }
//
//    public static class BiQuery20 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery20HighLevelTopics operation) {
//            return state.getQueryStore().getQuery20(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(Record record) {
//            String tagClass = record.getString(0);
//            int count = Integer.parseInt(record.getString(1));
//            return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClass, count);
//        }
//    }
//
//    public static class BiQuery21 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery21Zombies operation) {
//            return state.getQueryStore().getQuery21(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery21ZombiesResult convertSingleResult(Record record) {
//            long personId = Long.parseLong(record.getString(0));
//            int zombieCount = Integer.parseInt(record.getString(1));
//            int realCount = Integer.parseInt(record.getString(2));
//            double score = Double.parseDouble(record.getString(3));
//            return new LdbcSnbBiQuery21ZombiesResult(personId, zombieCount, realCount, score);
//        }
//    }
//
//    public static class BiQuery22 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery22InternationalDialog operation) {
//            return state.getQueryStore().getQuery22(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(Record record) {
//            long personIdA = Long.parseLong(record.getString(0));
//            long personIdB = Long.parseLong(record.getString(1));
//            String city1Name = record.getString(2);
//            int score = Integer.parseInt(record.getString(3));
//            return new LdbcSnbBiQuery22InternationalDialogResult(personIdA, personIdB, city1Name, score);
//        }
//    }
//
//    public static class BiQuery23 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery23HolidayDestinations operation) {
//            return state.getQueryStore().getQuery23(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(Record record) {
//            int messageCount = Integer.parseInt(record.getString(0));
//            String country = record.getString(1);
//            int month = Integer.parseInt(record.getString(2));
//            return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, country, month);
//        }
//    }
//
//
//    public static class BiQuery24 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery24MessagesByTopic operation) {
//            return state.getQueryStore().getQuery24(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(Record record) {
//            int messageCount = Integer.parseInt(record.getString(0));
//            int likeCount = Integer.parseInt(record.getString(1));
//            int year = Integer.parseInt(record.getString(2));
//            int month = Integer.parseInt(record.getString(3));
//            String continent = record.getString(4);
//            return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continent);
//        }
//    }
//
//    public static class BiQuery25 extends RedisGraphCypherListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult> {
//
//        @Override
//        public String getQueryString(RedisGraphCypherDbConnectionState state, LdbcSnbBiQuery25WeightedPaths operation) {
//            return state.getQueryStore().getQuery25(operation).replace("\n", " ").replace("\r", " ");
//        }
//
//        @Override
//        public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(Record record) {
//            //TODO: RedisGraph fix me
//            List<Long> personIds = new ArrayList<>(); //record.get(0).asList(Values.ofLong());
//            double weight = Double.parseDouble(record.getString(1));
//            return new LdbcSnbBiQuery25WeightedPathsResult(personIds, weight);
//        }
//    }


}

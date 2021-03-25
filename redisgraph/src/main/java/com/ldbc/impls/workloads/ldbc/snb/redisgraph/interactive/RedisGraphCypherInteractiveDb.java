package com.ldbc.impls.workloads.ldbc.snb.redisgraph.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.RedisGraphCypherDb;

import java.util.Map;

public class RedisGraphCypherInteractiveDb extends RedisGraphCypherDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

//        TODO: As soon as we fully support IC enable this
//        registerOperationHandler(LdbcQuery1.class, InteractiveQuery1.class);
        registerOperationHandler(LdbcQuery2.class, InteractiveQuery2.class);
//        registerOperationHandler(LdbcQuery3.class, InteractiveQuery3.class);
//        registerOperationHandler(LdbcQuery4.class, InteractiveQuery4.class);
//        registerOperationHandler(LdbcQuery5.class, InteractiveQuery5.class);
        registerOperationHandler(LdbcQuery6.class, InteractiveQuery6.class);
//        registerOperationHandler(LdbcQuery7.class, InteractiveQuery7.class);
        registerOperationHandler(LdbcQuery8.class, InteractiveQuery8.class);
        registerOperationHandler(LdbcQuery9.class, InteractiveQuery9.class);
//        registerOperationHandler(LdbcQuery10.class, InteractiveQuery10.class);
        registerOperationHandler(LdbcQuery11.class, InteractiveQuery11.class);
        registerOperationHandler(LdbcQuery12.class, InteractiveQuery12.class);
//        registerOperationHandler(LdbcQuery13.class, InteractiveQuery13.class);
//        registerOperationHandler(LdbcQuery14.class, InteractiveQuery14.class);


        registerOperationHandler(LdbcShortQuery1PersonProfile.class, ShortQuery1PersonProfile.class);
        registerOperationHandler(LdbcShortQuery2PersonPosts.class, ShortQuery2PersonPosts.class);
        registerOperationHandler(LdbcShortQuery3PersonFriends.class, ShortQuery3PersonFriends.class);
        registerOperationHandler(LdbcShortQuery4MessageContent.class, ShortQuery4MessageContent.class);
        registerOperationHandler(LdbcShortQuery5MessageCreator.class, ShortQuery5MessageCreator.class);
        registerOperationHandler(LdbcShortQuery6MessageForum.class, ShortQuery6MessageForum.class);
        //          TODO: As soon as we fully support IS enable this
//        registerOperationHandler(LdbcShortQuery7MessageReplies.class, ShortQuery7MessageReplies.class);

        registerOperationHandler(LdbcUpdate1AddPerson.class, Update1AddPersonRedisGraph.class);
        registerOperationHandler(LdbcUpdate2AddPostLike.class, Update2AddPostLikeRedisGraph.class);
        registerOperationHandler(LdbcUpdate3AddCommentLike.class, Update3AddCommentLikeRedisGraph.class);
        registerOperationHandler(LdbcUpdate4AddForum.class, Update4AddForumRedisGraph.class);
        registerOperationHandler(LdbcUpdate5AddForumMembership.class, Update5AddForumMembershipRedisGraph.class);
        registerOperationHandler(LdbcUpdate6AddPost.class, Update6AddPostRedisGraph.class);
        registerOperationHandler(LdbcUpdate7AddComment.class, Update7AddCommentRedisGraph.class);
        registerOperationHandler(LdbcUpdate8AddFriendship.class, Update8AddFriendshipRedisGraph.class);
    }

}

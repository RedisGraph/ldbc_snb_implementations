package com.ldbc.impls.workloads.ldbc.snb.redisgraph.bi;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.RedisGraphCypherDb;

import java.util.Map;

public class RedisGraphCypherBiDb extends RedisGraphCypherDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

        registerOperationHandler(LdbcSnbBiQuery1PostingSummary.class, BiQuery1.class);
        registerOperationHandler(LdbcSnbBiQuery2TopTags.class, BiQuery2.class);
        registerOperationHandler(LdbcSnbBiQuery3TagEvolution.class, BiQuery3.class);
        registerOperationHandler(LdbcSnbBiQuery4PopularCountryTopics.class, BiQuery4.class);
        registerOperationHandler(LdbcSnbBiQuery5TopCountryPosters.class, BiQuery5.class);
        registerOperationHandler(LdbcSnbBiQuery6ActivePosters.class, BiQuery6.class);
        registerOperationHandler(LdbcSnbBiQuery7AuthoritativeUsers.class, BiQuery7.class);
        registerOperationHandler(LdbcSnbBiQuery8RelatedTopics.class, BiQuery8.class);
        registerOperationHandler(LdbcSnbBiQuery9RelatedForums.class, BiQuery9.class);
        registerOperationHandler(LdbcSnbBiQuery10TagPerson.class, BiQuery10.class);
        registerOperationHandler(LdbcSnbBiQuery11UnrelatedReplies.class, BiQuery11.class);
        registerOperationHandler(LdbcSnbBiQuery12TrendingPosts.class, BiQuery12.class);
        registerOperationHandler(LdbcSnbBiQuery13PopularMonthlyTags.class, BiQuery13.class);
        registerOperationHandler(LdbcSnbBiQuery14TopThreadInitiators.class, BiQuery14.class);
        registerOperationHandler(LdbcSnbBiQuery15SocialNormals.class, BiQuery15.class);
        registerOperationHandler(LdbcSnbBiQuery16ExpertsInSocialCircle.class, BiQuery16.class);
        registerOperationHandler(LdbcSnbBiQuery17FriendshipTriangles.class, BiQuery17.class);
        registerOperationHandler(LdbcSnbBiQuery18PersonPostCounts.class, BiQuery18.class);
        registerOperationHandler(LdbcSnbBiQuery19StrangerInteraction.class, BiQuery19.class);
        registerOperationHandler(LdbcSnbBiQuery20HighLevelTopics.class, BiQuery20.class);
        registerOperationHandler(LdbcSnbBiQuery21Zombies.class, BiQuery21.class);
        registerOperationHandler(LdbcSnbBiQuery22InternationalDialog.class, BiQuery22.class);
        registerOperationHandler(LdbcSnbBiQuery23HolidayDestinations.class, BiQuery23.class);
        registerOperationHandler(LdbcSnbBiQuery24MessagesByTopic.class, BiQuery24.class);
        registerOperationHandler(LdbcSnbBiQuery25WeightedPaths.class, BiQuery25.class);
    }

}

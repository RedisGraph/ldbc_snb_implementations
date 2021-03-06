package com.ldbc.impls.workloads.ldbc.snb.cypher.bi;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDb;

import java.util.Map;

public class CypherBiDb extends CypherDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

        registerOperationHandler(LdbcSnbBiQuery1PostingSummary.class, BiQuery1.class);
        registerOperationHandler(LdbcSnbBiQuery2TagEvolution.class, BiQuery2.class);
        registerOperationHandler(LdbcSnbBiQuery3PopularCountryTopics.class, BiQuery3.class);
        registerOperationHandler(LdbcSnbBiQuery4TopCountryPosters.class, BiQuery4.class);
        registerOperationHandler(LdbcSnbBiQuery5ActivePosters.class, BiQuery5.class);
        registerOperationHandler(LdbcSnbBiQuery6AuthoritativeUsers.class, BiQuery6.class);
        registerOperationHandler(LdbcSnbBiQuery7RelatedTopics.class, BiQuery7.class);
        registerOperationHandler(LdbcSnbBiQuery8TagPerson.class, BiQuery8.class);
        registerOperationHandler(LdbcSnbBiQuery9TopThreadInitiators.class, BiQuery9.class);
        registerOperationHandler(LdbcSnbBiQuery10ExpertsInSocialCircle.class, BiQuery10.class);
        registerOperationHandler(LdbcSnbBiQuery11FriendshipTriangles.class, BiQuery11.class);
        registerOperationHandler(LdbcSnbBiQuery12PersonPostCounts.class, BiQuery12.class);
        registerOperationHandler(LdbcSnbBiQuery13Zombies.class, BiQuery13.class);
        registerOperationHandler(LdbcSnbBiQuery14InternationalDialog.class, BiQuery14.class);
        registerOperationHandler(LdbcSnbBiQuery15WeightedPaths.class, BiQuery15.class);
        registerOperationHandler(LdbcSnbBiQuery16FakeNewsDetection.class, BiQuery16.class);
        registerOperationHandler(LdbcSnbBiQuery17InformationPropagationAnalysis.class, BiQuery17.class);
        registerOperationHandler(LdbcSnbBiQuery18FriendRecommendation.class, BiQuery18.class);
        registerOperationHandler(LdbcSnbBiQuery19InteractionPathBetweenCities.class, BiQuery19.class);
        registerOperationHandler(LdbcSnbBiQuery20Recruitment.class, BiQuery20.class);
    }

}

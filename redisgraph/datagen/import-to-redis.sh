#!/bin/bash

source ./environment-variables-redisgraph.sh

echo "ensuring graph ${GRAPHNAME} does not exist. Time: $(date)"
redis-cli del ${GRAPHNAME}

echo "starting loading. Time: $(date)"

redisgraph-bulk-loader ${GRAPHNAME} -o \| \
  --enforce-schema \
  --skip-invalid-nodes \
  --skip-invalid-edges \
  -n ${REDISGRAPH_DATA_DIR}/static/City.csv \
  -n ${REDISGRAPH_DATA_DIR}/static/Country.csv \
  -n ${REDISGRAPH_DATA_DIR}/static/Continent.csv \
  -N Place ${REDISGRAPH_DATA_DIR}/static/Place.csv \
  -n ${REDISGRAPH_DATA_DIR}/static/Company.csv \
  -N University ${REDISGRAPH_DATA_DIR}/static/University.csv \
  -N Organisation ${REDISGRAPH_DATA_DIR}/static/Organisation.csv \
  -N TagClass ${REDISGRAPH_DATA_DIR}/static/tagclass${POSTFIX} \
  -N Tag ${REDISGRAPH_DATA_DIR}/static/tag${POSTFIX} \
  -N Forum ${REDISGRAPH_DATA_DIR}/dynamic/forum${POSTFIX} \
  -N Person ${REDISGRAPH_DATA_DIR}/dynamic/person${POSTFIX} \
  -N Message ${REDISGRAPH_DATA_DIR}/dynamic/comment${POSTFIX} \
  -N Message ${REDISGRAPH_DATA_DIR}/dynamic/post${POSTFIX} \
  -R IS_LOCATED_IN ${REDISGRAPH_DATA_DIR}/static/organisation_isLocatedIn_place${POSTFIX} \
  -R IS_PART_OF ${REDISGRAPH_DATA_DIR}/static/place_isPartOf_place${POSTFIX} \
  -R IS_SUBCLASS_OF ${REDISGRAPH_DATA_DIR}/static/tagclass_isSubclassOf_tagclass${POSTFIX} \
  -R HAS_TYPE ${REDISGRAPH_DATA_DIR}/static/tag_hasType_tagclass${POSTFIX} \
  -R HAS_CREATOR ${REDISGRAPH_DATA_DIR}/dynamic/comment_hasCreator_person${POSTFIX} \
  -R HAS_TAG ${REDISGRAPH_DATA_DIR}/dynamic/comment_hasTag_tag${POSTFIX} \
  -R IS_LOCATED_IN ${REDISGRAPH_DATA_DIR}/dynamic/comment_isLocatedIn_place${POSTFIX} \
  -R REPLY_OF ${REDISGRAPH_DATA_DIR}/dynamic/comment_replyOf_comment${POSTFIX} \
  -R REPLY_OF ${REDISGRAPH_DATA_DIR}/dynamic/comment_replyOf_post${POSTFIX} \
  -R CONTAINER_OF ${REDISGRAPH_DATA_DIR}/dynamic/forum_containerOf_post${POSTFIX} \
  -R HAS_MEMBER ${REDISGRAPH_DATA_DIR}/dynamic/forum_hasMember_person${POSTFIX} \
  -R HAS_MODERATOR ${REDISGRAPH_DATA_DIR}/dynamic/forum_hasModerator_person${POSTFIX} \
  -R HAS_TAG ${REDISGRAPH_DATA_DIR}/dynamic/forum_hasTag_tag${POSTFIX} \
  -R HAS_INTEREST ${REDISGRAPH_DATA_DIR}/dynamic/person_hasInterest_tag${POSTFIX} \
  -R IS_LOCATED_IN ${REDISGRAPH_DATA_DIR}/dynamic/person_isLocatedIn_place${POSTFIX} \
  -R KNOWS ${REDISGRAPH_DATA_DIR}/dynamic/person_knows_person${POSTFIX} \
  -R LIKES ${REDISGRAPH_DATA_DIR}/dynamic/person_likes_comment${POSTFIX} \
  -R LIKES ${REDISGRAPH_DATA_DIR}/dynamic/person_likes_post${POSTFIX} \
  -R STUDY_AT ${REDISGRAPH_DATA_DIR}/dynamic/person_studyAt_organisation${POSTFIX} \
  -R WORK_AT ${REDISGRAPH_DATA_DIR}/dynamic/person_workAt_organisation${POSTFIX} \
  -R HAS_CREATOR ${REDISGRAPH_DATA_DIR}/dynamic/post_hasCreator_person${POSTFIX} \
  -R HAS_TAG ${REDISGRAPH_DATA_DIR}/dynamic/post_hasTag_tag${POSTFIX} \
  -R IS_LOCATED_IN ${REDISGRAPH_DATA_DIR}/dynamic/post_isLocatedIn_place${POSTFIX} | tee ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt

echo "finished loading. Time: $(date)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
echo "Retrieving memory info" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
redis-cli info memory >>./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt

echo "Creating Indices Time: $(date)"
redis-cli GRAPH.QUERY ${GRAPHNAME} "CREATE INDEX ON :Message(id)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
redis-cli GRAPH.QUERY ${GRAPHNAME} "CREATE INDEX ON :Comment(id)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
redis-cli GRAPH.QUERY ${GRAPHNAME} "CREATE INDEX ON :Forum(id)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
redis-cli GRAPH.QUERY ${GRAPHNAME} "CREATE INDEX ON :Organisation(id)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
redis-cli GRAPH.QUERY ${GRAPHNAME} "CREATE INDEX ON :Person(id)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
redis-cli GRAPH.QUERY ${GRAPHNAME} "CREATE INDEX ON :Place(id)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
redis-cli GRAPH.QUERY ${GRAPHNAME} "CREATE INDEX ON :Post(id)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
redis-cli GRAPH.QUERY ${GRAPHNAME} "CREATE INDEX ON :Tag(id)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
redis-cli GRAPH.QUERY ${GRAPHNAME} "CREATE INDEX ON :TagClass(id)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt

echo "Finished Indices Time: $(date)" >> ./redisgraph-load-${GRAPHNAME}-SF_${SF}.txt
echo "Finished Indices Time: $(date)"

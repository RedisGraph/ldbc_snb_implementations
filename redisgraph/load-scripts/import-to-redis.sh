#!/bin/bash

echo "starting preprocessing"
source ./../environment-variables-redisgraph.sh

python3 ${PATH_TO_LOADER}/bulk_insert/bulk_insert.py ${GRAPHNAME} -o \| \
-n ${REDISGRAPH_DATA_DIR}/static/City.csv \
-n ${REDISGRAPH_DATA_DIR}/static/Company.csv \
-n ${REDISGRAPH_DATA_DIR}/static/Continent.csv \
-n ${REDISGRAPH_DATA_DIR}/static/Country.csv \
-n ${REDISGRAPH_DATA_DIR}/static/University.csv \
-N TagClass ${REDISGRAPH_DATA_DIR}/static/tagclass${POSTFIX} \
-N Tag ${REDISGRAPH_DATA_DIR}/static/tag${POSTFIX} \
-R IS_LOCATED_IN ${REDISGRAPH_DATA_DIR}/static/organisation_isLocatedIn_place${POSTFIX} \
-R IS_PART_OF ${REDISGRAPH_DATA_DIR}/static/place_isPartOf_place${POSTFIX} \
-R IS_SUBCLASS_OF ${REDISGRAPH_DATA_DIR}/static/tagclass_isSubclassOf_tagclass${POSTFIX} \
-R HAS_TYPE ${REDISGRAPH_DATA_DIR}/static/tag_hasType_tagclass${POSTFIX} \
-N Comment ${REDISGRAPH_DATA_DIR}/dynamic/comment${POSTFIX} \
-R HAS_CREATOR ${REDISGRAPH_DATA_DIR}/dynamic/comment_hasCreator_person${POSTFIX} \
-R HAS_TAG ${REDISGRAPH_DATA_DIR}/dynamic/comment_hasTag_tag${POSTFIX} \
-R IS_LOCATED_IN ${REDISGRAPH_DATA_DIR}/dynamic/comment_isLocatedIn_place${POSTFIX} \
-R REPLY_OF ${REDISGRAPH_DATA_DIR}/dynamic/comment_replyOf_comment${POSTFIX} \
-R REPLY_OF ${REDISGRAPH_DATA_DIR}/dynamic/comment_replyOf_post${POSTFIX} \
-N Forum ${REDISGRAPH_DATA_DIR}/dynamic/forum${POSTFIX} \
-N Person ${REDISGRAPH_DATA_DIR}/dynamic/person${POSTFIX} \
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
-N Post ${REDISGRAPH_DATA_DIR}/dynamic/post${POSTFIX} \
-R HAS_CREATOR ${REDISGRAPH_DATA_DIR}/dynamic/post_hasCreator_person${POSTFIX} \
-R HAS_TAG ${REDISGRAPH_DATA_DIR}/dynamic/post_hasTag_tag${POSTFIX} \
-R IS_LOCATED_IN ${REDISGRAPH_DATA_DIR}/dynamic/post_isLocatedIn_place${POSTFIX} \

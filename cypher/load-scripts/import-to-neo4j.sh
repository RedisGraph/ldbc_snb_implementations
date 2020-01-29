#!/bin/bash

source ./../environment-variables-neo4j.sh

$NEO4J_HOME/bin/neo4j-import --into $NEO4J_DB_DIR \
  --id-type=INTEGER \
  --nodes:Place "${NEO4J_DATA_DIR}/static/place${POSTFIX}" \
  --nodes:Organisation "${NEO4J_DATA_DIR}/static/organisation${POSTFIX}" \
  --nodes:TagClass "${NEO4J_DATA_DIR}/static/tagclass${POSTFIX}" \
  --nodes:Tag "${NEO4J_DATA_DIR}/static/tag${POSTFIX}" \
  --nodes:Message:Comment "${NEO4J_DATA_DIR}/dynamic/comment${POSTFIX}" \
  --nodes:Forum "${NEO4J_DATA_DIR}/dynamic/forum${POSTFIX}" \
  --nodes:Person "${NEO4J_DATA_DIR}/dynamic/person${POSTFIX}" \
  --nodes:Message:Post "${NEO4J_DATA_DIR}/dynamic/post${POSTFIX}" \
  --relationships:IS_PART_OF "${NEO4J_DATA_DIR}/static/place_isPartOf_place${POSTFIX}" \
  --relationships:IS_SUBCLASS_OF "${NEO4J_DATA_DIR}/static/tagclass_isSubclassOf_tagclass${POSTFIX}" \
  --relationships:IS_LOCATED_IN "${NEO4J_DATA_DIR}/static/organisation_isLocatedIn_place${POSTFIX}" \
  --relationships:HAS_TYPE "${NEO4J_DATA_DIR}/static/tag_hasType_tagclass${POSTFIX}" \
  --relationships:HAS_CREATOR "${NEO4J_DATA_DIR}/dynamic/comment_hasCreator_person${POSTFIX}" \
  --relationships:IS_LOCATED_IN "${NEO4J_DATA_DIR}/dynamic/comment_isLocatedIn_place${POSTFIX}" \
  --relationships:REPLY_OF "${NEO4J_DATA_DIR}/dynamic/comment_replyOf_comment${POSTFIX}" \
  --relationships:REPLY_OF "${NEO4J_DATA_DIR}/dynamic/comment_replyOf_post${POSTFIX}" \
  --relationships:CONTAINER_OF "${NEO4J_DATA_DIR}/dynamic/forum_containerOf_post${POSTFIX}" \
  --relationships:HAS_MEMBER "${NEO4J_DATA_DIR}/dynamic/forum_hasMember_person${POSTFIX}" \
  --relationships:HAS_MODERATOR "${NEO4J_DATA_DIR}/dynamic/forum_hasModerator_person${POSTFIX}" \
  --relationships:HAS_TAG "${NEO4J_DATA_DIR}/dynamic/forum_hasTag_tag${POSTFIX}" \
  --relationships:HAS_INTEREST "${NEO4J_DATA_DIR}/dynamic/person_hasInterest_tag${POSTFIX}" \
  --relationships:IS_LOCATED_IN "${NEO4J_DATA_DIR}/dynamic/person_isLocatedIn_place${POSTFIX}" \
  --relationships:KNOWS "${NEO4J_DATA_DIR}/dynamic/person_knows_person${POSTFIX}" \
  --relationships:LIKES "${NEO4J_DATA_DIR}/dynamic/person_likes_comment${POSTFIX}" \
  --relationships:LIKES "${NEO4J_DATA_DIR}/dynamic/person_likes_post${POSTFIX}" \
  --relationships:HAS_CREATOR "${NEO4J_DATA_DIR}/dynamic/post_hasCreator_person${POSTFIX}" \
  --relationships:HAS_TAG "${NEO4J_DATA_DIR}/dynamic/comment_hasTag_tag${POSTFIX}" \
  --relationships:HAS_TAG "${NEO4J_DATA_DIR}/dynamic/post_hasTag_tag${POSTFIX}" \
  --relationships:IS_LOCATED_IN "${NEO4J_DATA_DIR}/dynamic/post_isLocatedIn_place${POSTFIX}" \
  --relationships:STUDY_AT "${NEO4J_DATA_DIR}/dynamic/person_studyAt_organisation${POSTFIX}" \
  --relationships:WORK_AT "${NEO4J_DATA_DIR}/dynamic/person_workAt_organisation${POSTFIX}" \
  --delimiter '|'

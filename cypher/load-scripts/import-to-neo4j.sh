#!/bin/bash

source ./../environment-variables-neo4j.sh

$NEO4J_HOME/bin/neo4j-admin import --verbose \
  --normalize-types=true \
  --id-type=INTEGER \
  --nodes="${NEO4J_DATA_DIR}/static/place${POSTFIX}_with_labels" \
  --nodes="${NEO4J_DATA_DIR}/static/organisation${POSTFIX}" \
  --nodes="${NEO4J_DATA_DIR}/static/tagclass${POSTFIX}_with_labels" \
  --nodes="${NEO4J_DATA_DIR}/static/tag${POSTFIX}_with_labels" \
  --nodes="${NEO4J_DATA_DIR}/dynamic/comment${POSTFIX}_with_labels" \
  --nodes="${NEO4J_DATA_DIR}/dynamic/forum${POSTFIX}_with_labels" \
  --nodes="${NEO4J_DATA_DIR}/dynamic/person${POSTFIX}_with_labels" \
  --nodes="${NEO4J_DATA_DIR}/dynamic/post${POSTFIX}_with_labels" \
  --relationships="${NEO4J_DATA_DIR}/static/place_isPartOf_place${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/static/tagclass_isSubclassOf_tagclass${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/static/organisation_isLocatedIn_place${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/static/tag_hasType_tagclass${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/comment_hasCreator_person${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/comment_isLocatedIn_place${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/comment_replyOf_comment${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/comment_replyOf_post${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/forum_containerOf_post${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/forum_hasMember_person${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/forum_hasModerator_person${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/forum_hasTag_tag${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/person_hasInterest_tag${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/person_isLocatedIn_place${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/person_knows_person${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/person_likes_comment${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/person_likes_post${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/post_hasCreator_person${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/comment_hasTag_tag${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/post_hasTag_tag${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/post_isLocatedIn_place${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/person_studyAt_organisation${POSTFIX}_with_types" \
  --relationships="${NEO4J_DATA_DIR}/dynamic/person_workAt_organisation${POSTFIX}_with_types" \
  --delimiter '|'


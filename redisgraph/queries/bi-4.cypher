MATCH
  (:Place {name: $country})<-[:IS_PART_OF]-(:Place)<-[:IS_LOCATED_IN]-
  (person:Person)<-[:HAS_MODERATOR]-(forum:Forum)-[:CONTAINER_OF]->
  (post:Message)-[:HAS_TAG]->(:Tag)-[:HAS_TYPE]->(:TagClass {name: $tagClass})
RETURN
  forum.id,
  forum.title,
  forum.creationDate,
  person.id,
  count(DISTINCT post) AS postCount
  ORDER BY
  count(DISTINCT post) DESC,
  forum.id ASC
  LIMIT 20
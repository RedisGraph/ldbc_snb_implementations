MATCH
  (:Person {id: $personId})-[:KNOWS*$minPathDistance..$maxPathDistance] -(person:Person)

WITH DISTINCT person
MATCH
  (person)-[:IS_LOCATED_IN]->(:Place)-[:IS_PART_OF]->(:Place {name: $country}),
  (person)<-[:HAS_CREATOR]-(message:Message)-[:HAS_TAG]->(:Tag)-[:HAS_TYPE]->
  (:TagClass {name: $tagClass})
MATCH
  (message)-[:HAS_TAG]->(tag:Tag)
RETURN
  person.id,
  tag.name,
  count(DISTINCT message) AS messageCount
  ORDER BY
  messageCount DESC,
  tag.name ASC,
  person.id ASC
  LIMIT 100

MATCH
  path=allShortestPaths((p1:Person {id: $person1Id})-[:KNOWS*]-(p2:Person {id: $person2Id}))
UNWIND relationships(path) AS k
WITH
  path,
  startNode(k) AS pA,
  endNode(k) AS pB,
  0 AS relationshipWeights
OPTIONAL MATCH
  (pA)<-[:HAS_CREATOR]-(c:Comment)-[:REPLY_OF]->(post:Post)-[:HAS_CREATOR]->(pB),
  (post)<-[:CONTAINER_OF]-(forum:Forum)
WHERE forum.creationDate >= $startDate AND forum.creationDate <= $endDate
WITH path, pA, pB, relationshipWeights + count(c)*1.0 AS relationshipWeights
OPTIONAL MATCH
  (pA)<-[:HAS_CREATOR]-(c1:Comment)-[:REPLY_OF]->(c2:Comment)-[:HAS_CREATOR]->(pB),
  (c2)-[:REPLY_OF*]->(:Post)<-[:CONTAINER_OF]-(forum:Forum)
WHERE forum.creationDate >= $startDate AND forum.creationDate <= $endDate
WITH path, pA, pB, relationshipWeights + count(c1)*0.5 AS relationshipWeights
OPTIONAL MATCH
  (pB)<-[:HAS_CREATOR]-(c:Comment)-[:REPLY_OF]->(post:Post)-[:HAS_CREATOR]->(pA),
  (post)<-[:CONTAINER_OF]-(forum:Forum)
WHERE forum.creationDate >= $startDate AND forum.creationDate <= $endDate
WITH path, pA, pB, relationshipWeights + count(c)*1.0 AS relationshipWeights
OPTIONAL MATCH
  (pB)<-[:HAS_CREATOR]-(c1:Comment)-[:REPLY_OF]->(c2:Comment)-[:HAS_CREATOR]->(pA),
  (c2)-[:REPLY_OF*]->(:Post)<-[:CONTAINER_OF]-(forum:Forum)
WHERE forum.creationDate >= $startDate AND forum.creationDate <= $endDate
WITH path, pA, pB, relationshipWeights + count(c1)*0.5 AS relationshipWeights
WITH
  [person IN nodes(path) | person.id] AS personIds,
  sum(relationshipWeights) AS weight
RETURN
  personIds,
  weight
ORDER BY
  weight DESC,
  personIds ASC

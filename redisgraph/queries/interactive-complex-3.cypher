MATCH (person:Person {id: $personId})-[:KNOWS*1..2]-(friend:Person)<-[:HAS_CREATOR]-(messageX:Message),
      (messageX)-[:IS_LOCATED_IN]->(countryX:Place)
  WHERE
  NOT(person = friend)
  AND NOT((friend)-[:IS_LOCATED_IN]->()-[:IS_PART_OF]->(countryX))
  AND countryX.name = $countryXName AND messageX.creationDate >= $startDate
  AND messageX.creationDate < $endDate
WITH friend, count(DISTINCT messageX) AS xCount
MATCH (friend)<-[:HAS_CREATOR]-(messageY:Message)-[:IS_LOCATED_IN]->(countryY:Place)
  WHERE
  countryY.name = $countryYName
  AND NOT((friend)-[:IS_LOCATED_IN]->()-[:IS_PART_OF]->(countryY))
  AND messageY.creationDate >= $startDate
  AND messageY.creationDate < $endDate
WITH
  friend.id,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  xCount,
  count(DISTINCT messageY) AS yCount
RETURN
  friend.id,
  personFirstName,
  personLastName,
  xCount,
  yCount,
  xCount + yCount AS count
  ORDER BY count DESC, toInteger(friend.id) ASC
  LIMIT 20

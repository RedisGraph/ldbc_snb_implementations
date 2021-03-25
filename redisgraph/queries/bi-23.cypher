MATCH
  (home:Place {name: $country})<-[:IS_PART_OF]-(:Place)<-[:IS_LOCATED_IN]-
  (:Person)<-[:HAS_CREATOR]-(message:Message)-[:IS_LOCATED_IN]->(destination:Place)
  WHERE home <> destination
WITH
  message,
  destination,
  message.creationDate / 100000000000 % 100 AS month
RETURN
  count(message) AS messageCount,
  destination.name,
  month
  ORDER BY
  messageCount DESC,
  destination.name ASC,
  month ASC
  LIMIT 100

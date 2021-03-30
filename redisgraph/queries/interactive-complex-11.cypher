MATCH (person:Person {id: $personId})-[:KNOWS*1..2]-(friend:Person)
  WHERE NOT(person = friend)
WITH DISTINCT friend
MATCH (friend)-[workAt:WORK_AT]->(company:Organisation)-[:IS_LOCATED_IN]->(:Place {name: $countryName})
  WHERE workAt.workFrom < $workFromYear
RETURN
  friend.id,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  company.name AS organizationName,
  workAt.workFrom AS organizationWorkFromYear
  ORDER BY organizationWorkFromYear ASC, toInteger(friend.id) ASC, organizationName DESC
  LIMIT 10

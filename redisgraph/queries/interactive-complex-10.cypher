MATCH (person:Person {id: $personId})-[:KNOWS*2..2]-(friend:Person)-[:IS_LOCATED_IN]->(city:Place)
  WHERE
  ((friend.birthday / 100 % 100 = $month AND friend.birthday % 100 >= 21) OR
  (friend.birthday / 100 % 100 = $nextMonth AND friend.birthday % 100 < 22))
  AND NOT(friend = person)
  AND NOT((friend)-[:KNOWS]-(person))
WITH DISTINCT friend, city, person
OPTIONAL MATCH (friend)<-[:HAS_CREATOR]-(post:Post)
OPTIONAL MATCH (post)-[:HAS_TAG]->(commonPost:Tag)<-[:HAS_INTEREST]-(person)
WITH friend, city, collect(post) AS posts, person, count(commonPost) AS commonPostCount
WITH
  friend,
  city,
  length(posts) AS postCount,
  commonPostCount
RETURN
  friend.id AS personId,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  commonPostCount - (postCount- commonPostCount) AS commonInterestScore,
friend.gender AS personGender,
city.name AS personCityName
ORDER BY commonInterestScore DESC, toInteger(personId) ASC
LIMIT 10

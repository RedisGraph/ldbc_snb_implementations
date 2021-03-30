MATCH
  (country1:Country {name: $country1})<-[:IS_PART_OF]-(city1:City)<-[:IS_LOCATED_IN]-(person1:Person),
  (country2:Country {name: $country2})<-[:IS_PART_OF]-(city2:City)<-[:IS_LOCATED_IN]-(person2:Person)
WITH person1, person2, city1, 0 AS score
OPTIONAL MATCH (person1)<-[:HAS_CREATOR]-(c:Comment)-[:REPLY_OF]->(:Message)-[:HAS_CREATOR]->(person2)
WITH DISTINCT person1, person2, city1,
              score + (CASE c WHEN null THEN 0
                ELSE  4
                END) AS score
OPTIONAL MATCH (person1)<-[:HAS_CREATOR]-(m:Message)<-[:REPLY_OF]-(:Comment)-[:HAS_CREATOR]->(person2)
WITH DISTINCT person1, person2, city1,
              score + (CASE m WHEN null THEN 0
                ELSE  1
                END) AS score
OPTIONAL MATCH (person1)-[k:KNOWS]-(person2)
WITH DISTINCT person1, person2, city1,
              score + (CASE k WHEN null THEN 0
                ELSE 15
                END) AS score
OPTIONAL MATCH (person1)-[:LIKES]->(m:Message)-[:HAS_CREATOR]->(person2)
WITH DISTINCT person1, person2, city1,
              score + (CASE m WHEN null THEN 0
                ELSE 10
                END) AS score
OPTIONAL MATCH (person1)<-[:HAS_CREATOR]-(m:Message)<-[:LIKES]-(person2)
WITH DISTINCT person1, person2, city1,
              score + (CASE m WHEN null THEN 0
                ELSE  1
                END) AS score
  ORDER BY
  city1.name ASC,
  score DESC,
  person1.id ASC,
  person2.id ASC
WITH
  city1,
  collect({score: score, person1: person1, person2: person2})[0] AS top
RETURN
  top.person1.id,
  top.person2.id,
  city1.name,
  top.score
  ORDER BY
  top.score DESC,
  top.person1.id ASC,
  top.person2.id ASC

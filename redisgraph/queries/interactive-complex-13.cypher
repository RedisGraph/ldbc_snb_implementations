MATCH (person1:Person {id: $person1Id}), (person2:Person {id: $person2Id})
WITH shortestPath((person1)-[:KNOWS*]->(person2)) AS path
RETURN
  CASE path IS NULL
    WHEN true THEN -1
    ELSE length(path)
    END AS shortestPathLength;

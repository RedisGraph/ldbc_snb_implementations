MATCH (country:Place {name: $country})
MATCH (a:Person)-[:IS_LOCATED_IN]->(:Place)-[:IS_PART_OF]->(country)
MATCH (b:Person)-[:IS_LOCATED_IN]->(:Place)-[:IS_PART_OF]->(country)
MATCH (c:Person)-[:IS_LOCATED_IN]->(:Place)-[:IS_PART_OF]->(country)
MATCH (a)-[:KNOWS]-(b), (b)-[:KNOWS]-(c), (c)-[:KNOWS]-(a)
  WHERE a.id < b.id
  AND b.id < c.id
RETURN COUNT(*) AS count

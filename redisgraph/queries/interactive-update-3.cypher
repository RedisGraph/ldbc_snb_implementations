MATCH (person:Person {id: $personId}), (comment:Message {id: $commentId})
CREATE (person)-[:LIKES {creationDate: $creationDate}]->(comment)

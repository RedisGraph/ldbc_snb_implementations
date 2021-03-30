MATCH (person:Person {id: $personId}), (post:Message {id: $postId})
CREATE (person)-[:LIKES {creationDate: $creationDate}]->(post)

MATCH
  (start:Person {id: $personId})<-[:HAS_CREATOR]-(:Message)<-[:REPLY_OF]-(comment:Message)
    -[:HAS_CREATOR]->(person:Person)
RETURN
  person.id AS personId,
  person.firstName AS personFirstName,
  person.lastName AS personLastName,
  comment.creationDate AS commentCreationDate,
  comment.id,
  comment.content AS commentContent
  ORDER BY commentCreationDate DESC, toInteger(comment.id) ASC
  LIMIT 20

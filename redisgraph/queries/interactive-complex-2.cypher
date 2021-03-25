MATCH (:Person {id: $personId})-[:KNOWS]-(friend:Person)<-[:HAS_CREATOR]-(message:Message)
  WHERE message.creationDate <= $maxDate
RETURN
  friend.id AS personId,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  message.id,
  CASE exists(message.content)
    WHEN true THEN message.content
    ELSE message.imageFile
    END AS messageContent,
  message.creationDate AS messageCreationDate
  ORDER BY messageCreationDate DESC, toInteger(message.id) ASC
  LIMIT 20

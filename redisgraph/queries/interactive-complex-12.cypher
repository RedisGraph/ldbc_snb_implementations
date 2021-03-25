MATCH(:Person {id: $personId})-[:KNOWS]-(friend:Person)<-[:HAS_CREATOR]-(comment:Message)-[:REPLY_OF]->(:Message)
       -[:HAS_TAG]->(tag:Tag),
     (tag)-[:HAS_TYPE]->(tagClass:TagClass)-[:IS_SUBCLASS_OF*0..]->(baseTagClass:TagClass)
  WHERE tagClass.name = $tagClassName OR baseTagClass.name = $tagClassName
RETURN
  friend.id,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  collect(DISTINCT tag.name) AS tagNames,
  count(DISTINCT comment) AS replyCount
  ORDER BY replyCount DESC, toInteger(friend.id) ASC
  LIMIT 20

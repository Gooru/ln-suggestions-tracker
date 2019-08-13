## Suggestions Tracker

### Objective
This module is supposed to provide two functionalities:

- Add suggestion in a generic way
- Fetch suggestions for specific user in IL (course) or in class (class)

### Existing APIs

Note that existing APIs which are exposed from navigate-map module are supposed to function as is.
Teacher suggestions addition should happen from that API. System suggestions addition and acceptance
will continue to happen from existing APIs.

### New APIs

New APIs will be used in case of general purpose use case for addition of suggestion. For listing
suggestions, new APIs will be used.

#### Add suggestion API
`
curl -X POST \
  http://localhost:8080/api/stracker/v1/track 
  -H 'Authorization: Token Njg0OTIwMzktMzcxMy00MmRlLTkwYWQtOTRkNTk0NWNkNDgyOlR1ZSBGZWIgMTYgMDQ6NTI6MTUgVVRDIDIwMTY6MTQ1NTU5ODMzNTExNQ=='
  -H 'Cache-Control: no-cache'
  -H 'Content-Length: 657'
  -H 'Content-Type: application/json' 
  -H 'User-Agent: PostmanRuntime/7.15.2' 
  -H 'cache-control: no-cache' 
  -d '{
  "user_id": "68492039-3713-42de-90ad-94d5945cd482",
  "course_id": "c3c5a610-0c97-4ecc-852c-8586a7ba3b52",
  "unit_id": "c7ed7287-e114-4a7b-9381-6af4513284d7",
  "lesson_id": "a8548fc3-bb1d-40d4-b52c-0b76211dad10",
  "collection_id": "c45e4e59-3dde-4253-a4df-e27e5b77330f",
  "class_id": "0baefeb6-4f72-49b4-85fd-a20aa4f17100",
  "suggested_content_id": "8b6ae917-e757-44dd-b0e7-4e081cd9342b",
  "suggestion_origin": "teacher",
  "suggestion_originator_id": "0607cf97-6f9e-47c0-b610-01b36e02b4c6",
  "suggestion_criteria": "performance",
  "suggested_content_type": "collection",
  "suggested_to": "student",
  "suggestion_area": "course-map",
  "tx_code": null,
  "tx_code_type": null
}
'
`

##### Payload Explanation

<pre>
{
  "user_id": "uuid:mandatory",
  "course_id": "uuid:provide if it is course map suggestion",
  "unit_id": "uuid:provide if course map",
  "lesson_id": "uuid: provide if course map",
  "collection_id": "uuid: provide if course map",
  "class_id": "provide if user is in class",
  "suggested_content_id": "uuid: mandatory: id of item which is suggested",
  "suggestion_origin": "string:mandatory: who provided suggestion, valid value ['system'::text, 'teacher'::text]",
  "suggestion_originator_id": "uuid: id of teacher, if it is teacher provided suggestion",
  "suggestion_criteria": "string:mandatory: valid value ['location'::text, 'performance'::text]",
  "suggested_content_type": "string:mandatory: what type of suggestion is given",
  "suggested_to": "string:mandatory: suggested to whom, valid values ['student'::text, 'teacher'::text]",
  "suggestion_area": "string:mandatory: valid values ['course-map'::text, 'class-activity'::text, 'proficiency'::text]",
  "tx_code": "string:optional: gut code for which suggestion is given",
  "tx_code_type": "string: mandatory if tx_code is populated: valid values ['competency'::text, 'micro-competency'::text, 'alt-concept'::text]"
}
</pre>

#### Fetch suggestions for User in IL 

`
curl -X GET 
  'http://localhost:8080/api/stracker/v1/user/68492039-3713-42de-90ad-94d5945cd482/course/c3c5a610-0c97-4ecc-852c-8586a7ba3b52?scope=course-map' 
  -H 'Authorization: Token Njg0OTIwMzktMzcxMy00MmRlLTkwYWQtOTRkNTk0NWNkNDgyOlR1ZSBGZWIgMTYgMDQ6NTI6MTUgVVRDIDIwMTY6MTQ1NTU5ODMzNTExNQ==' 
  -H 'cache-control: no-cache'
`

Additional parameters:
- max
- offset

#### Fetch suggestions for User in Class

`
curl -X GET \
  'http://localhost:8080/api/stracker/v1/user/68492039-3713-42de-90ad-94d5945cd482/class/0baefeb6-4f72-49b4-85fd-a20aa4f17100?scope=' 
  -H 'Authorization: Token Njg0OTIwMzktMzcxMy00MmRlLTkwYWQtOTRkNTk0NWNkNDgyOlR1ZSBGZWIgMTYgMDQ6NTI6MTUgVVRDIDIwMTY6MTQ1NTU5ODMzNTExNQ==' 
  -H 'cache-control: no-cache'
  `
Additional parameters:
- max
- offset

### Suggestions Play

When the suggestions are played from Navigation flow, the path id and path type parameters are sent to analytics.
However, these id/type are currently stored in navigation path store which is different from suggestions store.
To bridge the gap, whenever, suggestions will be played from this store, player needs to send path type as "suggestion"
and suggestion id as path id. Based on this type, system should be able to decipher which store needs to be accessed.
The player sub system, quizzes API, analytics and datascope subsystem may need modification to support new path type.

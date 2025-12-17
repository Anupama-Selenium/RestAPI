Feature: validate place API's
@AddPlace
Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
Given Add Place Payload <accuracy>,"<name>","<phone>","<address>","<website>","<language>"
When user calls "AddPlaceAPI" with "post" http request
Then The API call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP" 
And verify the placeId created maps to "<name>" using "GetPlaceAPI"

	Examples:
	|accuracy|name			 |phone				|address				  |website			|language	|
	|50		 |anupama        |(+91) 9876543212  |9, pune, maharastra      |http://google.com|Marathi	|
#	|100	 |rush           |(+91) 1234567890  |10, dhule, maharastra    |http://google.com|german  	|

@DeletePlace
Scenario: Verify delete place API functionality is working
Given deletePlace Payload 
When user calls "DeletePlaceAPI" with "post" http request
Then The API call got success with status code 200
And "status" in response body is "OK"
package restAssured;
import static io.restassured.RestAssured.*;
import org.testng.Assert;
import io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
public class GraphQL_Query_Mutation {
	public static void main(String args[])
	{
		
//Query		
		String response =given().log().all().header("content-type", "application/json")
				.body("{\"query\":\"\\n    query IntrospectionQuery {\\n      __schema {\\n        \\n        queryType { name }\\n        mutationType { name }\\n        subscriptionType { name }\\n        types {\\n          ...FullType\\n        }\\n        directives {\\n          name\\n          description\\n          \\n          locations\\n          args {\\n            ...InputValue\\n          }\\n        }\\n      }\\n    }\\n\\n    fragment FullType on __Type {\\n      kind\\n      name\\n      description\\n      fields(includeDeprecated: true) {\\n        name\\n        description\\n        args {\\n          ...InputValue\\n        }\\n        type {\\n          ...TypeRef\\n        }\\n        isDeprecated\\n        deprecationReason\\n      }\\n      inputFields {\\n        ...InputValue\\n      }\\n      interfaces {\\n        ...TypeRef\\n      }\\n      enumValues(includeDeprecated: true) {\\n        name\\n        description\\n        isDeprecated\\n        deprecationReason\\n      }\\n      possibleTypes {\\n        ...TypeRef\\n      }\\n    }\\n\\n    fragment InputValue on __InputValue {\\n      name\\n      description\\n      type { ...TypeRef }\\n      defaultValue\\n    }\\n\\n    fragment TypeRef on __Type {\\n      kind\\n      name\\n      ofType {\\n        kind\\n        name\\n        ofType {\\n          kind\\n          name\\n          ofType {\\n            kind\\n            name\\n            ofType {\\n              kind\\n              name\\n              ofType {\\n                kind\\n                name\\n                ofType {\\n                  kind\\n                  name\\n                  ofType {\\n                    kind\\n                    name\\n                  }\\n                }\\n              }\\n            }\\n          }\\n        }\\n      }\\n    }\\n  \",\"operationName\":\"IntrospectionQuery\"}")
				.when().post("https://rahulshettyacademy.com/gq/graphql")
				.then().log().all().extract().response().asString();
		System.out.println(response);
		JsonPath js =new JsonPath(response);
		String characterName = js.getString("data.character.name");
		Assert.assertEquals(characterName, "guru");
		
//Mutation
		
	}

}

package restAssured;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import rest_pojo.Pojo_Ecom_CreateOrder_Serialization;
import rest_pojo.Pojo_Ecom_CreateOrder_orders_Serialization;
import rest_pojo.Pojo_Ecom_Login_Deserialize;
import rest_pojo.Pojo_Ecom_Login_Serialize;
public class ECommerceAPITest {
	public static void main(String[] args)
	{
//Login		
		Pojo_Ecom_Login_Serialize loginRequest= new Pojo_Ecom_Login_Serialize();
		loginRequest.setUserEmail("anupamand7@gmail.com");
		loginRequest.setUserPassword("Anu@2025");
		
		RequestSpecification req= new RequestSpecBuilder()
														.setBaseUri("https://rahulshettyacademy.com")
														.setContentType(ContentType.JSON)
														.build();
//SSL certification using: relaxedHTTPSValidation() after given()
		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all()
											.spec(req)
											.body(loginRequest);
		Pojo_Ecom_Login_Deserialize loginResponse= reqLogin.when().post("api/ecom/auth/login").then().extract().response().as(Pojo_Ecom_Login_Deserialize.class); //DeSerialize: class = Pojo_Ecom_Deserialize
		String token = loginResponse.getToken();
		String userId= loginResponse.getUserId();	
//Create Product
		RequestSpecification reqCreateProduct= new RequestSpecBuilder()
														.setBaseUri("https://rahulshettyacademy.com")
														.addHeader("Authorization", token)
														.build();
		RequestSpecification createProduct = given().log().all()
													.spec(reqCreateProduct)
													.param("productName", "Saree")
													.param("productAddedBy", userId)
													.param("productCategory", "fashion")
													.param("productPrice", "1111")
													.param("productSubCategory", "Saree")
													.param("productDescription", "Silk Saree")
													.param("productFor", "women")
													.multiPart("productImage", new File("C:\\Users\\rushi\\Desktop\\saree.png"));
		String addProductResponse = createProduct.when().post("api/ecom/product/add-product")
												 .then().log().all().extract().response().asString();
		JsonPath js= new JsonPath(addProductResponse);
		String productId = js.get("productId");
		System.out.println(productId);
	
//Create order	
		Pojo_Ecom_CreateOrder_orders_Serialization createOrderpojo= new Pojo_Ecom_CreateOrder_orders_Serialization();
		createOrderpojo.setCountry("India");
		createOrderpojo.setProductOrderedId(productId);
		List<Pojo_Ecom_CreateOrder_orders_Serialization> orderItem = new ArrayList<>();
		orderItem.add(createOrderpojo);
		Pojo_Ecom_CreateOrder_Serialization item = new Pojo_Ecom_CreateOrder_Serialization();
		item.setOrders(orderItem);
		
		RequestSpecification reqCreateOrder = new RequestSpecBuilder()
																.setBaseUri("https://rahulshettyacademy.com")
																.setContentType(ContentType.JSON)
																.addHeader("Authorization", token)
																.build();

		RequestSpecification createOrder = given().log().all()
						                                       .spec(reqCreateOrder)
						                                       .body(item);
		String createOrderResponse = createOrder.when().post("api/ecom/order/create-order")
							                            .then().log().all().extract().response().asString();
		JsonPath js2= new JsonPath(createOrderResponse);
		List<String> orders = js2.getList("orders");
		List<String> productOrdersId = js2.getList("productOrderId");
		String order=orders.get(0);
		String productOrders=productOrdersId.get(0);
		System.out.println(order);
		
//Get Order details
		RequestSpecification reqGetOrderDetails= new RequestSpecBuilder()
																		.setBaseUri("https://rahulshettyacademy.com")
																		.addHeader("Authorization", token)
																		.build();
		RequestSpecification getProductReq = given().log().all().spec(reqGetOrderDetails).pathParam("userId", userId);
		String getResponse = getProductReq.when().get("api/ecom/order/get-orders-for-customer/{userId}")
														.then().log().all().extract().response().asString();
		
		
		JsonPath js3 =new JsonPath(getResponse);
		String id  = js3.getString("data[0]._id");
		
		System.out.print(id);
		System.out.println("******************");
		System.out.print(getResponse);
		
//Delete product
		RequestSpecification reqDeleteProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
																		.addHeader("authorization", token)
																		.setContentType(ContentType.JSON).build();
		RequestSpecification deleteProduct = given().log().all().spec(reqDeleteProduct)
																 .pathParam("productId", productId);
		String deleteResponse = deleteProduct.when().delete("api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		
		System.out.println(deleteResponse);	
		
//Delete order
		RequestSpecification reqDeleteOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
																				.addHeader("authorization", token)
																				.setContentType(ContentType.JSON).build();
		RequestSpecification deleteOrder = given().log().all().spec(reqDeleteOrder)
																		 .pathParam("id", id);
		String deleteOrderResponse = deleteOrder.when().delete("api/ecom/order/delete-order/{id}").then().log().all().extract().response().asString();
		System.out.println(deleteOrderResponse);					
	}
}






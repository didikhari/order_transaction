# order_transaction

this repository contains an example of order transaction of an ecommerce, developed using Java Programming language and Spring, Jpa/Hibernate Query DSL.

i use Swagger2 for API documentation and Testing that can be accessed by : http://localhost:8181/swagger-ui.html

Assumption:
1. Customer need to login before
2. Only admin can update order status and tracking code
3. Flow for performing transaction:

	1. list product (Call /v1/products API)
	2. detail product (Call /v1/products/{id} API)
	3. add to cart (Call /v1/add-to-cart API)
		3.1 list cart item (Call /v1/carts API)
		3.2 update cart item (Call /v1/update-cart API)
		3.3 delete cart item (Call /v1/delete-cart/{id} API)
	4. input new address or list address
		4.1 get available province by calling /v1/address/province API
		4.2 get available city by calling /v1/address/{province}/cities
	5. Customer can check shipment cost by calling /v1/check-shipping-cost API
	6. submit order API (Call /v1/submit-order API)
	7. confirm payment API (Call /v1/confirm-payment API)
	8. Admin Approve/Cancel (Call /v1/admin/update-order API)
		8.1 Customer and Admin can check order detail by calling /v1/orders/{order_id} API
	9. Customer can check order status by calling /v1/orders API
	10. Customer can check shipment status by calling /v1/check-shipment-status API
	
4. Order status:
	1 = order initialized
	2 = order paid
	3 = order packed
	4 = order on shipment
	5 = order received by customer
	6 = order retur
	7 = order rejected / canceled
	
5. Hardcoded User:
	1. Customer:
		user@gmail.com / user123
		user1@gmail.com / user123
		user2@gmail.com / user123
		user3@gmail.com / user123
	2. Admin
		admin@gmail.com / admin123

6. Deployed Api:
	http://93.188.161.110:8181

7. Swagger Api Docs and Testing UI:
	http://93.188.161.110:8181/swagger-ui.html

8. Step to deploy on local Computer:
	Prerequisite: 
	
		1. Computer / Laptop connected to internet
		2. Java 1.8 installed on the pc
		3. Source code from Github
	
	Step:
	
		1. need to update upload.folder on application.properties to existin folder on your local pc
	
		2. perform build with following command 
			cd /project/root/dir/
			./gradlew clean
			./gradlew build
			
		3. run app with following command
			java -jar /build/libs/salestock-0.0.1-SNAPSHOT.jar
			
		


<p align="center">
  
  <img src="https://user-images.githubusercontent.com/94486861/198907763-9feac01c-4db6-4f79-9a88-44c6efcfed0e.png" />
</p>

## <b>INTRODUCTION</b>

In this project we will see how to create a simple application based on the microservice architecture. So the application will be divided on 2 microservices (or 3 soon), so the first microservice for managing customers and second for managing bills (and third for products soon).

## <b>CUSTOMER SERVICE</b>

![1___](https://user-images.githubusercontent.com/94486861/198893387-9286479e-dd15-453d-a880-a8cb71d6a1f5.png)

To communicate with database we use Spring Data and mapStruct for objects mapping.<br>
This is the project structure
<br>

![image](https://user-images.githubusercontent.com/94486861/198893816-e9accec0-b316-4ca1-b779-8e29ae7d5035.png)
<br>

#### <b>Customer Entity</b>

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    private String id;
    private String name;
    private String email;
}
```

#### <b>Customer Repository</b>

```java
public interface CustomerRepository extends JpaRepository<Customer,String> {
}
```

#### <b>Customer Service Interface</b>

```java
public interface CustomerService {

    CustomerResponseDTO save(CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO getCustomer(String id);
    CustomerResponseDTO update(CustomerRequestDTO customerRequestDTO);
    List<CustomerResponseDTO> listCustomers();
}
```

#### <b>Customer Mapper</b>

```java
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    
    CustomerResponseDTO customerToCustomerResponseDTO(Customer customer);
    Customer CustomerRequestDTOToCustomer(CustomerRequestDTO customerRequestDTO);
}
```

#### <b>Rest controller</b>

```java
@RestController
@RequestMapping("/api")
public class CustomerRestController {

    private CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/customers")
    List<CustomerResponseDTO> listCustomers(){
        return customerService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    CustomerResponseDTO getCustomer(@PathVariable String id){
        return customerService.getCustomer(id);
    }

    @PostMapping("/customers")
    CustomerResponseDTO save(@RequestBody CustomerRequestDTO customerRequestDTO){
        return customerService.save(customerRequestDTO);
    }

    @PutMapping("/customers/{id}")
    CustomerResponseDTO update(@PathVariable String id, @RequestBody CustomerRequestDTO customerRequestDTO){
        customerRequestDTO.setId(id);
        return customerService.update(customerRequestDTO);
    }
    

}
```
### <b>Dockerizing Customer Service</b>

```Dockerfile
FROM openjdk:8
EXPOSE 8761
ADD target/customer-service.jar customer-service.jar
ENTRYPOINT ["java","-jar","customer-service.jar"]
```

![image](https://user-images.githubusercontent.com/94486861/198897067-17259202-69c9-428d-a687-5e632f099e96.png)


### <b>Customer Service On Eureka Discovery</b>
![image](https://user-images.githubusercontent.com/94486861/198896831-ce2c6de8-07ba-4e5e-bc37-210bb38d7c59.png)

### <b>Some Tests</b>

![image](https://user-images.githubusercontent.com/94486861/198896976-5ea5deb7-0b7a-4f8f-b784-0ca03668cf8f.png)

![image](https://user-images.githubusercontent.com/94486861/198897004-f8204cc6-a5e2-47cd-a783-f050ec1ed5a9.png)

## Billing SERVICE

![image](https://user-images.githubusercontent.com/94486861/198897313-8a42885b-d3d3-44bc-8376-f135f64e6d3a.png)

#### <b>Project structure</b>

![image](https://user-images.githubusercontent.com/94486861/198897775-116f63cb-d5ca-4f72-b6af-d183bd8ff3e9.png)

#### <b>Invoice Entity</b>

```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {

    @Id
    private String id;
    private Date date;
    private BigDecimal amount;
    private String customerID;
    @Transient
    private Customer customer;
}
```

#### <b>Invoice Repository</b>

```java
public interface InvoiceRepository extends JpaRepository<Invoice,String> {
    List<Invoice> findByCustomerID(String customerId);
}
```

#### <b>Invoice Service Interface</b>

```java
public interface InvoiceService {

    InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO getInvoice(String id);
    List<InvoiceResponseDTO> invoiceByCustomer(String customerId);
    List<InvoiceResponseDTO> allInvoices();
}
```

#### <b>Invoice Mapper</b>

```java
@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    Invoice fromInvoiceRequestDTO(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO fromInvoice(Invoice invoice);

}
```

#### <b>Invoice Rest controller</b>

```java
@RestController
@RequestMapping("/api")
public class InvoiceRestController {

    private InvoiceService invoiceService;

    public InvoiceRestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @PostMapping("/invoices")
    InvoiceResponseDTO save(@RequestBody InvoiceRequestDTO invoiceRequestDTO){
        return invoiceService.save(invoiceRequestDTO);
    }

    @GetMapping("/invoices")
    List<InvoiceResponseDTO> allInvoices(){
        return invoiceService.allInvoices();
    }

    @GetMapping("/invoices/{id}")
    InvoiceResponseDTO getInvoice(@PathVariable(name ="id") String invoiceId){
        return invoiceService.getInvoice(invoiceId);
    }

    @GetMapping("/invoices/customers/{customerId}")
    List<InvoiceResponseDTO> invoicesByCustomer(@PathVariable String customerId){
        return invoiceService.invoiceByCustomer(customerId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

```

#### <b>Invoice Rest controller</b>

```java
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String id){
        super(String.format("Customer with \"%s\" not found!",id));
    }
}
```
### <b>Dockerizing Billing Service</b>

```Dockerfile
FROM openjdk:8
EXPOSE 8761
ADD target/billing-service.jar billing-service.jar
ENTRYPOINT ["java","-jar","billing-service.jar"]

```

![image](https://user-images.githubusercontent.com/94486861/198898131-7ce5d343-828e-4a3d-b2a0-000bd8999662.png)


### <b>Some Tests</b>

#### Save An Invoice

![image](https://user-images.githubusercontent.com/94486861/198898018-4d4321f1-2375-4df5-9c51-b248c352319f.png)

#### Invoices List

![image](https://user-images.githubusercontent.com/94486861/198898062-1c1e9356-7c0a-4cd0-ba46-5fcb221bc003.png)

## Gateway SERVICE

### Configuration

```properties
server.port=9999
spring.application.name=GATEWAY
eureka.instance.prefer-ip-address=true
spring.cloud.discovery.enabled=true
spring.main.web-application-type=reactive
```

### <b>Gateway Configuration</b>


```java
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator(
            ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp
            ){
        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
    }
}
```

### <b>Gateway On Eureka</b>


![image](https://user-images.githubusercontent.com/94486861/198906077-2b875bcb-24cd-4eae-88cb-0f49d31569e3.png)


### <b>Gateway Configuration</b>

```properties
server.port=9999
spring.application.name=GATEWAY
eureka.instance.prefer-ip-address=true
spring.cloud.discovery.enabled=true
spring.main.web-application-type=reactive
```

### <b>Dockerizing Gateway Service</b>

```Dockerfile
FROM openjdk:8
EXPOSE 9999
ADD target/gateway.jar gateway.jar
ENTRYPOINT ["java","-jar","gateway.jar"]
```
![image](https://user-images.githubusercontent.com/94486861/198906282-d369a526-3035-4c0e-9751-3d60ec2c265b.png)


## EUREKA SERVICE

![image](https://user-images.githubusercontent.com/94486861/198906457-ccc15437-96e8-449a-b2f6-8fa7c220f5b6.png)

### <b>Configuration</b>

```properties
server.port=8761
eureka.client.fetch-registry=false
eureka.client.register-with-eureka=false
```

### <b>Eureka Configuration</b>


```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args);
    }

}
```

### <b>Dockerizing Eureka Service</b>

```Dockerfile
FROM openjdk:8
EXPOSE 8761
ADD target/eureka.jar eureka.jar
ENTRYPOINT ["java","-jar","eureka.jar"]
```

![image](https://user-images.githubusercontent.com/94486861/198906476-8a0493e4-3e7c-4607-b9f0-df1b8a8d1c60.png)


## DOCKER COMPOSE

### <b>docker compose yml file</b>

```yml
version: "3.8"

services:
  eureka-service:
    build: ./eureka-service
    ports:
      - 8761:8761


  gateway:
    build: ./gateway
    restart: on-failure
    ports:
      - 9999:9999
    depends_on:
      - eureka-service
    environment:
      - eureka.client.service-url.defaultZone=http://eureka-service:8761/eureka
  

  billing-service:
      build: ./billing-service
      restart: on-failure
      ports:
        - 8083:8083
      depends_on:
        - eureka-service
      environment:
        - eureka.client.service-url.defaultZone=http://eureka-service:8761/eureka


  customer-service:
      build: ./customer-service
      restart: on-failure
      ports:
        - 8082:8082
      depends_on:
        - eureka-service
      environment:
        - eureka.client.service-url.defaultZone=http://eureka-service:8761/eureka

```

### <b>services on docker</b>

#### images

![image](https://user-images.githubusercontent.com/94486861/198906978-5daa77c7-e4a3-46f9-a01e-ad5ddfad0285.png)

#### containers

![image](https://user-images.githubusercontent.com/94486861/198906994-2631028f-dd94-479b-b21d-167accefef50.png)




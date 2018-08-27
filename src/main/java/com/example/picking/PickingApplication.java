package com.example.picking;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;
import org.springframework.context.annotation.Bean;

import com.example.picking.schema.ErrorResponseSchemaRepo;
import com.example.picking.schema.NewPickCreationRequestSchemaRepo;
import com.example.picking.schema.PickConfirmRequestSchemaRepo;
import com.example.picking.schema.PickResourceResponseSchemaRepo;

@SpringBootApplication
@EnableBinding(Source.class)
@EnableSchemaRegistryClient
@EnableAutoConfiguration
public class PickingApplication {
	private Random random = new Random();
	
	@Value( "${avro.schema.file.path:avro/}")
	private String avroSchemaFilePath;
	
	public static void main(String[] args) {
		SpringApplication.run(PickingApplication.class, args);
	}
	
/*	@Bean
	@InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "1"))
	public MessageSource<String> timerMessageSource() {
		return () -> MessageBuilder.withPayload("hello").build();
	}	
*/	
	@Bean
	public NewPickCreationRequestSchemaRepo createNewPickRequestSchemaRepo(@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint) throws Exception{
		NewPickCreationRequestSchemaRepo repo = new NewPickCreationRequestSchemaRepo(endpoint, avroSchemaFilePath);
		System.out.println("New Pick Request Schema repo id:" + repo.toString() + ":: Create New Pick Schema Registered:" + repo.registerSchema());
	    return repo;
	}
	
	@Bean
	public PickConfirmRequestSchemaRepo createPickConfirmRequestSchemaRepo(@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint) throws Exception{
		PickConfirmRequestSchemaRepo repo = new PickConfirmRequestSchemaRepo(endpoint, avroSchemaFilePath);
		System.out.println("Pick Confirm Request Schema repo id:" + repo.toString() + ":: PickConfirm Schema Registered:" + repo.registerSchema());
	    return repo;
	}

	@Bean
	public PickResourceResponseSchemaRepo createPickResourceResponseSchemaRepo(@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint) throws Exception{
		PickResourceResponseSchemaRepo repo = new PickResourceResponseSchemaRepo(endpoint, avroSchemaFilePath);
		System.out.println("Pick Response Schema repo id:" + repo.toString() + ":: Pick Resource/Reponse Schema Registered:" + repo.registerSchema());
	    return repo;
	}

	@Bean
	public ErrorResponseSchemaRepo createErrorSchemaRepo(@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint) throws Exception{
		ErrorResponseSchemaRepo repo = new ErrorResponseSchemaRepo(endpoint, avroSchemaFilePath);
		System.out.println("Error Response Schema Registered:" + repo.registerSchema());
	    return repo;
	}	
/*	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory);
	    return txManager;
	}	
	

	   @Bean
	   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	      LocalContainerEntityManagerFactoryBean em 
	        = new LocalContainerEntityManagerFactoryBean();
	      em.setDataSource(dataSource());
	      em.setPackagesToScan(new String[] { "com.example.demo.db" });
	 
	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      em.setJpaVendorAdapter(vendorAdapter);
	      em.setJpaProperties(additionalProperties());
	      return em;
	   }
	 
	   @Bean
	   public DataSource dataSource(){
	      DriverManagerDataSource dataSource = new DriverManagerDataSource();
	      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	      dataSource.setUrl("jdbc:mysql://localhost:3306/springboot_mysql_example");
	      dataSource.setUsername( "postgres" );
	      dataSource.setPassword( "krishna" );
	      return dataSource;
	   }
	   Properties additionalProperties() {
	       Properties properties = new Properties();
	       properties.setProperty("hibernate.hbm2ddl.auto", "update");
	       properties.setProperty(
	         "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
	        
	       return properties;
	   }	   
*/}

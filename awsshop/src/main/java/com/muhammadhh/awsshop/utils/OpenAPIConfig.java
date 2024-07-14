package com.muhammadhh.awsshop.utils;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {


  @Bean
  public OpenAPI myOpenAPI() {

	Server devServer = new Server();
	devServer.setUrl("http://localhost:8080");
	devServer.setDescription("Server URL in Development environment");

    Contact contact = new Contact();
    contact.setEmail("mh.haidor@gmail.com");
    contact.setName("Muhammad Hicho Haidor");
    contact.setUrl("https://github.com/Mohasb");

    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("Awsome Shop API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to manage Awsome Shop APP.").termsOfService("https://github.com/Mohasb/awsshop-API")
        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}
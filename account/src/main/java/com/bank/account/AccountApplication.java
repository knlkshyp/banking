package com.bank.account;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @Info(
			title = "Account microservice REST API documentation",
			description = "Microservice to serve account specific activities",
			version = "v1",
			contact = @Contact(
					name = "",
					email = "",
					url = ""
			),
			license = @License(
					name = "",
					url = ""
			)
		),
		externalDocs = @ExternalDocumentation(
				description = "",
				url = ""
		)
)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@RefreshScope
@EnableFeignClients
@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}

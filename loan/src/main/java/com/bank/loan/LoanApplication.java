package com.bank.loan;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@RefreshScope
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Loan microservice REST API documentation",
				description = "Microservice to serve loan specific activities",
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
public class LoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

}

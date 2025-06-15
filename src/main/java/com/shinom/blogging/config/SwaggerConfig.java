package com.shinom.blogging.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "Blogging Application API",
				description = "This is a blogging application with multiple features",
				termsOfService = "T&C",
				contact = @Contact(
						name = "Shinom",
						email = "humanresource2024@duck.com"),
				license = @License(
						name = "Apache "),
				version = "0.1"
		),
		servers = {
				@Server(
						description = "Dev",
						url="http://localhost:8082"
						),
				@Server(
						description = "Test",
						url="http://localhost:8082"
						)
		},
		security = @SecurityRequirement(name = "bearer")
)
@SecuritySchemes( 
	@SecurityScheme(
			name = "bearer",
			in = SecuritySchemeIn.HEADER,
			type = SecuritySchemeType.HTTP,
			bearerFormat = "JWT",
			scheme = "bearer",
			description = "security desc"
	)
)
public class SwaggerConfig {
    


}

package codeSake.saurav.saxena.bankingapplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//SpringDoc OpenAPI Starter WebMVC UI for swagger

//http://localhost:8081/swagger-ui/index.html#/
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "codeSake Banking Application",
				description = "" +
						"Rest Api For Banking Application",
				version = "v1.0",
				contact = @Contact(
						name = "SAURAV SAXENA",
						url = "https://github.com/sauravsaxena7",
						email = "sauravsrivastava121@gmail.com"
				),
				license = @License(
						name = "CodeSake",
						url = "https://github.com/sauravsaxena7/secure-banking-application-Java-Spring-Boot")),

		externalDocs = @ExternalDocumentation(
				description = "CodeSake Banking Application",
				url = "https://github.com/sauravsaxena7/secure-banking-application-Java-Spring-Boot"
		)
		//servers = @Server(url = "http://localhost:8081")
)

public class SecureBankingApplicationBySauravSaxenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureBankingApplicationBySauravSaxenaApplication.class, args);
	}

}

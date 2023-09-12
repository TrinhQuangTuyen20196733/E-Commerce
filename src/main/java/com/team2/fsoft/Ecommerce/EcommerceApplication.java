package com.team2.fsoft.Ecommerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "VN GroupBy API",
				version = "1.0.0",
				description = "Building VN GroupBy e-commerce platform",
				contact = @Contact(
						name = "Trịnh Quang Tuyến",
						email = "tuyen.tq196733@sis.hust.edu.vn"
				)
		)
)
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}

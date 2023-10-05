package com.team2.fsoft.Ecommerce;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableEncryptableProperties
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

    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
        return args -> {
           try {
               for (int i=0;i<100;i++){
                   kafkaTemplate.send("payment", "hello kafka_ " +i);
               }

           }
           catch (Exception ex) {
               System.out.println("abc");
           }
        };

    }

}

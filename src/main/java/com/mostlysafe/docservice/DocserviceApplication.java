package com.mostlysafe.docservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DocserviceConfiguration.class)
public class DocserviceApplication {


	public static void main(String[] args) {
		SpringApplication.run(DocserviceApplication.class, args);
	}

}

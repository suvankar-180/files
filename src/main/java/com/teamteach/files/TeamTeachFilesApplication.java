package com.teamteach.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.aws.autoconfigure.context.*;

@EnableAutoConfiguration(exclude = {
        ContextCredentialsAutoConfiguration.class,
        ContextInstanceDataAutoConfiguration.class,
        ContextRegionProviderAutoConfiguration.class,
        ContextResourceLoaderAutoConfiguration.class,
        ContextStackAutoConfiguration.class,
})
@SpringBootApplication
public class TeamTeachFilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamTeachFilesApplication.class, args);
	}

}

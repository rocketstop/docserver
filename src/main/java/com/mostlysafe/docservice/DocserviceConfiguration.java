package com.mostlysafe.docservice;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

@Configuration
@ComponentScan
@EnableMongoRepositories("com.mostlysafe.docservice")
@EntityScan("com.mostlysafe.docservice")
public class DocserviceConfiguration extends AbstractMongoClientConfiguration {

    private static final String DEFAULT_MONGODB_URL = "mongodb://localhost:27017/documents";

    @Autowired
    private Environment environment;
    protected Logger logger;

    public DocserviceConfiguration(Environment environment) {
        logger = Logger.getLogger(getClass().getName());
    }

    @Nonnull
    @Override
    protected String getDatabaseName() {
        String databaseName = environment.getProperty("database.name","documents");
        logger.info("Using database name: "+ databaseName);
        return databaseName;
    }

    @Nonnull
    @Override
    public MongoClient mongoClient() {
        String databaseUrl = environment.getProperty("database.url", DEFAULT_MONGODB_URL);
        logger.info("Using database url: "+ databaseUrl);
        ConnectionString connectionString = new ConnectionString(databaseUrl);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();

        return MongoClients.create(mongoClientSettings);


    }


}

package com.TruePrepDemo.TruePrepDemo.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    // Set the database name for MongoDB
    @Override
    protected String getDatabaseName() {
        return "TruePrep";  // Replace with your database name
    }

    // Create a MongoClient bean that connects to MongoDB Atlas
    @Override
    @Bean
    public MongoClient mongoClient() {
        String connectionString = "mongodb+srv://abhishek:abhishek1234@cluster0.jg9ak.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        return MongoClients.create(connectionString);
    }

    // Configure MongoTemplate to interact with MongoDB
    @Bean
    public MongoTemplate mongoTemplate() {
        MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
        return new MongoTemplate(mongoDatabaseFactory);
    }
}

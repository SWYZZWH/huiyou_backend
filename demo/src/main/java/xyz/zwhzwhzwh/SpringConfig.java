package xyz.zwhzwhzwh;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * 负责连接 mongoDB，连接字保存在 application。properties 中 
 * */
@Configuration
public class SpringConfig {

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

   
    @Bean
    public MongoClient mongoClient() {
    	//不指定codeRegistry，使用默认的
        //CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        //CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        return MongoClients.create(MongoClientSettings.builder()
                                                      .applyConnectionString(new ConnectionString(connectionString))
                                                      //.codecRegistry(codecRegistry)
                                                      .build());
    }
   

}

//另一种写法
/*
public class MongoConnector{
	
	private final MongoDatabaseFactory mongo;
	
	@Autowired
	public MongoConnector(MongoDatabaseFactory mongo) {
		this.mongo = mongo;
	}
	
	public void connect() {
		MongoDatabase db = mongo.getMongoDatabase();
	}
	
}
*/
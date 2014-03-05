package cmpe282.lab.database;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;
import com.amazonaws.services.dynamodbv2.util.Tables;


public class AWSDynamoDB {

 
    public static AmazonDynamoDBClient dynamoDB;
    public static String table_name = AmazonStoreSchema.TABLE_SHOPPINGCART;
    
    
    public static void init() throws Exception {
    	System.out.println("set region ! to US_ west _ 1");
        dynamoDB = new AmazonDynamoDBClient(new ClasspathPropertiesFileCredentialsProvider());
        Region usWest2 = Region.getRegion(Regions.US_WEST_2 );
        dynamoDB.setRegion(usWest2);
        createClient();
    }
    
	static void createClient() throws Exception {
		AWSCredentials credentials = new PropertiesCredentials(
				AWSTest.class.getResourceAsStream("AwsCredentials.properties"));

		dynamoDB = new AmazonDynamoDBClient(credentials);
	}

    
    public static int createTable(String table_name){
    	
    	CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(table_name)
                .withKeySchema(new KeySchemaElement().withAttributeName("product_id").withKeyType(KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition().withAttributeName("product_id").withAttributeType(ScalarAttributeType.N))
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
                TableDescription createdTableDescription = dynamoDB.createTable(createTableRequest).getTableDescription();
            System.out.println("Created Table: " + createdTableDescription);

            // Wait for it to become active
            System.out.println("Waiting for " + table_name + " to become ACTIVE...");
            Tables.waitForTableToBecomeActive(dynamoDB, table_name);
            
            return 1;

    }
    
    public static boolean doesTableExist(String table_name){
    	return Tables.doesTableExist(dynamoDB, table_name);
    }
    
    public static void descriptNewTalbe(String table_name){
    	 DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(table_name);
         TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
         System.out.println("Table Description: " + tableDescription);

    }
    
    public static void main(String[] args) throws Exception {
        init();
       if(doesTableExist(table_name)){
    	   System.out.println("table exsit");
       }else{
    	   createTable(table_name);
       }
        

       
    }


}
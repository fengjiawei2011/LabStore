package cmpe282.lab.database;

import java.util.ArrayList;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;

public class AWSTest {

	static String tableName = "ExampleTable";
	static AmazonDynamoDBClient client;

	public static void main(String[] args) throws Exception {

		// You need a client to send requests.
		createClient();

		createExampleTable();
		listMyTables();
		getTableInformation();
		updateExampleTable();

		deleteExampleTable();
	}

	static void createClient() throws Exception {
		AWSCredentials credentials = new PropertiesCredentials(
				AWSTest.class.getResourceAsStream("AwsCredentials.properties"));

		client = new AmazonDynamoDBClient(credentials);
	}

	static void createExampleTable() {

		// Provide the initial provisioned throughput values as Java long data
		// types
		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
				.withReadCapacityUnits(5L).withWriteCapacityUnits(6L);
		CreateTableRequest request = new CreateTableRequest().withTableName(
				tableName).withProvisionedThroughput(provisionedThroughput);

		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName(
				"Id").withAttributeType("N"));
		request.setAttributeDefinitions(attributeDefinitions);

		ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
		tableKeySchema.add(new KeySchemaElement().withAttributeName("Id")
				.withKeyType(KeyType.HASH));
		request.setKeySchema(tableKeySchema);

		CreateTableResult result = client.createTable(request);

		waitForTableToBecomeAvailable(tableName);

		getTableInformation();

	}

	static void listMyTables() {
		String lastEvaluatedTableName = null;
		do {

			ListTablesRequest listTablesRequest = new ListTablesRequest()
					.withLimit(10).withExclusiveStartTableName(
							lastEvaluatedTableName);

			ListTablesResult result = client.listTables(listTablesRequest);
			lastEvaluatedTableName = result.getLastEvaluatedTableName();

			for (String name : result.getTableNames()) {
				System.out.println(name);
			}

		} while (lastEvaluatedTableName != null);
	}

	static void getTableInformation() {

		TableDescription tableDescription = client.describeTable(
				new DescribeTableRequest().withTableName(tableName)).getTable();
		System.out.format("Name: %s:\n" + "Status: %s \n"
				+ "Provisioned Throughput (read capacity units/sec): %d \n"
				+ "Provisioned Throughput (write capacity units/sec): %d \n",
				tableDescription.getTableName(), tableDescription
						.getTableStatus(), tableDescription
						.getProvisionedThroughput().getReadCapacityUnits(),
				tableDescription.getProvisionedThroughput()
						.getWriteCapacityUnits());
	}

	static void updateExampleTable() {

		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
				.withReadCapacityUnits(6L).withWriteCapacityUnits(7L);

		UpdateTableRequest updateTableRequest = new UpdateTableRequest()
				.withTableName(tableName).withProvisionedThroughput(
						provisionedThroughput);

		UpdateTableResult result = client.updateTable(updateTableRequest);
		waitForTableToBecomeAvailable(tableName);
	}

	static void deleteExampleTable() {
		DeleteTableRequest deleteTableRequest = new DeleteTableRequest()
				.withTableName(tableName);
		DeleteTableResult result = client.deleteTable(deleteTableRequest);
		waitForTableToBeDeleted(tableName);
	}

	private static void waitForTableToBecomeAvailable(String tableName) {
		System.out.println("Waiting for " + tableName + " to become ACTIVE...");

		long startTime = System.currentTimeMillis();
		long endTime = startTime + (10 * 60 * 1000);
		while (System.currentTimeMillis() < endTime) {
			DescribeTableRequest request = new DescribeTableRequest()
					.withTableName(tableName);
			TableDescription tableDescription = client.describeTable(request)
					.getTable();
			String tableStatus = tableDescription.getTableStatus();
			System.out.println("  - current state: " + tableStatus);
			if (tableStatus.equals(TableStatus.ACTIVE.toString()))
				return;
			try {
				Thread.sleep(1000 * 20);
			} catch (Exception e) {
			}
		}
		throw new RuntimeException("Table " + tableName + " never went active");
	}

	private static void waitForTableToBeDeleted(String tableName) {
		System.out.println("Waiting for " + tableName
				+ " while status DELETING...");

		long startTime = System.currentTimeMillis();
		long endTime = startTime + (10 * 60 * 1000);
		while (System.currentTimeMillis() < endTime) {
			try {
				DescribeTableRequest request = new DescribeTableRequest()
						.withTableName(tableName);
				TableDescription tableDescription = client.describeTable(
						request).getTable();
				String tableStatus = tableDescription.getTableStatus();
				System.out.println("  - current state: " + tableStatus);
				if (tableStatus.equals(TableStatus.ACTIVE.toString()))
					return;
			} catch (ResourceNotFoundException e) {
				System.out.println("Table " + tableName
						+ " is not found. It was deleted.");
				return;
			}
			try {
				Thread.sleep(1000 * 20);
			} catch (Exception e) {
			}
		}
		throw new RuntimeException("Table " + tableName + " was never deleted");
	}

}

package com.example.picking.schema;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import lombok.Data;

@Data
public class AvroSchemaRepo {
	private String schemaRegistryURL;
	private CachedSchemaRegistryClient schemaRegistryClient;
	private SchemaMetadata schemaMetaData;
	private String schemaName;
	private String schemaFileName;
	private Schema schema;

     
	public AvroSchemaRepo(String schemaRegistryURL, String schemaName, String schemaFileName) {
		this.schemaRegistryURL = schemaRegistryURL;
		this.schemaName = schemaName;
		this.schemaFileName = schemaFileName;
		schemaRegistryClient = new CachedSchemaRegistryClient(schemaRegistryURL, 5);
	}

	public SchemaMetadata registerSchema() throws Exception {
		System.out.println("avro file name:" + schemaFileName);
		File file = new File(AvroSchemaRepo.class.getClassLoader().getResource(schemaFileName).getFile());
		//Resource banner = resourceLoader.getResource("file:c:/temp/filesystemdata.txt");
		String schemaDefFromFile = new String(Files.readAllBytes(file.toPath()));
		System.out.println("Schema Definition for :" + schemaName + " Avro Schema file:" + schemaDefFromFile);
		Schema inputSchema = Schema.parse(schemaDefFromFile);
		int schemaId = schemaRegistryClient.register(schemaName, inputSchema);
		schema = schemaRegistryClient.getById(schemaId);
		int version = schemaRegistryClient.getVersion(schemaName, schema);
		schemaMetaData = schemaRegistryClient.getSchemaMetadata(schemaName, version);
		System.out.println("schema id registered:" + schemaId + ", schema:" + schema);
		return schemaMetaData;
	}

	public GenericRecord getGenericRecord(String jsonString) throws IOException {
		DecoderFactory decoderFactory = new DecoderFactory();
		Decoder decoder = decoderFactory.jsonDecoder(schema, jsonString);
		DatumReader<GenericData.Record> reader = new GenericDatumReader<>(schema);
		GenericRecord genericRecord = reader.read(null, decoder);
		return genericRecord;
	}

	public String getJsonFromGenericRecord(GenericRecord record) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
		//Encoder encoder = new JsonEncoder(schema, out);
		JsonEncoder jsonEncoder = EncoderFactory.get().jsonEncoder(schema, out, true);
		writer.write(record, jsonEncoder);
		jsonEncoder.flush();
		//System.out.println("json string:" + out.toString());
		return out.toString();
	}
	
	private String test(GenericRecord record) throws IOException {
		GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Encoder e = EncoderFactory.get().binaryEncoder(os, null);
		writer.write(record, e);
		e.flush();
		os.close();
		byte[] byteData = os.toByteArray();
		String outJSON = new String(byteData,"UTF8");
		return outJSON;
	}

}

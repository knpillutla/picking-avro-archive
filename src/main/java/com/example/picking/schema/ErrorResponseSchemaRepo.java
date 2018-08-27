package com.example.picking.schema;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;

public class ErrorResponseSchemaRepo extends AvroSchemaRepo{
	public ErrorResponseSchemaRepo(String schemaRegistryEndpoint, String schemaFilePath) throws Exception{
		super(schemaRegistryEndpoint, "ErrorResponse", schemaFilePath+"avroErrorMessageResponse.avsc");
	}
	
	public String getErrorMsg(int responseCode, String errorMsg) throws IOException {
		GenericRecordBuilder bldr = new GenericRecordBuilder(this.getSchema());
		bldr.set(this.getSchema().getProp("responseCode"), responseCode);
		bldr.set(this.getSchema().getProp("errorMsg"), errorMsg);
		return this.getJsonFromGenericRecord(bldr.build());
	}	
}

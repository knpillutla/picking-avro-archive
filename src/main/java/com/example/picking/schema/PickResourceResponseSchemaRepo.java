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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.example.picking.db.Pick;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;

public class PickResourceResponseSchemaRepo extends AvroSchemaRepo{
	public PickResourceResponseSchemaRepo(String schemaRegistryEndpoint, String schemaFilePath) throws Exception{
		super(schemaRegistryEndpoint, "PickResource", schemaFilePath+"avroPickResourceResponse.avsc");
	}
	
	public String getPickResourceResponseJSON(Pick pickObj) throws IOException {
		if(pickObj == null) return null;
		Schema schema = getSchema();
		GenericRecordBuilder bldr = new GenericRecordBuilder(schema);
		bldr.set(schema.getField("id"), pickObj.getId());
		bldr.set(schema.getField("locnNbr"), pickObj.getLocnNbr());
		bldr.set(schema.getField("locnBrcd"), pickObj.getLocnBrcd());
		bldr.set(schema.getField("itemBrcd"), pickObj.getItemBrcd());
		bldr.set(schema.getField("qtyToPick"), pickObj.getQtyToPick());
		bldr.set(schema.getField("qtyPicked"), pickObj.getQtyPicked());
		bldr.set(schema.getField("fromContainer"), StringUtils.defaultString(pickObj.getFromContainer()));
		bldr.set(schema.getField("toContainer"), StringUtils.defaultString(pickObj.getToContainer()));
		bldr.set(schema.getField("pickType"), StringUtils.defaultString(pickObj.getPickType()));
		bldr.set(schema.getField("waveNbr"), StringUtils.defaultString(pickObj.getWaveNbr()));
		bldr.set(schema.getField("orderNbr"),StringUtils.defaultString(pickObj.getOrderNbr()));
		bldr.set(schema.getField("packageNbr"), StringUtils.defaultString(pickObj.getPackageNbr()));
		bldr.set(schema.getField("busUnit"), pickObj.getBusUnit());
		bldr.set(schema.getField("statCode"), pickObj.getStatCode());
		bldr.set(schema.getField("userId"), pickObj.getUserId());		
		
		return getJsonFromGenericRecord(bldr.build());
	}
}

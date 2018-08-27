package com.example.picking.schema;

import java.io.File;
import java.io.IOException;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.example.picking.db.Pick;

import io.confluent.kafka.schemaregistry.client.SchemaMetadata;

public class PickConfirmRequestSchemaRepo extends AvroSchemaRepo{
	public PickConfirmRequestSchemaRepo(String schemaRegistryEndpoint, String schemaFilePath) throws Exception{
		super(schemaRegistryEndpoint, "PickConfirmRequest", schemaFilePath+"avroPickConfirmRequest.avsc");
	}
	
	public Pick getPickConfirmRecord(String pickConfirmJsonStr) throws IOException {
		GenericRecord record = getGenericRecord(pickConfirmJsonStr);
		Pick pickObj = new Pick();
		pickObj.setId((Long)record.get("id"));
		pickObj.setLocnNbr((Integer)record.get("locnNbr"));
		pickObj.setLocnBrcd(record.get("locnBrcd").toString());
		pickObj.setItemBrcd(record.get("itemBrcd").toString());
		pickObj.setQtyToPick((Integer)record.get("qtyPicked"));
		pickObj.setToContainer(record.get("toContainer").toString());
		pickObj.setPickType(record.get("pickType").toString());
		pickObj.setWaveNbr(record.get("waveNbr").toString());
		pickObj.setPackageNbr(record.get("orderNbr").toString());
		pickObj.setPackageNbr(record.get("packageNbr").toString());
		pickObj.setBusUnit(record.get("busUnit").toString());
		
		pickObj.setStatCode(0);
		pickObj.setCreatedDttm(new java.util.Date());
		pickObj.setUpdatedDttm(new java.util.Date());
		pickObj.setUserId(record.get("userId").toString());
		return pickObj;
	}
}

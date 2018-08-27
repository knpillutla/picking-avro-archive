package com.example.picking.schema;

import java.io.File;
import java.io.IOException;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.example.picking.db.Pick;

import io.confluent.kafka.schemaregistry.client.SchemaMetadata;


public class NewPickCreationRequestSchemaRepo extends AvroSchemaRepo{

	public NewPickCreationRequestSchemaRepo(String schemaRegistryEndpoint, String schemaFilePath) throws Exception{
		super(schemaRegistryEndpoint, "NewPickRequest", schemaFilePath+"avroNewPickCreationRequest.avsc");
	}
	
	public Pick createNewPick(String newPickJsonStr) throws IOException {
		GenericRecord record = getGenericRecord(newPickJsonStr);
		
		Pick newPickObj = new Pick();
		newPickObj.setLocnNbr((Integer)record.get("locnNbr"));
		newPickObj.setLocnBrcd(record.get("locnBrcd").toString());
		newPickObj.setItemBrcd(record.get("itemBrcd").toString());
		newPickObj.setQtyToPick((Integer)record.get("qtyToPick"));
		newPickObj.setQtyPicked(0);
		newPickObj.setToContainer(record.get("toContainer").toString());
		newPickObj.setPickType(record.get("pickType").toString());
		newPickObj.setWaveNbr(record.get("waveNbr").toString());
		newPickObj.setOrderNbr(record.get("orderNbr").toString());
		newPickObj.setPackageNbr(record.get("packageNbr").toString());
		newPickObj.setBusUnit(record.get("busUnit").toString());
		
		newPickObj.setStatCode(0);
		newPickObj.setCreatedDttm(new java.util.Date());
		newPickObj.setUpdatedDttm(new java.util.Date());
		newPickObj.setUserId(record.get("userId").toString());
		return newPickObj;
	}
}

package com.example.picking.endpoint;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.picking.schema.ErrorResponseSchemaRepo;
import com.example.picking.schema.NewPickCreationRequestSchemaRepo;
import com.example.picking.schema.PickConfirmRequestSchemaRepo;
import com.example.picking.schema.PickResourceResponseSchemaRepo;

import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
@Controller
@RequestMapping("/v1/schemas")
public class PickingSchemaRepoRestEndPoint {

	@Autowired
	NewPickCreationRequestSchemaRepo newPickCreationRequestSchRegRepo;
	
	@Autowired
	ErrorResponseSchemaRepo errorResSchRegRepo;

	@Autowired
	PickConfirmRequestSchemaRepo pickConfirmRequestSchRegRepo;

	@Autowired
	PickResourceResponseSchemaRepo pickResourceResSchRegRepo;

	@GetMapping("/")
	public ResponseEntity getSchemaNames() throws IOException {
		try {
			StringBuilder strBldr = new StringBuilder();
			strBldr.append(newPickCreationRequestSchRegRepo.getSchemaName());
			strBldr.append(",");
			strBldr.append(pickConfirmRequestSchRegRepo.getSchemaName());
			strBldr.append(",");
			strBldr.append(pickResourceResSchRegRepo.getSchemaName());
			strBldr.append(",");
			strBldr.append(errorResSchRegRepo.getSchemaName());
			return ResponseEntity.ok(strBldr.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(errorResSchRegRepo.getErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occured while registering schema"));
		}
	}
	
	@GetMapping("/{schemaName}")
	public ResponseEntity getSchemaMetaData(@PathVariable("schemaName") String schemaName) throws IOException {
		try {
			SchemaMetadata schMetaData = null;
			schMetaData = (schMetaData == null && newPickCreationRequestSchRegRepo.getSchemaName().equals(schemaName)) ? newPickCreationRequestSchRegRepo.getSchemaMetaData():null;
			schMetaData = (schMetaData == null && pickConfirmRequestSchRegRepo.getSchemaName().equals(schemaName)) ? newPickCreationRequestSchRegRepo.getSchemaMetaData():null;
			schMetaData = (schMetaData == null && pickResourceResSchRegRepo.getSchemaName().equals(schemaName)) ? newPickCreationRequestSchRegRepo.getSchemaMetaData():null;
			schMetaData = (schMetaData == null && errorResSchRegRepo.getSchemaName().equals(schemaName)) ? newPickCreationRequestSchRegRepo.getSchemaMetaData():null;
			
			return ResponseEntity.ok(schMetaData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(errorResSchRegRepo.getErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occured while registering schema"));
		}
	}
}

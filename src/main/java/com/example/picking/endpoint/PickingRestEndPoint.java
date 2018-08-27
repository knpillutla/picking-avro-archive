package com.example.picking.endpoint;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.picking.schema.ErrorResponseSchemaRepo;
import com.example.picking.service.PickingService;
@Controller
@RequestMapping("/v1")
public class PickingRestEndPoint {

	@Autowired
	PickingService pickingService;
	
	@Autowired
	ErrorResponseSchemaRepo errorResSchRegRepo;	
	
	@GetMapping("/")
	public ResponseEntity hello() throws IOException {
		try {
			return ResponseEntity.ok("Hello Krishna!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(errorResSchRegRepo.getErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occured while getting next pick task"));
		}
	}

	@GetMapping("/{locnNbr}/picks/next")
	public ResponseEntity getNextPick() throws IOException {
		try {
			return ResponseEntity.ok(pickingService.getNextPick());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(errorResSchRegRepo.getErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occured while getting next pick task"));
		}
	}
	
	@GetMapping("/{locnNbr}/picks/{id}")
	public ResponseEntity getByPickId(@PathVariable("locnNbr") Integer locnNbr, @PathVariable("id") Long pickId) throws IOException {
		try {
			return ResponseEntity.ok(pickingService.findById(locnNbr, pickId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(errorResSchRegRepo.getErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occured while getting next pick task"));
		}
	}

	@PostMapping("/{locnNbr}/picks/{id}")
	public ResponseEntity confirmPick(@PathVariable("locnNbr") Integer locnNbr, @PathVariable("id") Long id, @RequestBody String pickingSchemaDataJsonStr) throws IOException {
		try {
			return ResponseEntity.ok(pickingService.confirmPick(pickingSchemaDataJsonStr));
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(errorResSchRegRepo.getErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occured while updating pick task:" + pickingSchemaDataJsonStr));
		}
	}	
	
	@PutMapping("/{locnNbr}/picks")
	public ResponseEntity newPickTask(@PathVariable("locnNbr") Integer locnNbr, @RequestBody String pickingSchemaDataJsonStr) throws IOException {
		try {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(pickingService.createPick(pickingSchemaDataJsonStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(errorResSchRegRepo.getErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occured while creating new pick task:" + pickingSchemaDataJsonStr));
		}
	}	
}

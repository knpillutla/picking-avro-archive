package com.example.picking.service;

import java.io.IOException;

import org.apache.avro.generic.GenericRecord;

public interface PickingService {
	public String getNextPick() throws Exception;

	public String confirmPick(String pickConfirmJsonStr) throws Exception;

	public String createPick(String newPickJsonStr) throws Exception;
	
	public String findById(Integer locnNbr, Long pickId) throws Exception;
}
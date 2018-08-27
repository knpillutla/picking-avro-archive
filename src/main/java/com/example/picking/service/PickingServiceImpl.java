package com.example.picking.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.picking.db.Pick;
import com.example.picking.db.PickingRepository;
import com.example.picking.schema.ErrorResponseSchemaRepo;
import com.example.picking.schema.NewPickCreationRequestSchemaRepo;
import com.example.picking.schema.PickConfirmRequestSchemaRepo;
import com.example.picking.schema.PickResourceResponseSchemaRepo;

@Service
public class PickingServiceImpl implements PickingService {
	
	@Autowired
	PickingRepository pickDAO;
	
	@Autowired
	NewPickCreationRequestSchemaRepo newPickSchRegRepo;
	
	@Autowired
	PickResourceResponseSchemaRepo pickResourceResponseSchRegRepo;

	@Autowired
	PickConfirmRequestSchemaRepo pickConfirmSchRegRepo;

	@Autowired
	ErrorResponseSchemaRepo errorSchemaRegistryRepo;

	/* (non-Javadoc)
	 * @see com.example.demo.PickingService#getNextPick()
	 */
	@Override
	public String getNextPick() throws Exception {
		Optional<Pick> pickTaskEntityOptional = pickDAO.findById((long) 1);
		if(pickTaskEntityOptional.isPresent())
			return pickResourceResponseSchRegRepo.getPickResourceResponseJSON(pickTaskEntityOptional.get());
		return null;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.PickingService#confirmPick(com.example.AvroPickTask)
	 */
	@Override
	public String confirmPick(String pickConfirmJsonStr) throws Exception{
		Pick pickObj = pickConfirmSchRegRepo.getPickConfirmRecord(pickConfirmJsonStr);
		System.out.println("confirmPick Start, :" + pickObj.toString());
		Pick updatedPickObj = pickDAO.save(pickObj);
		String returnStr = pickResourceResponseSchRegRepo.getPickResourceResponseJSON(updatedPickObj);
		System.out.println("confirmPick End, updated pick obj:" + pickObj.toString());
		return returnStr;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.PickingService#createNew(com.example.AvroPickTask)
	 */
	@Override
	public String createPick(String newPickJsonStr) throws IOException {
		Pick newPick = newPickSchRegRepo.createNewPick(newPickJsonStr);
		System.out.println("createPick Start, received new pick request:" + newPick.toString());
		Pick savedPickObj = pickDAO.save(newPick);
		String returnStr = pickResourceResponseSchRegRepo.getPickResourceResponseJSON(savedPickObj);
		System.out.println("createPick End, created new pick:" + savedPickObj.toString());
		return returnStr;
	}

	@Override
	public String findById(Integer locnNbr, Long pickId) throws Exception {
		System.out.println("findById Start:" + locnNbr + ",pickId:"+pickId);
		String returnStr = pickResourceResponseSchRegRepo.getPickResourceResponseJSON(pickDAO.findById(locnNbr, pickId));
		System.out.println("findById End:" + locnNbr + ",pickId:"+pickId);
		return returnStr;
	}

}

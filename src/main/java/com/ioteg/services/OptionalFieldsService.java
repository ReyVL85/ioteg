package com.ioteg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;
import com.ioteg.repositories.OptionalFieldsRepository;

@Service
public class OptionalFieldsService {

	private BlockService blockService;
	private OptionalFieldsRepository optionalFieldsRepository;
	private UserService userService;
	
	/**
	 * @param blockService
	 * @param optionalFieldsRepository
	 * @param userService
	 */
	@Autowired
	public OptionalFieldsService(BlockService blockService, OptionalFieldsRepository optionalFieldsRepository,
			UserService userService) {
		super();
		this.blockService = blockService;
		this.optionalFieldsRepository = optionalFieldsRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public OptionalFields createOptionalFields(Long blockId, OptionalFields optionalFields) throws EntityNotFoundException {
		
		optionalFields.setOwner(userService.loadLoggedUser());
		OptionalFields storedOptionalFields = optionalFieldsRepository.save(optionalFields);
		
		Block block = blockService.loadByIdWithOptionalFields(blockId);
		block.getOptionalFields().add(storedOptionalFields);
		blockService.save(block);

		return this.loadByIdWithFields(storedOptionalFields.getId());
	}
	
	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER') or hasRole('ADMIN')")
	public List<Field> getAllFields(Long optionalFieldsId) {
		return optionalFieldsRepository.findAllFieldsOf(optionalFieldsId);
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER') or hasRole('ADMIN')")
	public OptionalFields modifyOptionalFields(Long optionalFieldsId, OptionalFields optionalFields) throws EntityNotFoundException {
		OptionalFields storedOptionalFields = this.loadByIdWithFields(optionalFieldsId);

		storedOptionalFields.setMandatory(optionalFields.getMandatory());
		
		return optionalFieldsRepository.save(storedOptionalFields);
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER') or hasRole('ADMIN')")
	public void removeOptionalFields(Long optionalFieldsId) {
		optionalFieldsRepository.deleteById(optionalFieldsId);
	}
	
	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public void removeOptionalFieldsFromBlock(Long blockId, Long optionalFieldsId) throws EntityNotFoundException {
		
		Block block = blockService.loadByIdWithOptionalFields(blockId);
		block.getOptionalFields().remove(this.loadById(optionalFieldsId));
		blockService.save(block);
		
		this.removeOptionalFields(optionalFieldsId);
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER') or hasRole('ADMIN')")
	public OptionalFields loadById(Long optionalFieldsId) throws EntityNotFoundException {
		return optionalFieldsRepository.findById(optionalFieldsId)
				.orElseThrow(() -> new EntityNotFoundException(OptionalFields.class, "id", optionalFieldsId.toString()));
	}
	
	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER') or hasRole('ADMIN')")
	public OptionalFields loadByIdWithFields(Long optionalFieldsId) throws EntityNotFoundException {
		return optionalFieldsRepository.findByIdWithFields(optionalFieldsId)
				.orElseThrow(() -> new EntityNotFoundException(OptionalFields.class, "id", optionalFieldsId.toString()));
	}

	public OptionalFields save(OptionalFields optionalFields) {
		return optionalFieldsRepository.save(optionalFields);
	}
}

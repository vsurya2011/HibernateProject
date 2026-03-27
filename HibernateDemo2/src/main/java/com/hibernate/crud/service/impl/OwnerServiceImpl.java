package com.hibernate.crud.service.impl;

import java.util.List;
import java.util.Objects;

import com.hibernate.crud.config.PropertiesConfig;
import com.hibernate.crud.dto.OwnerDTO;
import com.hibernate.crud.exception.DuplicateOwnerFoundException;
import com.hibernate.crud.exception.OwnerNotFoundException;
import com.hibernate.crud.repository.OwnerRepository;
import com.hibernate.crud.service.OwnerService;
import com.hibernate.crud.util.MapperUtil;
import com.hibernate.crud.repository.impl.OwnerRepositoryImpl;
import com.hibernate.crud.entity.Owner;

public class OwnerServiceImpl implements OwnerService {
    private OwnerRepository ownerRepository;
    private static final String OWNER_ALREADY_EXISTS = "owner.already.exists";
    private static final String OWNER_NOT_FOUND = "owner.not.found";
    private static final PropertiesConfig PROPERTIES_CONFIG = PropertiesConfig.getInstance();

    public OwnerServiceImpl() {
        this.ownerRepository = new OwnerRepositoryImpl();
    }

    @Override
    public void saveOwner(OwnerDTO ownerDTO) throws DuplicateOwnerFoundException {
        Owner existingOwner = ownerRepository.findOwner(ownerDTO.getId());
        if (Objects.nonNull(existingOwner)) {
            throw new DuplicateOwnerFoundException(
                    String.format(PROPERTIES_CONFIG.getProperty(OWNER_ALREADY_EXISTS), ownerDTO.getId()));
        }
        Owner owner = MapperUtil.convertOwnerDtoToEntity(ownerDTO);
        ownerRepository.saveOwner(owner);
    }

    @Override
    public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findOwner(ownerId);
        if (Objects.isNull(owner)) {
            throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
        }
        return MapperUtil.convertOwnerEntityToDto(owner);
    }

    @Override
    public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findOwner(ownerId);
        if (Objects.isNull(owner)) {
            throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
        }
        ownerRepository.updatePetDetails(ownerId, petName);
    }

    @Override
    public void deleteOwner(int ownerId) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findOwner(ownerId);
        if (Objects.isNull(owner)) {
            throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
        }
        ownerRepository.deleteOwner(ownerId);
    }

    @Override
    public List<OwnerDTO> findAllOwners() {
        return ownerRepository.findAllOwners().stream().map(owner-> (OwnerDTO) MapperUtil.EntityToDTO(owner)).toList();
    }
}
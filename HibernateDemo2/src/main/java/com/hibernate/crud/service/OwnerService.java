package com.hibernate.crud.service;

import com.hibernate.crud.dto.OwnerDTO;
import com.hibernate.crud.exception.DuplicateOwnerFoundException;
import com.hibernate.crud.exception.OwnerNotFoundException;

import java.util.List;

public interface OwnerService {

    void saveOwner(OwnerDTO ownerDTO) throws DuplicateOwnerFoundException;

    OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

    void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

    void deleteOwner(int ownerId) throws OwnerNotFoundException;

    List<OwnerDTO> findAllOwners();
}
package com.hibernate.crud.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hibernate.crud.config.DatabaseConfig;
import com.hibernate.crud.entity.Owner;
import com.hibernate.crud.repository.OwnerRepository;

import java.util.List;

public class OwnerRepositoryImpl implements OwnerRepository {
    private SessionFactory sessionFactory = DatabaseConfig.getSessionFactory();

    @Override
    public void saveOwner(Owner owner) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(owner);
            transaction.commit();
        }
    }

    @Override
    public Owner findOwner(int ownerId) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Owner.class, ownerId);
        }
    }

    @Override
    public void updatePetDetails(int ownerId, String petName) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            var owner = session.find(Owner.class, ownerId);
            owner.setPetName(petName);
            session.merge(owner);
            transaction.commit();
        }
    }

    @Override
    public void deleteOwner(int ownerId) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            var owner = session.find(Owner.class, ownerId);
            session.remove(owner);
            transaction.commit();
        }
    }

    @Override
    public List<Owner> findAllOwners() {
        String hql="SELECT o FROM Owner o";
        try(Session session = sessionFactory.openSession()) {
            return session
                    .createSelectionQuery(hql,Owner.class)
                    .list();
        }
    }
}
package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.PromoIncompatible;
import com.link360.repository.PromoIncompatibleRepository;
import com.link360.repository.PromocionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoIncompatibleService {

    private final PromoIncompatibleRepository repo;
    private final PromocionRepository promoRepo;

    public PromoIncompatibleService(PromoIncompatibleRepository repo, PromocionRepository promoRepo) {
        this.repo = repo;
        this.promoRepo = promoRepo;
    }

    public List<PromoIncompatible> getAll() {
        return repo.findAll();
    }

    public Optional<PromoIncompatible> getById(int p1, int p2) {
        return repo.findById(p1, p2);
    }

    public List<com.link360.model.Promocion> getPromocionesForDropdown() {
        return promoRepo.findAllForDropdown();
    }

    public void create(PromoIncompatible pi) {
        try {
            repo.insert(pi);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(int p1, int p2) {
        try {
            repo.delete(p1, p2);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

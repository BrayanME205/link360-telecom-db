package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.PromoPaquete;
import com.link360.repository.PaqueteRepository;
import com.link360.repository.PromoPaqueteRepository;
import com.link360.repository.PromocionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoPaqueteService {

    private final PromoPaqueteRepository repo;
    private final PromocionRepository promoRepo;
    private final PaqueteRepository paqueteRepo;

    public PromoPaqueteService(PromoPaqueteRepository repo, PromocionRepository promoRepo, PaqueteRepository paqueteRepo) {
        this.repo = repo;
        this.promoRepo = promoRepo;
        this.paqueteRepo = paqueteRepo;
    }

    public List<PromoPaquete> getAll() {
        return repo.findAll();
    }

    public Optional<PromoPaquete> getById(int codPromocion, int codPaquete) {
        return repo.findById(codPromocion, codPaquete);
    }

    public List<com.link360.model.Promocion> getPromocionesForDropdown() {
        return promoRepo.findAllForDropdown();
    }

    public List<com.link360.model.Paquete> getPaquetesForDropdown() {
        return paqueteRepo.findAll();
    }

    public void create(PromoPaquete pp) {
        try {
            repo.insert(pp);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(int codPromocion, int codPaquete) {
        try {
            repo.delete(codPromocion, codPaquete);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

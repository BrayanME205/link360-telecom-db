package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.PromoServicio;
import com.link360.repository.PromoServicioRepository;
import com.link360.repository.PromocionRepository;
import com.link360.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoServicioService {

    private final PromoServicioRepository repo;
    private final PromocionRepository promoRepo;
    private final ServicioRepository servicioRepo;

    public PromoServicioService(PromoServicioRepository repo, PromocionRepository promoRepo, ServicioRepository servicioRepo) {
        this.repo = repo;
        this.promoRepo = promoRepo;
        this.servicioRepo = servicioRepo;
    }

    public List<PromoServicio> getAll() {
        return repo.findAll();
    }

    public Optional<PromoServicio> getById(int codPromocion, int codServicio) {
        return repo.findById(codPromocion, codServicio);
    }

    public List<com.link360.model.Promocion> getPromocionesForDropdown() {
        return promoRepo.findAllForDropdown();
    }

    public List<com.link360.model.Servicio> getServiciosForDropdown() {
        return servicioRepo.findAll();
    }

    public void create(PromoServicio ps) {
        try {
            repo.insert(ps);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(int codPromocion, int codServicio) {
        try {
            repo.delete(codPromocion, codServicio);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

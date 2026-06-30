package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.PromoPlan;
import com.link360.repository.PlanRepository;
import com.link360.repository.PromoPlanRepository;
import com.link360.repository.PromocionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoPlanService {

    private final PromoPlanRepository repo;
    private final PromocionRepository promoRepo;
    private final PlanRepository planRepo;

    public PromoPlanService(PromoPlanRepository repo, PromocionRepository promoRepo, PlanRepository planRepo) {
        this.repo = repo;
        this.promoRepo = promoRepo;
        this.planRepo = planRepo;
    }

    public List<PromoPlan> getAll() {
        return repo.findAll();
    }

    public Optional<PromoPlan> getById(int codPromocion, int codPlan) {
        return repo.findById(codPromocion, codPlan);
    }

    public List<com.link360.model.Promocion> getPromocionesForDropdown() {
        return promoRepo.findAllForDropdown();
    }

    public List<com.link360.model.Plan> getPlanesForDropdown() {
        return planRepo.findAllForDropdown();
    }

    public void create(PromoPlan pp) {
        try {
            repo.insert(pp);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(int codPromocion, int codPlan) {
        try {
            repo.delete(codPromocion, codPlan);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

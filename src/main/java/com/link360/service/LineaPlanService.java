package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.LineaPlan;
import com.link360.repository.LineaMovilRepository;
import com.link360.repository.LineaPlanRepository;
import com.link360.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineaPlanService {

    private final LineaPlanRepository repo;
    private final LineaMovilRepository lineaRepo;
    private final PlanRepository planRepo;

    public LineaPlanService(LineaPlanRepository repo, LineaMovilRepository lineaRepo, PlanRepository planRepo) {
        this.repo = repo;
        this.lineaRepo = lineaRepo;
        this.planRepo = planRepo;
    }

    public List<LineaPlan> getAll() {
        return repo.findAll();
    }

    public Optional<LineaPlan> getById(String numero, int codPlan, String fecha) {
        return repo.findById(numero, codPlan, fecha);
    }

    public List<com.link360.model.LineaMovil> getLineasForDropdown() {
        return lineaRepo.findAllForDropdown();
    }

    public List<com.link360.model.Plan> getPlanesForDropdown() {
        return planRepo.findAllForDropdown();
    }

    public void create(LineaPlan lp) {
        try {
            repo.insert(lp);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(LineaPlan lp) {
        try {
            repo.update(lp);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(String numero, int codPlan, String fecha) {
        try {
            repo.delete(numero, codPlan, fecha);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.CategoriaComercial;
import com.link360.model.Plan;
import com.link360.repository.CategoriaComercialRepository;
import com.link360.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository repo;
    private final CategoriaComercialRepository catRepo;

    public PlanService(PlanRepository repo, CategoriaComercialRepository catRepo) {
        this.repo = repo;
        this.catRepo = catRepo;
    }

    public List<Plan> getAll() {
        return repo.findAll();
    }

    public Optional<Plan> getById(int id) {
        return repo.findById(id);
    }

    public List<Plan> getAllForDropdown() {
        return repo.findAllForDropdown();
    }

    public List<CategoriaComercial> getCategoriasForDropdown() {
        return catRepo.findAll();
    }

    public void create(Plan p) {
        try {
            repo.insert(p);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(Plan p) {
        try {
            repo.update(p);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(int id) {
        try {
            repo.delete(id);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public String getDeleteWarning(int id) {
        if (repo.hasLineas(id)) {
            return "Este plan tiene líneas asociadas en el historial. No se puede eliminar mientras existan líneas que lo hayan contratado.";
        }
        return null;
    }
}

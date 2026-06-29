package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Factura;
import com.link360.repository.FacturaRepository;
import com.link360.repository.LineaMovilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    private final FacturaRepository repo;
    private final LineaMovilRepository lineaRepo;

    public FacturaService(FacturaRepository repo, LineaMovilRepository lineaRepo) {
        this.repo = repo;
        this.lineaRepo = lineaRepo;
    }

    public List<Factura> getAll() {
        return repo.findAll();
    }

    public Optional<Factura> getById(int id) {
        return repo.findById(id);
    }

    public List<Factura> getAllForDropdown() {
        return repo.findAllForDropdown();
    }

    public List<com.link360.model.LineaMovil> getLineasForDropdown() {
        return lineaRepo.findAllForDropdown();
    }

    public void create(Factura f) {
        try {
            repo.insert(f);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(Factura f) {
        try {
            repo.update(f);
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

    public String getCascadeWarning(int id) {
        if (repo.hasConceptos(id)) {
            return "Esta factura tiene conceptos de cobro asociados. Al eliminarla se eliminarán también todos sus conceptos en cascada.";
        }
        return null;
    }
}

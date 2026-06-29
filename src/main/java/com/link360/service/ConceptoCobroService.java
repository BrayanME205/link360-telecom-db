package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.ConceptoCobro;
import com.link360.repository.ConceptoCobroRepository;
import com.link360.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConceptoCobroService {

    private final ConceptoCobroRepository repo;
    private final FacturaRepository facturaRepo;

    public ConceptoCobroService(ConceptoCobroRepository repo, FacturaRepository facturaRepo) {
        this.repo = repo;
        this.facturaRepo = facturaRepo;
    }

    public List<ConceptoCobro> getAll() {
        return repo.findAll();
    }

    public Optional<ConceptoCobro> getById(int numFactura, String tipoConcepto) {
        return repo.findById(numFactura, tipoConcepto);
    }

    public List<com.link360.model.Factura> getFacturasForDropdown() {
        return facturaRepo.findAllForDropdown();
    }

    public void create(ConceptoCobro c) {
        try {
            repo.insert(c);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(ConceptoCobro c) {
        try {
            repo.update(c);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(int numFactura, String tipoConcepto) {
        try {
            repo.delete(numFactura, tipoConcepto);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

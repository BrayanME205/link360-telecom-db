package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.CategoriaComercial;
import com.link360.repository.CategoriaComercialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaComercialService {

    private final CategoriaComercialRepository repo;

    public CategoriaComercialService(CategoriaComercialRepository repo) {
        this.repo = repo;
    }

    public List<CategoriaComercial> getAll() {
        return repo.findAll();
    }

    public Optional<CategoriaComercial> getById(int id) {
        return repo.findById(id);
    }

    public void create(CategoriaComercial c) {
        try {
            repo.insert(c);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(CategoriaComercial c) {
        try {
            repo.update(c);
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
        if (repo.hasPlanes(id)) {
            return "Esta categoría tiene planes asociados. No se puede eliminar mientras existan planes que la referencien.";
        }
        return null;
    }
}

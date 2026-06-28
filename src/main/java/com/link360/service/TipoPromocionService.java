package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.TipoPromocion;
import com.link360.repository.TipoPromocionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoPromocionService {

    private final TipoPromocionRepository repo;

    public TipoPromocionService(TipoPromocionRepository repo) {
        this.repo = repo;
    }

    public List<TipoPromocion> getAll() {
        return repo.findAll();
    }

    public Optional<TipoPromocion> getById(String id) {
        return repo.findById(id);
    }

    public void create(TipoPromocion t) {
        try {
            repo.insert(t);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(TipoPromocion t) {
        try {
            repo.update(t);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(String id) {
        try {
            repo.delete(id);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public String getDeleteWarning(String id) {
        if (repo.hasPromociones(id)) {
            return "Este tipo de promoción tiene promociones asociadas. No se puede eliminar mientras existan promociones que lo referencien.";
        }
        return null;
    }
}

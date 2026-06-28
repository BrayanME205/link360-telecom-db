package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Descuento;
import com.link360.repository.DescuentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DescuentoService {

    private final DescuentoRepository repo;

    public DescuentoService(DescuentoRepository repo) {
        this.repo = repo;
    }

    public List<Descuento> getAll() {
        return repo.findAll();
    }

    public Optional<Descuento> getById(int id) {
        return repo.findById(id);
    }

    public void create(Descuento d) {
        try {
            repo.insert(d);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(Descuento d) {
        try {
            repo.update(d);
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
        if (repo.hasConsumos(id)) {
            return "ADVERTENCIA (SET NULL): Este descuento está aplicado a uno o más consumos. Al eliminarlo, el campo de descuento en esos consumos quedará en NULL, pero los consumos NO serán eliminados.";
        }
        return null;
    }
}

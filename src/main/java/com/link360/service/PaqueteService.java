package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Paquete;
import com.link360.repository.PaqueteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaqueteService {

    private final PaqueteRepository repo;

    public PaqueteService(PaqueteRepository repo) {
        this.repo = repo;
    }

    public List<Paquete> getAll() {
        return repo.findAll();
    }

    public Optional<Paquete> getById(int id) {
        return repo.findById(id);
    }

    public void create(Paquete p) {
        try {
            repo.insert(p);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(Paquete p) {
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
        if (repo.hasHistorial(id)) {
            return "Este paquete tiene historial de adquisiciones registradas. No se puede eliminar mientras existan registros históricos asociados.";
        }
        return null;
    }
}

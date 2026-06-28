package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Servicio;
import com.link360.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepository repo;

    public ServicioService(ServicioRepository repo) {
        this.repo = repo;
    }

    public List<Servicio> getAll() {
        return repo.findAll();
    }

    public Optional<Servicio> getById(int id) {
        return repo.findById(id);
    }

    public void create(Servicio s) {
        try {
            repo.insert(s);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(Servicio s) {
        try {
            repo.update(s);
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
            return "Este servicio está contratado por una o más líneas. No se puede eliminar mientras haya líneas que lo tengan activo.";
        }
        return null;
    }
}

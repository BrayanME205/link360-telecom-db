package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.HistorialPaquete;
import com.link360.repository.HistorialPaqueteRepository;
import com.link360.repository.LineaMovilRepository;
import com.link360.repository.PaqueteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialPaqueteService {

    private final HistorialPaqueteRepository repo;
    private final LineaMovilRepository lineaRepo;
    private final PaqueteRepository paqueteRepo;

    public HistorialPaqueteService(HistorialPaqueteRepository repo,
            LineaMovilRepository lineaRepo,
            PaqueteRepository paqueteRepo) {
        this.repo = repo;
        this.lineaRepo = lineaRepo;
        this.paqueteRepo = paqueteRepo;
    }

    public List<HistorialPaquete> getAll() {
        return repo.findAll();
    }

    public Optional<HistorialPaquete> getById(String numero, int codPaquete, String fecha) {
        return repo.findById(numero, codPaquete, fecha);
    }

    public List<com.link360.model.LineaMovil> getLineasForDropdown() {
        return lineaRepo.findAllForDropdown();
    }

    public List<com.link360.model.Paquete> getPaquetesForDropdown() {
        return paqueteRepo.findAll();
    }

    public void create(HistorialPaquete h) {
        try {
            repo.insert(h);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(HistorialPaquete h) {
        try {
            repo.update(h);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(String numero, int codPaquete, String fecha) {
        try {
            repo.delete(numero, codPaquete, fecha);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.RedencionPuntos;
import com.link360.repository.PuntosFidelizacionRepository;
import com.link360.repository.RedencionPuntosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedencionPuntosService {

    private final RedencionPuntosRepository repo;
    private final PuntosFidelizacionRepository puntosRepo;

    public RedencionPuntosService(RedencionPuntosRepository repo,
            PuntosFidelizacionRepository puntosRepo) {
        this.repo = repo;
        this.puntosRepo = puntosRepo;
    }

    public List<RedencionPuntos> getAll() {
        return repo.findAll();
    }

    public Optional<RedencionPuntos> getById(String cedula, int numFactura, String fechaRedencion) {
        return repo.findById(cedula, numFactura, fechaRedencion);
    }

    public List<com.link360.model.PuntosFidelizacion> getPuntosForDropdown() {
        return puntosRepo.findAll();
    }

    public void create(RedencionPuntos r) {
        try {
            repo.insert(r);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(RedencionPuntos r) {
        try {
            repo.update(r);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(String cedula, int numFactura, String fechaRedencion) {
        try {
            repo.delete(cedula, numFactura, fechaRedencion);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

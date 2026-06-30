package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.LineaServicio;
import com.link360.repository.LineaMovilRepository;
import com.link360.repository.LineaServicioRepository;
import com.link360.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineaServicioService {

    private final LineaServicioRepository repo;
    private final LineaMovilRepository lineaRepo;
    private final ServicioRepository servicioRepo;

    public LineaServicioService(LineaServicioRepository repo, LineaMovilRepository lineaRepo, ServicioRepository servicioRepo) {
        this.repo = repo;
        this.lineaRepo = lineaRepo;
        this.servicioRepo = servicioRepo;
    }

    public List<LineaServicio> getAll() {
        return repo.findAll();
    }

    public Optional<LineaServicio> getById(String numero, int codServicio) {
        return repo.findById(numero, codServicio);
    }

    public List<com.link360.model.LineaMovil> getLineasForDropdown() {
        return lineaRepo.findAllForDropdown();
    }

    public List<com.link360.model.Servicio> getServiciosForDropdown() {
        return servicioRepo.findAll();
    }

    public void create(LineaServicio ls) {
        try {
            repo.insert(ls);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(LineaServicio ls) {
        try {
            repo.update(ls);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(String numero, int codServicio) {
        try {
            repo.delete(numero, codServicio);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

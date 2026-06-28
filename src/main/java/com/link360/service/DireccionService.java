package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Cliente;
import com.link360.model.Direccion;
import com.link360.repository.ClienteRepository;
import com.link360.repository.DireccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DireccionService {

    private final DireccionRepository repo;
    private final ClienteRepository clienteRepo;

    public DireccionService(DireccionRepository repo, ClienteRepository clienteRepo) {
        this.repo = repo;
        this.clienteRepo = clienteRepo;
    }

    public List<Direccion> getAll() { return repo.findAll(); }
    public Optional<Direccion> getById(String cedula) { return repo.findById(cedula); }
    public List<Cliente> getClientesForDropdown() { return clienteRepo.findAllForDropdown(); }

    public void create(Direccion d) {
        try { repo.insert(d); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void update(Direccion d) {
        try { repo.update(d); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void delete(String cedula) {
        try { repo.delete(cedula); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }
}
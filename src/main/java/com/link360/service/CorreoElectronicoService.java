package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Cliente;
import com.link360.model.CorreoElectronico;
import com.link360.repository.ClienteRepository;
import com.link360.repository.CorreoElectronicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorreoElectronicoService {

    private final CorreoElectronicoRepository repo;
    private final ClienteRepository clienteRepo;

    public CorreoElectronicoService(CorreoElectronicoRepository repo, ClienteRepository clienteRepo) {
        this.repo = repo;
        this.clienteRepo = clienteRepo;
    }

    public List<CorreoElectronico> getAll() { return repo.findAll(); }
    public Optional<CorreoElectronico> getById(String cedula, String email) { return repo.findById(cedula, email); }
    public List<Cliente> getClientesForDropdown() { return clienteRepo.findAllForDropdown(); }

    public void create(CorreoElectronico c) {
        try { repo.insert(c); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void update(CorreoElectronico c) {
        try { repo.update(c); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void delete(String cedula, String email) {
        try { repo.delete(cedula, email); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }
}
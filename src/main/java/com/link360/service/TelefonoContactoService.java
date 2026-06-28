package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Cliente;
import com.link360.model.TelefonoContacto;
import com.link360.repository.ClienteRepository;
import com.link360.repository.TelefonoContactoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoContactoService {

    private final TelefonoContactoRepository repo;
    private final ClienteRepository clienteRepo;

    public TelefonoContactoService(TelefonoContactoRepository repo, ClienteRepository clienteRepo) {
        this.repo = repo;
        this.clienteRepo = clienteRepo;
    }

    public List<TelefonoContacto> getAll() {
        return repo.findAll();
    }

    public Optional<TelefonoContacto> getById(String cedula, String numero) {
        return repo.findById(cedula, numero);
    }

    public List<Cliente> getClientesForDropdown() {
        return clienteRepo.findAllForDropdown();
    }

    public void create(TelefonoContacto t) {
        try {
            repo.insert(t);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(TelefonoContacto t) {
        try {
            repo.update(t);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(String cedula, String numero) {
        try {
            repo.delete(cedula, numero);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

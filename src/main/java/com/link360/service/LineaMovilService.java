package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Cliente;
import com.link360.model.LineaMovil;
import com.link360.repository.ClienteRepository;
import com.link360.repository.LineaMovilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineaMovilService {

    private final LineaMovilRepository repo;
    private final ClienteRepository clienteRepo;

    public LineaMovilService(LineaMovilRepository repo, ClienteRepository clienteRepo) {
        this.repo = repo;
        this.clienteRepo = clienteRepo;
    }

    public List<LineaMovil> getAll() { return repo.findAll(); }
    public Optional<LineaMovil> getById(String id) { return repo.findById(id); }
    public List<LineaMovil> getAllForDropdown() { return repo.findAllForDropdown(); }
    public List<Cliente> getClientesForDropdown() { return clienteRepo.findAllForDropdown(); }

    public void create(LineaMovil l) {
        try { repo.insert(l); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void update(LineaMovil l) {
        try { repo.update(l); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void delete(String id) {
        try { repo.delete(id); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public String getCascadeWarning(String id) {
        boolean facturas = repo.hasFacturas(id);
        boolean consumos = repo.hasConsumos(id);
        if (facturas || consumos) {
            return "Esta línea tiene " +
                (facturas ? "facturas " : "") +
                (facturas && consumos ? "y " : "") +
                (consumos ? "consumos " : "") +
                "asociados. Al eliminarla se eliminarán también todos esos registros en cascada, " +
                "incluyendo historial de paquetes, planes contratados y servicios.";
        }
        return null;
    }
}
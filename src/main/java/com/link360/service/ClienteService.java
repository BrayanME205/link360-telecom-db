package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Cliente;
import com.link360.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> getAll() {
        return repo.findAll();
    }

    public Optional<Cliente> getById(String cedula) {
        return repo.findById(cedula);
    }

    public void create(Cliente c) {
        try {
            repo.insert(c);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(Cliente c) {
        try {
            repo.update(c);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(String cedula) {
        try {
            repo.delete(cedula);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public String getCascadeWarning(String cedula) {
        if (repo.hasLineas(cedula)) {
            return "Este cliente tiene líneas móviles asociadas. Al eliminarlo se eliminarán también "
                    + "sus líneas, consumos, facturas, puntos de fidelización, historial de paquetes, "
                    + "teléfonos de contacto, correos y dirección registrados.";
        }
        return null;
    }
}

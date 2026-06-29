package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.PuntosFidelizacion;
import com.link360.repository.ClienteRepository;
import com.link360.repository.FacturaRepository;
import com.link360.repository.PuntosFidelizacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuntosFidelizacionService {

    private final PuntosFidelizacionRepository repo;
    private final ClienteRepository clienteRepo;
    private final FacturaRepository facturaRepo;

    public PuntosFidelizacionService(PuntosFidelizacionRepository repo,
            ClienteRepository clienteRepo,
            FacturaRepository facturaRepo) {
        this.repo = repo;
        this.clienteRepo = clienteRepo;
        this.facturaRepo = facturaRepo;
    }

    public List<PuntosFidelizacion> getAll() {
        return repo.findAll();
    }

    public Optional<PuntosFidelizacion> getById(String cedula, int numFactura) {
        return repo.findById(cedula, numFactura);
    }

    public List<com.link360.model.Cliente> getClientesForDropdown() {
        return clienteRepo.findAllForDropdown();
    }

    public List<com.link360.model.Factura> getFacturasForDropdown() {
        return facturaRepo.findAllForDropdown();
    }

    public void create(PuntosFidelizacion p) {
        try {
            repo.insert(p);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void update(PuntosFidelizacion p) {
        try {
            repo.update(p);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(String cedula, int numFactura) {
        try {
            repo.delete(cedula, numFactura);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public String getCascadeWarning(String cedula, int numFactura) {
        if (repo.hasRedenciones(cedula, numFactura)) {
            return "Este registro de puntos tiene redenciones asociadas. Al eliminarlo se eliminarán también todas las redenciones en cascada.";
        }
        return null;
    }
}

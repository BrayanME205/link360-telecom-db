package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.*;
import com.link360.repository.ConsumoRepository;
import com.link360.repository.DescuentoRepository;
import com.link360.repository.LineaMovilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumoService {

    private final ConsumoRepository repo;
    private final LineaMovilRepository lineaRepo;
    private final DescuentoRepository descuentoRepo;

    public ConsumoService(ConsumoRepository repo, LineaMovilRepository lineaRepo,
                          DescuentoRepository descuentoRepo) {
        this.repo = repo;
        this.lineaRepo = lineaRepo;
        this.descuentoRepo = descuentoRepo;
    }

    public List<Consumo> getAll() { return repo.findAll(); }
    public Optional<Consumo> getById(int id) { return repo.findById(id); }
    public List<Consumo> getAllForDropdown() { return repo.findAllForDropdown(); }
    public List<LineaMovil> getLineasForDropdown() { return lineaRepo.findAllForDropdown(); }
    public List<Descuento> getDescuentosForDropdown() { return descuentoRepo.findAll(); }

    public List<ConsumoVoz> getAllVoz() { return repo.findAllVoz(); }
    public Optional<ConsumoVoz> getVozById(int id) { return repo.findVozById(id); }

    public List<ConsumoSimple> getAllSms() { return repo.findAllSms(); }
    public Optional<ConsumoSimple> getSmsById(int id) { return repo.findSmsById(id); }

    public List<ConsumoSimple> getAllDatos() { return repo.findAllDatos(); }
    public Optional<ConsumoSimple> getDatosById(int id) { return repo.findDatosById(id); }

    public List<ConsumoSimple> getAllRoaming() { return repo.findAllRoaming(); }
    public Optional<ConsumoSimple> getRoamingById(int id) { return repo.findRoamingById(id); }

    public void create(Consumo c) {
        try { repo.insert(c); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void update(Consumo c) {
        try { repo.update(c); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void delete(int id) {
        try { repo.delete(id); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void createVoz(ConsumoVoz v) {
        try { repo.insertVoz(v); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void updateVoz(ConsumoVoz v) {
        try { repo.updateVoz(v); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void deleteVoz(int id) {
        try { repo.deleteVoz(id); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void createSimple(ConsumoSimple s, String tipo) {
        try {
            switch (tipo) {
                case "SMS": repo.insertSms(s); break;
                case "DATOS": repo.insertDatos(s); break;
                case "ROAMING": repo.insertRoaming(s); break;
            }
        } catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void updateSimple(ConsumoSimple s, String tipo) {
        try {
            switch (tipo) {
                case "SMS": repo.updateSms(s); break;
                case "DATOS": repo.updateDatos(s); break;
                case "ROAMING": repo.updateRoaming(s); break;
            }
        } catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void deleteSimple(int id, String tipo) {
        try {
            switch (tipo) {
                case "SMS": repo.deleteSms(id); break;
                case "DATOS": repo.deleteDatos(id); break;
                case "ROAMING": repo.deleteRoaming(id); break;
            }
        } catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public String getCascadeWarning(int id) {
        return "Eliminar este consumo eliminará también todos sus subtipos asociados (Voz, SMS, Datos, Roaming) en cascada.";
    }
}
package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.Promocion;
import com.link360.model.TipoPromocion;
import com.link360.repository.PromocionRepository;
import com.link360.repository.TipoPromocionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromocionService {

    private final PromocionRepository repo;
    private final TipoPromocionRepository tipoRepo;

    public PromocionService(PromocionRepository repo, TipoPromocionRepository tipoRepo) {
        this.repo = repo;
        this.tipoRepo = tipoRepo;
    }

    public List<Promocion> getAll() { return repo.findAll(); }
    public Optional<Promocion> getById(int id) { return repo.findById(id); }
    public List<Promocion> getAllForDropdown() { return repo.findAllForDropdown(); }
    public List<TipoPromocion> getTiposForDropdown() { return tipoRepo.findAll(); }

    public void create(Promocion p) {
        try { repo.insert(p); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void update(Promocion p) {
        try { repo.update(p); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public void delete(int id) {
        try { repo.delete(id); }
        catch (Exception e) { throw new DatabaseException(DatabaseException.translate(e), e); }
    }

    public String getDeleteWarning(int id) {
        if (repo.hasPlanes(id))
            return "Esta promoción está aplicada a planes, servicios o paquetes. Al eliminarla se eliminarán también todas esas asociaciones en cascada.";
        return null;
    }
}
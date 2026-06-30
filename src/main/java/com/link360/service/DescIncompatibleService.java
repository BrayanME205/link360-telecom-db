package com.link360.service;

import com.link360.exception.DatabaseException;
import com.link360.model.DescIncompatible;
import com.link360.repository.DescIncompatibleRepository;
import com.link360.repository.DescuentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DescIncompatibleService {

    private final DescIncompatibleRepository repo;
    private final DescuentoRepository descuentoRepo;

    public DescIncompatibleService(DescIncompatibleRepository repo, DescuentoRepository descuentoRepo) {
        this.repo = repo;
        this.descuentoRepo = descuentoRepo;
    }

    public List<DescIncompatible> getAll() {
        return repo.findAll();
    }

    public Optional<DescIncompatible> getById(int d1, int d2) {
        return repo.findById(d1, d2);
    }

    public List<com.link360.model.Descuento> getDescuentosForDropdown() {
        return descuentoRepo.findAll();
    }

    public void create(DescIncompatible di) {
        try {
            repo.insert(di);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }

    public void delete(int d1, int d2) {
        try {
            repo.delete(d1, d2);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.translate(e), e);
        }
    }
}

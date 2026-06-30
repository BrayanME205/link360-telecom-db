package com.link360.service;

import com.link360.repository.ConsultasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConsultasService {

    private final ConsultasRepository repo;

    public ConsultasService(ConsultasRepository repo) {
        this.repo = repo;
    }

    public List<Map<String, Object>> getResumenMensual() {
        return repo.resumenMensualCliente();
    }

    public List<Map<String, Object>> getConsumoPorLinea() {
        return repo.consumoPorLinea();
    }

    public List<Map<String, Object>> getFacturasPendientes() {
        return repo.facturasPendientes();
    }

    public List<Map<String, Object>> getPlanesConPromociones() {
        return repo.planesActivosConPromociones();
    }

    public List<Map<String, Object>> getTopClientesPuntos() {
        return repo.topClientesPorPuntos();
    }

    public List<Map<String, Object>> getConsumosVozInternacionales() {
        return repo.consumosVozInternacionales();
    }

    public List<Map<String, Object>> getAuditoria() {
        return repo.auditoria();
    }

    public List<Map<String, Object>> getAuditoriaCompleta() {
        return repo.auditoriaCompleta();
    }
}

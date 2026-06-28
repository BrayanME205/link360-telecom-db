package com.link360.exception;

public class DatabaseException extends RuntimeException {

    private final String userMessage;

    public DatabaseException(String userMessage, Throwable cause) {
        super(cause);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public static String translate(Exception e) {
        String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";

        if (msg.contains("porcentaje de descuento supera")) {
            return "El porcentaje de descuento ingresado supera el máximo permitido para ese tipo de promoción.";
        }
        if (msg.contains("foreign key") && (msg.contains("delete") || msg.contains("update"))) {
            return "No se puede eliminar o modificar este registro porque existen registros relacionados que dependen de él. Elimine primero los registros dependientes.";
        }
        if (msg.contains("foreign key") || msg.contains("reference")) {
            return "El valor ingresado no existe en la tabla relacionada. Verifique los datos seleccionados.";
        }
        if (msg.contains("primary key") || msg.contains("duplicate key") || msg.contains("unique")) {
            return "Ya existe un registro con ese identificador. No se permiten valores duplicados.";
        }
        if (msg.contains("ck_cliente_tipocliente")) {
            return "El tipo de cliente debe ser: Bronce, Plata, Oro o Platino.";
        }
        if (msg.contains("ck_lineamovil_tipolinea")) {
            return "El tipo de línea debe ser: Prepago, Postpago o Empresarial.";
        }
        if (msg.contains("ck_lineamovil_tecnologia")) {
            return "La tecnología debe ser: 4G o 5G.";
        }
        if (msg.contains("ck_lineamovil_estadolinea")) {
            return "El estado de línea debe ser: Activa, Suspendida o Cancelada.";
        }
        if (msg.contains("ck_lineamovil_tiposim")) {
            return "El tipo de SIM debe ser: SIM Fisica o eSIM.";
        }
        if (msg.contains("ck_factura_estadopago")) {
            return "El estado de pago debe ser: Pagada, Pendiente o Vencida.";
        }
        if (msg.contains("ck_promocion_fechas")) {
            return "La fecha de fin de la promoción debe ser mayor o igual a la fecha de inicio.";
        }
        if (msg.contains("ck_promocion_porcdescuento")) {
            return "El porcentaje de descuento debe estar entre 0 y 100.";
        }
        if (msg.contains("ck_descuento_horafin")) {
            return "La hora de fin del descuento debe ser mayor a la hora de inicio.";
        }
        if (msg.contains("ck_descuento_numdias")) {
            return "El número de días debe estar entre 1 y 7.";
        }
        if (msg.contains("ck_lineaplan_fechas")) {
            return "La fecha de fin del plan debe ser mayor o igual a la fecha de inicio.";
        }
        if (msg.contains("ck_tipopromocion_porcmax")) {
            return "El porcentaje máximo debe estar entre 0 y 100.";
        }
        if (msg.contains("check constraint") || msg.contains("ck_")) {
            return "El valor ingresado no cumple con las restricciones definidas para este campo.";
        }
        if (msg.contains("cannot insert the value null") || msg.contains("not null")) {
            return "Hay campos obligatorios sin completar. Por favor revise el formulario.";
        }
        if (msg.contains("connection") || msg.contains("timeout")) {
            return "No se pudo conectar con el servidor de base de datos. Intente nuevamente.";
        }
        return "Error en la base de datos: " + e.getMessage();
    }
}

package com.banco.clientes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@EnableR2dbcAuditing // Esto activa el llenado automático de fechas
public class R2dbcConfig {
}
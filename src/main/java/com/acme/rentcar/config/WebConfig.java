package com.acme.rentcar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/// Zentrale Konfiguration für Spring Web MVC.
///
/// Diese Klasse stellt Beans und Einstellungen bereit, die das Verhalten der
/// Web-Schicht (REST-Controller) beeinflussen. Sie implementiert [WebMvcConfigurer],
/// um bei Bedarf Standardeinstellungen von Spring Boot zu erweitern.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /// Erstellt eine Filter-Bean für die Erzeugung von ETags.
    ///
    /// Der [ShallowEtagHeaderFilter] berechnet einen Hash (z.B. MD5) über den
    /// gesamten Inhalt des Response-Bodys und setzt diesen als `ETag`-HTTP-Header.
    ///
    /// Nutzen:
    /// Wenn ein Client diesen ETag bei der nächsten Anfrage im `If-None-Match`-Header mitsendet
    /// und sich der Inhalt nicht geändert hat, antwortet der Server mit `304 Not Modified`
    /// (ohne Body). Dies spart Bandbreite und verbessert die Performance.
    ///
    /// @return Die konfigurierte Filter-Instanz.
    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}

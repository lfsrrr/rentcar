package com.acme.rentcar;

import com.acme.rentcar.config.DevConfig;
import com.acme.rentcar.config.WebConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.acme.rentcar.Banner.TEXT;

/// Klasse mit der `main`-Methode für die Anwendung auf Basis von _Spring Boot_.
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
@SpringBootApplication(proxyBeanMethods = false)
@Import({WebConfig.class, DevConfig.class})
@EnableJpaAuditing
@SuppressWarnings({"ImplicitSubclassInspection", "ClassUnconnectedToPackage"})
public final class Application {
    private Application() {
    }

    /// Hauptprogramm, um den Microservice zu starten.
    ///
    /// @param args Evtl. zusätzliche Argumente für den Start des Microservice
    public static void main(final String... args) {
        new SpringApplicationBuilder(Application.class)
            .banner((_, _, out) -> out.println(TEXT))
            .run(args);
    }
}

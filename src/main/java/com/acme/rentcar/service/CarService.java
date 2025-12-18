package com.acme.rentcar.service;

import jakarta.mail.MessagingException;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import com.acme.rentcar.controller.CarDTO;
import com.acme.rentcar.controller.CarMapper;
import com.acme.rentcar.entity.Car;
import com.acme.rentcar.entity.CarDetails;
import com.acme.rentcar.repository.CarRepository;
import com.acme.rentcar.repository.CarSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper; // <--- WICHTIG
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@SuppressWarnings("preview")
public class CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final CarRepository repository;
    private final CarMapper mapper;

    @Nullable
    private final JavaMailSender mailSender;

    public CarService(final CarRepository repository,
                      final CarMapper mapper,
                      final ObjectProvider<JavaMailSender> mailSenderProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.mailSender = mailSenderProvider.getIfAvailable();
    }

    @Transactional(readOnly = true)
    public Collection<CarDTO> find(final MultiValueMap<String, String> searchParams) {
        final Specification<Car> spec = CarSpecifications.withCriteria(searchParams);
        final var cars = repository.findAll(spec, Sort.by("hersteller").ascending());
        LOGGER.debug("find: {} Autos gefunden", cars.size());
        return mapper.toDTOs(cars);
    }

    @Transactional(readOnly = true)
    public CarDTO findById(final UUID id) {
        final var car = repository.findCarById(id);
        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }
        return mapper.toDTO(car);
    }

    public CarDTO create(final CarDTO dto) {
        final var newCar = new Car(null, dto.hersteller(), dto.modell(), dto.erstzulassung(),
            dto.kennzeichen(), null, List.of());
        final var details = new CarDetails(null, dto.farbe(), dto.sitzplaetze(),
            dto.motor(), Year.of(dto.erstzulassung().getYear()));
        newCar.setDetails(details);
        final var savedCar = repository.save(newCar);

        sendMail(savedCar);

        return mapper.toDTO(savedCar);
    }

    public CarDTO update(final UUID id, final CarDTO dto) {
        final var existingCar = repository.findCarById(id);
        if (existingCar == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }
        existingCar.setHersteller(dto.hersteller());
        existingCar.setModell(dto.modell());
        existingCar.setKennzeichen(dto.kennzeichen());
        existingCar.setErstzulassung(dto.erstzulassung());

        final var details = existingCar.getDetails();
        if (details != null) {
            details.setFarbe(dto.farbe());
            details.setSitzplaetze(dto.sitzplaetze());
            details.setMotor(dto.motor());
            details.setBaujahr(Year.of(dto.erstzulassung().getYear()));
        }
        final var savedCar = repository.save(existingCar);
        return mapper.toDTO(savedCar);
    }

    /// Sendet eine E-Mail asynchron (angepasst an das Beispiel des Professors).
    @Async
    protected void sendMail(final Car car) {
        if (mailSender == null) {
            LOGGER.warn("Kein MailSender verfügbar. Es wird keine Mail gesendet.");
            return;
        }

        // Wir nutzen MimeMessage für HTML-Mails (wie im Skript Seite 1) [cite: 7]
        final var mimeMessage = mailSender.createMimeMessage();

        try {
            // 'true' bedeutet: Es ist eine Multipart-Email (Text + HTML) [cite: 8, 15]
            final var messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom("noreply@rentcar.acme.com");
            messageHelper.setTo("manager@acme.com");
            messageHelper.setSubject("Neues Auto: " + car.getKennzeichen());

            // 1. Einfacher Text (Fallback) [cite: 12]
            final var plainText = "Neues Auto angelegt:\n"
                + "Hersteller: " + car.getHersteller() + "\n"
                + "Modell: " + car.getModell();

            // 2. HTML Text (Schick formatiert) [cite: 13, 14]
            final var htmlText = "<h2>Neues Auto registriert</h2>"
                + "<ul>"
                + "<li><strong>Hersteller:</strong> " + car.getHersteller() + "</li>"
                + "<li><strong>Modell:</strong> <em>" + car.getModell() + "</em></li>"
                + "<li><strong>Kennzeichen:</strong> " + car.getKennzeichen() + "</li>"
                + "</ul>";

            // Beide Versionen setzen
            messageHelper.setText(plainText, htmlText);

            mailSender.send(mimeMessage);
            LOGGER.info("HTML-E-Mail für Auto {} erfolgreich versendet.", car.getId());

        } catch (MessagingException | MailException e) {
            // Fehler fangen, wie im Skript gezeigt [cite: 17]
            LOGGER.error("Fehler beim Senden der E-Mail: {}", e.getMessage());
        }
    }
}

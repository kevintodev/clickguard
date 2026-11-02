package com.clickguard.service;

import com.clickguard.dto.ClickFeaturesRequest;
import com.clickguard.dto.ClickRequest;
import com.clickguard.dto.PredictionResponse;
import com.clickguard.model.Click;
import com.clickguard.repository.ClickRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ClickService {

    private static final Logger log = LoggerFactory.getLogger(ClickService.class);

    private final ClickRepository repository;
    private final WebClient webClient;

    public ClickService(
        ClickRepository repository,
        @Value("${python.service.url}") String pythonUrl) {
        this.repository = repository;
        this.webClient = WebClient.builder().baseUrl(pythonUrl).build();
    }

    public Click processClick(ClickRequest request) {
        ClickFeaturesRequest f = request.getFeatures();
        PredictionResponse prediction;

        try {
            prediction = webClient.post()
                .uri("/score")
                .bodyValue(Map.of(
                    "clicks_per_minute", f.getClicksPerMinute(),
                    "amount_vs_mean", f.getAmountVsMean(),
                    "unique_ips", f.getUniqueIps(),
                    "seconds_since_last_click", f.getSecondsSinceLastClick(),
                    "user_agent_entropy", f.getUserAgentEntropy()
                ))
                .retrieve()
                .bodyToMono(PredictionResponse.class)
                .timeout(Duration.ofSeconds(3))
                .block();
        } catch (RuntimeException ex) {
            log.error("No se pudo obtener la puntuación del detector", ex);
            throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "El detector no está disponible",
                ex
            );
        }

        Click click = new Click();
        click.setIp(request.getIp());
        click.setUserAgent(request.getUserAgent());
        click.setAmount(request.getAmount());
        click.setTimestamp(LocalDateTime.now());
        click.setAnomalyScore(prediction.getScore());
        click.setIsAnomaly(prediction.getIsAnomaly());
        click.setReason(prediction.getReason());

        return repository.save(click);
    }

    public List<Click> getRecent() {
        return repository.findTop100ByOrderByTimestampDesc();
    }
}
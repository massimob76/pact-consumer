package pact.consumer.client;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pact.consumer.dto.ScoreUsername;
import pact.consumer.dto.ScoreUsernameTimestamp;
import pact.consumer.exception.UserNotFoundException;

@ExtendWith({ PactConsumerTestExt.class, SpringExtension.class })
@PactTestFor(port = "8095")
@SpringBootTest
@TestPropertySource("classpath:application.yml")
class ScoreClientTest {

    private static final Map<String, String> HEADERS = singletonMap("Content-Type", APPLICATION_JSON_VALUE);
    private static final ScoreUsernameTimestamp SCORE_USERNAME_TIMESTAMP =
            new ScoreUsernameTimestamp("John", 123, Instant.parse("2007-12-03T10:15:30.00Z"));
    private static final ScoreUsername SCORE_USERNAME = new ScoreUsername("Pete", 124);
    private static final UserNotFoundException PETE_NOT_FOUND = new UserNotFoundException("Could not find user: Pete");

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScoreClient scoreClient;

    @Pact(provider="pact-provider", consumer="pact-consumer")
    RequestResponsePact primeAllScores(PactDslWithProvider builder) throws JsonProcessingException {
        return builder
                .given("John has scored 123")
                .uponReceiving("getAllScores")
                .path("/api/v1/scores")
                .method("GET")
                .willRespondWith()
                .headers(HEADERS)
                .status(HttpStatus.OK.value())
                .body(objectMapper.writeValueAsString(asList(SCORE_USERNAME_TIMESTAMP)))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod="primeAllScores")
    void getAllScores() {
        assertEquals(asList(SCORE_USERNAME_TIMESTAMP), scoreClient.getAllScores());
    }

    @Pact(provider="pact-provider", consumer="pact-consumer")
    RequestResponsePact primeScore(PactDslWithProvider builder) throws JsonProcessingException {
        return builder
                .given("John has scored 123")
                .uponReceiving("getScore")
                .path("/api/v1/scores/John")
                .method("GET")
                .willRespondWith()
                .headers(HEADERS)
                .status(HttpStatus.OK.value())
                .body(objectMapper.writeValueAsString(SCORE_USERNAME_TIMESTAMP))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod="primeScore")
    void getScore() {
        assertEquals(SCORE_USERNAME_TIMESTAMP, scoreClient.getScore("John"));
    }

    @Pact(provider="pact-provider", consumer="pact-consumer")
    RequestResponsePact primeUserNotFound(PactDslWithProvider builder) throws JsonProcessingException {
        return builder
                .given("John has scored 123")
                .uponReceiving("getScore")
                .path("/api/v1/scores/Pete")
                .method("GET")
                .willRespondWith()
                .headers(HEADERS)
                .status(HttpStatus.NOT_FOUND.value())
                .body(objectMapper.writeValueAsString(PETE_NOT_FOUND))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod="primeUserNotFound")
    void getScore_whenNotFound() {
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> scoreClient.getScore("Pete"));
        assertEquals("Could not find user: Pete", userNotFoundException.getMessage());
    }

    @Pact(provider="pact-provider", consumer="pact-consumer")
    RequestResponsePact primeCreateScore(PactDslWithProvider builder) throws JsonProcessingException {
        return builder
                .given("John has scored 123")
                .uponReceiving("createScore")
                .path("/api/v1/scores")
                .method("POST")
                .body(objectMapper.writeValueAsString(SCORE_USERNAME))
                .willRespondWith()
                .headers(HEADERS)
                .status(HttpStatus.ACCEPTED.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod="primeCreateScore")
    void createScore() {
        scoreClient.createScore("Pete", 124);
    }

    @Pact(provider="pact-provider", consumer="pact-consumer")
    RequestResponsePact primeUpdateScore(PactDslWithProvider builder) throws JsonProcessingException {
        return builder
                .given("John has scored 123")
                .uponReceiving("updateScore")
                .path("/api/v1/scores/John")
                .method("PUT")
                .body(objectMapper.writeValueAsString(124))
                .willRespondWith()
                .headers(HEADERS)
                .status(HttpStatus.ACCEPTED.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod="primeUpdateScore")
    void updateScore() {
        scoreClient.updateScore("John", 124);
    }

    @Pact(provider="pact-provider", consumer="pact-consumer")
    RequestResponsePact primeDeleteScore(PactDslWithProvider builder) throws JsonProcessingException {
        return builder
                .given("John has scored 123")
                .uponReceiving("updateScore")
                .path("/api/v1/scores/John")
                .method("DELETE")
                .willRespondWith()
                .headers(HEADERS)
                .status(HttpStatus.ACCEPTED.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod="primeDeleteScore")
    void deleteScore() {
        scoreClient.deleteScore("John");
    }

}
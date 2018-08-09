package pact.consumer.client;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import pact.consumer.dto.ScoreUsername;
import pact.consumer.dto.ScoreUsernameTimestamp;

public class ScoreClient {

  private static final String HOSTNAME = "http://localhost:1234";
  private static final String PATH = "/api/v1/scores";

  @Autowired
  private RestTemplate restTemplate;

  public Collection<ScoreUsernameTimestamp> getAllScores() {
    return restTemplate
        .exchange(HOSTNAME + PATH, HttpMethod.GET, null, new ParameterizedTypeReference<Collection<ScoreUsernameTimestamp>>() {})
        .getBody();
  }

  public ScoreUsernameTimestamp getScore(String name) {
    return restTemplate.getForObject(HOSTNAME + PATH + "/" + name, ScoreUsernameTimestamp.class);
  }

  public void createScore(String name, int score) {
    restTemplate.postForObject(HOSTNAME + PATH, new ScoreUsername(name, score), Void.class);
  }

  public void updateScore(String name, int score) {
    restTemplate.put(HOSTNAME + PATH + "/" + name, score);
  }

  public void deleteScore(String name) {
    restTemplate.delete(HOSTNAME + PATH + "/" + name);
  }


}

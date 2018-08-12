package pact.consumer.client;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pact.consumer.dto.ScoreUsername;
import pact.consumer.dto.ScoreUsernameTimestamp;
import pact.consumer.exception.UserNotFoundException;

@Service
public class ScoreClient {

  private static final String PATH = "/api/v1/scores";

  private String baseUrl;

  @Autowired
  public ScoreClient(@Value("${producer.host}") String hostname) {
    baseUrl = hostname + PATH;
  }

  @Autowired
  private RestTemplate restTemplate;

  public Collection<ScoreUsernameTimestamp> getAllScores() {
    return restTemplate
        .exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Collection<ScoreUsernameTimestamp>>() {})
        .getBody();
  }

  public ScoreUsernameTimestamp getScore(String name) throws UserNotFoundException {
      return restTemplate.getForObject(baseUrl + "/" + name, ScoreUsernameTimestamp.class);
  }

  public void createScore(String name, int score) {
    restTemplate.postForObject(baseUrl, new ScoreUsername(name, score), Void.class);
  }

  public void updateScore(String name, int score) {
    restTemplate.put(baseUrl + "/" + name, score);
  }

  public void deleteScore(String name) {
    restTemplate.delete(baseUrl + "/" + name);
  }


}

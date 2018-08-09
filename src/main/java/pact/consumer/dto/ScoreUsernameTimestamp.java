package pact.consumer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import javax.validation.constraints.NotNull;

public class ScoreUsernameTimestamp {

    @NotNull
    private final String name;

    @NotNull
    private final int score;

    @NotNull
    private final Instant timestamp;

    @JsonCreator
    public ScoreUsernameTimestamp(@JsonProperty("name") String name, @JsonProperty("score") int score, @JsonProperty("timestamp") Instant timestamp) {
        this.name = name;
        this.score = score;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreUsernameTimestamp score = (ScoreUsernameTimestamp) o;

        if (this.score != score.score) return false;
        if (name != null ? !name.equals(score.name) : score.name != null) return false;
        return timestamp != null ? timestamp.equals(score.timestamp) : score.timestamp == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + score;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Score{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", timestamp=" + timestamp +
                '}';
    }
}

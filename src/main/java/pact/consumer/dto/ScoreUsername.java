package pact.consumer.dto;

import java.util.Objects;

public class ScoreUsername {

    private final String name;

    private final int score;

    public ScoreUsername(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScoreUsername that = (ScoreUsername) o;
        return score == that.score &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score);
    }

    @Override
    public String toString() {
        return "ScoreUsername{" +
            "name='" + name + '\'' +
            ", score=" + score +
            '}';
    }
}

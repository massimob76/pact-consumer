package pact.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pact.consumer.client.ScoreClient;

@Component
public class Demo implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

    @Autowired
    private ScoreClient scoreClient;

    @Override
    public void run(String... args) throws Exception {
    }

}

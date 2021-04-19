package co.com.courseupdate.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Component
@Profile("actuator")
public class SpringHostEndpoint implements HealthIndicator {

    private static boolean checkHost(String url) {
        try {
            URL host = new URL(url);
            URLConnection urlConnection = host.openConnection();
            urlConnection.connect();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Health health() {
        String host = "https://spring.io";
        if (checkHost(host)) {
            return Health.up()
                    .withDetail("Host", host)
                    .withDetail("Message", "Host Available")
                    .build();
        } else {
            return Health.down()
                    .withDetail(host, "HostNot Available")
                    .build();
        }

    }
}

package io.enfuse.redisappdemo.apm;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "elastic.apm")
@ConditionalOnProperty(value = "elastic.apm.enabled", havingValue = "true")
public class ElasticConfiguration {

    private static final String SERVER_URL_KEY = "server_url";
    private String serverUrl;

    private static final String SERVICE_NAME_KEY = "service_name";
    private String serviceName;

    private static final String SECRET_TOKEN_KEY = "secret_token";
    private String secretToken;

    private static final String ENVIRONMENT_KEY = "environment";
    private String environment;

    private static final String APPLICATION_PACKAGES_KEY = "application_packages";
    private String applicationPackages;

    private static final String LOG_LEVEL_KEY = "log_level";
    private String logLevel;

    @PostConstruct
    public void init() {
        Map<String, String> apmProps = new HashMap<>(6);
        apmProps.put(SERVER_URL_KEY, serverUrl);
        apmProps.put(SERVICE_NAME_KEY, serviceName);
        apmProps.put(SECRET_TOKEN_KEY, secretToken);
        apmProps.put(ENVIRONMENT_KEY, environment);
        apmProps.put(APPLICATION_PACKAGES_KEY, applicationPackages);
        apmProps.put(LOG_LEVEL_KEY, logLevel);
        System.out.println(apmProps);
        ElasticApmAttacher.attach(apmProps);

    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setApplicationPackages(String applicationPackages) {
        this.applicationPackages = applicationPackages;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
}
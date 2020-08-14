package project.parser.bookye.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigs {

    @Bean
    public RestHighLevelClient client(ESConfig esConfig) {
        int port = Integer.parseInt(esConfig.getPort());
        String host = esConfig.getHost();

        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(host, port, "http"));
        return new RestHighLevelClient(clientBuilder);
    }
}

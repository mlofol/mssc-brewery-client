package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component

public class BlockingRestTemplateCustomizerImpl implements RestTemplateCustomizer {

    private Integer maxTotalConnection;

    private Integer defaultmaxTotalConnections;

    private Integer connectionRequestTimeout;

    private Integer socketTimeout;

    public BlockingRestTemplateCustomizerImpl(
            @Value("${sfg.maxtotalconnections}") Integer maxTotalConnection,
            @Value("${sfg.defaultmaxtotalconnections}") Integer defaultmaxTotalConnections,
            @Value("${sfg.connectionrequesttimeout}") Integer connectionRequestTimeout,
            @Value("${sfg.sockettimeout}") Integer socketTimeout
    ) {
        this.maxTotalConnection=maxTotalConnection;
        this.defaultmaxTotalConnections=defaultmaxTotalConnections;
        this.connectionRequestTimeout=connectionRequestTimeout;
        this.socketTimeout=socketTimeout;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConnection);
        connectionManager.setDefaultMaxPerRoute(defaultmaxTotalConnections);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(clientHttpRequestFactory());
    }
}

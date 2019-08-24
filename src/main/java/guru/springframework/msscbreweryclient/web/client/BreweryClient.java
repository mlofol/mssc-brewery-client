package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {
    private String apihost;
    public final String BEER_PATH_V1 = "/api/v1/beer/";
    public final String CUSTOMER_PATH_V1 = "/api/v1/customer/";
    private final RestTemplate restTemplate;


    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }

    public BeerDto getBeerById(UUID uuid) {
        return restTemplate.getForObject(apihost + BEER_PATH_V1 + uuid, BeerDto.class);
    }

    public URI saveNewBeer(BeerDto beerDto){
        return restTemplate.postForLocation(apihost + BEER_PATH_V1 ,beerDto);
    }

    public void updateBeer (UUID id, BeerDto beerDto){
        restTemplate.put(apihost+BEER_PATH_V1+id.toString(),beerDto);
    }

    public void deleteBeerById(UUID id){
        restTemplate.delete(apihost+BEER_PATH_V1+"/"+id);
    }

    public CustomerDto getCustomerById(UUID randomUUID) {
        return restTemplate.getForObject(apihost+CUSTOMER_PATH_V1+randomUUID,CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto) {
        return restTemplate.postForLocation(apihost+CUSTOMER_PATH_V1,customerDto);
    }

    public void updateCustomer(CustomerDto customerDto) {
        restTemplate.put(apihost+CUSTOMER_PATH_V1+customerDto.getId().toString(),customerDto);
    }

    public void deleteCustomerById(UUID randomUUID) {
        restTemplate.delete(apihost+CUSTOMER_PATH_V1+randomUUID);
    }
}

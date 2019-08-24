package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient breweryClient;

    @Test
    void getBeerById() {
        //given

        //when
        BeerDto dto = breweryClient.getBeerById(UUID.randomUUID());
        //then
        assertNotNull(dto);

    }

    @Test
    void testSaveNewBeer() {
        //Given
        BeerDto beerDto = BeerDto.builder().beerName("New Beer").id(UUID.randomUUID()).build();
//when
        URI uri = breweryClient.saveNewBeer(beerDto);
        System.out.println(uri);

    }

    @Test
    void testUpdate() {
        //Given
        BeerDto beerDto = BeerDto.builder().beerName("New Beer").id(UUID.randomUUID()).build();
        //when
        breweryClient.updateBeer(beerDto.getId(), beerDto);
    }
    @Test
    void testDelete (){
        breweryClient.deleteBeerById(UUID.randomUUID());
    }

    @Test
    void getCustomerById(){
        CustomerDto customerDto = breweryClient.getCustomerById(UUID.randomUUID());

        assertNotNull(customerDto);
    }

    @Test
    void postCustomer(){
        CustomerDto customerDto=CustomerDto.builder().id(UUID.randomUUID()).name("Carmen").build();
        URI uri= breweryClient.saveNewCustomer(customerDto);
        assertNotNull(uri);
    }

    @Test
    void updateCustomer(){
        CustomerDto customerDto=CustomerDto.builder().id(UUID.randomUUID()).name("Carmen").build();
        breweryClient.updateCustomer(customerDto);
    }

    @Test
    void deleteCustomer(){
        breweryClient.deleteCustomerById(UUID.randomUUID());
    }
}
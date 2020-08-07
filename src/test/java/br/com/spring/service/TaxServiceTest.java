package br.com.spring.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.spring.service.TaxService;

@RunWith(SpringRunner.class)
public class TaxServiceTest {

    private TaxService service = new TaxService();
    
    @Test
    public void bracket() {
        final String taxBracket = service.getBracket(500);
        assertThat(taxBracket, is(equalTo("LOW")));
    }
    
    @Test
    public void allBracket() {
        final List<String> allBrackets = service.allBracket();
        assertThat(allBrackets, is(not(empty())));
        assertThat(allBrackets, contains("LOW","MEDIUM", "HEIGHT"));
    }
    
}

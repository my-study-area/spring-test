package br.com.spring.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import br.com.spring.service.Calculadora;

public class CalculadoraTest {

    @Test
    public void deveRetornar5QuandoSomar4E1() {
        assertThat(Calculadora.soma(2, 3), is(5));
    }
    
    @Test
    public void deveRetornar6QuandoSomar4E2() {
        assertThat(Calculadora.soma(4, 2), is(6));
    }
    
    @Test
    public void testar() {
        assertThat(Calculadora.soma(4, 2), allOf(is(6), not(0)));
    }

}

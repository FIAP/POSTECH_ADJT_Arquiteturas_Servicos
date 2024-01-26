package br.com.fiap.mscidade;

import br.com.fiap.mscidade.controller.CidadeController;
import br.com.fiap.mscidade.model.Cidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class CidadeControllerTest {

    private CidadeController cidadeController;
    private List<Cidade> cidades;

    @BeforeEach
    void setUp() {
        cidades = Arrays.asList(
                new Cidade(1, "São Paulo", "Brasil", "A maior cidade da América do Sul."),
                new Cidade(2, "Nova Iorque", "EUA", "Conhecida como a cidade que nunca dorme.")
        );

        cidadeController = Mockito.spy(new CidadeController());

        Mockito.doReturn(cidades).when(cidadeController).listarCidades();
    }

    @Test
    void deveRetornarCidadeQuandoIdExistente() {
        Cidade cidade = cidadeController.getCidade(1);
        assertNotNull(cidade);
        assertEquals(1, cidade.getId());
    }

    @Test
    void deveRetornarNullQuandoIdInexistente() {
        Cidade cidade = cidadeController.getCidade(99); // supondo que 99 não é um ID válido
        assertNull(cidade);
    }
}

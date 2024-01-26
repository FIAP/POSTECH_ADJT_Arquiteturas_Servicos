package br.com.fiap.mscidade.controller;

import br.com.fiap.mscidade.model.Cidade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CidadeController {

    private List<Cidade> cidades = Arrays.asList(
            new Cidade(1, "São Paulo", "Brasil",
                    "É uma cidade muito grande e com ótimas padarias"),

            new Cidade(2, "Florianópolis", "Brasil",
                    "Tem lindas praias"),

            new Cidade(3, "Porto Alegre", "Brasil",
                    "É uma cidade muito fria no inverno")
    );

    @GetMapping("/cidades")
    public List<Cidade>listarCidades() {
        return cidades;
    }


    @GetMapping("/cidade/{id}")
    public Cidade getCidade(@PathVariable int id) {
        return cidades.stream().filter(cidade -> cidade.getId() == id)
                .findFirst()
                .orElse(null);
    }

}

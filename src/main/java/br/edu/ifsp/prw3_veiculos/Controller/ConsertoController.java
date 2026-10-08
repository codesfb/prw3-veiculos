package br.edu.ifsp.prw3_veiculos.Controller;

import br.edu.ifsp.prw3_veiculos.dto.DadosListagemConserto;
import br.edu.ifsp.prw3_veiculos.dto.DadosListagemFull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consertos")
public class ConsertoController {
//    @GetMapping("full")
//    public Page<DadosListagemFull> listar(Pageable pageable) {
//        return repository.findAll(pageable).map(DadosListagemFull::new);
//    }

//    @GetMapping
//    public List<DadosListagemConserto> listar() {
//        return repository.findAll().stream().map(DadosListagemConserto::new).toList();
//    }
}

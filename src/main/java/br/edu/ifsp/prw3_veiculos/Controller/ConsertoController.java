package br.edu.ifsp.prw3_veiculos.Controller;

import br.edu.ifsp.prw3_veiculos.model.Conserto;
import br.edu.ifsp.prw3_veiculos.repository.ConsertoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consertos")
public class ConsertoController {
    private final ConsertoRepository consertoRepository;

    public ConsertoController(ConsertoRepository consertoRepository) {
        this.consertoRepository = consertoRepository;
    }

    @PostMapping
    public ResponseEntity<Conserto> cadastrar(@RequestBody Conserto conserto) {
        conserto.setId(null);
        Conserto salvo = consertoRepository.save(conserto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
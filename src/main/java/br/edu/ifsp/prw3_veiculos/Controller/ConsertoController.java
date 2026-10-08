package br.edu.ifsp.prw3_veiculos.Controller;

import br.edu.ifsp.prw3_veiculos.dto.DadosListagemConserto;
import br.edu.ifsp.prw3_veiculos.dto.DadosListagemFull;
import br.edu.ifsp.prw3_veiculos.model.Conserto;
import br.edu.ifsp.prw3_veiculos.repository.ConsertoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consertos")
public class ConsertoController {
    private final ConsertoRepository consertoRepository;

    public ConsertoController(ConsertoRepository consertoRepository) {
        this.consertoRepository = consertoRepository;
    }

    @GetMapping
    public List<DadosListagemConserto> listar() {
        return consertoRepository.findAllByAtivoTrue().stream()
                .map(DadosListagemConserto::new)
                .toList();
    }

    @GetMapping("/full")
    public Page<DadosListagemFull> listarCompleto(Pageable pageable) {
        return consertoRepository.findAll(pageable)
                .map(DadosListagemFull::new);
    }

    @PostMapping
    public ResponseEntity<Conserto> cadastrar(@Valid @RequestBody Conserto conserto) {
        conserto.setId(null);
        conserto.setAtivo(true);
        Conserto salvo = consertoRepository.save(conserto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemConserto> getConsertoById(@PathVariable Long id) {
        Optional<Conserto> consertoOptional = consertoRepository.findById(id);

        if (consertoOptional.isPresent()) {
            Conserto conserto = consertoOptional.get();
            return ResponseEntity.ok(new DadosListagemConserto(conserto));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}

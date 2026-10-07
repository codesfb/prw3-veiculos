package br.edu.ifsp.prw3_veiculos.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("medicos")
public class VeiculoController {
    @PostMapping
    public void cadastrar(){
    }
}

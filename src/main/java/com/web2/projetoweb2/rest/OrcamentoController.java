package com.web2.projetoweb2.rest;

import com.web2.projetoweb2.dto.OrcamentoDTO;
import com.web2.projetoweb2.entity.Orcamento;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.services.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @PostMapping
    public ResponseEntity<Orcamento> criarOrcamento(@RequestBody OrcamentoDTO orcamentoDTO, @AuthenticationPrincipal Usuario funcionarioLogado) {
        Orcamento novoOrcamento = orcamentoService.criarOrcamento(orcamentoDTO);
        return new ResponseEntity<>(novoOrcamento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrcamentoDTO>> listarOrcamentos() {
        List<Orcamento> orcamentos = orcamentoService.listarOrcamentos();
        List<OrcamentoDTO> orcamentosDto = orcamentos.stream()
                .map(OrcamentoDTO::new) // converte cada orcamento em orcamento DTO
                .toList();
        return new ResponseEntity<>(orcamentosDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoDTO> obterOrcamentoPorId(@PathVariable Integer id) {
        return orcamentoService.obterOrcamentoPorId(id)
                .map(orcamento -> new ResponseEntity<>(new OrcamentoDTO(orcamento), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<OrcamentoDTO> obterOrcamentoPorSolicatacao(@PathVariable Integer solicitacaoId) {
        return orcamentoService.obterOrcamentoPorSolicatacao(solicitacaoId)
                .map(orcamento -> new ResponseEntity<>(new OrcamentoDTO(orcamento), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> atualizarOrcamento(@PathVariable Integer id, @RequestBody Orcamento orcamentoAtualizado) {
        try {
            Orcamento orcamento = orcamentoService.atualizarOrcamento(id, orcamentoAtualizado);
            return new ResponseEntity<>(orcamento, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/aprovar/{id}")
    public ResponseEntity<OrcamentoDTO> aprovarOrcamento(@PathVariable Integer id) {
        try {
            Orcamento orcamento = orcamentoService.aprovarOrcamento(id);
            return new ResponseEntity<>(new OrcamentoDTO(orcamento), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/rejeitar/{id}")
    public ResponseEntity<OrcamentoDTO> rejeitarOrcamento(@PathVariable Integer id,  @RequestBody String motivoRejeicao) {
        try {
            Orcamento orcamento = orcamentoService.rejeitarOrcamento(id, motivoRejeicao);
            return new ResponseEntity<>(new OrcamentoDTO(orcamento), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOrcamento(@PathVariable Integer id) {
        try {
            orcamentoService.excluirOrcamento(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

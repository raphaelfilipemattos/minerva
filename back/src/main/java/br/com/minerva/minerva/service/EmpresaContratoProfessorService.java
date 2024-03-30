package br.com.minerva.minerva.service;

import br.com.minerva.minerva.domain.EmpresaContratoProfessor;
import br.com.minerva.minerva.domain.PropostaContratoProfessor;
import br.com.minerva.minerva.domain.StatusPagamento;
import br.com.minerva.minerva.domain.TipoRecebimentoContrato;
import br.com.minerva.minerva.model.CursoDTO;
import br.com.minerva.minerva.repos.CursoRepository;
import br.com.minerva.minerva.repos.EmpresaContratoProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaContratoProfessorService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EmpresaContratoProfessorRepository empresaContratoProfessorRepository;


    public void criaContratoEmpresa(PropostaContratoProfessor propostaProfessor, CursoDTO cursoDTO){
        var empresaContrato = new EmpresaContratoProfessor();

        empresaContrato.setProposta(propostaProfessor);
        empresaContrato.setProfessor(propostaProfessor.getProfessor());
        empresaContrato.setEmpresa(propostaProfessor.getEmpresa());
        empresaContrato.setStatus_pagamento(StatusPagamento.NAO_PAGO.getValor());
        var valorpagamento = propostaProfessor.getValor();
        if (propostaProfessor.getTipo_recebimento().equals( TipoRecebimentoContrato.PORCENTAGEM.getValor()) ){
            valorpagamento = (float) (cursoDTO.getValor().floatValue() * propostaProfessor.getValor() / 100.00);
        }

        empresaContrato.setValor_pagamento(valorpagamento);
        empresaContrato.setTipo_pagamento(propostaProfessor.getTipo_recebimento());
        empresaContrato.setValorpago(0);

        empresaContrato.setCurso( this.cursoRepository.findById(cursoDTO.getIdcurso()).get() );
        this.empresaContratoProfessorRepository.save(empresaContrato);

    }
}

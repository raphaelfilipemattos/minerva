package br.com.minerva.minerva.service;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.domain.Pagamento;
import br.com.minerva.minerva.domain.Pagamentocurso;
import br.com.minerva.minerva.domain.Perfil;
import br.com.minerva.minerva.model.*;
import br.com.minerva.minerva.moodle.WebServiceMoodle;
import br.com.minerva.minerva.repos.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.ZoneOffset.*;

@Service
public class PagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    private InscricaoService inscricaoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private PagamentoCursoRepository pagamentoCursoRepository;
    @Autowired
    private Ambiente ambiente;
    @Autowired
    private SalaService salaService;



    public List<FormaPagamentoDTO> getFormasPagamentos(){
         return this.formaPagamentoRepository.findAll().stream().map(FormaPagamentoDTO::new).toList();
    }

    private Boolean processaPagamento(PagamentoCursoDTO pagamentoCursoDTO){
        return true;
    }

    @Transactional
    private void fazInscricaoCursos(PagamentoCursoInscricaoDTO pagamentoCursoInscricaoDTO){
            var idusuario = pagamentoCursoInscricaoDTO.getPagamento().getIdusuario().getIdusuario();
            var ws = this.ambiente.getWebServiceMoodle();

            for(var pagamentocurso : pagamentoCursoInscricaoDTO.getPagamentocurso()) {
                var inscricaoDTO = new InscricaoDTO();
                inscricaoDTO.setIdusuario(  idusuario);
                inscricaoDTO.setIdcurso(  pagamentocurso.getCurso().getIdcurso() );
                inscricaoDTO.setIdpagamentocurso(pagamentocurso.getIdpagamentocurso());
                inscricaoDTO.setData(pagamentocurso.getPagamento().getData());
                inscricaoDTO.setHora(pagamentocurso.getPagamento().getHora());

                this.inscricaoService.create(inscricaoDTO);
                this.salaService.definePapelSala(idusuario,Perfil.ALUNO,pagamentocurso.getCurso().getIdcurso(),ambiente.getEmpresaAtual().getIdempresa(), ws );
            }
    }

    @Transactional
    private PagamentoCursoInscricaoDTO criaPagamentoInscriao(PagamentoCursoDTO pagamentoCursoDTO){
        var pagamento = new Pagamento();
        var pagamentoCursoInscricaoDTO = new PagamentoCursoInscricaoDTO();
        pagamentoCursoDTO.setIdusuario(Ambiente.getUsuarioLogado().getIdusuario());

        pagamento.setIdformaPagamento( formaPagamentoRepository.getById(pagamentoCursoDTO.getIdFormaPagamento()));
        pagamento.setIdentificador(UUID.randomUUID().toString().substring(0,10));
        pagamento.setIdusuario( this.usuarioRepository.getById(pagamentoCursoDTO.getIdusuario()) );
        pagamento.setValor(pagamentoCursoDTO.getValorTotal());
        pagamento.setValorPago(pagamentoCursoDTO.getValorTotal());
        pagamento.setValor_desconto(0.00); //Rever
        pagamento.setData(LocalDate.now());
        pagamento.setHora(LocalTime.now() );

        var  pagamentoSalvo = this.pagamentoRepository.save(pagamento);

        pagamentoCursoInscricaoDTO.setPagamento(pagamentoSalvo);

        for( var cursoDTO: pagamentoCursoDTO.getCursos() ){
            var pagamentoCurso= new Pagamentocurso();
            pagamentoCurso.setValor(cursoDTO.getValor());
            pagamentoCurso.setCurso( this.cursoRepository.getById(cursoDTO.getIdcurso()) );
            pagamentoCurso.setPagamento(pagamentoSalvo);
            var pagamentos = pagamentoCursoInscricaoDTO.getPagamentocurso();
            if (pagamentos == null){
                pagamentos = new ArrayList<Pagamentocurso>();
            }
            pagamentos.add(this.pagamentoCursoRepository.save(pagamentoCurso) );
            pagamentoCursoInscricaoDTO.setPagamentocurso(pagamentos);
        }
        pagamentoCursoInscricaoDTO.setCursos( pagamentoCursoDTO.getCursos() );

        return pagamentoCursoInscricaoDTO;

    }


    public Boolean fazPagamentoCurso(PagamentoCursoDTO pagamentoCursoDTO){
        try {
            if (this.processaPagamento(pagamentoCursoDTO)) {
                this.fazInscricaoCursos( this.criaPagamentoInscriao(pagamentoCursoDTO) );
                return true;
            }
            return false;
        }catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao processar o pagamento: "+e.getMessage());
        }
    }
}

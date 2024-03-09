package br.com.minerva.minerva.service;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.domain.Empresa;
import br.com.minerva.minerva.model.EmpresaDTO;
import br.com.minerva.minerva.model.EmpresaNovaDTO;
import br.com.minerva.minerva.repos.EmpresaRepository;
import br.com.minerva.minerva.repos.PacoteRepository;
import br.com.minerva.minerva.util.NotFoundException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import br.com.minerva.minerva.util.Utils;
import jakarta.validation.Valid;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final PacoteRepository pacoteRepository;

    private final CriaAmbienteService criaAmbienteService;

    @Autowired
    private UsuarioService usuarioService;


    private void trataLogo(String logo_base64, UUID nomeLogo){
        Utils.ImageToBase64(logo_base64,Ambiente.localLogoEmpresa(),nomeLogo);
    }

    public EmpresaService(final EmpresaRepository empresaRepository,
                          final PacoteRepository pacoteRepository, CriaAmbienteService criaAmbienteService) {
        this.empresaRepository = empresaRepository;
        this.pacoteRepository = pacoteRepository;
        this.criaAmbienteService = criaAmbienteService;
    }

    public List<EmpresaDTO> findAll() {
        final List<Empresa> empresas = empresaRepository.findAll(Sort.by("idempresa"));
        return empresas.stream()
                .map(empresa -> mapToDTO(empresa, new EmpresaDTO()))
                .toList();
    }

    public EmpresaDTO get(final UUID idempresa) {
        return empresaRepository.findById(idempresa)
                .map(empresa -> mapToDTO(empresa, new EmpresaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(@Valid final EmpresaNovaDTO empresaDTO) {
        final Empresa empresa = new Empresa();
        empresaNovaToEntity(empresaDTO, empresa);
        var ambiente = UUID.randomUUID().toString().replace("-","").substring(1,20);
        var logo = UUID.randomUUID();
        empresa.setNomeAmbiente(ambiente);
        empresa.setLogo(logo);
        this.trataLogo(empresaDTO.getLogo(), logo);
        var idempresa = empresaRepository.save(empresa).getIdempresa();

        //Cria usuario
        var usuario = empresaDTO.getUsuarioMaster();
        usuario.setIdempresa(idempresa);
        this.usuarioService.create(usuario);
        return idempresa;
    }

    public void update(final UUID idempresa, final EmpresaDTO empresaDTO) {
        final Empresa empresa = empresaRepository.findById(idempresa)
                .orElseThrow(NotFoundException::new);
        mapToEntity(empresaDTO, empresa);
        this.trataLogo(empresaDTO.getLogo(), empresa.getLogo());
        empresaRepository.save(empresa);
    }

    public void delete(final UUID idempresa) {
        empresaRepository.deleteById(idempresa);
    }

    private EmpresaDTO mapToDTO(final Empresa empresa, final EmpresaDTO empresaDTO) {
        empresaDTO.setIdempresa(empresa.getIdempresa());
        empresaDTO.setRazaoSocial(empresa.getRazaoSocial());
        empresaDTO.setNomeFantasia(empresa.getNomeFantasia());
        empresaDTO.setCnpjCpf(empresa.getCnpjCpf());
        empresaDTO.setEmailFinanceiro(empresa.getEmailFinanceiro());
        empresaDTO.setEmailEducacional(empresa.getEmailEducacional());
        //empresaDTO.setLogo(empresa.getLogo());
        //empresaDTO.setIdpacote(empresa.getIdpacote() == null ? null : empresa.getIdpacote().getIdpacote());
        return empresaDTO;
    }

    private Empresa mapToEntity(final EmpresaDTO empresaDTO, final Empresa empresa) {
        empresa.setRazaoSocial(empresaDTO.getRazaoSocial());
        empresa.setNomeFantasia(empresaDTO.getNomeFantasia());
        empresa.setCnpjCpf(empresaDTO.getCnpjCpf());
        empresa.setEmailFinanceiro(empresaDTO.getEmailFinanceiro());
        empresa.setEmailEducacional(empresaDTO.getEmailEducacional());
        //empresa.setLogo(empresaDTO.getLogo());
        //empresa.setSeq(empresaDTO.getSeq());
        //empresa.setNomeAmbiente(empresaDTO.getNomeAmbiente());
       // final Pacote idpacote = empresaDTO.getIdpacote() == null ? null : pacoteRepository.findById(empresaDTO.getIdpacote())
       //         .orElseThrow(() -> new NotFoundException("idpacote not found"));
       // empresa.setIdpacote(idpacote);
        empresa.setIdpacote(empresaDTO.getIdpacote());
        empresa.setDominio(empresaDTO.getDominio());
        empresa.setDominioava(empresaDTO.getDominioava());
        return empresa;
    }

    private Empresa empresaNovaToEntity(final EmpresaNovaDTO empresaDTO, final Empresa empresa) {
        empresa.setRazaoSocial(empresaDTO.getRazaoSocial());
        empresa.setNomeFantasia(empresaDTO.getNomeFantasia());
        empresa.setCnpjCpf(empresaDTO.getCnpjCpf());
        empresa.setEmailFinanceiro(empresaDTO.getEmailFinanceiro());
        empresa.setEmailEducacional(empresaDTO.getEmailEducacional());
        //empresa.setLogo(empresaDTO.getLogo());
        empresa.setIdpacote(empresaDTO.getIdpacote());
        empresa.setDominio(empresaDTO.getDominio());
        empresa.setDominioava(empresaDTO.getDominioava());
        return empresa;
    }

    public boolean cnpjCpfExists(final String cnpjCpf) {
        return empresaRepository.existsByCnpjCpfIgnoreCase(cnpjCpf);
    }

}

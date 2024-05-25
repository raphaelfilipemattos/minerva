package br.com.minerva.minerva.service;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.domain.Empresa;
import br.com.minerva.minerva.domain.Perfil;
import br.com.minerva.minerva.domain.PerfilUsuarioEmpresa;
import br.com.minerva.minerva.domain.Usuario;
import br.com.minerva.minerva.model.LoginAvaDTO;
import br.com.minerva.minerva.model.PerfilDTO;
import br.com.minerva.minerva.model.UsuarioDTO;
import br.com.minerva.minerva.model.UsuarioNovoDTO;
import br.com.minerva.minerva.moodle.WebServiceMoodle;
import br.com.minerva.minerva.moodle.models.UsuarioMoodleModel;
import br.com.minerva.minerva.repos.EmpresaRepository;
import br.com.minerva.minerva.repos.PerfilRepository;
import br.com.minerva.minerva.repos.PerfilUsuarioEmpresaRepository;
import br.com.minerva.minerva.repos.UsuarioRepository;
import br.com.minerva.minerva.util.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private WebServiceMoodle webServiceMoodle;
    @Autowired
    private PerfilUsuarioEmpresaRepository perfilUsuarioEmpresaRepository;

    @Autowired
    private PerfilRepository perfil;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private Ambiente ambiente;

    public UsuarioService(final UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("idusuario"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public List<UsuarioDTO> getUsuariosEmpresa(UUID idempresa, UUID idperfil) {
        final List<Usuario> usuarios ;
        if(idperfil == null) {
            usuarios = usuarioRepository.getUsuariosEmpresa(idempresa);
        }else{
            usuarios = usuarioRepository.getUsuariosEmpresaByPerfil(idempresa,idperfil);
        }
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final UUID idusuario) {
        return usuarioRepository.findById(idusuario)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(@Valid final UsuarioNovoDTO usuarioNovoDTO) {
        final Usuario usuario = new Usuario();
        usuarioNovoToEntity(usuarioNovoDTO, usuario);
        usuario.setSenha(AutentificacaoService.criptografaSenha(usuarioNovoDTO.getSenha()));
        var idusuario =  usuarioRepository.save(usuario).getIdusuario();
        if (! idusuario.toString().isEmpty()){
            var empresa = this.ambiente.getEmpresaAtual();
            var usuarioDTO = new UsuarioDTO();

            usuarioDTO.setIdusuario(idusuario);
            usuarioDTO.setIdempresa(empresa.getIdempresa());
            usuarioDTO.setCpf(usuarioNovoDTO.getCpf());
            usuarioDTO.setEmail(usuarioNovoDTO.getEmail());
            usuarioDTO.setNome(usuarioNovoDTO.getNome());
            sincronizaMoodle(empresa,usuarioDTO);

        }
        return idusuario;
    }

    public void update(final UUID idusuario, final UUID IdEmpresa, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(idusuario)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
        var empresa = this.empresaRepository.findById(IdEmpresa).get();
        sincronizaMoodle(empresa, usuarioDTO);

    }

    public void sincronizaMoodle(Empresa empresa, UsuarioDTO usuarioDTO ){
        this.sincronizaMoodlePerfil(empresa, usuarioDTO,Perfil.ALUNO);
    }
    public void sincronizaMoodlePerfil(Empresa empresa, UsuarioDTO usuarioDTO,UUID idperfil ){
        this.webServiceMoodle.setEmpresa(empresa);
        var usuarioMoodle = this.webServiceMoodle.getUsuarioByUsername(usuarioDTO.getIdusuario().toString());
        if (usuarioMoodle == null){
            usuarioMoodle = new UsuarioMoodleModel();
            usuarioMoodle.setIdnumber(usuarioDTO.getIdusuario().toString());
            usuarioMoodle.setUsername(usuarioDTO.getIdusuario().toString());
        }

        usuarioMoodle.setEmail(usuarioDTO.getEmail());
        usuarioMoodle.setFirstname(usuarioDTO.getNome());
        usuarioMoodle.setLastname(usuarioDTO.getNome());
        usuarioMoodle.setPassword(LoginAvaDTO.geraSenhaAva(usuarioDTO)  );
        usuarioMoodle = this.webServiceMoodle.cria_atualiza_Usuario(usuarioMoodle);
        this.addPerfilUsuarioEmpresa(empresa,usuarioDTO,usuarioMoodle, idperfil);

    }

    private void addPerfilUsuarioEmpresa(Empresa empresa, UsuarioDTO usuarioDTO,UsuarioMoodleModel usuarioMoodleModel, UUID idperfil){
        var perfilusuario =  this.perfilUsuarioEmpresaRepository.findByIdusuarioAndIdempresaAndIdperfil(empresa.getIdempresa(), usuarioDTO.getIdusuario(),idperfil);
        if (perfilusuario == null){
            perfilusuario = new PerfilUsuarioEmpresa();
        }
        var usuario = this.usuarioRepository.findById(usuarioDTO.getIdusuario()).get();
        perfilusuario.setEmpresa(empresa);
        perfilusuario.setIdusuarioMoodle(Integer.toUnsignedLong(usuarioMoodleModel.getId()));
        perfilusuario.setUsuario(usuario);
        perfilusuario.setPerfil( this.perfil.findById(idperfil).get() );
        this.perfilUsuarioEmpresaRepository.save(perfilusuario);
    }


    public void delete(final UUID idusuario) {
        usuarioRepository.deleteById(idusuario);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setIdusuario(usuario.getIdusuario());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setSeq(usuario.getSeq());
        usuarioDTO.setDataCadastro(usuario.getDataCadastro());
        usuarioDTO.setLastChange(usuario.getLastChange());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setSeq(usuarioDTO.getSeq());
        usuario.setDataCadastro(usuarioDTO.getDataCadastro());
        usuario.setLastChange(usuarioDTO.getLastChange());
        return usuario;
    }

    private Usuario usuarioNovoToEntity(final UsuarioNovoDTO usuarioDTO, final Usuario usuario) {
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());

        return usuario;
    }
    public List<PerfilDTO> getPerilEmpresa(UUID idusuario, UUID idEmpresa){
        return this.perfilUsuarioEmpresaRepository.findByIdusuarioAndIdempresa(idusuario, idEmpresa).stream().map( PerfilDTO::new ).toList();
        }



    public boolean emailExists(final String email) {
        return usuarioRepository.existsByEmailIgnoreCase(email);
    }

    public boolean cpfExists(final String cpf) {
        return usuarioRepository.existsByCpfIgnoreCase(cpf);
    }

}

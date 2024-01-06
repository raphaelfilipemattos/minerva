package br.com.minerva.minerva.service;

import br.com.minerva.minerva.domain.Usuario;
import br.com.minerva.minerva.model.UsuarioDTO;
import br.com.minerva.minerva.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutentificacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public static String criptografaSenha(String senha){
        return Sha512DigestUtils.shaHex(senha);
    }

    @Override
    public UsuarioDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepository.getUsuarioByLogin(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário ou senha incorretos ");
        }
        var usuarioLogado = new UsuarioDTO();
        usuarioLogado.setIdusuario(usuario.getIdusuario());
        usuarioLogado.setNome(usuario.getNome());
        usuarioLogado.setCpf(usuario.getCpf());
        usuarioLogado.setEmail(usuario.getEmail());
        usuarioLogado.setDataCadastro(usuario.getDataCadastro());
        usuarioLogado.setSenha(usuario.getSenha());
        return usuarioLogado;
    }
}

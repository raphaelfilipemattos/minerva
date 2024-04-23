import TokenService from "@/services/TokenService";
import ItemMenu from "./ItemMenu";

export default function MenuAdm(){
    const tokenService = new TokenService();
    
    if (! tokenService.isAdm()) return <></>;
    
    return (
        <ul className="navbar-nav me-4 mb-2 mb-lg-0">
            <li className="nav-item dropdown">
                <a className="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Administração
                </a>
                <ul className="dropdown-menu">
                    <ItemMenu url="/adm/cursos" className="dropdown-item" > Cursos</ItemMenu>
                    <ItemMenu url="/adm/usuarios" className="dropdown-item" > Usuários</ItemMenu>
                    <ItemMenu url="/adm/alunos" className="dropdown-item" > Alunos</ItemMenu>
                    <ItemMenu url="/adm/professores" className="dropdown-item" > Professores</ItemMenu>
                    <li> <hr className="divider" /></li>
                    <ItemMenu url="/adm/propostascurso" className="dropdown-item" > Propostas de cursos </ItemMenu>
                    <ItemMenu url="/adm/pagamentos" className="dropdown-item" > Pagamentos</ItemMenu>
                    <ItemMenu url="/adm/despesas" className="dropdown-item" > Despesas</ItemMenu>
                    <li> <hr className="divider" /></li>
                    <ItemMenu url="/adm/configuracoes" className="dropdown-item" > Configurações</ItemMenu>
                    <ItemMenu url="/adm/relatorios" className="dropdown-item" > Relatórios</ItemMenu>
                </ul>
            </li>

        </ul>
    )
}
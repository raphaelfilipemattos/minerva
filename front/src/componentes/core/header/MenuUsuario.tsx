import ItemMenu from "./ItemMenu";

export default function MenuUsuario(){

    return (        
        <ul className="navbar-nav me-4 mb-2 mb-lg-0">            
            <li className="nav-item dropdown">
                <a className="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Menu do usuário
                </a>
                <ul className="dropdown-menu">                
                    <ItemMenu url="#" className="dropdown-item" >Meus Cursos</ItemMenu>
                    <li> <hr className="divider" /></li>
                    <ItemMenu url="#" className="dropdown-item" >Finaneiro</ItemMenu>                
                    <ItemMenu url="#" className="dropdown-item" >Perfil</ItemMenu>                
                    <li> <hr className="divider" /></li>
                    <ItemMenu url="/logoff" className="dropdown-item" >Sair </ItemMenu>
                </ul>
            </li>
        </ul>
       

    );

}
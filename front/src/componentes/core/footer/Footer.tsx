import style from "./footer.module.css"
export default function Footer(){
   return (
    <footer className={style.footer}>
        <span className={style.texto}>Desenvolvido por Raphael Mattos</span> 
    </footer>
   );
}
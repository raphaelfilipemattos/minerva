import style from "./hero.module.css";
export default function Hero(){

    return (        
        <section id="hero" className={style.hero+ " d-flex justify-content-center align-items-center "} >
          <div className="container position-relative" data-aos="zoom-in-up" data-aos-duration="3000">
            <h1>Learning Today,<br/>Leading Tomorrow</h1>
            <h2>We are team of talented designers making websites with Bootstrap</h2>
            <a href="#cursos" className={style.btn_get_started}>Nossos cursos</a>
          </div>
        </section>
    );


}
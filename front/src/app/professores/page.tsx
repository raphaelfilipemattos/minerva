import Professores from "@/componentes/professores/Professores";

export default function ProfessorPage(){
    return (
        <section>
            <div className="row">
                <p>
                Gostaria de ser nosso professor? <a href="/propostaprofessor">Clique aqui e saiba como</a>
                </p>
            </div>
            <Professores/>
        </section>
    )
}
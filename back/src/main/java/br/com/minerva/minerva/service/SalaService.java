package br.com.minerva.minerva.service;

import br.com.minerva.minerva.domain.Curso;
import br.com.minerva.minerva.domain.CursoSala;
import br.com.minerva.minerva.model.CursoDTO;
import br.com.minerva.minerva.moodle.WebServiceMoodle;
import br.com.minerva.minerva.moodle.models.CursoMoodleModel;
import br.com.minerva.minerva.repos.CursoSalaRepository;
import br.com.minerva.minerva.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;


@Service
public class SalaService {

    @Autowired
    private CursoSalaRepository cursoSalaRepository;

    public void criaAtualizaSala(CursoDTO cursoDTO,Curso curso, WebServiceMoodle webServiceMoodle){
        CursoMoodleModel cursoMoodle = null;
        CursoSala cursoSala = this.cursoSalaRepository.findByIdCurso(curso.getIdcurso());
        if (cursoSala == null){
            cursoSala = new CursoSala();
            cursoMoodle = new CursoMoodleModel();
            cursoMoodle.setId(-1);
        }else{
           cursoMoodle = webServiceMoodle.getCursoById(cursoSala.getIdcursoMoodle());
        }

        cursoMoodle.setCategoryid(curso.getIdcourseMoodle().intValue());
        cursoMoodle.setFullname(curso.getNomeCurso());
        cursoMoodle.setShortname(String.valueOf(LocalDate.now().getYear())+" - "+ curso.getApelido());

        cursoMoodle.setStartdate( Utils.DateToMoodleDate(cursoDTO.getDataIni()).intValue() );
        cursoMoodle.setEnddate( Utils.DateToMoodleDate( cursoDTO.getDataFim() ).intValue() );
        cursoMoodle.setVisible(cursoDTO.getAtivo());
        cursoMoodle = webServiceMoodle.cria_atualiza_Curso(cursoMoodle);


        cursoSala.setCurso(curso);
        cursoSala.setDataIni(cursoDTO.getDataIni());
        cursoSala.setDataFim(cursoDTO.getDataFim());
        cursoSala.setIdcursoMoodle(cursoMoodle.getId());
        this.cursoSalaRepository.save(cursoSala);
    }

    public boolean apagarSala(UUID idSala, WebServiceMoodle webServiceMoodle){
        return false;
    }
}

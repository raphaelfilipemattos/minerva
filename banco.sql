--
-- PostgreSQL database dump
--

-- Dumped from database version 15.5 (Debian 15.5-0+deb12u1)
-- Dumped by pg_dump version 15.5 (Debian 15.5-0+deb12u1)

-- Started on 2023-12-18 20:14:00 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 17093)
-- Name: logs; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA logs;


ALTER SCHEMA logs OWNER TO postgres;

--
-- TOC entry 230 (class 1255 OID 17259)
-- Name: gera_chave(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.gera_chave() RETURNS uuid
    LANGUAGE plpgsql PARALLEL SAFE
    AS $$begin
  return  gen_random_uuid();
end;$$;


ALTER FUNCTION public.gera_chave() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 223 (class 1259 OID 17179)
-- Name: curso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.curso (
    idcurso uuid DEFAULT public.gera_chave() NOT NULL,
    idempresa uuid NOT NULL,
    nome_curso character varying(255) NOT NULL,
    apelido character varying(100) NOT NULL,
    ativo boolean DEFAULT true NOT NULL,
    seq bigint NOT NULL,
    idcourse_moodle bigint DEFAULT '-1'::integer NOT NULL,
    valor numeric(6,2) NOT NULL,
    imagem_capa uuid,
    descricao_completa text
);


ALTER TABLE public.curso OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 17194)
-- Name: curso_turma; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.curso_turma (
    idcurso_turma uuid DEFAULT public.gera_chave() NOT NULL,
    idcurso uuid NOT NULL,
    descricao character varying(50) NOT NULL,
    data_ini date NOT NULL,
    data_fim date NOT NULL
);


ALTER TABLE public.curso_turma OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 17101)
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa (
    idempresa uuid DEFAULT public.gera_chave() NOT NULL,
    razao_social character varying(100) NOT NULL,
    nome_fantasia character varying(100) NOT NULL,
    cnpj_cpf character varying(14) NOT NULL,
    email_financeiro character varying(200) NOT NULL,
    email_educacional character varying(200) NOT NULL,
    logo uuid,
    seq bigint NOT NULL,
    nome_ambiente character varying(20) NOT NULL,
    idpacote uuid NOT NULL
);


ALTER TABLE public.empresa OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 17178)
-- Name: empresa_curso_seq_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empresa_curso_seq_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.empresa_curso_seq_seq OWNER TO postgres;

--
-- TOC entry 3480 (class 0 OID 0)
-- Dependencies: 222
-- Name: empresa_curso_seq_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.empresa_curso_seq_seq OWNED BY public.curso.seq;


--
-- TOC entry 229 (class 1259 OID 17243)
-- Name: empresa_estilo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa_estilo (
    idempresa_estilo uuid DEFAULT public.gera_chave() NOT NULL,
    idempresa uuid NOT NULL,
    cor_fundo character varying(16),
    fonte_padrao character varying(20),
    css text
);


ALTER TABLE public.empresa_estilo OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17100)
-- Name: empresa_seq_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empresa_seq_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.empresa_seq_seq OWNER TO postgres;

--
-- TOC entry 3481 (class 0 OID 0)
-- Dependencies: 216
-- Name: empresa_seq_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.empresa_seq_seq OWNED BY public.empresa.seq;


--
-- TOC entry 227 (class 1259 OID 17223)
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pagamento (
    idforma_pagamento uuid DEFAULT public.gera_chave() NOT NULL,
    descricao character(50) NOT NULL
);


ALTER TABLE public.forma_pagamento OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17203)
-- Name: inscricao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.inscricao (
    idinscricao uuid DEFAULT public.gera_chave() NOT NULL,
    idusuario uuid NOT NULL,
    idcurso uuid NOT NULL,
    data date DEFAULT CURRENT_DATE NOT NULL,
    hora time without time zone DEFAULT CURRENT_TIME NOT NULL,
    seq bigint NOT NULL,
    identificador character(10) NOT NULL
);


ALTER TABLE public.inscricao OWNER TO postgres;

--
-- TOC entry 3482 (class 0 OID 0)
-- Dependencies: 226
-- Name: COLUMN inscricao.identificador; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.inscricao.identificador IS 'Identificador do pagamento';


--
-- TOC entry 225 (class 1259 OID 17202)
-- Name: inscricao_seq_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.inscricao_seq_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.inscricao_seq_seq OWNER TO postgres;

--
-- TOC entry 3483 (class 0 OID 0)
-- Dependencies: 225
-- Name: inscricao_seq_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.inscricao_seq_seq OWNED BY public.inscricao.seq;


--
-- TOC entry 215 (class 1259 OID 17094)
-- Name: pacote; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pacote (
    idpacote uuid DEFAULT public.gera_chave() NOT NULL,
    descricao character varying(50) NOT NULL,
    valor numeric(5,2) NOT NULL,
    tipo "char" DEFAULT '%'::"char" NOT NULL,
    max_cursos smallint DEFAULT '-1'::integer NOT NULL,
    uso_disco smallint
);


ALTER TABLE public.pacote OWNER TO postgres;

--
-- TOC entry 3484 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN pacote.uso_disco; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.pacote.uso_disco IS 'Uso do HD do moodledata;
Valor em GB';


--
-- TOC entry 228 (class 1259 OID 17228)
-- Name: pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pagamento (
    idpagamento uuid DEFAULT public.gera_chave() NOT NULL,
    idinscricao uuid NOT NULL,
    valor numeric(6,2) NOT NULL,
    valor_pago numeric(6,2) NOT NULL,
    data date NOT NULL,
    hora timestamp without time zone,
    idforma_pagamento uuid NOT NULL
);


ALTER TABLE public.pagamento OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17114)
-- Name: perfil; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.perfil (
    idperfil uuid DEFAULT public.gera_chave() NOT NULL,
    nome character varying(50) NOT NULL
);


ALTER TABLE public.perfil OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17161)
-- Name: perfil_empresa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.perfil_empresa (
    idperfil_empresa uuid DEFAULT public.gera_chave() NOT NULL,
    idempresa uuid NOT NULL,
    idperfil uuid NOT NULL,
    idperfil_moodle integer NOT NULL
);


ALTER TABLE public.perfil_empresa OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 17140)
-- Name: perfil_usuario_empresa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.perfil_usuario_empresa (
    idperfil_usuario_empresa uuid DEFAULT public.gera_chave() NOT NULL,
    idusuario uuid NOT NULL,
    idperfil uuid NOT NULL,
    idusuario_moodle bigint DEFAULT '-1'::integer NOT NULL,
    idempresa uuid NOT NULL
);


ALTER TABLE public.perfil_usuario_empresa OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17117)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    idusuario uuid DEFAULT public.gera_chave() NOT NULL,
    nome character varying(150) NOT NULL,
    email character varying(200) NOT NULL,
    cpf character varying(11) NOT NULL,
    senha character varying(200) NOT NULL,
    seq bigint NOT NULL,
    data_cadastro timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_change timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 3261 (class 2604 OID 17183)
-- Name: curso seq; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.curso ALTER COLUMN seq SET DEFAULT nextval('public.empresa_curso_seq_seq'::regclass);


--
-- TOC entry 3251 (class 2604 OID 17104)
-- Name: empresa seq; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa ALTER COLUMN seq SET DEFAULT nextval('public.empresa_seq_seq'::regclass);


--
-- TOC entry 3267 (class 2604 OID 17208)
-- Name: inscricao seq; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inscricao ALTER COLUMN seq SET DEFAULT nextval('public.inscricao_seq_seq'::regclass);


--
-- TOC entry 3292 (class 2606 OID 17185)
-- Name: curso curso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.curso
    ADD CONSTRAINT curso_pkey PRIMARY KEY (idcurso);


--
-- TOC entry 3304 (class 2606 OID 17249)
-- Name: empresa_estilo empresa_estilo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa_estilo
    ADD CONSTRAINT empresa_estilo_pkey PRIMARY KEY (idempresa_estilo);


--
-- TOC entry 3274 (class 2606 OID 17108)
-- Name: empresa empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (idempresa);


--
-- TOC entry 3300 (class 2606 OID 17227)
-- Name: forma_pagamento forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (idforma_pagamento);


--
-- TOC entry 3296 (class 2606 OID 17210)
-- Name: inscricao inscricao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inscricao
    ADD CONSTRAINT inscricao_pkey PRIMARY KEY (idinscricao);


--
-- TOC entry 3272 (class 2606 OID 17099)
-- Name: pacote pacote_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacote
    ADD CONSTRAINT pacote_pkey PRIMARY KEY (idpacote);


--
-- TOC entry 3302 (class 2606 OID 17232)
-- Name: pagamento pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_pkey PRIMARY KEY (idpagamento);


--
-- TOC entry 3288 (class 2606 OID 17165)
-- Name: perfil_empresa perfil_empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfil_empresa
    ADD CONSTRAINT perfil_empresa_pkey PRIMARY KEY (idperfil_empresa);


--
-- TOC entry 3278 (class 2606 OID 17139)
-- Name: perfil perfil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfil
    ADD CONSTRAINT perfil_pkey PRIMARY KEY (idperfil);


--
-- TOC entry 3286 (class 2606 OID 17145)
-- Name: perfil_usuario_empresa perfil_usuario_empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfil_usuario_empresa
    ADD CONSTRAINT perfil_usuario_empresa_pkey PRIMARY KEY (idperfil_usuario_empresa);


--
-- TOC entry 3276 (class 2606 OID 17131)
-- Name: empresa unq_empresa_cnpj; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT unq_empresa_cnpj UNIQUE (cnpj_cpf);


--
-- TOC entry 3294 (class 2606 OID 17187)
-- Name: curso unq_empresa_curso; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.curso
    ADD CONSTRAINT unq_empresa_curso UNIQUE (idempresa, apelido);


--
-- TOC entry 3298 (class 2606 OID 17212)
-- Name: inscricao unq_inscricao_identificador; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inscricao
    ADD CONSTRAINT unq_inscricao_identificador UNIQUE (identificador);


--
-- TOC entry 3290 (class 2606 OID 17167)
-- Name: perfil_empresa unq_perfil_empresa; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfil_empresa
    ADD CONSTRAINT unq_perfil_empresa UNIQUE (idempresa, idperfil);


--
-- TOC entry 3280 (class 2606 OID 17129)
-- Name: usuario unq_usuario_cpf; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT unq_usuario_cpf UNIQUE (cpf);


--
-- TOC entry 3282 (class 2606 OID 17127)
-- Name: usuario unq_usuario_email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT unq_usuario_email UNIQUE (email);


--
-- TOC entry 3284 (class 2606 OID 17125)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (idusuario);


--
-- TOC entry 3312 (class 2606 OID 17197)
-- Name: curso_turma curso_turma; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.curso_turma
    ADD CONSTRAINT curso_turma FOREIGN KEY (idcurso) REFERENCES public.curso(idcurso) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3311 (class 2606 OID 17188)
-- Name: curso fk_curso_empresa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.curso
    ADD CONSTRAINT fk_curso_empresa FOREIGN KEY (idempresa) REFERENCES public.empresa(idempresa) ON UPDATE CASCADE;


--
-- TOC entry 3317 (class 2606 OID 17250)
-- Name: empresa_estilo fk_empresa_estilo_empresa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa_estilo
    ADD CONSTRAINT fk_empresa_estilo_empresa FOREIGN KEY (idempresa) REFERENCES public.empresa(idempresa) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3305 (class 2606 OID 17109)
-- Name: empresa fk_empresa_plano; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT fk_empresa_plano FOREIGN KEY (idpacote) REFERENCES public.pacote(idpacote) ON UPDATE CASCADE;


--
-- TOC entry 3313 (class 2606 OID 17218)
-- Name: inscricao fk_inscricao_curso; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inscricao
    ADD CONSTRAINT fk_inscricao_curso FOREIGN KEY (idcurso) REFERENCES public.curso(idcurso) ON UPDATE CASCADE;


--
-- TOC entry 3314 (class 2606 OID 17213)
-- Name: inscricao fk_inscricao_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inscricao
    ADD CONSTRAINT fk_inscricao_usuario FOREIGN KEY (idusuario) REFERENCES public.usuario(idusuario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3315 (class 2606 OID 17238)
-- Name: pagamento fk_pagamento_forma_pagamento; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT fk_pagamento_forma_pagamento FOREIGN KEY (idforma_pagamento) REFERENCES public.forma_pagamento(idforma_pagamento) ON UPDATE CASCADE;


--
-- TOC entry 3316 (class 2606 OID 17233)
-- Name: pagamento fk_pagamento_inscricao; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT fk_pagamento_inscricao FOREIGN KEY (idinscricao) REFERENCES public.inscricao(idinscricao) ON UPDATE CASCADE;


--
-- TOC entry 3309 (class 2606 OID 17168)
-- Name: perfil_empresa fk_perfil_empresa_empresa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfil_empresa
    ADD CONSTRAINT fk_perfil_empresa_empresa FOREIGN KEY (idempresa) REFERENCES public.empresa(idempresa) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3310 (class 2606 OID 17173)
-- Name: perfil_empresa fk_perfil_empresa_perfil; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfil_empresa
    ADD CONSTRAINT fk_perfil_empresa_perfil FOREIGN KEY (idperfil) REFERENCES public.perfil(idperfil) ON UPDATE CASCADE;


--
-- TOC entry 3306 (class 2606 OID 17156)
-- Name: perfil_usuario_empresa fk_perfil_usuario_empresa_empresa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfil_usuario_empresa
    ADD CONSTRAINT fk_perfil_usuario_empresa_empresa FOREIGN KEY (idempresa) REFERENCES public.empresa(idempresa) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3307 (class 2606 OID 17146)
-- Name: perfil_usuario_empresa fk_perfil_usuario_empresa_perfil; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfil_usuario_empresa
    ADD CONSTRAINT fk_perfil_usuario_empresa_perfil FOREIGN KEY (idperfil) REFERENCES public.perfil(idperfil) ON UPDATE CASCADE;


--
-- TOC entry 3308 (class 2606 OID 17151)
-- Name: perfil_usuario_empresa fk_perfil_usuario_empresa_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfil_usuario_empresa
    ADD CONSTRAINT fk_perfil_usuario_empresa_usuario FOREIGN KEY (idusuario) REFERENCES public.usuario(idusuario) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2023-12-18 20:14:00 -03

--
-- PostgreSQL database dump complete
--


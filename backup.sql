--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2 (Debian 15.2-1.pgdg110+1)
-- Dumped by pg_dump version 15.2

-- Started on 2023-03-28 10:16:51

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16406)
-- Name: appuser; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appuser (
    id bigint NOT NULL,
    username character varying,
    password character varying
);


ALTER TABLE public.appuser OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16401)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    age integer,
    name character varying(255)
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16394)
-- Name: person_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_seq OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16400)
-- Name: pk_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pk_sequence
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pk_sequence OWNER TO postgres;

--
-- TOC entry 3328 (class 0 OID 16406)
-- Dependencies: 217
-- Data for Name: appuser; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.appuser (id, username, password) FROM stdin;
1	admin	$2y$10$DsK4z1Xxj1/7GIqr6FDuiObpGV8eRBgWNJLpW.tTpaADbmfqIDsNe
\.


--
-- TOC entry 3327 (class 0 OID 16401)
-- Dependencies: 216
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person (id, age, name) FROM stdin;
1	44	John Doe
2	44	Honza
3	49	John Smith
4	25	Link
5	57	Mario
6	41	Samus
7	61	Lara
8	32	Bruce
9	70	William
10	35	Honza
\.


--
-- TOC entry 3334 (class 0 OID 0)
-- Dependencies: 214
-- Name: person_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_seq', 651, true);


--
-- TOC entry 3335 (class 0 OID 0)
-- Dependencies: 215
-- Name: pk_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pk_sequence', 2501, true);


--
-- TOC entry 3182 (class 2606 OID 16405)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


-- Completed on 2023-03-28 10:16:51

--
-- PostgreSQL database dump complete
--


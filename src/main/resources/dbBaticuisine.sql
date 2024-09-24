--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-09-24 21:10:08

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
-- TOC entry 4874 (class 1262 OID 49557)
-- Name: Baticuizine; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "Baticuizine" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';


ALTER DATABASE "Baticuizine" OWNER TO postgres;

\connect "Baticuizine"

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
-- TOC entry 851 (class 1247 OID 49621)
-- Name: project_status; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.project_status AS ENUM (
    'IN_PROGRESS',
    'COMPLETED',
    'CANCELED'
);


ALTER TYPE public.project_status OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 49558)
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    address character varying(255) NOT NULL,
    phone character varying(20) NOT NULL,
    is_professional boolean NOT NULL
);


ALTER TABLE public.client OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 49631)
-- Name: component; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.component (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    vat_rate numeric(5,2) DEFAULT NULL::numeric,
    component_type character varying(50) NOT NULL,
    project_id uuid
);


ALTER TABLE public.component OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 49650)
-- Name: estimate; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estimate (
    id uuid NOT NULL,
    estimated_amount numeric(10,2) NOT NULL,
    issue_date date NOT NULL,
    validity_date date NOT NULL,
    is_accepted boolean NOT NULL,
    project_id uuid
);


ALTER TABLE public.estimate OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 49646)
-- Name: labor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.labor (
    hourly_rate numeric(10,2) NOT NULL,
    hours_worked numeric(10,2) NOT NULL,
    productivity_factor numeric(5,2) NOT NULL
)
INHERITS (public.component);


ALTER TABLE public.labor OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 49642)
-- Name: material; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.material (
    unit_cost numeric(10,2) NOT NULL,
    quantity numeric(10,2) NOT NULL,
    transport_cost numeric(10,2) NOT NULL,
    quality_coefficient numeric(5,2) NOT NULL
)
INHERITS (public.component);


ALTER TABLE public.material OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 49587)
-- Name: project; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.project (
    id uuid NOT NULL,
    project_name character varying(255) NOT NULL,
    profit_margin numeric(5,2) DEFAULT 0,
    total_cost numeric(10,2) DEFAULT 0,
    status public.project_status NOT NULL,
    surface numeric(10,2) NOT NULL,
    client_id uuid
);


ALTER TABLE public.project OWNER TO postgres;

--
-- TOC entry 4714 (class 2604 OID 49649)
-- Name: labor vat_rate; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.labor ALTER COLUMN vat_rate SET DEFAULT NULL::numeric;


--
-- TOC entry 4713 (class 2604 OID 49645)
-- Name: material vat_rate; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.material ALTER COLUMN vat_rate SET DEFAULT NULL::numeric;


--
-- TOC entry 4716 (class 2606 OID 49564)
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- TOC entry 4720 (class 2606 OID 49636)
-- Name: component component_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT component_pkey PRIMARY KEY (id);


--
-- TOC entry 4722 (class 2606 OID 49654)
-- Name: estimate estimate_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estimate
    ADD CONSTRAINT estimate_pkey PRIMARY KEY (id);


--
-- TOC entry 4718 (class 2606 OID 49593)
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- TOC entry 4724 (class 2606 OID 49637)
-- Name: component component_project_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT component_project_id_fkey FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 4725 (class 2606 OID 49655)
-- Name: estimate estimate_project_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estimate
    ADD CONSTRAINT estimate_project_id_fkey FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 4723 (class 2606 OID 49594)
-- Name: project project_client_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.client(id);


-- Completed on 2024-09-24 21:10:09

--
-- PostgreSQL database dump complete
--


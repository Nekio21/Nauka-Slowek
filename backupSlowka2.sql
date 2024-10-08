PGDMP                         |           latarka    15.1    15.1 @    x           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            y           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            z           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            {           1262    41079    latarka    DATABASE     �   CREATE DATABASE latarka WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United Kingdom.1250';
    DROP DATABASE latarka;
                postgres    false                        3079    41313    pgcrypto 	   EXTENSION     <   CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;
    DROP EXTENSION pgcrypto;
                   false            |           0    0    EXTENSION pgcrypto    COMMENT     <   COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';
                        false    2                       1255    41263    procedura_odejmi_punkty()    FUNCTION     �  CREATE FUNCTION public.procedura_odejmi_punkty() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
declare
roznica integer;
begin
SELECT (TO_DATE(to_char(NEW.logowaniedate, 'YYYY-MM-DD'), 'YYYY-MM-DD') - TO_DATE(to_char(OLD.logowaniedate, 'YYYY-MM-DD'), 'YYYY-MM-DD')) AS integer INTO roznica;

	UPDATE Slowka s SET punkty = (punkty - roznica) Where s.idzestawu IN (SELECT pf.idzes FROM userzestawy pf Where pf.idowner = NEW.iduser);
	
	return NEW;
end;
$$;
 0   DROP FUNCTION public.procedura_odejmi_punkty();
       public          postgres    false                       1255    41287 
   procenty()    FUNCTION     �  CREATE FUNCTION public.procenty() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
declare
	ilosc integer;
	dobre integer;
	punktyCos integer;
begin
	SELECT COUNT(*) FROM Slowka s WHERE s.idzestawu = New.idzes INTO ilosc;
	SELECT COUNT(*) FROM Slowka s WHERE s.idzestawu = New.idzes AND ostatniaodpowiedzdobra = true INTO dobre;
	SELECT SUM(punkty) FROM Slowka s WHERE s.idzestawu = New.idzes INTO punktyCos;

	UPDATE historiazestawu h SET procentznajomosci = (100 * dobre)/ilosc Where h.idhz = New.idhz;
	UPDATE Zestawy z SET procentobecnejznajomosci = (100 * dobre)/ilosc Where z.idzestawy = New.idzes;
	UPDATE Zestawy z SET punkty = punktyCos Where z.idzestawy = New.idzes;
	
	return NEW;
end;
$$;
 !   DROP FUNCTION public.procenty();
       public          postgres    false                       1255    41295    punkty()    FUNCTION     *  CREATE FUNCTION public.punkty() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
declare
	punktyCos integer;
begin
	SELECT SUM(punkty) FROM Slowka s WHERE s.idzestawu = New.idzestawu INTO punktyCos;

	UPDATE Zestawy z SET punkty = punktyCos Where z.idzestawy = New.idzestawu;
	
	return NEW;
end;
$$;
    DROP FUNCTION public.punkty();
       public          postgres    false            �            1259    41194    gra    TABLE     �   CREATE TABLE public.gra (
    idgra integer NOT NULL,
    idkalendarz integer NOT NULL,
    rozpoczeciegry timestamp without time zone,
    gamemode character varying(100) NOT NULL
);
    DROP TABLE public.gra;
       public         heap    postgres    false            �            1259    41193    gra_idgra_seq    SEQUENCE     �   CREATE SEQUENCE public.gra_idgra_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.gra_idgra_seq;
       public          postgres    false    228            }           0    0    gra_idgra_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.gra_idgra_seq OWNED BY public.gra.idgra;
          public          postgres    false    227            �            1259    41228    historiazestawu    TABLE     �   CREATE TABLE public.historiazestawu (
    idhz integer NOT NULL,
    idgry integer NOT NULL,
    procentznajomosci integer,
    datagry timestamp without time zone,
    idzes integer
);
 #   DROP TABLE public.historiazestawu;
       public         heap    postgres    false            �            1259    41227    historiazestawu_idhz_seq    SEQUENCE     �   CREATE SEQUENCE public.historiazestawu_idhz_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.historiazestawu_idhz_seq;
       public          postgres    false    232            ~           0    0    historiazestawu_idhz_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.historiazestawu_idhz_seq OWNED BY public.historiazestawu.idhz;
          public          postgres    false    231            �            1259    41181 	   kalendarz    TABLE     �   CREATE TABLE public.kalendarz (
    idkalendarz integer NOT NULL,
    iduser integer NOT NULL,
    datakalendarz timestamp without time zone NOT NULL,
    dayvalue character varying(50) NOT NULL
);
    DROP TABLE public.kalendarz;
       public         heap    postgres    false            �            1259    41180    kalendarz_idkalendarz_seq    SEQUENCE     �   CREATE SEQUENCE public.kalendarz_idkalendarz_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.kalendarz_idkalendarz_seq;
       public          postgres    false    226                       0    0    kalendarz_idkalendarz_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.kalendarz_idkalendarz_seq OWNED BY public.kalendarz.idkalendarz;
          public          postgres    false    225            �            1259    41156    slowka    TABLE     f  CREATE TABLE public.slowka (
    idslowko integer NOT NULL,
    idzestawu integer,
    texta character varying(400),
    textb character varying(400),
    dobreodpowiedzi integer,
    zleodpowiedzi integer,
    punkty integer,
    datastworzenia timestamp without time zone,
    ostatniagra timestamp without time zone,
    ostatniaodpowiedzdobra boolean
);
    DROP TABLE public.slowka;
       public         heap    postgres    false            �            1259    41154    slowka_idslowko_seq    SEQUENCE     �   CREATE SEQUENCE public.slowka_idslowko_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.slowka_idslowko_seq;
       public          postgres    false    224            �           0    0    slowka_idslowko_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.slowka_idslowko_seq OWNED BY public.slowka.idslowko;
          public          postgres    false    222            �            1259    41155    slowka_idzestawu_seq    SEQUENCE     �   CREATE SEQUENCE public.slowka_idzestawu_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.slowka_idzestawu_seq;
       public          postgres    false    224            �           0    0    slowka_idzestawu_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.slowka_idzestawu_seq OWNED BY public.slowka.idzestawu;
          public          postgres    false    223            �            1259    41218    slowkagra_idslowkagra_seq    SEQUENCE     �   CREATE SEQUENCE public.slowkagra_idslowkagra_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.slowkagra_idslowkagra_seq;
       public          postgres    false            �            1259    41205 	   slowkagra    TABLE     �   CREATE TABLE public.slowkagra (
    idgry integer NOT NULL,
    idslowka integer NOT NULL,
    dobrzeczyzle boolean NOT NULL,
    idslowkagra integer DEFAULT nextval('public.slowkagra_idslowkagra_seq'::regclass) NOT NULL
);
    DROP TABLE public.slowkagra;
       public         heap    postgres    false    230            �            1259    41084    users    TABLE     �   CREATE TABLE public.users (
    iduser integer NOT NULL,
    login character varying(50) NOT NULL,
    image bytea,
    logowaniedate timestamp without time zone,
    haslo bytea
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    41083    users_iduser_seq    SEQUENCE     �   CREATE SEQUENCE public.users_iduser_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.users_iduser_seq;
       public          postgres    false    216            �           0    0    users_iduser_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.users_iduser_seq OWNED BY public.users.iduser;
          public          postgres    false    215            �            1259    41116    userzestawy    TABLE     ^   CREATE TABLE public.userzestawy (
    idowner integer NOT NULL,
    idzes integer NOT NULL
);
    DROP TABLE public.userzestawy;
       public         heap    postgres    false            �            1259    41114    userzestawy_idowner_seq    SEQUENCE     �   CREATE SEQUENCE public.userzestawy_idowner_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.userzestawy_idowner_seq;
       public          postgres    false    221            �           0    0    userzestawy_idowner_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.userzestawy_idowner_seq OWNED BY public.userzestawy.idowner;
          public          postgres    false    219            �            1259    41115    userzestawy_idzes_seq    SEQUENCE     �   CREATE SEQUENCE public.userzestawy_idzes_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.userzestawy_idzes_seq;
       public          postgres    false    221            �           0    0    userzestawy_idzes_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.userzestawy_idzes_seq OWNED BY public.userzestawy.idzes;
          public          postgres    false    220            �            1259    41093    zestawy    TABLE       CREATE TABLE public.zestawy (
    idzestawy integer NOT NULL,
    nazwa character varying(50) NOT NULL,
    punkty integer,
    ostatniagra timestamp without time zone,
    datastworzenia timestamp without time zone,
    procentobecnejznajomosci integer
);
    DROP TABLE public.zestawy;
       public         heap    postgres    false            �            1259    41092    zestawy_idzestawy_seq    SEQUENCE     �   CREATE SEQUENCE public.zestawy_idzestawy_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.zestawy_idzestawy_seq;
       public          postgres    false    218            �           0    0    zestawy_idzestawy_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.zestawy_idzestawy_seq OWNED BY public.zestawy.idzestawy;
          public          postgres    false    217            �           2604    41197 	   gra idgra    DEFAULT     f   ALTER TABLE ONLY public.gra ALTER COLUMN idgra SET DEFAULT nextval('public.gra_idgra_seq'::regclass);
 8   ALTER TABLE public.gra ALTER COLUMN idgra DROP DEFAULT;
       public          postgres    false    228    227    228            �           2604    41231    historiazestawu idhz    DEFAULT     |   ALTER TABLE ONLY public.historiazestawu ALTER COLUMN idhz SET DEFAULT nextval('public.historiazestawu_idhz_seq'::regclass);
 C   ALTER TABLE public.historiazestawu ALTER COLUMN idhz DROP DEFAULT;
       public          postgres    false    231    232    232            �           2604    41184    kalendarz idkalendarz    DEFAULT     ~   ALTER TABLE ONLY public.kalendarz ALTER COLUMN idkalendarz SET DEFAULT nextval('public.kalendarz_idkalendarz_seq'::regclass);
 D   ALTER TABLE public.kalendarz ALTER COLUMN idkalendarz DROP DEFAULT;
       public          postgres    false    226    225    226            �           2604    41159    slowka idslowko    DEFAULT     r   ALTER TABLE ONLY public.slowka ALTER COLUMN idslowko SET DEFAULT nextval('public.slowka_idslowko_seq'::regclass);
 >   ALTER TABLE public.slowka ALTER COLUMN idslowko DROP DEFAULT;
       public          postgres    false    222    224    224            �           2604    41160    slowka idzestawu    DEFAULT     t   ALTER TABLE ONLY public.slowka ALTER COLUMN idzestawu SET DEFAULT nextval('public.slowka_idzestawu_seq'::regclass);
 ?   ALTER TABLE public.slowka ALTER COLUMN idzestawu DROP DEFAULT;
       public          postgres    false    224    223    224            �           2604    41087    users iduser    DEFAULT     l   ALTER TABLE ONLY public.users ALTER COLUMN iduser SET DEFAULT nextval('public.users_iduser_seq'::regclass);
 ;   ALTER TABLE public.users ALTER COLUMN iduser DROP DEFAULT;
       public          postgres    false    216    215    216            �           2604    41120    userzestawy idowner    DEFAULT     z   ALTER TABLE ONLY public.userzestawy ALTER COLUMN idowner SET DEFAULT nextval('public.userzestawy_idowner_seq'::regclass);
 B   ALTER TABLE public.userzestawy ALTER COLUMN idowner DROP DEFAULT;
       public          postgres    false    221    219    221            �           2604    41121    userzestawy idzes    DEFAULT     v   ALTER TABLE ONLY public.userzestawy ALTER COLUMN idzes SET DEFAULT nextval('public.userzestawy_idzes_seq'::regclass);
 @   ALTER TABLE public.userzestawy ALTER COLUMN idzes DROP DEFAULT;
       public          postgres    false    221    220    221            �           2604    41096    zestawy idzestawy    DEFAULT     v   ALTER TABLE ONLY public.zestawy ALTER COLUMN idzestawy SET DEFAULT nextval('public.zestawy_idzestawy_seq'::regclass);
 @   ALTER TABLE public.zestawy ALTER COLUMN idzestawy DROP DEFAULT;
       public          postgres    false    218    217    218            �           2606    41199    gra gra_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY public.gra
    ADD CONSTRAINT gra_pkey PRIMARY KEY (idgra);
 6   ALTER TABLE ONLY public.gra DROP CONSTRAINT gra_pkey;
       public            postgres    false    228            �           2606    41233 $   historiazestawu historiazestawu_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.historiazestawu
    ADD CONSTRAINT historiazestawu_pkey PRIMARY KEY (idhz);
 N   ALTER TABLE ONLY public.historiazestawu DROP CONSTRAINT historiazestawu_pkey;
       public            postgres    false    232            �           2606    41186    kalendarz kalendarz_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public.kalendarz
    ADD CONSTRAINT kalendarz_pkey PRIMARY KEY (idkalendarz);
 B   ALTER TABLE ONLY public.kalendarz DROP CONSTRAINT kalendarz_pkey;
       public            postgres    false    226            �           2606    41162    slowka slowka_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.slowka
    ADD CONSTRAINT slowka_pkey PRIMARY KEY (idslowko);
 <   ALTER TABLE ONLY public.slowka DROP CONSTRAINT slowka_pkey;
       public            postgres    false    224            �           2606    41224    slowkagra slowkagra_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public.slowkagra
    ADD CONSTRAINT slowkagra_pkey PRIMARY KEY (idslowkagra);
 B   ALTER TABLE ONLY public.slowkagra DROP CONSTRAINT slowkagra_pkey;
       public            postgres    false    229            �           2606    41091    users users_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (iduser);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    216            �           2606    41098    zestawy zestawy_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.zestawy
    ADD CONSTRAINT zestawy_pkey PRIMARY KEY (idzestawy);
 >   ALTER TABLE ONLY public.zestawy DROP CONSTRAINT zestawy_pkey;
       public            postgres    false    218            �           2620    41296    slowka ai_nowe_punkty    TRIGGER     q   CREATE TRIGGER ai_nowe_punkty AFTER UPDATE ON public.slowka FOR EACH STATEMENT EXECUTE FUNCTION public.punkty();
 .   DROP TRIGGER ai_nowe_punkty ON public.slowka;
       public          postgres    false    281    224            �           2620    41288 #   historiazestawu ai_procentuj_triger    TRIGGER     {   CREATE TRIGGER ai_procentuj_triger AFTER INSERT ON public.historiazestawu FOR EACH ROW EXECUTE FUNCTION public.procenty();
 <   DROP TRIGGER ai_procentuj_triger ON public.historiazestawu;
       public          postgres    false    280    232            �           2620    41264    users bu_odejmij_punkty    TRIGGER     ~   CREATE TRIGGER bu_odejmij_punkty AFTER UPDATE ON public.users FOR EACH ROW EXECUTE FUNCTION public.procedura_odejmi_punkty();
 0   DROP TRIGGER bu_odejmij_punkty ON public.users;
       public          postgres    false    216    282            �           2606    41200    gra gra_idkalendarz_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.gra
    ADD CONSTRAINT gra_idkalendarz_fkey FOREIGN KEY (idkalendarz) REFERENCES public.kalendarz(idkalendarz);
 B   ALTER TABLE ONLY public.gra DROP CONSTRAINT gra_idkalendarz_fkey;
       public          postgres    false    228    3267    226            �           2606    41234 *   historiazestawu historiazestawu_idgry_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.historiazestawu
    ADD CONSTRAINT historiazestawu_idgry_fkey FOREIGN KEY (idgry) REFERENCES public.gra(idgra);
 T   ALTER TABLE ONLY public.historiazestawu DROP CONSTRAINT historiazestawu_idgry_fkey;
       public          postgres    false    3269    232    228            �           2606    41275    historiazestawu idzes    FK CONSTRAINT     {   ALTER TABLE ONLY public.historiazestawu
    ADD CONSTRAINT idzes FOREIGN KEY (idzes) REFERENCES public.zestawy(idzestawy);
 ?   ALTER TABLE ONLY public.historiazestawu DROP CONSTRAINT idzes;
       public          postgres    false    232    3263    218            �           2606    41187    kalendarz kalendarz_iduser_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.kalendarz
    ADD CONSTRAINT kalendarz_iduser_fkey FOREIGN KEY (iduser) REFERENCES public.users(iduser);
 I   ALTER TABLE ONLY public.kalendarz DROP CONSTRAINT kalendarz_iduser_fkey;
       public          postgres    false    216    226    3261            �           2606    41163    slowka slowka_idzestawu_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.slowka
    ADD CONSTRAINT slowka_idzestawu_fkey FOREIGN KEY (idzestawu) REFERENCES public.zestawy(idzestawy);
 F   ALTER TABLE ONLY public.slowka DROP CONSTRAINT slowka_idzestawu_fkey;
       public          postgres    false    218    224    3263            �           2606    41208    slowkagra slowkagra_idgry_fkey    FK CONSTRAINT     |   ALTER TABLE ONLY public.slowkagra
    ADD CONSTRAINT slowkagra_idgry_fkey FOREIGN KEY (idgry) REFERENCES public.gra(idgra);
 H   ALTER TABLE ONLY public.slowkagra DROP CONSTRAINT slowkagra_idgry_fkey;
       public          postgres    false    228    229    3269            �           2606    41213 !   slowkagra slowkagra_idslowka_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.slowkagra
    ADD CONSTRAINT slowkagra_idslowka_fkey FOREIGN KEY (idslowka) REFERENCES public.slowka(idslowko);
 K   ALTER TABLE ONLY public.slowkagra DROP CONSTRAINT slowkagra_idslowka_fkey;
       public          postgres    false    3265    229    224            �           2606    41124 $   userzestawy userzestawy_idowner_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.userzestawy
    ADD CONSTRAINT userzestawy_idowner_fkey FOREIGN KEY (idowner) REFERENCES public.users(iduser);
 N   ALTER TABLE ONLY public.userzestawy DROP CONSTRAINT userzestawy_idowner_fkey;
       public          postgres    false    221    3261    216            �           2606    41129 "   userzestawy userzestawy_idzes_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.userzestawy
    ADD CONSTRAINT userzestawy_idzes_fkey FOREIGN KEY (idzes) REFERENCES public.zestawy(idzestawy);
 L   ALTER TABLE ONLY public.userzestawy DROP CONSTRAINT userzestawy_idzes_fkey;
       public          postgres    false    221    218    3263           
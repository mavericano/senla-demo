CREATE TABLE public.weather_info
(
    id bigserial NOT NULL,
    humidity smallint,
    pressure_millibars smallint,
    temp_celsius smallint,
    temp_fahrenheit smallint,
    wind_speed_ms double precision,
    requested_at timestamp,
    city character varying(255) COLLATE pg_catalog."default",
    condition_text character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT weather_info_pkey PRIMARY KEY (id)
)
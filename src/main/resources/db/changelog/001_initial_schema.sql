CREATE TABLE public.weather_info
(
    id bigserial not null,
    humidity smallint not null,
    pressure_millibars double precision not null,
    temp_celsius double precision not null,
    temp_fahrenheit double precision not null,
    wind_speed_ms double precision not null,
    requested_at timestamp without time zone not null check(requested_at <= NOW()),
    city character varying(255) COLLATE pg_catalog."default" not null,
    condition_text character varying(255) COLLATE pg_catalog."default" not null,
    CONSTRAINT weather_info_pkey PRIMARY KEY (id)
)
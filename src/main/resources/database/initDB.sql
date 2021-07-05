CREATE SCHEMA IF NOT EXISTS sensors
CREATE TABLE IF NOT EXISTS metrics
(
    id    BIGINT PRIMARY KEY ,
    version integer NOT NULL,
    sensor_id integer NOT NULL ,
    time_stamp bigint NOT NULL ,
    sensor_value smallint  NOT NULL
    );
CREATE TABLE IF NOT EXISTS detectors
(
    sensor_id BIGINT PRIMARY KEY ,
    name VARCHAR NOT NULL
);
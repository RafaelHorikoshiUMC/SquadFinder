CREATE TABLE perfils (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    apelido TEXT NOT NULL,
    nascimento TEXT NOT NULL,
    genero_preferido TEXT NOT NULL,
    comunicacao TEXT NOT NULL,
    plataforma TEXT NOT NULL,
    periodo_online TEXT NOT NULL,
    estilo_de_jogo TEXT NOT NULL,
    descricao TEXT NOT NULL,
    estado_civil TEXT NOT NULL
);
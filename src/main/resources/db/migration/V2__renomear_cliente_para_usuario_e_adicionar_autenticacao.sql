-- Renomeia a tabela de cliente para usuario
ALTER TABLE cliente
    RENAME TO usuario;

-- Adiciona os dados necessários para autenticação
ALTER TABLE usuario
    ADD COLUMN senha VARCHAR(255);

ALTER TABLE usuario
    ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'CUSTOMER';

-- Atualiza o relacionamento do carrinho
ALTER TABLE carrinho
    RENAME COLUMN cliente_id TO usuario_id;

ALTER TABLE carrinho
    DROP CONSTRAINT fk_carrinho_cliente;

ALTER TABLE carrinho
    ADD CONSTRAINT fk_carrinho_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id);

-- Atualiza o relacionamento da venda
ALTER TABLE venda
    RENAME COLUMN cliente_id TO usuario_id;

ALTER TABLE venda
    DROP CONSTRAINT fk_venda_cliente;

ALTER TABLE venda
    ADD CONSTRAINT fk_venda_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id);
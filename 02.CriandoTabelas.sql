create database ecommerce;

use ecommerce;

create table usuarios(
	id int auto_increment primary key,
    nome varchar(100) not null,
    email varchar(100) unique not null,
    senha varchar(15) not null,
    celular varchar(20),
    cpf varchar(14),
    criado_em datetime default current_timestamp
);

create table enderecos(
	id int auto_increment primary key,
    cliente_id int,
    rua varchar(100),
    numero varchar(10),
    bairro varchar(50),
    cidade varchar(50),
    estado char(2),
    cep varchar(20)
);

create table categorias(
	id int auto_increment primary key,
    nome varchar(100) not null,
    descricao text
);

create table produtos(
	id int auto_increment primary key,
    nome varchar(100) not null,
    descricao text,
    preco decimal(10,2),
    estoque int,
	categoria_id int
);

create table itens_pedido(
	id int auto_increment primary key,
    pedido_id int,
    produto_id int,
    quantidade int not null,
    preco_unitario decimal(10,2) not null
);

create table pedidos(
	id int auto_increment primary key,
    cliente_id int,
    data_pedido datetime default current_timestamp,
    status varchar(50) default 'Pendente',
    total decimal(10,2)
);
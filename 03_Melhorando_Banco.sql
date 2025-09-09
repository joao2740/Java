/* 6. Modificando e melhorando o banco*/
-- 6.1 Renomeando Colunas
alter table clientes
change column telefone celular varchar(15);
-- 6.2 Renomeando Tabela
rename table clientes to usuarios;
-- alternando tipo de dados dee uma coluna 
alter table usuarios 
add column telefone varchar(12) after email;usuarios
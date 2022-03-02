\c village

INSERT INTO roles (role_id,role_name) VALUES (1,'ADMIN');
INSERT INTO roles (role_id,role_name) VALUES (2,'USER');
INSERT INTO roles (role_id,role_name) VALUES (3,'ADMIN,USER');

INSERT INTO user_credential(id, email, password) VALUES (1,'admin@company.com','$2a$10$OnAe86LNvkgk0FK1imKIneYP9ToxNTNGGwHK84hzdvhtYggs4y7YO');

INSERT INTO users_role(user_id,role_id) VALUES (1,1);

INSERT INTO resident(id, first_name, last_name, age, born_date, income, cpf, user_id) VALUES (1,'System','Admin',23,'1998-05-03',3500,'86672514023',1);

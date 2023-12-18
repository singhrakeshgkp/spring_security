
use spring_security_role;
SELECT * FROM spring_security.customers where user_name='rsingh-admin';

insert into customers (id,user_name,password) values(1,'rsingh-admin','admin123');
insert into customers  (id,user_name,password) values(2,'rsingh-staff','staff123');
insert into customers (id,user_name,password) values(3,'rsingh-user','user123');

select * from roles;

insert into roles (id,role_name) values(1,'admin');
insert into roles (id,role_name) values(2,'staff');
insert into roles (id,role_name) values(3,'user');

select * from authorities;

insert into authorities values (1,'read');
insert into authorities values (2,'write');
insert into authorities values (3,'delete');


select * from customers_roles;

insert into customers_roles (customer_id,role_id) values(1,1);
insert into customers_roles (customer_id,role_id) values(2,2);
insert into customers_roles (customer_id,role_id) values(3,3);

select * from roles_authorities;

insert into roles_authorities (role_id,authority_id) values(1,1);
insert into roles_authorities (role_id,authority_id) values(1,2);
insert into roles_authorities (role_id,authority_id) values(1,3);

insert into roles_authorities (role_id,authority_id) values(2,1);
insert into roles_authorities (role_id,authority_id) values(2,2);

insert into roles_authorities (role_id,authority_id) values(3,1);



SELECT * FROM spring_security.customers;

insert into customers (id,user_name,password) values(1,'rsingh-user','user123');
insert into customers  (id,user_name,password) values(2,'rsingh-staff','staff123');
insert into customers (id,user_name,password) values(3,'rsingh-admin','admin123');

insert into authorities values (1,'read');
insert into authorities values (2,'write');
insert into authorities values (3,'delete');


insert into customers_authorities (customer_id,authority_id) values(1,1);
insert into customers_authorities (customer_id,authority_id) values(2,1);
insert into customers_authorities (customer_id,authority_id) values(2,2);
insert into customers_authorities (customer_id,authority_id) values(3,1);
insert into customers_authorities (customer_id,authority_id) values(3,2);
insert into customers_authorities (customer_id,authority_id) values(3,3);


select * from customers_authorities;
commit

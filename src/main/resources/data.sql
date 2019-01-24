
insert into users values(1,1,1,1,1,"user","pass","admin@admin.com", null);

insert into roles(role) value("USER");
insert into roles(role) value("ADMIN");

insert into user_role values(1,1);

insert into income_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Wynagrodzenie");
insert into income_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Prezent");
insert into income_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Nagroda");
insert into income_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Sprzedaż");
insert into income_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Odsetki");
insert into income_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Różne");

insert into expense_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Jedzenie");
insert into expense_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Mieszkanie");
insert into expense_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Inne opłaty i rachunki");
insert into expense_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Zdrowie, higiena i chemia");
insert into expense_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Ubranie");
insert into expense_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Relaks");
insert into expense_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Transport");
insert into expense_categories(created_by, created_date, is_global, name) values(1, '2019-01-01', 1, "Różne");

begin transaction;

drop table if exists brewery;
drop table if exists beer;
drop table if exists users;
drop table if exists roles;

create table brewery(
    brewery_id serial not null,
    brewery_name varchar(100) not null,
    street_address varchar(100) not null,
    city varchar(50) not null,
    state_abbreviation varchar(2) not null,
    zip_code varchar(10) not null,
    phone_number varchar(10) null,
    constraint pk_brewery primary key (brewery_id)
    );

create table beer(
    beer_id serial not null,
    beer_name varchar(50) not null,
    style varchar(50) not null,
    description varchar(500) null,
    brewery_id int not null,
    constraint pk_beer primary key (beer_id),
    constraint fk_beer_brewery_id foreign key (brewery_id) references brewery (brewery_id)
);

create table users(
    username varchar(200) primary key,
    password varchar(200)
);

create table roles(
    username varchar(200) references users(username),
    role varchar(200)
);

insert into brewery (brewery_name, street_address, city, state_abbreviation, zip_code, phone_number)
values
    ('March First Brewing', '7885 E Kemper Rd', 'Cincinnati', 'OH', '45249', '5139261282'),
    ('Masthead Brewing Company', '1261 Superior Ave', 'Cleveland', 'OH', '44114', '2162066176'),
    ('Russian River Brewing Company', '725 4th St', 'Santa Rosa', 'CA', '95404', '7075452337'),
    ('Rhinegeist Brewery', '1910 Elm St', 'Cincinnati', 'OH', '45202', '5133811267'),
    ('Utepils Brewing', '225 Thomas Ave N #700', 'Minneapolis', 'MN', '55405', '6122497800');
select * from brewery;

insert into beer (beer_name, style, description, brewery_id)
values
    ('Encantado Raspberry', 'Sour', 'A refreshing raspberry gose perfect for lovers on non-traditional beers', (select brewery_id from brewery where brewery_name = 'March First Brewing')),
    ('Webbed Roots', 'Stout', 'Imperial Stout brewed with maple syrup aged in bourbon barrels, and then double barrel aged in maple syrup bourbon barrels', (select brewery_id from brewery where brewery_name = 'Masthead Brewing Company')),
    ('Pliny the Elder', 'Double IPA', 'A well balanced IPA with the fresh hop scent of floral, citrus, and pine', (select brewery_id from brewery where brewery_name = 'Russian River Brewing Company')),
    ('Cheetah', 'Lager', 'Pure, crisp, clean. Blissful simplicity', (select brewery_id from brewery where brewery_name = 'Rhinegeist Brewery')),
    ('Keller Pils', 'Pilsner', 'Our signature Pilsner, unfiltered. Keller Pils is brewed with Czech-grown barley and hops; fermented with a Czech lager yeast strain.', (select brewery_id from brewery where brewery_name = 'Utepils Brewing'));
select * from beer

commit;
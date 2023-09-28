CREATE TABLE category(
   id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
   category_name VARCHAR(255)
);
CREATE TABLE role(
   id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
   date_create TIMESTAMP,
   date_update TIMESTAMP,
   name VARCHAR(255)
);
CREATE TABLE user_and_role(
   role_id BIGINT NOT NULL,
   user_id BIGINT NOT NULL,
   PRIMARY KEY (user_id, role_id)
);
CREATE TABLE users(
   id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
   birthday DATE,
   created_at TIMESTAMP,
   email VARCHAR(255),
   enabled BOOLEAN NOT NULL,
   first_name VARCHAR(255),
   last_modified TIMESTAMP,
   last_name VARCHAR(255),
   password VARCHAR(255),
   phone VARCHAR(255),
   username VARCHAR(255),
   CONSTRAINT email_unique UNIQUE (email)
 );

CREATE TABLE confirmationtoken(
   id BIGINT NOT NULL PRIMARY KEY,
   confirmed_at TIMESTAMP,
   created_at TIMESTAMP NOT NULL,
   expired_at TIMESTAMP NOT NULL,
   date_update TIMESTAMP NOT NULL,
   token VARCHAR(255) NOT NULL,
   user_id BIGINT NOT NULL,
   CONSTRAINT fk_users FOREIGN KEY(user_id) REFERENCES users(id)
);
CREATE TABLE expense_title(
   id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
   expense_name VARCHAR(255),
   category_id BIGINT,
   CONSTRAINT fk_category FOREIGN KEY(category_id) REFERENCES category(id)
);
CREATE TABLE expense(
   id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
   amount NUMERIC(19,2),
   comment VARCHAR(255),
   created_at TIMESTAMP,
   currency VARCHAR(255),
   exchange_rate_to_ruble NUMERIC(19,2),
   expense_title_id BIGINT,
   user_id BIGINT,
   CONSTRAINT fk_expense_title FOREIGN KEY(expense_title_id) REFERENCES expense_title(id)
);

INSERT INTO category (category_name) VALUES ('business lunch');
INSERT INTO category (category_name) VALUES ('food');
INSERT INTO category (category_name) VALUES ('fast&food&restaurant');
INSERT INTO category (category_name) VALUES ('rental(рентл)');
INSERT INTO category (category_name) VALUES ('gift for others');
INSERT INTO category (category_name) VALUES ('mobile bills');
INSERT INTO category (category_name) VALUES ('transport');
INSERT INTO category (category_name) VALUES ('earnings');
INSERT INTO category (category_name) VALUES ('healthcare&fitnes');
INSERT INTO category (category_name) VALUES ('entertainment');
INSERT INTO category (category_name) VALUES ('utility bills');
INSERT INTO category (category_name) VALUES ('clothes');
INSERT INTO category (category_name) VALUES ('extraodinary');
INSERT INTO category (category_name) VALUES ('household');
INSERT INTO category (category_name) VALUES ('travelling');
INSERT INTO category (category_name) VALUES ('cashback');
INSERT INTO category (category_name) VALUES ('electronic');
INSERT INTO category (category_name) VALUES ('human capital');

INSERT INTO expense_title(expense_name,category_id) VALUES ('seconds_bb','1');
INSERT INTO expense_title(expense_name,category_id) VALUES ('starters(soup)_bb','1');
INSERT INTO expense_title(expense_name,category_id) VALUES ('bread','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('seconds_ff','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('rental(рентл)','4');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pastry(пейстри)','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('drink_bb','1');
INSERT INTO expense_title(expense_name,category_id) VALUES ('other<10RUR','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('afternoon snack','1');
INSERT INTO expense_title(expense_name,category_id) VALUES ('milk','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('drink_ff','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('gift for others','5');
INSERT INTO expense_title(expense_name,category_id) VALUES ('sour cream','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('salad_bb','1');
INSERT INTO expense_title(expense_name,category_id) VALUES ('starters(soup)_ff','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('breakfast_bb','1');
INSERT INTO expense_title(expense_name,category_id) VALUES ('mobile bills','6');
INSERT INTO expense_title(expense_name,category_id) VALUES ('curd','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('home cooking in store2','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('apples','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cheese','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('salad','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('onion','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('land','7');
INSERT INTO expense_title(expense_name,category_id) VALUES ('tomatos','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('bananas','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('salary','8');
INSERT INTO expense_title(expense_name,category_id) VALUES ('chicken filllet','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('yoghurt','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('butter','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('green','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('bottled water','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cake','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('desert','1');
INSERT INTO expense_title(expense_name,category_id) VALUES ('healthcare&fitnes','9');
INSERT INTO expense_title(expense_name,category_id) VALUES ('candy','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('salad_ff','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cucumbers','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('sausage','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('chicken legs','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('potatos','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('entertainment','10');
INSERT INTO expense_title(expense_name,category_id) VALUES ('carrot','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('eggs','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('internet bills','11');
INSERT INTO expense_title(expense_name,category_id) VALUES ('clothes','12');
INSERT INTO expense_title(expense_name,category_id) VALUES ('electric bills','11');
INSERT INTO expense_title(expense_name,category_id) VALUES ('chocolate','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('subway','7');
INSERT INTO expense_title(expense_name,category_id) VALUES ('extraodinary','13');
INSERT INTO expense_title(expense_name,category_id) VALUES ('spaghetti','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('tea','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('home cooking in store1','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('telephone bills','11');
INSERT INTO expense_title(expense_name,category_id) VALUES ('other household','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('water','11');
INSERT INTO expense_title(expense_name,category_id) VALUES ('fish','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pepper','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('beef','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('lemon','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('canned vegetables(кянд)','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('juice','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('ice-cream','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('rice','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('buckwheat','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('travelling','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cashback','16');
INSERT INTO expense_title(expense_name,category_id) VALUES ('bumf','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('tickets','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('condiment','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('toothpaste','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('meatballs','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('mince','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('jam','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('grape','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cherry','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('zucchini','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('watermelon','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cabbage','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('nuts','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('meal','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('curd pudding','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('sugar','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('soap','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('detergent','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cream','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('oil','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pear','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('city transport','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('peach','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('mortgage','4');
INSERT INTO expense_title(expense_name,category_id) VALUES ('garlic','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pea','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pork','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('souce','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cafe','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('soap powder','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('condensed milk','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('home cooking in store3(dessert)','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cleaning sponge','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('raisins','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('beer','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('beans','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('mayonnaise(меинэйз)','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('excursion','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('honey','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('electronic','17');
INSERT INTO expense_title(expense_name,category_id) VALUES ('dried fruits','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('orange','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('kiwi','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('shampoo','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('crackers','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('tips_ff','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('tooth brush','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('home cooking in store4(beverage)','3');
INSERT INTO expense_title(expense_name,category_id) VALUES ('salt','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('coke','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('other one','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('plum','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('broccoli','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('sponge','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('arugula','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('bulb','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('tangerine','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('diospyros','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('hotel','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('appliance','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('beet','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('ketchup(кетчап)','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('books','18');
INSERT INTO expense_title(expense_name,category_id) VALUES ('frozen vegetables','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cauliflower','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('ginger','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('lettuce','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('semolina','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('millet','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('kisel','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('ear plugs','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('preparative expenses','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cutlet(катлит)','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pate','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('money back','16');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pineapple','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('strawberry','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cotton buds','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('oatmeal','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('intercity bus','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('abricot','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('eggplant','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('wheat','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('mushrooms','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('taxi','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('souvenirs','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('courses','18');
INSERT INTO expense_title(expense_name,category_id) VALUES ('corn','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('comb(расческа)коум','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('gel for shower','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('tomato paste','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('almonds (а:мондс)','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('radish','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('khinkali','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('bast whisp','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('shaver edge','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('tips','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('grapefruit','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('matches','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('sell product/service','16');
INSERT INTO expense_title(expense_name,category_id) VALUES ('associated costs','18');
INSERT INTO expense_title(expense_name,category_id) VALUES ('ravioli','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('walnut','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('reinvestment','16');
INSERT INTO expense_title(expense_name,category_id) VALUES ('milkshake','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('caviar','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('persimmon','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('kvas','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('mango','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('garbage bag','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('shoe polish','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('sun lounger','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('softener','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pomelo','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('flour','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('blueberry','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('sunflower seeds','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pomegranate','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('plov','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('deodorant','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cartridge for fumitox','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('shaving foam','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('avia','7');
INSERT INTO expense_title(expense_name,category_id) VALUES ('peanut','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('gum','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('laurel leaf','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cashew','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('vInigar','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('vodka','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('coffee','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('marrow','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pickles','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('loyalty card','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('frozen uncooked food','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('cream-gel','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('face cream','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('dishes','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('barley','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('avocado','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('bed linen','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('toilet','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('dental floss','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('alkaline battery','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('children food','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('soup','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('wipe','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('rum(рам)','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('chips','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('corn grits','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('dough(дэв)','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('ends of cuts','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('meat','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('pistachio(писташиэу)','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('rye flakes','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('currant','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('couscous','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('coriander','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('vegetable set','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('wine','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('tab','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('towel','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('mattress(мЯтрис)','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('air freshener','14');
INSERT INTO expense_title(expense_name,category_id) VALUES ('theatre','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('lost money','15');
INSERT INTO expense_title(expense_name,category_id) VALUES ('subscribtion','18');
INSERT INTO expense_title(expense_name,category_id) VALUES ('celery','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('gooseberry','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('figs','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('peanut paste','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('rental housing','16');
INSERT INTO expense_title(expense_name,category_id) VALUES ('other utility','11');
INSERT INTO expense_title(expense_name,category_id) VALUES ('baking soda','2');
INSERT INTO expense_title(expense_name,category_id) VALUES ('chocolate cream','2');




INSERT INTO users(birthday,created_at,email,enabled,first_name,last_modified,last_name,password, phone,username) VALUES (null,null,'ivan@mail.ru','t','Ivan',null,'Ivanov    ','$2a$05$qOWwd1NUJi.Zs3zLGhiJUOHzZ.x/lU2czcDHByq.FcPHyAfriDP9i','777','ivan');
INSERT INTO users(birthday,created_at,email,enabled,first_name,last_modified,last_name,password, phone,username) VALUES (null,null,'roman@mail.ru','t','Roman',null,'Romanov   ','$2a$05$74//KmHbBVt68j7VW5wrnuuua6wypEekRfuEkpFY3o/Ahd9G62qeO','773','roman');
INSERT INTO users(birthday,created_at,email,enabled,first_name,last_modified,last_name,password, phone,username) VALUES (null,null,'marina@mail.ru','t','Marina',null,'Fedotova  ','$2a$05$jdRuwVSd/3Qlp6qzJQARRuKaDkKJPW8rzE2AJ94hncgR2s1IUSRwq','774','marina');


INSERT INTO role(date_create, date_update, name) VALUES (CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'ROLE_USER');

INSERT INTO user_and_role(user_id, role_id) VALUES (1,1);
INSERT INTO user_and_role(user_id, role_id) VALUES (2,1);
INSERT INTO user_and_role(user_id, role_id) VALUES (3,1);
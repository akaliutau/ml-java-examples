drop table if exists preferences;
drop table if exists books;
drop table if exists users;

CREATE TABLE preferences ( 
 user_id BIGINT NOT NULL, 
 item_id BIGINT NOT NULL, 
 preference REAL NOT NULL, 
 timestamp DATE, 
 PRIMARY KEY (user_id, item_id) 
);

CREATE INDEX taste_preferences_user_id_index ON preferences (user_id); 
CREATE INDEX taste_preferences_item_id_index ON preferences (item_id);


CREATE TABLE books ( 
 item_id BIGINT NOT NULL, 
 title VARCHAR(255) NOT NULL, 
 author VARCHAR(255) NOT NULL, 
 year INT NOT NULL, 
 publisher VARCHAR(255), 
 image_url_s VARCHAR(255) NOT NULL, 
 image_url_m VARCHAR(255) NOT NULL, 
 image_url_b VARCHAR(255) NOT NULL, 
 PRIMARY KEY (item_id) 
);

CREATE INDEX item_id_index ON preferences (item_id);


CREATE TABLE users ( 
 user_id BIGINT NOT NULL, 
 location VARCHAR(255), 
 age INT, 
 PRIMARY KEY (user_id) 
);

CREATE INDEX user_id_index ON preferences (user_id); 


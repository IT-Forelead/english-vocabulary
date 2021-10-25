CREATE TABLE "user" ("id" SERIAL PRIMARY KEY, "name" VARCHAR NOT NULL UNIQUE);
CREATE TABLE "word" ("id" SERIAL PRIMARY KEY, "value" VARCHAR NOT NULL, description VARCHAR NOT NULL);

INSERT INTO "user" VALUES (1, 'John');
INSERT INTO "user" VALUES (2, 'Doe');
INSERT INTO "user" VALUES (3, 'Mack');

INSERT INTO "word" VALUES (1, 'Size', 'Size length');
INSERT INTO "word" VALUES (2, 'Flower', 'Beautiful');
INSERT INTO "word" VALUES (3, 'Car', 'Auto');
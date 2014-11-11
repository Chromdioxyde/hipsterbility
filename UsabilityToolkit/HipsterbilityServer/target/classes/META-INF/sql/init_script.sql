/* Create view for JDBCRealm */
CREATE OR REPLACE VIEW usergroup AS SELECT u.USERNAME AS 'USERNAME', g.groups_NAME AS 'NAME' FROM USER u INNER JOIN user_realmgroup g on g.USER_ID = u.ID WHERE u.ACTIVE=1;

/* Insert groups for security roles */
INSERT INTO realmgroup VALUES('ADMIN');
INSERT INTO realmgroup VALUES('USER');

/*
  Create admin account, password is SHA256 hash and can be created e.g. by Google Guava using the following method:
  String hash = Hashing.sha256().hashString("password", Charsets.UTF_8).toString();
  TODO: change password for productive deployment!
 */
INSERT INTO user (username, EMAIL, PASSWORD, ACTIVE) VALUES ('admin', 'admin@example.com', 'ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae',1); -- pw: test123
INSERT INTO user_realmgroup (groups_NAME, USER_ID)values ('ADMIN', (SELECT u.id FROM user u WHERE u.username = 'admin' ));

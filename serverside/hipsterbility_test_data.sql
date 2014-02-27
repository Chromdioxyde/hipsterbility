/**********	CLEAR TABLES	*********/
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE hipsterbility.sessions;
TRUNCATE TABLE hipsterbility.devices;
TRUNCATE TABLE hipsterbility.apps;
TRUNCATE TABLE hipsterbility.tasks;
TRUNCATE TABLE hipsterbility.todos;
TRUNCATE TABLE hipsterbility.tasks;
TRUNCATE TABLE hipsterbility.audios;
TRUNCATE TABLE hipsterbility.captures;
TRUNCATE TABLE hipsterbility.logs;
TRUNCATE TABLE hipsterbility.users;
TRUNCATE TABLE hipsterbility.results;
TRUNCATE TABLE hipsterbility.videos;
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/**********	SET VARIABLES FOR SCALABILITY	*********/
SET @name_user1 = 'Albert', @name_user2 = 'Oliver', @name_user3 = 'Max';

/**********	INSERT USERS	*********/
SET @usr = @name_user1;
SET @pwd = 'wurstsalat';
SET @active = 1;
INSERT INTO hipsterbility.users (name, password, active) 
VALUES(@usr, @pwd, @active);

SET @usr = @name_user2;
SET @pwd = 'pommes';
SET @active = 1;
INSERT INTO hipsterbility.users (name, password, active) 
VALUES(@usr, @pwd, @active);

SET @usr = @name_user3;
SET @pwd = 'trustno1';
SET @active = 0;
INSERT INTO hipsterbility.users (name, password, active) 
VALUES(@usr, @pwd, @active);

/**********	INSERT DEVICES	*********/
SET @name_device1 = 'Nexus 5', @name_device2 = 'Galaxy Note 10.1', @name_device3 = 'Galaxy S';
SET @dtype1 = 'Phone', @dtype2 = 'Tablet';

SET @usr = @name_user1;
SET @dev = @name_device1;
SET @ver = '4.4.2';
SET @dtype = @dtype1;
INSERT INTO hipsterbility.devices (name, os_version, type, users_idusers)
VALUES (@dev, @ver, @dtype, (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr));

SET @usr = @name_user2;
SET @dev = @name_device1;
SET @ver = '4.4.2';
SET @dtype = @dtype1;
INSERT INTO hipsterbility.devices (name, os_version, type, users_idusers)
VALUES (@dev, @ver, @dtype, (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr));

SET @usr = @name_user3;
SET @dev = @name_device3;
SET @ver = '2.3.3';
SET @dtype = @dtype1;
INSERT INTO hipsterbility.devices (name, os_version, type, users_idusers)
VALUES (@dev, @ver, @dtype, (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr));

SET @usr = @name_user1;
SET @dev = @name_device2;
SET @ver = '4.4';
SET @dtype = @dtype1;
INSERT INTO hipsterbility.devices (name, os_version, type, users_idusers)
VALUES (@dev, @ver, @dtype, (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr));

/**********	INSERT APPS	*********/
SET @name_app1 = 'testApp';

INSERT INTO hipsterbility.apps (name)
VALUES (@name_app1);

/**********	INSERT SESSIONS	*********/
SET @name_session_prefix = 'Hipsterbility Test Session ';
SET @i = 0;

-- -----------------------------------------------------------------------------
SET @usr = @name_user1;
SET @act = 1;
SET @app = @name_app1;
SET @dev = @name_device1;
SET @i = @i+1;
INSERT INTO hipsterbility.sessions (name, active, users_idusers, apps_idapps, devices_iddevices)
VALUES (CONCAT(@name_session_prefix, @i)
	, @act
	, (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr)
	, (SELECT idapps FROM hipsterbility.apps WHERE name LIKE @app)
	, (SELECT iddevices FROM hipsterbility.devices WHERE name LIKE @dev AND users_idusers = (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr))
);
-- -----------------------------------------------------------------------------
SET @usr = @name_user2;
SET @act = 1;
SET @app = @name_app1;
SET @dev = @name_device1;
SET @i = @i+1;
INSERT INTO hipsterbility.sessions (name, active, users_idusers, apps_idapps, devices_iddevices)
VALUES (CONCAT(@name_session_prefix, @i)
	, @act
	, (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr)
	, (SELECT idapps FROM hipsterbility.apps WHERE name LIKE @app)
	, (SELECT iddevices FROM hipsterbility.devices WHERE name LIKE @dev AND users_idusers = (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr))
);
-- -----------------------------------------------------------------------------
SET @usr = @name_user1;
SET @act = 0;
SET @app = @name_app1;
SET @dev = @name_device2;
SET @i = @i+1;
INSERT INTO hipsterbility.sessions (name, active, users_idusers, apps_idapps, devices_iddevices)
VALUES (CONCAT(@name_session_prefix, @i)
	, @act
	, (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr)
	, (SELECT idapps FROM hipsterbility.apps WHERE name LIKE @app)
	, (SELECT iddevices FROM hipsterbility.devices WHERE name LIKE @dev AND users_idusers = (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr))
);
-- -----------------------------------------------------------------------------
SET @usr = @name_user3;
SET @act = 1;
SET @app = @name_app1;
SET @dev = @name_device3;
SET @i = @i+1;
INSERT INTO hipsterbility.sessions (name, active, users_idusers, apps_idapps, devices_iddevices)
VALUES (CONCAT(@name_session_prefix, @i)
	, @act
	, (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr)
	, (SELECT idapps FROM hipsterbility.apps WHERE name LIKE @app)
	, (SELECT iddevices FROM hipsterbility.devices WHERE name LIKE @dev AND users_idusers = (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr))
);
-- -----------------------------------------------------------------------------
SET @usr = @name_user1;
SET @act = 1;
SET @app = @name_app1;
SET @dev = @name_device1;
SET @i = @i+1;
INSERT INTO hipsterbility.sessions (name, active, users_idusers, apps_idapps, devices_iddevices)
VALUES (CONCAT(@name_session_prefix, @i)
	, @act
	, (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr)
	, (SELECT idapps FROM hipsterbility.apps WHERE name LIKE @app)
	, (SELECT iddevices FROM hipsterbility.devices WHERE name LIKE @dev AND users_idusers = (SELECT idusers FROM hipsterbility.users WHERE name LIKE @usr))
);
-- -----------------------------------------------------------------------------

/**********	INSERT TODOS	*********/
SET @name_prefix_todos = "Example Todo ";

SET @t = 0;
SET @t = @t+1;
SET @number_session = 1;
SET @act = 1;
SET @sn = CONCAT(@name_session_prefix, @number_session);
INSERT INTO hipsterbility.todos(name, description, active, sessions_idsessions)
VALUES(CONCAT(@name_prefix_todos, @t)
	, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam'
	,  @act
	, (SELECT idsessions FROM hipsterbility.sessions WHERE name LIKE @sn)
);
SET @t = @t+1;
SET @act = 0;
INSERT INTO hipsterbility.todos(name, description, active, sessions_idsessions)
VALUES(CONCAT(@name_prefix_todos, @t)
	, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam'
	,  @act
	, (SELECT idsessions FROM hipsterbility.sessions WHERE name LIKE @sn)
);
SET @t = @t+1;
SET @act = 1;
INSERT INTO hipsterbility.todos(name, description, active, sessions_idsessions)
VALUES(CONCAT(@name_prefix_todos, @t)
	, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam'
	,  @act
	, (SELECT idsessions FROM hipsterbility.sessions WHERE name LIKE @sn)
);

-- -----------------------------------------------------------------------------

SET @number_session = 2;
SET @t = @t+1;
SET @act = 0;
SET @sn = CONCAT(@name_session_prefix, @number_session);
INSERT INTO hipsterbility.todos(name, description, active, sessions_idsessions)
VALUES(CONCAT(@name_prefix_todos, @t)
	, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam'
	,  @act
	, (SELECT idsessions FROM hipsterbility.sessions WHERE name LIKE @sn)
);
SET @t = @t+1;
SET @act = 1;
INSERT INTO hipsterbility.todos(name, description, active, sessions_idsessions)
VALUES(CONCAT(@name_prefix_todos, @t)
	, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam'
	,  @act
	, (SELECT idsessions FROM hipsterbility.sessions WHERE name LIKE @sn)
);
SET @t = @t+1;
SET @act = 1;
INSERT INTO hipsterbility.todos(name, description, active, sessions_idsessions)
VALUES(CONCAT(@name_prefix_todos, @t)
	, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam'
	,  @act
	, (SELECT idsessions FROM hipsterbility.sessions WHERE name LIKE @sn)
);
-- -----------------------------------------------------------------------------

/**********	INSERT TASKS	*********/
SET @name_prefix_tasks = "Example task ";
SET @k = 0;

SET @number_todo = 1;
SET @tn = CONCAT(@name_prefix_todos, @number_todo);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);

-- -----------------------------------------------------------------------------
SET @number_todo = @number_todo+1;
SET @tn = CONCAT(@name_prefix_todos, @number_todo);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);


-- -----------------------------------------------------------------------------
SET @number_todo = @number_todo+1;
SET @tn = CONCAT(@name_prefix_todos, @number_todo);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);


-- -----------------------------------------------------------------------------
SET @number_todo = @number_todo+1;
SET @tn = CONCAT(@name_prefix_todos, @number_todo);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);
SET @k = @k+1;
INSERT INTO hipsterbility.tasks (name, done, todos_idtodos)
VALUES ( CONCAT(@name_prefix_tasks, @k)
	, 0
	, (SELECT idtodos FROM hipsterbility.todos WHERE name LIKE @tn)
);


COMMIT; -- commit at the end in case something went wront
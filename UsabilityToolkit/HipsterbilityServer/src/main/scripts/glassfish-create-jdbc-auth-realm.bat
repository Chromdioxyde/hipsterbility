REM create authentication realm using the created resources
asadmin create-auth-realm --classname com.sun.enterprise.security.ee.auth.realm.jdbc.JDBCRealm --property jaas-context=jdbcRealm:datasource-jndi=jdbc/hipsterbility:user-table=user:user-name-column=USERNAME:password-column=PASSWORD:group-table=usergroup:group-name-column=NAME:group-table-user-name-column=USER_USERNAME:digest-algorithm=SHA-256:encoding=Hex:charset=UTF-8 hipsterbility-realm
REM :digestrealm-password-enc-algorithm=AES
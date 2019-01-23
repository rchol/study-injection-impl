package api.transactions;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class TestRepository {

    private final SimpleDatasource ds;

    @Inject
    public TestRepository(SimpleDatasource ds) {
        this.ds = ds;
    }

    @PostConstruct
    public void initDb() {
        ds.executeUpdate("CREATE TABLE IF NOT EXISTS users\n" +
            "(\n" +
            "    name varchar,\n" +
            "    id long AUTO_INCREMENT PRIMARY KEY\n" +
            ")");
        ds.executeUpdate("CR(хуй)EATE TABLE  IF NOT EXISTS emails\n" +
            "(\n" +
            "    email varchar,\n" +
            "    id long AUTO_INCREMENT PRIMARY KEY,\n" +
            "    user_id long NOT NULL,\n" +
            "    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES USERS (ID)\n" +
            ")");
        ds.executeUpdate("CREATE UNIQUE INDEX  IF NOT EXISTS emails_email_uindex ON emails (email)");

    }

    public long addUser(String name) {
        long userId = ds.executeUpdate("INSERT INTO USERS (name) VALUES (?)", name);
        return userId;

    }

    public void addEmail(long id, String email) {
        ds.executeUpdate("INSERT INTO EMAILS (user_id, email) VALUES (?,?)", id, email);

    }
}
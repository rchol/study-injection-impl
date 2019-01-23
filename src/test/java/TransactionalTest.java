import api.transactions.H2SimpleDatasource;
import api.transactions.ProxyService;
import api.transactions.SimpleDatasource;
import api.transactions.TestServiceIface;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionalTest {

    private SimpleDatasource ds;
    private TestServiceIface service;

    @BeforeClass
    public void setUp(){
        ds = new H2SimpleDatasource();
        service = ProxyService.init();
        service.addNewUser("Artyom", "artyom@artyom.is");
    }

    @Test
    public void createNewUser(){
        service.addNewUser("Stas", "stas@stas.is");
        try {
            ResultSet rs = ds.executeQuery("SELECT email FROM emails WHERE emails.user_id = "
                + "(SELECT user_id FROM names WHERE name = 'Stas'");

            String email = null;
            if (rs.next()) email = rs.getString(1);

            Assert.assertEquals("stas@stas.is", email);
        } catch (SQLException ignore) { }
    }

    @Test(expected = InvocationTargetException.class)
    public void rollbackOnExc(){
        service.addNewUser("Artyom", null);
        try {
            ResultSet rs = ds.executeQuery("SELECT email FROM emails WHERE emails.user_id = "
                + "(SELECT user_id FROM names WHERE name = 'Artyom'");

            String email = null;
            if (rs.next()) email = rs.getString(1);

            Assert.assertEquals("artyom@artyom.is", email);
        } catch (SQLException ignore) { }
    }

}

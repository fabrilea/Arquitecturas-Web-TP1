package tp.integrador.factory;

import tp.integrador.dao.entidadesDao.ClienteDao;
import tp.integrador.dao.entidadesDao.FacturaDao;
import tp.integrador.dao.entidadesDao.Factura_ProductoDao;
import tp.integrador.dao.entidadesDao.ProductoDao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDAOFactory extends AbstractFactory {
    private static MySQLDAOFactory instance = null;

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String uri = "jdbc:mysql://localhost:3306/integrador1";
    public static Connection conn;

    private MySQLDAOFactory() {
    }

    /*Singleton*/
    public static synchronized MySQLDAOFactory getInstance() {
        if (instance == null) {
            instance = new MySQLDAOFactory();
        }
        return instance;
    }

    public static Connection createConnection() {
        if (conn != null) {
            return conn;
        }
        String driver = DRIVER;
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClienteDao getClienteDao() {
        return new ClienteDao(createConnection());
    }

    @Override
    public FacturaDao getFacturaDao() { return new FacturaDao(createConnection()); }

    @Override
    public ProductoDao getProducto() { return new ProductoDao(createConnection()); }

    @Override
    public Factura_ProductoDao getFacturaProducto() { return new Factura_ProductoDao(createConnection()); }

}

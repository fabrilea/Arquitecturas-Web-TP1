package tp.integrador.factory;


import tp.integrador.dao.entidadesDao.ClienteDao;
import tp.integrador.dao.entidadesDao.FacturaDao;
import tp.integrador.dao.entidadesDao.Factura_ProductoDao;
import tp.integrador.dao.entidadesDao.ProductoDao;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;
    public abstract ClienteDao getClienteDao();
    public abstract FacturaDao getFacturaDao();
    public abstract ProductoDao getProductoDao();
    public abstract Factura_ProductoDao getFacturaProductoDao();
    public static AbstractFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC : {
                return MySQLDAOFactory.getInstance();
            }
            default: {
                return null;
            }
        }
    }

}

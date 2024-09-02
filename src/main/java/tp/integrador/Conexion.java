package tp.integrador;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class Conexion {
    public void abrirConexion(String driver) {
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException |
                 NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();

            System.exit(1);
        }
    }

    public void cerrarConexion(Connection conexion) throws SQLException {
        if (conexion != null) {
            if (!conexion.isClosed()){
                conexion.close();
            }
        }
    }
}

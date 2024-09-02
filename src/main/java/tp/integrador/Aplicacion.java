package tp.integrador;

import java.sql.SQLException;

public class Aplicacion {
    public static void main(String[] args) throws SQLException {
        ConsultasCliente c1 = new ConsultasCliente("test1");

        c1.crearDB();
        System.out.println("\n");
        c1.retornarRecaudacionMayor();
        System.out.println("\n");
        c1.listarClientes();
    }
}

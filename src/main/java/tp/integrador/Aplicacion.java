package tp.integrador;

import tp.integrador.utils.HelperMySQL;

import java.sql.SQLException;

public class Aplicacion {
    public static void main(String[] args) throws Exception {
        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();
        dbMySQL.createTables();
        dbMySQL.populateDB();
        dbMySQL.closeConnection();

        ConsultasCliente c1 = new ConsultasCliente();


        System.out.println("\n");

        c1.retornarRecaudacionMayor();

        System.out.println("\n");

        c1.listarClientes();
    }
}

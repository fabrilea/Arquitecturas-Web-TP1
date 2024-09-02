package tp.integrador;

import tp.integrador.dao.entidadesDao.ClienteDao;
import tp.integrador.utils.HelperMySQL;

import java.sql.*;

public class ConsultasCliente{
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/integrador1";

    public void retornarRecaudacionMayor() {
        try {
            Connection conexion = DriverManager.getConnection(url, "root", "");
            String select = "SELECT p.nombre, (p.valor * f.cantidad) AS recaudacion " +
                    "FROM Producto p JOIN Factura_Producto f ON p.idProducto = f.idProducto " +
                    "ORDER BY recaudacion DESC " +
                    "";
            PreparedStatement ps = conexion.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombreProducto = rs.getString("nombre");
                double recaudacion = rs.getDouble("recaudacion");

                System.out.println("El producto que más ha recaudado es: " + nombreProducto);
                System.out.println("Recaudación total: " + recaudacion);
            } else {
                System.out.println("No se encontraron productos en la base de datos.");
            }
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarClientes() {
        try{
        Connection conexion = DriverManager.getConnection(url, "root", "");
        String sql = "SELECT c.nombre, SUM(p.valor) AS total_facturado\n" +
                "FROM Cliente c\n" +
                "JOIN Factura f ON f.idCliente = c.idCliente\n" +
                "JOIN Factura_Producto fp ON f.idFactura = fp.idFactura\n JOIN Producto p ON fp.idProducto = p.idProducto " +
                "GROUP BY c.idCliente, c.nombre\n" +
                "ORDER BY total_facturado DESC " +
                "";

        // Ejecutando la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

        // Procesando el resultado
        System.out.println("Clientes ordenados por facturación total:");

        while (rs.next()) {
            String nombreCliente = rs.getString("nombre");
            double totalFacturado = rs.getDouble("total_facturado");

            System.out.println(nombreCliente + " - Total facturado: " + totalFacturado);
        }
        conexion.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}

package tp.integrador;

import java.sql.*;

public class ConsultasCliente extends Conexion implements QuerysDAO{
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String nombre;
    private String url;

    public ConsultasCliente(String nombre) throws SQLException {
        this.nombre = nombre;
        this.url = "jdbc:mysql://localhost:3306/" + nombre;
    }

    @Override
    public void crearDB() {
        try {
            super.abrirConexion(driver);
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
            String db = "CREATE DATABASE " + nombre;
            PreparedStatement ps = conexion.prepareStatement(db);
            ps.execute();
            ps.close();
            super.cerrarConexion(conexion);

            ConsultasDB cdb = new ConsultasDB(nombre);
            cdb.crearCliente();
            cdb.crearFactura();
            cdb.crearProducto();
            cdb.crearFactura_Producto();
            /*
            cdb.constraintFacturaCliente();
            cdb.constraintFacturaProducto_Producto();
            cdb.constraintFacturaProducto_Factura();
            */
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void retornarRecaudacionMayor() {
        try {
            super.abrirConexion(driver);
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
            super.cerrarConexion(conexion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listarClientes() {
        try{
        super.abrirConexion(driver);
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
        super.cerrarConexion(conexion);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}

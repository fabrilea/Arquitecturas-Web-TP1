package tp.integrador.dao.entidadesDao;

import tp.integrador.dao.Dao;
import tp.integrador.entidades.Cliente;
import tp.integrador.entidades.Factura;
import tp.integrador.entidades.Factura_Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Factura_ProductoDao implements Dao<Factura_Producto> {
    private Connection conn;

    public Factura_ProductoDao(Connection conn) {
        this.conn = conn;
    }


    @Override
    public Object select(long id) {
        String query = "SELECT idFactura, idProducto, cantidad FROM Factura_Producto " +
                "WHERE idFactura = ?";
        return query;
    }


    @Override
    public void insert(Object Factura_Producto) throws SQLException {
        try {
            String query = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, (long) Factura_Producto);
            ps.setLong(2, (long) Factura_Producto);
            ps.setInt(3, (int) Factura_Producto);
            ps.execute();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(long id, Object Factura_Producto) {
        try {
            String query = "UPDATE Factura_Producto (idProducto, cantidad) VALUES (?, ?, ?)"
                    + " WHERE idFactura = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(2, (long) Factura_Producto);
            ps.setInt(3, (int) Factura_Producto);
            ps.execute();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String query = "DELETE FROM Factura_Producto WHERE idFactura = ?";
    }

    @Override
    public Object find(long id) {
        String query = "SELECT fp.idProducto, fp.idFactura, fp.cantidad " +
                "FROM Factura_Producto fp " +
                "WHERE fp.idProducto = ?";
        Factura_Producto factura_productoById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setLong(1, id); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                Long idProducto = rs.getLong("idProducto");
                int cantidad = rs.getInt("cantidad");

                // Crear una nueva instancia de Cliente con los datos recuperados de la consulta
                factura_productoById = new Factura_Producto(id, idProducto, cantidad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return factura_productoById;
    }


}


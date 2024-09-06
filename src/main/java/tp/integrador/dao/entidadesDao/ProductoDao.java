package tp.integrador.dao.entidadesDao;

import tp.integrador.dao.Dao;
import tp.integrador.dto.ClienteDto;
import tp.integrador.dto.ProductoDto;
import tp.integrador.entidades.Cliente;
import tp.integrador.entidades.Factura_Producto;
import tp.integrador.entidades.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao implements Dao<Producto> {
    private Connection conn;

    public ProductoDao(Connection conn) {
        this.conn = conn;
    }


    @Override
    public Object select(long id) {
        String query = "SELECT idProducto, nombre, valor FROM Producto " +
                "WHERE idProducto = ?";
        return query;
    }


    @Override
    public void insert(Object Producto) throws SQLException {
        try {
            String query = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, (long) Producto);
            ps.setString(2, Producto.toString());
            ps.setFloat(3, (float) Producto);
            ps.execute();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(long id, Object Producto) {
        try {
            String query = "UPDATE Producto (nombre, valor) VALUES (?, ?)"
                    + " WHERE idProducto = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(2, Producto.toString());
            ps.setFloat(3, (float) Producto);
            ps.execute();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String query = "DELETE FROM Producto WHERE idProducto = ?";
    }

    @Override
    public Object find(long id) {
        String query = "SELECT p.idProducto, p.nombre, p.valor " +
                "FROM Producto p " +
                "WHERE p.idProducto = ?";
        Producto productoById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setLong(1, id); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                float valor = rs.getFloat("valor");

                // Crear una nueva instancia de Cliente con los datos recuperados de la consulta
                productoById = new Producto(id, nombre, valor);
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

        return productoById;
    }


    public ProductoDto findProductoDTO(long id) {
        String query = "SELECT p.nombre, (p.valor * f.cantidad) AS recaudacion " +
                "FROM Producto p JOIN Factura_Producto f ON p.idProducto = f.idProducto " +
                "WHERE idProducto = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        ProductoDto productoDto = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setLong(1, id); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                float recaudacion = rs.getFloat("recaudacion");

                // Crear una nueva instancia de PersonaDTO con los datos recuperados de la consulta
                productoDto = new ProductoDto(nombre, recaudacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productoDto;
    }


    public ProductoDto productoQueMasRecaudo() {
        String query = "SELECT p.nombre, (p.valor * f.cantidad) AS recaudacion " +
                "FROM Producto p JOIN Factura_Producto f ON p.idProducto = f.idProducto " +
                "ORDER BY recaudacion DESC " +
                "LIMIT 1";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductoDto productoDto = null;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                float recaudacion = rs.getFloat("recaudacion");

                // Crear una nueva instancia de PersonaDTO con los datos recuperados de la consulta
                productoDto = new ProductoDto(nombre, recaudacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productoDto;
    }
}

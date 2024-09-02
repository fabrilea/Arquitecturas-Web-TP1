package tp.integrador.dao.entidadesDao;

import tp.integrador.dao.Dao;
import tp.integrador.entidades.Factura;
import tp.integrador.entidades.Factura_Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturaDao implements Dao<Factura> {
    private Connection conn;

    public FacturaDao(Connection conn) {
        this.conn = conn;
    }


    @Override
    public Object select(long id) {
        String query = "SELECT idFactura, idCliente FROM Factura " +
                "WHERE idFactura = ?";
        return query;
    }


    @Override
    public void insert(Object Factura) throws SQLException {
        try {
            String query = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, (long) Factura);
            ps.setLong(2, (long) Factura);
            ps.execute();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(long id, Object Factura) {
        try {
            String query = "UPDATE Factura (idCliente) VALUES (?)"
                    + " WHERE idFactura = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(2, (long) Factura);
            ps.execute();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String query = "DELETE FROM Factura WHERE idFactura = ?";
    }

    @Override
    public Object find(long id) {
        String query = "SELECT f.idFactura, f.idCliente " +
                "FROM Factura f " +
                "WHERE f.idFactura = ?";
        Factura facturaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setLong(1, id); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                Long idCliente = rs.getLong("idCliente");

                // Crear una nueva instancia de Cliente con los datos recuperados de la consulta
                facturaById = new Factura(id, idCliente);
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

        return facturaById;
    }
}

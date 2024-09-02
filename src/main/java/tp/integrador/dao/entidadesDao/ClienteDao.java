package tp.integrador.dao.entidadesDao;

import tp.integrador.dao.Dao;
import tp.integrador.entidades.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDao implements Dao<Cliente> {

    private Connection conn;

    public ClienteDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Object select(long id) {
        String query = "SELECT nombre, email FROM Cliente " +
                "WHERE idCliente = ?";
        return query;
    }

    @Override
    public void insert(Object cliente) throws SQLException {
        try {
            String query = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, (int) cliente);
            ps.setString(2, (String) cliente);
            ps.setString(3, (String) cliente);
            ps.execute();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(long id, Object cliente) {
        try {
            String query = "UPDATE Cliente (idCliente, nombre, email) VALUES (?, ?, ?) "
                    + "WHERE idCliente = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(2, (String) cliente);
            ps.setString(3, (String) cliente);
            ps.execute();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String query = "DELETE FROM Cliente WHERE idCliente = ?";
    }

    @Override
    public Object find(long id) {
        String query = "SELECT c.idCliente, c.nombre, c.email " +
                "FROM Cliente c " +
                "WHERE c.idCliente = ?";
        Cliente clienteById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setLong(1, id); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");

                // Crear una nueva instancia de Cliente con los datos recuperados de la consulta
                clienteById = new Cliente(id, nombre, email);
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

        return clienteById;
    }

}

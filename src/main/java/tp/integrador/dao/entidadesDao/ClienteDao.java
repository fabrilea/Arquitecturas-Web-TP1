package tp.integrador.dao.entidadesDao;

import tp.integrador.dao.Dao;
import tp.integrador.dto.ClienteDto;
import tp.integrador.dto.ProductoDto;
import tp.integrador.entidades.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


    public ClienteDto findClienteDTO(long id) {
        String query = "SELECT c.nombre, c.email, SUM(p.valor) AS total_facturado\n" +
                "FROM Cliente c\n" +
                "JOIN Factura f ON f.idCliente = c.idCliente\n" +
                "JOIN Factura_Producto fp ON f.idFactura = fp.idFactura\n JOIN Producto p ON fp.idProducto = p.idProducto " +
                "WHERE idCliente = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        ClienteDto clienteDto = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setLong(1, id); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                float total_facturado = rs.getFloat("total_facturado");

                // Crear una nueva instancia de PersonaDTO con los datos recuperados de la consulta
                clienteDto = new ClienteDto(nombre, email, total_facturado);
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

        return clienteDto;
    }


    public List<ClienteDto> listarClientes() {
        String query = "SELECT c.nombre, c.email, SUM(p.valor) AS total_facturado\n" +
                "FROM Cliente c\n" +
                "JOIN Factura f ON f.idCliente = c.idCliente\n" +
                "JOIN Factura_Producto fp ON f.idFactura = fp.idFactura\n JOIN Producto p ON fp.idProducto = p.idProducto " +
                "GROUP BY c.idCliente, c.nombre\n" +
                "ORDER BY total_facturado DESC " +
                "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ClienteDto> listado = null;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            // Crear una nueva instancia de Persona con los datos recuperados de la consulta
            listado = new ArrayList<ClienteDto>();
            while (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                float total_facturado  = rs.getFloat("total_facturado");
                ClienteDto clienteDto = new ClienteDto(nombre, email, total_facturado);
                listado.add(clienteDto);
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

        return listado;
    }
}

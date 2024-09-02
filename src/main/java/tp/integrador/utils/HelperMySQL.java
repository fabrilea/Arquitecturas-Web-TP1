package tp.integrador.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import tp.integrador.entidades.Cliente;
import tp.integrador.entidades.Factura;
import tp.integrador.entidades.Factura_Producto;
import tp.integrador.entidades.Producto;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelperMySQL {
    private Connection conn = null;

    public HelperMySQL() {//Constructor
        String driver = "com.mysql.cj.jdbc.Driver";
        String uri = "jdbc:mysql://localhost:3306/integrador1";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dropTables() throws SQLException {
        String dropCliente = "DROP TABLE IF EXISTS Cliente";
        this.conn.prepareStatement(dropCliente).execute();

        String dropFactura = "DROP TABLE IF EXISTS Factura";
        this.conn.prepareStatement(dropFactura).execute();

        String dropFactura_Producto = "DROP TABLE IF EXISTS Factura_Producto";
        this.conn.prepareStatement(dropFactura_Producto).execute();

        String dropProducto = "DROP TABLE IF EXISTS Producto";
        this.conn.prepareStatement(dropProducto).execute();
    }

    public void createTables() throws SQLException {
        String tableCliente = "CREATE TABLE IF NOT EXISTS Cliente( " +
                "idCliente int  NOT NULL, " +
                "nombre varchar(500)  NOT NULL, " +
                "email varchar(500)  NOT NULL)";
        this.conn.prepareStatement(tableCliente).execute();

        String tableFactura = "CREATE TABLE IF NOT EXISTS Factura (\n" +
                "    idFactura int  NOT NULL,\n" +
                "    idCliente int  NULL\n" +
                ")";
        this.conn.prepareStatement(tableFactura).execute();

        String tableFactura_Producto = "CREATE TABLE IF NOT EXISTS Factura_Producto (\n" +
                "    idFactura int  NOT NULL,\n" +
                "    idProducto int  NOT NULL,\n" +
                "    cantidad int  NOT NULL\n" +
                ");\n";
        this.conn.prepareStatement(tableFactura_Producto).execute();

        String tableProducto = "CREATE TABLE IF NOT EXISTS Producto (\n" +
                "    idProducto int  NOT NULL,\n" +
                "    nombre varchar(45)  NOT NULL,\n" +
                "    valor float  NOT NULL" +
                ")";
        this.conn.prepareStatement(tableProducto).execute();
    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "src\\main\\resources\\" + archivo;
        Reader in = new FileReader(path);
        String[] header = {};  // Puedes configurar tu encabezado personalizado aquí si es necesario
        CSVParser csvParser = CSVFormat.EXCEL.withHeader(header).parse(in);

        Iterable<CSVRecord> records = csvParser.getRecords();
        return records;
    }

    public void populateDB() throws Exception {
        try {
            System.out.println("Populating DB...");
            for (CSVRecord row : getData("csv/clientes.csv")) {
                if (row.size() >= 3) { // Verificar que hay al menos 3 campos en el CSVRecord
                    String idString = row.get(0);
                    String nombreString = row.get(1);
                    String emailString = row.get(2);
                    if (!idString.isEmpty() && !nombreString.isEmpty() && !emailString.isEmpty()) {
                        try {
                            long idCliente = Long.parseLong(idString);
                            String nombre = nombreString;
                            String email = emailString;
                            Cliente cliente = new Cliente(idCliente, nombre, email);
                            insertCliente(cliente, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de Cliente: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Clientes insertados");

            for (CSVRecord row : getData("csv/facturas.csv")) {
                if (row.size() >= 2) { // Verificar que hay al menos 4 campos en el CSVRecord
                    String idString = row.get(0);
                    String numeroString = row.get(1);
                    if (!idString.isEmpty() && !numeroString.isEmpty()) {
                        try {
                            long idFactura = Long.parseLong(idString);
                            long idCliente = Long.parseLong(numeroString);
                            Factura factura = new Factura(idFactura, idCliente);
                            insertFactura(factura, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de Cliente: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("Facturas insertados");

            for (CSVRecord row : getData("csv/productos.csv")) {
                if (row.size() >= 3) {
                    String idString = row.get(0);
                    String nombreString = row.get(1);
                    String valorString = row.get(2);
                    if (!idString.isEmpty() && !nombreString.isEmpty() && !valorString.isEmpty()) {
                        try {
                            long idProducto = Long.parseLong(idString);
                            String nombre = nombreString;
                            float valor = Float.parseFloat(valorString);
                            Producto producto = new Producto(idProducto, nombre, valor);
                            insertProducto(producto, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de Cliente: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("Productos insertados");

            for (CSVRecord row : getData("csv/facturas-productos.csv")) {
                if (row.size() >= 3) {
                    String idString = row.get(0);
                    String idString2 = row.get(1);
                    String cantidadString = row.get(2);
                    if (!idString.isEmpty() && !idString2.isEmpty() && !cantidadString.isEmpty()) {
                        try {
                            long idFactura = Long.parseLong(idString);
                            long idProducto = Long.parseLong(idString2);
                            int cantidad = Integer.parseInt(cantidadString);
                            Factura_Producto factura_producto = new Factura_Producto(idFactura, idProducto, cantidad);
                            insertFactura_Producto(factura_producto, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de Cliente: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("Facturas y Productos insertados");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int insertCliente(Cliente cliente, Connection conn) throws Exception {

        String insert = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setLong(1,cliente.getidCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3,cliente.getEmail());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private int insertFactura(Factura factura, Connection conn) throws Exception {

        String insert = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setLong(1,factura.getIdFactura());
            ps.setLong(2, factura.getIdCliente());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private int insertProducto(Producto producto, Connection conn) throws Exception {

        String insert = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setLong(1, producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setFloat(3, producto.getValor());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private int insertFactura_Producto(Factura_Producto factura_producto, Connection conn) throws Exception {

        String insert = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setLong(1,factura_producto.getIdFactura());
            ps.setLong(2, factura_producto.getIdProducto());
            ps.setInt(3, factura_producto.getCantidad());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private void closePsAndCommit(Connection conn, PreparedStatement ps) {
        if (conn != null){
            try {
                ps.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



package tp.integrador;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class ConsultasDB extends Conexion implements TablasDAO{
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String nombre;
    private String url;


    public ConsultasDB(String nombre) throws SQLException {
        this.nombre = nombre;
        this.url = "jdbc:mysql://localhost:3306/" + nombre;
    }


    @Override
    public void crearCliente() {
        try {
            Connection conexion = DriverManager.getConnection(url, "root", "");
            super.abrirConexion(driver);
            String table = "CREATE TABLE Cliente(" +
                    "idCliente int  NOT NULL," +
                    "nombre varchar(500)  NOT NULL," +
                    "email varchar(500)  NOT NULL," +
                    "CONSTRAINT Cliente_pk PRIMARY KEY (idCliente))";
            PreparedStatement ps = conexion.prepareStatement(table);
            ps.execute();
            ps.close();
            agregarClientes(conexion);
            super.cerrarConexion(conexion);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void agregarClientes(Connection conn) {
        try(
                FileReader reader = new FileReader("src/main/java/tp/integrador/csv/clientes.csv");
                CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

            String query = "INSERT INTO Cliente (idCliente, nombre, email) VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            for (CSVRecord row : csvParser) {
                pstmt.setInt(1, Integer.parseInt(row.get("idCliente")));
                pstmt.setString(2, row.get("idCliente"));
                pstmt.setString(3, row.get("email"));
                pstmt.executeUpdate();
            }

            System.out.println("Datos cargados correctamente.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void crearFactura() {
        try {
            Connection conexion = DriverManager.getConnection(url, "root", "");
            super.abrirConexion(driver);
            String table = "CREATE TABLE Factura (" +
                    "idFactura int  NOT NULL," +
                    "idCliente int  NULL)";
            PreparedStatement ps = conexion.prepareStatement(table);
            ps.execute();
            ps.close();
            agregarFactura(conexion);
            super.cerrarConexion(conexion);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void agregarFactura(Connection conn) {
        try(
                FileReader reader = new FileReader("src/main/java/tp/integrador/csv/facturas.csv");
                CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

            String query = "INSERT INTO Factura (idFactura, idCliente) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            for (CSVRecord row : csvParser) {
                pstmt.setInt(1, Integer.parseInt(row.get("idFactura")));
                pstmt.setInt(2, Integer.parseInt(row.get("idCliente")));
                pstmt.executeUpdate();
            }

            System.out.println("Datos cargados correctamente.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void crearProducto() {
        try {
            Connection conexion = DriverManager.getConnection(url, "root", "");
            super.abrirConexion(driver);
            String table = "CREATE TABLE Producto (" +
                    "idProducto int  NOT NULL," +
                    "nombre varchar(45)  NOT NULL," +
                    "valor float  NOT NULL)";
            PreparedStatement ps = conexion.prepareStatement(table);
            ps.execute();
            ps.close();
            agregarProducto(conexion);
            super.cerrarConexion(conexion);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void agregarProducto(Connection conn) {
        try(
            FileReader reader = new FileReader("src/main/java/tp/integrador/csv/productos.csv");
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

            String query = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            for (CSVRecord row : csvParser) {
                pstmt.setInt(1, Integer.parseInt(row.get("idProducto")));
                pstmt.setString(2, row.get("nombre"));
                pstmt.setFloat(3, Float.parseFloat(row.get("valor")));
                pstmt.executeUpdate();
            }

            System.out.println("Datos cargados correctamente.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void crearFactura_Producto() {
        try {
            Connection conexion = DriverManager.getConnection(url, "root", "");
            super.abrirConexion(driver);
            String table = "CREATE TABLE Factura_Producto (" +
                    "idFactura int  NOT NULL," +
                    "idProducto int  NOT NULL," +
                    "cantidad int  NOT NULL)";
            PreparedStatement ps = conexion.prepareStatement(table);
            ps.execute();
            ps.close();
            agregarFactura_Producto(conexion);
            super.cerrarConexion(conexion);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void agregarFactura_Producto(Connection conn) {
        try(
            FileReader reader = new FileReader("src/main/java/tp/integrador/csv/facturas-productos.csv");
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

                String query = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);

                for (CSVRecord row : csvParser) {
                    pstmt.setInt(1, Integer.parseInt(row.get("idFactura")));
                    pstmt.setInt(2, Integer.parseInt(row.get("idProducto")));
                    pstmt.setInt(3, Integer.parseInt(row.get("cantidad")));
                    pstmt.executeUpdate();
                }

                System.out.println("Datos cargados correctamente.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    }
/*
    @Override
    public void constraintFacturaCliente() {
        try {
            Connection conexion = DriverManager.getConnection(url, "root", "");
            super.abrirConexion(driver);
            String constraint = "ALTER TABLE Factura ADD CONSTRAINT Factura_Cliente\n" +
                    "    FOREIGN KEY (idCliente)\n" +
                    "    REFERENCES Cliente (idCliente)  \n";
            PreparedStatement ps = conexion.prepareStatement(constraint);
            ps.execute();
            ps.close();
            super.cerrarConexion(conexion);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void constraintFacturaProducto_Producto() {
        try {
            Connection conexion = DriverManager.getConnection(url, "root", "");
            super.abrirConexion(driver);
            String constraint = "ALTER TABLE Factura_Producto ADD CONSTRAINT FacturaProducto_Producto\n" +
                    "    FOREIGN KEY (idProducto)\n" +
                    "    REFERENCES Producto (idProducto)  \n";
            PreparedStatement ps = conexion.prepareStatement(constraint);
            ps.execute();
            ps.close();
            super.cerrarConexion(conexion);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void constraintFacturaProducto_Factura() {
        try {
            Connection conexion = DriverManager.getConnection(url, "root", "");
            super.abrirConexion(driver);
            String constraint = "ALTER TABLE Factura_Producto ADD CONSTRAINT FacturaProducto_Factura\n" +
                    "    FOREIGN KEY (idFactura)\n" +
                    "    REFERENCES Factura (idFactura)  \n";
            PreparedStatement ps = conexion.prepareStatement(constraint);
            ps.execute();
            ps.close();
            super.cerrarConexion(conexion);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    */


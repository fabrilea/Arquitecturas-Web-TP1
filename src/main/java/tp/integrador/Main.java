package tp.integrador;

import tp.integrador.dao.entidadesDao.ClienteDao;
import tp.integrador.dao.entidadesDao.FacturaDao;
import tp.integrador.dao.entidadesDao.Factura_ProductoDao;
import tp.integrador.dao.entidadesDao.ProductoDao;
import tp.integrador.dto.ClienteDto;
import tp.integrador.dto.ProductoDto;
import tp.integrador.entidades.Cliente;
import tp.integrador.factory.AbstractFactory;
import tp.integrador.utils.HelperMySQL;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();
        dbMySQL.createTables();
        dbMySQL.populateDB();
        dbMySQL.closeConnection();


        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);
        System.out.println();
        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");
        System.out.println();
        ClienteDao cliente = chosenFactory.getClienteDao();
        ProductoDao producto = chosenFactory.getProductoDao();
        FacturaDao factura = chosenFactory.getFacturaDao();
        Factura_ProductoDao factura_producto = chosenFactory.getFacturaProductoDao();


        System.out.println("Busco una Persona por id: ");
        Cliente clienteById = (Cliente) cliente.find(2);
        System.out.println(clienteById);
        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");
        System.out.println();
        System.out.println("Lista de Clientes: ");
        List<ClienteDto> listadoClientes = cliente.listarClientes();
        for (ClienteDto clienteDto : listadoClientes) {
            System.out.println(clienteDto);
        }
        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");
        System.out.println();
        System.out.println("Producto que más recaudó: ");
        ProductoDto productoQueMasRecaudo = producto.productoQueMasRecaudo();
        System.out.println(productoQueMasRecaudo);

        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");

    }
}

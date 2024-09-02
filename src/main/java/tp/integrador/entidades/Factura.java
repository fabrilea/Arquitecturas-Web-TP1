package tp.integrador.entidades;

public class Factura {
    private long idFactura;
    private long idCliente;

    public Factura(long idFactura, long cliente) {
        this.idFactura = idFactura;
        this.idCliente = cliente;
    }

    public long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Factura{" + "idFactura=" + idFactura + ", idCliente=" + idCliente + '}';
    }
}

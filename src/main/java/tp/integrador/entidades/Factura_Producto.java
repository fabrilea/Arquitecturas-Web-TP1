package tp.integrador.entidades;

public class Factura_Producto {
    private long idFactura;
    private long idProducto;
    private int cantidad;

    public Factura_Producto(long idFactura, long idProducto, int cantidad) {
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Factura_Producto{" + "idFactura=" + idFactura + ", id_Producto=" + idProducto + ", cantidad=" + cantidad + '}';
    }
}

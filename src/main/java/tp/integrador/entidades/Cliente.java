package tp.integrador.entidades;

public class Cliente {

    private long idCliente;
    private String nombre;
    private String email;

    public Cliente(long idCliente, String nombre, String email) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.email = email;
    }


    public long getidCliente() {
        return idCliente;
    }

    public void setidCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", nombre=" + nombre + ", email=" + email + '}';
    }
}

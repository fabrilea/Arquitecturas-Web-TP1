package tp.integrador.dto;

public class ClienteDto {
    private String nombre;
    private String email;
    private float total_facturado;

    public ClienteDto() {
    }

    public ClienteDto(String nombre, String email, float total_facturado) {
        this.nombre = nombre;
        this.email = email;
        this.total_facturado = total_facturado;
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

    public float getTotal_facturado() {
        return total_facturado;
    }

    public void setTotal_facturado(float total_facturado) {
        this.total_facturado = total_facturado;
    }

    @Override
    public String toString() {
        return "ClienteDto{" + "nombre=" + nombre + ", email=" + email + ", total_facturado=" + total_facturado + '}';
    }
}

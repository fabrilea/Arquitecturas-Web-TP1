package tp.integrador.dto;

public class ProductoDto {
    private String nombre;
    private float recaudacion;

    public ProductoDto() {
    }

    public ProductoDto(String nombre, float recaudacion) {
        this.nombre = nombre;
        this.recaudacion = recaudacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(float recaudacion) {
        this.recaudacion = recaudacion;
    }

    @Override
    public String toString() {
        return "Producto{" + "nombre=" + nombre + ", recaudacion=" + recaudacion + '}';
    }
}

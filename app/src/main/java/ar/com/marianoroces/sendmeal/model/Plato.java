package ar.com.marianoroces.sendmeal.model;

public class Plato {

    private String titulo;
    private String descripcion;
    private double precio;
    private int calorias;

    public Plato(String titulo, String descripcion, double precio, int calorias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }
}

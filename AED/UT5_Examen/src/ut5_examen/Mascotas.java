package ut5_examen;

public class Mascotas {
    String nombre;
    String chip;
    String raza;
    String telefono;
    String veterinario;

    public Mascotas() {}

    public Mascotas(String nombre, String chip, String raza, String telefono, String veterinario) {
        this.nombre = nombre;
        this.chip = chip;
        this.raza = raza;
        this.telefono = telefono;
        this.veterinario = veterinario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    @Override
    public String toString() {
        return "Mascotas{" +
                "nombre='" + nombre + '\'' +
                ", chip='" + chip + '\'' +
                ", raza='" + raza + '\'' +
                ", telefono='" + telefono + '\'' +
                ", veterinario='" + veterinario + '\'' +
                '}';
    }
}

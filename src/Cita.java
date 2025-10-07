import java.time.LocalDateTime;

public class Cita {
    // Atributos privados
    private int id;
    private LocalDateTime fechaYHora;
    private String motivo;
    private int idDoctor;
    private int idPaciente;

    // Constructor (este ya lo tenías bien)
    public Cita(int id, LocalDateTime fechaYHora, String motivo, int idDoctor, int idPaciente) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.motivo = motivo;
        this.idDoctor = idDoctor;
        this.idPaciente = idPaciente;
    }

    // --- GETTERS (ESTA ES LA PARTE QUE FALTABA) ---
    public int getId() {
        return id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public int getIdPaciente() {
        return idPaciente;
    }
}
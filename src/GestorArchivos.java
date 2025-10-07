import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;

public class GestorArchivos {

    public void guardarDoctores(ArrayList<Doctor> listaDoctores) {
        File archivo = new File("db/doctores.csv");
        try (PrintWriter out = new PrintWriter(new FileWriter(archivo))) {
            // Escribimos una línea por cada doctor en la lista
            for (Doctor doctor : listaDoctores) {
                out.println(doctor.getId() + "," + doctor.getNombre() + "," + doctor.getEspecialidad());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los doctores: " + e.getMessage());
        }
    }

    public ArrayList<Doctor> cargarDoctores() {
        ArrayList<Doctor> listaDoctores = new ArrayList<>();
        File archivo = new File("db/doctores.csv");

        // Si el archivo no existe devuelve la lista vacía
        if (!archivo.exists()) {
            return listaDoctores;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Separa los datos por la coma
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    // Convierte el ID de String a int
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String especialidad = datos[2];
                    // Crea el objeto y lo añade a la lista
                    listaDoctores.add(new Doctor(id, nombre, especialidad));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar los doctores: " + e.getMessage());
        }
        return listaDoctores;
    }

    public void guardarPacientes(ArrayList<Paciente> listaPacientes) {
        File archivo = new File("db/pacientes.csv");
        try (PrintWriter out = new PrintWriter(new FileWriter(archivo))) {
            for (Paciente paciente : listaPacientes) {
                out.println(paciente.getId() + "," + paciente.getNombre());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los pacientes: " + e.getMessage());
        }
    }

    public ArrayList<Paciente> cargarPacientes() {
        ArrayList<Paciente> listaPacientes = new ArrayList<>();
        File archivo = new File("db/pacientes.csv");
        if (!archivo.exists()) return listaPacientes;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    listaPacientes.add(new Paciente(id, nombre));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar los pacientes: " + e.getMessage());
        }
        return listaPacientes;
    }

    public void guardarCitas(ArrayList<Cita> listaCitas) {
        File archivo = new File("db/citas.csv");
        try (PrintWriter out = new PrintWriter(new FileWriter(archivo))) {
            for (Cita cita : listaCitas) {
                out.println(cita.getId() + "," + cita.getFechaYHora() + "," + cita.getMotivo() + "," + cita.getIdDoctor() + "," + cita.getIdPaciente());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las citas: " + e.getMessage());
        }
    }

    public ArrayList<Cita> cargarCitas() {
        ArrayList<Cita> listaCitas = new ArrayList<>();
        File archivo = new File("db/citas.csv");
        if (!archivo.exists()) return listaCitas;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    int id = Integer.parseInt(datos[0]);
                    LocalDateTime fecha = LocalDateTime.parse(datos[1]); // Convertir el texto a fecha
                    String motivo = datos[2];
                    int idDoctor = Integer.parseInt(datos[3]);
                    int idPaciente = Integer.parseInt(datos[4]);
                    listaCitas.add(new Cita(id, fecha, motivo, idDoctor, idPaciente));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las citas: " + e.getMessage());
        }
        return listaCitas;
    }

}
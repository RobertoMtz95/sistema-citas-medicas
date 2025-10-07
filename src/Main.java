/**
 * @author Oscar Roberto Rivera Martinez
 * @version 1.0
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    static GestorArchivos gestor = new GestorArchivos();

    // Listas
    static ArrayList<Doctor> listaDoctores = new ArrayList<>();
    static ArrayList<Paciente> listaPacientes = new ArrayList<>();
    static ArrayList<Cita> listaCitas = new ArrayList<>();

    // IDs
    static int contadorIdDoctor = 1;
    static int contadorIdPaciente = 1;
    static int contadorIdCita = 1;

    public static void main(String[] args) {
        // Al iniciar, se leen los archivos .csv para recuperar la información guardada.
        listaDoctores = gestor.cargarDoctores();
        listaPacientes = gestor.cargarPacientes();
        listaCitas = gestor.cargarCitas();

        // Se ajustan los contadores para evitar IDs duplicados.
        if (!listaDoctores.isEmpty()) {
            contadorIdDoctor = listaDoctores.get(listaDoctores.size() - 1).getId() + 1;
        }
        if (!listaPacientes.isEmpty()) {
            contadorIdPaciente = listaPacientes.get(listaPacientes.size() - 1).getId() + 1;
        }
        if (!listaCitas.isEmpty()) {
            contadorIdCita = listaCitas.get(listaCitas.size() - 1).getId() + 1;
        }

        System.out.println("Bienvenido al sistema de citas medicas");

        // Validamos el usuario
        if (validarCredenciales()) {
            int opcion = 0;
            while (opcion != 7) {
                opcion = mostrarMenu();
            }
        }

        System.out.println("Gracias por usar el sistema. ¡Adiós!");
    }

    public static boolean validarCredenciales(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        if (user.equals("admin") && pass.equals("123")) {
            System.out.println("Acceso correcto.");
            return true;
        } else {
            System.out.println("Credenciales incorrectas.");
            return false;
        }
    }

    public static int mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Dar de alta doctor");
        System.out.println("2. Dar de alta paciente");
        System.out.println("3. Dar de alta cita");
        System.out.println("4. Ver doctores");
        System.out.println("5. Ver pacientes");
        System.out.println("6. Ver citas");
        System.out.println("7. Salir");
        System.out.print("Elige una opción: ");

        int opcionSeleccionada = scanner.nextInt();

        switch (opcionSeleccionada) {
            case 1:
                altaDoctor();
                break;
            case 2:
                altaPaciente();
                break;
            case 3:
                altaCita();
                break;
            case 4:
                verDoctores();
                break;
            case 5:
                verPacientes();
                break;
            case 6:
                verCitas();
                break;
            case 7:
                break;
            default:
                System.out.println("Opción no válida. Inténtalo de nuevo.");
        }
        return opcionSeleccionada;
    }

    public static void altaDoctor(){
        // Datos
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el nombre del doctor: ");
        String nombre = scanner.nextLine();
        System.out.print("Introduce la especialidad: ");
        String especialidad = scanner.nextLine();

        Doctor nuevoDoctor = new Doctor(contadorIdDoctor, nombre, especialidad);
        listaDoctores.add(nuevoDoctor);
        contadorIdDoctor++;

        System.out.println("¡Doctor registrado con éxito!");

        // Guardamos los cambios en el archivo .csv
        gestor.guardarDoctores(listaDoctores);
    }

    public static void verDoctores(){
        System.out.println("\n--- LISTA DE DOCTORES ---");
        if (listaDoctores.isEmpty()) {
            System.out.println("No hay doctores registrados.");
        } else {
            for (Doctor doctor : listaDoctores) {
                System.out.println("ID: " + doctor.getId() + " | Nombre: " + doctor.getNombre() + " | Especialidad: " + doctor.getEspecialidad());
            }
        }
    }

    public static void altaPaciente(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el nombre del paciente: ");
        String nombre = scanner.nextLine();

        Paciente nuevoPaciente = new Paciente(contadorIdPaciente, nombre);
        listaPacientes.add(nuevoPaciente);
        contadorIdPaciente++;

        System.out.println("¡Paciente registrado con éxito!");

        gestor.guardarPacientes(listaPacientes);
    }

    public static void verPacientes() {
        System.out.println("\n--- LISTA DE PACIENTES ---");
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            for (Paciente paciente : listaPacientes) {
                System.out.println("ID: " + paciente.getId() + " | Nombre: " + paciente.getNombre());
            }
        }
    }

    public static void altaCita(){
        if (listaDoctores.isEmpty() || listaPacientes.isEmpty()) {
            System.out.println("Debe haber al menos un doctor y un paciente registrados para crear una cita.");
            return;
        }

        System.out.println("Seleccione un doctor de la lista:");
        verDoctores();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Escriba el ID del doctor: ");
        int idDoctorSeleccionado = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer del scanner.

        System.out.println("\nSeleccione un paciente de la lista:");
        verPacientes();
        System.out.print("Escriba el ID del paciente: ");
        int idPacienteSeleccionado = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Escriba el motivo de la cita: ");
        String motivo = scanner.nextLine();

        // Se crea la fecha y hora actual para la cita.
        LocalDateTime fechaYHora = LocalDateTime.now();

        Cita nuevaCita = new Cita(contadorIdCita, fechaYHora, motivo, idDoctorSeleccionado, idPacienteSeleccionado);
        listaCitas.add(nuevaCita);
        contadorIdCita++;
        System.out.println("¡Cita registrada con éxito!");

        gestor.guardarCitas(listaCitas);
    }

    public static void verCitas(){
        System.out.println("\n--- LISTA DE CITAS ---");
        if (listaCitas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }

        // Para mostrar la fecha y hora de una forma más legible.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Cita cita : listaCitas) {
            String nombreDoctor = "No encontrado";
            String nombrePaciente = "No encontrado";

            for (Doctor doc : listaDoctores) {
                if (doc.getId() == cita.getIdDoctor()) {
                    nombreDoctor = doc.getNombre();
                    break;
                }
            }

            for (Paciente pac : listaPacientes) {
                if (pac.getId() == cita.getIdPaciente()) {
                    nombrePaciente = pac.getNombre();
                    break;
                }
            }

            System.out.println("ID Cita: " + cita.getId() + " | Fecha: " + cita.getFechaYHora().format(formatter) + " | Motivo: " + cita.getMotivo());
            System.out.println("  > Doctor: " + nombreDoctor + " | Paciente: " + nombrePaciente);
        }
    }
}
package main;

import u3.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TournamentApp {

    public static void main(String[] args) {
        Tournament tournament = new Tournament();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sorteador aleatorio de torneos (octavos, cuartos, semifinal y final).");
        System.out.print("¿Deseas introducir equipos manualmente? (s/n): ");
        String resp = scanner.nextLine().trim().toLowerCase();

        List<Team> teams;
        if (resp.equals("s") || resp.equals("si") || resp.equals("y") || resp.equals("yes")) {
            teams = readTeamsFromConsole(scanner);
        } else {
            teams = defaultTeams16();
            System.out.println("Usando lista de ejemplo de 16 equipos:");
            teams.forEach(t -> System.out.println(" - " + t));
        }

        int stageNumber = 1;
        List<Team> current = new ArrayList<>(teams);

        while (current.size() > 1) {
            System.out.println();
            System.out.println("========== Etapa " + stageNumber + " (equipos: " + current.size() + ") ==========");
            List<Match> matches = tournament.generatePairings(current);
            for (int i = 0; i < matches.size(); i++) {
                System.out.printf("Partido %d: %s%n", i + 1, matches.get(i));
            }

            System.out.print("Presiona ENTER para simular resultados de esta etapa...");
            scanner.nextLine();

            List<Team> winners = tournament.simulateStage(matches);
            System.out.println("Ganadores que avanzan:");
            for (Team w : winners) {
                System.out.println(" - " + w);
            }

            current = winners;
            stageNumber++;
        }

        if (current.size() == 1) {
            System.out.println();
            System.out.println("***** CAMPEÓN: " + current.get(0).getName() + " *****");
        } else {
            System.out.println("No hay equipos suficientes para determinar un campeón.");
        }

        scanner.close();
    }

    private static List<Team> readTeamsFromConsole(Scanner scanner) {
        List<Team> teams = new ArrayList<>();
        System.out.print("¿Cuántos equipos quieres introducir? ");
        int n = 0;
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                n = Integer.parseInt(line);
                if (n <= 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.print("Por favor introduce un número válido de equipos: ");
            }
        }
        System.out.println("Introduce los nombres de los equipos (uno por línea):");
        for (int i = 0; i < n; i++) {
            System.out.printf("Equipo %d: ", i + 1);
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Nombre vacío, usa 'Equipo#" + (i + 1) + "'.");
                name = "Equipo#" + (i + 1);
            }
            teams.add(new Team(name));
        }
        return teams;
    }

    private static List<Team> defaultTeams16() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team("Atlético Norte"));
        teams.add(new Team("Deportivo Central"));
        teams.add(new Team("Unión Este"));
        teams.add(new Team("Real Sur"));
        teams.add(new Team("C.F. Bravo"));
        teams.add(new Team("Club Olímpico"));
        teams.add(new Team("Sporting Verde"));
        teams.add(new Team("A. C. Azul"));
        teams.add(new Team("Huracán F.C."));
        teams.add(new Team("Rangers United"));
        teams.add(new Team("Libertad"));
        teams.add(new Team("Independiente"));
        teams.add(new Team("Tigres"));
        teams.add(new Team("Leones"));
        teams.add(new Team("Águilas"));
        teams.add(new Team("Dragones"));
        return teams;
    }
}
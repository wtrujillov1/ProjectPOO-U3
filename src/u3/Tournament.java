package u3;

import java.util.*;

public class Tournament {

    private final Random random = new Random();

    /**
     * Genera emparejamientos aleatorios para la lista de equipos dada.
     * Si hay un número impar, el último equipo tras barajar obtiene un bye.
     * Se asegura que no haya partidos donde un equipo se enfrente a sí mismo.
     *
     * @param teams lista de equipos (se hará una copia interna)
     * @return lista de matches para la etapa
     */
    public List<Match> generatePairings(List<Team> teams) {
        if (teams == null) throw new IllegalArgumentException("Lista de equipos es null.");
        // Hacer copia y manejar nombres duplicados si existen
        List<Team> copy = new ArrayList<>(teams);
        ensureUniqueNames(copy);

        Collections.shuffle(copy, random);

        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < copy.size(); i += 2) {
            Team home = copy.get(i);
            Team away = (i + 1 < copy.size()) ? copy.get(i + 1) : null;
            // safety check: if away equals home (debería haberse evitado), re-shuffle up to un par de intentos
            if (away != null && home.equals(away)) {
                boolean fixed = tryFixSelfMatch(copy, i);
                if (!fixed) {
                    throw new IllegalStateException("No se pudo evitar un emparejamiento consigo mismo tras varios intentos.");
                }
                home = copy.get(i);
                away = (i + 1 < copy.size()) ? copy.get(i + 1) : null;
            }
            matches.add(new Match(home, away));
        }

        // Asegurar unicidad de partidos dentro de la etapa (no repetidos)
        // Con la generación actual no es posible tener duplicados, pero hacemos una comprobación por seguridad.
        Set<String> seen = new HashSet<>();
        for (Match m : matches) {
            String key = m.unorderedPairKey();
            if (seen.contains(key)) {
                throw new IllegalStateException("Emparejamiento duplicado detectado en la misma etapa: " + key);
            }
            seen.add(key);
        }

        return matches;
    }

    /**
     * Simula los resultados de una lista de partidos de forma aleatoria.
     * Para partidos con bye, el único equipo avanza.
     *
     * @param matches lista de partidos
     * @return lista de equipos ganadores
     */
    public List<Team> simulateStage(List<Match> matches) {
        List<Team> winners = new ArrayList<>();
        for (Match m : matches) {
            if (m.isBye()) {
                m.setWinner(m.getHome());
            } else {
                // elegir ganador aleatoriamente entre home y away
                Team winner = random.nextBoolean() ? m.getHome() : m.getAway();
                m.setWinner(winner);
            }
            winners.add(m.getWinner());
        }
        return winners;
    }

    // Intenta arreglar un self-match intercambiando con otra posición
    private boolean tryFixSelfMatch(List<Team> list, int index) {
        int attempts = 0;
        int size = list.size();
        while (attempts < 10) {
            int swapWith = random.nextInt(size);
            if (swapWith == index || swapWith == index + 1) {
                attempts++;
                continue;
            }
            Collections.swap(list, index + 1, swapWith);
            if (!list.get(index).equals(list.get(index + 1))) {
                return true;
            }
            attempts++;
        }
        return false;
    }

    // Si hay nombres duplicados, los hace únicos añadiendo sufijos y muestra aviso
    private void ensureUniqueNames(List<Team> list) {
        Map<String, Integer> counts = new HashMap<>();
        boolean hadDuplicates = false;
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).getName();
            String key = name.toLowerCase();
            int c = counts.getOrDefault(key, 0);
            if (c > 0) {
                // renombrar
                hadDuplicates = true;
                String newName = name + " (" + (c + 1) + ")";
                list.set(i, new Team(newName));
            }
            counts.put(key, c + 1);
        }
        if (hadDuplicates) {
            System.out.println("Se han detectado nombres duplicados. Algunos equipos han sido renombrados para mantener unicidad.");
        }
    }
}

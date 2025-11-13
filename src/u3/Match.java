package u3;

//import java.util.Objects;

public class Match {
    private final Team home;
    private final Team away; // can be null for bye
    private Team winner; // null until decided

    public Match(Team home, Team away) {
        if (home == null) throw new IllegalArgumentException("Equipo local no puede ser null.");
        if (away != null && home.equals(away))
            throw new IllegalArgumentException("Un equipo no puede jugar consigo mismo: " + home);
        this.home = home;
        this.away = away;
    }

    public Team getHome() {
        return home;
    }

    public Team getAway() {
        return away;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        if (winner == null) throw new IllegalArgumentException("Ganador no puede ser null.");
        if (!winner.equals(home) && !(away != null && winner.equals(away)))
            throw new IllegalArgumentException("El ganador debe ser uno de los participantes del partido.");
        this.winner = winner;
    }

    public boolean isBye() {
        return away == null;
    }

    @Override
    public String toString() {
        if (isBye()) {
            return String.format("%s (bye)", home);
        } else {
            return String.format("%s vs %s", home, away);
        }
    }

    // For internal equality checks (unordered pair)
    public String unorderedPairKey() {
        if (away == null) return home.getName();
        String a = home.getName();
        String b = away.getName();
        if (a.compareToIgnoreCase(b) <= 0) {
            return a + "||" + b;
        } else {
            return b + "||" + a;
        }
    }
}
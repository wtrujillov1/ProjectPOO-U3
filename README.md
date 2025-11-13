# Sorteador aleatorio de torneos (Java)

Aplicación Java que organiza aleatoriamente los partidos de un torneo de eliminación directa: octavos (si hay 16 equipos), cuartos, semifinal y final. Cumple las siguientes reglas:
- Ningún equipo repite un partido en la misma etapa (los emparejamientos se generan a partir de la lista de equipos sin duplicados).
- Un equipo no puede jugar contra sí mismo.
- Si hay un número impar de equipos en una fase, se asigna un bye (pasa automáticamente a la siguiente ronda).

Cómo compilar y ejecutar
1. Coloca todos los archivos `.java` en el mismo directorio.
2. Compila:
   javac *.java
3. Ejecuta:
   java TournamentApp

Uso
- El programa preguntará si quieres introducir equipos manualmente; si no, usará una lista de ejemplo (16 equipos).
- Muestra los emparejamientos por etapa y simula aleatoriamente el ganador de cada partido.
- Continúa hasta declarar el campeón.

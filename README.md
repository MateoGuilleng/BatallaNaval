# Documentacion

## Requerimientos funcionales

- Al principio se le pedira al jugador que ingrese su nombre

- El juego debe tener un tablero 10x10.

- El jugador debe colocar 5 barcos seleccionandolos en una grilla visual punto por punto (limitar orientacion)

- La máquina también debe colocar 5 barcos.

- El jugador debe pre seleccionar un tipo de misil antes de lanzar un ataque.

- El jugador dispara seleccionando una celda.

- La máquina responde con su turno.

- El sistema debe indicar mediante una interfaz:

    - El numero de botes que le queda a cada jugador

    - El numero de misiles de cada tipo que le queda a cada jugador

    - El tiempo restante que le queda a cada jugador para seleccionar la casilla a tirarle el misil

- Despues de disparado el misil el sistema debe indicar:

    - Agua

    - Impacto

    - Barco hundido

    - Bomba (Si encuentra una bomba, no gasta la que se uso en esa casilla)

- El jugador solo puede ver sus barcos.


- El juego termina cuando todos los barcos de un jugador son destruidos.

- Al final del juego se mostrara un mensaje felicitando al ganador, seguido de 2 botones, uno para volver a jugar y el otro para salir


## Requerimientos no funcionales


- Software

    - Java JDK 17 o superior

- Sistema operativo

    - Windows / Linux / Mac

- Rendimiento

    - Respuesta menor a 1 segundo por jugada

    - Usabilidad

    - Interfaz clara

    - Botones identificables

    - Mensajes al jugador

# Que falta por hacer

Cosas por hacer en el codigo

Antes de pasar a hacer la parte grafica y el controlador debemos definir bien los modelos, a continuacion algunas anotaciones

### Agregar los barcos al tablero


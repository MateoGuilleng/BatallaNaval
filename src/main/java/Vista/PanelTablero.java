package Vista;

import Modelo.TipoCasilla;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panel visual de un tablero 10x10. Solo pinta, no tiene lógica de juego.
 * El controlador le dice qué color mostrar en cada casilla.
 */
public class PanelTablero extends JPanel {

    public static final int CELDA = 45;
    public static final int FILAS = 10;
    public static final int COLS  = 10;

    // Color por tipo de casilla
    private static final Color C_AGUA       = new Color(30,  100, 200);
    private static final Color C_BARCO      = new Color(80,  80,  80);   // gris: barco propio
    private static final Color C_TOCADO     = new Color(255, 200, 0);    // amarillo: impacto parcial
    private static final Color C_HUNDIDO    = new Color(180, 0,   0);    // rojo: hundido
    private static final Color C_BOMBA      = new Color(130, 0,   200);  // morado: misil recogido
    private static final Color C_AGUA_DISP  = new Color(100, 160, 255);  // azul claro: agua disparada
    private static final Color C_PISTA      = new Color(255, 230, 0);    // amarillo "?"
    private static final Color C_HOVER      = new Color(255, 255, 255, 60);

    private final TipoCasilla[][] estado;
    private final boolean[][]     pista;
    private int hoverFila = -1, hoverCol = -1;

    public interface ClickListener { void onClick(int fila, int col); }
    private ClickListener listener;

    public PanelTablero() {
        estado = new TipoCasilla[FILAS][COLS];
        pista  = new boolean[FILAS][COLS];
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLS; j++)
                estado[i][j] = TipoCasilla.AGUA;

        setPreferredSize(new Dimension(COLS * CELDA, FILAS * CELDA));
        setBorder(BorderFactory.createLineBorder(new Color(0, 30, 80), 2));

        addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int col  = e.getX() / CELDA;
                int fila = e.getY() / CELDA;
                if (fila >= 0 && fila < FILAS && col >= 0 && col < COLS && listener != null)
                    listener.onClick(fila, col);
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override public void mouseMoved(MouseEvent e) {
                hoverFila = e.getY() / CELDA;
                hoverCol  = e.getX() / CELDA;
                repaint();
            }
        });
    }

    public void setClickListener(ClickListener l) { this.listener = l; }

    /** El controlador llama esto para actualizar una casilla. */
    public void setCasilla(int fila, int col, TipoCasilla tipo) {
        estado[fila][col] = tipo;
        pista[fila][col]  = false; // si se disparó, quitar pista
        repaint();
    }

    public void mostrarPista(int fila, int col) {
        pista[fila][col] = true;
        repaint();
    }

    public void resetear() {
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLS; j++) {
                estado[i][j] = TipoCasilla.AGUA;
                pista[i][j]  = false;
            }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLS; j++)
                pintarCelda(g2, i, j);
    }

    private void pintarCelda(Graphics2D g, int fila, int col) {
        int x = col * CELDA, y = fila * CELDA;

        // Fondo
        g.setColor(colorDe(estado[fila][col]));
        g.fillRect(x, y, CELDA, CELDA);

        // Pista "?" encima si aplica
        if (pista[fila][col] && estado[fila][col] == TipoCasilla.AGUA) {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(C_PISTA);
            FontMetrics fm = g.getFontMetrics();
            String s = "?";
            g.drawString(s, x + (CELDA - fm.stringWidth(s)) / 2,
                            y + (CELDA + fm.getAscent()) / 2 - 4);
        }

        // Hover
        if (fila == hoverFila && col == hoverCol) {
            g.setColor(C_HOVER);
            g.fillRect(x, y, CELDA, CELDA);
        }

        // Borde
        g.setColor(new Color(0, 30, 80, 150));
        g.drawRect(x, y, CELDA, CELDA);
    }

    private Color colorDe(TipoCasilla t) {
        return switch (t) {
            case BARCO     -> C_BARCO;
            case TOCADO    -> C_TOCADO;
            case HUNDIDO   -> C_HUNDIDO;
            case BOMBA     -> C_BOMBA;
            case AGUA_DISP -> C_AGUA_DISP;
            default        -> C_AGUA;
        };
    }
}

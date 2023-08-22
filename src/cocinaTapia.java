
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Dan
 */
public class cocinaTapia extends javax.swing.JFrame {

    private Lista<Ingrediente> banda = new Lista<Ingrediente>();
    static Lista<Orden> ordenes = new Lista<Orden>();
    private int ingredienteSeleccion = 0;
    private int ordenSeleccion = 0;
    private static Orden ordenActual1 = null;
    private static Orden ordenActual2 = null;
    private static Orden ordenActual3 = null;
    private int puntaje = 0;
    private static int tiempoOrden = 10;
    private static int minutos = 2;
    private static int segundos = 0;

    /**
     * Creates new form cocinaTapia
     */
    public cocinaTapia() {
        initComponents();
        banda.insertar(new Ingrediente());
        banda.insertar(new Ingrediente());
        banda.insertar(new Ingrediente());
        banda.insertar(new Ingrediente());
        banda.insertar(new Ingrediente());
        cargarBanda();
        generarOrden();
        actualizarReloj();
        playMusic();
    }

    private void playMusic() {
        try {

            File musicFile = new File(getClass().getResource("/Popurri.wav").getFile());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void ponerIngrediente() {
        Orden orden = null;
        if (ordenSeleccion == 1) {
            orden = ordenActual1;
        } else if (ordenSeleccion == 2) {
            orden = ordenActual2;
        } else if (ordenSeleccion == 3) {
            orden = ordenActual3;
        }

        if (orden != null && ingredienteSeleccion != 0) {

            orden.EliminarIngrediente(banda.obtener(
                    ingredienteSeleccion - 1).getNombre());

            if (orden.getIngredientes().tamano() == 0) {
                puntaje = puntaje + orden.getPuntos();
                if (ordenSeleccion == 1) {
                    ordenActual1 = null;
                } else if (ordenSeleccion == 2) {
                    ordenActual2 = null;
                } else if (ordenSeleccion == 3) {
                    ordenActual3 = null;
                }
                actualizarInfo();
                this.TBPuntaje.setText(Integer.toString(puntaje));
                recibirOrden();

                // Verificar si se debe reorganizar las órdenes y agregar una nueva
                if (ordenSeleccion == 1 && ordenActual1 == null) {
                    reorganizarOrdenes();
                    agregarNuevaOrden();
                }
            }

            banda.eliminar(ingredienteSeleccion - 1);
            banda.insertar(new Ingrediente());
            cargarBanda();
            System.out.println(orden);
            ordenSeleccion = 0;
            ingredienteSeleccion = 0;
        }
        actualizarInfo();
    }

    private void reorganizarOrdenes() {
        if (ordenActual2 != null) {
            ordenActual1 = ordenActual2;
            ordenActual2 = ordenActual3;
        } else if (ordenActual3 != null) {
            ordenActual1 = ordenActual3;
        }

        ordenActual3 = null;
        TOrden1.setText(ordenActual1 != null ? ordenActual1.getNombre() : "completa");
        TOrden2.setText(ordenActual2 != null ? ordenActual2.getNombre() : "completa");
        TOrden3.setText("completa");
        TBingredientes1.setText(ordenActual1 != null ? ordenActual1.getIngredientes().listarNombres() : "");
        TBingredientes2.setText(ordenActual2 != null ? ordenActual2.getIngredientes().listarNombres() : "");
        TBingredientes3.setText("");
        actualizarInfo();
    }

    private void agregarNuevaOrden() {
        if (ordenes.tamano() > 0) {
            ordenActual3 = ordenes.obtener(0);
            ordenes.eliminar(0);
            TOrden3.setText(ordenActual3.getNombre());
            TBingredientes3.setText(ordenActual3.getIngredientes().listarNombres());
            recibirOrden();
        }
    }

    public void actualizarInfo() {
        if (ordenActual1 != null) {
            TOrden1.setText(ordenActual1.getNombre());
            TBingredientes1.setText(ordenActual1.getIngredientes().listarNombres());
        } else {
            TOrden1.setText("completa");
            TBingredientes1.setText("");
        }
        if (ordenActual2 != null) {
            TOrden2.setText(ordenActual2.getNombre());
            TBingredientes2.setText(ordenActual2.getIngredientes().listarNombres());
        } else {
            TOrden2.setText("completa");
            TBingredientes2.setText("");
        }
        if (ordenActual3 != null) {
            TOrden3.setText(ordenActual3.getNombre());
            TBingredientes3.setText(ordenActual3.getIngredientes().listarNombres());
        } else {
            TOrden3.setText("completa");
            TBingredientes3.setText("");
        }
    }

    private void generarOrden() {
        Thread updateThread = new Thread(() -> {
            while (true) {
                if (!(ordenActual1 != null && ordenActual2 != null && ordenActual3 != null)) {
                    Random random = new Random();

                    String[] tiposHamburguesa = {"HCarne", "HQueso", "HClasica"};
                    int indiceAleatorio = random.nextInt(tiposHamburguesa.length);
                    String tipoHamburguesa = tiposHamburguesa[indiceAleatorio];

                    Orden orden = new Orden(tipoHamburguesa);
                    ordenes.insertar(orden);
                    System.out.println("Se agregó una orden");
                    System.out.println(ordenes.tamano());
                }
                recibirOrden();
                this.TBespera.setText(ordenes.listarNombres());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        updateThread.start();
    }

    private void actualizarReloj() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat formato = new SimpleDateFormat("mm:ss");
            long tiempoInicio = System.currentTimeMillis();

            while (true) {
                long tiempoActual = System.currentTimeMillis();
                long diferencia = tiempoActual - tiempoInicio;
                long tiempoRestante = 2 * 60 * 1000 - diferencia;

                if (tiempoRestante <= 0) {
                    TBTiempo.setText("Tiempo expirado");
                    this.setVisible(false);
                    JOptionPane.showMessageDialog(null,
                            "Game Over... \n El puntaje es: " + puntaje,
                            "Puntaje", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    System.exit(0);
                    break;
                }

                Date tiempoFormateado = new Date(tiempoRestante);
                TBTiempo.setText(formato.format(tiempoFormateado));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void cargarBanda() {
        this.jButton5.setIcon(new javax.swing.ImageIcon(
                getClass().getResource(banda.obtener(0).getImagen())));
        this.jButton2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource(banda.obtener(1).getImagen())));
        this.jButton3.setIcon(new javax.swing.ImageIcon(
                getClass().getResource(banda.obtener(2).getImagen())));
        this.jButton4.setIcon(new javax.swing.ImageIcon(
                getClass().getResource(banda.obtener(3).getImagen())));
        this.jButton6.setIcon(new javax.swing.ImageIcon(
                getClass().getResource(banda.obtener(4).getImagen())));
    }

    public void recibirOrden() {
        if (ordenes.tamano() > 0) {
            if (ordenActual1 == null) {
                ordenActual1 = ordenes.obtener(0);
                TOrden1.setText(ordenActual1.getNombre());
                TBingredientes1.setText(ordenActual1.getIngredientes().listarNombres());
                ordenes.eliminar(0);
            } else if (ordenActual2 == null) {
                ordenActual2 = ordenes.obtener(0);
                TOrden2.setText(ordenActual2.getNombre());
                TBingredientes2.setText(ordenActual2.getIngredientes().listarNombres());
                ordenes.eliminar(0);
            } else if (ordenActual3 == null) {
                ordenActual3 = ordenes.obtener(0);
                TOrden3.setText(ordenActual3.getNombre());
                TBingredientes3.setText(ordenActual3.getIngredientes().listarNombres());
                ordenes.eliminar(0);
            }
            actualizarInfo();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        Fondo = new javax.swing.JPanel();
        Cinta = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        puntos = new javax.swing.JLabel();
        tiempo = new javax.swing.JLabel();
        LOrden2 = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        TBingredientes1 = new javax.swing.JLabel();
        TOrden1 = new javax.swing.JLabel();
        TBespera = new javax.swing.JLabel();
        Basurero = new javax.swing.JButton();
        TBPuntaje = new javax.swing.JLabel();
        TBTiempo = new javax.swing.JLabel();
        LOrden3 = new javax.swing.JLabel();
        LOrden1 = new javax.swing.JLabel();
        TOrden2 = new javax.swing.JLabel();
        TBingredientes2 = new javax.swing.JLabel();
        TOrden3 = new javax.swing.JLabel();
        TBingredientes3 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        Fondo.setBackground(new java.awt.Color(255, 255, 255));
        Fondo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Cinta.setBackground(new java.awt.Color(204, 204, 204));
        Cinta.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Carne.png"))); // NOI18N
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Lechuga.png"))); // NOI18N
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pan.png"))); // NOI18N
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Queso.png"))); // NOI18N
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pan.png"))); // NOI18N
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout CintaLayout = new javax.swing.GroupLayout(Cinta);
        Cinta.setLayout(CintaLayout);
        CintaLayout.setHorizontalGroup(
            CintaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CintaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CintaLayout.setVerticalGroup(
            CintaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CintaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(CintaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        puntos.setFont(new java.awt.Font("Alienware Heavy", 0, 24)); // NOI18N
        puntos.setText("Puntos:");

        tiempo.setFont(new java.awt.Font("Alienware Heavy", 0, 24)); // NOI18N
        tiempo.setText("Tiempo:");

        LOrden2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/7f979e56970fac5113b24d5e71ff032d-icono-de-plato-de-cena.png"))); // NOI18N
        LOrden2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LOrden2MouseClicked(evt);
            }
        });

        logo.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logotipo-Convenios_0.png"))); // NOI18N
        logo.setToolTipText("");

        TBingredientes1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TBingredientes1.setText("jLabel3");

        TOrden1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TOrden1.setText("jLabel3");

        TBespera.setText("lista");

        Basurero.setText("Basurero");
        Basurero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BasureroMouseClicked(evt);
            }
        });

        TBPuntaje.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        TBPuntaje.setText("0");

        TBTiempo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        TBTiempo.setText("00:00");

        LOrden3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/7f979e56970fac5113b24d5e71ff032d-icono-de-plato-de-cena.png"))); // NOI18N
        LOrden3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LOrden3MouseClicked(evt);
            }
        });

        LOrden1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/7f979e56970fac5113b24d5e71ff032d-icono-de-plato-de-cena.png"))); // NOI18N
        LOrden1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LOrden1MouseClicked(evt);
            }
        });

        TOrden2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TOrden2.setText("jLabel3");

        TBingredientes2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TBingredientes2.setText("jLabel3");

        TOrden3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TOrden3.setText("jLabel3");

        TBingredientes3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TBingredientes3.setText("jLabel3");

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Cinta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(368, 368, 368)
                .addComponent(Basurero)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addComponent(tiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TBTiempo))
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addComponent(puntos, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TBPuntaje)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(TOrden3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                    .addComponent(TBingredientes3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(4, 4, 4))
                            .addComponent(TBespera, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, FondoLayout.createSequentialGroup()
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(TOrden1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LOrden1)
                                    .addComponent(TBingredientes1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LOrden2)
                            .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(TBingredientes2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                .addComponent(TOrden2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(6, 6, Short.MAX_VALUE)
                        .addComponent(LOrden3)))
                .addGap(25, 25, 25))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addContainerGap(14, Short.MAX_VALUE)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(puntos, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TBPuntaje))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(TBTiempo))
                            .addComponent(TBespera, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LOrden2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LOrden1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TOrden2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TOrden1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TBingredientes1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(TBingredientes2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(LOrden3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(TOrden3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TBingredientes3, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cinta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Basurero)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LOrden1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LOrden1MouseClicked
        // TODO add your handling code here:
        ordenSeleccion = 1;
        ponerIngrediente();
    }//GEN-LAST:event_LOrden1MouseClicked

    private void LOrden3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LOrden3MouseClicked
        // TODO add your handling code here:
        ordenSeleccion = 3;

        ponerIngrediente();
    }//GEN-LAST:event_LOrden3MouseClicked

    private void BasureroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BasureroMouseClicked
        // TODO add your handling code here:
        banda.eliminar(ingredienteSeleccion - 1);
        banda.insertar(new Ingrediente());
        cargarBanda();
    }//GEN-LAST:event_BasureroMouseClicked

    private void LOrden2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LOrden2MouseClicked
        // TODO add your handling code here:
        ordenSeleccion = 2;

        ponerIngrediente();
    }//GEN-LAST:event_LOrden2MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
        this.ingredienteSeleccion = 5;
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
        this.ingredienteSeleccion = 1;
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        this.ingredienteSeleccion = 4;
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        this.ingredienteSeleccion = 3;
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        this.ingredienteSeleccion = 2;
    }//GEN-LAST:event_jButton2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(cocinaTapia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cocinaTapia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cocinaTapia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cocinaTapia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cocinaTapia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Basurero;
    private javax.swing.JPanel Cinta;
    private javax.swing.JPanel Fondo;
    private javax.swing.JLabel LOrden1;
    private javax.swing.JLabel LOrden2;
    private javax.swing.JLabel LOrden3;
    private javax.swing.JLabel TBPuntaje;
    private javax.swing.JLabel TBTiempo;
    private javax.swing.JLabel TBespera;
    private javax.swing.JLabel TBingredientes1;
    private javax.swing.JLabel TBingredientes2;
    private javax.swing.JLabel TBingredientes3;
    private javax.swing.JLabel TOrden1;
    private javax.swing.JLabel TOrden2;
    private javax.swing.JLabel TOrden3;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel puntos;
    private javax.swing.JLabel tiempo;
    // End of variables declaration//GEN-END:variables
}

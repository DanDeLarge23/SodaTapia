
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author Paula
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static Lista<Orden> ordenes = new Lista<Orden>();

    public static void main(String[] args) throws InterruptedException {
        Thread ordenThread = new Thread(new OrdenRunnable());
        ordenThread.start();
        while(true){
            TimeUnit.SECONDS.sleep(1);
            Main.atenderOrden();
        }
    }
    
    public static class OrdenRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    generarOrden();
                    Thread.sleep(2 * 1000); // Esperar 20 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void generarOrden() {
        Random random = new Random();

        String[] tiposHamburguesa = {"HCarne", "HQueso", "HClasica"};
        int indiceAleatorio = random.nextInt(tiposHamburguesa.length);
        String tipoHamburguesa = tiposHamburguesa[indiceAleatorio];

        Orden orden = new Orden(tipoHamburguesa); // Supongo que tienes una lista de ingredientes
        ordenes.insertar(orden);
        System.out.println("Se agregó una orden");
        System.out.println(ordenes.tamano());
    }

    public static void atenderOrden() {
        System.out.println(ordenes.tamano());

        System.out.println(ordenes.obtener(0).getNombre());
        System.out.println(ordenes.obtener(0).getIngredientes().tamano());

        ordenes.obtener(0).EliminarIngrediente("pan");

        ordenes.obtener(0).EliminarIngrediente("queso");

        ordenes.obtener(0).EliminarIngrediente("lechuga");

        ordenes.obtener(0).EliminarIngrediente("carne");

        System.out.println(ordenes.obtener(0).getIngredientes().tamano());
        System.out.println(ordenes.obtener(0).getIngredientes().toString());

        ordenes.eliminar(0);

        System.out.println(ordenes.tamano());
    }

}

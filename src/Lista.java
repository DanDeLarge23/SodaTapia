/**
 *
 * @author Paula
 */
public class Lista<T> {
    private Nodo<T> cabeza;
    private Nodo<T> ultimo;

    public Lista() {
        cabeza = null;
        ultimo = null;
    }

    public void insertar(T dato) {
        Nodo nuevoNodo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
    }

    public T obtener(int indice) {
        if (cabeza == null || indice < 0) {
            return null;
        }
        
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
            if (actual == cabeza) {
                return null; 
            }
        }
        
        return actual.dato;
    }

    
    
    public int tamano() {
        Nodo actual = cabeza;
        int contador = 0;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }
        return contador;
    }
    
    public void eliminar(int indice) {
        if (indice < 0) {
            System.out.println("Índice inválido");
            return;
        }

        if (indice == 0) {
            if (cabeza != null) {
                cabeza = cabeza.siguiente;
            } else {
                System.out.println("La lista está vacía");
            }
            return;
        }

        Nodo actual = cabeza;
        Nodo anterior = null;
        int contador = 0;

        while (actual != null && contador < indice) {
            anterior = actual;
            actual = actual.siguiente;
            contador++;
        }

        if (actual != null) {
            anterior.siguiente = actual.siguiente;
        } else {
            System.out.println("Índice fuera del rango");
        }
    }
    
    public String listarNombres() {
    Nodo<T> actual = cabeza;
    int contador = 1;
    String res = "";
    while (actual != null) {
        res = res +contador + ". " + actual.dato.toString() + "\n";
        actual = actual.siguiente;
        contador++;
    }
    return res;
}
    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Nodo actual = cabeza;
        while (actual != null) {
            builder.append(actual.dato).append(" ");
            actual = actual.siguiente;
        }
        return builder.toString();
    }
}

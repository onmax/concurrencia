package entrega6;

import es.upm.babel.cclib.Producto;
import es.upm.babel.cclib.Semaphore;
import es.upm.babel.cclib.Almacen;

// TODO: importar  la clase  de los  semaforos.
/**
 * Implementacion de la clase Almacen que permite el almacenamiento FIFO de
 * hasta un determinado numero de productos y el uso simultaneo del almacen por varios threads.
 */
class AlmacenN implements Almacen {
	private int capacidad = 0;
	private Producto[] almacenado = null;
	private int nDatos = 0;
	private int aExtraer = 0;
	private int aInsertar = 0;
	static Semaphore sPlazas;

	public AlmacenN(int n) {
		capacidad = n;
		almacenado = new Producto[capacidad];
		nDatos = 0;
		aExtraer = 0;
		aInsertar = 0;
		Semaphore sPlazas = new Semaphore (n);
	}

	public void almacenar(Producto producto) {
		sPlazas.await();
		almacenado[aInsertar] = producto;
		nDatos++;
		aInsertar++;
		aInsertar %= capacidad;
		sPlazas.signal();
	}

	public Producto extraer() {
		Producto result;
		sPlazas.await();
		result = almacenado[aExtraer];
		almacenado[aExtraer] = null;
		nDatos--;
		aExtraer++;
		aExtraer %= capacidad;
		sPlazas.signal();
		return result;
	}
}
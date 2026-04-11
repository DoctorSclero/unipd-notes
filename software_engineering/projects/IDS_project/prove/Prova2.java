import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Prova2 {
	public static void main(String[] args) {
		HashMap<Integer,String> hm = new HashMap<Integer,String>();

		hm.put(1, "banana");
		hm.put(2, "arancia");
		hm.put(3, "carota");

		Object[] array = hm.entrySet().toArray();

		for (Object i : array) {
			System.out.println(i);
		}
		System.out.println("---");

		((Map.Entry<Integer, String>) array[0]).setValue("ananas");
		for (Object i : array) {
			System.out.println(i);
		}
		
		System.out.println("---");

		Iterator<Map.Entry<Integer,String>> it = hm.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		

		System.out.println("---");
		it = hm.entrySet().iterator();
		it.next();
		it.remove();

		it = hm.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}

		System.out.println("---");
		for (Object i : array) {
			System.out.println(i);
		}
		System.out.println("---");

		((Map.Entry<Integer, String>) array[0]).setValue("ananas");

		it = hm.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}

		System.out.println(hm.get(1));
		
		hm.put(1, "banana");

		((Map.Entry<Integer, String>) array[0]).setValue("insalata");
		it = hm.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}

		System.out.println("---");
		it = hm.entrySet().iterator();
		Object[] array2 = hm.entrySet().toArray();
		System.out.println(array2[0] == it.next());
	}
}

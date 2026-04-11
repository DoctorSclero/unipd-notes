public class Prova {
	public static void main(String[] args) {
		/*P[] v1 = new P[5];
		P[] v2 = new P[5];
		P p1 = new P();
		p1.nome = "Mario";
		P p2 = new P();
		p2.nome = "Luca";
		P p3 = new P();
		p3.nome = "Giovanni";
		
		v1[0] = v2[0] = p1;
		v1[1] = v2[1] = p2;
		
		System.err.println(v1);
		System.out.println(v1[0]);
		System.out.println(v1[1]);
		
		System.err.println(v2);
		System.out.println(v2[0]);
		System.out.println(v2[1]);
		
		v1[1] = null;
		
		System.err.println(v1);
		System.out.println(v1[0]);
		System.out.println(v1[1]);
		
		System.err.println(v2);
		System.out.println(v2[0]);
		System.out.println(v2[1]);*/
		
		W w = new W(5);
		try {
			w = new W(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(w);
	}
}

class P {
	public String nome;

	public String toString() {
		return nome;
	}
}

class W {
	int num;

	public W(int i) {
		num = i;
		if (i == 0)
			throw new NullPointerException();
	}
	public String toString() {
		return "" + num;
	}
}

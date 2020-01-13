import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Generator {
	
	public static void main(String[] args)throws IOException{
		
		PriorityQueue<Unit> queue = new PriorityQueue<Unit>();
		String sss = "";
		testString(sss, 8, queue);
//		Sample sample_0 = new Sample("AACCBDCWAABAACBVAACDCABYAACBCCDZAACADCDEAADACBCVCC");
//		Sample sample_0 = new Sample("AA");
//        System.out.println(sample_0.getResult());
		System.out.println(queue);
    }

	private static void testString(String sss, int i, PriorityQueue<Unit> queue) throws IOException {
		if (i == 0)
			return;
		if (sss.length() > 0) {
			Sample sample = new Sample(sss);
			System.out.print(sample.getString() + ": ");
			int result = sample.getResult();
			System.out.println(result);
			queue.add(new Unit(sample.getString(), result));
			checkQueue(queue);
		}
		for (int j = 0; j < 4; j++) {
			testString(sss + String.valueOf((char)(68-j)), i - 1, queue);
		}
		
	}

	private static void checkQueue(PriorityQueue<Unit> queue) {
		if (queue.size() < 100)
			return;
		LinkedList<Unit> list = new LinkedList<Unit>();
		for (int i = 0; i < 36; i++) {
			list.add(queue.poll());
		}
		queue.clear();
		for (Unit k: list) {
			queue.add(k);
			System.out.println(k.getContent() + " - " + k.getValue());
		}
	}
}

class Unit implements Comparable<Unit>{
	private String content;
	private double value;
	
	public Unit(String content, int score) {
		this.content = content;
		this.value = (double)score / content.length();
	}
	
	public String getContent() {
		return this.content;
	}
	
	public double getValue() {
		return this.value;
	}

	@Override
	public int compareTo(Unit o) {
		return -Double.compare(this.value, o.value);
	}
	
}
package textgen;

public interface Tree<E> {
	
	public boolean contains(E target);
	
	public boolean insert(E data);
	
	public boolean remove(E target);
	
	public int size();

}

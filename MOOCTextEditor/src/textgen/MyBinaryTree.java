package textgen;

public class MyBinaryTree<E extends Comparable<? super E>> implements Tree<E> {
	
	private TreeNode<E> root;
	private int size;
	
	public boolean contains(E toFind) {
		TreeNode<E> curr = root;
		int comp;
		while (curr != null) {
			comp = curr.getData().compareTo(toFind);
			if (comp < 0) {
				curr = curr.getLeftChild();
			} else if (comp > 0) {
				curr = curr.getRightChild();
			} else {
				return true;
			}
		}
		return false;
	} // what if toFind equals to null
	
	private boolean insert1(E toInsert) {
		if (this.root == null) {
			this.root = new TreeNode<E>(toInsert);
			return true;
		}
		TreeNode<E> curr = root;
		int comp;
		while (curr != null) {
			comp = curr.getData().compareTo(toInsert);
			if (comp < 0) {
				if (curr.getLeftChild() == null) {
					curr.setLeftChild(new TreeNode<E>(toInsert, curr));
					return true;
				} else {
					curr = curr.getLeftChild();
				}
			} else if (comp > 0) {
				if (curr.getRightChild() == null) {
					curr.setRightChild(new TreeNode<E>(toInsert, curr));
					return true;
				} else {
					curr = curr.getLeftChild();
				}
			} else {
				return false;
			}
		}
		return false;
	} // what if toInsert equals to null
	
	private boolean insert2(E toInsert) {
		if (this.root == null) {
			this.root = new TreeNode<E>(toInsert);
			return true;
		}
		TreeNode<E> curr = root;
		int comp = toInsert.compareTo(curr.getData());
		while ((comp < 0 && curr.getLeftChild() != null) || 
				(comp > 0 && curr.getRightChild() != null)) {
			if (comp < 0)
				curr = curr.getLeftChild();
			else
				curr = curr.getRightChild();
			comp = toInsert.compareTo(curr.getData());
		}
		if (comp > 0)
			curr.setRightChild(new TreeNode<E>(toInsert, curr));
		else if (comp < 0)
			curr.setLeftChild(new TreeNode<E>(toInsert, curr));
		else
			return false;
		return true;
	} // what if toInsert equals to null
	
	public boolean insert(E toInsert) {
		return this.insert1(toInsert);
//		return this.insert2(toInsert);
	}
	
	private TreeNode<E> findSmallestRightChild(TreeNode<E> curr) {
		// we are sure that curr is NOT null
		TreeNode<E> toReturn = curr.getRightChild();
		if (toReturn == null)
			return toReturn;
		while (toReturn.getLeftChild() != null) {
			toReturn = toReturn.getLeftChild();
		}
		return toReturn;
	}
	
	public boolean remove(E toRemove) {
		// find the node whose data is to be remove
		TreeNode<E> nodeToRemove = root;
		int comp;
		while (nodeToRemove != null) {
			comp = nodeToRemove.getData().compareTo(toRemove);
			if (comp < 0) {
				nodeToRemove = nodeToRemove.getLeftChild();
			} else if (comp > 0) {
				nodeToRemove = nodeToRemove.getRightChild();
			} else {
				break;
			}
		}
		if (nodeToRemove == null)
			return false;
		// found, replace it with the smallest node in right subtree
		TreeNode<E> nodeToReplace = this.findSmallestRightChild(nodeToRemove);
		if (nodeToReplace == null) {
			nodeToRemove.getParent().setLeftChild(nodeToRemove.getLeftChild());
			if (nodeToRemove.getLeftChild() != null)
				nodeToRemove.getLeftChild().setParent(nodeToRemove.getParent());
			return true;
		}
		nodeToRemove.setData(nodeToReplace.getData());
		nodeToReplace.getParent().setLeftChild(nodeToReplace.getRightChild());
		if (nodeToReplace.getRightChild() != null)
			nodeToReplace.getParent().getLeftChild().setParent(nodeToReplace.getParent());
		return true;
	}
	
	public int size(){
		return this.size;
	}
	
	public static void main(String[] args) {
		
	}
}

class TreeNode<E> {
	private E data;
	private TreeNode<E> parent;
	private TreeNode<E> leftChild;
	private TreeNode<E> rightChild;
	
	public TreeNode(E data) {
		this.data = data;
		this.parent = null;
		this.leftChild = null;
		this.rightChild = null;
	}
	
	public TreeNode(E data, TreeNode<E> parent) {
		this(data);
		this.parent = parent;
	}
	
	public TreeNode(E data, TreeNode<E> leftChild, TreeNode<E> rightChild) {
		this(data);
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	public TreeNode(E data, TreeNode<E> parent, TreeNode<E> leftChild, TreeNode<E> rightChild) {
		this(data, leftChild, rightChild);
		this.parent = parent;
	}
	
	public E getData() {
		return this.data;
	}
	
	public TreeNode<E> getParent() {
		return this.parent;
	}
	
	public TreeNode<E> getLeftChild() {
		return this.leftChild;
	}
	
	public TreeNode<E> getRightChild() {
		return this.rightChild;
	}
	
	public void setData(E data) {
		this.data = data;
	}
	
	public void setParent(TreeNode<E> parent) {
		this.parent = parent;
	}
	
	public void setLeftChild(TreeNode<E> leftChild) {
		this.leftChild = leftChild;
	}
	
	public void setRightChild(TreeNode<E> rightChild) {
		this.rightChild = rightChild;
	}
	
}
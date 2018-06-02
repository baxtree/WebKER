package jackteng.pattree;

import java.util.LinkedList;

public class IntegerStack {
	private LinkedList ll = new LinkedList();
	public void push(Object o){
		ll.addFirst(o);
	}
	public Object pop(){
		return ll.removeFirst();
	}
	public Object peek(){
		return ll.getFirst();
	}
	public boolean empty(){
		return ll.isEmpty();
	}
}

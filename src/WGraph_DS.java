package ex1.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;


/**
 * This class represents an undirectional weighted graph.
 * It should support a large number of nodes (over 10^6, with average degree of 10).
 * The implementation should be based on an efficient compact representation
 *
 */
public class WGraph_DS implements weighted_graph {
	private HashMap<Integer ,node_info> grp = new HashMap<Integer ,node_info>();
	private int size=0;
	private int edge=0;

	private int MC=0;

	public WGraph_DS() {	
		this.grp = new HashMap<Integer ,node_info>();
		this.size=0;
		this.edge=0;
		this.MC=0;
	}

	public WGraph_DS(WGraph_DS g) {	
		this.size=g.nodeSize();
		this.edge=g.edgeSize();
		this.MC=0;
		this.grp = new HashMap<Integer ,node_info>();
		Iterator<node_info> moves = g.getV().iterator();
		while(moves.hasNext()) {	
			Node temp=new Node((Node)moves.next());
			this.grp.put(temp.getKey(), temp);
		}
	}

	@Override
	public node_info getNode(int key) {return grp.get(key);}

	@Override
	public boolean hasEdge(int node1, int node2) {
		if(grp.containsKey(node1)&&grp.containsKey(node2)) {
			Node temp=(Node) getNode(node1);
			return temp.hasNi(node2);
		}
		return false;
	}

	@Override
	public double getEdge(int node1, int node2) {
		Node temp = (Node) getNode(node1);
		return temp.getHash().get(node2);
	}

	@Override
	public void addNode(int key) {
		if(!grp.containsKey(key)) {
			Node temp=new Node(key);
			grp.put(temp.getKey(), temp);
			this.size++;
			this.MC++;
		}
	}

	@Override
	public void connect(int node1, int node2, double w) {
		if(getNode(node1)==null||getNode(node2)==null);
		else if(!((Node) grp.get(node1)).hasNi(node2)&&w>=0&&grp.containsKey(node1)&&grp.containsKey(node2)) {
			Node t1=(Node) grp.get(node1);
			Node t2=(Node) grp.get(node2);
			t1.addNi(t2, w);
			t2.addNi(t1, w);
			this.edge++;
			this.MC++;
		}
		else if(w>=0&&grp.containsKey(node1)&&grp.containsKey(node2)) {
			Node t1=(Node) grp.get(node1);
			Node t2=(Node) grp.get(node2);
			t1.getHash().put(node2, w);
			t2.getHash().put(node1, w);
			this.MC++;
		}

	}

	@Override
	public Collection<node_info> getV() {return this.grp.values();}

	@Override
	public Collection<node_info> getV(int node_id) {
		if(grp.containsKey(node_id)) {
			HashMap<Integer ,node_info> coll = new HashMap<Integer ,node_info>();
			Iterator<Integer> moves=((Node)grp.get(node_id)).getKeySet().iterator();
			while(moves.hasNext()) {
				int temp=moves.next();
				coll.put(temp, grp.get(temp));
			}
			return coll.values();
		}
		return null;
	}

	@Override
	public node_info removeNode(int key) {
		if(this.grp.containsKey(key)) {
			Node retur = (Node)this.grp.get(key);
			Iterator<node_info> moves =getV(key).iterator();
			while(moves.hasNext()) {			
				removeEdge(moves.next().getKey(), key);
				//				this.edge--;
			}
			this.grp.remove(key);
			this.size--;
			this.MC++;
			return	retur;
		}
		return null;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		if(hasEdge(node1, node2)) {
			Node nod1 = (Node) this.grp.get(node1);
			Node nod2 = (Node) this.grp.get(node2);
			nod1.removeNode(nod2);
			nod2.removeNode(nod1);
			this.edge--;
			this.MC++;
		}
	}

	/**
	 * this method returns all the String info to be nothing and all the Tag to be 0
	 * @param moves->goes on the hashMap
	 * @return
	 */
	public void clearTag() {
		Iterator<node_info> moves = this.grp.values().iterator();
		while(moves.hasNext()) {			
			node_info temp=moves.next();
			temp.setTag(0.0);
			temp.setInfo("");
		}
	}

	/**
	 * returns the length of the shortest path between start to end
	 * Note: if no such path --> returns -1
	 * @param start - start node
	 * @param end - end (target) node
	 * @return
	 */
	public double shortestPathDist(int start, int end) {
		if(!(this.grp.containsKey(start)&&this.grp.containsKey(end))) 	//check if the two points in the graph
			return -1;
		else {
			LinkedList<Node> moves=new LinkedList<Node>();
			this.grp.get(start).setInfo("V");
			this.grp.get(start).setTag(0);
			moves.add((Node) this.grp.get(start));

			while(!moves.isEmpty()) {
				Node node=moves.poll();

				node.setInfo("V");
				Iterator<Integer> itN=node.getKeySet().iterator();
				while(itN.hasNext()) {
					Node ag=(Node) this.grp.get(itN.next());
					int keyAg=ag.getKey();
					if((ag.getTag()==0||ag.getTag()>node.getHash().get(keyAg)+node.getTag())&&((!ag.getInfo().equals("V"))||node.getTag()+node.getHash().get(keyAg)<ag.getTag())) {
						moves.add(ag);
						ag.setTag(node.getHash().get(keyAg)+node.getTag());
					}
				}
			}
			if(grp.get(end).getTag()==0&&end!=start)	//if the end not connect to the start
				return -1;
			else
				return grp.get(end).getTag();
		}
	}


	public List<node_info> shortestPath(int start, int end) {
		double dest=shortestPathDist(start, end);
		if(dest ==-1)
			return null;
		else {
			LinkedList<node_info> moves= new LinkedList<node_info>();
			moves.add(this.grp.get(end));
			while(dest!=0) {
				Node node =(Node) moves.peekFirst();
				boolean flag=true;

				Iterator<Integer> next=node.getKeySet().iterator();
				while(next.hasNext()&&flag) {
					Node ag=(Node) this.grp.get(next.next());
					int keyAg=ag.getKey();
					if(ag.getTag()==dest-node.getHash().get(keyAg)) {
						moves.addFirst(ag);
						dest=dest-node.getHash().get(keyAg);
						flag=false;
					}
				}
			}
			return moves;
		}
	}

	@Override
	public int nodeSize() {return this.size;}

	@Override
	public int edgeSize() {return edge;}

	@Override
	public int getMC() {return this.MC;}

	/**
	 * set node to the graph
	 * @return
	 */
	public void addN(Node node) {
		if(!grp.containsKey(node.getKey())) {
			grp.put(node.getKey(), node);
			this.size++;
			this.MC++;
			this.edge+=node.getsize();

		}
	}

	@Override
	public boolean equals(Object a) {
		String s1="";
		String s2="";		
		Gson gson=new GsonBuilder().create();
		s1=gson.toJson(this);
		s2=gson.toJson(a);
		if(s1.length()>s2.length()) {
			int d=s1.length()-s2.length();
			s1=s1.substring(0, s1.length()-10-d);
			s2=s2.substring(0, s2.length()-10);
		}
		else {
			int d=s2.length()-s1.length();
			s1=s1.substring(0, s1.length()-10);
			s2=s2.substring(0, s2.length()-10-d);
		}
		return s1.equals(s2);

	}


	protected void fixedge() {this.edge/=2;}//the method fix after loading

}

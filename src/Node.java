package ex1.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;



	   /**
     * represeant the node_info.
     * Note: each node_data should have a unique key.
     * @return
     */
	public class Node implements node_info {

		private	int key;
		private	String info;
		private HashMap<Integer ,Double> coll = new HashMap<Integer ,Double>();
		private double tag=0;
		private int sizeCnnect=0;
		
		/**
		 * copy constructor
		 * @return
		 */
		public Node(Node n) {
			this.key=n.getKey();
			this.info=n.getInfo();
			
			this.coll = new HashMap<Integer ,Double>();
			Iterator<Integer> in = n.coll.keySet().iterator();
			Iterator<Double> dub = n.coll.values().iterator();
			while(in.hasNext()) {	
				this.coll.put(in.next(), dub.next());
			}
			this.tag=0;
			this.sizeCnnect=n.getsize();
		}
		
		/**
		 *build a temp Node
		 * @return
		 */
		public Node(int n) {
			this.key=n;
			this.tag=0;
			this.info="";
			this.coll = new HashMap<Integer ,Double>();
			this.sizeCnnect=0;
		}
		
		public void addNi(Node t,double tag) {
			if(t!=null) {
				coll.put(t.getKey(), tag);
				this.sizeCnnect++;
			}
		}
		
		public boolean hasNi(int key) {return coll.containsKey(key);}
		
		public void removeNode(Node node) {
			coll.remove(node.getKey());
			this.sizeCnnect--;
		}
		
		public Collection<Double> getNi() {return this.coll.values();}//return all the V edge
		public Collection<Integer> getKeySet() {return this.coll.keySet();}
		@Override
		public int getKey() {return this.key;}

		@Override
		public String getInfo() {return this.info;}

		@Override
		public void setInfo(String s) {this.info=""+s;}

		@Override
		public double getTag() {return this.tag;}

		@Override
		public void setTag(double t) {this.tag=t;}
		
		public HashMap<Integer ,Double> getHash() {return this.coll;}
		public int getsize() {return this.sizeCnnect;}
		
	}
	
	
	


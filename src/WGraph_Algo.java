package ex1.src;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WGraph_Algo implements weighted_graph_algorithms {
	private weighted_graph g=new WGraph_DS();


	@Override
	public void init(weighted_graph g) {this.g=g;}

	@Override
	public weighted_graph getGraph() {return this.g;}

	@Override
	public weighted_graph copy() {
		if(this.g!=null) {
			WGraph_DS temp= new WGraph_DS((WGraph_DS) this.g);
			return temp;
		}
		return null;
	}

	
	@Override
	public boolean isConnected() {
		if(!this.g.getV().iterator().hasNext())
			return true;
		Iterator<node_info> grp= this.g.getV().iterator();
		LinkedList<Integer> moves=new LinkedList<Integer>();
		moves.add(grp.next().getKey());

		while(!moves.isEmpty()) {
			Node temp=(Node) g.getNode(moves.poll());
			temp.setTag(1.0);

			Iterator<Integer> arr=temp.getKeySet().iterator();
			while(arr.hasNext()) {
				int t=arr.next();
				if(g.getNode(t).getTag()==0.0) 
					moves.add(t);
			}

		}
		while(grp.hasNext()) {
			Double flag=grp.next().getTag();
			if(flag==0.0) {
				WGraph_DS a=(WGraph_DS) this.g;
				a.clearTag();
				g=a;
				return false;
			}
		}
		((WGraph_DS) g).clearTag();
		return true;
	}




	@Override
	public double shortestPathDist(int src, int dest) {
		WGraph_DS a=(WGraph_DS)g;
		double temp=a.shortestPathDist(src, dest);
		a.clearTag();
		return temp;
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
		WGraph_DS a=(WGraph_DS)g;		
		LinkedList<node_info> temp=(LinkedList<node_info>) a.shortestPath(src, dest);
		a.clearTag();
		return temp;
	}

	@Override
	public boolean save(String file) {
		try {
			String s="";
			Iterator<node_info> moves = g.getV().iterator();
			while(moves.hasNext()) {
				Gson gson=new GsonBuilder().create();
				s+=gson.toJson(g.getNode(moves.next().getKey()))+",/";        	
			}
			PrintWriter pw=new PrintWriter(new File(file));
			pw.write(s);
			pw.close();
			return true;
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean load(String file) {
        
		try {
		File read=new File(file);
		Scanner sc=new Scanner(read);
	    String s=sc.nextLine();   
		WGraph_DS q=new WGraph_DS();
		String[] arr=s.split(",/");

		for(int i=0;i<arr.length;i++) {
			Gson gson=new GsonBuilder().create();
			Node temp=gson.fromJson(arr[i], Node.class);
			q.addN(temp);
		}
		q.fixedge();
		this.g=q;
		return true;
	}
		catch(FileNotFoundException e){
			e.printStackTrace();
			return false;
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
			s1=s1.substring(0, s1.length()-7-d);
			s2=s2.substring(0, s2.length()-7);
		}
		else {
			int d=s2.length()-s1.length();
			s1=s1.substring(0, s1.length()-7);
			s2=s2.substring(0, s2.length()-7-d);
		}
		return s1.equals(s2);
	}
	
	
}

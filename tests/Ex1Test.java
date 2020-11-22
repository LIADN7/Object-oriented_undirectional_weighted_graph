package ex1.tests;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;



class Ex1Test {


	/**
	 * Checks if the graph adds, deletes, conect, size ... properly
	 * (all the simple update and remove node)
	 */
	@Test
	void nodeSize() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(3);
		g.connect(0,1,1);
		g.connect(0,2,2);
		g.connect(0,3,3);
		g.connect(0,1,1);
		int e_size =  g.edgeSize();
		assertEquals(3, e_size);
		double w03 = g.getEdge(0,3);
		double w30 = g.getEdge(3,0);
		assertEquals(w03, w30);
		assertEquals(w03, 3);        

		g.removeNode(4);         
		g.removeNode(3);        
		g.removeNode(2);
		g.removeNode(1);
		g.removeNode(1);
		int s = g.nodeSize();
		assertEquals(1,s);

	}

	/**
	 * check if the graph contain the node,
	 * 
	 */
	@Test
	void getV() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(0,1,1);
		g.connect(0,2,2);
		g.connect(0,3,3);
		g.connect(0,1,1);
		Collection<node_info> v = g.getV();
		Iterator<node_info> iter = v.iterator();
		while (iter.hasNext()) {
			node_info n = iter.next();
			assertNotNull(n);
		}
	}

/**
 * Check the shortest Path and the distance,
 * and check if copy of the graph work
 */
	@Test
	void graphConnect() {
		WGraph_DS g = doGraph8();
		WGraph_Algo a=new WGraph_Algo();
		a.init(g);
		assertEquals(true,a.isConnected());
		double sho=a.shortestPathDist(1, 8);
		assertEquals(14.0,sho);//if the distance is correct
		
		LinkedList<node_info> link=(LinkedList<node_info>) a.shortestPath(1, 8);		
		assertEquals(1,link.poll().getKey());
		assertEquals(4,link.poll().getKey());
		assertEquals(5,link.poll().getKey());
		assertEquals(6,link.poll().getKey());
		assertEquals(7,link.poll().getKey());
		assertEquals(8,link.poll().getKey());//if the path is correct
		
		WGraph_DS cop = (WGraph_DS) a.copy();//check if copy
		assertEquals(true, cop.equals((WGraph_DS)a.getGraph()));
	}
/**
 * check if do save and load , and check if they equals
 */
	@Test
	void saveLoad() {
		WGraph_DS g = doGraph8();
		WGraph_Algo a=new WGraph_Algo();
		a.init(g);
		assertEquals(true, a.save("test.txt"));//save
		WGraph_Algo b=new WGraph_Algo();
		assertEquals(true, b.load("test.txt"));//load
		assertEquals(true, a.equals(b));//if they equals
		
	}	
	
	/**
	 * Builds a graph with a million vertices
	 *  and shows what the construction time is
	 */
	@Test
	void GraphMillion() {
		long start = new Date().getTime();
		WGraph_DS g = new WGraph_DS();
		for(int i=0;i<1000000;i++)
			g.addNode(i);
		for(int i=0;i<1000000;i++)
			for(int j=0;j<7;j++)
				g.connect(i,i+j,(Math.random()*7)+1);
		assertEquals(1000000, g.nodeSize());
		assertEquals(6999979, g.edgeSize());
		long end = new Date().getTime();
		System.out.println("Graph construction million nodes run time: "+((double)(end-start)/1000)+" secends");
	}
	
	
	
	/**
	 * Build a graph connect of 8 V
	 */
	public WGraph_DS doGraph8() {
		WGraph_DS g = new WGraph_DS();
		for(int i=1;i<9;i++)
			g.addNode(i);

		g.connect(1,2,3);
		g.connect(1,3,4);
		g.connect(1,4,5);
		g.connect(3,4,4);		
		g.connect(4,5,3);
		g.connect(5,6,1);
		g.connect(5,8,7);		
		g.connect(6,7,2);
		g.connect(7,8,3);		
		return g;
	}
}

import org.json.*;
import java.util.List;
import java.io.*;


/**
 * Testfile for elementary cycle search.
 */
public class TestCycles {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws java.io.IOException {

		/* testing version, used predefined graph
		//int num_vertices = Integer.parseInt(args[0]);
		int num_vertices = 10;

		String nodes[] = new String[num_vertices];
		boolean adjMatrix[][] = new boolean[num_vertices][num_vertices];

		//initialize the name for each node
		for (int i = 0; i < num_vertices; i++) {
			nodes[i] = Integer.toString(i);
		}

		//first loop
		adjMatrix[0][1] = true;
		adjMatrix[1][2] = true;
		adjMatrix[2][0] = true;
		
		adjMatrix[2][4] = true;
		adjMatrix[1][3] = true;
		adjMatrix[3][6] = true;
		adjMatrix[6][5] = true;
		adjMatrix[5][3] = true;
		adjMatrix[6][7] = true;
		adjMatrix[7][8] = true;
		adjMatrix[7][9] = true;
		adjMatrix[9][6] = true;
		//reversed first loop
		adjMatrix[1][0] = true;
		adjMatrix[2][1] = true;
		adjMatrix[0][2] = true;
		//single node loop--->the algorithm does not condiser this as a cycle
		adjMatrix[9][9] = true;
		*/
		/*
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String line;

		while ((line = stdin.readLine()) != null && line.length()!= 0) {
			String[] vertices = line.split(" ", 2);
			int v1 = Integer.parseInt(vertices[0]);
			int v2 = Integer.parseInt(vertices[1]);
			adjMatrix[v1][v2] = true;
		}
		*/

		/* test version, using imported json file from Expertiza*/
		File file = new File("F:\\Dropbox\\my papers\\2016 colussion\\collusion_detection_sample_735 program1.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		String str = new String(data, "UTF-8");
		
		JSONObject obj = new JSONObject(str);
		
		JSONArray actors = obj.getJSONArray("actors");
		int num_vertices = actors.length();
		String nodes[] = new String[num_vertices];
		
		//create list of nodes
		for (int i = 0; i < actors.length(); i++)
		{
		    String individual_actor_id = actors.getJSONObject(i).getString("id");
		    nodes[i] = individual_actor_id;
		}
		
		boolean adjMatrix[][] = new boolean[num_vertices][num_vertices];
		//create list of edges
		JSONArray crituques = obj.getJSONArray("crituques");
		for (int i = 0; i < crituques.length(); i++)
		{
		    String reviewerActorIdString = crituques.getJSONObject(i).getString("reviewer_actor_id");
		    String revieweeActorIdString = crituques.getJSONObject(i).getString("reviewee_actor_id");
		    double score = crituques.getJSONObject(i).getDouble("score");
		    int reviewerActorIdInt = indexOfNodes(reviewerActorIdString, nodes);
		    int revieweeActorIdInt = indexOfNodes(revieweeActorIdString, nodes);
		    if(reviewerActorIdInt!=-1 && revieweeActorIdInt!=-1 && score>95.0)
		    {
		    	adjMatrix[reviewerActorIdInt][revieweeActorIdInt] = true;
		    }
		}
		
		// create the Cycles Search object, find the cycles and out put them.
		ElementaryCyclesSearch ecs = new ElementaryCyclesSearch(adjMatrix, nodes);
		List cycles = ecs.getElementaryCycles();
		System.out.println("\"colluder_sycles\":[");
		
		for (int i = 0; i < cycles.size(); i++) {
			
			List cycle = (List) cycles.get(i);
			
			if(cycle.size()>5)
			{
				continue;
			}
			System.out.print("{\n\"colluders\":[");
			
			for (int j = 0; j < cycle.size(); j++) {
				String node = (String) cycle.get(j);
				
				if (j < cycle.size() - 1) {
					System.out.print("{\"id\":\""+node + "\"},");
				} else {
					System.out.print("{\"id\":\""+node + "\"}");
				}
			}
			
			if(i < cycles.size()-1){
				System.out.print("]\n},\n");
			}
			else
			{
				System.out.print("]\n}\n");
			}
		}
		System.out.println("]");
		
	}
	
	private static int indexOfNodes(String id, String[] nodes){
		int index = -1;
		for (int i=0;i<nodes.length;i++) {
			if (nodes[i].equals(id)) {
			index = i;
			break;
			}
		}
		return index;
	}

}
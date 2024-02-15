package boj17396;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
  // 100000 * 100000 로 선언하면 오류남 
	final static long TMAX = 10000000001L;
	final static int start = 0; // 시작하는 위치 

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	static class Node { // 목적지와 걸리는 시간을 저장
		int dest;
		long t;
		
		public Node(int dest, long t) {
			super();
			this.dest = dest;
			this.t = t;
		}
	}
	
	static int N, M; // 분기점 수, 엣지
	static boolean[] visited; // 적에게 보이는지 여부 - 방문해서 못감 처리하면됨
	static ArrayList<Node>[] dist; 
	static long[] uDist;
	
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		dist = new ArrayList[N];
		visited = new boolean[N];
		for(int i = 0; i < N; i++) {
			visited[i] = (Integer.parseInt(st.nextToken()) == 1);
			dist[i] = new ArrayList<>();
		}
		
		uDist = new long[N]; // 유섭이의 거리정보 
		Arrays.fill(uDist, TMAX * N);
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			dist[a].add(new Node(b, t));
			dist[b].add(new Node(a, t)); // 양방향이므로 두개 다 저장 
		}
		
		PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> {
			if(a.t > b.t) return 1; // 오름차순 정렬 
			return -1;
		});

    // 초기값 설정 
		for(Node node : dist[start]) {
			uDist[node.dest] = node.t;
			pq.add(node);
		}
		
		visited[start] = true; // 내 자신은 방문 안함
		visited[N-1] = false; // 마지막은 방문 해야함 
		
		while(!pq.isEmpty()) {
			Node minTimeNode = pq.poll(); // Node의 우선순위 높은거 가져오기 
			if(visited[minTimeNode.dest]) continue; // 방문했으면 다음거 
			
			visited[minTimeNode.dest] = true; 
			if(minTimeNode.dest == N-1) break;
			
			for(Node node : dist[minTimeNode.dest]) {
				if(visited[node.dest]) continue;
        // 바뀐 값 기준으로 업데이트  
				if(uDist[minTimeNode.dest] + node.t < uDist[node.dest]) {
					uDist[node.dest] = uDist[minTimeNode.dest] + node.t;
					pq.add(new Node(node.dest, uDist[node.dest]));
				}
			}
		}
		
		long answer = -1;
		if(visited[N-1]) { // 방문 안했으면 
			answer = uDist[N-1];
		}
		
		bw.write(Long.toString(answer));
		bw.flush();
		bw.close();
	}
}

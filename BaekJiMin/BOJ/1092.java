import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N, M;
	static Integer[] crains;
	static TreeMap<Integer, Integer> boxes;
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		
		crains = new Integer[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			crains[i] = Integer.parseInt(st.nextToken());
		}
		// 크레인이 실을 수 있는 화물 무게가 큰 순으로 정렬 
		Arrays.sort(crains, Collections.reverseOrder()); 
		
		M = Integer.parseInt(br.readLine());
		boxes = new TreeMap<>(); // 이분탐색을 위해 TreeMap 사용 
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < M; i++) {
			int m = Integer.parseInt(st.nextToken());
			Integer cnt;
			if((cnt = boxes.get(m)) == null){
				boxes.put(m, 1);
				continue;
			}
			boxes.replace(m, cnt+1);
		}
		
		int time = 0;	
		// 처리하지 못하는 화물이 있는 경우 -1 처리 
		if(boxes.higherEntry(crains[0]) != null) {
			time = -1;
		}
		
		Loop1 : while(time != -1 && !boxes.isEmpty()) {
			time++; // 시간 증가 
			//System.out.println(time+"?");
			for(int n = 0; n < N; n++) {
				// crains[n]을 포함해 가장 가까운 최댓값 구하기 
				Entry<Integer, Integer> box = boxes.floorEntry(crains[n]);
				if(box == null) break; // 없으면 crains 0 부터 시작 
				
				if(box.getValue() == 1) { // 마지막이면 삭제 
					boxes.remove(box.getKey());
					continue;
				}
				
				// 아니면 갯수 1 감소 
				boxes.replace(box.getKey(), box.getValue()-1);
				if(boxes.isEmpty()) break Loop1;
			}
		}
		
		bw.write(Integer.toString(time));
		bw.flush();
		bw.close();
	}
	
}

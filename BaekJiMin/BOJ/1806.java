import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N, answer;
	static int[] nums;
	static long S;
	
	public static void main(String[] args) throws Exception {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
        
		st = new StringTokenizer(br.readLine());
		nums = new int[N];
		for(int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		
		int start = 0;
		int end = 0;
		long sum = 0; 
		int answer = N+1;
		
		while(start <= end) {			
			if(sum >= S) {
				answer = Math.min(answer, end - start);
				sum -= nums[start];
				start++;
				continue;
			}
			
			if(end == N) break;
			sum += nums[end++];
		}
		
		if(answer == N+1) {
			answer = 0;
		}
		
		bw.write(Integer.toString(answer));
		bw.flush();
		bw.close();
	}
}

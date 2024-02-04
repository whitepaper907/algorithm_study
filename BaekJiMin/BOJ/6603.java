import java.io.*;
import java.util.StringTokenizer;

public class Main {

	static int k;
	static int[] S;
	static int[] nums = new int[6];
	static StringBuilder answer;
	public static void findNums(int num, int cnt) {
		if(cnt == 6) {
			for(int out : nums) {
				answer.append(out).append(" ");
			}
			answer.append("\n");
			return;
		}
		
		for(int i=num+1; i <= k - (6 - cnt) ;i++) { // 들어온 다음 수 부터 cnt를 6으로 만들 수 있는 수까지 탐색하기
			nums[cnt] = S[i];
			findNums(i, cnt+1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		answer = new StringBuilder();
		
		while(true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			k = Integer.parseInt(st.nextToken());
			if(k == 0) break;
			
			S = new int[k];
			for(int i=0;i<k;i++) {
				S[i] = Integer.parseInt(st.nextToken());
			}
			
			findNums(-1, 0); // 함수에서 num+1부터 탐색하기 때문에 -1로 전달함
			answer.append("\n");
		}
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		bw.write(answer.toString());
		bw.flush();
		bw.close();
	}
}

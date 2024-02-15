import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringBuilder answer = new StringBuilder();
	static int[] uParent;
	
	public static void main(String[] args) throws IOException {
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		uParent = new int[n+1];
		for(int i = 1; i <= n; i++) {
			uParent[i] = i;
		}
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int op = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int parentA = findParent(a); // a의 부모
			int parentB = findParent(b); // b의 부모
			
			switch(op) {
				case 0: { 
          // 부모는 작은 값으로 저장하기 
					if(uParent[parentA] < uParent[parentB]) {
						uParent[parentB] = uParent[parentA];
						break;
					}
					uParent[parentA] = uParent[parentB];
					break;
				}
				
				case 1: {
					if(parentA == parentB) { // 부모가 같으면 yes
						answer.append("YES").append("\n");
						break;
					}
					answer.append("NO").append("\n");
					break;
				}
			}
			
		}
		
		bw.write(answer.toString());
		bw.flush();
		bw.close();

	}
	
	public static int findParent(int num) {
		if(num == uParent[num]) {
			return uParent[num];
		}
		// 부모 찾으면서 업데이트 같이 해주기 
		return uParent[num] = findParent(uParent[num]);
	}
}

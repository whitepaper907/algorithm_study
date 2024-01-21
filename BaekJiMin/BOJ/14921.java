import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] vals = new int[N];
		for(int i=0;i<N;i++) {
			vals[i] = Integer.parseInt(st.nextToken());
		}
		
		int B = Integer.MAX_VALUE;
		int start = 0, end = N-1;
		while(start < end) {
			int val = vals[start] + vals[end];
			// B 업데이트
			B = (Math.abs(val)>Math.abs(B))? B : val;
			
			//idx 이동
			if(val > 0) end--;
			else if(val < 0) start++;
			else break;
		}
		
		System.out.println(B);
	}

}

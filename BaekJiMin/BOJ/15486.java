import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	
	static int N;
	static Consult[] consult;
	static int[] payoff;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		
		consult = new Consult[N];
		for(int n = 0; n < N; n++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			consult[n] = new Consult(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		payoff = new int[N+1]; // 수익
    // 뒤에서 부터 업데이트 해줘야 N번만 순회 가능 
		for(int n = N-1; n >= 0; n--) {
			if(n + consult[n].T > N) {
				payoff[n] = payoff[n+1];
				continue;
			}
			
			payoff[n] = Math.max(payoff[n+consult[n].T] + consult[n].P, payoff[n+1]);
		}
		
		bw.write(Integer.toString(payoff[0]));
		bw.flush();
		bw.close();

	}
	
	static class Consult {
		int T;
		int P;
		
		public Consult(int t, int p) {
			T = t;
			P = p;
		}
		
	}

}

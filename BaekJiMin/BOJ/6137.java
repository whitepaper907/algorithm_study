import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		char[] abc = new char[N];
		for(int i=0;i<N;i++) {
			abc[i] = br.readLine().charAt(0);
		}
		
		StringBuilder sb = new StringBuilder(); // answer
		int len  = 0; 
		int s = 0, e = N-1;
		while(s <= e) {
			if(s == e || abc[s] < abc[e]) {
				sb.append(abc[s++]); len++;
			}
			else if(abc[s] == abc[e]) {
				sb.append(abc[s]); len++;
				int ts = s+1, te = e-1;
				while(ts < te && abc[ts] == abc[te]) {
					ts++; te--;
				}
				if(abc[ts] < abc[te]) s++;
				else e--;
			}
			else {sb.append(abc[e--]); len++;}
			
			if(len == 80) { //80글자마다 새줄 문자 출력
				sb.append("\n"); len = 0;
			}
		}
		
		System.out.println(sb);
	}
}

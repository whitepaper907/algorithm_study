import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N, answer; 
	static boolean[][] map;
	static List<Integer>[] dx, dy, canMove;
	
	public static void main(String[] args) throws Exception {
		
		N = Integer.parseInt(br.readLine());
		map = new boolean[N][N];
		
		for(int i = 0; i < N ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = (st.nextToken().equals("1")); // 1이면 true
			}
		}
		
		init();
		movePipe(0, 1, 0); // (0,1) 부터 시작
		
		bw.write(Integer.toString(answer));
		bw.flush();
		bw.close();
	}
	
	public static void movePipe(int x, int y, int dir) {
		if(x == N-1 && y == N-1) {
			answer++;
			return;
		}
		
		for(int nextDir : canMove[dir]) { // 다음 방향 (ex, 가로면 가로 대각선)
			int d = 0;
			for(; d < dx[nextDir].size(); d++) { // 확인해야하는 칸 확인하기 
				int nx = x + dx[nextDir].get(d);
				int ny = y + dy[nextDir].get(d);
				
				if(nx < 0 || nx >= N || ny < 0 || ny >= N) break;
				if(map[nx][ny]) break;
			}
			
			if(d < dx[nextDir].size()) continue;
			
			movePipe(x + dx[nextDir].get(0), y + dy[nextDir].get(0), nextDir);
 		}
		
	}
	
	public static void init() {
		dx = new ArrayList[3];
		dy = new ArrayList[3];
		canMove = new ArrayList[3];
		
		for(int i = 0; i < 3; i++) {
			dx[i] = new ArrayList<>();
			dy[i] = new ArrayList<>();
			canMove[i] = new ArrayList<>();
		}
		
		// 첫번째 파이프 : 가로 
		dx[0].add(0);
		dy[0].add(1);

		// 가로는 가로와 대각선 이동 가능
		canMove[0].add(0);
		canMove[0].add(2);
		
		// 두번째 파이프 : 세로
		dx[1].add(1);
		dy[1].add(0);

		// 세로는 세로와 대각선 이동 가능
		canMove[1].add(1);
		canMove[1].add(2);
		
		// 세번째 파이프 : 대각선
		dx[2].add(1);
		dy[2].add(1);
		
		dx[2].add(0);
		dy[2].add(1);
		
		dx[2].add(1);
		dy[2].add(0);

		// 대각선 모두 가능 
		canMove[2].add(0);
		canMove[2].add(1);
		canMove[2].add(2);
	}

}

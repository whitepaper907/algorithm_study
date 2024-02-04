import java.io.*;

public class Main {

	// 퀸은 가로 세로 대각선 어디든 갈 수 있다.
	static int N, answer;
	static boolean[][] visited;
	public static void findNQueen(int y) {
		if(y == N) {
			answer++;
			return; 
		}
		
		for(int x = 0; x < N ; x++) {
			if(!(visited[x][0] || visited[x - y + N][1] || visited[x + y][2])) {
				changeState(x, y);
				findNQueen(y+1);
				changeState(x, y);
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		N = Integer.parseInt(br.readLine());
		// 0 : 행, 1 : 대각선(왼 -> 오) 2: 대각선(오 -> 왼)
		visited = new boolean[N*2][4]; 
		
		findNQueen(0);
		
		bw.write(Integer.toString(answer));
		bw.flush();
		bw.close();
	}

  // true는 false로 false는 true를 만들어주는 함수
  // 시간은 느려지긴 했는데 깔끔하게 하고 싶어서 추가함 
	public static void changeState(int x, int y) { 
		visited[x][0] = !visited[x][0];
		visited[x - y + N][1] = !visited[x - y + N][1]; 
		visited[x + y][2] = !visited[x + y][2];
		
	}
}

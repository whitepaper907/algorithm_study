import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;


public class Main {
	
	final static int DELETE = -2;
	final static int BLACK = -1;
	final static int RED = 0;
	
	static class Bomb { // 폭탄 위치 정보 
		int x;
		int y;
		
		public Bomb(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	static class Bombs implements Comparable<Bombs> { // 폭탄 묶음 
		int cnt;
		int redCnt;
		int x; // y보다 x가 우선 - 가장 큰거
		int y; // 가장 작은
		
		public Bombs(int cnt, int redCnt, int x, int y) {
			this.cnt = cnt;
			this.redCnt = redCnt;
			this.x = x;
			this.y = y;
		}

		// 빨간색 폭탄이 가장 적게 포함된 것
		@Override
		public int compareTo(Bombs o) {
			if(cnt == o.cnt) {
				return o.redCnt - redCnt;
			}
			return cnt - o.cnt;
		}

	}

	static int[][] map;
	static int bombCount;  
	static int N, M;
	
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static boolean[][] visited;
	static Bombs maxBombs;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int answer = 0;
		while(true) { // 처음부터 폭탄이 없는 경우 
			maxBombs = new Bombs(-1, -1, -1, -1);
			visited = new boolean[N][N];
			
			// 행이 가장 큰 폭탄을 선택 - 빨간색이 아니면서 행이 가장 큰 칸
			// 행이 같으면 열이 가장 작은 [행][열]
			for(int i = N-1; i >= 0; i--) {
				for(int j = 0; j < N; j++) {
					// 빨간색만으로 이루어진 것은 포함할 수 없다
					if(!visited[i][j] && map[i][j] > RED) { // 색깔 폭탄만
						findBombs(i, j);
					}
				}
			}
			
			if(maxBombs.cnt <= 1) break; // 폭탄 묶음은 2개 이상
			
			//폭탄 제거
			visited = new boolean[N][N];
			deleteBombs();

			answer += maxBombs.cnt * maxBombs.cnt;
			
			//중력
			gravity();
			//반시계
			rotate();
			//중력
			gravity();
			
		}
		
		bw.write(Integer.toString(answer));
		bw.flush();
		bw.close();
		
	}
	
	public static boolean checkRange(int x, int y) {
		if(x < 0 || x >= N || y < 0 || y >= N) return false;
		return true;
	}
	
	public static void findBombs(int x, int y) {
		
		// 폭탄 묶음 찾기
		Deque<Bomb> queue = new ArrayDeque<>();
		queue.add(new Bomb(x, y));
		visited[x][y] = true;
		
		List<Bomb> redBombs = new ArrayList<>();
		Bombs newBombs = new Bombs(0, 0, x, y);
		while(!queue.isEmpty()) {
			Bomb currBomb = queue.pollFirst();
			newBombs.cnt++;
			
			for(int d = 0; d < 4; d++) {
				int nx = dx[d] + currBomb.x;
				int ny = dy[d] + currBomb.y;
				
				if(!checkRange(nx, ny) || visited[nx][ny]) continue;
				
				// 2개 색깔 포함 가능하지만, 하나는 반드시 빨간색
				if(map[x][y] == map[nx][ny] || map[nx][ny] == RED) {
					queue.add(new Bomb(nx, ny));
					visited[nx][ny] = true;
					
					if (map[nx][ny] == RED) {
						redBombs.add(new Bomb(nx, ny));
					}
				}
			}
			
		}
		
		newBombs.redCnt = redBombs.size();
		// 빨간색은 중복이 가능하다 - 빨간색 visited 처리
		for(Bomb b : redBombs) {
			visited[b.x][b.y] = false;
		}
				
		if(newBombs.compareTo(maxBombs) > 0) {
			maxBombs = newBombs;
		}
	}
	
	public static void deleteBombs() {
		Deque<Bomb> queue = new ArrayDeque<>();
		queue.add(new Bomb(maxBombs.x, maxBombs.y)); 
		visited[maxBombs.x][maxBombs.y] = true;
		
		int color = map[maxBombs.x][maxBombs.y]; // 여기 x, y는 red가 아니어서 사용가능
		while(!queue.isEmpty()) {
			Bomb currBomb = queue.pollFirst();
			map[currBomb.x][currBomb.y] = DELETE;
			for(int d = 0; d < 4; d++) {
				int nx = dx[d] + currBomb.x;
				int ny = dy[d] + currBomb.y;
				if(!checkRange(nx, ny) || visited[nx][ny]) continue;

        if(color == map[nx][ny] || map[nx][ny] == RED) {
					queue.add(new Bomb(nx, ny));
					visited[nx][ny] = true;
				}
			}
		}
	}
	
	public static void gravity() {
		
		// -> 방향으로 열 탐색
		for(int y = 0; y < N; y++) {
			int dropIdx = N - 1;
			while(true) {
				int targetIdx = -1;
				// 아래부터 색깔 폭탄 찾기
				for(int x = dropIdx; x >= 0; x--) {
					if(map[x][y] == BLACK) {
						dropIdx = -1;
						continue;
					}
					
					if(map[x][y] == DELETE) {
						if(dropIdx < 0) dropIdx = x;
						continue;
					}
					
					if(dropIdx > 0) { // 발견 
						targetIdx = x;
						break;
					}
				}
				
				if(targetIdx == dropIdx) {
					dropIdx--;
					continue;
				}
				
				if(targetIdx >= 0 && dropIdx > 0) {
					map[dropIdx--][y] = map[targetIdx][y];
					map[targetIdx][y] = DELETE;
					continue;
				}
				
				break;
			} 
		}
		
	}

	// 반시계(왼쪽)으로 90도 회전
	public static void rotate() {
		int[][] changeMap = new int[N][N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				changeMap[i][j] = map[j][N-1-i];
			}
		}
		map = changeMap;
	}
}

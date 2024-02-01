import java.io.*;
import java.util.StringTokenizer;

public class robotCleaner {
	//방향 : 북 동 남 서 -> 해당 방향은 시계 방향, 반시계면 -1로 돌아가야됨
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static int[][] map;
	static int N, M;
	public static void main(String[] args) throws IOException {
		//
		//// ~~ 입력 받기 시작 ~~ 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		int robotX = Integer.parseInt(st.nextToken());
		int robotY = Integer.parseInt(st.nextToken());
		int dir = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		for(int n=0;n<N;n++) {
			st = new StringTokenizer(br.readLine());
			for(int m=0;m<M;m++) {
				map[n][m] = Integer.parseInt(st.nextToken());
			}
		}
		//// ~~ 입력 받기 끝 ~~ 
		//
		
		int answer = 0;
		boolean ing  = true;
		while(ing) {
			//1. 현재 칸이 청소 되지 않았면 청소한다
			if(map[robotX][robotY] == 0) {
				map[robotX][robotY] = 2; // 
				answer++;
			}
			
			int nextDir = -1;
			int d = 1;
			for(;d<=4;d++) {
				nextDir = dir - d; // -4 면 나자신으로 돌아옴 
				if(nextDir < 0) nextDir += 4;
				int nx = robotX + dx[nextDir];
				int ny = robotY + dy[nextDir];
				if(checkRange(nx, ny)) {
					if(map[nx][ny] == 0) { //3. 청소되지 않는 칸이 있다면
						//3-1. 반시계회전
						dir = nextDir;
						//3-2. 바라보는 방향 기준으로 앞쪽 칸이 청소되지않은 빈칸인 경우, 전진 후 1번으로 돌아간다. 
						robotX = nx;
						robotY = ny;
						break;
					}
				}
			}
			
			if(d > 4) { //2. 주변에 청소할 곳이 없으면
				//2-1. 후진이 되면 후진하고 다시 1번으로 돌아간다
				// (0 <-> 2) (1 <-> 3) 방향으로 바꾸어 후진, 이때 바라보는 방향은 유지할 것
				int backDir = (dir + 2) % 4;
				int nx = robotX + dx[backDir];
				int ny = robotY + dy[backDir];
				if(checkRange(nx, ny)) {
					robotX = nx;
					robotY = ny;
					continue;
				}
				
				//2-2. 후진할 수 없다면 작동을 멈춘다
				ing = false;
			}
			
		}
		
		System.out.println(answer);	
	}
	
	private static boolean checkRange(int x, int y) {
		if(x<0 || x>=N || y<0 || y >= M) return false;
		if(map[x][y] == 1) return false;
		return true;
	}

}

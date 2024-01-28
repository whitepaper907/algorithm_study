package boj7569;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static class Loc{
		int n;
		int m;
		int h;
		public Loc(int n, int m, int h) {
			this.n = n;
			this.m = m;
			this.h = h;
		}
		
	}
	
	static int N, M, H;
	public static boolean checkRange(Loc loc) {
		if(loc.n >= 0 && loc.m >= 0 && loc.h >= 0 
				&& loc.n < N && loc.m < M && loc.h < H) {
			
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		int[][][] tomatoes = new int[H][N][M];
		int tom_target = 0;
		Queue<Loc> q = new LinkedList<>();
		for(int i=0;i<H;i++) {
			for(int j=0;j<N;j++) {
				st = new StringTokenizer(br.readLine());
				for(int k=0;k<M;k++) {
					tomatoes[i][j][k] = Integer.parseInt(st.nextToken());
					if(tomatoes[i][j][k] == 1) { // 익은 토마토
						q.add(new Loc(j, k, i));
					}
					if(tomatoes[i][j][k] == 0) tom_target++; // 익어야하는 토마토
					
				}
			}
		}
		
		int day = -1; // qSize 0일때 제외를 위해 -1
		int[] dx = {1, -1, 0, 0, 0, 0};
		int[] dy = {0, 0, 1, -1, 0, 0};
		int[] dh = {0, 0, 0, 0, 1, -1};
		int qSize = q.size();
		while(!q.isEmpty()) {
			Loc loc = q.poll(); qSize--;
			for(int d = 0; d<6;d++) {
				Loc nloc =  new Loc(loc.n+dx[d], loc.m+dy[d], loc.h+dh[d]); 
				if(checkRange(nloc)) {
					if(tomatoes[nloc.h][nloc.n][nloc.m] == 0) {
						tomatoes[nloc.h][nloc.n][nloc.m] = 1; // 토마토 익히기 
						tom_target--; // 토마토 익은거 확인 
						q.add(nloc);
					}
				}
				
			}
			
			if(qSize == 0) {
				qSize = q.size();
				day++;
			}
		}
		
		if(tom_target > 0) System.out.println(-1);
		else System.out.println(day);
		
		
	}

}

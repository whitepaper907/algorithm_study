class Solution {
    
    public int solution(String dartResult) {
        int answer = 0;
        
        int[] culArr = new int[3];
        int game = -1;
        for(int i=0;i<dartResult.length();i++){
            int ch = dartResult.charAt(i);
            if(ch>='0' && ch<='9') {
                // 10 처리하기 
                // 숫자가 마지막에 올 일 없어서 따로 처리 안해줌 
                if(ch =='1' && dartResult.charAt(i+1) =='0'){
                    culArr[++game] = 10; i++;
                }
                else culArr[++game] = ch- '0';
                
            }
            else if(ch == 'D') {
                culArr[game] = (int)Math.pow(culArr[game],2);
            } 
            else if (ch == 'T') {
                culArr[game] = (int)Math.pow(culArr[game],3);
            }
            else if(ch == '*') {
                culArr[game] *= 2;
                if(game!=0) culArr[game-1] *= 2;
            } 
            else if(ch == '#') {
                culArr[game] = -culArr[game]; 
            }              
            
        }
        
        for(int res : culArr) answer+=res;
        return answer;
    }
}

import java.util.*;
//The following part should be completed by students.
//Students can modify anything except the class name and exisiting functions and varibles.
public class StudentAI extends AI 
{
  private HashMap<String,Integer> scoremap=new HashMap<>();
  private HashMap<String,Integer> oppo_scoremap=new HashMap<>();
  private String[][] studentBoard;

 public StudentAI(int col, int row, int k, int g)
 {
  super(col, row, k, g);
  //intialize move score
  if(k==5) {
   scoremap.put("ooooo", 50000);
   scoremap.put("+oooo+", 4320);

   scoremap.put("+ooo++", 720);
   scoremap.put("++ooo+", 720);

   scoremap.put("+oo+o+", 720);
   scoremap.put("+o+oo+", 720);

   scoremap.put("oooo+", 720);
   scoremap.put("+oooo", 720);

   scoremap.put("oo+oo", 720);
   scoremap.put("o+ooo", 720);

   scoremap.put("ooo+o", 720);
   scoremap.put("++oo++", 120);

   scoremap.put("++o+o+", 120);
   scoremap.put("+o+o++", 120);

   scoremap.put("+++o++", 20);
   scoremap.put("++o+++", 20);

   oppo_scoremap.put("xxxxx", 50000);
   oppo_scoremap.put("+xxxx+", 4320);

   oppo_scoremap.put("+xxx++", 720);
   oppo_scoremap.put("++xxx+", 720);

   oppo_scoremap.put("+xx+x+", 720);
   oppo_scoremap.put("+x+xx+", 720);

   oppo_scoremap.put("xxxx+", 720);
   oppo_scoremap.put("+xxxx", 720);

   oppo_scoremap.put("xx+xx", 720);
   oppo_scoremap.put("x+xxx", 720);

   oppo_scoremap.put("xxx+x", 720);
   oppo_scoremap.put("++xx++", 120);

   oppo_scoremap.put("++x+x+", 120);
   oppo_scoremap.put("+x+x++", 120);

   oppo_scoremap.put("+++x++", 20);
   oppo_scoremap.put("++x+++", 20);
  }
  if(k==4){
   scoremap.put("oooo", 50000);
   scoremap.put("+ooo+", 4320);

   scoremap.put("+oo++", 720);
   scoremap.put("++oo+", 720);

   scoremap.put("ooo+", 720);
   scoremap.put("+ooo", 720);

   scoremap.put("o+oo", 720);
   scoremap.put("oo+o", 720);

   scoremap.put("+o+o+", 120);

   scoremap.put("++o++", 20);
   scoremap.put("+++o+", 20);
   scoremap.put("+o+++", 20);
   oppo_scoremap.put("xxxx", 50000);
   oppo_scoremap.put("+xxx+", 4320);

   oppo_scoremap.put("+xx++", 720);
   oppo_scoremap.put("++xx+", 720);

   oppo_scoremap.put("xxx+", 720);
   oppo_scoremap.put("+xxx", 720);

   oppo_scoremap.put("x+xx", 720);
   oppo_scoremap.put("xx+x", 720);

   oppo_scoremap.put("+x+x+", 120);

   oppo_scoremap.put("++x++", 20);
   oppo_scoremap.put("+++x+", 20);
   oppo_scoremap.put("+x+++", 20);
  }
  studentBoard=new String[row][col];
  for(int j=0;j<row;j++){
    for(int i=0;i<col;i++){
      studentBoard[i][j]="+";
    }
  }
 }
 public Move GetMove(Move move)
 {
  //update opponent's move
  Move studentMove=new Move();
  int max_score=Integer.MIN_VALUE;
   if(move.row==-1){
    studentMove.row=row/2;
    studentMove.col=col/2;
    if(g==1) studentMove=validMove(studentMove,"o");
    studentBoard[studentMove.row][studentMove.col]="o";
    return studentMove;
   }
   if(g==1) move=validMove(move,"x");
   int move_r=move.row,move_c=move.col;
   studentBoard[move_r][move_c]="x"; 
   // System.out.println("before AI:");
   // printBoard(studentBoard);
   for(Move nextmove: validMoves(studentBoard)){
      String[][] newBoard=clone(studentBoard);
      //if(g==1) nextmove=validMove(nextmove,"o");
      newBoard[nextmove.row][nextmove.col]="o";
      int score = iterativeDeepeningSearch(newBoard);
      // if (score >= 50000) {
      //   return nextmove;
      // }
      if (score > max_score) {
        max_score = score;
        studentMove = nextmove;
      }
   }
   if(g==1)
    studentMove=validMove(studentMove,"o");
   studentBoard[studentMove.row][studentMove.col]="o";
   //System.out.println("After AI:");
   //printBoard(studentBoard);
   //System.out.println("aiMove: "+studentMove.col+" "+studentMove.row);
   return studentMove;
 }

public ArrayList<Move> validMoves(String[][] board){
  ArrayList<Move> validmoves=new ArrayList<Move>();
  if(g == 0) {
    
    for(int i=0;i<board.length;i++)
      for(int j=0;j<board[i].length;j++){
        if(board[i][j].equals("+"))
          validmoves.add(new Move(j,i));
      }
  }
  else {
    for(int j=col-1;j>=0;j--){
      int i=row-1;
      while(i>=0&&!board[i][j].equals("+")){
        i--;
      }
      if(i>=0)
       validmoves.add(new Move(j,i));
    }
  }
  return validmoves;
}

public String[][] clone(String[][] board){
  String[][] clonedBoard=new String[row][col];
  for(int i=0;i<row;i++){
    for(int j=0;j<col;j++){
      clonedBoard[i][j]=board[i][j];    
    }
  }
  return clonedBoard;
}

private int iterativeDeepeningSearch(String[][] state) {
    
    int depth = 1;
    int score = 0;
    boolean isMyMove=false;
    
    while (depth<4) {
      
      
      int searchResult = search(state, depth, Integer.MIN_VALUE, Integer.MAX_VALUE,isMyMove);
      
      if (searchResult >= 50000) {
        return searchResult;
      }
      
      score = searchResult;
      
      depth++;
    }
    
    return score;
  }

  private int search(String[][] state, int depth, int alpha, int beta, boolean MyMove) {
    ArrayList<Move> moves = validMoves(state);
    int score = eval(MyMove, state);
    
    if ((depth == 0) || (moves.size() == 0) || (score >= 50000) || (score <= -50000)) {
      return score;
    }
    
    if (MyMove) {
      for (Move move : moves) {
        String[][] childState = clone(state);
        childState[move.row][move.col]="o";
        alpha = Math.max(alpha, search(childState, depth - 1, alpha, beta, !MyMove));
        
        if (beta <= alpha) {
          break;
        }
      }
      return alpha;
    } else {
      for (Move move : moves) {
        String[][] childState = clone(state);
        childState[move.row][move.col]="x";
        beta = Math.min(beta, search(childState, depth - 1, alpha, beta,!MyMove));
          
        if (beta <= alpha) {
          break;
        }
      }
      return beta;
    }
  }

public int eval(boolean MyMove,String[][] state){
  int score=0;
  for (int i=0;i<row ;i++ ) {
    score+=caculateAll(state[i],MyMove);
  }
  for(int i=0;i<col;i++){
    String[] col_i=new String[row];
    for(int j=0;j<row;j++){
      col_i[j]=state[j][i];
    }
    score+=caculateAll(col_i,MyMove);
  }
  for(int i=0;i<col;i++){
    String[] diag_0=diag(0,i,0);
    score+=caculateAll(diag_0,MyMove);
    String[] diag_1=diag(1,i,0);
    score+=caculateAll(diag_1,MyMove);
  }
  for(int j=1;j<row;j++){
    String[] diag_0=diag(0,col-1,j);
    score+=caculateAll(diag_0,MyMove);
    String[] diag_1=diag(1,col-1,j);
    score+=caculateAll(diag_1,MyMove);
  }
  // printBoard(state);
  // System.out.println("score: "+ score);
  return score;
}

public int caculateAll(String[] list,boolean MyMove){
int my=1,oppo=-1;
  // if(MyMove){
  //   my=-1*my;
  //   oppo=-1*oppo;
  // }
  StringBuilder sb = new StringBuilder();
  for (String s : list)
  {
    sb.append(s);
  }
  String str=sb.toString();
  int score=0;
  for(String s:scoremap.keySet()){
   if(str.indexOf(s)!=-1){
    score+=my*scoremap.get(s);
   }
  }
  for(String s:oppo_scoremap.keySet()){
   if(str.indexOf(s)!=-1){
    score+=oppo*oppo_scoremap.get(s);
   }
  }

  return score;
}

 public String[] diag(int flag,int i,int j){
  ArrayList<String> list=new ArrayList<String>();
  if(flag==0){
    while(i>0&&j>0){
      i--;j--;
    }
    while(i<row-1&&j<col-1){
      list.add(studentBoard[i++][j++]);
    }
    list.add(studentBoard[i][j]);
  }
  else{
    while(i<row-1&&j>0){
      i++;
      j--;
    }
    while(i>0&&j<col-1){
      list.add(studentBoard[i--][j++]);
    }
    list.add(studentBoard[i][j]);
  }
  return list.toArray(new String[list.size()]);
}

public Move validMove(Move move,String player){
  for (int i = row - 1; i >= 0; --i)
      {
        if (studentBoard[i][move.col].equals("+"))
        {
          studentBoard[i][move.col]=player;
          move.row=i;
          break;
        }
      }
      return move;
  }
  public void printBoard(String[][] board){
    for(String[] rowi: board){
      for(String ele:rowi){
        System.out.print(ele+" ");
      }
      System.out.println();
    }
    System.out.println();
  }
  // public int eval2(String[][] studentBoard, boolean MyMove){
  //   int max_score=Integer.MIN_VALUE;
  //   int min_score=Integer.MAX_VALUE;
  //   if(MyMove){
  //     for(int i=0;i<row;i++){
  //     for(int j=0;j<col;j++){
  //       if(studentBoard[i][j].equals("+")){
  //         String[] row_i=studentBoard[i];
  //         int score=0;
  //         row_i[j]="o";
  //         score=caculate(row_i);
  //         String[] col_j=new String[row];
  //         for(int k=0;k<row;k++){
  //          col_j[k]=studentBoard[k][j];
  //         }
  //         score+=caculate(col_j);
  //         score+=caculate(diag(0,i,j));
  //         score+=caculate(diag(1,i,j));
  //         max_score=max_score>score?max_score:score;
  //         if(max_score==score){
  //           studentMove.row=i;
  //           studentMove.col=j;
  //         }
  //         row_i[j]="+";
  //         }
  //       }
  //     }
  //   }
  //   else{
  //     for(int i=0;i<row;i++){
  //     for(int j=0;j<col;j++){
  //       if(studentBoard[i][j].equals("+")){
  //         String[] row_i=studentBoard[i];
  //         int score=0;
  //         row_i[j]="x";
  //         score=caculate(row_i);
  //         String[] col_j=new String[row];
  //         for(int k=0;k<row;k++){
  //          col_j[k]=studentBoard[k][j];
  //         }
  //         score+=caculate(col_j);
  //         score+=caculate(diag(0,i,j));
  //         score+=caculate(diag(1,i,j));
  //         min_score=min_score<score?min_score:score;
  //         if(min_score==score){
  //           studentMove.row=i;
  //           studentMove.col=j;
  //         }
  //         row_i[j]="+";
  //         }
  //       }
  //     }
  //   }
  //   return 
  // }
}
package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private int step;
    private boolean turn,mode;
    private TextView mention,textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9;
    private String player1,player2;
    private Button resetButton, backButton;
    private Stack<TextView> textViewStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        step = 0;
        turn = true;
        mode = true;
        player1 = "〇";
        player2 = "x";
        mention = (TextView) findViewById(R.id.mention);
        textView1 = (TextView) findViewById(R.id.Button1);
        textView2 = (TextView) findViewById(R.id.Button2);
        textView3 = (TextView) findViewById(R.id.Button3);
        textView4 = (TextView) findViewById(R.id.Button4);
        textView5 = (TextView) findViewById(R.id.Button5);
        textView6 = (TextView) findViewById(R.id.Button6);
        textView7 = (TextView) findViewById(R.id.Button7);
        textView8 = (TextView) findViewById(R.id.Button8);
        textView9 = (TextView) findViewById(R.id.Button9);
        resetButton = (Button) findViewById(R.id.resetBtn);
        backButton = (Button) findViewById(R.id.backBtn);
        textViewStack = new Stack<>();
        TextView[][] textViewList = new TextView[][]{{textView1, textView2,textView3},{textView4,textView5,textView6},{textView7,textView8,textView9}};
        for (TextView[] viewList:textViewList) {
            for (TextView viewItem:viewList) {
                viewItem.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        // 去除掉点了两次同一个格子的情况
                        if (!(viewItem.getText().length() > 0) && mode) {
                            viewItem.setText(turn?player1:player2);
                            turn = !turn;
                            mention.setText("现在轮到:"+(turn ? player1 : player2)+"玩家");
                            step += 1;
                            textViewStack.push(viewItem);
                            if (step >= 5)
                                if (checkResult(textViewList)) {
                                    // 对局结束禁用棋盘
                                    mode = false;
                                    AlertDialog.Builder resultDialog = new AlertDialog.Builder(MainActivity.this);
                                    mention.setText("游戏结束,"+(turn ? player2 : player1)+"赢啦");
                                    resultDialog.setTitle("对局结果");
                                    resultDialog.setMessage("胜利的玩家是:" + (turn ? player2 : player1));
                                    resultDialog.setPositiveButton("好耶", null);
                                    resultDialog.setNegativeButton("再来一局", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            resetTictactoe(textViewList);
                                        }
                                    });
                                    resultDialog.show();
                                }
                            else if (step >= 9) {
                                    mode = false;
                                    AlertDialog.Builder resultDialog = new AlertDialog.Builder(MainActivity.this);
                                    mention.setText("游戏结束,平局啦");
                                    resultDialog.setTitle("对局结果");
                                    resultDialog.setMessage("平局啦");
                                    resultDialog.setPositiveButton("好耶", null);
                                    resultDialog.setNegativeButton("再来一局", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            resetTictactoe(textViewList);
                                        }
                                    });
                                    resultDialog.show();
                            }
                        }
                    }
                });
            }
        }
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTictactoe(textViewList);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = true;
                if(step > 0) {
                    backMethod().setText("");
                    mention.setText((turn ? player1 : player2) + "玩家悔棋");
                } else {
                    mention.setText("还没下就想悔棋？");
                }
            }
        });
    }

    private boolean checkResult(TextView[][] textViews){
        if (Math.abs(exchangeValue(textViews[0][0]) + exchangeValue(textViews[1][1]) + exchangeValue(textViews[2][2])) == 3 ||
                Math.abs(exchangeValue(textViews[2][0]) + exchangeValue(textViews[1][1]) + exchangeValue(textViews[0][2])) == 3)
            return true;
        for (int i = 0; i < 3; i++)
            if (Math.abs(exchangeValue(textViews[i][0]) + exchangeValue(textViews[i][1]) + exchangeValue(textViews[i][2])) == 3 ||
                    Math.abs(exchangeValue(textViews[2][i]) + exchangeValue(textViews[1][i]) + exchangeValue(textViews[0][i])) == 3)
                return true;
        return false;
    }

    private int exchangeValue(TextView input) {
        String result = (String) input.getText();
        if (result.equals(player1)) return 1;
        else if(result.equals(player2)) return -1;
        else return 0;
    }

    private void resetTictactoe(TextView[][] textViewList) {
        // 解除棋盘禁用状态
        mode = true;
        for (TextView[] viewList:textViewList) {
            for (TextView viewItem : viewList) {
                viewItem.setText("");
                mention.setText("井字棋小游戏");
            }
        }
        while (!textViewStack.empty()) {
            textViewStack.pop();
        }
        step = 0;
        turn = true;
    }

    private TextView backMethod() {
        turn = ! turn;
        step -= 1;
        return textViewStack.pop();
    }
}
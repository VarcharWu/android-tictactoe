课程名称：      移动互联网软件开发    实验类型：        设计型         
实验项目名称：              界面布局与UI控件设计                                         
实验地点：     Library   实验日期：   2023  年    3  月   29  日

一、实验目的和要求 
通过本实验，考查学生应用Android Studio进行界面布局与UI控件设计的实践能力，使学生掌握基于XML文件完成Activity布局的方法，掌控常用的Activity布局方式，掌握常用UI组件在布局文件中的使用与编程技巧，以及相关的事件处理方法。
二、实验环境
	系统开发平台：Android
	系统开发环境：Android Studio
	运行平台：Windows操作系统
三、实验要求
1.	使用常用的UI控件和布局方式完成一个Activity界面，设计一个简单的应用。
2.	界面中需包含常用的UI控件，根据设计需要自由添加，使用多种类型的UI控件进行设计。
3.	利用UI控件的布局和事件处理实现应用设计的功能。
4.	恰当美化界面，在界面底部显示本人信息。
四、实验步骤
	阐述应用的设计思路，包括使用的布局方式、UI控件的作用、应用实现的功能。
	图文结合阐述实验的操作步骤。
1、	页面基本布局设计 
页面最外层采用线性布局，设置子元素垂直居中。
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    tools:context=".MainActivity">
其次设置了标题文字，与此同时，这个textview还起到了作为游戏提示的一个作用，我会在后文局面判断中详细阐述。
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center_horizontal"
    android:text="井字棋小游戏"
    android:fontFamily="@font/my_font"
    android:id="@+id/mention"
    android:textSize="40sp"/>
后面使用网格布局生成3x3的矩阵。使用网格布局的一个好处就是，可以使用userDefaultMargin将每一个textview自动添加一点边距，使得棋盘更加美观。同时通过规定行数和列数的方式使得3x3的布局变得简单，极大减少了布局的代码量。
<GridLayout
    android:layout_width="385dp"
    android:layout_height="385dp"
    android:columnCount="3"
    android:rowCount="3"
    android:layout_gravity="center"
    android:background="@color/black"
    android:useDefaultMargins="true">
    <TextView
        android:id="@+id/Button1"
        android:text=""
        android:fontFamily="@font/my_font"
        android:textSize="100sp"
        android:gravity="center"
        android:layout_height="120dp"
        android:layout_width="120dp"
        android:background="#fff" />
最下方使用约束布局，将两个按钮平均分布在两端。
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="380dp"
    android:layout_height="wrap_content"
    android:layout_marginTop="10dp">

    <Button
        android:id="@+id/backBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="112dp"
        android:text="我要悔棋"
        android:textSize="25dp"
        android:backgroundTint="#FF9800"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@+id/resetBtn"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/resetBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="我要重来"
        android:textSize="25dp"
        android:backgroundTint="#4CAF50"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/backBtn"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
2、	设置TextView监听与改变
九宫格的其实是由9个TextView组成，当我们点击了TextView之后就会发生点击事件。为了简化代码，我设计为二维数组形式存储，然后设置了双重for循环对其添加监听。
点击后直接发生改变，设置TextView的文本。
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
3、	设计局面判断算法
前文提及，我将textview设置为二维数组，这是便于为对于3x3矩阵的判断，核心算法代码如下：
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
第一个if判断是否出现斜边为3个同样标记，如果是，则返回结束信号。
第二个if判断每列/每行是否出现3个相同标记，如果是，则返回结束信号。
其他情况返回false。（此处处理，我将player1视为1，player2视为-1，空白视为0）
例如下面棋盘其实等价于对该二维数组的处理：
 				 
此时斜边对绝对值为3，则判断turn的上一手玩家胜利。
4、	设置对局结算弹窗
这里就使用了一个alertdialog，在做的过程就是忘记加show()函数了导致一直没有出现……在checkResult函数判断结果出现时就结束对局弹窗。
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
5、	设计“悔棋”、“重开”功能
先说简单的重开功能，下面是核心代码
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
思想就是，把所有的textview循环一次然后给文本内容换成空。这里代码有写了判断空栈，其实是给悔棋使用的。下面是悔棋的核心代码：
private TextView backMethod() {
    turn = ! turn;
    step -= 1;
    return textViewStack.pop();
}
当我们每走一步棋，就会将一个textview入栈，如果悔棋的话，就交换会上一步的棋手，步数-1，同时利用栈后进先出的性质，回到上一次的状态。
在这里也解释了上面的代码为什么要设计统计步数step，一是因为我们最少要走5步才能赢，因此我们判断结果的函数需要放到5步及其以上才能判断，二是当步数到了9步我们就已经结束了，如果在过程中并没有结束游戏那么就可以判断为平局，三是判断是否还能悔棋，如果step==0就休想悔棋，会出现栈空出栈的报错。
6、	异常处理（重复点击、结束禁用棋盘）
当用户重复点击已经有下过棋子的textview时，我们就不让棋子状态变化，核心语句是
if (!(viewItem.getText().length() > 0) && mode) {
这边的mode其实是设置为限制棋盘的，因为当用户结算了之后，就不能继续在已经结束了的棋盘上下棋，所以要设置mode使得棋盘禁用。
五、实验结果
	程序运行的界面和结果的截图。
  
六、实验问题及总结
	实验过程中遇到的问题和解决方案，对应用的设计、改进和优化策略，对实验的总结和体会。

	在本次实验中，我遇到了以下问题：
1、	设置时逻辑疏漏
在设计算法时，我第一次设计邻接矩阵的时候是将值设为0和1，导致不容易找到游戏结束通解。后面参考了leetcode其中的一题，发现其实设计为相反数可以使得业务简化。在设计为相反数之后，忘记对结果取绝对值导致后手x玩家不能判断成功。因此在设计时还是要按流程，写完算法之后考虑完善每一个测试用例。
 
2、	对页面的渲染逻辑不清晰导致程序无法正常启动
我一开始设置玩每一个TextView的时候，其实把代码写在这几句前面了。
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
因为ContentView并没有生成出来，我其实使用findElementById()函数是取不到他的对象的，所以在运行的过程中会报空指针异常。后来就将所有业务的代码移到后面去了，在这里没有考虑清楚业务的时序。
	总体做本次实验的时候还是非常的顺利，基本上一气呵成，主要在本次实验中学习到了如何设计优秀的数据结构使得程序简化。但是同时有一个不足之处，就是对每一个TextView加了监听之后会导致性能降低，其实可以考虑捕捉屏幕触摸点的x,y坐标来判断到底点到了哪里，可能会提高程序运行效率。其次，这个逻辑还较为简单，如果有时间可以拓展此井字棋模型到五子棋，会更有挑战性！
![image](https://github.com/VarcharWu/android-tictactoe/assets/101856130/c05a9374-db6c-46e3-84b0-938926dd2fa1)

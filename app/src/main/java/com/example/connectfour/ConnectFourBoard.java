package com.example.connectfour;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.connectfour.game.Piece;
import com.example.connectfour.game.UserPlayer;
import com.example.connectfour.game.Validator;

import java.util.ArrayList;

public class ConnectFourBoard extends View {

    private final int boardColour;
    private final int playerOneColour;
    private final int playerTwoColour;
    private final int winningLineColour;

    private final Paint paint = new Paint();


    private int columnSpacing = getWidth()/7;
    private int rowSpacing = getHeight()/6;

    public boolean enableClick;

    public ConnectFourBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ConnectFourBoard, 0, 0);

        try {
            boardColour = a.getInteger(R.styleable.ConnectFourBoard_boardColour, 0);
            playerOneColour = a.getInteger(R.styleable.ConnectFourBoard_PlayerOneColour, 0);
            playerTwoColour = a.getInteger(R.styleable.ConnectFourBoard_PlayerTwoColour, 0);
            winningLineColour = a.getInteger(R.styleable.ConnectFourBoard_WinningLineColour, 0);
        } finally {
            a.recycle();
        }

        enableClick = true;
    }

    ConnectFourViewModel getViewModel() {
        MainGameActivity mainGameActivity =  (MainGameActivity) getContext();
        if(mainGameActivity.gameViewModel == null) {
            return null;
        }
        else {
            return mainGameActivity.gameViewModel;
        }
    }

    @Override
    protected  void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int mHeight = getMeasuredHeight();
        int mWidth = getMeasuredWidth();
        columnSpacing = (mHeight-100)/8;
        rowSpacing = mWidth/7 ;
        setMeasuredDimension(mHeight, mWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN) {


            int col = (int) Math.ceil((x-columnSpacing)/columnSpacing) - 1;

            if(col > 6) { col = 6;}
            if(col < 0) { col = 0;}


            // TODO: add piece to board here
            ConnectFourViewModel viewModel = getViewModel();
            if(viewModel == null) {
                return false;
            }
            if(!(viewModel.game.getCurrentPlayer() instanceof UserPlayer) ) {
                return false;
            }
            if(!enableClick) {return false;}

            viewModel.onBoardUpdate(col);
            invalidate();

            return true;
        }

        return false;

    }

    private void drawGameBoard(Canvas canvas) {

        paint.setColor(boardColour);
        paint.setStrokeWidth(10);

        canvas.drawRoundRect(columnSpacing, 10, columnSpacing*8, rowSpacing*6 + 10, 8, 8, paint);
        paint.setStrokeWidth(8);
        for(int column=2; column<=7; column++){
            canvas.drawLine((columnSpacing*column), 10, (columnSpacing*column), canvas.getHeight() + 10 - rowSpacing, paint);
        }

        for(int row=1; row<6; row++) {
            canvas.drawLine(columnSpacing, 10 + (rowSpacing*row), columnSpacing*8, 10+(rowSpacing*row), paint);
        }

        ConnectFourViewModel viewModel = getViewModel();
        if(viewModel == null) {
            return;
        }
        else {
            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 6; y++) {
                    Piece.Type piece = viewModel.game.getBoard().getPiece(x, y);
                    drawPiece(canvas, x, y, piece);
                }
            }
        }

    }

    private void drawPiece(Canvas canvas, int xPosition, int yPosition, Piece.Type pieceType){
        paint.setAntiAlias(true);
        switch(pieceType) {
            case Red:
                paint.setColor(playerOneColour);
                break;
            case Blue:
                paint.setColor(playerTwoColour);
                break;
            default:
                return;

        }
        paint.setStrokeWidth(4);
        int boardOffsetX = columnSpacing;
        int boardOffsetY = 10;
        int circleRadius = (int)(columnSpacing*0.3);

        int pieceXPosition = (columnSpacing * xPosition) + (boardOffsetX + columnSpacing/2);
        int pieceYPosition = (rowSpacing * (5-yPosition)) + (boardOffsetY + rowSpacing/2);

        canvas.drawCircle(pieceXPosition, pieceYPosition, circleRadius, paint);
    }

    public void highlightWin(){
        ConnectFourViewModel viewModel = getViewModel();
        int[] columns = viewModel.game.getBoard().getTopOfColumns();
        for(int col : columns){
        }

    }
}

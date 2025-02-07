package com.example.connectfour.game;

import java.util.Arrays;
import java.util.Collections;

public class Validator {

    public static Streak isWinningMove(int x_pos, Board board){
        Streak[] countArray = new Streak[10];
        int y_pos = board.getTopOfColumns()[x_pos];
        y_pos = y_pos>0 ? y_pos-1 : y_pos;

        //Left
        Piece.Type leftType = board.getPiece(x_pos, y_pos);
        countArray[0] = new Streak(countLeft(x_pos, y_pos, leftType, board), leftType);

        //Right
        Piece.Type rightType = board.getPiece(x_pos, y_pos);
        countArray[1] = new Streak(countRight(x_pos, y_pos, rightType, board), rightType);

        // Left + Right
        // Have to account for (x_pos, y_pos) being counted twice!
        countArray[2] = (leftType != Piece.Type.Empty) && (leftType == rightType) ?
                new Streak(countArray[0].count + countArray[1].count-1, leftType)
                    : new Streak(0, Piece.Type.Empty);

        // Forwards Diagonal
        Piece.Type forwardsTypeUp = board.getPiece(x_pos, y_pos);
        countArray[3] = new Streak(countForwardsDiagonalUp(x_pos, y_pos, forwardsTypeUp, board), forwardsTypeUp);

        Piece.Type forwardsTypeDown = board.getPiece(x_pos, y_pos);
        countArray[4] = new Streak(countForwardsDiagonalDown(x_pos, y_pos, forwardsTypeDown, board), forwardsTypeDown);

        // Forwards Up + Down
        // Have to account for (x_pos, y_pos) being counted twice!
        countArray[5] = (forwardsTypeUp != Piece.Type.Empty) && (forwardsTypeUp == forwardsTypeDown) ?
                new Streak(countArray[3].count + countArray[4].count-1, forwardsTypeUp)
                    : new Streak(0, Piece.Type.Empty);

        // Backwards Diagonal
        Piece.Type backwardsTypeUp = board.getPiece(x_pos, y_pos);
        countArray[6] = new Streak(countBackwardsDiagonalUp(x_pos, y_pos, backwardsTypeUp, board), backwardsTypeUp);

        Piece.Type backwardsTypeDown = board.getPiece(x_pos, y_pos);
        countArray[7] = new Streak(countBackwardsDiagonalDown(x_pos, y_pos, backwardsTypeDown, board), backwardsTypeDown);

        // Backwards Up + Down
        // Have to account for (x_pos, y_pos) being counted twice!
        countArray[8] = (backwardsTypeUp != Piece.Type.Empty) && (backwardsTypeUp == backwardsTypeDown) ?
                new Streak(countArray[6].count + countArray[7].count-1, backwardsTypeUp)
                    : new Streak(0, Piece.Type.Empty);

        // Down
        Piece.Type downType = board.getPiece(x_pos, y_pos);
        countArray[9] = new Streak(countDown(x_pos, y_pos, downType, board), downType);

        int max = 0;
        Streak maxStreak = new Streak(0, Piece.Type.Empty);
        for( Streak streak : countArray){
            if(streak.count >= max) {
                max = streak.count;
                maxStreak = streak;
            }
        }
        return maxStreak;
    }

    public static  Direction streakDirection (int x_pos, Board board){
        Streak[] countArray = new Streak[7];
        int y_pos = board.getTopOfColumns()[x_pos];
        y_pos = y_pos>0 ? y_pos-1 : y_pos;

        //Left
        Piece.Type leftType = board.getPiece(x_pos, y_pos);
        countArray[0] = new Streak(countLeft(x_pos, y_pos, leftType, board), leftType);

        //Right
        Piece.Type rightType = board.getPiece(x_pos, y_pos);
        countArray[1] = new Streak(countRight(x_pos, y_pos, rightType, board), rightType);

        // Forwards Diagonal
        Piece.Type forwardsTypeUp = board.getPiece(x_pos, y_pos);
        countArray[2] = new Streak(countForwardsDiagonalUp(x_pos, y_pos, forwardsTypeUp, board), forwardsTypeUp);

        Piece.Type forwardsTypeDown = board.getPiece(x_pos, y_pos);
        countArray[3] = new Streak(countForwardsDiagonalDown(x_pos, y_pos, forwardsTypeDown, board), forwardsTypeDown);


        // Backwards Diagonal
        Piece.Type backwardsTypeUp = board.getPiece(x_pos, y_pos);
        countArray[4] = new Streak(countBackwardsDiagonalUp(x_pos, y_pos, backwardsTypeUp, board), backwardsTypeUp);

        Piece.Type backwardsTypeDown = board.getPiece(x_pos, y_pos);
        countArray[5] = new Streak(countBackwardsDiagonalDown(x_pos, y_pos, backwardsTypeDown, board), backwardsTypeDown);

        // Down
        Piece.Type downType = board.getPiece(x_pos, y_pos);
        countArray[6] = new Streak(countDown(x_pos, y_pos, downType, board), downType);

        int max = 0;
        int index = 0;
        int indexMax = 0;
        Streak maxStreak = new Streak(0, Piece.Type.Empty);
        for( Streak streak : countArray){
            if(streak.count >= max) {
                max = streak.count;
                maxStreak = streak;
                indexMax = index;
            }
            index++;
        }

        switch (indexMax){
            case 0:
                return Direction.Left;
            case 1:
                return Direction.Right;
            case 2:
                return Direction.BackwardsDiagonalUp;
            case 3:
                return Direction.BackwardsDiagonalDown;
            case 4:
                return Direction.ForwardDiagonalUp;
            case 5:
                return Direction.ForwardDiagonalDown;
            case 6:
                return Direction.Down;
            default:
                return Direction.Error;

        }
    }

    public static Streak getStreak(int x_pos, Board board){
        Streak[] countArray = new Streak[10];
        int y_pos = board.getTopOfColumns()[x_pos];
        //y_pos = y_pos>0 ? y_pos-1 : y_pos;

        //Left
        Piece.Type leftType = board.getPiece(x_pos-1, y_pos);
        countArray[0] = new Streak(countLeft(x_pos-1, y_pos, leftType, board) + 1, leftType);

        //Right
        Piece.Type rightType = board.getPiece(x_pos+1, y_pos);
        countArray[1] = new Streak(countRight(x_pos+1, y_pos, rightType, board) + 1, rightType);

        // Left + Right
        // Have to account for (x_pos, y_pos) being counted twice!
        // ...but only need to calculate if there is a gap!
        Piece.Type leftRightType = board.getPiece(x_pos, y_pos);
        countArray[2] = (leftRightType == Piece.Type.Empty)
                && (leftType != Piece.Type.Empty) && (leftType == rightType) ?
                    new Streak(countArray[0].count + countArray[1].count - 1, leftType)
                    : new Streak(0, Piece.Type.Empty);

        // Forwards Diagonal
        Piece.Type forwardsTypeUp = board.getPiece(x_pos+1, y_pos+1);
        countArray[3] = new Streak(countForwardsDiagonalUp(x_pos+1, y_pos+1, forwardsTypeUp, board) + 1, forwardsTypeUp);

        Piece.Type forwardsTypeDown = board.getPiece(x_pos-1, y_pos-1);
        countArray[4] = new Streak(countForwardsDiagonalDown(x_pos-1, y_pos-1, forwardsTypeDown, board) + 1, forwardsTypeDown);

        // Forwards Up + Down
        // Have to account for (x_pos, y_pos) being counted twice!
        // ...but only need to calculate if there is a gap!
        Piece.Type upDownType = board.getPiece(x_pos, y_pos);
        countArray[5] = (upDownType == Piece.Type.Empty) && (forwardsTypeUp != Piece.Type.Empty) && (forwardsTypeUp == forwardsTypeDown) ?
                new Streak(countArray[3].count + countArray[4].count - 1, forwardsTypeUp)
                : new Streak(0, Piece.Type.Empty);

        // Backwards Diagonal
        Piece.Type backwardsTypeUp = board.getPiece(x_pos-1, y_pos+1);
        countArray[6] = new Streak(countBackwardsDiagonalUp(x_pos-1, y_pos+1, backwardsTypeUp, board) + 1, backwardsTypeUp);

        Piece.Type backwardsTypeDown = board.getPiece(x_pos+1, y_pos-1);
        countArray[7] = new Streak(countBackwardsDiagonalDown(x_pos+1, y_pos-1, backwardsTypeDown, board) + 1, backwardsTypeDown);

        // Backwards Up + Down
        // Have to account for (x_pos, y_pos) being counted twice!
        // ...but only need to calculate if there is a gap!
        countArray[8] = (upDownType == Piece.Type.Empty) && (backwardsTypeUp != Piece.Type.Empty) && (backwardsTypeUp == backwardsTypeDown) ?
                new Streak(countArray[6].count + countArray[7].count - 1, backwardsTypeUp)
                : new Streak(0, Piece.Type.Empty);

        // Down
        Piece.Type downType = board.getPiece(x_pos, y_pos-1);
        countArray[9] = new Streak(countDown(x_pos, y_pos-1, downType, board) + 1, downType);

        int max = 0;
        Streak maxStreak = new Streak(0, Piece.Type.Empty);
        for( Streak streak : countArray){
            if(streak.count >= max) {
                max = streak.count;
                maxStreak = streak;

            }
        }
        return maxStreak;
    }

    static int countLeft(int origin_x, int origin_y, Piece.Type currentPieceType, Board board){
        int count = 0;
        if(currentPieceType == Piece.Type.Empty)
        {
            return count;
        }

        if(board.getPiece(origin_x, origin_y) == currentPieceType)
        {
            count++;
            if(board.getPiece(origin_x - 1, origin_y) == currentPieceType)
            {
                count++;
                if(board.getPiece(origin_x - 2, origin_y) == currentPieceType)
                {
                    count++;
                    if(board.getPiece(origin_x - 3, origin_y) == currentPieceType)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    static int countRight(int origin_x, int origin_y, Piece.Type currentPieceType, Board board){
        int count = 0;
        if(currentPieceType == Piece.Type.Empty)
        {
            return count;
        }

        if(board.getPiece(origin_x, origin_y) == currentPieceType)
        {
            count++;
            if(board.getPiece(origin_x + 1, origin_y) == currentPieceType)
            {
                count++;
                if(board.getPiece(origin_x + 2, origin_y) == currentPieceType)
                {
                    count++;
                    if(board.getPiece(origin_x + 3, origin_y) == currentPieceType)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    static int countBackwardsDiagonalUp(int origin_x, int origin_y, Piece.Type currentPieceType, Board board){
        int count = 0;
        if(currentPieceType == Piece.Type.Empty)
        {
            return count;
        }

        if(board.getPiece(origin_x, origin_y) == currentPieceType)
        {
            count++;
            if(board.getPiece(origin_x - 1, origin_y + 1) == currentPieceType)
            {
                count++;
                if(board.getPiece(origin_x - 2, origin_y + 2) == currentPieceType)
                {
                    count++;
                    if(board.getPiece(origin_x - 3, origin_y + 3) == currentPieceType)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    static int countBackwardsDiagonalDown(int origin_x, int origin_y, Piece.Type currentPieceType, Board board){
        int count = 0;
        if(currentPieceType == Piece.Type.Empty)
        {
            return count;
        }

        if(board.getPiece(origin_x, origin_y) == currentPieceType)
        {
            count++;
            if(board.getPiece(origin_x + 1, origin_y - 1) == currentPieceType)
            {
                count++;
                if(board.getPiece(origin_x + 2, origin_y - 2) == currentPieceType)
                {
                    count++;
                    if(board.getPiece(origin_x + 3, origin_y - 3) == currentPieceType)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    static int countForwardsDiagonalUp(int origin_x, int origin_y, Piece.Type currentPieceType, Board board){
        int count = 0;
        if(currentPieceType == Piece.Type.Empty)
        {
            return count;
        }

        if(board.getPiece(origin_x, origin_y) == currentPieceType)
        {
            count++;
            if(board.getPiece(origin_x + 1, origin_y + 1) == currentPieceType)
            {
                count++;
                if(board.getPiece(origin_x + 2, origin_y + 2) == currentPieceType)
                {
                    count++;
                    if(board.getPiece(origin_x + 3, origin_y + 3) == currentPieceType)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    static int countForwardsDiagonalDown(int origin_x, int origin_y, Piece.Type currentPieceType, Board board){
        int count = 0;
        if(currentPieceType == Piece.Type.Empty)
        {
            return count;
        }

        if(board.getPiece(origin_x, origin_y) == currentPieceType)
        {
            count++;
            if(board.getPiece(origin_x - 1, origin_y - 1) == currentPieceType)
            {
                count++;
                if(board.getPiece(origin_x - 2, origin_y - 2) == currentPieceType)
                {
                    count++;
                    if(board.getPiece(origin_x - 3, origin_y - 3) == currentPieceType)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    static int countDown(int origin_x, int origin_y, Piece.Type currentPieceType, Board board){
        int count = 0;
        if(currentPieceType == Piece.Type.Empty)
        {
            return count;
        }

        if(board.getPiece(origin_x, origin_y) == currentPieceType)
        {
            count++;
            if(board.getPiece(origin_x, origin_y - 1) == currentPieceType)
            {
                count++;
                if(board.getPiece(origin_x, origin_y - 2) == currentPieceType)
                {
                    count++;
                    if(board.getPiece(origin_x, origin_y - 3) == currentPieceType)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

}

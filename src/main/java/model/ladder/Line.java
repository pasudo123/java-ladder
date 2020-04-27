package model.ladder;

import model.moving.MovingType;
import model.player.Players;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Line {

    private static final int START_POINT = 0;
    private static final int DEFAULT_INTERVAL = 5;
    private List<Point> points = new ArrayList<>();

    public static Line of(Players players) {
        final Line line = new Line();
        line.createStartBarByPlayers((players.getPlayerCount() - 1), START_POINT);
        return line;
    }

    private void createStartBarByPlayers(final int totalPoint, final int currentPoint){
        points.add(new Point(PointState.BAR, currentPoint));

        if(totalPoint == currentPoint){
            return;
        }

        PointState currentPointState = Point.generateStateByRandom();

        if(isBeforeLine()){
            currentPointState = PointState.SPACE;
        }

        createIntervalByPoint(currentPointState);
        createStartBarByPlayers(totalPoint, (currentPoint + 1));
    }


    private void createIntervalByPoint(PointState state){
        IntStream.rangeClosed(1, DEFAULT_INTERVAL)
                .forEach(i -> points.add(new Point(state)));
    }

    private boolean isBeforeLine(){
        if(points.size() == 1){
            return false;
        }

        return points.get(points.size() - 2)
                .isDash();
    }

    public List<String> toDisplays(){
        return points.stream()
                .map(Point::getDisplay)
                .collect(Collectors.toList());
    }

    public int getPosition(int colIndex){
        if(isMovableLeft(leftOnePosition(colIndex))){
            return moveSideIfPossibleElseNot(MovingType.LEFT, colIndex);
        }
        if(isMovableRight(rightOnePosition(colIndex))){
            return moveSideIfPossibleElseNot(MovingType.RIGHT, colIndex);
        }
        return colIndex;
    }

    private int moveSideIfPossibleElseNot(MovingType movingType, int colIndex){

        int newPosition = movingType.move(colIndex);

        if(isMovableLeft(newPosition) && isMovableRight(newPosition)){
            return moveSideIfPossibleElseNot(movingType, newPosition);
        }

        return newPosition;
    }

    public int convertPlayerIndexToPosition(int playerIndex) {
        return (playerIndex * DEFAULT_INTERVAL + playerIndex);
    }

    public int convertPositionToPlayerIndex(int position) {
        return (position % DEFAULT_INTERVAL == 0)
                ? (position - 1) / DEFAULT_INTERVAL
                : position / DEFAULT_INTERVAL;
    }

    private int leftOnePosition(int currentPos){ return currentPos - 1; }
    private int rightOnePosition(int currentPos){ return currentPos + 1; }
    private boolean isMovableLeft(int position){
        return (position >= 0) && points.get(position).isDash();
    }

    private boolean isMovableRight(int position) {
        return (position < points.size()) && points.get(position).isDash();
    }
}

package main;

import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Timer {
	public boolean timeIsOver = false;
	private ChessBoard chessboard;
	
	public Timer(ChessBoard chessboard) {
		this.chessboard = chessboard;
	}
	
	public Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler <> ( ) {
		@Override
		public void handle ( ActionEvent event ) {
			User user = chessboard.getTurnUser ( );
			if (!timeIsOver) {
				user.secondPass ( );
				chessboard.getChessBar ( ).setTimer ( user.getName ( ) + "'s timer: " + TimeUnit.SECONDS.toMinutes ( user.getTimer ( ) ) + ":" + (user.getTimer ( ) % 60) );
			}
			if (!timeIsOver && (chessboard.whiteUser.getTimer () == 0 || chessboard.blackUser.getTimer () == 0)) {
				timeIsOver = true;
				chessboard.timerOver ( user );
			}
		}
	}));
}

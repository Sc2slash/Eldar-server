package server.utilities;

public class Timer {
	
	 double startTime;
	 double lastTime;
	 double delta;
	 
	 public Timer() {
		 startTime = 0;
		 lastTime = 0;
		 delta = 0;
	 }
	 
	 public void start() {
		 startTime = System.nanoTime();
		 lastTime = startTime;
	 }
	 
	 public void update() {
		 lastTime = System.nanoTime();
	 }
	 
	 public double getTime() {
		 return System.nanoTime()-lastTime;
	 }
	 
	 public double getTimeS() {
		 return (System.nanoTime()-lastTime)/1000000000.0;
	 }
	}

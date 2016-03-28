package com;

import com.ReadThread;
import com.WriteThread;

public class Multithread {

			public static void main(String[] args) {
				ReadThread readThread = new ReadThread();
				readThread.start();
				
				WriteThread writeThreadOne = new WriteThread();
				WriteThread writeThreadTwo = new WriteThread();
				WriteThread writeThreadThree = new WriteThread();
				WriteThread writeThreadFour = new WriteThread();
				WriteThread writeThreadFive = new WriteThread();				
				writeThreadOne.start();
				writeThreadTwo.start();
				writeThreadThree.start();
				writeThreadFour.start();
				writeThreadFive.start();
			}
	}
	

//}
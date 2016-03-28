package com;



import com.File;
import com.DBConnection;
import com.ReadThread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class WriteThread extends Thread {
	public static final String SQL = "UPDATE PERSON.PERSON SET STATUS = 1 WHERE ID = ?";
	public static final String SUCCESS_SQL = "UPDATE PERSON.PERSON SET STATUS = 3 WHERE ID = ?";
	public static final String ERROR_SQL = "UPDATE PERSON.PERSON SET STATUS = 4 WHERE ID = ?";
	public static PreparedStatement stmt;
	public static Connection conn;

	static Logger log=Logger.getLogger(WriteThread.class.getName());

	public void run() {
		super.run();
		try {
			conn = DBConnection.getConenction();
			while(true) {
				Thread.sleep(1000);
				write();
			}
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void write() {
		File p = null;
		try {
			p = ReadThread.getFile();
			if(p != null) {
				
				//System.out.println(Thread.currentThread().getName() + " Working on "+ p);
				log.info(Thread.currentThread().getName() + " Working on "+ p);
				stmt = conn.prepareStatement(SQL);
				stmt.setInt(1, p.getID());
				stmt.executeUpdate();
				
				//System.out.println(Thread.currentThread().getName() + " Busy updating "+ p);
				log.info(Thread.currentThread().getName() + " Busy updating "+ p);
				Thread.sleep(1000); // Busy updating.
				stmt = conn.prepareStatement(SUCCESS_SQL);
				stmt.setInt(1, p.getID());
				stmt.executeUpdate();
				
				//System.out.println(Thread.currentThread().getName() + " Update complete for "+ p);
				log.info(Thread.currentThread().getName() + " Update complete for "+ p);
			}
		} catch (InterruptedException | SQLException e) {
			try {
				if(p!= null) {
					stmt = conn.prepareStatement(ERROR_SQL);
					stmt.setInt(1, p.getID());
					stmt.executeUpdate();
					e.printStackTrace();
					
					//System.out.println(Thread.currentThread().getName() + " Update failed for "+ p);
					log.info(Thread.currentThread().getName() + " Update failed for "+ p);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
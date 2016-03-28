package com;

import com.File;
import com.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ReadThread extends Thread {
	public static final String SQL = "SELECT * FROM PERSON.PERSON WHERE STATUS = 0";
	public static final Set<File> fileSet = new HashSet<File>();
	public static Statement stmt;
	public static Connection conn;

	static Logger log=Logger.getLogger(ReadThread.class.getName());
	@Override
		public void run() {
			super.run();
			try {
				conn = DBConnection.getConenction();
				stmt = conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//read continuously in the background
			while (true) {
				synchronized (fileSet) {
					read();
				}
				try {
					Thread.sleep(10000L); //read every 10 seconds.
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void read() {
			try {
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next()) {
					fileSet.add(new File(rs.getInt(1), rs.getString(2), rs
							.getInt(3)));
				}
				//System.out.println("Current size of new records : "+fileSet.size());
				log.info("Current size of new records : "+fileSet.size());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public static synchronized File getFile() throws InterruptedException {
			synchronized (fileSet) {
				if (!fileSet.isEmpty()) {
					System.out.println("lalala");
					fileSet.wait(1000L);
					File p = fileSet.iterator().next();
					ReadThread.fileSet.remove(p);
					fileSet.notifyAll();
					return p;
				}
			}
			return null;
		}
	}

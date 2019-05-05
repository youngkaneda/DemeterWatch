/* $Id: GenerateINI.java,v 1.1 2008/03/25 17:55:24 arianne_rpg Exp $ */
/***************************************************************************
 *						(C) Copyright 2003 - Marauroa					   *
 ***************************************************************************
 ***************************************************************************
 *																		   *
 *	 This program is free software; you can redistribute it and/or modify  *
 *	 it under the terms of the GNU General Public License as published by  *
 *	 the Free Software Foundation; either version 2 of the License, or	   *
 *	 (at your option) any later version.								   *
 *																		   *
 ***************************************************************************/
package marauroa.functional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import marauroa.common.crypto.RSAKey;

public class GenerateINI {

	/** Where data is read from */
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	/** The name of the output file */
	final static String filename = "server.ini";

	/**
	 * reads a String from the input. When no String is choosen the defaultValue
	 * is used.
	 * 
	 * @param input
	 *            the buffered input, usually System.in
	 * @param defaultValue
	 *            if no value is written.
	 * @return the string readed or default if none was read.
	 */
	public static String getStringWithDefault(BufferedReader input, String defaultValue) {
		String ret = "";
		try {
			ret = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		if (ret.length() == 0 && defaultValue != null) {
			ret = defaultValue;
		}
		return ret;
	}

	/**
	 * reads a String from the input. When no String is choosen the errorMessage
	 * is is displayed and the application is terminated.
	 * 
	 * @param input
	 *            the input stream, usually System.in
	 */
	public static String getStringWithoutDefault(BufferedReader input, String errorMessage) {
		String ret = "";
		try {
			ret = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		if (ret.length() == 0) {
			System.out.println(errorMessage);
			System.out.println("Terminating...");
			System.exit(1);
		}
		return ret;
	}

	/**
	 * makes the first letter of the source uppercase
	 * 
	 * @param source
	 *            the string
	 * @return *T*he string, with first letter is upper case.
	 */
	public static String uppcaseFirstLetter(String source) {
		return (source.length() > 0) ? Character.toUpperCase(source.charAt(0))
		        + source.substring(1) : source;
	}

	private static String gameName;

	private static String databaseName;

	private static String databaseHost;

	private static String databaseUsername;

	private static String databasePassword;

	private static String databaseImplementation;

	private static String factoryImplementation;

	private static String tcpPort;

	private static String worldImplementation;

	private static String ruleprocessorImplementation;

	private static String turnLength;

	private static String statisticsFilename;

	private static RSAKey rsakey;

	public static void main(String[] args) throws FileNotFoundException {
		gameName = "test";

		/** Write configuration for database */
		factoryImplementation = getFactoryImplementation();
		databaseImplementation = getDatabaseImplementation();
		databaseName = getDatabaseName();
		databaseHost = getDatabaseHost();
		databaseUsername = getDatabaseUsername();
		databasePassword = getDatabasePassword();

		System.out.println("Using \"" + databaseName + "\" as database name\n");
		System.out.println("Using \"" + databaseHost + "\" as database host\n");
		System.out.println("Using \"" + databaseUsername + "\" as database user\n");
		System.out.println("Using \"" + databasePassword + "\" as database user password\n");

		System.out.println("In order to make efective these options please run:");
		System.out.println("# mysql");
		System.out.println("  create database " + databaseName + ";");
		System.out.println("  grant all on " + databaseName + ".* to " + databaseUsername
		        + "@localhost identified by '" + databasePassword + "';");
		System.out.println("  exit");

		tcpPort = getTCPPort();

		worldImplementation = getWorldImplementation();
		ruleprocessorImplementation = getRuleProcessorImplementation();

		turnLength = getTurnLength();

		statisticsFilename = getStatisticsFilename();

		/* The size of the RSA Key in bits, usually 512 */
		String keySize = getRSAKeyBits();
		System.out.println("Using key of " + keySize + " bits.");
		System.out.println("Please wait while the key is generated.");
		rsakey = RSAKey.generateKey(Integer.valueOf(keySize));
		PrintWriter out = new PrintWriter(new FileOutputStream(filename));
		write(out);
		out.close();
	}

	private static String getRSAKeyBits() {
		System.out
		        .print("Write size for the RSA key of the server. Be aware that a key bigger than 1024 could be very long to create [512]: ");
		String keySize = getStringWithDefault(in, "512");
		return keySize;
	}

	private static String getStatisticsFilename() {
		return "./server_stats.xml";
	}

	private static String getTurnLength() {
		return "300";
	}

	private static String getRuleProcessorImplementation() {
		return "marauroa.test.MockRPRuleProcessor";
	}

	private static String getWorldImplementation() {
		return "marauroa.test.MockRPWorld";
	}

	private static String getFactoryImplementation() {
		return "marauroa.server.game.rp.RPObjectFactory";
	}

	private static String getTCPPort() {
		return "3217";
	}

	private static String getDatabaseImplementation() {
		return "marauroa.server.game.db.JDBCDatabase";
	}

	private static void write(PrintWriter out) {
		out.println("# Generated .ini file for Test Game at " + new Date());
		out.println("database_implementation=" + databaseImplementation);
		out.println("factory_implementation=" + factoryImplementation);
		out.println();
		out.println("jdbc_url=jdbc:mysql://" + databaseHost + "/" + databaseName);
		out.println("jdbc_class=com.mysql.jdbc.Driver");
		out.println("jdbc_user=" + databaseUsername);
		out.println("jdbc_pwd=" + databasePassword);
		out.println();
		out.println("tcp_port=" + tcpPort);
		out.println();
		out.println("world=" + worldImplementation);
		out.println("ruleprocessor=" + ruleprocessorImplementation);
		out.println();
		out.println("turn_length=" + turnLength);
		out.println();
		out.println("server_typeGame=" + gameName);
		out.println("server_name=" + gameName + " Marauroa server");
		out.println("server_version=0.01");
		out.println("server_contact=https://sourceforge.net/tracker/?atid=514826&group_id=66537&func=browse");
		out.println();
		out.println("statistics_filename=" + statisticsFilename);
		out.println();
		rsakey.print(out);
	}

	protected static String getDatabasePassword() {
		System.out.print("Write value of the database user password: ");
		String databasepassword = getStringWithoutDefault(in, "Please enter a database password");
		return databasepassword;
	}

	protected static String getDatabaseUsername() {
		System.out.print("Write name of the database user: ");
		String databaseuser = getStringWithoutDefault(in, "Please enter a database user");
		return databaseuser;
	}

	protected static String getDatabaseHost() {
		System.out.print("Write name of the database host [localhost]: ");
		String databasehost = getStringWithDefault(in, "localhost");
		return databasehost;
	}

	protected static String getDatabaseName() {
		System.out.print("Write name of the database [marauroa]: ");
		String databasename = getStringWithDefault(in, "marauroa");
		return databasename;
	}
}

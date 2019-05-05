/* $Id: CrushServer.java,v 1.2 2008/03/27 11:32:51 arianne_rpg Exp $ */
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
package marauroa.functional.load;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import marauroa.common.Configuration;
import marauroa.common.Log4J;
import marauroa.common.game.AccountResult;
import marauroa.common.game.CharacterResult;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
import marauroa.common.game.Result;
import marauroa.functional.IFunctionalTest;
import marauroa.functional.MarauroadLauncher;
import marauroa.functional.SimpleClient;

/**
 * Test the server and client framework y running lots of clients several times
 * against a server. The test is sucessfull if all client complete correctly.
 *
 * @author miguel
 *
 */
public class CrushServer implements IFunctionalTest {

	private static final int PORT = 3217;

	private static final int TIMES_TO_LOGIN = 10;

	private static int index;

	private int completed;

	private static final int NUM_CLIENTS = 150;

	private static final boolean DETACHED_SERVER = true;

	private static MarauroadLauncher server;
	
	public void setUp() throws Exception {
		if (!DETACHED_SERVER) {
			Log4J.init("log4j.properties");
			server = new MarauroadLauncher(PORT);
			Configuration.setConfigurationFile("src/marauroa/test/server.ini");

			try {
				Configuration.getConfiguration();
			} catch (Exception e) {
				fail("Unable to find configuration file");
			}

			server.start();
			Thread.sleep(2000);
		}
	}

	public void tearDown() throws Exception {
		if (!DETACHED_SERVER) {
			server.finish();
			Thread.sleep(2000);
		}
	}

	/**
	 * Test the perception management in game.
	 */
	public void launch() throws Exception {
		for (int i = 0; i < NUM_CLIENTS; i++) {
			new Thread() {

				int i;

				@Override
				public void run() {
					try {
						Random rand=new Random();
						System.out.println("Initing client");
						i = index++;
						SimpleClient client = new SimpleClient("client.properties");

						Thread.sleep(Math.abs(rand.nextInt() % 20) * 1000);

						client.connect("localhost", PORT);
						AccountResult resAcc = client.createAccount("testUsername" + i, "password","email");
						assertTrue("Account creation must not fail", !resAcc.failed());

						assertEquals("testUsername" + i, resAcc.getUsername());
						assertEquals(Result.OK_CREATED, resAcc.getResult());

						Thread.sleep(Math.abs(rand.nextInt() % 100) * 1000 + 5000);

						client.login("testUsername" + i, "password");

						RPObject template = new RPObject();
						template.put("client", "junit" + i);
						CharacterResult resChar = client.createCharacter("testCharacter", template);
						assertEquals("testCharacter", resChar.getCharacter());

						RPObject result = resChar.getTemplate();
						assertTrue(result.has("client"));
						assertEquals("junit" + i, result.get("client"));

						client.logout();

						for (int logins = 0; logins < TIMES_TO_LOGIN; logins++) {
							Thread.sleep(Math.abs(rand.nextInt() % 30) * 1000 + 5000);
							client.login("testUsername" + i, "password");

							String[] characters = client.getCharacters();
							assertEquals(1, characters.length);
							assertEquals("testCharacter", characters[0]);

							boolean choosen = client.chooseCharacter("testCharacter");
							assertTrue(choosen);

							int amount = Math.abs(rand.nextInt() % 90) + 10;
							while (client.getPerceptions() < amount) {
								client.loop(0);

								if (rand.nextInt() % 10 == 0) {
									/*
									 * Send an action to server.
									 */
									RPAction action = new RPAction();
									action.put("type", "chat");
									action.put("text", "Hello world");
									client.send(action);
								}

								if (rand.nextInt() % 1000 == 0) {
									/*
									 * Randomly close the connection
									 */
									System.out
									        .println("FORCED CLOSE CONNECTION: Testint random disconnects on server");
									client.close();
									return;
								}

								Thread.sleep(1000);
							}

							client.logout();
						}

						client.close();
						Thread.sleep(Math.abs(rand.nextInt() % 60) * 1000);

					} catch (Exception e) {
						System.out.println("Problem for player: testUsername" + i);
						e.printStackTrace();
						System.exit(-1);
					} finally {
						completed++;
					}
				}
			}.start();
		}

		while (completed != NUM_CLIENTS) {
			Thread.sleep(10000);
			System.out.println("Still missing to complete: " + (NUM_CLIENTS - completed));
		}
	}
}

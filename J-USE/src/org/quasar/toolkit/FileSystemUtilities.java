package org.quasar.toolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSystemUtilities {
	/***********************************************************
	 * @param directoryname
	 *            The name of the directory to be created
	 ***********************************************************/
	public static void createDirectory(String directoryname) {
		if (new File(directoryname).exists())
			return;
		else
			try {
				// Create multiple directories
				if ((new File(directoryname)).mkdirs())
					System.out.println("Directory: " + directoryname
							+ " created!");
			} catch (Exception e) {
				System.out
						.println("ERROR: Package directories "
								+ directoryname
								+ " could not be created. Check directory naming convention, priviledges or disk quota.");
				e.printStackTrace();
			}
	}

	/***********************************************************
	 * @param filename
	 *            The name of the file whose name (without extension) we want
	 ***********************************************************/
	public static String fileName(String filename) {
		String name = "";

		int i = filename.lastIndexOf('.');
		int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

		if (i > p) {
			name = filename.substring(0, i);
		}
		return name;
	}

	/***********************************************************
	 * @param filename
	 *            The name of the file whose extension we want
	 ***********************************************************/
	public static String fileExtension(String filename) {
		String extension = "";

		int i = filename.lastIndexOf('.');
		int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

		if (i > p) {
			extension = filename.substring(i + 1);
		}
		return extension;
	}

	/***********************************************************
	 * @param directoryname
	 *            The name of the directory to be removed
	 ***********************************************************/
	public static void removeDirectory(String directoryname) {
		FileSystemUtilities.deleteFolder(new File(directoryname));
	}

	/***********************************************************
	 * @param folder
	 *            the folder to delete
	 ***********************************************************/
	private static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

	/***********************************************************
	 * @param sourceFilename
	 * @param destFilename
	 * @throws IOException
	 ***********************************************************/
	@SuppressWarnings("resource")
	public static void copyFile(String sourceFilename, String destFilename) {
		File sourceFile = new File(sourceFilename);
		File destFile = new File(destFilename);

		// System.out.println("Copying file " + sourceFilename + " to " +
		// destFilename);

		if (!sourceFile.exists()) {
			System.out.println("ERROR: Source file " + sourceFilename
					+ " does not exist!");
			return;
		}

		try {
			if (!destFile.exists()) {
				destFile.createNewFile();
			}
			FileChannel source = null;
			FileChannel destination = null;
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();

			if (destination != null && source != null)
				destination.transferFrom(source, 0, source.size());

			if (source != null)
				source.close();

			if (destination == null)
				System.out.println("ERROR: Destination file " + destFilename
						+ " was not created!");
			else
				destination.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***********************************************************
	 * @param filename
	 * @param oldString
	 * @param newString
	 ***********************************************************/
	public static void replaceStringInFile(String filename, String oldString,
			String newString) {
		try {
			File file = new File(filename);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "";
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();

			String newtext = oldtext.replaceAll(oldString, newString);

			FileWriter writer = new FileWriter(filename);
			writer.write(newtext);
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * @param filename
	 * @return
	 */
	public static boolean fileExists(String filename) {
		File theFile = new File(filename);
		return theFile.exists();
	}

	/**
	 * @param path
	 * @return
	 */
	public static List<File> recursiveListFiles(String path) {

		List<File> result = new ArrayList<File>(Arrays.asList(new File(path)
				.listFiles()));

		for (File f : new File(path).listFiles())
			if (f.isDirectory())
				result.addAll(recursiveListFiles(f.getAbsolutePath()));

		return result;
	}

}

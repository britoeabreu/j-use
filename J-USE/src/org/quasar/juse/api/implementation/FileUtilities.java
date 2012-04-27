package org.quasar.juse.api.implementation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FileUtilities
{
	private final String	IDENTATOR	= "\t";
	private int				fIndent		= 0;
	private int				fIndentStep	= 1;
	private String			buffer		= "";
	private PrintWriter		fOut		= null;

	/***********************************************************
	 * Default constructor
	 ***********************************************************/
	public FileUtilities()
	{
		super();
	}

	/***********************************************************
	 * @param directoryname
	 *            The name of the directory to be created
	 ***********************************************************/
	public void createDirectory(String directoryname)
	{
		try
		{
			// Create multiple directories
			if ((new File(directoryname)).mkdirs())
				System.out.println("Package directories: " + directoryname + " created!");
		}
		catch (Exception e)
		{
			System.out.println("ERROR: Package directories " + directoryname
							+ " could not be created. Check directory naming convention, priviledges or disk quota.");
			e.printStackTrace();
		}
	}

	/***********************************************************
	 * @param directoryname
	 *            The name of the directory where the file to open is placed
	 * @param classname
	 *            The name of the file to open
	 ***********************************************************/
	public boolean openOutputFile(String directoryname, String filename)
	{
		String file = directoryname + "/" + filename;
		boolean result = true;
		try
		{
			if (fOut != null)
				fOut.close();
			File f = new File(file);
			if (f.exists())
			{
				JFrame frame = new JFrame();
				int answer = JOptionPane.showConfirmDialog(frame, "The file " + filename
								+ " already exists!\nDo you want to overwrite it?", "WARNING", JOptionPane.YES_NO_OPTION);
				frame.dispose();
				if (answer == JOptionPane.YES_OPTION)
					fOut = new PrintWriter(new FileWriter(file));
				else
					result = false;
			}
			else
				fOut = new PrintWriter(new FileWriter(file));
		}
		catch (IOException e)
		{
			createDirectory(directoryname);
			openOutputFile(directoryname, filename);
		}
		return result;
	}

	/***********************************************************
	 * Close the current opened file
	 ***********************************************************/
	public void closeOutputFile()
	{
		if (fOut != null)
			fOut.close();
	}

	/***********************************************************
	 * @param slist
	 *            List of strings to be printed, followed by a newline
	 ***********************************************************/
	public void println(String... slist)
	{
		for (int i = 0; i < fIndent; i++)
			fOut.print(IDENTATOR);
		fOut.print(buffer);
		for (String s : slist)
			fOut.print(s);
		fOut.println();
		buffer = "";
	}

	/***********************************************************
	 * @param s
	 *            String to be printed as a comment, followed by a newline
	 ***********************************************************/
	public void printlnc(String s)
	{
		println("//" + IDENTATOR + s);
	}

	/***********************************************************
	 * @param s
	 *            String to be printed in the currently opened file (without newline)
	 ***********************************************************/
	public void print(String s)
	{
		buffer += s;
	}

	/***********************************************************
	 * Increase the current identation level
	 ***********************************************************/
	public void incIndent()
	{
		fIndent += fIndentStep;
	}

	/***********************************************************
	 * Decrease the current identation level
	 ***********************************************************/
	public void decIndent()
	{
		if (fIndent < fIndentStep)
			throw new RuntimeException("unbalanced indentation");
		fIndent -= fIndentStep;
	}

	/***********************************************************
	 * @param s
	 *            The original string
	 * @return The string s with its first letter capitalized
	 ***********************************************************/
	public String capitalize(String s)
	{
		return s.toUpperCase().substring(0, 1) + s.substring(1);
	}

}
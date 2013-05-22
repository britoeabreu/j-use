package org.quasar.juse.persistence;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.ext.IncompatibleFileFormatException;
import com.db4o.ext.OldFormatException;
import com.db4o.query.Query;

public abstract class Database
{
		private static String databasePath;
		private static String databaseName;
		private static String databaseExtension;

	private static ObjectContainer	oc = null;

	/***********************************************************
	 * @return
	 ***********************************************************/
	public static synchronized void open(String _databasePath, String _databaseName, String _databaseExtension)
	{
		databasePath = _databasePath;
		databaseName = _databaseName;
		databaseExtension = _databaseExtension;
	}
	
	/***********************************************************
	 * @return
	 ***********************************************************/
	public static synchronized String currentDatabase()
	{
		return "Database[" +databasePath + "/" + databaseName + "." + databaseExtension + "]";
	}
	
	
	/***********************************************************
	* deletes all database objects
	 ***********************************************************/
	public static synchronized void cleanUp()
	{
		getDB();
		for (Object object: oc.queryByExample(new Object()))
				oc.delete(object);
		System.out.println("\t - database reset to empty state");
	}
	
	
	/***********************************************************
	 * @return
	 ***********************************************************/
	private static synchronized void getDB()
	{
			if (oc == null || oc.ext().isClosed())
				try
				{
					oc = Db4oEmbedded.openFile(dbConfig(), databasePath + "/" + databaseName + "." + databaseExtension);
				}
				catch (Db4oIOException e)
				{
					e.printStackTrace();
				}
				catch (DatabaseFileLockedException e)
				{
					e.printStackTrace();
				}
				catch (IncompatibleFileFormatException e)
				{
					e.printStackTrace();
				}
				catch (OldFormatException e)
				{
					e.printStackTrace();
				}
				catch (DatabaseReadOnlyException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
	}

	/***********************************************************
	 * ex: Database.get(Movie.class, "name", "Gone with the wind")
	 * @param theClass
	 * @param fieldName
	 * @param constraint
	 * @return
	 ***********************************************************/
	public static synchronized <T> T get(Class<T> theClass, String fieldName, String constraint)
	{
		getDB();
		Query q = oc.query();
		q.constrain(theClass);
		q.descend(fieldName).constrain(constraint);
		ObjectSet<T> result = q.execute();
		if (result.hasNext())
			return result.next();
		return null;
	}

	/***********************************************************
	 * ex: Database.allInstances(Movie.class), Database.allInstances(Object.class)
	* @param theClass
	* @return
	***********************************************************/
	public static synchronized <T> Set<T> allInstances(Class<T> theClass)
	{
		getDB();
		return new HashSet<T>(oc.query(theClass));
	}


	/***********************************************************
	* @param theClass
	* @return
	***********************************************************/
	public static synchronized void insert(Object ...objects)
	{
		getDB();
		for (Object o: objects)
			oc.store(o);
		oc.commit();
	}

	/***********************************************************
	* @param theClass
	* @return
	***********************************************************/
	public static synchronized void delete(Object ...objects)
	{
		getDB();
		for (Object o: objects)
			oc.delete(o);
		oc.commit();
	}

	/***********************************************************
	* @param theClass
	* @return
	***********************************************************/
	public static synchronized void update(Object ...objects)
	{
		getDB();
		for (Object o: objects)
			oc.store(o);
		oc.commit();
		
		// é preciso cuidado pois temos de ter a certeza que estamos a guardar o mesmo objecto, ou seja, como o java usa muito referencias pode
		// ocorrer o DB4O nao reconhecer a referencia
		// que esta a ser guardada como o objecto que tinha na BD
		// para garantir pode se fazer uma query pelo objecto
		// que queremos usando por exemplo o metodo get
	}

	/***********************************************************
	* @return
	***********************************************************/
	public static synchronized void close()
	{
		oc.close();
	}

	/***********************************************************
	 * This method is mainly for performance tuning
	 * 
	 * @return
	 * @throws IOException
	 ***********************************************************/
	private static EmbeddedConfiguration dbConfig() throws IOException
	{
		EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();

		// configuration.common().objectClass(Receipt.class).objectField("ID").indexed(true);
		// configuration.common().objectClass(Receipt.class).updateDepth(2);
		// configuration.common().objectClass(Client.class).objectField("ID").indexed(true);
		// configuration.common().objectClass(Client.class).updateDepth(2);
		//
		// configuration.common().objectClass(Detail.class).updateDepth(2);
		//
		// configuration.common().objectClass(Product.class).objectField("ID").indexed(true);
		// configuration.common().objectClass(Product.class).updateDepth(2);
		// configuration.common().objectClass(Product_X.class).objectField("ID").indexed(true);
		// configuration.common().objectClass(Product_X.class).updateDepth(2);
		// configuration.common().objectClass(Product_Y.class).objectField("ID").indexed(true);
		// configuration.common().objectClass(Product_Y.class).updateDepth(2);

		return configuration;
	}

}
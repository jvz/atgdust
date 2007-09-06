package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import atg.adapter.gsa.GSARepository;
import atg.dtm.TransactionDemarcation;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryItem;
import atg.test.AtgDustTestCase;
import atg.test.AtgDustTestCase.DB_VENDOR;

/**
 * 
 * Example test case to illustrate the usage of AtgDustTestCase built-in
 * database functionalities.
 * 
 * @author Robert Hellwig
 * 
 */
public class SongRepositoryTest extends AtgDustTestCase {

  private GSARepository songsRepository;

  @Override
  public void setUp() throws Exception {

    super.setUp();

    setConfigPath("src/test/resources/config".replace("/", File.separator));
    final List<String> propertyFiles = new ArrayList<String>();
    propertyFiles.add("/GettingStarted/SongsRepository");
    useExistingPropertyFiles(propertyFiles);

  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
    // shut down Nucleus
    stopNucleus();
    // Shut down HSQLDB
    stopDb();
    cleanUp();

  }

  /**
   * Runs a test using an in-memory HSQL database
   * @throws Exception
   */
  public void testWithInMemoryDb() throws Exception {

    prepareGsaTest(new File("target/test-classes/config/"),
        new String[] { "GettingStarted/songs.xml" },
        "/GettingStarted/SongsRepository");
    songsRepository = (GSARepository) getService("/GettingStarted/SongsRepository");

    TransactionDemarcation td = new TransactionDemarcation();
    assertNotNull(td);
    boolean rollback = true;

    assertNotNull(songsRepository);

    try {
      // Start a new transaction
      td.begin(((GSARepository) songsRepository).getTransactionManager());
      // Create the item
      MutableRepositoryItem item = songsRepository.createItem("artist");
      item.setPropertyValue("name", "name");
      // Persist to the repository
      songsRepository.addItem(item);
      // Try to get it back from the repository
      String id = item.getRepositoryId();
      RepositoryItem item2 = songsRepository.getItem(id, "artist");
      assertNotNull(
          " We did not get back the item just created from the repository.",
          item2);

      // GettingStartedService gs = new GettingStartedService();
      // gs.setSongsRepository(songsRepository);
      //
      // assertNotNull(gs.getAllPopSongs());

      rollback = false;
    }
    finally {
      // End the transaction, rollback on error
      if (td != null) {
        td.end(rollback);
      }
    }
  }

  /**
   * Example test with MySQL Database.
   * This test is disabled by default (starts with "X")
   * because the MySQL JDBC drivers are not included in the atg dust package.   * 
   * @throws Exception
   */
  public void XtestWithExistingMysqlDb() throws Exception {

    final String userName = "username";
    final String password = "password";
    final String host = "localhost";

    final String port = "3306";
    final String dbName = "database_one";
    final DB_VENDOR dbVendor = DB_VENDOR.MySQLDBConnection;

    prepareGsaTest(new File("target/test-classes/config/"),
        new String[] { "GettingStarted/songs.xml" },
        "/GettingStarted/SongsRepository", userName, password, host, port,
        dbName, dbVendor);
    songsRepository = (GSARepository) getService("/GettingStarted/SongsRepository");

    TransactionDemarcation td = new TransactionDemarcation();
    assertNotNull(td);
    boolean rollback = true;

    assertNotNull(songsRepository);

    try {
      // Start a new transaction
      td.begin(((GSARepository) songsRepository).getTransactionManager());
      // Create the item
      MutableRepositoryItem item = songsRepository.createItem("artist");
      item.setPropertyValue("name", "name");
      // Persist to the repository
      songsRepository.addItem(item);
      // Try to get it back from the repository
      String id = item.getRepositoryId();
      RepositoryItem item2 = songsRepository.getItem(id, "artist");
      assertNotNull(
          " We did not get back the item just created from the repository.",
          item2);

      // GettingStartedService gs = new GettingStartedService();
      // gs.setSongsRepository(songsRepository);
      //
      // assertNotNull(gs.getAllPopSongs());

      rollback = false;
    }
    finally {
      // End the transaction, rollback on error
      if (td != null) {
        td.end(rollback);
      }
    }
  }
}
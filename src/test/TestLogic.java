package test;

import static org.junit.Assert.assertEquals;
import main.java.backend.Command;
import main.java.backend.Logic;

import org.junit.Before;
import org.junit.Test;

public class TestLogic {

    private static final String ROOT_DIR = System.getProperty("user.dir");
    private static final String TEST_RESOURCES_DIR = "/src/test/resources/";
    private static final String TEST_FILE1 = "testFile1.txt";
    private Logic logic;

    @Before
    public void init() {
        logic = new Logic();
    }

    @Test
    public void testHandleEnterPress() {
        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR + " only"),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR +
                                            " include java"),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR + " include txt"),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR + TEST_FILE1),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("view A1234567Z"),
                     Command.Type.VIEW);

        assertEquals(logic.handleEnterPress("view author1"), Command.Type.VIEW);

        assertEquals(logic.handleEnterPress("summary"), Command.Type.SUMMARY);

        assertEquals(logic.handleEnterPress("from"), Command.Type.INVALID);
    }

    @Test
    public void testGetOverviewData() {
        logic.handleEnterPress("collate from " + ROOT_DIR + TEST_RESOURCES_DIR +
                               TEST_FILE1);
        assertEquals(logic.getOverviewData().size(), 1);
        assertEquals(logic.getOverviewData().get(0).getName(), "author1");
    }

    @Test
    public void testGetTargetAuthor() {
        assertEquals(logic.getTargetAuthor(), null);
        logic.handleEnterPress("collate from " + ROOT_DIR + TEST_RESOURCES_DIR +
                               TEST_FILE1);
        logic.handleEnterPress("view author1");
        assertEquals(logic.getTargetAuthor().getName(), "author1");
    }

}

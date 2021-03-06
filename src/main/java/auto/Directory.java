package auto;

import java.io.File;
import java.nio.file.Path;

public class Directory {

    private Directory(){}

    public static final String PROJ_DIR = System.getProperty("user.dir")+"/";

    public static final String MAIN_DIR = new File(PROJ_DIR + "src/main/").getAbsolutePath();
    public static final String JAVA_DIR = new File(MAIN_DIR + "java/").getAbsolutePath();
    public static final String SRC_RESOURCE_DIR = new File(MAIN_DIR + "resources/").getAbsolutePath();
    public static final String LOG_DIR = new File(MAIN_DIR + "log/").getAbsolutePath();

    public static final String TEST_DIR = new File(PROJ_DIR + "src/test/").getAbsolutePath();
    public static final String UNITTEST_RESULT_DIR = new File(TEST_DIR + "unittestsResult/").getAbsolutePath();
    public static final String TEST_RESOURCE_DIR = new File(TEST_DIR + "resources/").getAbsolutePath();
    public static final String TEST_JAVA_DIR = new File(TEST_DIR + "java/").getAbsolutePath();

    // testcase
    public static final String TESTS_DIR = new File(TEST_JAVA_DIR + "java/tests/").getAbsolutePath();
//    public static final String TESTS_RESULT_DIR = new File(TEST_DIR + "result/").getAbsolutePath();
    public static final String TESTS_RESULT_DIR = new File(PROJ_DIR + "result/").getAbsolutePath();
    public static final String TESTS_LOG_DIR = new File(TESTS_RESULT_DIR + "log/").getAbsolutePath();

    public static final String CAPS_FILE = Directory.Path("capabilities.yml", SRC_RESOURCE_DIR);
    public static final String DEVICE_FILE = Directory.Path("device.yml", SRC_RESOURCE_DIR);
    public static final String TESTSET_FILE = Directory.Path("testset.yml", SRC_RESOURCE_DIR);

    public static String Path(String relativePath){
        return getFile(relativePath).getAbsolutePath();
    }

    public static String Path(String relativePath, String parentPath){
        return getFile(relativePath, parentPath).getAbsolutePath();
    }

    public static File getFile(String relativePath){
        return getFile(relativePath, PROJ_DIR);
    }

    public static File getFile(String relativePath, String parentPath){
        String wholePath = relativePath.startsWith("/")
                ? parentPath + relativePath
                : parentPath + "/" + relativePath;
        return new File(wholePath);
    }

    public static Path getPath(String relativePath){
        return getPath(relativePath, PROJ_DIR);

    }

    public static Path getPath(String relativePath, String parentPath){
//        String wholePath = relativePath.startsWith("/")
//                ? parentPath + relativePath
//                : parentPath + "/" + relativePath;
//        return Paths.get(wholePath);
        return getFile(relativePath, parentPath).toPath();
    }

}

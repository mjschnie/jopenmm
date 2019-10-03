package edu.uiowa.jopenmm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p>OpenMMUtils class.</p>
 *
 * @author Michael J. Schnieders
 * @version $Id: $Id
 */
public class OpenMMUtils {

    private static boolean init = false;

    /**
     * Constant <code>JNA_LIBRARY_PATH=""</code>
     */
    private static String JNA_LIBRARY_PATH = "";
    /**
     * Constant <code>OPENMM_PLUGIN_LIB=""</code>
     */
    private static String OPENMM_LIB_DIR = "";
    /**
     * Constant <code>OPENMM_PLUGIN_DIR=""</code>
     */
    private static String OPENMM_PLUGIN_DIR = "";

    /**
     * <p>init.</p>
     */
    public synchronized static void init() {

        if (init) {
            return;
        }

        init = true;

        try {
            JarFile jarFile = jarForClass(edu.uiowa.jopenmm.OpenMMLibrary.class, null);

            String os;
            if (com.sun.jna.Platform.isMac()) {
                os = "darwin";
            } else if (com.sun.jna.Platform.isLinux()) {
                os = "linux_x64";
            } else {
                os = "win_x64";
            }

            String directory = "lib/" + os;

            Path path = Files.createTempDirectory("openmm");
            File toDir = path.toFile();
            OPENMM_LIB_DIR = toDir.getAbsolutePath() + "/" + os;

            copyResourcesToDirectory(jarFile, directory, OPENMM_LIB_DIR);

            JNA_LIBRARY_PATH = System.getProperty("jna.library.path", "");
            if (!JNA_LIBRARY_PATH.equalsIgnoreCase("")) {
                JNA_LIBRARY_PATH = OPENMM_LIB_DIR + File.pathSeparator + JNA_LIBRARY_PATH;
            } else {
                JNA_LIBRARY_PATH = OPENMM_LIB_DIR;
            }
            System.setProperty("jna.library.path", JNA_LIBRARY_PATH);

            OPENMM_PLUGIN_DIR = OPENMM_LIB_DIR + "/plugins";
        } catch (Exception e) {
            System.err.println(" Exception configuring OpenMM: " + e.toString());
        }
    }

    /**
     * Return the location of the temporary OpenMM lib directory. This will return null until
     * the OpenMMUtils.init() method is called.
     *
     * @return The lib directory.
     */
    public static String getLibDirectory() {
        return OPENMM_LIB_DIR;
    }

    /**
     * Return the location of the temporary OpenMM plugin directory. This will return null until
     * the OpenMMUtils.init() method is called.
     *
     * @return The plugin directory.
     */
    public static String getPluginDirectory() {
        return OPENMM_PLUGIN_DIR;
    }

    private static JarFile jarForClass(Class<?> clazz, JarFile defaultJar) {
        String path = "/" + clazz.getName().replace('.', '/') + ".class";
        URL jarUrl = clazz.getResource(path);
        if (jarUrl == null) {
            return defaultJar;
        }

        String url = jarUrl.toString();
        int bang = url.indexOf("!");
        String JAR_URI_PREFIX = "jar:file:";
        if (url.startsWith(JAR_URI_PREFIX) && bang != -1) {
            try {
                return new JarFile(url.substring(JAR_URI_PREFIX.length(), bang));
            } catch (IOException e) {
                throw new IllegalStateException("Error loading jar file.", e);
            }
        } else {
            return defaultJar;
        }
    }

    private static void copyResourcesToDirectory(JarFile fromJar, String jarDir, String destDir)
            throws java.io.IOException {
        for (Enumeration<JarEntry> entries = fromJar.entries(); entries.hasMoreElements(); ) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().startsWith(jarDir + "/") && !entry.isDirectory()) {
                File dest = new java.io.File(destDir + "/" + entry.getName().substring(jarDir.length() + 1));
                File parent = dest.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                FileOutputStream out = new FileOutputStream(dest);
                InputStream in = fromJar.getInputStream(entry);

                try {
                    byte[] buffer = new byte[8 * 1024];

                    int s;
                    while ((s = in.read(buffer)) > 0) {
                        out.write(buffer, 0, s);
                    }
                } catch (java.io.IOException e) {
                    throw new java.io.IOException("Could not copy asset from jar file", e);
                } finally {
                    try {
                        in.close();
                    } catch (java.io.IOException ignored) {
                    }
                    try {
                        out.close();
                    } catch (java.io.IOException ignored) {
                    }
                }
            }
        }
    }


}

package org.kosala.toml;

import com.moandjiezana.toml.Toml;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Main class of Toml Reader which read the content of TOML configuration file.
 *
 * @since 0.1.0
 */
public class TomlReader {

    private static Logger logger = Logger.getLogger(TomlReader.class);

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("toml-reader").build()
                .defaultHelp(true)
                .description("Read data from a given toml configuration file.");
        parser.addArgument("-f", "--file")
                .help("path to the TOML file");
        Namespace namespace = null;
        try {
            namespace = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        Toml configToml = new Toml();
        if (namespace.getString("file") != null) {
            try {
                configToml = parseConfigFile(namespace.getString("file"));
            } catch (FileNotFoundException e) {
                logger.error("Error occurred by configuration file path : " + e);
                exitWithError();
            } catch (RuntimeException e) {
                logger.error("Could not read the given configuration file : " + e);
                exitWithError();
            }
        } else {
            logger.warn("Could not find path to configuration file. " +
                    "Please provide path of configuration file for read the information.");
            exit();
        }

        logger.info("Starting configuration file reading process..");
        StringBuilder output = new StringBuilder("\n > University Name : " + readUniversityName(configToml) +
                "\n > University Country : " + readUniversityCountry(configToml) +
                "\n > Department Name : " + readUDepartmentName(configToml) +
                "\n > Number of Students : " + readNumberOfStudents(configToml) +
                "\n > Subjects : ");
        for (String subject : readSubjects(configToml)) {
            output.append("\n\t").append(subject);
        }
        logger.info(output.toString());
        exit();
    }

    private static Toml parseConfigFile(String path) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(path);
        Toml configToml = new Toml().read(inputStream);
        return configToml;
    }

    private static String readUniversityName (Toml toml) {
        String universityName = "";
        try {
            universityName = toml.getString("university.name");
        } catch (RuntimeException e) {
            logger.warn("Could not read the value of University Name : ");
        }
        return universityName;
    }

    private static String readUniversityCountry (Toml toml) {
        String countryName = "";
        try {
            countryName = toml.getString("university.country");
        } catch (RuntimeException e) {
            logger.warn("Could not read the value of Country Name : ");
        }
        return countryName;
    }

    private static String readUDepartmentName (Toml toml) {
        String departmentName = "";
        try {
            departmentName = toml.getString("university.department.name");
        } catch (RuntimeException e) {
            logger.warn("Could not read the value of Department Name : ");
        }
        return departmentName;
    }

    private static Long readNumberOfStudents (Toml toml) {
        Long numberOfStudents = -1L;
        try {
            numberOfStudents = toml.getLong("university.department.students");
        } catch (RuntimeException e) {
            logger.warn("Could not read the number of students : ");
        }
        return numberOfStudents;
    }

    private static List<String> readSubjects (Toml toml) {
        List<String> subjects = new ArrayList<>();
        try {
            subjects = toml.getList("university.department.subjects");
        } catch (RuntimeException e) {
            logger.warn("Could not read the list of Subjects : ");
        }
        return subjects;
    }

    private static void exitWithError() {
        logger.info("Process stopped with errors. Exit the application..");
        System.exit(1);
    }

    private static void exit() {
        logger.info("Process finished. Exit the application..");
        System.exit(0);
    }
}

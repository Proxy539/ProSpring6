package com.apress.prospring6.four;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component("builtInSample")
public class DiverseValuesContainer {

    private byte[] bytes; // ByteArrayPropertyEditor
    private Character character; // CharacterEditor
    private Class<?> cls; // ClassEditor
    private Boolean trueOrFalse; // CustomBooleanEditor
    private List<String> stringList; // CustomCollectionEditor
    private Date date; // CustomDateEdtior
    private Float floatValue; // CustomNumberEditor
    private File file; // FileEditor
    private InputStream stream; // InputStreamEditor
    private Locale locale; // LocaleEditor
    private Pattern pattern; // PatternEditor
    private Properties properties; // PropertiesEditor
    private String trimString; // StringTimmerEditor
    private URL url; // URLEditor

    private static Logger logger = LoggerFactory.getLogger(DiverseValuesContainer.class);

    @Value("A")
    public void setCharacter(Character character) {
        logger.info("Setting character: {}", character);
        this.character = character;
    }

    @Value("java.lang.String")
    public void setCls(Class<?> cls) {
        logger.info("Setting class: {}", cls.getName());
        this.cls = cls;
    }

    @Value("#{sustemProperties['java.io.tmpdir']}#{systemProperties['file.separator']}test.txt")
    public void setFile(File file) {
        logger.info("Setting file: {}", file.getAbsolutePath());
        this.file = file;
    }

    @Value("en_US")
    public void setLocale(Locale locale) {
        logger.info("Setting local: {}", locale.getDisplayName());
        this.locale = locale;
    }

    @Value("name=Ben age=41")
    public void setProperties(Properties properties) {
        logger.info("Loaded {}", properties.size() + " propetties");
        this.properties = properties;
    }

    @Value("https://iuliana-cosmina.com")
    public void setUrl(URL url) {
        logger.info("Setting URL: {}", url.toExternalForm());
        this.url = url;
    }

    @Value("John Mayer")
    public void setBytes(byte... bytes) {
        logger.info("Setting bytes: {}", Arrays.toString(bytes));
        this.bytes = bytes;
    }

    @Value("true")
    public void setTrueOrFalse(Boolean trueOrFalse) {
        logger.info("Setting Boolean: {}", trueOrFalse);
        this.trueOrFalse = trueOrFalse;
    }

    @Value("#{valuesHolder.stringList}")
    public void setStringList(List<String> stringList) {
        logger.info("Setting stringList with: {}", stringList);
        this.stringList = stringList;
    }

    @Value("20/08/1981")
    public void setDate(Date date) {
        logger.info("Setting date: {}", date);
        this.date = date;
    }

    @Value("123.45678")
    public void setFloatValue(Float floatValue) {
        logger.info("Setting float value: {}", floatValue);
        this.floatValue = floatValue;
    }

    @Value("#{valuesHolder.inputStream}")
    public void setStream(InputStream stream) {
        this.stream = stream;
        logger.info("Setting stream & reading from it: {}",
                new BufferedReader(new InputStreamReader(stream)).lines().parallel()
                        .collect(Collectors.joining("\n")));
    }

    @Value("a*b")
    public void setPattern(Pattern pattern) {
        logger.info("Setting pattern: {}", pattern);
        this.pattern = pattern;
    }

    @Value("    String need trimming    ")
    public void setTrimString(String trimString) {
        logger.info("Setting trim string: {}", trimString);
        this.trimString = trimString;
    }

    public static class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

        @Override
        public void registerCustomEditors(PropertyEditorRegistry registry) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
            registry.registerCustomEditor(Date.class, new CustomDateEditor(dateFormatter, true));

            registry.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        }
    }

    public static void main(String[] args) throws Exception {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        Path path = Files.createFile(Path.of(baseDir.getAbsolutePath(), "test.txt"));
        Files.writeString(path, "Hello World!");
        path.toFile().deleteOnExit();

        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(ValuesHolder.class, DiverseValuesContainer.class);
        ctx.refresh();

        ctx.close();
    }
}

@Component
class ValuesHolder {
    List<String> stringList;
    InputStream inputStream;

    public ValuesHolder(List<String> stringList) {
        this.stringList = List.of("Mayer", "Psihoza", "Mazikeen");

        try {
            this.inputStream = new FileInputStream(
                    System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "test.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // we are not interested in this exception
        }
    }
}

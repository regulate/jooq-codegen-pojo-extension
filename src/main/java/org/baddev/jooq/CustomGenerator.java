package org.baddev.jooq;

import org.jooq.tools.JooqLogger;
import org.jooq.util.*;

import java.io.File;

/**
 * Created by IPotapchuk on 7/5/2016.
 */
public class CustomGenerator extends JavaGenerator implements ExtendedGenerator {

    boolean generatePropertiesConstants = false;
    boolean generateJaxbAnnotations = false;

    @Override
    public boolean generatePropertiesConstants() {
        return generatePropertiesConstants;
    }

    @Override
    public void setGeneratePropertiesConstants(boolean generatePropertiesConstants) {
        this.generatePropertiesConstants = generatePropertiesConstants;
    }

    @Override
    public boolean generateJaxbAnnotations() {
        return generateJaxbAnnotations;
    }

    @Override
    public void setGenerateJaxbAnnotations(boolean generateJaxbAnnotations) {
        this.generateJaxbAnnotations = generateJaxbAnnotations;
    }

    @Override
    protected void generatePojoClassFooter(TableDefinition table, JavaWriter out) {
        if (generatePropertiesConstants) {
            out.println();
            for (ColumnDefinition c : table.getColumns())
                out.tab(1).println("public static final String P_%s = \"%s\";",
                        c.getName().toUpperCase(),
                        getStrategy().getJavaMemberName(c, GeneratorStrategy.Mode.POJO));
            out.println();
        }
        super.generatePojoClassFooter(table, out);
    }

    @Override
    protected void printClassAnnotations(JavaWriter out, SchemaDefinition schema) {
        super.printClassAnnotations(out, schema);
        if (isToGenerateJaxbAnnotations(out)) {
            out.println("@%s", "javax.xml.bind.annotation.XmlRootElement");
            out.println("@%s(%s)", "javax.xml.bind.annotation.XmlAccessorType", "javax.xml.bind.annotation.XmlAccessType.FIELD");
        }
    }

    @Override
    protected void printColumnJPAAnnotation(JavaWriter out, ColumnDefinition column) {
        super.printColumnJPAAnnotation(out, column);
        if (isToGenerateJaxbAnnotations(out))
            out.tab(1).println("@%s", "javax.xml.bind.annotation.XmlElement");
    }

    @Override
    protected void generatePojos(SchemaDefinition schema) {
        printInfo();
        super.generatePojos(schema);
    }

    private boolean isToGenerateJaxbAnnotations(JavaWriter out) {
        if (generateJaxbAnnotations) {
            File classFile = out.file();
            //decision based on directory name
            if (classFile.getParent() != null && classFile.getParent().endsWith("pojos"))
                return true;
        }
        return false;
    }

    private void printInfo() {
        JooqLogger.getLogger(getClass()).warn("Using codegen pojo extension");
        JooqLogger.getLogger(getClass()).warn("Parameters:");
        JooqLogger.getLogger(getClass()).warn(" generatePropertiesConstants: " + generatePropertiesConstants);
        JooqLogger.getLogger(getClass()).warn(" generateJaxbAnnotations: " + generateJaxbAnnotations);
    }
}

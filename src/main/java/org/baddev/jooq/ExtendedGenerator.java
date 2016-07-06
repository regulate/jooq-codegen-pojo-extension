package org.baddev.jooq;

import org.jooq.util.Generator;

/**
 * Created by IPotapchuk on 7/5/2016.
 */
public interface ExtendedGenerator extends Generator {

    boolean generatePropertiesConstants();

    void setGeneratePropertiesConstants(boolean generatePropertiesConstants);

    boolean generateJaxbAnnotations();

    void setGenerateJaxbAnnotations(boolean generateJaxbAnnotations);

}

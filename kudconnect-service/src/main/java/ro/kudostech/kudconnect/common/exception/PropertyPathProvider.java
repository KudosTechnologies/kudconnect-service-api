package ro.kudostech.kudconnect.common.exception;

import org.hibernate.validator.spi.nodenameprovider.*;
import ro.kudostech.kudconnect.common.PropertyPath;

public class PropertyPathProvider implements PropertyNodeNameProvider {

    @Override
    public String getName(Property property) {
        if (property instanceof final JavaBeanProperty javaBeanProperty) {
            try {
                final PropertyPath propertyPath = javaBeanProperty
                        .getDeclaringClass()
                        .getDeclaredField(javaBeanProperty.getName())
                        .getAnnotation(PropertyPath.class);
                return propertyPath != null ? propertyPath.value() : "/" + property.getName();
            } catch (NoSuchFieldException e) {
                return property.getName();
            }
        }

        return property.getName();
    }
}

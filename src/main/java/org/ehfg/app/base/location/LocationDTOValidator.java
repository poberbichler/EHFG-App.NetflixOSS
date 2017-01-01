package org.ehfg.app.base.location;

import org.ehfg.app.base.Coordinate;

import javax.validation.*;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * @author patrick
 * @since 08.2015
 */
class LocationDTOValidator implements ConstraintValidator<LocationMappingValid, Location> {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public void initialize(LocationMappingValid constraintAnnotation) {
        // nothing to do
    }

    @Override
    public boolean isValid(Location value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.getPoint() != null) {
            return true;
        }

        Set<ConstraintViolation<Coordinate>> violations = validator.validate(value.getCoordinate(), Default.class);
        if (violations.isEmpty()) {
            return true;
        }

        return false;
    }
}

package co.epvtecnologia.sistema_pos_v10.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import co.epvtecnologia.sistema_pos_v10.service.FacturaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the codigoFactura value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = FacturaCodigoFacturaUnique.FacturaCodigoFacturaUniqueValidator.class
)
public @interface FacturaCodigoFacturaUnique {

    String message() default "{Exists.factura.codigoFactura}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class FacturaCodigoFacturaUniqueValidator implements ConstraintValidator<FacturaCodigoFacturaUnique, String> {

        private final FacturaService facturaService;
        private final HttpServletRequest request;

        public FacturaCodigoFacturaUniqueValidator(final FacturaService facturaService,
                final HttpServletRequest request) {
            this.facturaService = facturaService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(facturaService.get(Long.parseLong(currentId)).getCodigoFactura())) {
                // value hasn't changed
                return true;
            }
            return !facturaService.codigoFacturaExists(value);
        }

    }

}

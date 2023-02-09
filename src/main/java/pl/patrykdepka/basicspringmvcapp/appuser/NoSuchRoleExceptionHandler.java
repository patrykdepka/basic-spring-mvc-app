package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import pl.patrykdepka.basicspringmvcapp.core.AbstractExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class NoSuchRoleExceptionHandler extends AbstractExceptionHandler {
    private static final String MESSAGE_CODE = "exception.NoSuchRoleException.message";

    public NoSuchRoleExceptionHandler(MessageSource messageSource) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE_CODE, messageSource, NoSuchRoleException.class);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        response.setStatus(httpStatus.value());
        return getDefaultModelAndView();
    }
}

package com.kodilla.ecommercee.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {
    @AllArgsConstructor
    @Getter
    public static class Error {
        private String level;
        private String code;
        private String description;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public Error handleUserNotFoundException(UserNotFoundException unfe) {
        return new Error("ERROR", "user.does.not.exist", "User with ID " + unfe.getUserId() + " not found.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(CartNotFoundException.class)
    public Error handleCartNotFoundException(CartNotFoundException cnfe) {
        return new Error("ERROR", "cart.does.not.exist", "Cart with ID " + cnfe.getCartId() + " not found.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    public Error handleCartNotFoundException(ProductNotFoundException pnfe) {
        return new Error("ERROR", "product.does.not.exist", "Product with ID " + pnfe.getProductId() + " not found.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(QuantityLessThanZeroException.class)
    public Error handleInvalidQuantityException(QuantityLessThanZeroException iqe) {
        return new Error("ERROR", "quantity.less.than.zero", iqe.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(InsufficientStockException.class)
    public Error handleInsufficientStockException(InsufficientStockException ise) {
        return new Error("ERROR", "product.insufficient.stock", ise.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(UsernameExistsException.class)
    public Error handleUsernameExistsException(UsernameExistsException uee) {
        return new Error("ERROR", "username.already.exists", uee.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(EmailExistsException.class)
    public Error handleEmailExistsException(EmailExistsException eee) {
        return new Error("ERROR", "email.already.exists", eee.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(GroupNotFoundException.class)
    public Error handleGroupNotFoundException(GroupNotFoundException gnfe) {
        return new Error("ERROR", "group.does.not.exist", gnfe.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ParentGroupNotFoundException.class)
    public Error handleParentGroupNotFoundException(ParentGroupNotFoundException pgnfe) {
        return new Error("ERROR", "parent.group.does.not.exist", pgnfe.getMessage());
    }
}

package com.drinksleo.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {


    IMAGE_EMPTY(1300,"KO","Empty image"),
    IMAGE_REQUIRED(1301,"KO","The image is required and was not received"),
    IMAGE_UPLOAD_FAIL(1302,"KO","Image Upload Fail"),
    RECIPE_NOT_EXISTS(1303,"KO","Recipe do not exists");

    private Integer code;
    private String description;
    private String message;


    ExceptionEnum(Integer code, String description, String message) {
        this.code = code;
        this.message = message;
        this.description = description;
    }


}

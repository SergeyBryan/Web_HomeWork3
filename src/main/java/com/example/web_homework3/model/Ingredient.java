package com.example.web_homework3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class Ingredient {
    @NotBlank(message = "Имя не может быть пустым или состоять из пробелов")
    private String name;
    @Min(value = 1,message = "Количество не может быть отрицательным или равняться 0")
    private int amount;
    @NotBlank(message = "Поле 'Еденица измерения' не может быть пустым")
    private String measureUnit;
}

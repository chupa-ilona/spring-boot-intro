package spring.springbootintro.config;

import org.mapstruct.InjectionStrategy;

@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS,
        implementationPackage = "spring.springbootintro.mapper.impl"
)
public class MapperConfig {
}

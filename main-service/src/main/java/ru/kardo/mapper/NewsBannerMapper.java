package ru.kardo.mapper;

import org.mapstruct.Mapper;
import ru.kardo.dto.newsBanner.NewsBannerDto;
import ru.kardo.model.NewsBanner;

@Mapper(componentModel = "spring", uses = NewsBanner.class)
public interface NewsBannerMapper {

    NewsBannerDto toNewsBannerDto(NewsBanner banner);
}

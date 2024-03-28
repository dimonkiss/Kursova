package org.example.mapper;

import org.example.dto.PostCreateDTO;
import org.example.dto.PostItemDTO;
import org.example.entities.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "datePosted", target = "dateCreated", dateFormat = "dd.MM.yyyy HH:mm:ss")
    PostItemDTO PostItemDTO(PostEntity Post);
    List<PostItemDTO> categoriesListItemDTO(List<PostEntity> list);

    @Mapping(target = "imageUrl", ignore = true)
    PostEntity PostEntityByPostCreateDTO(PostCreateDTO Post);
}
package ru.kardo.mapper;

import org.mapstruct.Mapper;
import ru.kardo.dto.document.DocumentDto;
import ru.kardo.model.Document;

@Mapper(componentModel = "spring", uses = Document.class)
public interface DocumentMapper {

    DocumentDto toDocumentDto(Document document);
}

package kh.edu.cstad.springa_4_bankingapi.service.Impl;

import kh.edu.cstad.springa_4_bankingapi.domain.Media;
import kh.edu.cstad.springa_4_bankingapi.dto.MediaResponse;
import kh.edu.cstad.springa_4_bankingapi.repository.MediaRepository;
import kh.edu.cstad.springa_4_bankingapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.util.stream.Collectors;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    private final MediaRepository mediaRepository;


    @Override
    public MediaResponse upload(MultipartFile file) {

        String name = UUID.randomUUID().toString();

        int lastIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");

        String extension = file.getOriginalFilename().substring(lastIndex + 1);

        Path path = Paths.get(serverPath + String.format("%s.%s", name, extension));

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Media upload failed");
        }

        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setMimeTypeFile(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .mimeTypeFile(media.getMimeTypeFile())
                .size(file.getSize())
                .uri(baseUri + String.format("%s.%s", name, extension))
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(MultipartFile[] files) {

        List<MediaResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                responses.add(upload(file));
            }
        }
        return responses;
    }

    @Override
    public Resource downloadByName(String fileName) {

        try {
            String[] parts = fileName.split("\\.");
            if (parts.length != 2) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid filename format. Expected: name.extension"
                );
            }

            String name = parts[0];
            String extension = parts[1];

            Media media = mediaRepository.findByNameAndExtensionAndIsDeletedFalse(name, extension)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Media not found or has been deleted"
                    ));

            //build file path
            Path filePath = Paths.get(serverPath).resolve(fileName);

            //
            if (!Files.exists(filePath)) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Physical file not found or has been deleted"
                );
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "File not readable"
                );
            }

        } catch(MalformedURLException e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error accessing file: " + e.getMessage()
            );
       }
   }

    @Override
    public void deleteByName(String fileName) {

        try{
            String[] parts = fileName.split("\\.");
            if (parts.length != 2) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid filename format. Expected: name.extension"
                );
            }

            String name = parts[0];
            String extension = parts[1];

            Media media = mediaRepository.findByNameAndExtensionAndIsDeletedFalse(name, extension)
                    .orElseThrow( () -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Media not found or already deleted")
                    );

            media.setIsDeleted(true);
            mediaRepository.save(media);
        } catch (Exception e){
            if ( e instanceof ResponseStatusException ) {
                throw e;
            }
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to delete media: " + e.getMessage()
            );
        }

    }

    @Override
    public List<String> getAllFileNames() {

        List<Media> activeMedia = mediaRepository.findAllByIsDeletedFalse();
        return activeMedia.stream()
                .map(media -> String.format("%s.%s", media.getName(), media.getExtension()))
                .collect(Collectors.toList());
    }
}

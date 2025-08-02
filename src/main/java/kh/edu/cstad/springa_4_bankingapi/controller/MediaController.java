package kh.edu.cstad.springa_4_bankingapi.controller;

import kh.edu.cstad.springa_4_bankingapi.dto.MediaResponse;
import kh.edu.cstad.springa_4_bankingapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    //upload single file
    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile file) {
        return mediaService.upload(file);
    }

    //upload multiple file
    @PostMapping("/batch")
    public List<MediaResponse> uploadMultiple(@RequestPart MultipartFile[] files) {
        return mediaService.uploadMultiple(files);
    }

    //download media by name (name + ext )
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadByName(@PathVariable String fileName) {
        try{

            Resource resource = mediaService.downloadByName(fileName);

            String contentType = "application/octet-stream";
            try {
                contentType = Files.probeContentType(Paths.get(fileName));
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
            }catch (Exception e){

            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    //delete media by name
    @DeleteMapping("/{fileName}")
    public ResponseEntity<Map<String, String>> deleteByName(@PathVariable String fileName) {
        try {
            mediaService.deleteByName(fileName);

            Map<String, String> response = new HashMap<>();
            response.put("message", "File '" + fileName + "' has been deleted");
            response.put("fileName", fileName);

            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getReason());
            errorResponse.put("fileName", fileName);

            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        }
    }

    //get all media file list
    @GetMapping
    public List<String> getAllMediaFiles() {
        return mediaService.getAllFileNames();
    }

}

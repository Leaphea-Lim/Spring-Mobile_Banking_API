package kh.edu.cstad.springa_4_bankingapi.service;

import kh.edu.cstad.springa_4_bankingapi.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    /**
     * Upload sing file
     * @param file from HTTP requestion
     * @return MediaResponse
     */
    MediaResponse upload(MultipartFile file);

    List<MediaResponse> uploadMultiple(MultipartFile[] files);

    Resource downloadByName(String fileName);

    void deleteByName(String fileName);

    List<String> getAllFileNames();

}

package com.example.coupleapp.common;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class FIleVo {
    private List<MultipartFile> fileList;
}

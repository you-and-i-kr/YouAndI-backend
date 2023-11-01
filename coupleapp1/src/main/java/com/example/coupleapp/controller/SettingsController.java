package com.example.coupleapp.controller;


import com.example.coupleapp.dto.MemberDTO;
import com.example.coupleapp.dto.SettingsDTO;
import com.example.coupleapp.exception.domian.SettingsNotFoundException;
import com.example.coupleapp.service.SettingsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
@Api(tags="설정API ",value = "설정 ", description = "노트이름 바꾸기 ")
public class SettingsController {
    private final SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

//    @PutMapping("/{id}")
//    @ApiOperation(value = "기록장 이름 바꾸기")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "기록장이름이 업데이트 됐습니다."),
//            @ApiResponse(code = 404, message = "업데이트를 할 수 없습니다.")
//    })
//    public ResponseEntity<Void> updateSettings(
//            @ApiParam(value = "기록장 이름 입력 ", required = true) @PathVariable String note_name,
//            @RequestBody SettingsDTO request) {
//        // request 객체는 업데이트할 설정 정보를 담고 있어야 합니다.
//        // 설정 업데이트 로직을 호출하고 필요에 따라 예외 처리를 수행합니다.
//        try {
//            settingsService.updateSettings(note_name, request);
//            return ResponseEntity.ok().build();
//        } catch (SettingsNotFoundException ex) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "파트너와 이별하기 ")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "헤어졌습니다."),
            @ApiResponse(code = 404, message = "둘은 계속 사겨야합니다.")
    })
    public ResponseEntity<Void> deleteSettings(
            @ApiParam(value = "memberId를 입력하세요", required = true) @PathVariable Long id) {
        settingsService.deleteSettings(id);
        return ResponseEntity.noContent().build();
    }
}

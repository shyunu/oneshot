package com.project.oneshot.controller;

import com.project.oneshot.command.BankVO;
import com.project.oneshot.command.EmployeeAuthVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.PositionVO;
import com.project.oneshot.hr.employee.EmployeeService;
import com.project.oneshot.security.EmployeeDetails;
import com.project.oneshot.security.EmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/hrm")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDetailsService employeeDetailsService; //로그인 및 비밀번호 관리 등등 보안서비스

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EmployeeRestController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 사원 목록 조회
    @GetMapping("/getEmployee")
    public ResponseEntity<Map<String, Object>> getAllEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Map<String, Object> employees = employeeService.getAllEmployees(page, size);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    //사원검색
    @PostMapping("/searchEmployee")
    public ResponseEntity<List<EmployeeVO>> searchEmployee(@RequestBody EmployeeVO vo) {
        List<EmployeeVO> employees = employeeService.getSearchEmployees(vo);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // 은행 목록 조회
    @GetMapping("/getBank")
    public ResponseEntity<List<BankVO>> getAllBank() {
        List<BankVO> banks = employeeService.getAllBank();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }
    // 직급 목록 조회
    @GetMapping("/getPosition")
    public ResponseEntity<List<PositionVO>> getAllPosition() {
        List<PositionVO> positions = employeeService.getAllPosition();
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    // 사원 등록
    @PostMapping("/registEmployee")
    public ResponseEntity<String> insertEmployee(
            @ModelAttribute EmployeeVO employeeVO,
            @RequestParam(value = "employeePhoto", required = false) MultipartFile employeePhoto) {

        // 파일을 저장할 경로
        String uploadDir = "D:/file_repo/";
        if(employeePhoto == null || employeePhoto.isEmpty()) {
            System.out.println("사진없음");
            employeeVO.setEmployeePhotoPath("default");
        }

        // 폴더가 존재하지 않으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 저장 경로를 설정
        if (employeePhoto != null && !employeePhoto.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + employeePhoto.getOriginalFilename();
                File file = new File(uploadDir + fileName);
                employeePhoto.transferTo(file);

                // VO에 파일 경로를 설정
                employeeVO.setEmployeePhotoPath(fileName);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("File upload failed.");
            }
        }

        try {
            if (employeeService.insertEmployee(employeeVO) == 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("등록이 정상적으로 되지 않았습니다."); // 409 Conflict
            }
            //EmployeeAuth테이블에 비밀번호 넣기
            EmployeeAuthVO employeeAuthVO = new EmployeeAuthVO();
            employeeAuthVO.setEmployeeNo(employeeVO.getEmployeeNo());
            if (employeeVO.getEmployeeBirth() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
                String EmployeeBirthPassword = employeeVO.getEmployeeBirth().format(formatter);
                employeeAuthVO.setEmployeePassword(bCryptPasswordEncoder.encode(EmployeeBirthPassword));
            }
            if(employeeDetailsService.insertEmployeeAuth(employeeAuthVO)==0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("비밀번호 등록이 정상적으로 되지 않았습니다.");
            }
            return ResponseEntity.ok("File and data insert successfully!");
            } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Data processing failed.");
        }
    }


    @PostMapping("/updateEmployee")
    public ResponseEntity<String> updateEmployee(
            @ModelAttribute EmployeeVO employeeVO,
            @RequestParam(value = "employeePhoto", required = false) MultipartFile employeePhoto) {

        // 파일을 저장할 경로
        String uploadDir = "C:/Users/rkdgu/Desktop/IdeaProjects/oneshot/img/";

        // 폴더가 존재하지 않으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 저장 경로를 설정
        if (employeePhoto != null && !employeePhoto.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + employeePhoto.getOriginalFilename();
                File file = new File(uploadDir + fileName);
                employeePhoto.transferTo(file);

                // VO에 파일 경로를 설정
                employeeVO.setEmployeePhotoPath(fileName);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("File upload failed.");
            }
        }

        try {
            if (employeeService.updateEmployee(employeeVO) == 0) {
                return ResponseEntity.ok("수정이 정상적으로 되지 않았습니다.");
            }
            //EmployeeAuth테이블에 비밀번호 넣기
            EmployeeAuthVO employeeAuthVO = new EmployeeAuthVO();
            employeeAuthVO.setEmployeeNo(employeeVO.getEmployeeNo());
            if (employeeVO.getEmployeeBirth() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
                String EmployeeBirthPassword = employeeVO.getEmployeeBirth().format(formatter);
                employeeAuthVO.setEmployeePassword(bCryptPasswordEncoder.encode(EmployeeBirthPassword));
            }
            if(employeeDetailsService.insertEmployeeAuth(employeeAuthVO)==0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("비밀번호 등록이 정상적으로 되지 않았습니다.");
            }
            return ResponseEntity.ok("File and data uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Data processing failed.");
        }
    }


    // 사원 비활성화
    @DeleteMapping("/updateResignEmployee")
    public ResponseEntity<String> updateResignEmployee(@RequestBody List<EmployeeVO> employeeNos) {
        try {
            int result = employeeService.updateResignEmployee(employeeNos);
            if (result > 0) {
                return ResponseEntity.ok("삭제 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제할 사원이 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
        }
    }

    //테스트
    @PostMapping("/action")
    public ResponseEntity<?> performAction(Authentication authentication) {
        // 인증된 사용자 정보 가져오기
        EmployeeDetails userDetails = (EmployeeDetails) authentication.getPrincipal();

        // 비즈니스 로직
        return ResponseEntity.ok("Action performed by " + userDetails.getUsername());
    }
}
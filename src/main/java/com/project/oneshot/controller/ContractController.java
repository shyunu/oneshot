package com.project.oneshot.controller;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractItemVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.sales.contract.ContractCriteria;
import com.project.oneshot.sales.contract.ContractMapper;
import com.project.oneshot.sales.contract.ContractPageVO;
import com.project.oneshot.sales.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/sales")
public class ContractController {

    @Autowired
    @Qualifier("contractService") //서비스연결
    private ContractService contractService;

    @Autowired
    private ContractMapper contractMapper; // ContractMapper 주입

    // ----- 계약가격내역 ----- //
    @GetMapping("/contract")
    public String contract(ContractCriteria cri, Model model) {
        System.out.println("cri = " + cri);
        List<ContractVO> list = contractService.getList(cri);
        model.addAttribute("list", list);



        int totalCount = contractService.getTotalCount(cri);
        ContractPageVO pageVO = new ContractPageVO(cri, totalCount);
        model.addAttribute("pageVO", pageVO);

        List<ClientVO> clientList = contractService.getClientList();
        model.addAttribute("clientList", clientList);

        return "sales/contract";
    }

    // 계약 등록하기
    @PostMapping("/registForm")
    public String contractRegist(@ModelAttribute ContractVO vo) {
        // 계약서 파일을 사용하여 처리
        if (vo.getContractFile() != null && !vo.getContractFile().isEmpty()) {
            try {
                // 파일을 byte[]로 변환
                vo.setContractFileData(vo.getContractFile().getBytes()); // byte[]로 설정
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/errorPage"; // 에러 페이지 리다이렉트
            }
        }

        // 계약 등록 서비스 호출
        contractService.contractRegist(vo);

        // 성공 메시지 인코딩
        String originalString = "승인대기";
        String encodedString = "";
        try {
            encodedString = URLEncoder.encode(originalString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 계약 페이지로 리다이렉트
        return "redirect:/sales/contract?contractPriceStatus=" + encodedString;
    }

    // 이미지 조회
    @GetMapping("/file/view/{contractPriceNo}")
    public String getFile(@PathVariable("contractPriceNo") Integer contractPriceNo, Model model) {
        // ContractVO 객체 생성
        ContractVO vo = new ContractVO();
        vo.setContractPriceNo(contractPriceNo); // contractPriceNo 설정

        // 계약서 파일 데이터 조회
        ContractVO contractFile = contractMapper.getImageByContractPriceNo(vo); // contractMapper 인스턴스를 통해 호출

        if (contractFile != null) {
            String base64File = Base64.getEncoder().encodeToString(contractFile.getContractFileData());
            System.out.println("base64File = " + base64File);
            model.addAttribute("contractFile", "data:image;base64," + base64File); // 변수 이름을 "contractFile"로 설정
            //model.addAttribute("contractFile", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBw8QEBAPDw8NDw8PDxAPEA0ODQ8NDQ0PFREWFhURFRUYHSggGBolGxUVITEhJSkrLi8uFx8zODMtNygtLisBCgoKDg0OFxAQFysdHh0rLSsrLS0tLS0tLS0rLS0rKy0tLS0tLSstLSstLS0tKy0rLSsuLSstKy0tLSstLS0rK//AABEIARMAtwMBIgACEQEDEQH/xAAcAAACAwEBAQEAAAAAAAAAAAAAAQIDBAUGBwj/xAA7EAACAQIEAwYDBgUDBQAAAAAAAQIDEQQSITEFQVEGImFxgZETobEHIzLB4fBCUnKC8WKiwhQVM4OS/8QAGAEBAQEBAQAAAAAAAAAAAAAAAAECAwT/xAAgEQEBAQEAAgICAwAAAAAAAAAAAQIRAzESIRNBIlFx/9oADAMBAAIRAxEAPwD7MAwOgQwAAAAAAYGXiOPo4em6tepClTjvObsr9F1fggNQXPk/aH7YYwllwdFSjsq1fMs3jGC1t5s8vU+1ribnFxdCyd8nwrqXhvdInTj9ABc/P3GPtV4nWVqTjhotLSlFSqf/AE72Xl7nn1254knd43FX8a0voOnH6hQz4LwD7W8fSaWIhTxlLm7KlXivCUdH6o+sdle2WD4jH7ibjVSvLD1LRqx8UtmvFDpx6EYgKGAAADEMIQwAAAAAiAAFAAAAIBNgc/jvGqGCoyr15WhHRRWs6k3tCK5t/q9Efnvtd2or8QrudWVoRbVKim/h0o9F1fWXPyR0/tH7SzxuMqRi38DDSnRpR5Xi7TqeLbXtY8RCUbtyei28X1M1VeJalJvlsvBcicpRjlet0tuhD4LlKy05vwXiZK7d2k76ga62IW61XTmmRzKS/bsZ6SvZPmToaNrw28SDbBWSajd/zRetvI1YHHzpVI1aU5QnBpxknlnCXmY6V1FNPR+trA4uV3/FHR9JRKP0x2D7RriODhWdlVg/h1orlUjztyTVn6noz4H9jPH3Qxyw8pfdYxfCa5RrJN05eusf7kffCxAADKAAAIBDAKQDABAAgGJjIsIjJnnu3fGXguH4nERdqkYZKT6VajUIv0cr+h35s8F9scHLhk7P8NehJrqs9re7T9AsfCIztF63066sotazfnb10NPw+62+dkl0V9zNW1fgZVPD1btt3tslzk9iuGElKWkW1vZbtdRUZJL1/I9n2VwHxZ1Lr8Cjry2Tymda5G8Y+VeLnRcZ6XeV2LKWHvKSvZvM/S/+Gd/tLhfh10oq7qW06u9vzOVO+qSeZatc1a6a+QlTWeVTG6g01uk7/vzLoK0VPVNpJrra/wCRrqQTjFNK9vey1MtWaytP/HR/IvU419mKjWMwso7/APVYdrlqq0T9Vn5Q4FdV6MrXcalOVurzp2P1eaiUhiGVAAAEAAAAAABEAAKCMhkZBFVRnjPtNpOpw3ERXL4c3/TGpFv5I9hVZyeKU41ITpzV4zi4SXVNWYV+aq0tEuuvz/Qro0s0W/E7PaLgk8LWlSfejCV4S/npvZ/L3uc+leK0V9bnKukjnwhZ28z6j9mNBSo15b/e6v8A9NO3zzezPnn/AElWUsypTfLb2+R9D+zupKjJ05RcVUWzVrtN3+pz8np18M5XH7R4FviFHS9pR0/uOdicJGnOvKo8qyaJq0m3Jr/i36nvu1PCqrqwqUk08rWZK7i1KMk/9tvU4VThuDozVTFzc81SF5zvJtXu2orZO+XXozOdOmse7HkqMXamlCU3e2mrebutfNDfAK06ipqE7uLdnFpr9u59GxGPw2JyRoUq0YSzQpz+Co03llbdP+aJ7DCYKKtOUU52V5Ws2X58qXx/T432U4PKePp0ZKUHSmpVOTjk731t7n3jgFWaqVKc27NKUIvXLZ66+KaZ4OtgJ0uMqVJqCxWGm3KyajKNk3b0Xue24bOarRU7OSeVtbO8WPlfnCYn49PRAAHqeMAMCIQDEVQAwIICGxMoGQkSZCQGeqczFHTqnOxC3KjwfbnB56MpKKc4NZXbXV2t8xPg+GVCFanSvmpxqJR1lLNFP3O7xWjmi1a/h5ambs3C+Fp02rOi5UfBxi+414ZXH2Z5/L9V6/FO57/TxvEKuLvTdLD0X8TMvhpN1qUku7n1SWvp4np+E0qsHF1acE001OG0tNdOXQ7scGr3sFemlbwON5Y7x1KlGMoLxR57EdnYuUlKEakJyUnGesXbb2PS4f8A8a8NB05p6dAnXPwXDIQtaEIqKsoxikorwNslYvehTNip1y8Xh716NRWzQjVim72WbLv4aM7PCqH3t3rlTlfrJ6XI4LDRqTea/di7Wdmm2jr4ehGCtFb7t6tnXGO2Vy35ZM3K5DEhnd5QAAAAAAAAMCtiGIoTIyJkWgM1RGGvE6FRGWtEqOFjKZz8DKNObWym9Vb+Lk/e/udrFUzh46iY3mV0xu5dhSM2MnFZczsr/loGDr54Jv8AEtJefUK6T3PHqcr2ZssWYDjKlTvGM7u/3c45J3XJp/U1cOlVnHNWpxpT1WWE3Ujv1svocqni6FJ96ST97E32noXtFTm+eSOe3sHT4Wz6juSZWyjCYp1I5sk4X2U7J/Jl1yyud+mvhT+8a/0fmjro4nCJ3q+cWl+/Q7qR6fH6ePyewhgBtgAAAAAAAAABAQxFCEyQmBVNGapE1yRRUQRzcRA5WKo3O7ViYMRSCuFSk6cr8npJdUbqkFONr6SW66Gijw/M80l3d7fzfocejxNNzdu6qk4uPOLjJr6WOHmk9vR4bfTFU7O001JxU7c5tzfzOpgcArru2iuSVkaqFWEtU019PM0PFRjzS9Tzvb+bXONNrKyMuIrW7q3fyRTV4gn+HX6EcNHXM9W9Ww5cbsBWVOcZPZb+VtT1EJJpNapq6fgeQOp2XxN41KLd3Sn3evw2rr53O/i1+nm8uf27oAB2cAIYAIBiAAAYEBEmKxoILDHYCpoqnE0SRCSAx1IlEKOZ67L5mycbk6dPQqKXT0PmWPpSoY7EUn+CrKNaH90Vf/cmfVXGx5Ttnw1OdGtZXs4N+t1/yOXmncu/gvNPPU6TWqNNOlfcuoUjZSoHje21TSpG6nGxKFAuhTDNqtQuU8CjL/uLafceGlnXJtTjlfnqzoOFkW9nMN36tVr8VoR8ldt+7+R0xP5Rz3qfGu9cZEdj1vEYEM5JTQ4GIYECGAAILADZQrBchOoV3b3LwWuXQrcRjsUQnDQuSIPYsRKIyRj4thPi0WrXlHvR81+lzdJaGariHBaK7fXkiWdnFzeXrykaFjZRpnRxeET78Vo3quj6+RT8Ox47iyvZNzUVZCxRBxYld2itW9Ei8OrKdJ1HlW38UuiOzRpKCsttkvAzUaDjFKL83/MyalOLvunuvzPRjHI8u9/JpQyMJp6r/BI2wraIk57kShJklMSAosTuBWInBY2UzqdPcKsuXXQr5e4gsUBhT/DH+lfQZQkyRAmAolkdl5FcSeayJQ5FdSjp4kk9Swnoc2LcH1i9GiNaFtVrF7P8jfVopmV0WrreL3XIms/KNZ1ysFaZs4Zhbd+W8lp/pj+pXTwd5q+sFr5+B1oo54zy9rpvf1yBRBoYjo4qK8Gu9DfmuTQ6GIUtNpc4vf06krldajGW+j6rRo0LZkRQTSs3drnzYxAAAkUMBABVS1b87iq7e4Ub2bW6f7RKdpRuvYC2P4Y+S+hEdB9yP9K+gSRIISZYillsdihRJMjEkAFkWVkoMlFhGaGRqvQgjRjb3LSFLZeRMAIyYyMwIgAGggAYCYCGAmAriAhhefmVttNksM+80GIjqBbh5Xiv3zJzM+Flo10ZplsRFJKDIoCqmiRWmWAA47kRoC0hW2ZMjUWnqjIkkMBEAQkTKywACA0AGNEZAERyGiEwiKGSpdREVlg7STNFdXRmkalrEoy0JWlbqjYnoc6TtJeZ0E9AiCGCAKGWIrZOIDBAAFiYPl5kUxp6peDZkTExiIEyKiyUhrYohkYnAtAdFViJcyDL1CK6jLshRW6eIE+SAqrVLP0AqqZIvovSxTLcsosDFi9H8zdSndGTHIlhJXin009tANggGAEiCLAAAACURQ/E/IaCnu/QzUWCGIiozGRkSZQrjuxAEDYkxMIlFplqmkzTZIrDxKtlinzensI5/aCo4uD3sm7ebsBpY7VRBTZKaK4hDxkLq5kwL/Euln7/AODfP8LORTrZaq6S7vryA7ENhmeNbkaIpgNE0RQwG2IQIC1oVLn5/kTI0lp7/UyJiACCDJshzJSKERkSEVEUiaEMAm9DLLc01WZ7CDz/AB53nbkkvfcDJx6M3Wnlk0u7p/agHWnq2yvmWTK7FRa9med4knq1unfyPQ30OViMNnqqP8L70v6eaBG3hyzxjUatmiml0ujbYFbkFwhSQBJhcKLDjuIlDcC0Udl5BIZgCAAAr5kiIXNBsCOYGESGRjuSuSiFVlJObKqs0rq6ule19fOxocLiMLty6sC6rByeVK7eoBXamVMsqFbCByI07Xv6EZsoo1HdroFdOwWKqFdPR7mgIgIm0RYAicCBOBKJkiJIypCbGyMiiImNkWVCHcEJlAhghSYCZ4vt1jq1OdOFFWdRa1FG8o5bWSfK937HsrmKsm01OjGakrSWZPMl4WHeS8nf99N4sm5b6g4PT+7hUlrKcIu/oIuwHdio2yxSVot3aVtgM5lmZKmrLbYuq7FKYwNMlIzUV35eS+rAAJVNGmup0aL0AAqxiYARESUNwAotQxAYUEZABYiDIgBoMBAA0RmAAQYMACppAAAf/9k="); // 변수 이름을 "contractFile"로 설정
        } else {
            model.addAttribute("contractFile", null); // 파일이 없는 경우
        }
        return "sales/contractFileView"; // Thymeleaf 템플릿 이름
    }


    // ----- 날짜 데이터 변환 ----- //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // 엄격한 날짜 형식 검사
        binder.registerCustomEditor(Date.class, new org.springframework.beans.propertyeditors.CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitForm(ContractVO contractVO) {
        return "result";
    }
}
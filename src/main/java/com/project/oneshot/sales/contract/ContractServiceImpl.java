package com.project.oneshot.sales.contract;

import com.project.oneshot.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("contractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public void contractRegist(ContractVO contractVO) {
        // 중복되는 계약을 확인하여, 신규 계약의 범위와 겹치는 기존 계약이 있는지 확인
        int overlappingCount = contractMapper.countOverlappingContracts(
                contractVO.getProductNo(),
                contractVO.getClientNo(),
                contractVO.getContractSdate(),
                contractVO.getContractEdate(),
                contractVO.getContractPriceStatus()
        );
        System.out.println("overlappingCount = " + overlappingCount);

        // 중복되는 계약이 있는 경우 처리
        if (overlappingCount > 0) {
            // 기존 계약의 수정이 필요한 상황 처리
            List<ContractVO> overlappingContracts = contractMapper.getOverlappingContracts(
                    contractVO.getProductNo(),
                    contractVO.getClientNo(),
                    contractVO.getContractSdate(),
                    contractVO.getContractEdate(),
                    contractVO.getContractPriceStatus()
            );

            // 신규 계약의 시작일과 종료일을 LocalDate로 변환
            LocalDate newContractStartDate = contractVO.getContractSdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate newContractEndDate = contractVO.getContractEdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            for (ContractVO existingContract : overlappingContracts) {
                LocalDate existingStartDate = existingContract.getContractSdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate existingEndDate = existingContract.getContractEdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // 케이스 4: 신규 계약이 기존 계약을 완전히 포함하는 경우
                if (newContractStartDate.isBefore(existingStartDate) && newContractEndDate.isAfter(existingEndDate) || (newContractStartDate.isEqual(existingStartDate) && newContractEndDate.isEqual(existingEndDate))) {
                    // 기존 계약의 시작일과 종료일을 신규 계약의 시작일과 종료일로 업데이트
                    existingContract.setContractPrice(contractVO.getContractPrice());
                    existingContract.setContractSdate(contractVO.getContractSdate());
                    existingContract.setContractEdate(contractVO.getContractEdate());
                    contractMapper.deleteContract(existingContract);
                } else {
                    // 케이스 3: 신규 계약이 기존 계약의 중간에 있는 경우 -> 기존 계약을 분할
                    if (newContractStartDate.isAfter(existingStartDate) && newContractEndDate.isBefore(existingEndDate)) {
                        // 기존 계약의 원래 종료일을 변수에 저장
                        Date originalEndDate = existingContract.getContractEdate();
                        System.out.println("Original End Date: " + originalEndDate);

                        // 기존 계약의 첫 번째 부분 종료일 설정
                        LocalDate firstPartEndDate = newContractStartDate.minusDays(1);
                        existingContract.setContractEdate(Date.from(firstPartEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        contractMapper.updateContract(existingContract);

                        // 두 번째 계약 (뒷부분) 생성
                        //ContractVO splitContract = new ContractVO();
                        //splitContract.setProductNo(existingContract.getProductNo());
                        //splitContract.setClientNo(existingContract.getClientNo());
                        //splitContract.setEmployeeNo(existingContract.getEmployeeNo());
                        //splitContract.setContractPrice(existingContract.getContractPrice());
                        //splitContract.setContractSdate(Date.from(newContractEndDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())); // 뒷부분의 시작일
                        //splitContract.setContractEdate(originalEndDate); // 뒷부분의 종료일 (원래 계약 종료일)
                        //splitContract.setContractPriceStatus(existingContract.getContractPriceStatus());

                        // 새로운 분할 계약 등록
                        //contractMapper.getRegist(splitContract);
                    } else {
                        // 케이스 1: 기존 계약의 종료일이 신규 계약의 종료일보다 늦으면
                        if (newContractEndDate.isBefore(existingEndDate)) {
                            System.out.println("case1");
                            LocalDate newExistingStartDate = newContractEndDate.plusDays(1);
                            existingContract.setContractSdate(Date.from(newExistingStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                            contractMapper.updateContract(existingContract);
                        }

                        // 케이스 2: 신규 계약이 기존 계약의 중간에 겹치는 경우, 기존 계약의 종료일을 조정
                        if (newContractStartDate.isAfter(existingStartDate) && newContractStartDate.isBefore(existingEndDate)) {
                            System.out.println("case2");
                            LocalDate newExistingEndDate = newContractStartDate.minusDays(1);
                            existingContract.setContractEdate(Date.from(newExistingEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                            contractMapper.updateContract(existingContract);
                        }
                    }
                }
            }
        }

        // 신규 계약 등록
        contractMapper.getRegist(contractVO);
    }

    @Override
    public List<ContractVO> getList(ContractCriteria cri) {

        List<ContractVO> list = contractMapper.getList(cri);

        LocalDate currentDate = LocalDate.now();
        for (ContractVO vo : list) {
            LocalDate contractEdate = vo.getContractEdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); //코드 분석 필요
            long daysBetween = ChronoUnit.DAYS.between(contractEdate, currentDate);

            if(daysBetween > 0 || daysBetween == 0) {
                vo.setContractDday("계약만료");
            } else {
                vo.setContractDday("D" + daysBetween);
            }
        }

        return list;
    }

    @Override
    public List<ClientVO> getClientList() {

        List<ClientVO> list = contractMapper.getClientList();
        return list;
    }

    @Override
    public ClientVO getContractUpdateList(int clientNo) {

        ClientVO updateList = contractMapper.getContractUpdateList(clientNo);
        return updateList;
    }

    @Override
    public List<ProductVO> getContractProductList() {
        List<ProductVO> list = contractMapper.getContractProductList();
        return list;
    }

    @Override
    public Integer getContractPriceNo() {
        return contractMapper.getContractPriceNo();
    }

    @Override
    public ContractVO getContractDetails(int contractPriceNo) {
        return contractMapper.getContractDetails(contractPriceNo);
    }

    @Override
    public void contractModify(ContractVO vo) {
        System.out.println("ContractServiceImpl.contractModify");
        System.out.println("vo = " + vo);
        contractMapper.getContractModify(vo);
    }

    @Override
    public int getTotalCount(ContractCriteria cri) {
        return contractMapper.getTotalCount(cri);
    }

    @Override
    public void updateContract(ContractVO vo) {
    }

    @Override
    public void updateContractDate(int clientNo, int productNo, Date contractSdate, Date contractEdate) {
    }

    @Override
    public void approveContract(int contractPriceNo) {
        contractMapper.approveContract(contractPriceNo);
    }

    @Override
    public void rejectContract(int contractPriceNo) {
        contractMapper.rejectContract(contractPriceNo);
    }
}
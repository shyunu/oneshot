package com.project.oneshot.hr.department;


import com.project.oneshot.entity.mybatis.DepartmentVO;

import java.util.List;

public interface DepartmentService {
    public int insertDepartment(DepartmentVO vo);//등록
    public List<DepartmentVO> selectDepartment();

    //public int productInsert(ProductVO vo); //등록
//    public int productInsert(ProductVO vo, List<MultipartFile> list); //등록
//
//    public ArrayList<ProductVO> getList(String userId, Criteria cri); //목록
//    public int getTotal(String userId, Criteria cri); //전체게시글 수
//
//    public ProductVO getDetail(int prodId); //상세내역
//    public int productUpdate(ProductVO vo); //수정
//    public void productDelete(int prodId); //삭제
//
//    //카테고리 1단계
//    public ArrayList<CategoryVO> getCategory();
//    //카테고리 2,3단계
//    public ArrayList<CategoryVO> getCategoryChild(CategoryVO vo);
//    //파일데이터 조회
//    public ArrayList<ProductUploadVO> getImgs(int prodId);




}

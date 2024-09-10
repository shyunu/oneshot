// 첫 번째 팝업의 다음 버튼 클릭 시 두 번째 팝업 보이기
document.getElementById('buttonNext').addEventListener('click', function () {
    document.getElementById('popupDeleteConfirmation').style.display = 'none';
    document.getElementById('popupFinalWarning').style.display = 'flex';
});

// 첫 번째 팝업의 닫기 버튼 클릭 시 팝업 닫기
document.getElementById('buttonClosePopup').addEventListener('click', function () {
    document.getElementById('popupDeleteConfirmation').style.display = 'none';
});

// 두 번째 팝업의 삭제 버튼 클릭 시 경고 메시지 (실제 삭제 로직 필요 시 추가)
document.getElementById('buttonDelete').addEventListener('click', function () {
    alert('항목이 삭제되었습니다.');
    document.getElementById('popupFinalWarning').style.display = 'none';
});

// 두 번째 팝업의 닫기 버튼 클릭 시 팝업 닫기
document.getElementById('buttonFinalClose').addEventListener('click', function () {
    document.getElementById('popupFinalWarning').style.display = 'none';
});

// 두 번째 팝업의 뒤로 버튼 클릭 시 첫 번째 팝업으로 돌아가기
document.getElementById('buttonBack').addEventListener('click', function () {
    document.getElementById('popupFinalWarning').style.display = 'none';
    document.getElementById('popupDeleteConfirmation').style.display = 'flex';
});

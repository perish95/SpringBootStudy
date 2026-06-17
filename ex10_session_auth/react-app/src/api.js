import axios from 'axios';

// axios는 기본 파일이 아니라 설치 필요
// axios: 요청을 날리는 라이브러리
// withCredentials: 쿠키 사용할 수 있도록 설정 
const api = axios.create({
  baseURL: 'http://localhost:8080', // 백엔드 서버 주소
  withCredentials: true // 로그인/로그아웃/데이터 요청 시 쿠키를 항상 포함
});

export default api;
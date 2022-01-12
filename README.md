<p>
  
<a href="https://github.com/pigs-pango-team/gg-pigs-api/actions">  
  <img alt="Build" src="https://github.com/pigs-pango-team/gg-pigs-api/workflows/build/badge.svg" />
</a>
<a href="https://github.com/pigs-pango-team/gg-pigs-api/issues">
  <img alt="Issues" src="https://img.shields.io/github/issues/pigs-pango-team/gg-pigs-api?color=0088ff" />
</a>
<a href="https://github.com/pigs-pango-team/gg-pigs-api/pulls">
  <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/pigs-pango-team/gg-pigs-api?color=0088ff" />
</a>
</p>

### https://gg-pigs.com

<br>

### 개발 키워드 / 이슈 🔥

**1. 이미지 서버(저장소) 선택 : GitHub Repository vs Google Drive**

170 x 56 의 이미지 파일 기준, 이미지 로딩 속도는 다음과 같습니다.

- Google Drive : 300ms ~ 400ms (눈으로도 버벅거림을 느낄 수 있음)
- Github Repository : 40ms
( * 이미지 크기, 요청의 수에 따라 측정한 ms 의 값은 다를 수 있습니다. )

Github Repository 의 로딩 속도가 약 10배 정도 빠른 것을 확인할 수 있습니다.

<br>

**2. API 캐시 적용**

광고 리스트 조회 API : 변화 주기 적다고 판단 → 캐싱 적용(max-age: 5s)

<br>

**3. 이메일 알림 Kafka 활용**

광고 등록 요청 시 이메일 알림 → Kafka 활용

<br>

**4. AdBlock 데이터 블락 이슈**

AdBlock과 같은 플러그인에 의해 api/데이터가 blcok

- `ad`, `advertisement` 등의 키워드 변경 → `poster`


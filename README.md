<p>
  
<a href="https://github.com/95team/gg-pigs-api/actions">  
  <img alt="Build" src="https://github.com/95team/gg-pigs-api/workflows/build/badge.svg" />
</a>
<a href="https://github.com/95team/gg-pigs-api/issues">
  <img alt="Issues" src="https://img.shields.io/github/issues/95team/gg-pigs-api?color=0088ff" />
</a>
<a href="https://github.com/95team/gg-pigs-api/pulls">
  <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/95team/gg-pigs-api?color=0088ff" />
</a>
</p>

## https://gg-pigs.com

<br>

## How To Run

**1. infra 환경을 실행합니다. (mysql, redis, kafka, ...)**

```shell
make all # infra 환경을 실행합니다.
```

> `make all` : infra 환경을 실행합니다. <br>
> `make clear` : infra 환경을 종료합니다.

**2. application.yml 에 비밀 값을 설정합니다.**

> 별도로 공유합니다.

**3. 애플리케이션을 실행합니다.**

<br>

## Architecture

![광고돼지 아키텍처 drawio (1)](https://user-images.githubusercontent.com/35790290/153448150-c7a7c99d-734b-4897-8574-bc33a9759893.png)

<br>

### `apps/`

- Application 모듈

- [X] **app-api**
  - [X] 기능
  - [ ] 모듈화 작업

<br>

### `modules/`

- 서비스 독립적, 이후 외부로 추출할 수 있는 모듈(패키지)
- apps/ 패키지 의존성 X

- [x] **[security](https://github.com/95team/gg-pigs-api/tree/develop/modules/security)**
  - [X] 기능
  - [X] 모듈화 작업 필요
- [ ] **ems**
  - [X] 기능
  - [ ] 모듈화 작업 필요
- [ ] **redis**
  - [X] 기능
  - [ ] 모듈화 작업 필요
- [ ] **github**
  - [X] 기능
  - [ ] 모듈화 작업 필요

<br>

#################
# 주의
# 개발자가 sudo java로 배포를 한 경우에는 root 권한으로 실행됐기 때문에
# codedeploy가 kill을 할 수 없음.(sudo 권한이 필요하기 때문)
#
# 배포자동화가 원활하게 이뤄지게 하려면 배포는 항상 codedepoly로 진행해야함.
#################

#!/usr/bin/env bash
# 기존 Nginx에 연결되어 있진 않지만, 실행중이던 스프링 부트 종료

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH) # stop.sh가 있는 경로 찾기
source ${ABSDIR}/profile.sh # import 구문

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z "${IDLE_PID}" ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> $IDLE_PID 프로세스에 SIGTERM(kill -15) 보내기"
  kill -15 ${IDLE_PID}

  # 종료될 때까지 최대 10초 대기
  TIMEOUT=10
  while [ $TIMEOUT -gt 0 ]; do
    if ps -p ${IDLE_PID} > /dev/null; then
      sleep 1
      TIMEOUT=$((TIMEOUT-1))
    else
      echo "> 프로세스 정상 종료됨"
      break
    fi
  done

  # 아직 살아있으면 강제 종료
  if ps -p ${IDLE_PID} > /dev/null; then
    echo "> 프로세스가 정상 종료되지 않아 강제 종료(kill -9) 진행"
    kill -9 ${IDLE_PID}
    sleep 2
  fi
fi
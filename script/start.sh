#!/usr/bin/env bash
# 배포할 신규버전 스프링 부트 프로젝트를 stop.sh로 종료한 'profile'로 실행

# 현재 실행중이지 않은 Profile을 찾아서 실행
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH) # start.sh 경로 찾기
source ${ABSDIR}/profile.sh # import 구문

REPOSITORY=/home/ec2-user/app/project
PROJECT_NAME=bookspot

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."
nohup java -jar \
    -Dspring.config.location=classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-prod.properties \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
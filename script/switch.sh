#!/usr/bin/env bash
# Nginx가 바라보는 스프링 부트를 최신 버전으로 변경

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"

    # echo "..." 엔진엑스가 변경할 프록시 주소 생성
    # | sudo tee ... 앞선 출력문을 파일에 덮어씌우기
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    # 엔진엑스 설정 다시 불러오기. restart와는 다르게 끊김이 없음.
    echo "> 엔진엑스 Reload"
    sudo service nginx reload
}
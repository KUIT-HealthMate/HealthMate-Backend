CONTAINER_ID=$(docker ps -qf "name=my_app")
if [ -z "$CONTAINER_ID" ]; then
  # 컨테이너가 실행 중이지 않으면 알림 보내기
  curl -X POST -H 'Content-type: application/json' --data '{"text":"The container 'my_app' is not running!"}' $DISCORD_URL
fi